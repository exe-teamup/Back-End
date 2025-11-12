package com.team.exeteamup.service.impl;

import com.team.exeteamup.dto.request.joinRequest.HandleJoinRequestRequest;
import com.team.exeteamup.dto.request.joinRequest.JoinRequestRequest;
import com.team.exeteamup.dto.response.JoinRequestResponse;
import com.team.exeteamup.entity.Group;
import com.team.exeteamup.entity.JoinRequest;
import com.team.exeteamup.entity.User;
import com.team.exeteamup.enums.joinRequest.JoinRequestStatus;
import com.team.exeteamup.enums.joinRequest.JoinRequestType;
import com.team.exeteamup.exception.EmptyDeniedReasonException;
import com.team.exeteamup.exception.FullGroupException;
import com.team.exeteamup.mapper.JoinRequestMapper;
import com.team.exeteamup.repository.JoinRequestRepository;
import com.team.exeteamup.service.inter.GroupService;
import com.team.exeteamup.service.inter.JoinRequestService;
import com.team.exeteamup.service.inter.UserService;
import jakarta.persistence.EntityNotFoundException;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.List;
import java.util.stream.Collectors;

@Service
@Transactional(readOnly = true)
@RequiredArgsConstructor
public class JoinRequestServiceImpl implements JoinRequestService {


    private final JoinRequestRepository joinRequestRepository;
    private final JoinRequestMapper joinRequestMapper;
    private final GroupService groupService;
    private final UserService userService;


    @Override
    public JoinRequest findById(long id) {
        return joinRequestRepository.findById(id)
                .orElseThrow(() -> new EntityNotFoundException(
                        "No join request found with id " +
                                id)
                );
    }


    @Override
    public JoinRequestResponse findResponseById(long joinRequestId) {
        return joinRequestMapper.toResponse(findById(joinRequestId));
    }


    @Override
    public List<JoinRequestResponse> findAll() {
        return joinRequestRepository
                .findAll()
                .stream()
                .map(joinRequestMapper::toResponse)
                .collect(Collectors.toCollection(ArrayList::new));
    }


    @Override
    public List<JoinRequestResponse> findByStudentId(long studentId) {
        return joinRequestRepository
                .findByUser(userService.findById(studentId))
                .stream()
                .map(joinRequestMapper::toResponse)
                .collect(Collectors.toCollection(ArrayList::new));
    }


    @Override
    @Transactional
    public JoinRequestResponse save(JoinRequestRequest request) {

        Group group = groupService.findGroupById(request.getGroupId());
        User user = userService.findById(request.getStudentId());

        checkGroupCapacityAndResponse(group, request.getRequestType());

        JoinRequest joinRequest = new JoinRequest(user,
                group,
                LocalDateTime.now(),
                JoinRequestStatus.PENDING,
                null,
                request.getRequestType(),
                LocalDateTime.now());

        return joinRequestMapper
                .toResponse(joinRequestRepository.save(joinRequest));
    }


    private void checkGroupCapacityAndResponse(Group group, JoinRequestType joinRequestType) {
        if(group.getMemberCount() == 6) {
            if(joinRequestType == JoinRequestType.STUDENT_REQUEST) {
                throw new FullGroupException("Nhóm đã đầy, không thể gửi yêu cầu tham gia");
            } else {
                throw new FullGroupException("Nhóm đã đầy, không thể mời sinh viên vào nhóm");
            }
        }
    }


    @Override
    @Transactional
    public JoinRequestResponse handleJoinRequest(long joinRequestId,
                                                 HandleJoinRequestRequest handleJoinRequestRequest) {

        JoinRequest joinRequest = findById(joinRequestId);
        JoinRequest updatedJoinRequest;

        if(joinRequest.getRequestType() == JoinRequestType.STUDENT_REQUEST) {
            updatedJoinRequest = handleStudentJoinRequest(joinRequest, handleJoinRequestRequest);
        } else  {
            updatedJoinRequest = handleGroupInvitationJoinRequest(joinRequest, handleJoinRequestRequest);
        }

        return joinRequestMapper.toResponse(updatedJoinRequest);
    }


    private JoinRequest handleStudentJoinRequest(JoinRequest joinRequest, HandleJoinRequestRequest handleJoinRequestRequest) {

        JoinRequestStatus status = handleJoinRequestRequest.getRequestStatus();
        String denyReason = handleJoinRequestRequest.getDenyReason();

        User user = joinRequest.getUser();
        Group group = joinRequest.getGroup();

        JoinRequest result;

        switch (status) {
            case APPROVED -> { // group-leader approved
                groupService.addMember(group.getGroupId(), user.getUserId());
                joinRequest.setRequestStatus(JoinRequestStatus.APPROVED);
                // add event listener
            }
            case DENIED -> { // group-leader denied
                if (denyReason == null || denyReason.isBlank()) {
                    throw new EmptyDeniedReasonException("Vui lòng điền lý do khi từ chối");
                }
                joinRequest.setRequestStatus(JoinRequestStatus.DENIED);
                joinRequest.setDenyReason(handleJoinRequestRequest.getDenyReason());
                    // add event listener
            }
            case WITHDRAWN -> { // user withdrawn
                joinRequest.setRequestStatus(JoinRequestStatus.WITHDRAWN);
            }
            default -> {
                throw new IllegalStateException("Trạng thai xử lý không hợp lệ " + status);
            }
        }

        joinRequest.setUpdatedAt(LocalDateTime.now());
        result = joinRequestRepository.save(joinRequest);

        return result;
    }


    private JoinRequest handleGroupInvitationJoinRequest(JoinRequest joinRequest, HandleJoinRequestRequest handleJoinRequestRequest) {

        JoinRequestStatus status = handleJoinRequestRequest.getRequestStatus();
        String denyReason = handleJoinRequestRequest.getDenyReason();

        User user = joinRequest.getUser();
        Group group = joinRequest.getGroup();

        JoinRequest result;

        switch (status) {
            case APPROVED -> { // user approved
                groupService.addMember(group.getGroupId(), user.getUserId());
                joinRequest.setRequestStatus(JoinRequestStatus.APPROVED);
                // add event listener
            }
            case DENIED -> { // user denied
                if (denyReason == null || denyReason.isBlank()) {
                    throw new EmptyDeniedReasonException("Vui lòng điền lý do khi từ chối");
                }
                joinRequest.setRequestStatus(JoinRequestStatus.DENIED);
                joinRequest.setDenyReason(handleJoinRequestRequest.getDenyReason());
                // add event listener
            }
            case WITHDRAWN -> { // group-leader withdrawn
                joinRequest.setRequestStatus(JoinRequestStatus.WITHDRAWN);
            }
            default -> {
                throw new IllegalStateException("Trạng thai xử lý không hợp lệ " + status);
            }
        }

        joinRequest.setUpdatedAt(LocalDateTime.now());
        result = joinRequestRepository.save(joinRequest);

        return result;
    }


    @Override
    @Transactional
    public JoinRequestResponse delete(long joinRequestId) {
        JoinRequest joinRequest = findById(joinRequestId);

        joinRequestRepository.delete(joinRequest);

        return joinRequestMapper
                .toResponse(joinRequest);
    }
}