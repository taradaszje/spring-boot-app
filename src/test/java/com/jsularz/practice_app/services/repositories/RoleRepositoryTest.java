package com.jsularz.practice_app.services.repositories;

import com.jsularz.practice_app.TestObjectFactory;
import com.jsularz.practice_app.models.Role;
import com.jsularz.practice_app.models.RoleType;
import org.assertj.core.api.WithAssertions;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.test.context.junit4.SpringRunner;

import javax.annotation.Resource;

@RunWith(value = SpringRunner.class)
public class RoleRepositoryTest extends AbstractRepositoryTest implements WithAssertions {

    @Resource
    private RoleRepository roleRepository;

    @Test
    public void shouldFindByName(){
        //given
        final Role role = TestObjectFactory.createRole(RoleType.SITE_USER);
        //testEntityManager.persist(role); only 3 roles in app, it is forbidden to create new one
        //when
        final Role found = roleRepository.findByName(role.getName());
        //then
        assertThat(found.getName()).isEqualTo(role.getName());
        assertThat(found.getDescription()).isEqualTo(role.getDescription());
    }

    @Test
    public void shouldNotFindByNameIfNameNotExists(){
        //given
        final Role role = new Role();
        role.setName("RANDOM_ROLE");
        //when
        final Role found = roleRepository.findByName(role.getName());
        //then
        assertThat(found).isNull();
    }
}
