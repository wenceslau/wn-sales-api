package com.wnsales.service.impl;

import com.wnsales.controller.AccountController;
import com.wnsales.model.User;
import com.wnsales.repository.UserRepository;
import com.wnsales.service.PaymentService;
import com.wnsales.service.UserService;
import org.assertj.core.api.Assertions;
import org.junit.Before;
import org.junit.jupiter.api.Test;
import org.junit.runner.RunWith;
import org.mockito.BDDMockito;
import org.mockito.Mockito;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.orm.jpa.DataJpaTest;
import org.springframework.boot.test.autoconfigure.web.servlet.WebMvcTest;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.boot.test.context.TestConfiguration;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.context.annotation.Bean;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageImpl;
import org.springframework.data.domain.PageRequest;
import org.springframework.test.context.TestPropertySource;
import org.springframework.test.context.junit4.SpringRunner;

import java.util.Arrays;
import java.util.Optional;

import static org.assertj.core.api.Assertions.assertThat;

@SpringBootTest
@TestPropertySource(locations = "classpath:application-integrationtest.properties")
class UserServiceImplTest {

    @MockBean
    private UserRepository userRepository;

    @Autowired
    private UserService userService;

    @Test
    void givenUser_whenFindAll_thenShouldBeFound() {
        User usr1 = new User();
        usr1.setName("User1");

        PageRequest pageRequest = PageRequest.of(1, 10);
        Page<User> pageUser = new PageImpl<>(Arrays.asList(usr1), pageRequest, 1);

        BDDMockito.given(userRepository.findAll(pageRequest)).willReturn(pageUser);

        assertThat(pageUser.getTotalElements()).isGreaterThan(0);

    }

    @Test
    void givenUser_whenFindById_thenShouldBeFound() {

        User usr1 = new User();
        usr1.setName("User1");
        Optional<User> optionalUser = Optional.of(usr1);

        BDDMockito.given(userRepository.findById(1L)).willReturn(optionalUser);

        assertThat(optionalUser.isPresent()).isTrue();
    }

    @Test
    void givenUser_whenSave_thenShouldBeCreate() {
        User user = new User();
        user.setName("UserA");
        user.setId(1L);

        BDDMockito.given(userRepository.save(BDDMockito.any(User.class))).willReturn(user);

        assertThat(user.getId()).isEqualTo(1L);
    }

    @Test
    void givenUser_whenSave_thenShouldBeEdit() {
        User user = new User();
        user.setName("user");
        user.setId(1L);

        BDDMockito.given(userRepository.save(BDDMockito.any(User.class))).willReturn(user);

        assertThat(user.getName()).isEqualTo("user");
    }

}