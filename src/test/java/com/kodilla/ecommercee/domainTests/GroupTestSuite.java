package com.kodilla.ecommercee.domainTests;

import com.kodilla.ecommercee.domain.Group;
import com.kodilla.ecommercee.domain.Product;
import com.kodilla.ecommercee.repository.GroupRepository;
import com.kodilla.ecommercee.repository.ProductRepository;
import jakarta.transaction.Transactional;
import org.junit.jupiter.api.*;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;

import java.math.BigDecimal;
import java.util.ArrayList;
import java.util.List;
import java.util.Optional;

import static org.junit.jupiter.api.Assertions.*;

@SpringBootTest
@Transactional
public class GroupTestSuite {

    @Autowired
    private GroupRepository groupRepository;

    @Autowired
    private ProductRepository productRepository;

    private final Group groupOne = new Group();
    private final List<Product> productsListOne = new ArrayList<>();
    private final Product productOne = new Product();
    private final Product productTwo = new Product();

    @BeforeEach
    public void setUpForTest() {
        productOne.setName("productOne");
        productOne.setDescription("descOne");
        productOne.setPrice(BigDecimal.valueOf(11.11));
        productTwo.setActive(true);

        productTwo.setName("productTwo");
        productTwo.setDescription("descTwo");
        productTwo.setPrice(BigDecimal.valueOf(22.22));
        productTwo.setActive(true);

        productsListOne.add(productOne);
        productsListOne.add(productTwo);

        groupOne.setName("groupOne");
        groupOne.setDescription("descGroupOne");
        groupOne.setProducts(productsListOne);
        groupOne.setActive(true);
    }

    @AfterEach
    public void cleanUp() {
        groupRepository.deleteAll();
        productRepository.deleteAll();
    }

    @Test
    public void testAddGroup() {
        //Given

        //When
        groupRepository.save(groupOne);
        Optional<Group> retrievedGroup = groupRepository.findById(groupOne.getId());

        //Then
        assertTrue(retrievedGroup.isPresent());
        assertEquals(groupOne.getName(), retrievedGroup.get().getName());
        assertEquals(groupOne.getDescription(), retrievedGroup.get().getDescription());
        assertEquals(groupOne.isActive(), retrievedGroup.get().isActive());
    }

    @Test
    public void testGetAllGroups() {
        //Given
        Group groupTwo = new Group();
        groupTwo.setName("groupTwo");
        groupTwo.setDescription("descGroupTwo");
        groupTwo.setActive(true);
        groupRepository.save(groupOne);
        groupRepository.save(groupTwo);

        //When
        List<Group> retrievedGroupsList = groupRepository.findAll();

        //Then
        assertEquals(2, retrievedGroupsList.size());
    }

    @Test
    public void testGetGroupById() {
        //Given
        Group groupTwo = new Group();
        groupTwo.setName("groupTwo");
        groupTwo.setDescription("descGroupTwo");
        groupTwo.setActive(true);
        groupRepository.save(groupOne);
        groupRepository.save(groupTwo);

        //When
        Optional<Group> foundGroupOne = groupRepository.findById(groupOne.getId());
        Optional<Group> foundGroupTwo = groupRepository.findById(groupTwo.getId());

        //Then
        assertTrue(foundGroupOne.isPresent());
        assertEquals(groupOne.getId(), foundGroupOne.get().getId());
        assertEquals(groupOne.getName(), foundGroupOne.get().getName());
        assertTrue(foundGroupTwo.isPresent());
        assertEquals(groupTwo.getId(), foundGroupTwo.get().getId());
        assertEquals(groupTwo.getName(), foundGroupTwo.get().getName());
    }

    @Test
    public void testUpdateGroup() {
        //Given
        groupRepository.save(groupOne);

        //When
        Group groupUpdated = groupRepository.save(groupOne);
        groupUpdated.setName("nameUpdated");
        groupUpdated.setDescription("descUpdated");
        groupRepository.save(groupUpdated);
        Optional<Group> retrievedGroup = groupRepository.findById(groupUpdated.getId());

        //Then
        assertTrue(retrievedGroup.isPresent());
        assertEquals(groupUpdated.getId(), retrievedGroup.get().getId());
        assertEquals(groupUpdated.getName(), retrievedGroup.get().getName());
        assertEquals(groupUpdated.getDescription(), retrievedGroup.get().getDescription());
        assertEquals(groupUpdated.isActive(), retrievedGroup.get().isActive());
    }

    @Test
    public void testFindAllActiveGroups() {
        //Given
        Group groupInactive = new Group();
        groupInactive.setName("groupInactive");
        groupInactive.setDescription("descGroupInactive");
        groupInactive.setActive(false);
        groupRepository.save(groupOne);
        groupRepository.save(groupInactive);

        //When
        List<Group> allGroups = groupRepository.findAll();
        List<Group> activeGroups = groupRepository.findAllByActiveTrue();

        //Then
        assertEquals(2, allGroups.size());
        assertEquals(1, activeGroups.size());
        assertTrue(activeGroups.contains(groupOne));
        assertFalse(activeGroups.contains(groupInactive));
    }

    @Test
    public void testDeactivateGroup() {
        //Given
        groupRepository.save(groupOne);

        //When
        groupRepository.delete(groupOne);
        Optional<Group> deletedGroup = groupRepository.findById(groupOne.getId());

        //Then
        assertTrue(deletedGroup.isPresent());
        assertFalse(deletedGroup.get().isActive());
    }

    @Test
    public void testDeleteGroupFromDb() {
        //Given
        groupRepository.save(groupOne);

        //When
        groupRepository.deleteById(groupOne.getId());

        //Then
        assertTrue(groupRepository.findAll().contains(groupOne));
    }

    @Test
    public void testDeactivateGroupWithoutDeletingProductsFromDb() {
        //Given
        groupRepository.save(groupOne);
        productRepository.save(productOne);
        productRepository.save(productTwo);

        //When
        groupRepository.delete(groupOne);
        List<Group> allGroups = groupRepository.findAll();
        Optional<Group> deactivatedGroup = groupRepository.findById(groupOne.getId());
        List<Product> allProducts = productRepository.findAll();

        //Then
        assertEquals(1, allGroups.size());
        assertTrue(deactivatedGroup.isPresent());
        assertFalse(deactivatedGroup.get().isActive());
        assertEquals(2, allProducts.size());
    }

    @Test void testDeleteGroupWithDeletingProductsFromDb() {
        //Given
        groupRepository.save(groupOne);
        productRepository.save(productOne);
        productRepository.save(productTwo);

        //When
        groupRepository.deleteById(groupOne.getId());

        //Then
        assertTrue(groupRepository.findAll().contains(groupOne));
        assertTrue(productRepository.findAll().contains(productOne));
        assertTrue(productRepository.findAll().contains(productTwo));
    }
}
