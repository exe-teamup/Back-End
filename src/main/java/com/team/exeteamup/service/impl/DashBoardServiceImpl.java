package com.team.exeteamup.service.impl;

import com.team.exeteamup.dto.response.DashboardStatsResponse;
import com.team.exeteamup.entity.AccountNotification;
import com.team.exeteamup.enums.SemesterStatus;
import com.team.exeteamup.enums.joinRequest.JoinRequestStatus;
import com.team.exeteamup.repository.*;
import com.team.exeteamup.service.inter.DashBoardService;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

import java.time.LocalDate;
import java.time.LocalDateTime;
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
}
