package com.startsteps.ecommerceapi.persistence;

import com.startsteps.ecommerceapi.model.User;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.orm.jpa.DataJpaTest;
import org.springframework.test.context.ActiveProfiles;
import org.springframework.test.context.junit.jupiter.SpringExtension;

import java.util.Optional;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertTrue;


@ActiveProfiles(profiles = "test")
@ExtendWith(SpringExtension.class)
@DataJpaTest
class UserRepositoryTest {
    @Autowired
    private UserRepository userRepository;
    @Test
    void testFindByUsername() {
        // given
        User user = new User();
        user.setUsername("test-username");
        user.setEmail("test-email");
        user.setPassword("test-password");
        userRepository.save(user);

        // when
        Optional<User> foundUser = userRepository.findByUsername("test-username");

        // then
        assertTrue(foundUser.isPresent());
        assertEquals(user, foundUser.get());
    }

    @Test
    void testFindByEmail() {
        // given
        User user = new User();
        user.setUsername("test-username");
        user.setEmail("test-email");
        user.setPassword("test-password");
        userRepository.save(user);

        // when
        Optional<User> foundUser = userRepository.findByEmail("test-email");

        // then
        assertTrue(foundUser.isPresent());
        assertEquals(user, foundUser.get());
    }

    @Test
    void testFindUserByUserId() {
        // given
        User user = new User();
        user.setUsername("test-username");
        user.setEmail("test-email");
        user.setPassword("test-password");
        userRepository.save(user);

        // when
        Optional<User> foundUser = userRepository.findUserByUserId(user.getUserId());

        // then
        assertTrue(foundUser.isPresent());
        assertEquals(user, foundUser.get());
    }

    @Test
    void testExistsByUsername() {
        // given
        User user = new User();
        user.setUsername("test-username");
        user.setEmail("test-email");
        user.setPassword("test-password");
        userRepository.save(user);

        // when
        boolean exists = userRepository.existsByUsername("test-username");

        // then
        assertTrue(exists);
    }

    @Test
    void testExistsByEmail() {
        // given
        User user = new User();
        user.setUsername("test-username");
        user.setEmail("test-email");
        user.setPassword("test-password");
        userRepository.save(user);

        // when
        boolean exists = userRepository.existsByEmail("test-email");

        // then
        assertTrue(exists);
    }
}