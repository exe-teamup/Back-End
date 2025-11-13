package com.team.exeteamup.specification;

import com.team.exeteamup.entity.User;
import jakarta.persistence.criteria.Predicate;
import org.springframework.data.jpa.domain.Specification;

import java.util.ArrayList;
import java.util.List;

public class UserSpecification {

    public static Specification<User> filterUsers(Long majorId, Long courseId, Boolean isLeader, Boolean hasGroup) {
        return (root, query, criteriaBuilder) -> {
            List<Predicate> predicates = new ArrayList<>();

            // 1. Lọc theo Major ID
            if (majorId != null) {
                predicates.add(criteriaBuilder.equal(root.get("major").get("id"), majorId));
            }

            // 2. Lọc theo Course ID
            if (courseId != null) {
                predicates.add(criteriaBuilder.equal(root.get("course").get("id"), courseId));
            }

            // 3. Lọc theo IsLeader (true/false)
            if (isLeader != null) {
                predicates.add(criteriaBuilder.equal(root.get("isLeader"), isLeader));
            }

            // 4. Lọc theo HasGroup (Đã có nhóm hay chưa)
            if (hasGroup != null) {
                if (hasGroup) {
                    // hasGroup = true -> Tìm user có group_id KHÁC NULL
                    predicates.add(criteriaBuilder.isNotNull(root.get("group")));
                } else {
                    // hasGroup = false -> Tìm user có group_id LÀ NULL (chưa có nhóm)
                    predicates.add(criteriaBuilder.isNull(root.get("group")));
                }
            }

            // Gộp tất cả điều kiện bằng AND
            return criteriaBuilder.and(predicates.toArray(new Predicate[0]));
        };
    }
}