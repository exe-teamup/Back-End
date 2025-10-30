package com.team.exeteamup.service.impl;

import com.team.exeteamup.exception.AppException;
import com.team.exeteamup.dto.request.LecturerRequest;
import com.team.exeteamup.dto.response.lecturer.LecturerResponse;
import com.team.exeteamup.entity.Account;
import com.team.exeteamup.entity.Lecturer;
import com.team.exeteamup.enums.account.AccountRole;
import com.team.exeteamup.enums.account.AccountStatus;
import com.team.exeteamup.enums.LecturerStatus;
import com.team.exeteamup.mapper.lecturer.LecturerMapper;
import com.team.exeteamup.repository.AccountRepository;
import com.team.exeteamup.repository.LecturerRepository;
import com.team.exeteamup.service.LecturerService;
import com.team.exeteamup.utils.UserUtils;
import jakarta.persistence.EntityNotFoundException;
import lombok.RequiredArgsConstructor;
import org.apache.poi.ss.usermodel.Row;
import org.apache.poi.ss.usermodel.Sheet;
import org.apache.poi.ss.usermodel.Workbook;
import org.apache.poi.xssf.usermodel.XSSFWorkbook;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.web.multipart.MultipartFile;

import java.io.IOException;
import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.Iterator;
import java.util.List;

@Service
@Transactional(readOnly = true)
@RequiredArgsConstructor
public class LecturerServiceImpl implements LecturerService {

    private final LecturerRepository lecturerRepository;
    private final AccountRepository accountRepository;
    private final LecturerMapper lecturerMapper;
    private final UserUtils userUtils;

    @Override
    @Transactional
    public List<Lecturer> importStudentsFromExcel(MultipartFile file) throws IOException {
        List<Lecturer> lecturers = new ArrayList<>();

        try (Workbook workbook = new XSSFWorkbook(file.getInputStream())) {
            Sheet sheet = workbook.getSheetAt(0);
            Iterator<Row> rows = sheet.iterator();
            int rowNumber = 0;

            while (rows.hasNext()) {
                Row currentRow = rows.next();

                if (rowNumber == 0) {
                    rowNumber++;
                    continue;
                }

                String email = currentRow.getCell(0).getStringCellValue();
                String fullName = currentRow.getCell(1).getStringCellValue();

                Account account = Account.builder()
                        .email(email)
                        .role(AccountRole.LECTURER)
                        .createdAt(LocalDateTime.now())
                        .status(AccountStatus.ACTIVE)
                        .build();
                accountRepository.save(account);

                Lecturer lecturer = Lecturer.builder()
                        .account(account)
                        .fullName(fullName)
                        .lecturerStatus(LecturerStatus.ACTIVE)
                        .build();
                lecturers.add(lecturer);
            }
        }
        return lecturerRepository.saveAll(lecturers);
    }

    @Override
    @Transactional
    public LecturerResponse updateLecturer(Long lecturerId, LecturerRequest request) {
        Lecturer lecturer = lecturerRepository.findById(lecturerId)
                .orElseThrow(() -> new AppException("Không tìm thấy giảng viên"));

        lecturer.setLecturerStatus(request.getStatus());

        if (request.getStatus() == LecturerStatus.INACTIVE) {
            lecturer.getAccount().setStatus(AccountStatus.INACTIVE);
        } else {
            lecturer.getAccount().setStatus(AccountStatus.ACTIVE);
        }

        accountRepository.save(lecturer.getAccount());
        Lecturer updated = lecturerRepository.save(lecturer);

        return LecturerResponse.builder()
                .lecturerId(updated.getLecturerId())
                .lecturerName(updated.getFullName())
                .lecturerStatus(updated.getLecturerStatus().name())
                .accountId(updated.getAccount().getAccountId())
                .accountStatus(updated.getAccount().getStatus().name())
                .build();
    }

    @Override
    public List<LecturerResponse> getAllLecturers() {
        List<Lecturer> lecturers = lecturerRepository.findAll();

        return lecturers.stream()
                .map(lecturerMapper::toResponse)
                .toList();
    }

    @Override
    public LecturerResponse getLecturer(Long lecturerId) {
        Lecturer lecturer = lecturerRepository.findById(lecturerId)
                .orElseThrow(() -> new AppException("Không tìm thấy giảng viên"));

        return lecturerMapper.toResponse(lecturer);
    }

    @Override
    @Transactional
    public LecturerResponse deleteLecturer(Long lecturerId) {

        Lecturer lecturer = findById(lecturerId);
        Account account = lecturer.getAccount();

        lecturer.setLecturerStatus(LecturerStatus.INACTIVE);
        account.setStatus(AccountStatus.INACTIVE);

        accountRepository.save(account);
        Lecturer savedLecturer = lecturerRepository.save(lecturer);

        return lecturerMapper.toResponse(savedLecturer);
    }

    @Override
    public Lecturer findById(Long lecturerId) {
        return lecturerRepository.findById(lecturerId)
                .orElseThrow(() ->
                        new EntityNotFoundException(
                                "Lecturer not found with id: " + lecturerId)
                );
        }

    @Override
    public LecturerResponse getCurrentLecturer() {
        Account currentAccount = userUtils.getCurrentAccount();
        Lecturer lecturer = lecturerRepository.findByAccount_AccountId(currentAccount.getAccountId());
        return lecturerMapper.toResponse(lecturer);
    }
}
