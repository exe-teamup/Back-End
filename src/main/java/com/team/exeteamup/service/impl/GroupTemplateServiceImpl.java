package com.team.exeteamup.service.impl;

import com.team.exeteamup.dto.request.GroupTemplateRequest;
import com.team.exeteamup.dto.response.GroupTemplateResponse;
import com.team.exeteamup.entity.GroupTemplate;
import com.team.exeteamup.mapper.GroupTemplateMapper;
import com.team.exeteamup.repository.GroupTemplateRepository;
import com.team.exeteamup.service.inter.GroupTemplateService;
import jakarta.persistence.EntityNotFoundException;
import lombok.RequiredArgsConstructor;
import org.springframework.cache.annotation.CacheEvict;
import org.springframework.cache.annotation.Cacheable;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;

@Service
@Transactional(readOnly = true)
@RequiredArgsConstructor
public class GroupTemplateServiceImpl implements GroupTemplateService {

    private final GroupTemplateRepository groupTemplateRepository;
    private final GroupTemplateMapper groupTemplateMapper;


    @Override
    @Cacheable(value = "group_template", key = "#groupTemplateId")
    public GroupTemplate findById(long groupTemplateId) {
        return groupTemplateRepository.findById(groupTemplateId)
                .orElseThrow(() -> new EntityNotFoundException("Group Template not found with id " + groupTemplateId));
    }

    @Override
    public GroupTemplateResponse findResponseById(long groupTemplateId) {

        GroupTemplate groupTemplate = findById(groupTemplateId);

        return groupTemplateMapper.toResponse(groupTemplate);
    }


    @Override
    @Cacheable("group_templates")
    public List<GroupTemplateResponse> getAll() {
        return groupTemplateRepository.findAll().stream()
                .map(groupTemplateMapper::toResponse)
                .toList();
    }


    @Override
    @Transactional
    @CacheEvict(cacheNames = "group_template", allEntries = true)
    public GroupTemplateResponse saveGroupTemplate(GroupTemplateRequest groupTemplateRequest) {

        validateGroupTemplateUniqueness(groupTemplateRequest.getTemplate());

        GroupTemplate groupTemplate = groupTemplateMapper.toEntity(groupTemplateRequest);

        GroupTemplate savedGroupTemplate = groupTemplateRepository.save(groupTemplate);

        return groupTemplateMapper.toResponse(savedGroupTemplate);
    }


    @Override
    @Transactional
    @CacheEvict(cacheNames = "group_template", allEntries = true)
    public GroupTemplateResponse updateGroupTemplate(long groupTemplateId, GroupTemplateRequest groupTemplateRequest) {

        GroupTemplate groupTemplate = findById(groupTemplateId);

        groupTemplateMapper.updateEntityFromRequest(groupTemplate, groupTemplateRequest);

        GroupTemplate updatedGroupTemplate = groupTemplateRepository.save(groupTemplate);

        return groupTemplateMapper.toResponse(updatedGroupTemplate);
    }


    @Override
    @Transactional
    @CacheEvict(cacheNames = "group_template", allEntries = true)
    public GroupTemplateResponse deleteGroupTemplate(long groupTemplateId) {

        GroupTemplate groupTemplate = findById(groupTemplateId);

        groupTemplateRepository.delete(groupTemplate);

        return groupTemplateMapper.toResponse(groupTemplate);
    }


    public void validateGroupTemplateUniqueness(String template) {
        groupTemplateRepository.findByTemplate(template)
                .ifPresent(existingTemplate -> {
                    throw new IllegalArgumentException(
                            "Group Template with template '" +
                                    template +
                                    "' already exists.");
                });
    }
}
