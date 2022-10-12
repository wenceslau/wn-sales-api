package com.wnsales.controller;

import com.wnsales.model.Account;
import com.wnsales.model.dto.AccountDTO;
import com.wnsales.service.AccountService;
import com.wnsales.util.Utils;
import org.junit.jupiter.api.Test;
import org.junit.runner.RunWith;
import org.mockito.BDDMockito;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.WebMvcTest;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageImpl;
import org.springframework.data.domain.PageRequest;
import org.springframework.http.MediaType;
import org.springframework.test.context.junit4.SpringRunner;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.test.web.servlet.request.MockMvcRequestBuilders;
import org.springframework.test.web.servlet.result.MockMvcResultMatchers;

import java.util.List;
import java.util.Optional;

@RunWith(SpringRunner.class)
@WebMvcTest(AccountController.class)
class AccountControllerTest {

    @Autowired
    private MockMvc mvc;

    @MockBean
    private AccountService accountService;

    @Test
    void givenAccount_whenFindById_ThenReturnJsonElement() throws Exception {
        Account acc1 = new Account();
        acc1.setAccountName("Account1");
        Optional<Account> optionalAccount = Optional.of(acc1);

        BDDMockito.given(accountService.findById(1L)).willReturn(optionalAccount);

        mvc.perform(MockMvcRequestBuilders.get("/account/1")
                        .contentType(MediaType.APPLICATION_JSON))
                .andExpect(MockMvcResultMatchers.status().isOk())
                .andExpect(MockMvcResultMatchers.jsonPath("$.accountName").value("Account1"));
    }

    @Test
    void givenAccounts_whenList_ThenReturnPageJsonArray() throws Exception {
        Account acc1 = new Account();
        acc1.setAccountName("Account1");

        PageRequest pageRequest = PageRequest.of(1, 10);
        Page<Account> pageAccount = new PageImpl<>(List.of(acc1), pageRequest, 1);

        BDDMockito.given(accountService.findAll(pageRequest)).willReturn(pageAccount);

        mvc.perform(MockMvcRequestBuilders.get("/account?page=1&size=10")
                        .contentType(MediaType.APPLICATION_JSON))
                .andExpect(MockMvcResultMatchers.status().isOk());
    }

    @Test
    void givenAccount_WhenSave_ThenReturnStatusOk() throws Exception {
        AccountDTO accDTO = new AccountDTO();
        accDTO.setAccountName("Account1");
        accDTO.setIban("PT50003800015623391998072");
        accDTO.setUserId(1L);

        Account accResp = new Account();
        accResp.setId(1L);
        accResp.setAccountName("Account1");
        accResp.setIban("PT50003800015623391998072");

        BDDMockito.given(accountService.save(Account.of(accDTO))).willReturn(accResp);

        mvc.perform(MockMvcRequestBuilders
                        .post("/account")
                        .content(Utils.objectToJson(accDTO))
                        .contentType(MediaType.APPLICATION_JSON))
                .andExpect(MockMvcResultMatchers.status().is2xxSuccessful())
                .andExpect(MockMvcResultMatchers.jsonPath("$.id").value(1L));

    }

    @Test
    void givenAccount_WhenSaveWithWrongIban_ThenReturnStatusNoOk() throws Exception {
        Account accReq = new Account();
        accReq.setAccountName("Account1");

        Account accResp = new Account();
        accResp.setId(1L);
        accResp.setAccountName("Account1");

        BDDMockito.given(accountService.save(accReq)).willReturn(accResp);

        mvc.perform(MockMvcRequestBuilders
                        .post("/account")
                        .content(Utils.objectToJson(accReq))
                        .contentType(MediaType.APPLICATION_JSON))
                .andExpect(MockMvcResultMatchers.status().is4xxClientError());
    }

    @Test
    void givenAccount_WhenEdit_thenReturnStatusOk() throws Exception {

        AccountDTO accDTO = new AccountDTO();
        accDTO.setAccountName("AccountChanged");
        accDTO.setIban("PT50003800015623391998072");
        accDTO.setUserId(1L);

        Account accResp = new Account();
        accResp.setId(1L);
        accResp.setAccountName("AccountChanged");
        accResp.setIban("PT50003800015623391998072");

        BDDMockito.given(accountService.edit(1L,Account.of(accDTO))).willReturn(accResp);

        mvc.perform(MockMvcRequestBuilders
                        .put("/account/1")
                        .content(Utils.objectToJson(accDTO))
                        .contentType(MediaType.APPLICATION_JSON))
                .andExpect(MockMvcResultMatchers.status().is2xxSuccessful())
                .andExpect(MockMvcResultMatchers.jsonPath("$.accountName").value("AccountChanged"));
    }

    @Test
    void givenAccount_WhenPartialEdit_thenReturnStatusOk() throws Exception {

        Account accReq = new Account();
        accReq.setAccountName("AccountChanged");

        Account accResp = new Account();
        accResp.setId(1L);
        accResp.setAccountName("AccountChanged");
        accResp.setIban("PT50003800015623391998072");

        BDDMockito.given(accountService.partialEdit(1L,accReq)).willReturn(accResp);

        mvc.perform(MockMvcRequestBuilders
                        .patch("/account/1")
                        .content(Utils.objectToJson(accReq))
                        .contentType(MediaType.APPLICATION_JSON))
                .andExpect(MockMvcResultMatchers.status().is2xxSuccessful())
                .andExpect(MockMvcResultMatchers.jsonPath("$.iban").value("PT50003800015623391998072"));

    }
}