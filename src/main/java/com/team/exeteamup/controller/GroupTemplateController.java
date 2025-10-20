package com.team.exeteamup.controller;

import com.team.exeteamup.dto.request.GroupTemplateRequest;
import com.team.exeteamup.service.GroupTemplateService;
import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.validation.BindingResult;
import org.springframework.validation.FieldError;
import org.springframework.web.bind.annotation.*;

import java.util.LinkedHashMap;
import java.util.Map;

@RestController
@RequestMapping("/api/group-templates")
@RequiredArgsConstructor
public class GroupTemplateController {

    private final GroupTemplateService groupTemplateService;

    @PostMapping("")
    public ResponseEntity<?> createGroupTemplate(@Valid @RequestBody GroupTemplateRequest request,
                                                 BindingResult bindingResult) {
        if(bindingResult.hasErrors()) {
            Map<String, String> errors = new LinkedHashMap<>();
            errors.put("api", "group-template - post - createGroupTemplate");

            for (FieldError error : bindingResult.getFieldErrors()) {
                errors.put(error.getField(),error.getDefaultMessage());
            }
            return ResponseEntity.badRequest().body(errors);

        }
        return ResponseEntity.ok(groupTemplateService.saveGroupTemplate(request));
    }

//    @GetMapping("")
//    public ResponseEntity<?> getAllGroupTemplates() {
//        return ResponseEntity.ok(groupTemplateService.f());
//    }
}
