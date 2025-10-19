package com.team.exeteamup.mapper;

import com.team.exeteamup.dto.request.GroupTemplateRequest;
import com.team.exeteamup.dto.response.GroupTemplateResponse;
import com.team.exeteamup.entity.GroupTemplate;
import org.springframework.stereotype.Component;

@Component
public class GroupTemplateMapper {

    public GroupTemplateResponse toResponse(GroupTemplate groupTemplate) {
        return GroupTemplateResponse.builder()
                .id(groupTemplate.getId())
                .minMember(groupTemplate.getMinMember())
                .maxMember(groupTemplate.getMaxMember())
                .minMajor(groupTemplate.getMinMajor())
                .template(groupTemplate.getTemplate())
                .build();
    }


    public GroupTemplate toEntity(GroupTemplateRequest groupTemplateRequest) {
        return GroupTemplate.builder()
                .minMember(groupTemplateRequest.getMinMember())
                .maxMember(groupTemplateRequest.getMaxMember())
                .minMajor(groupTemplateRequest.getMinMajor())
                .template(groupTemplateRequest.getTemplate())
                .build();
    }


    public void updateEntityFromRequest(GroupTemplate groupTemplate,
                                  GroupTemplateRequest groupTemplateRequest) {
        groupTemplate.setMinMember(groupTemplateRequest.getMinMember());
        groupTemplate.setMaxMember(groupTemplateRequest.getMaxMember());
        groupTemplate.setMinMajor(groupTemplateRequest.getMinMajor());
        groupTemplate.setTemplate(groupTemplateRequest.getTemplate());
    }
}
