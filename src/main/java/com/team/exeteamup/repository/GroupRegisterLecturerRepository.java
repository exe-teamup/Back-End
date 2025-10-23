package com.team.exeteamup.repository;

import com.team.exeteamup.dto.response.group.GroupRegisterLecturerResponse;
import com.team.exeteamup.entity.Group;
import com.team.exeteamup.entity.GroupRegisterLecturer;
import com.team.exeteamup.enums.RegisterStatus;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;

import java.util.List;

public interface GroupRegisterLecturerRepository extends JpaRepository<GroupRegisterLecturer, Long> {
    boolean existsByGroup_GroupIdAndLecturer_LecturerId(Long groupId, Long lecturerId);
    List<GroupRegisterLecturer> findByGroup_GroupId(Long groupId);
    void deleteAllByGroup(Group group);
    @Query("SELECT g FROM GroupRegisterLecturer g " +
            "WHERE g.lecturer.lecturerId = :lecturerId " +
            "AND g.registerStatus = :status")
    List<GroupRegisterLecturer> findPendingGroupsByLecturerId(Long lecturerId, RegisterStatus status);
}
