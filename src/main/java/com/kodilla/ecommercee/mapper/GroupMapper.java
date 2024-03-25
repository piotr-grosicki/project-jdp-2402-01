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
        List<Product> groupProductsList = groupDto.getProductsIds().stream()
                .map(productRepository::findByIdAndActiveTrue)
                .filter(Optional::isPresent)
                .map(Optional::get)
                .toList();
        return new Group(
                groupDto.getId(),
                groupDto.getName(),
                groupDto.getDescription(),
                groupProductsList,
                groupDto.isActive());
    }

    public GroupDto mapToGroupDto(final Group group) {
        List<Long> groupProductsIds = group.getProducts().stream()
                .filter(Product::isActive)
                .map(Product::getId)
                .toList();
        return new GroupDto(
                group.getId(),
                group.getName(),
                group.getDescription(),
                groupProductsIds,
                group.isActive());
    }

    public List<GroupDto> mapToGroupDtoList(final List<Group> groupList) {
        return groupList.stream()
                .map(this::mapToGroupDto)
                .toList();
    }
}