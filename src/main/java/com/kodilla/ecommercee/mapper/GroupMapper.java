package com.kodilla.ecommercee.mapper;

import com.kodilla.ecommercee.domain.Group;
import com.kodilla.ecommercee.domain.GroupDto;
import com.kodilla.ecommercee.domain.Product;
import com.kodilla.ecommercee.repository.ProductRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.List;
import java.util.Optional;


@Service
public class GroupMapper {

    @Autowired
    private ProductRepository productRepository;

    public Group mapToGroup(final GroupDto groupDto) {
        Group group = new Group();
        group.setId(groupDto.getId());
        group.setName(groupDto.getName());
        group.setDescription(groupDto.getDescription());

        List<Product> products;
        try {
            products = groupDto.getProductsIds().stream()
                    .map(p -> productRepository.findByIdAndActiveTrue(p))
                    .filter(Optional::isPresent)
                    .map(Optional::get)
                    .toList();
        } catch (NullPointerException e) {
            products = new ArrayList<>();
        }

        group.setProducts(products);
        group.setActive(groupDto.isActive());

        return group;
    }

    public GroupDto mapToGroupDto(final Group group) {
        GroupDto groupDto = new GroupDto();
        groupDto.setId(group.getId());
        groupDto.setName(group.getName());
        groupDto.setDescription(group.getDescription());

        List<Long> productsIds = new ArrayList<>();
        try {
            productsIds = group.getProducts().stream()
                    .filter(Product::isActive)
                    .map(Product::getId)
                    .toList();
        } catch (NullPointerException e) {
            productsIds = new ArrayList<>();
        }

        groupDto.setProductsIds(productsIds);
        groupDto.setActive(group.isActive());

        return groupDto;
    }

    public List<GroupDto> mapToGroupDtoList(final List<Group> groupList) {
        return groupList.stream()
                .map(this::mapToGroupDto)
                .toList();
    }

}
