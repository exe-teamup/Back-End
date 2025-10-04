package com.team.exeteamup.controller;

import com.team.exeteamup.dto.request.MajorRequest;
import com.team.exeteamup.dto.response.MajorResponse;
import com.team.exeteamup.service.MajorService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping("api/majors")
public class MajorController {
    @Autowired
    private MajorService majorService;

    @PostMapping("")
    public ResponseEntity<MajorResponse> createMajor(@RequestBody MajorRequest majorRequest) {
        return ResponseEntity.ok(majorService.createMajor(majorRequest));
    }
}
