package com.team.exeteamup.service.impl;

import com.team.exeteamup.exception.AppException;
import com.team.exeteamup.dto.request.MajorRequest;
import com.team.exeteamup.dto.response.MajorResponse;
import com.team.exeteamup.entity.Major;
import com.team.exeteamup.mapper.MajorMapper;
import com.team.exeteamup.repository.MajorRepository;
import com.team.exeteamup.service.inter.MajorService;
import lombok.RequiredArgsConstructor;
import org.apache.poi.ss.usermodel.Row;
import org.apache.poi.ss.usermodel.Sheet;
import org.apache.poi.ss.usermodel.Workbook;
import org.apache.poi.ss.usermodel.WorkbookFactory;
import org.springframework.cache.annotation.CacheEvict;
import org.springframework.cache.annotation.Cacheable;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.web.multipart.MultipartFile;

import java.io.InputStream;
import java.util.ArrayList;
import java.util.List;
import java.util.stream.Collectors;

@Service
@RequiredArgsConstructor
public class MajorServiceImpl implements MajorService {

    private final MajorRepository majorRepository;
    private final MajorMapper majorMapper;

    @Override
    @CacheEvict(cacheNames = "major", allEntries = true)
    public MajorResponse createMajor(MajorRequest majorRequest) {
        Major major = new Major();
        major.setMajorName(majorRequest.getMajorName());
        major.setMajorCode(majorRequest.getMajorCode());
        major.setLevel(majorRequest.getLevel());
        major.setMajorStatus(true);

        if (majorRequest.getLevel() == 1) {
            major.setParentMajor(null);
        }

        else if (majorRequest.getLevel() == 2) {
            if (majorRequest.getParentMajorId() == null) {
                throw new RuntimeException("Cần id chuyên ngành cha cho level 2");
            }

            Major parent = majorRepository.findById(majorRequest.getParentMajorId())
                    .orElseThrow(() -> new RuntimeException("Không tìm thấy chuyên ngành cha"));

            if (parent.getLevel() != 1) {
                throw new RuntimeException("Chuyên ngành cha phải là level 1");
            }
            major.setParentMajor(parent);
        }
        Major saved = majorRepository.save(major);
        return majorMapper.toResponse(saved);
    }

    @Override
    @CacheEvict(cacheNames = "majors", allEntries = true)
    public List<MajorResponse> importMajors(MultipartFile file) {
        List<MajorResponse> result = new ArrayList<>();

        try (InputStream is = file.getInputStream()) {
            Workbook workbook = WorkbookFactory.create(is);
            Sheet sheet = workbook.getSheetAt(0);

            for (int i = 1; i <= sheet.getLastRowNum(); i++) { // bỏ dòng header
                Row row = sheet.getRow(i);
                if (row == null) continue;

                String majorName = row.getCell(0).getStringCellValue();
                String majorCode = row.getCell(1).getStringCellValue();
                Long level = (long) row.getCell(2).getNumericCellValue();

                Long parentMajorId = null;
                if (level == 2 && row.getCell(3) != null) {
                    parentMajorId = (long) row.getCell(3).getNumericCellValue();
                }
                if (majorRepository.existsByMajorCode(majorCode)) continue;
                if (majorRepository.existsByMajorName(majorName)) continue;

                Major major = new Major();
                major.setMajorName(majorName);
                major.setMajorCode(majorCode);
                major.setMajorStatus(true);
                major.setLevel(level);

                if (level == 2 && parentMajorId != null) {
                    Major parent = majorRepository.findById(parentMajorId)
                            .orElseThrow(() -> new RuntimeException("Không tìm thấy chuyên ngành cha"));
                    major.setParentMajor(parent);
                }

                Major saved = majorRepository.save(major);
                result.add(majorMapper.toResponse(saved));
            }
            workbook.close();
        } catch (Exception e) {
            throw new RuntimeException("Failed to import majors: " + e.getMessage(), e);
        }

        return result;
    }

    @Override
    @Cacheable("majors")
    public List<MajorResponse> getAllMajors() {
        List<Major> majors = majorRepository.findByMajorStatusIsTrue();
        return majors.stream()
                .map(majorMapper::toResponse)
                .collect(Collectors.toList());
    }

    @Override
    @Transactional(readOnly = true)
    @Cacheable(value = "majors_by_level", key = "#level")
    public List<MajorResponse> getMajorsByLevel(Long level) {
        List<Major> majors = majorRepository.findByLevelAndMajorStatusIsTrue(level);
        return majorMapper.toResponseList(majors);
    }

    @Override
    @Transactional(readOnly = true)
    @Cacheable(value = "majors_by_parent", key = "#parentMajorId")
    public List<MajorResponse> getMajorsByParentMajorId(Long parentMajorId) {
        List<Major> majors = majorRepository.findByParentMajor_MajorIdAndMajorStatusIsTrue(parentMajorId);
        return majorMapper.toResponseList(majors);
    }

    @Override
    @Transactional
    @CacheEvict(cacheNames = "major", allEntries = true)
    public MajorResponse updateMajor(Long id, MajorRequest majorRequest) {
        Major major = majorRepository.findById(id)
                .orElseThrow(() -> new AppException("Chuyên ngành không tồn tại"));

        Major parentMajor = null;
        if (majorRequest.getLevel() == 1 && majorRequest.getParentMajorId() != null) {
            throw new AppException("Chuyên ngành lớn phải rỗng");
        }

        if (majorRequest.getLevel() == 2 && majorRequest.getParentMajorId() == null) {
            throw new AppException("Chuyên ngành lớn không được rỗng");
        }

        if (majorRequest.getParentMajorId() != null) {
            parentMajor = majorRepository.findById(majorRequest.getParentMajorId())
                    .orElseThrow(() -> new RuntimeException("Chuyên ngành lớn không tồn tại"));
        }

        major.setMajorName(majorRequest.getMajorName());
        major.setMajorCode(majorRequest.getMajorCode());
        major.setLevel(majorRequest.getLevel());
        major.setMajorStatus(majorRequest.getMajorStatus());
        major.setParentMajor(parentMajor);

        Major saved = majorRepository.save(major);
        return majorMapper.toResponse(saved);
    }

    @Override
    @CacheEvict(cacheNames = "major", allEntries = true)
    public void deleteMajor(Long majorId) {
        Major major = findById(majorId);
        majorRepository.delete(major);
    }

    @Override
    @Cacheable(value = "major", key = "#majorId")
    public Major findById(Long majorId) {
        return majorRepository.findById(majorId)
                .orElseThrow(() -> new AppException("Chuyên ngành không tồn tại"));
    }

    @Override
    public List<Major> findAllByIds(List<Long> majorIds) {
        return majorIds
                .stream()
                .map(id -> findById(id))
                .collect(Collectors.toList());
    }
}
