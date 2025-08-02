package com.expensivemanager.service;

import com.expensivemanager.model.GroupMember;
import com.expensivemanager.model.Group;
import com.expensivemanager.model.User;
import com.expensivemanager.repository.GroupMemberRepository;
import com.expensivemanager.repository.GroupRepository;
import com.expensivemanager.repository.UserRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;

/**
 * Service layer for group membership management.
 * Handles adding, removing, and listing members in groups.
 */
@Service
public class GroupMemberService {

    @Autowired
    private GroupMemberRepository groupMemberRepository;

    @Autowired
    private GroupRepository groupRepository;

    @Autowired
    private UserRepository userRepository;

    /**
     * Adds a user to a group as a member.
     * Throws IllegalArgumentException if group or user is not found, or if already a member.
     *
     * @param groupId the group ID
     * @param userId the user ID
     * @return the created GroupMember entity
     */
    public GroupMember addMember(Long groupId, Long userId) {
        Group group = groupRepository.findById(groupId)
                .orElseThrow(() -> new IllegalArgumentException("Group not found"));
        User user = userRepository.findById(userId)
                .orElseThrow(() -> new IllegalArgumentException("User not found"));
        // Prevent duplicate membership
        boolean alreadyMember = groupMemberRepository.findByGroupId(groupId).stream()
                .anyMatch(m -> m.getUser().getId().equals(userId));
        if (alreadyMember) {
            throw new IllegalArgumentException("User is already a member of the group");
        }
        GroupMember member = new GroupMember();
        member.setGroup(group);
        member.setUser(user);
        return groupMemberRepository.save(member);
    }

    /**
     * Removes a user from a group. Does nothing if not a member.
     *
     * @param groupId the group ID
     * @param userId the user ID
     */
    public void removeMember(Long groupId, Long userId) {
        List<GroupMember> members = groupMemberRepository.findByGroupId(groupId);
        for (GroupMember member : members) {
            if (member.getUser().getId().equals(userId)) {
                groupMemberRepository.delete(member);
                break;
            }
        }
    }

    /**
     * Lists all members of a group.
     *
     * @param groupId the group ID
     * @return list of GroupMember entities
     */
    public List<GroupMember> getMembersByGroupId(Long groupId) {
        return groupMemberRepository.findByGroupId(groupId);
    }

    /**
     * Lists all groups a user is a member of.
     *
     * @param userId the user ID
     * @return list of GroupMember entities
     */
    public List<GroupMember> getGroupsByUserId(Long userId) {
        return groupMemberRepository.findByUserId(userId);
    }

    /**
     * Retrieves all members (users) belonging to a specific group.
     *
     * @param groupId the group ID
     * @return a list of users who are members of the group
     */
    public List<User> getGroupMembers(Long groupId) {
        // Get all group member entries for the given group
        List<GroupMember> members = groupMemberRepository.findByGroupId(groupId);
        // Extract the User objects from the group member entries
        return members.stream()
                .map(GroupMember::getUser)
                .toList();
    }

}
