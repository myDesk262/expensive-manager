package com.expensivemanager.repository;

import com.expensivemanager.model.GroupMember;

import java.util.List;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

/**
 * Repository interface for accessing and managing GroupMember entities.
 * Handles user membership records in groups.
 */
@Repository
public interface GroupMemberRepository extends JpaRepository<GroupMember, Long> {
    // Example custom method: find all members by group
    // List<GroupMember> findByGroupId(Long groupId);

    // Example in GroupMemberRepository
    List<GroupMember> findByGroupId(Long groupId);
    List<GroupMember> findByUserId(Long userId);

}
