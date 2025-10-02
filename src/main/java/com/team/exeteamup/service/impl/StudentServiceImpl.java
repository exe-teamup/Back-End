package com.team.exeteamup.service.impl;

import com.team.exeteamup.Exception.AppException;
import com.team.exeteamup.dto.response.StudentResponse;
import com.team.exeteamup.entity.Account;
import com.team.exeteamup.entity.Student;
import com.team.exeteamup.enums.AccountRole;
import com.team.exeteamup.enums.AccountStatus;
import com.team.exeteamup.enums.StudentStatus;
import com.team.exeteamup.mapper.StudentMapper;
import com.team.exeteamup.repository.AccountRepository;
import com.team.exeteamup.repository.StudentRepository;
import com.team.exeteamup.service.StudentService;
import org.apache.poi.ss.usermodel.Row;
import org.apache.poi.ss.usermodel.Sheet;
import org.apache.poi.ss.usermodel.Workbook;
import org.apache.poi.xssf.usermodel.XSSFWorkbook;
import org.springframework.data.domain.Pageable;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.stereotype.Service;
import org.springframework.web.multipart.MultipartFile;

import java.io.IOException;
import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.Date;
import java.util.Iterator;
import java.util.List;
import java.util.stream.Collectors;

@Service
public class StudentServiceImpl implements StudentService {
    @Autowired
    private StudentRepository studentRepository;

    @Autowired
    private StudentMapper studentMapper;
    @Autowired
    private AccountRepository accountRepository;

    public StudentServiceImpl(StudentRepository studentRepository) {
        this.studentRepository = studentRepository;
    }

    @Override
    public List<StudentResponse> getAllStudents() {
        List<Student> students = studentRepository.findAll();
        return students.stream()
                .map(studentMapper::toResponse)
                .collect(Collectors.toList());
    }

    public Page<StudentResponse> getAllStudents(Pageable pageable) {
        Page<Student> studentPage = studentRepository.findAll(pageable);
        return studentPage.map(studentMapper::toResponse);
    }

    @Override
    public List<Student> importStudentsFromExcel(MultipartFile file) throws IOException {
        List<Student> students = new ArrayList<>();

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
                String studentCode = currentRow.getCell(2).getStringCellValue();
                String phone = currentRow.getCell(3).getStringCellValue();
                String bio = currentRow.getCell(4) != null ? currentRow.getCell(4).getStringCellValue() : null;

                if (accountRepository.existsByEmail(email)) {
                    throw new RuntimeException("Email already exists: " + email); }

                if (studentRepository.existsByStudentCode(studentCode)) {
                    throw new RuntimeException("Student code already exists: " + studentCode);
                }

                if (students.stream().anyMatch(s -> s.getStudentCode().equals(studentCode))) {
                    throw new RuntimeException("Duplicate student code in file: " + studentCode);
                }

                if (students.stream().anyMatch(s -> s.getAccount().getEmail().equals(email))) {
                    throw new RuntimeException("Duplicate email in file: " + email);
                }

                Account account = Account.builder()
                        .email(email)
                        .role(AccountRole.STUDENT)
                        .createdAt(LocalDateTime.now())
                        .status(AccountStatus.ACTIVE)
                        .build();
                accountRepository.save(account);

                Student student = Student.builder()
                        .account(account)
                        .studentCode(studentCode)
                        .fullName(fullName)
                        .phoneNumber(phone)
                        .bio(bio)
                        .createdAt(new Date())
                        .studentStatus(StudentStatus.ELIGIBLE)
                        .isLeader(false)
                        .build();
                students.add(student);
            }
        }
        return studentRepository.saveAll(students);
    }

    @Override
    public void deleteStudentById(long studentId) {
        Student student = studentRepository.findByStudentId(studentId)
                .orElseThrow(() -> new AppException("Sinh viên không tồn tại"));

        if (!student.getStudentStatus().equals(StudentStatus.ELIGIBLE)) {
            throw new AppException("Sinh viên không tồn tại");
        }

        student.setStudentStatus(StudentStatus.NOT_ELIGIBLE);
        studentRepository.save(student);
    }
}
