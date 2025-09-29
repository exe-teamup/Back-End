package com.team.exeteamup.service;

import com.team.exeteamup.dto.request.GroupTemplateRequest;
import com.team.exeteamup.entity.GroupTemplate;

import java.util.List;

public interface GroupTemplateService {
    public List<GroupTemplate> findAll();
    public GroupTemplate findById(long id);
    public GroupTemplate save(GroupTemplateRequest groupTemplateRequest);
    public void deleteById(long id);
    public GroupTemplate update(GroupTemplate groupTemplate);
}
