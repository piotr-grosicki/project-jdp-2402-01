package com.kodilla.ecommercee.mapper;

import com.kodilla.ecommercee.domain.Group;
import com.kodilla.ecommercee.domain.Product;
import com.kodilla.ecommercee.domain.ProductDto;
import com.kodilla.ecommercee.repository.GroupRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Optional;

@Service
public class ProductMapper {

    @Autowired
    private GroupRepository groupRepository;

    public Product mapToProduct(final ProductDto productDto) {
        Product product = new Product();
        product.setId(productDto.getId());
        product.setName(productDto.getName());
        product.setDescription(productDto.getDescription());
        product.setPrice(productDto.getPrice());
        product.setActive(productDto.isActive());

        Optional<Group> optionalGroup = groupRepository.findById(productDto.getGroup_id());
        if (optionalGroup.isPresent()) {
            Group group = optionalGroup.get();
            product.setGroup(group);
        } else {
            product.setGroup(null);
        }
        return product;
    }

    public ProductDto mapToProductDto(final Product product) {
        ProductDto productDto =  new ProductDto();
                productDto.setId(product.getId());
                productDto.setName(product.getName());
                productDto.setDescription(product.getDescription());
                productDto.setPrice(product.getPrice());
                productDto.setActive(product.isActive());
                try {
                    productDto.setGroup_id(product.getGroup().getId());
                } catch (NullPointerException e) {
                    productDto.setGroup_id(null);
                }
                return productDto;
    }

    public List<ProductDto> mapToProductDtoList(final List<Product> productList) {
        return productList.stream()
                .map(this::mapToProductDto)
                .toList();
    }

}
