package com.kodilla.ecommercee.service;

import com.kodilla.ecommercee.domain.Group;
import com.kodilla.ecommercee.exception.GroupNotFoundException;
import com.kodilla.ecommercee.repository.GroupRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
@RequiredArgsConstructor
public class GroupService {

    private final GroupRepository groupRepository;

    public List<Group> getAllGroups() {
        return groupRepository.findAllByActiveTrue();
    }

    public Group getGroup(final Long groupId) throws GroupNotFoundException {
        return groupRepository.findByIdAndActiveTrue(groupId).
                orElseThrow(GroupNotFoundException::new);
    }

    public Group saveGroup(final Group group) {
        return groupRepository.save(group);
    }

    public void deleteGroup(final Long groupId) throws GroupNotFoundException {
        groupRepository.delete(groupRepository.findByIdAndActiveTrue(groupId).
                orElseThrow(GroupNotFoundException::new));
    }

}
