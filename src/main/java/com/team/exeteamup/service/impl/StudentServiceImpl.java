package com.team.exeteamup.service.impl;

import com.team.exeteamup.entity.Course;
import com.team.exeteamup.exception.AppException;
import com.team.exeteamup.dto.response.StudentResponse;
import com.team.exeteamup.entity.Account;
import com.team.exeteamup.entity.Major;
import com.team.exeteamup.entity.Student;
import com.team.exeteamup.enums.AccountRole;
import com.team.exeteamup.enums.AccountStatus;
import com.team.exeteamup.enums.StudentStatus;
import com.team.exeteamup.mapper.StudentMapper;
import com.team.exeteamup.repository.AccountRepository;
import com.team.exeteamup.repository.CourseRepository;
import com.team.exeteamup.repository.MajorRepository;
import com.team.exeteamup.repository.StudentRepository;
import com.team.exeteamup.service.StudentService;
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
public class StudentServiceImpl implements StudentService {
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
    @Transactional
    public List<StudentResponse> importStudentsFromExcel(MultipartFile file) throws IOException {
        List<Student> studentsToSave = new ArrayList<>();

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
                String studentCode = currentRow.getCell(2).getStringCellValue().trim();
                String phone = currentRow.getCell(3).getStringCellValue().trim();
                String bio = currentRow.getCell(4) != null ? currentRow.getCell(4).getStringCellValue().trim() : null;
                String majorName = currentRow.getCell(5).getStringCellValue().trim();
                String courseCode = currentRow.getCell(6).getStringCellValue().trim();

                if (accountRepository.existsByEmail(email)) {
                    throw new RuntimeException("Email already exists: " + email);
                }
                if (studentRepository.existsByStudentCode(studentCode)) {
                    throw new RuntimeException("Student code already exists: " + studentCode);
                }

                if (studentsToSave.stream().anyMatch(s -> s.getStudentCode().equals(studentCode))) {
                    throw new RuntimeException("Duplicate student code in file: " + studentCode);
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

                Student student = Student.builder()
                        .account(account)
                        .major(major)
                        .studentCode(studentCode)
                        .fullName(fullName)
                        .phoneNumber(phone)
                        .bio(bio)
                        .course(course)
                        .createdAt(LocalDateTime.now())
                        .studentStatus(StudentStatus.ELIGIBLE)
                        .isLeader(false)
                        .build();

                studentsToSave.add(student);
            }
        }

        List<Student> savedStudents = studentRepository.saveAll(studentsToSave);

        List<StudentResponse> responses = savedStudents.stream()
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
                    student.setStudentStatus(StudentStatus.NOT_ELIGIBLE);
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
        Student student = studentRepository.findByStudentId(studentId)
                .orElseThrow(() -> new AppException("Sinh viên không tồn tại"));

        if (!student.getStudentStatus().equals(StudentStatus.ELIGIBLE)) {
            throw new AppException("Sinh viên không tồn tại");
        }

        student.setStudentStatus(StudentStatus.NOT_ELIGIBLE);
        studentRepository.save(student);
    }

    @Override
    public Student findById(long studentId) {
        return studentRepository.findById(studentId)
                .orElseThrow(() -> new EntityNotFoundException(
                        "Student not found: " + studentId)
                );
    }
}
