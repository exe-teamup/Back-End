package com.team.exeteamup.service.impl;

import com.team.exeteamup.dto.response.StudentResponse;
import com.team.exeteamup.entity.Student;
import com.team.exeteamup.mapper.StudentMapper;
import com.team.exeteamup.repository.StudentRepository;
import com.team.exeteamup.service.StudentService;
import org.springframework.data.domain.Pageable;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.stream.Collectors;

@Service
public class StudentServiceImpl implements StudentService {
    @Autowired
    private StudentRepository studentRepository;

    @Autowired
    private StudentMapper studentMapper;

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

//    public Page<StudentResponse> getAllStudents(Pageable pageable) {
//        Page<Student> studentPage = studentRepository.findAll(pageable);
//        return studentPage.map(studentMapper::toResponse);
//    }
}
