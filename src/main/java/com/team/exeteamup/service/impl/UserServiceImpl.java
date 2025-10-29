package com.team.exeteamup.service.impl;

import com.team.exeteamup.entity.Course;
import com.team.exeteamup.exception.AppException;
import com.team.exeteamup.dto.response.UserResponse;
import com.team.exeteamup.entity.Account;
import com.team.exeteamup.entity.Major;
import com.team.exeteamup.entity.User;
import com.team.exeteamup.enums.account.AccountRole;
import com.team.exeteamup.enums.account.AccountStatus;
import com.team.exeteamup.enums.UserStatus;
import com.team.exeteamup.mapper.StudentMapper;
import com.team.exeteamup.repository.AccountRepository;
import com.team.exeteamup.repository.CourseRepository;
import com.team.exeteamup.repository.MajorRepository;
import com.team.exeteamup.repository.StudentRepository;
import com.team.exeteamup.service.inter.UserService;
import jakarta.persistence.EntityNotFoundException;
import org.apache.poi.ss.usermodel.Cell;
import org.apache.poi.ss.usermodel.Row;
import org.apache.poi.ss.usermodel.Sheet;
import org.apache.poi.ss.usermodel.Workbook;
import org.apache.poi.xssf.usermodel.XSSFWorkbook;
import org.springframework.data.domain.Pageable;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.web.multipart.MultipartFile;

import java.io.IOException;
import java.io.InputStream;
import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.Iterator;
import java.util.List;
import java.util.stream.Collectors;

@Service
@Transactional
public class UserServiceImpl implements UserService {
    @Autowired
    private StudentRepository studentRepository;

    @Autowired
    private StudentMapper studentMapper;
    @Autowired
    private AccountRepository accountRepository;
    @Autowired
    private MajorRepository majorRepository;

    @Autowired
    private CourseRepository courseRepository;


    public UserServiceImpl(StudentRepository studentRepository) {
        this.studentRepository = studentRepository;
    }

    @Override
    public List<UserResponse> getAllStudents() {
        List<User> users = studentRepository.findAll();
        return users.stream()
                .map(studentMapper::toResponse)
                .collect(Collectors.toList());
    }

    public Page<UserResponse> getAllStudents(Pageable pageable) {
        Page<User> studentPage = studentRepository.findAll(pageable);
        return studentPage.map(studentMapper::toResponse);
    }

    @Override
    @Transactional
    public List<UserResponse> importStudentsFromExcel(MultipartFile file) throws IOException {
        List<User> studentsToSave = new ArrayList<>();

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

                String email = currentRow.getCell(0).getStringCellValue().trim();
                String fullName = currentRow.getCell(1).getStringCellValue().trim();
                String userCode = currentRow.getCell(2).getStringCellValue().trim();
                String phone = currentRow.getCell(3).getStringCellValue().trim();
                String bio = currentRow.getCell(4) != null ? currentRow.getCell(4).getStringCellValue().trim() : null;
                String majorName = currentRow.getCell(5).getStringCellValue().trim();
                String courseCode = currentRow.getCell(6).getStringCellValue().trim();

                if (accountRepository.existsByEmail(email)) continue;
                if (studentRepository.existsByUserCode(userCode)) continue;

                if (studentsToSave.stream().anyMatch(s -> s.getUserCode().equals(userCode))) {
                    throw new RuntimeException("Duplicate student code in file: " + userCode);
                }
                if (studentsToSave.stream().anyMatch(s -> s.getAccount().getEmail().equals(email))) {
                    throw new RuntimeException("Duplicate email in file: " + email);
                }

                Major major = majorRepository.findByMajorName(majorName)
                        .orElseThrow(() -> new RuntimeException("Major not found: " + majorName));

                Course course = courseRepository.findByCourseCode(courseCode)
                        .orElseThrow(() -> new AppException("Lớp học không tồn tại"));

                Account account = Account.builder()
                        .email(email)
                        .role(AccountRole.STUDENT)
                        .status(AccountStatus.ACTIVE)
                        .createdAt(LocalDateTime.now())
                        .build();

                accountRepository.save(account);

                User user = User.builder()
                        .account(account)
                        .major(major)
                        .userCode(userCode)
                        .fullName(fullName)
                        .phoneNumber(phone)
                        .bio(bio)
                        .course(course)
                        .createdAt(LocalDateTime.now())
                        .userStatus(UserStatus.ELIGIBLE)
                        .isLeader(false)
                        .build();

                studentsToSave.add(user);
            }
        }

        List<User> savedUsers = studentRepository.saveAll(studentsToSave);

        List<UserResponse> responses = savedUsers.stream()
                .map(studentMapper::toResponse)
                .collect(Collectors.toList());

        return responses;
    }


    @Override
    @Transactional
    public void importStudentsNotEligible(MultipartFile file) throws IOException {
        try (InputStream inputStream = file.getInputStream();
             Workbook workbook = new XSSFWorkbook(inputStream)) {

            Sheet sheet = workbook.getSheetAt(0);
            for (int i = 1; i <= sheet.getLastRowNum(); i++) {
                Row row = sheet.getRow(i);
                if (row == null) continue;

                Cell emailCell = row.getCell(0);
                if (emailCell == null) continue;

                String email = emailCell.getStringCellValue().trim();

                studentRepository.findByAccount_Email(email).ifPresentOrElse(student -> {
                    student.setUserStatus(UserStatus.NOT_ELIGIBLE);
                    studentRepository.save(student);
                    System.out.println("✅ Đổi trạng thái: " + email);
                }, () -> {
                    System.out.println("⚠️ Không tìm thấy sinh viên: " + email);
                });
            }

        } catch (IOException e) {
            throw new RuntimeException("Lỗi khi đọc file Excel", e);
        }
    }

    @Override
    public void deleteStudentById(long studentId) {
        User user = studentRepository.findByUserId(studentId)
                .orElseThrow(() -> new AppException("Sinh viên không tồn tại"));

        if (!user.getUserStatus().equals(UserStatus.ELIGIBLE)) {
            throw new AppException("Sinh viên không tồn tại");
        }

        user.setUserStatus(UserStatus.NOT_ELIGIBLE);
        studentRepository.save(user);
    }

    @Override
    public User findById(long studentId) {
        return studentRepository.findById(studentId)
                .orElseThrow(() -> new EntityNotFoundException(
                        "Student not found: " + studentId)
                );
    }

    @Override
    public List<UserResponse> getStudentWithoutGroup() {
        return studentMapper.toResponseList(studentRepository.findByGroupIsNull());
    }

    @Override
    public UserResponse getStudentById(long studentId) {
        User user = studentRepository.findByUserId(studentId)
                .orElseThrow(() -> new AppException("Không tìm thấy sinh viên"));
        return studentMapper.toResponse(user);
    }

    @Override
    public List<UserResponse> searchStudents(String keyword) {
        List<User> users = studentRepository.searchStudents(keyword);
        if (users.isEmpty()) {
            throw new AppException("Không tìm thấy sinh viên");
        }
        return users.stream()
                .map(studentMapper::toResponse)
                .toList();
    }
}
