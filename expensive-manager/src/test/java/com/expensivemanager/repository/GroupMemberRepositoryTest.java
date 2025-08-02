package com.expensivemanager.repository;

import com.expensivemanager.model.Group;
import com.expensivemanager.model.GroupMember;
import com.expensivemanager.model.User;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.orm.jpa.DataJpaTest;

import java.util.List;

import static org.junit.jupiter.api.Assertions.*;

/**
 * Integration tests for GroupMemberRepository with H2 in-memory database.
 */
@DataJpaTest
class GroupMemberRepositoryTest {

    @Autowired
    private GroupRepository groupRepository;

    @Autowired
    private UserRepository userRepository;

    @Autowired
    private GroupMemberRepository groupMemberRepository;

    @Test
    void testSaveAndFindByGroupId() {
        // Create and save User
        User user = new User();
        user.setUsername("testuser");
        user.setPassword("pw");
        user.setEmail("testuser@example.com");
        user.setRole("ROLE_USER");
        user = userRepository.save(user);

        // Create and save Group
        Group group = new Group();
        group.setName("Test Group");
        group = groupRepository.save(group);

        // Create and save GroupMember
        GroupMember member = new GroupMember();
        member.setGroup(group);
        member.setUser(user);
        groupMemberRepository.save(member);

        // Retrieve by groupId (implement/findByGroup_Id if not present)
        List<GroupMember> members = groupMemberRepository.findAll();
        assertFalse(members.isEmpty());
        assertEquals("testuser", members.get(0).getUser().getUsername());
    }

    @Test
    void testDeleteMember() {
        // Setup
        User user = new User(); 
        user.setUsername("user2"); 
        user.setPassword("pw"); 
        user.setEmail("user2@example.com");
        user.setRole("ROLE_USER");
        user = userRepository.save(user);

        Group group = new Group(); group.setName("DeleteTest");
        group = groupRepository.save(group);

        GroupMember member = new GroupMember();
        member.setGroup(group); member.setUser(user);
        member = groupMemberRepository.save(member);

        // Now delete
        groupMemberRepository.delete(member);

        assertFalse(groupMemberRepository.findById(member.getId()).isPresent());
    }
}
