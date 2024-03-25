package com.kodilla.ecommercee.mapper;

import com.kodilla.ecommercee.domain.Group;
import com.kodilla.ecommercee.domain.GroupDto;
import com.kodilla.ecommercee.domain.Product;
import com.kodilla.ecommercee.repository.ProductRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Component;

import java.util.List;
import java.util.Optional;

@Component
@RequiredArgsConstructor
public class GroupMapper {

    private final ProductRepository productRepository;

    public Group mapToGroup(final GroupDto groupDto) {
        Group group = new Group();
        group.setId(groupDto.getId());
        group.setName(groupDto.getName());
        group.setDescription(groupDto.getDescription());
        group.setProducts(groupDto.getProductsIds().stream()
                .map(productRepository::findByIdAndActiveTrue)
                .filter(Optional::isPresent)
                .map(Optional::get)
                .toList());
        group.setActive(groupDto.isActive());
        return group;
    }

    public GroupDto mapToGroupDto(final Group group) {
        GroupDto groupDto = new GroupDto();
        groupDto.setId(group.getId());
        groupDto.setName(group.getName());
        groupDto.setDescription(group.getDescription());
        groupDto.setProductsIds(group.getProducts().stream()
                .filter(Product::isActive)
                .map(Product::getId)
                .toList());
        groupDto.setActive(group.isActive());
        return groupDto;
    }

    public List<GroupDto> mapToGroupDtoList(final List<Group> groupList) {
        return groupList.stream().map(this::mapToGroupDto).toList();
    }
}
