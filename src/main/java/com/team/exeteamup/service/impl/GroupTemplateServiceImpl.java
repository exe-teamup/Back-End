package com.team.exeteamup.service.impl;

import com.team.exeteamup.dto.request.GroupTemplateRequest;
import com.team.exeteamup.entity.GroupTemplate;
import com.team.exeteamup.repository.GroupTemplateRepository;
import com.team.exeteamup.service.GroupTemplateService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class GroupTemplateServiceImpl implements GroupTemplateService {

    @Autowired
    private GroupTemplateRepository groupTemplateRepository;

    @Override
    public List<GroupTemplate> findAll() {
        return groupTemplateRepository.findAll();
    }

    @Override
    public GroupTemplate findById(long id) {
        return groupTemplateRepository.findById(id).orElse(null);
    }

    @Override
    public GroupTemplate save(GroupTemplateRequest groupTemplateRequest) {
        return groupTemplateRepository.save(GroupTemplate.builder()
                                            .template(groupTemplateRequest.getTemplate())
                                            .max_member(groupTemplateRequest.getMax_member())
                                            .min_member(groupTemplateRequest.getMin_member())
                                            .min_major(groupTemplateRequest.getMin_major())
                                            .build());
    }

    @Override
    public void deleteById(long id) {
        if(groupTemplateRepository.existsById(id)) {
            groupTemplateRepository.deleteById(id);
        } else {
            throw new RuntimeException("GroupTemplate with id " + id + " not found");
        }
    }

    @Override
    public GroupTemplate update(GroupTemplate groupTemplate) {
        if(groupTemplateRepository.existsById(groupTemplate.getTemplateId())) {
            return groupTemplateRepository.save(groupTemplate);
        } else {
            throw new RuntimeException("GroupTemplate with id " + groupTemplate.getTemplateId() + " not found");
        }
    }
}
