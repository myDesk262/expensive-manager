package com.expensivemanager.controller;

import com.expensivemanager.config.JwtAuthenticationFilter;
import com.expensivemanager.model.GroupMember;
import com.expensivemanager.model.User;
import com.expensivemanager.service.GroupMemberService;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.AutoConfigureMockMvc;
import org.springframework.boot.test.autoconfigure.web.servlet.WebMvcTest;
import org.springframework.test.context.bean.override.mockito.MockitoBean;
import org.springframework.test.web.servlet.MockMvc;

import static org.mockito.ArgumentMatchers.eq;
import static org.mockito.Mockito.when;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.*;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.*;

/**
 * Unit tests for group membership endpoints in GroupMemberController.
 * Uses MockMvc to test REST endpoints.
 */
@WebMvcTest(GroupMemberController.class)
@AutoConfigureMockMvc(addFilters = false)
class GroupMemberControllerTest {

    @Autowired
    private MockMvc mockMvc;

    @MockitoBean
    private GroupMemberService groupMemberService;

    @MockitoBean
    private JwtAuthenticationFilter jwtAuthenticationFilter;

    @Test
    @DisplayName("Add a user to a group")
    void addMember_success() throws Exception {
        GroupMember member = new GroupMember();
        member.setId(301L);
        User user = new User(); user.setId(9L); user.setUsername("bob");
        member.setUser(user);

        when(groupMemberService.addMember(eq(1L), eq(9L))).thenReturn(member);

        mockMvc.perform(post("/groups/1/members/9"))
                .andExpect(status().isOk())
                .andExpect(jsonPath("$.id").value(301L))
                .andExpect(jsonPath("$.user.username").value("bob"));
    }

    @Test
    @DisplayName("Remove a user from a group")
    void removeMember_success() throws Exception {
        // You may want to verify removal logic
        mockMvc.perform(delete("/groups/1/members/9"))
                .andExpect(status().isOk())
                .andExpect(content().string("Member removed successfully"));
    }

    /* @Test
    @DisplayName("List all members of a group")
    void getAllMembers_success() throws Exception {
        GroupMember member = new GroupMember();
        member.setId(201L);
        User user = new User();
        user.setId(10L);
        user.setUsername("alice");
        member.setUser(user);

        when(groupMemberService.getGroupMembers(1L)).thenReturn(List.of(user));

        mockMvc.perform(get("/groups/1/members "))
                .andExpect(status().isOk())
                .andExpect(jsonPath("$[0].id").value(10L))
                .andExpect(jsonPath("$[0].username").value("alice"));
    } */

}
