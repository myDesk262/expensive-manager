package com.expensivemanager.service;

import com.expensivemanager.model.Group;
import com.expensivemanager.model.GroupMember;
import com.expensivemanager.model.User;
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
 * Unit tests for group membership logic in GroupService (or a dedicated GroupMemberService).
 * Uses Mockito to isolate business logic from the database.
 */
class GroupMemberServiceTest {

    @Mock
    private GroupRepository groupRepository;

    @Mock
    private UserRepository userRepository;

    @Mock
    private GroupMemberRepository groupMemberRepository;

    @InjectMocks
    private GroupService groupService; // Or GroupMemberService, if separated

    @BeforeEach
    void setUp() {
        MockitoAnnotations.openMocks(this);
    }

    @Test
    void testAddMember_success() {
        Group group = new Group(); group.setId(5L);
        User user = new User(); user.setId(6L);

        when(groupRepository.findById(5L)).thenReturn(Optional.of(group));
        when(userRepository.findById(6L)).thenReturn(Optional.of(user));

        GroupMember member = new GroupMember();
        member.setGroup(group);
        member.setUser(user);
        when(groupMemberRepository.save(any(GroupMember.class))).thenReturn(member);

        GroupMember result = groupService.addMember(5L, 6L);

        assertEquals(group, result.getGroup());
        assertEquals(user, result.getUser());
    }

    @Test
    void testAddMember_groupNotFound() {
        when(groupRepository.findById(anyLong())).thenReturn(Optional.empty());
        Exception ex = assertThrows(IllegalArgumentException.class,
                () -> groupService.addMember(999L, 1L));
        assertEquals("Group or User not found", ex.getMessage());
    }

    @Test
    void testRemoveMember_success() {
        Group group = new Group(); group.setId(3L);
        User user = new User(); user.setId(4L);
        GroupMember member = new GroupMember(); member.setGroup(group); member.setUser(user);

        when(groupMemberRepository.findAll()).thenReturn(List.of(member));

        groupService.removeMember(3L, 4L);

        verify(groupMemberRepository).delete(member);
    }
}
