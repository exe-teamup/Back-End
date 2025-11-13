package com.team.exeteamup.service.impl;

import com.team.exeteamup.dto.response.DashboardStatsResponse;
import com.team.exeteamup.dto.response.LecturerWorkLoadDTO;
import com.team.exeteamup.dto.response.LecturerWorkloadDashboardResponse;
import com.team.exeteamup.entity.Account;
import com.team.exeteamup.entity.AccountNotification;
import com.team.exeteamup.entity.Course;
import com.team.exeteamup.entity.Lecturer;
import com.team.exeteamup.enums.SemesterStatus;
import com.team.exeteamup.enums.joinRequest.JoinRequestStatus;
import com.team.exeteamup.repository.*;
import com.team.exeteamup.service.inter.DashBoardService;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.time.LocalDate;
import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.List;

@Service
@RequiredArgsConstructor
public class DashBoardServiceImpl implements DashBoardService {
    private final UserRepository userRepository;
    private final LecturerRepository lecturerRepository;
    private final SemesterRepository semesterRepository;
    private final CourseRepository courseRepository;
    private final GroupRepository groupRepository;
    private final PostRepository postRepository;
    private final JoinRequestRepository joinRequestRepository;
    private final AccountNotificationRepository accountNotificationRepository;

    @Override
    public DashboardStatsResponse getDashboardStats() {
        long totalStudents = userRepository.count();
        long totalLecturers = lecturerRepository.count();
        long activeSemesters = semesterRepository.countBySemesterStatus(SemesterStatus.ACTIVE);
        long totalCourses = courseRepository.count();

        LocalDateTime startOfDay = LocalDate.now().atStartOfDay();
        long groupsToday = groupRepository.countByCreatedAtAfter(startOfDay);
        long postsToday = postRepository.countByCreatedAtAfter(startOfDay);
        long pendingRequests = joinRequestRepository.countByRequestStatus(JoinRequestStatus.PENDING);

        List<AccountNotification> recentNotifications = accountNotificationRepository.findTop5ByOrderByCreatedAtDesc();
        List<DashboardStatsResponse.RecentActivityResponse> recentActivity = recentNotifications.stream()
                .map(notification -> DashboardStatsResponse.RecentActivityResponse.builder()
                        .content(notification.getFormattedContent())
                        .createdAt(notification.getCreatedAt())
                        .build())
                .toList();

        return DashboardStatsResponse.builder()
                .totalStudents(totalStudents)
                .totalLecturers(totalLecturers)
                .activeSemesters(activeSemesters)
                .totalCourses(totalCourses)
                .groupsCreatedToday(groupsToday)
                .newPostsToday(postsToday)
                .pendingJoinRequests(pendingRequests)
                .recentActivity(recentActivity)
                .build();
    }

    @Override
    @Transactional(readOnly = true)
    public LecturerWorkloadDashboardResponse getLecturerWorkloadDashboard() {
        List<Lecturer> lecturers = lecturerRepository.findAll();

        long total = 0;
        long withSlot = 0;
        long almostFull = 0;
        long full = 0;
        final double ALMOST_FULL_THRESHOLD = 80.0;

        List<LecturerWorkLoadDTO> detailsList = new ArrayList<>();

        for (Lecturer lecturer : lecturers) {
            total++;
            int currentLoad = 0;
            int quota = 0;

            List<Course> courses = lecturer.getCourses();
            if (courses != null) {
                for (Course course : courses) {
                    if (course.getMaxStudents() != null) {
                        quota += course.getMaxStudents();
                    }
                    if (course.getUsers() != null) {
                        currentLoad += course.getUsers().size();
                    }
                }
            }

            double percentage = (quota == 0) ? 0.0 : ((double) currentLoad / quota) * 100.0;
            String status;

            if (percentage >= 100.0) {
                status = "FULL";
                full++;
            } else if (percentage >= ALMOST_FULL_THRESHOLD) {
                status = "ALMOST_FULL";
                almostFull++;
            } else {
                status = "AVAILABLE";
                withSlot++;
            }

            Account account = lecturer.getAccount();

            LecturerWorkLoadDTO dto = LecturerWorkLoadDTO.builder()
                    .lecturerId(lecturer.getLecturerId())
                    .lecturerName(lecturer.getFullName())
                    .email(account != null ? account.getEmail() : "N/A")
                    .currentLoad(currentLoad)
                    .quota(quota)
                    .percentage(Math.round(percentage * 100.0) / 100.0)
                    .status(status)
                    .build();

            detailsList.add(dto);
        }

        return LecturerWorkloadDashboardResponse.builder()
                .totalLecturers(total)
                .lecturersWithSlot(withSlot)
                .lecturersAlmostFull(almostFull)
                .lecturersFull(full)
                .lecturerDetails(detailsList)
                .build();
    }
}
