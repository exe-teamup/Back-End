package com.team.exeteamup.repository;

import com.team.exeteamup.entity.GroupTemplate;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.Optional;

@Repository
public interface GroupTemplateRepository extends JpaRepository<GroupTemplate, Long> {
    Optional<GroupTemplate> findByTemplate(String template);
}
