package com.wnsales.service.impl;

import com.wnsales.model.Account;
import com.wnsales.repository.AccountRepository;
import com.wnsales.service.AccountService;
import org.junit.jupiter.api.Test;
import org.junit.runner.RunWith;
import org.mockito.BDDMockito;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.AutoConfigureMockMvc;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageImpl;
import org.springframework.data.domain.PageRequest;
import org.springframework.test.context.TestPropertySource;
import org.springframework.test.context.junit4.SpringRunner;

import java.util.Arrays;
import java.util.Optional;

import static org.assertj.core.api.Assertions.assertThat;
import static org.junit.jupiter.api.Assertions.*;

@SpringBootTest
@TestPropertySource(locations = "classpath:application-integrationtest.properties")
class AccountServiceImplTest {

    @MockBean
    private AccountRepository accountRepository;

    @Autowired
    private AccountService accountService;

    @Test
    void givenAccount_whenFindAll_thenShouldBeFound() {
        Account usr1 = new Account();
        usr1.setAccountName("Account1");

        PageRequest pageRequest = PageRequest.of(1, 10);
        Page<Account> pageAccount = new PageImpl<>(Arrays.asList(usr1), pageRequest, 1);

        BDDMockito.given(accountRepository.findAll(pageRequest)).willReturn(pageAccount);

        assertThat(pageAccount.getTotalElements()).isGreaterThan(0);

    }

    @Test
    void givenAccount_whenFindById_thenShouldBeFound() {

        Account usr1 = new Account();
        usr1.setAccountName("Account1");
        Optional<Account> optionalAccount = Optional.of(usr1);

        BDDMockito.given(accountRepository.findById(1L)).willReturn(optionalAccount);

        assertThat(optionalAccount.isPresent()).isTrue();
    }

    @Test
    void givenAccount_whenSave_thenShouldBeCreate() {
        Account account = new Account();
        account.setAccountName("AccountA");
        account.setId(1L);

        BDDMockito.given(accountRepository.save(BDDMockito.any(Account.class))).willReturn(account);

        assertThat(account.getId()).isEqualTo(1L);
    }

    @Test
    void givenAccount_whenSave_thenShouldBeEdit() {
        Account account = new Account();
        account.setAccountName("account");
        account.setId(1L);

        BDDMockito.given(accountRepository.save(BDDMockito.any(Account.class))).willReturn(account);

        assertThat(account.getAccountName()).isEqualTo("account");
    }

}