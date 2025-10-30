package com.team.exeteamup.service.impl;

import com.team.exeteamup.exception.AppException;
import com.team.exeteamup.dto.request.SemesterRequest;
import com.team.exeteamup.dto.response.SemesterResponse;
import com.team.exeteamup.entity.Semester;
import com.team.exeteamup.mapper.SemesterMapper;
import com.team.exeteamup.repository.SemesterRepository;
import com.team.exeteamup.service.SemesterService;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Optional;
import java.util.stream.Collectors;

@Service
@RequiredArgsConstructor
public class SemesterServiceImpl implements SemesterService {
    private final SemesterRepository semesterRepository;

    @Override
    public SemesterResponse createSemester(SemesterRequest semesterRequest) {
        if (semesterRepository.existsBySemesterCode(semesterRequest.getSemesterCode())) {
            throw new AppException("Semester already exists");
        }

        Semester semester = Semester.builder()
                .semesterCode(semesterRequest.getSemesterCode())
                .semesterName(semesterRequest.getSemesterName())
                .startDate(semesterRequest.getStartDate())
                .endDate(semesterRequest.getEndDate())
                .semesterStatus(semesterRequest.getSemesterStatus())
                .build();
        Semester saved = semesterRepository.save(semester);
        return SemesterMapper.toResponse(saved);
    }

    @Override
    public List<SemesterResponse> getAllSemesters() {
        return semesterRepository.findAll()
                .stream()
                .map(SemesterMapper::toResponse)
                .collect(Collectors.toList());
    }

    @Override
    public SemesterResponse getSemesterById(Long semesterId) {
        Semester semester = semesterRepository.findById(semesterId)
                .orElseThrow(() -> new AppException("Semester not found"));
        return SemesterMapper.toResponse(semester);
    }

    @Override
    public SemesterResponse updateSemester(Long semesterId, SemesterRequest semesterRequest) {
        Semester semester = semesterRepository.findById(semesterId)
                .orElseThrow(() -> new AppException("Kì học không tồn tại"));

        Optional.ofNullable(semesterRequest.getSemesterCode()).ifPresent(semester::setSemesterCode);
        Optional.ofNullable(semesterRequest.getSemesterName()).ifPresent(semester::setSemesterName);
        Optional.ofNullable(semesterRequest.getStartDate()).ifPresent(semester::setStartDate);
        Optional.ofNullable(semesterRequest.getEndDate()).ifPresent(semester::setEndDate);
        Optional.ofNullable(semesterRequest.getSemesterStatus()).ifPresent(semester::setSemesterStatus);

        Semester updatedSemester = semesterRepository.save(semester);
        return SemesterMapper.toResponse(updatedSemester);
    }

    @Override
    public void deleteSemester(Long semesterId) {
        Semester semester = semesterRepository.findById(semesterId)
                .orElseThrow(() -> new AppException("Kì học không tồn tại"));
        semesterRepository.delete(semester);
    }

    @Override
    public List<SemesterResponse> getSemestersOfCurrentLecturer() {
        return List.of();
    }
}
