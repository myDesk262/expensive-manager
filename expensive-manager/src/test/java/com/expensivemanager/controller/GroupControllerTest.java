package com.expensivemanager.controller;

import com.expensivemanager.model.Group;
import com.expensivemanager.model.GroupMember;
import com.expensivemanager.repository.UserRepository;
import com.expensivemanager.service.GroupService;
import com.expensivemanager.service.JwtUtil;

import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.AutoConfigureMockMvc;
import org.springframework.boot.test.autoconfigure.web.servlet.WebMvcTest;
import org.springframework.http.MediaType;
import org.springframework.test.context.bean.override.mockito.MockitoBean;
import org.springframework.test.web.servlet.MockMvc;

import java.util.Collections;
import java.util.List;

import static org.mockito.ArgumentMatchers.*;
import static org.mockito.Mockito.when;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.*;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.*;

/**
 * Unit tests for GroupController endpoints using MockMvc.
 */
@WebMvcTest(GroupController.class)
@AutoConfigureMockMvc(addFilters = false)
class GroupControllerTest {

    @Autowired
    private MockMvc mockMvc;

    @MockitoBean
    private GroupService groupService;

    @MockitoBean
    private JwtUtil jwtUtil;

    @MockitoBean
    private UserRepository userRepository;

    @Test
    @DisplayName("Create a new group")
    void createGroup_success() throws Exception {
        Group group = new Group();
        group.setId(1L);
        group.setName("Roommates");

        when(groupService.createGroup(anyString())).thenReturn(group);

        mockMvc.perform(post("/groups")
                        .contentType(MediaType.APPLICATION_JSON)
                        .content("{\"name\":\"Roommates\"}"))
                .andExpect(status().isOk())
                .andExpect(jsonPath("$.id").value(1L))
                .andExpect(jsonPath("$.name").value("Roommates"));
    }

    @Test
    @DisplayName("Add a member to a group")
    void addMember_success() throws Exception {
        GroupMember member = new GroupMember();
        member.setId(1L);
        // You can set more fields if you want

        when(groupService.addMember(eq(2L), eq(3L))).thenReturn(member);

        mockMvc.perform(post("/groups/2/members/3"))
                .andExpect(status().isOk())
                .andExpect(jsonPath("$.id").value(1L));
    }

    @Test
    @DisplayName("Remove a member from a group")
    void removeMember_success() throws Exception {
        // No need to mock void methods unless you want to verify
        mockMvc.perform(delete("/groups/2/members/3"))
                .andExpect(status().isOk())
                .andExpect(content().string("Member removed successfully"));
    }

    @Test
    @DisplayName("Get all groups")
    void getAllGroups_success() throws Exception {
        Group group = new Group();
        group.setId(1L);
        group.setName("Office Friends");

        when(groupService.getAllGroups()).thenReturn(List.of(group));

        mockMvc.perform(get("/groups"))
                .andExpect(status().isOk())
                .andExpect(jsonPath("$[0].id").value(1L))
                .andExpect(jsonPath("$[0].name").value("Office Friends"));
    }

    @Test
    @DisplayName("Get members of a group")
    void getGroupMembers_success() throws Exception {
        GroupMember member = new GroupMember();
        member.setId(1L);

        when(groupService.getGroupMembers(2L)).thenReturn(Collections.singletonList(member));

        mockMvc.perform(get("/groups/2/members"))
                .andExpect(status().isOk())
                .andExpect(jsonPath("$[0].id").value(1L));
    }
}
