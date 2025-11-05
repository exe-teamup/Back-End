package com.team.exeteamup.service.impl;

import com.team.exeteamup.dto.request.SwapRequest;
import com.team.exeteamup.entity.*;
import com.team.exeteamup.enums.CourseStatus;
import com.team.exeteamup.exception.AppException;
import com.team.exeteamup.dto.response.UserResponse;
import com.team.exeteamup.enums.account.AccountRole;
import com.team.exeteamup.enums.account.AccountStatus;
import com.team.exeteamup.enums.UserStatus;
import com.team.exeteamup.mapper.StudentMapper;
import com.team.exeteamup.repository.AccountRepository;
import com.team.exeteamup.repository.CourseRepository;
import com.team.exeteamup.repository.MajorRepository;
import com.team.exeteamup.repository.UserRepository;
import com.team.exeteamup.service.inter.UserService;
import com.team.exeteamup.repository.*;
import com.team.exeteamup.service.inter.CourseService;
import com.team.exeteamup.utils.UserUtils;
import jakarta.persistence.EntityNotFoundException;
import jakarta.servlet.http.HttpServletRequest;
import lombok.RequiredArgsConstructor;
import org.apache.poi.ss.usermodel.Cell;
import org.apache.poi.ss.usermodel.Row;
import org.apache.poi.ss.usermodel.Sheet;
import org.apache.poi.ss.usermodel.Workbook;
import org.apache.poi.xssf.usermodel.XSSFWorkbook;
import org.springframework.cache.annotation.CacheEvict;
import org.springframework.cache.annotation.Cacheable;
import org.springframework.data.domain.Pageable;
import org.springframework.data.domain.Page;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.web.context.request.RequestContextHolder;
import org.springframework.web.context.request.ServletRequestAttributes;
import org.springframework.web.multipart.MultipartFile;

import java.io.IOException;
import java.io.InputStream;
import java.time.LocalDateTime;
import java.util.*;
import java.util.stream.Collectors;

@Service
@Transactional
@RequiredArgsConstructor
public class UserServiceImpl implements UserService {

    private final UserRepository userRepository;
    private final StudentMapper studentMapper;
    private final AccountRepository accountRepository;
    private final MajorRepository majorRepository;
    private final CourseRepository courseRepository;
    private final UserUtils userUtils;
    private final GroupRepository groupRepository;
    private final CourseChangeRepository courseChangeRepository;
    private final CourseService courseService;


    @Override
    @Cacheable(cacheNames = "users_list", key = "'allStudents'")
    public List<UserResponse> getAllStudents() {
        List<User> users = userRepository.findAll();
        return users.stream()
                .map(studentMapper::toResponse)
                .collect(Collectors.toList());
    }

    public Page<UserResponse> getAllStudents(Pageable pageable) {
        Page<User> studentPage = userRepository.findAll(pageable);
        return studentPage.map(studentMapper::toResponse);
    }

    @Override
    @Transactional
    @CacheEvict(cacheNames = "users", allEntries = true)
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
                if (userRepository.existsByUserCode(userCode)) continue;

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

        List<User> savedUsers = userRepository.saveAll(studentsToSave);

        List<UserResponse> responses = savedUsers.stream()
                .map(studentMapper::toResponse)
                .collect(Collectors.toList());

        return responses;
    }

    @Override
    @Transactional
    @CacheEvict(cacheNames = "users", allEntries = true)
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

                userRepository.findByAccount_Email(email).ifPresentOrElse(student -> {
                    student.setUserStatus(UserStatus.NOT_ELIGIBLE);
                    userRepository.save(student);
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
    @CacheEvict(cacheNames = "user", allEntries = true)
    public void deleteStudentById(long studentId) {
        User user = userRepository.findByUserId(studentId)
                .orElseThrow(() -> new AppException("Sinh viên không tồn tại"));

        if (!user.getUserStatus().equals(UserStatus.ELIGIBLE)) {
            throw new AppException("Sinh viên không tồn tại");
        }

        user.setUserStatus(UserStatus.NOT_ELIGIBLE);
        userRepository.save(user);
    }

    @Override
    @Cacheable(value = "user", key = "#studentId")
    public User findById(long studentId) {
        return userRepository.findById(studentId)
                .orElseThrow(() -> new EntityNotFoundException(
                        "Student not found: " + studentId)
                );
    }

    @Override
    @Cacheable("users_no_group")
    public List<UserResponse> getStudentWithoutGroup() {
        return studentMapper.toResponseList(userRepository.findByGroupIsNull());
    }

    @Override
    @Cacheable(value = "user", key = "#studentId")
    public UserResponse getStudentById(long studentId) {
        User user = userRepository.findByUserId(studentId)
                .orElseThrow(() -> new AppException("Không tìm thấy sinh viên"));
        return studentMapper.toResponse(user);
    }

    @Override
    public List<UserResponse> searchStudents(String keyword) {
        List<User> users = userRepository.searchStudents(keyword);
        if (users.isEmpty()) {
            throw new AppException("Không tìm thấy sinh viên");
        }
        return users.stream()
                .map(studentMapper::toResponse)
                .toList();
    }

    @Override
    @Transactional
    @CacheEvict(cacheNames = "user", allEntries = true)
    public UserResponse moveStudentCourses(Long newCourseId) {
        ServletRequestAttributes attr = (ServletRequestAttributes) RequestContextHolder.currentRequestAttributes();
        HttpServletRequest request = attr.getRequest();
        String authHeader = request.getHeader("Authorization");

        if (authHeader == null || !authHeader.startsWith("Bearer ")) {
            throw new AppException("Yêu cầu thiếu token xác thực.");
        }
        //String token = authHeader.substring(7);

        Account currentAccount = userUtils.getCurrentAccount();
        User student = userRepository.findByAccountId(currentAccount.getId())
                .orElseThrow(() -> new AppException("Không tìm thấy sinh viên"));

        Course newCourse = courseRepository.findByCourseId(newCourseId)
                .orElseThrow(() -> new AppException("Lớp học không tồn tại"));

        Course oldCourse = student.getCourse();
        if (oldCourse == null) {
            throw new AppException("Sinh viên chưa thuộc lớp nào");
        }

        if (oldCourse.getStatus() == CourseStatus.LOCKED) {
            throw new AppException("Lớp học hiện tại của bạn (" + oldCourse.getCourseCode() + ") đã bị khóa, không thể chuyển đi.");
        }
        if (newCourse.getStatus() == CourseStatus.LOCKED) {
            throw new AppException("Lớp học bạn muốn chuyển đến (" + newCourse.getCourseCode() + ") đã bị khóa, không thể chuyển đến.");
        }

        if (oldCourse.getCourseId() == newCourse.getCourseId()) {
            throw new AppException("Bạn đã ở lớp học này");
        }

        int currentStudentCount = userRepository.countByCourse_CourseId(newCourse.getCourseId());
        if (newCourse.getMaxStudents() != null && newCourse.getMaxStudents() > 0) {
            if (currentStudentCount > newCourse.getMaxStudents()) {
                throw new AppException("Lớp này đã đạt giới hạn thành viên");
            }
        }

        Group currentGroup = student.getGroup();
        if (currentGroup != null && currentGroup.getCourse().getCourseId() == oldCourse.getCourseId()) {
            if (student.getIsLeader()) {
                throw new AppException("Vui lòng chuyển quyền nhóm trưởng cho thành viên khác");
            }
            int memberCount = userRepository.countByGroup_GroupId(currentGroup.getGroupId());
            if (memberCount <= 3) {
                throw new AppException("Nhóm bạn dưới 3 thành viên. Vui lòng giải tán trước khi rời");
            }
            student.setGroup(null);
            student.setIsLeader(false);
            currentGroup.setMemberCount(memberCount - 1);
            groupRepository.save(currentGroup);
        }
        CourseChange log = CourseChange.builder()
                .user(student)
                .oldCourse(oldCourse)
                .newCourse(newCourse)
                .build();
        courseChangeRepository.save(log);

        student.setCourse(newCourse);
        User savedStudent = userRepository.save(student);

        return studentMapper.toResponse(savedStudent);
    }

    @Override
    @Cacheable(value = "users_by_course", key = "#courseId")
    public List<UserResponse> getStudentByCourseId(long courseId) {
        Course course = courseService.findById(courseId);

        return course.getUsers()
                .stream().map(studentMapper::toResponse)
                .toList();
    }

    @Override
    @CacheEvict(cacheNames = "users", allEntries = true)
    public Map<String, UserResponse> swapStudentCourse(SwapRequest request) {
        if (request.getStudentId1().equals(request.getStudentId2())) {
            throw new AppException("Không thể hoán đổi với chính mình");
        }

        User student1 = userRepository.findById(request.getStudentId1())
                .orElseThrow(() -> new AppException("Không tìm thấy sinh viên 1"));
        User student2 = userRepository.findById(request.getStudentId2())
                .orElseThrow(() -> new AppException("Không tìm thấy sinh viên 2"));

        Course course1 = student1.getCourse();
        Course course2 = student2.getCourse();
        Group group1 = student1.getGroup();
        Group group2 = student2.getGroup();

        if (course1.getStatus() == CourseStatus.LOCKED || course2.getStatus() == CourseStatus.LOCKED) {
            throw new AppException("Đã hết thời hạn chuyển lớp");
        }

        if (course1.getCourseId() == course2.getCourseId()) {
            throw new AppException("Hai sinh viên cùng lớp, không thể hoán đổi");
        }

        if(student1.getIsLeader()) {
            throw new AppException(student1.getFullName() + "là nhóm trưởng. Vui lòng chuyển quyền trước khi hoán đổi");
        }

        if(student2.getIsLeader()) {
            throw new AppException(student1.getFullName() + "là nhóm trưởng. Vui lòng chuyển quyền trước khi hoán đổi");
        }

        if (group1 != null) {
            student1.setGroup(null);
            group1.setMemberCount(group1.getMemberCount() - 1);
            groupRepository.save(group1);
        }

        if (group2 != null) {
            student2.setGroup(null);
            group2.setMemberCount(group2.getMemberCount() - 1);
            groupRepository.save(group2);
        }

        student1.setCourse(course2);
        student2.setCourse(course1);

        courseChangeRepository.save(CourseChange.builder()
                .user(student1)
                .oldCourse(course1)
                .newCourse(course2)
                .build());
        courseChangeRepository.save(CourseChange.builder()
                .user(student2)
                .oldCourse(course2)
                .newCourse(course1)
                .build());
        userRepository.save(student1);
        userRepository.save(student2);
        Map<String, UserResponse> response = new HashMap<>();
        response.put("student1", studentMapper.toResponse(student1));
        response.put("student2", studentMapper.toResponse(student2));
        return response;
    }
}