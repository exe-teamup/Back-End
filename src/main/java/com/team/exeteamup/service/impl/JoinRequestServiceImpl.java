package com.team.exeteamup.service.impl;

import com.team.exeteamup.dto.request.joinRequest.HandleJoinRequestRequest;
import com.team.exeteamup.dto.request.joinRequest.JoinRequestRequest;
import com.team.exeteamup.dto.response.JoinRequestResponse;
import com.team.exeteamup.entity.Group;
import com.team.exeteamup.entity.JoinRequest;
import com.team.exeteamup.entity.Student;
import com.team.exeteamup.enums.JoinRequestStatus;
import com.team.exeteamup.mapper.JoinRequestMapper;
import com.team.exeteamup.repository.JoinRequestRepository;
import com.team.exeteamup.service.GroupService;
import com.team.exeteamup.service.JoinRequestService;
import com.team.exeteamup.service.StudentService;
import jakarta.persistence.EntityNotFoundException;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.time.LocalDateTime;
import java.util.List;

@Service
@Transactional(readOnly = true)
@RequiredArgsConstructor
public class JoinRequestServiceImpl implements JoinRequestService {

    private final JoinRequestRepository joinRequestRepository;
    private final JoinRequestMapper joinRequestMapper;
    private final GroupService groupService;
    private final StudentService studentService;

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
                .toList();
    }

    @Override
    public List<JoinRequestResponse> findByUserId(long userId) {
        return joinRequestRepository
                .findByUserId(userId)
                .stream()
                .map(joinRequestMapper::toResponse)
                .toList();
    }

    @Override
    @Transactional
    public JoinRequestResponse save(JoinRequestRequest request) {

        Group group = groupService.findGroupById(request.getGroupId());
        Student student = studentService.findById(request.getStudentId());

        JoinRequest joinRequest = new JoinRequest(student,
                group,
                LocalDateTime.now(),
                JoinRequestStatus.PENDING,
                null,
                request.getRequestType());

        return joinRequestMapper
                .toResponse(joinRequestRepository.save(joinRequest));
    }

    @Override
    @Transactional
    public JoinRequestResponse handleJoinRequest(HandleJoinRequestRequest handleJoinRequestRequest) {

        JoinRequest joinRequest = findById(handleJoinRequestRequest.getJoinRequestId());

        joinRequest.setRequestStatus(handleJoinRequestRequest.getRequestStatus());
        joinRequest.setDenyReason(handleJoinRequestRequest.getDenyReason());

        return joinRequestMapper.toResponse(joinRequestRepository.save(joinRequest));
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
