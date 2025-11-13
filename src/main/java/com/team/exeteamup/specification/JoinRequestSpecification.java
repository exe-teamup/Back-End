package com.team.exeteamup.specification;

import com.team.exeteamup.entity.JoinRequest;
import com.team.exeteamup.enums.joinRequest.JoinRequestType;
import jakarta.persistence.criteria.Predicate;
import org.springframework.data.jpa.domain.Specification;

import java.util.ArrayList;
import java.util.List;

public class JoinRequestSpecification {

    public static Specification<JoinRequest> filterJoinRequests(Long userId, JoinRequestType type) {
        return (root, query, criteriaBuilder) -> {
            List<Predicate> predicates = new ArrayList<>();

            // 1. Lọc theo User ID (Sinh viên gửi request hoặc được mời)
            if (userId != null) {
                // JoinRequest -> User -> userId
                predicates.add(criteriaBuilder.equal(root.get("user").get("userId"), userId));
            }

            // 2. Lọc theo Loại request (STUDENT_REQUEST / GROUP_INVITATION)
            if (type != null) {
                predicates.add(criteriaBuilder.equal(root.get("requestType"), type));
            }

            // Gộp các điều kiện bằng AND
            return criteriaBuilder.and(predicates.toArray(new Predicate[0]));
        };
    }
}