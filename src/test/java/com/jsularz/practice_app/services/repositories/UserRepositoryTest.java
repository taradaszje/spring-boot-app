package com.jsularz.practice_app.services.repositories;

import com.jsularz.practice_app.models.User;
import org.assertj.core.api.WithAssertions;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.test.context.junit4.SpringRunner;

import javax.annotation.Resource;
import java.util.Optional;

import static com.jsularz.practice_app.TestObjectFactory.createUser;

@RunWith(value = SpringRunner.class)
public class UserRepositoryTest extends AbstractRepositoryTest implements WithAssertions {

    @Resource
    private UserRepository userRepository;

    @Test
    public void shouldFindByEmail(){
        //given
        final User user = createUser();
        user.setEmail("jarek@gmail.com");
        testEntityManager.persist(user);

        //when
        final Optional<User> found = userRepository.findByEmail(user.getEmail());

        //then
        assertThat(found.get()).isEqualToComparingFieldByField(user);

    }

    @Test
    public void shouldNotFindByEmailIfEmailNotExists(){
        //given
        final User user = createUser();
        user.setEmail("jaro@gmail.com");
        final String email = "niejaro@gmail.com";
        testEntityManager.persist(user);

        //when
        final Optional<User> found = userRepository.findByEmail(email);

        //then
        assertThat(found).isEqualTo(Optional.empty());
    }
}
