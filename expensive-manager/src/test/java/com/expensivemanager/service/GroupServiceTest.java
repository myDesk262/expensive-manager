package com.expensivemanager.service;

import com.expensivemanager.model.Group;
import com.expensivemanager.model.User;
import com.expensivemanager.model.GroupMember;
import com.expensivemanager.repository.GroupMemberRepository;
import com.expensivemanager.repository.GroupRepository;
import com.expensivemanager.repository.UserRepository;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.mockito.*;
import java.util.*;

import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.Mockito.*;

/**
 * Unit tests for GroupService business logic.
 * Uses Mockito to isolate the service from real database access.
 */
class GroupServiceTest {

    @Mock
    private GroupRepository groupRepository;

    @Mock
    private UserRepository userRepository;

    @Mock
    private GroupMemberRepository groupMemberRepository;

    @InjectMocks
    private GroupService groupService;

    @BeforeEach
    void setUp() {
        MockitoAnnotations.openMocks(this);
    }

    @Test
    void testCreateGroup_success() {
        Group group = new Group();
        group.setName("Travel Buddies");
        when(groupRepository.save(any(Group.class))).thenReturn(group);

        Group created = groupService.createGroup("Travel Buddies");

        assertEquals("Travel Buddies", created.getName());
        verify(groupRepository).save(any(Group.class));
    }

    @Test
    void testAddMember_success() {
        Group group = new Group(); group.setId(1L);
        User user = new User(); user.setId(2L);

        when(groupRepository.findById(1L)).thenReturn(Optional.of(group));
        when(userRepository.findById(2L)).thenReturn(Optional.of(user));

        GroupMember savedMember = new GroupMember();
        savedMember.setGroup(group); savedMember.setUser(user);
        when(groupMemberRepository.save(any(GroupMember.class))).thenReturn(savedMember);

        GroupMember member = groupService.addMember(1L, 2L);

        assertNotNull(member);
        assertEquals(group, member.getGroup());
        assertEquals(user, member.getUser());
    }

    @Test
    void testAddMember_groupNotFound() {
        when(groupRepository.findById(99L)).thenReturn(Optional.empty());
        Exception ex = assertThrows(IllegalArgumentException.class,
            () -> groupService.addMember(99L, 2L));
        assertEquals("Group or User not found", ex.getMessage());
    }

    @Test
    void testRemoveMember_success() {
        Group group = new Group(); group.setId(1L);
        User user = new User(); user.setId(2L);
        GroupMember member = new GroupMember(); member.setGroup(group); member.setUser(user);

        List<GroupMember> members = new ArrayList<>();
        members.add(member);

        when(groupMemberRepository.findAll()).thenReturn(members);

        groupService.removeMember(1L, 2L);

        verify(groupMemberRepository).delete(member);
    }

    @Test
    void testGetAllGroups() {
        Group g1 = new Group(); g1.setId(1L); g1.setName("G1");
        when(groupRepository.findAll()).thenReturn(List.of(g1));
        List<Group> all = groupService.getAllGroups();
        assertEquals(1, all.size());
        assertEquals("G1", all.get(0).getName());
    }
}
