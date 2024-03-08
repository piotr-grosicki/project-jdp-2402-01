package com.kodilla.ecommercee.controller;

import com.kodilla.ecommercee.domain.Group;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.ArrayList;
import java.util.List;

@RestController
@RequestMapping("/groups")
public class GroupController {
    private final List<Group> productGroups = new ArrayList<>();
    private int nextGroupId =1;

    @GetMapping
    public List<Group> getAllGroups() {
        return productGroups;
    }

    @PostMapping
    public Group addGroup(@RequestBody Group group) {
        group.setId(nextGroupId);
        nextGroupId++;
        productGroups.add(group);
        return group;
    }

    @GetMapping("/{id}")
    public ResponseEntity<Group> getGroupById(@PathVariable("id") int id) {
        for (Group group : productGroups) {
            if (group.getId() == id) {
                return ResponseEntity.ok(group);
            }
        }
        return ResponseEntity.notFound().build();
    }
    @PutMapping("/{id}")
    public ResponseEntity<Group> updateGroup(@PathVariable("id") int id, @RequestBody Group updatedGroup) {
        for (Group group : productGroups) {
            if (group.getId() == id) {
                updatedGroup.setId(id);
                productGroups.remove(group);
                productGroups.add(updatedGroup);
                return ResponseEntity.ok(updatedGroup);
            }
        }
        return ResponseEntity.notFound().build();
    }
}

