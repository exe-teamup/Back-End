package com.team.exeteamup.controller;

import com.team.exeteamup.dto.request.MajorRequest;
import com.team.exeteamup.dto.response.MajorResponse;
import com.team.exeteamup.service.inter.MajorService;
import io.swagger.v3.oas.annotations.security.SecurityRequirement;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.multipart.MultipartFile;

import java.util.List;
    
@RestController
@RequestMapping("api/majors")
//@SecurityRequirement(name = "bearerAuth")
public class MajorController {
    @Autowired
    private MajorService majorService;

    @PostMapping("")
    public ResponseEntity<MajorResponse> createMajor(@RequestBody MajorRequest majorRequest) {
        return ResponseEntity.ok(majorService.createMajor(majorRequest));
    }

    @PostMapping(value = "import", consumes = MediaType.MULTIPART_FORM_DATA_VALUE)
    public ResponseEntity <List<MajorResponse>> importMajor(@RequestParam("file") MultipartFile file) {
        List<MajorResponse> response = majorService.importMajors(file);
        return ResponseEntity.ok(response);
    }

    @GetMapping("")
    public ResponseEntity<List<MajorResponse>> getAllMajors() {
        List<MajorResponse> response = majorService.getAllMajors();
        return ResponseEntity.ok(response);
    }

    @GetMapping("level/{level}")
    public ResponseEntity<List<MajorResponse>> getMajorsByLevel(@PathVariable Long level) {
        List<MajorResponse> response = majorService.getMajorsByLevel(level);
        return ResponseEntity.ok(response);
    }

    @GetMapping("parent/{id}")
    public ResponseEntity <List<MajorResponse>> getMajorsByParentMajor(@PathVariable Long id) {
        List<MajorResponse> response = majorService.getMajorsByParentMajorId(id);
        return ResponseEntity.ok(response);
    }

    @PutMapping("/{id}")
    public ResponseEntity<MajorResponse> updateMajor(@PathVariable Long id,
                                                     @RequestBody MajorRequest majorRequest) {
        MajorResponse updated = majorService.updateMajor(id, majorRequest);
        return ResponseEntity.ok(updated);
    }

    @DeleteMapping("{id}")
    public ResponseEntity<String> deleteMajor(@PathVariable Long id) {
        majorService.deleteMajor(id);
        return ResponseEntity.ok("Xóa chuyên ngành thành công");
    }
}
