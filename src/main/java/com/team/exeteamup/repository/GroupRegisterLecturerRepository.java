package com.team.exeteamup.repository;

import com.team.exeteamup.entity.Group;
import com.team.exeteamup.entity.GroupRegisterLecturer;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;

import java.util.List;

public interface GroupRegisterLecturerRepository extends JpaRepository<GroupRegisterLecturer, Long> {
    boolean existsByGroup_GroupIdAndLecturer_LecturerId(Long groupId, Long lecturerId);
    List<GroupRegisterLecturer> findByGroup_GroupId(Long groupId);
    void deleteAllByGroup(Group group);
    @Query("""
        SELECT DISTINCT r.group
        FROM GroupRegisterLecturer r
        WHERE r.lecturer.lecturerId = :lecturerId
          AND r.registerStatus = 'PENDING'
    """)
    List<Group> findPendingGroupsByLecturer(@Param("lecturerId") Long lecturerId);
}
