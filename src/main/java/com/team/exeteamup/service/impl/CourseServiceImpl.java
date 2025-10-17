package com.team.exeteamup.service.impl;

import com.team.exeteamup.dto.request.CourseRequest;
import com.team.exeteamup.dto.request.CourseUpdateRequest;
import com.team.exeteamup.dto.request.LecturerSelectionRequest;
import com.team.exeteamup.dto.response.CourseResponse;
import com.team.exeteamup.dto.response.GroupResponse;
import com.team.exeteamup.entity.Course;
import com.team.exeteamup.entity.Lecturer;
import com.team.exeteamup.entity.Semester;
import com.team.exeteamup.exception.AppException;
import com.team.exeteamup.mapper.CourseMapper;
import com.team.exeteamup.mapper.GroupMapper;
import com.team.exeteamup.repository.CourseRepository;
import com.team.exeteamup.repository.GroupRepository;
import com.team.exeteamup.repository.LecturerRepository;
import com.team.exeteamup.repository.SemesterRepository;
import com.team.exeteamup.service.CourseService;
import jakarta.transaction.Transactional;
import lombok.RequiredArgsConstructor;
import org.apache.poi.ss.usermodel.Row;
import org.apache.poi.ss.usermodel.Sheet;
import org.apache.poi.ss.usermodel.Workbook;
import org.apache.poi.xssf.usermodel.XSSFWorkbook;
import org.springframework.stereotype.Service;
import org.springframework.web.multipart.MultipartFile;

import java.io.IOException;
import java.util.ArrayList;
import java.util.List;

@Service
@Transactional
@RequiredArgsConstructor
public class CourseServiceImpl implements CourseService {

    private final CourseRepository courseRepository;
    private final CourseMapper courseMapper;
    private final SemesterRepository semesterRepository;
    private final LecturerRepository lecturerRepository;
    private final GroupRepository groupRepository;
    private final GroupMapper groupMapper;

    @Override
    public CourseResponse createCourse(CourseRequest courseRequest) {
        Course course  = courseMapper.toEntity(courseRequest);
        courseRepository.save(course);
        return courseMapper.toResponse(course);
    }

    @Override
    public List<CourseResponse> getAllCourses() {
        List<Course> courses = courseRepository.findAll();
        return courseMapper.toResponseList(courses);
    }

    @Override
    public CourseResponse getCourseById(Long id) {
        Course course = courseRepository.findById(id)
                .orElseThrow(() -> new AppException("Lớp không tồn tại"));
        return courseMapper.toResponse(course);
    }

    @Override
    public List<CourseResponse> getCoursesBySemesterId(Long semesterId) {
        List<Course> courses = courseRepository.findBySemester_SemesterId(semesterId);
        return courseMapper.toResponseList(courses);
    }

    @Override
    public List<CourseResponse> getCoursesByLecturerId(Long lecturerId) {
        List<Course> courses = courseRepository.findByLecturer_LecturerId(lecturerId);
        return courseMapper.toResponseList(courses);
    }

    @Override
    public CourseResponse updateCourse(CourseUpdateRequest request) {
        Course course = courseRepository.findById(request.getCourseId())
                .orElseThrow(() -> new AppException("Lớp không tồn tại"));

        if (request.getSemesterId() != null) {
            Semester semester = semesterRepository.findById(request.getSemesterId())
                    .orElseThrow(() -> new AppException("Kì học không tồn tại"));
            course.setSemester(semester);
        }

        if (request.getLecturerId() != null) {
            Lecturer lecturer = lecturerRepository.findById(request.getLecturerId())
                    .orElseThrow(() -> new AppException("Giảng viên không tồn tại"));
            course.setLecturer(lecturer);
        }

        courseMapper.updateEntity(course, request);
        Course updatedCourse = courseRepository.save(course);
        return courseMapper.toResponse(updatedCourse);
    }



    @Override
    public List<CourseResponse> importCoursesFromExcel(MultipartFile file) {
        List<CourseResponse> importedCourses = new ArrayList<>();

        try (Workbook workbook = new XSSFWorkbook(file.getInputStream())) {
            Sheet sheet = workbook.getSheetAt(0);
            List<Course> courseList = new ArrayList<>();

            for (int i = 1; i <= sheet.getLastRowNum(); i++) {
                Row row = sheet.getRow(i);
                if (row == null) continue;

                long semesterId = (long) row.getCell(0).getNumericCellValue();
                String courseName = row.getCell(1).getStringCellValue();
                String courseCode = row.getCell(2).getStringCellValue();
                int maxGroup = (int) row.getCell(3).getNumericCellValue();
                int groupCount = (int) row.getCell(4).getNumericCellValue();

                if (courseRepository.existsByCourseCode(courseCode)) continue;

                Semester semester = semesterRepository.findById(semesterId)
                        .orElseThrow(() -> new RuntimeException("Semester not found: " + semesterId));

                CourseRequest request = new CourseRequest();
                request.setCourseCode(courseCode);
                request.setCourseName(courseName);
                request.setSemesterId(semesterId);
                request.setMaxGroup(maxGroup);
                request.setGroupCount(groupCount);
                Course course = courseMapper.toEntity(request);
                courseList.add(course);
            }

            List<Course> saved = courseRepository.saveAll(courseList);
            importedCourses = saved.stream()
                    .map(courseMapper::toResponse)
                    .toList();

        } catch (IOException e) {
            throw new RuntimeException("Failed to import Excel file: " + e.getMessage());
        }
        return importedCourses;
    }

    @Override
    public void deleteCourse(Long courseId) {
        Course course = courseRepository.findById(courseId)
                .orElseThrow(() -> new AppException("Lớp học không tồn tại"));

        courseRepository.delete(course);
    }
}

