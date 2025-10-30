package com.team.exeteamup.controller;

import com.team.exeteamup.dto.request.GroupTemplateRequest;
import com.team.exeteamup.dto.response.GroupTemplateResponse;
import com.team.exeteamup.service.inter.GroupTemplateService;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/api/group-templates")
@RequiredArgsConstructor
public class GroupTemplateController {

    private final GroupTemplateService groupTemplateService;


    @GetMapping
    public ResponseEntity<List<GroupTemplateResponse>> getAll() {
        List<GroupTemplateResponse> templates = groupTemplateService.getAll();
        return ResponseEntity.ok(templates);
    }


    @GetMapping("/{id}")
    public ResponseEntity<GroupTemplateResponse> getById(@PathVariable("id") long id) {
        GroupTemplateResponse response = groupTemplateService.findResponseById(id);
        return ResponseEntity.ok(response);
    }


    @PostMapping
    public ResponseEntity<GroupTemplateResponse> create(@RequestBody GroupTemplateRequest request) {
        GroupTemplateResponse created = groupTemplateService.saveGroupTemplate(request);
        return ResponseEntity.status(HttpStatus.CREATED).body(created);
    }


    @PutMapping("/{id}")
    public ResponseEntity<GroupTemplateResponse> update(@PathVariable("id") long id,
                                                        @RequestBody GroupTemplateRequest request) {
        GroupTemplateResponse updated = groupTemplateService.updateGroupTemplate(id, request);
        return ResponseEntity.ok(updated);
    }


    @DeleteMapping("/{id}")
    public ResponseEntity<GroupTemplateResponse> delete(@PathVariable("id") long id) {
        GroupTemplateResponse deleted = groupTemplateService.deleteGroupTemplate(id);
        return ResponseEntity.ok(deleted);
    }
}
