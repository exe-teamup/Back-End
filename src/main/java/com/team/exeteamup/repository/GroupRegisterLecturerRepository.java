package com.team.exeteamup.repository;

import com.team.exeteamup.entity.GroupRegisterLecturer;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.List;

public interface GroupRegisterLecturerRepository extends JpaRepository<GroupRegisterLecturer, Long> {
    boolean existsByGroup_GroupIdAndLecturer_LecturerId(Long groupId, Long lecturerId);
    List<GroupRegisterLecturer> findByGroup_GroupId(Long groupId);
}
