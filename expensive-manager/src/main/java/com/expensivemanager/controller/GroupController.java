package com.expensivemanager.controller;

import com.expensivemanager.model.Group;
import com.expensivemanager.model.GroupMember;
import com.expensivemanager.service.GroupService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

/**
 * REST controller for group and group membership management.
 */
@RestController
@RequestMapping("/groups")
public class GroupController {

    @Autowired
    private GroupService groupService;

    /**
     * Creates a new group.
     * @param group Group object with a name.
     * @return The created group.
     */
    @PostMapping
    public ResponseEntity<Group> createGroup(@RequestBody Group group) {
        Group created = groupService.createGroup(group.getName());
        return ResponseEntity.ok(created);
    }

    /**
     * Adds a member (user) to a group.
     * @param groupId Group ID.
     * @param userId User ID.
     * @return The created GroupMember.
     */
    @PostMapping("/{groupId}/members/{userId}")
    public ResponseEntity<GroupMember> addMember(@PathVariable Long groupId, @PathVariable Long userId) {
        GroupMember member = groupService.addMember(groupId, userId);
        return ResponseEntity.ok(member);
    }

    /**
     * Removes a user from a group.
     * @param groupId Group ID.
     * @param userId User ID.
     * @return Success message.
     */
    @DeleteMapping("/{groupId}/members/{userId}")
    public ResponseEntity<String> removeMember(@PathVariable Long groupId, @PathVariable Long userId) {
        groupService.removeMember(groupId, userId);
        return ResponseEntity.ok("Member removed successfully");
    }

    /**
     * Lists all groups.
     * @return List of groups.
     */
    @GetMapping
    public ResponseEntity<List<Group>> getAllGroups() {
        return ResponseEntity.ok(groupService.getAllGroups());
    }

    /**
     * Lists all members of a group.
     * @param groupId Group ID.
     * @return List of GroupMember entities.
     */
    @GetMapping("/{groupId}/members")
    public ResponseEntity<List<GroupMember>> getGroupMembers(@PathVariable Long groupId) {
        return ResponseEntity.ok(groupService.getGroupMembers(groupId));
    }
}
