package com.team.exeteamup.repository;

import com.team.exeteamup.entity.GroupLecturer;
import com.team.exeteamup.entity.embedded.GroupLecturerId;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.Optional;

@Repository
public interface GroupLecturerRepository extends JpaRepository<GroupLecturer, GroupLecturerId> {
    Optional<GroupLecturer> findByGroup_GroupIdAndIsMainTrue(Long groupId);
    void deleteAllByGroup_GroupId(Long groupId);
}
