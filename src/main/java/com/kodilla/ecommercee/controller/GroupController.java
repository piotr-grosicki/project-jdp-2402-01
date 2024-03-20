package com.kodilla.ecommercee.controller;

import com.kodilla.ecommercee.domain.Group;
import com.kodilla.ecommercee.domain.GroupDto;
import com.kodilla.ecommercee.exception.GroupNotFoundException;
import com.kodilla.ecommercee.mapper.GroupMapper;
import com.kodilla.ecommercee.service.GroupService;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/groups")
@RequiredArgsConstructor
public class GroupController {

    private final GroupService groupService;
    private final GroupMapper groupMapper;

    @GetMapping
    public ResponseEntity<List<GroupDto>> getAllGroups() {
        List<Group> groups = groupService.getAllGroups();
        return ResponseEntity.ok(groupMapper.mapToGroupDtoList(groups));
    }

    @GetMapping("{groupId}")
    public ResponseEntity<GroupDto> getGroup(@PathVariable("groupId") Long groupId) throws GroupNotFoundException {
        return ResponseEntity.ok(groupMapper.mapToGroupDto(groupService.getGroup(groupId)));
    }

    @PostMapping
    public ResponseEntity<Void> createGroup(@RequestBody GroupDto groupDto) {
            Group group = groupMapper.mapToGroup(groupDto);
            groupService.saveGroup(group);
            return ResponseEntity.ok().build();
    }

    @PutMapping
    public ResponseEntity<GroupDto> updateGroup(@RequestBody GroupDto groupDto) {
        Group group = groupMapper.mapToGroup(groupDto);
        Group savedGroup = groupService.saveGroup(group);
        return ResponseEntity.ok(groupMapper.mapToGroupDto(savedGroup));
    }

    @DeleteMapping(value = "{groupId}")
    public ResponseEntity<Void> deleteGroup(@PathVariable Long groupId) throws GroupNotFoundException {
        groupService.deleteGroup(groupId);
        return ResponseEntity.ok().build();
    }
}
