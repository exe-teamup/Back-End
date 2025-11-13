package com.team.exeteamup.specification;

import com.team.exeteamup.entity.Group;
import com.team.exeteamup.entity.User;
import com.team.exeteamup.enums.GroupStatus;
import jakarta.persistence.criteria.Join;
import jakarta.persistence.criteria.JoinType;
import jakarta.persistence.criteria.Predicate;
import org.springframework.data.jpa.domain.Specification;

import java.util.ArrayList;
import java.util.List;

public class GroupSpecification {

    public static Specification<Group> filterGroups(GroupStatus status, Long majorId) {
        return (root, query, criteriaBuilder) -> {
            List<Predicate> predicates = new ArrayList<>();

            // 1. Điều kiện Status (Nếu khác null thì mới thêm điều kiện)
            if (status != null) {
                predicates.add(criteriaBuilder.equal(root.get("groupStatus"), status));
            }

            // 2. Điều kiện Major (Của Leader)
            if (majorId != null) {
                // Join bảng Group -> User (để lấy thông tin thành viên)
                // JoinType.INNER nghĩa là chỉ lấy Group nào có User (an toàn hơn)
                Join<Group, User> usersJoin = root.join("users", JoinType.INNER);

                // Chỉ xét User là Leader
                Predicate isLeader = criteriaBuilder.isTrue(usersJoin.get("isLeader"));

                // User đó phải thuộc MajorId truyền vào
                Predicate hasMajor = criteriaBuilder.equal(usersJoin.get("major").get("id"), majorId);

                // Gộp 2 điều kiện: Phải là Leader VÀ Phải đúng Major
                predicates.add(criteriaBuilder.and(isLeader, hasMajor));
            }

            // Xếp các điều kiện chồng lên nhau bằng AND
            return criteriaBuilder.and(predicates.toArray(new Predicate[0]));
        };
    }
}