package com.expensivemanager.repository;

import com.expensivemanager.model.Group;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.orm.jpa.DataJpaTest;

import java.util.List;

import static org.junit.jupiter.api.Assertions.*;

/**
 * Integration tests for GroupRepository using H2 in-memory DB.
 */
@DataJpaTest
class GroupRepositoryTest {

    @Autowired
    private GroupRepository groupRepository;

    @Test
    void testSaveAndFindById() {
        Group group = new Group();
        group.setName("Test Group");
        Group saved = groupRepository.save(group);

        assertNotNull(saved.getId());

        Group found = groupRepository.findById(saved.getId()).orElse(null);
        assertNotNull(found);
        assertEquals("Test Group", found.getName());
    }

    @Test
    void testFindAll() {
        Group g1 = new Group(); g1.setName("G1");
        Group g2 = new Group(); g2.setName("G2");
        groupRepository.save(g1);
        groupRepository.save(g2);

        List<Group> groups = groupRepository.findAll();
        assertTrue(groups.size() >= 2);
    }
}
