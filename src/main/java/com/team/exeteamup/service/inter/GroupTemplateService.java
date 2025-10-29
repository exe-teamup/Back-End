package com.team.exeteamup.service.inter;

import com.team.exeteamup.dto.request.GroupTemplateRequest;
import com.team.exeteamup.dto.response.GroupTemplateResponse;
import com.team.exeteamup.entity.GroupTemplate;

import java.util.List;

public interface GroupTemplateService {
    GroupTemplate findById(long groupTemplateId);
    GroupTemplateResponse findResponseById(long groupTemplateId);
    List<GroupTemplateResponse> getAll();
    GroupTemplateResponse saveGroupTemplate(GroupTemplateRequest groupTemplateRequest);
    GroupTemplateResponse updateGroupTemplate(long groupTemplateId, GroupTemplateRequest groupTemplateRequest);
    GroupTemplateResponse deleteGroupTemplate(long groupTemplateId);
}
