package com.expensivemanager.controller;

import com.expensivemanager.model.GroupMember;
import com.expensivemanager.service.GroupMemberService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

/**
 * REST controller for managing group memberships.
 * Provides endpoints for adding, removing, and listing group members.
 */
@RestController
@RequestMapping("/groups")
public class GroupMemberController {

    @Autowired
    private GroupMemberService groupMemberService;

    /**
     * Adds a user as a member to a group.
     *
     * @param groupId the group ID
     * @param userId  the user ID
     * @return the added GroupMember entity
     */
    @PostMapping("/{groupId}/members/{userId}")
    public ResponseEntity<GroupMember> addMember(@PathVariable Long groupId, @PathVariable Long userId) {
        GroupMember member = groupMemberService.addMember(groupId, userId);
        return ResponseEntity.ok(member);
    }

    /**
     * Removes a user from a group.
     *
     * @param groupId the group ID
     * @param userId  the user ID
     * @return a success message
     */
    @DeleteMapping("/{groupId}/members/{userId}")
    public ResponseEntity<String> removeMember(@PathVariable Long groupId, @PathVariable Long userId) {
        groupMemberService.removeMember(groupId, userId);
        return ResponseEntity.ok("Member removed successfully");
    }

    /**
     * Lists all members in a group.
     * @param groupId the group ID
     * @return list of GroupMember entities
     */
    @GetMapping("/{groupId}/members")
    public ResponseEntity<List<GroupMember>> listMembers(@PathVariable Long groupId) {
        List<GroupMember> members = groupMemberService.getMembersByGroupId(groupId);
        return ResponseEntity.ok(members);
    }

    /**
     * Lists all group memberships for a user.
     *
     * @param userId the user ID
     * @return list of GroupMember entities (groups this user belongs to)
     */
    @GetMapping("/memberships/{userId}")
    public ResponseEntity<List<GroupMember>> listGroupsForUser(@PathVariable Long userId) {
        List<GroupMember> memberships = groupMemberService.getGroupsByUserId(userId);
        return ResponseEntity.ok(memberships);
    }

}
                  