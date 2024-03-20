package com.kodilla.ecommercee.controller;

import com.kodilla.ecommercee.domain.GroupDto;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.ArrayList;
import java.util.List;

@RestController
@RequestMapping("/groups")
public class GroupController {

    private final List<GroupDto> productGroups = new ArrayList<>();
    private Long nextGroupId = 1L;

    @GetMapping
    public List<GroupDto> getAllGroups() {
        return productGroups;
    }

    @GetMapping("/{groupId}")
    public ResponseEntity<GroupDto> getGroupById(@PathVariable("groupId") Long groupId) {
        for (GroupDto group : productGroups) {
            if (group.getId().equals(groupId)) {
                return ResponseEntity.ok(group);
            }
        }
        return ResponseEntity.notFound().build();
    }

    @PostMapping
    public GroupDto addGroup(@RequestBody GroupDto group) {
        group.setId(nextGroupId);
        nextGroupId++;
        productGroups.add(group);
        return group;
    }

    @PutMapping("/{groupId}")
    public ResponseEntity<GroupDto> updateGroup(@PathVariable("groupId") Long groupId, @RequestBody GroupDto updatedGroup) {
        for (GroupDto group : productGroups) {
            if (group.getId().equals(groupId)) {
                updatedGroup.setId(groupId);
                productGroups.remove(group);
                productGroups.add(updatedGroup);
                return ResponseEntity.ok(updatedGroup);
            }
        }
        return ResponseEntity.notFound().build();
    }
}
