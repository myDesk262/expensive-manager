package com.expensivemanager.service;

import com.expensivemanager.model.Group;
import com.expensivemanager.model.GroupMember;
import com.expensivemanager.model.User;
import com.expensivemanager.repository.GroupMemberRepository;
import com.expensivemanager.repository.GroupRepository;
import com.expensivemanager.repository.UserRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Optional;

/**
 * Service layer for group management and group membership operations.
 */
@Service
public class GroupService {

    @Autowired
    private GroupRepository groupRepository;
    @Autowired
    private UserRepository userRepository;
    @Autowired
    private GroupMemberRepository groupMemberRepository;

    /**
     * Creates a new group with the given name.
     * @param name Group name.
     * @return The created Group entity.
     */
    public Group createGroup(String name) {
        Group group = new Group();
        group.setName(name);
        return groupRepository.save(group);
    }

    /**
     * Adds a user to the group as a member.
     * @param groupId The group ID.
     * @param userId The user ID.
     * @return The created GroupMember entity.
     */
    public GroupMember addMember(Long groupId, Long userId) {
        Optional<Group> groupOpt = groupRepository.findById(groupId);
        Optional<User> userOpt = userRepository.findById(userId);
        if (groupOpt.isEmpty() || userOpt.isEmpty()) {
            throw new IllegalArgumentException("Group or User not found");
        }

        GroupMember member = new GroupMember();
        member.setGroup(groupOpt.get());
        member.setUser(userOpt.get());
        return groupMemberRepository.save(member);
    }

    /**
     * Removes a user from a group.
     * @param groupId The group ID.
     * @param userId The user ID.
     */
    public void removeMember(Long groupId, Long userId) {
        List<GroupMember> members = groupMemberRepository.findAll();
        for (GroupMember member : members) {
            if (member.getGroup().getId().equals(groupId) && member.getUser().getId().equals(userId)) {
                groupMemberRepository.delete(member);
                break;
            }
        }
    }

    /**
     * Finds all groups.
     * @return List of groups.
     */
    public List<Group> getAllGroups() {
        return groupRepository.findAll();
    }

    /**
     * Finds all members of a given group.
     * @param groupId The group ID.
     * @return List of GroupMember entities.
     */
    public List<GroupMember> getGroupMembers(Long groupId) {
        // You can optimize this by writing a custom repository method
        return groupMemberRepository.findAll()
            .stream()
            .filter(m -> m.getGroup().getId().equals(groupId))
            .toList();
    }
}
