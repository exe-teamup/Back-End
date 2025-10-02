package com.team.exeteamup.service.impl;

import com.team.exeteamup.entity.Account;
import com.team.exeteamup.entity.Lecturer;
import com.team.exeteamup.entity.Student;
import com.team.exeteamup.enums.AccountRole;
import com.team.exeteamup.enums.AccountStatus;
import com.team.exeteamup.enums.LecturerStatus;
import com.team.exeteamup.repository.AccountRepository;
import com.team.exeteamup.repository.LecturerRepository;
import com.team.exeteamup.service.LecturerService;
import lombok.RequiredArgsConstructor;
import org.apache.poi.ss.usermodel.Row;
import org.apache.poi.ss.usermodel.Sheet;
import org.apache.poi.ss.usermodel.Workbook;
import org.apache.poi.xssf.usermodel.XSSFWorkbook;
import org.springframework.stereotype.Service;
import org.springframework.web.multipart.MultipartFile;

import java.io.IOException;
import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.Date;
import java.util.Iterator;
import java.util.List;

@Service
@RequiredArgsConstructor
public class LecturerServiceImpl implements LecturerService {
    private final LecturerRepository lecturerRepository;
    private final AccountRepository accountRepository;

    @Override
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
}
