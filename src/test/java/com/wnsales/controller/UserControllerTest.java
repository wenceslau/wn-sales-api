package com.wnsales.controller;

import com.wnsales.model.User;
import com.wnsales.service.UserService;
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

import java.util.Arrays;
import java.util.Optional;

@RunWith(SpringRunner.class)
@WebMvcTest(UserController.class)
class UserControllerTest {

    @Autowired
    private MockMvc mvc;

    @MockBean
    private UserService userService;

    @Test
    void givenUser_whenFindById_ThenReturnJsonElement() throws Exception {
        User usr1 = new User();
        usr1.setName("User1");
        Optional<User> optionalUser = Optional.of(usr1);

        BDDMockito.given(userService.findById(1L)).willReturn(optionalUser);

        mvc.perform(MockMvcRequestBuilders.get("/user/1")
                        .contentType(MediaType.APPLICATION_JSON))
                .andExpect(MockMvcResultMatchers.status().isOk())
                .andExpect(MockMvcResultMatchers.jsonPath("$.name").value("User1"));
    }

    @Test
    void givenUsers_whenList_ThenReturnPageJsonArray() throws Exception {
        User usr1 = new User();
        usr1.setName("User1");
        PageRequest pageRequest = PageRequest.of(1, 10);
        Page<User> pageUser = new PageImpl<>(Arrays.asList(usr1), pageRequest, 1);
        BDDMockito.given(userService.findAll(pageRequest)).willReturn(pageUser);

        mvc.perform(MockMvcRequestBuilders.get("/user?page=1&size=10")
                        .contentType(MediaType.APPLICATION_JSON))
                .andExpect(MockMvcResultMatchers.status().isOk());
    }

    @Test
    void givenUser_WhenSave_ThenReturnStatusOk() throws Exception {
        User usrReq = new User();
        usrReq.setName("User1");
        usrReq.setEmail("user1@user1");
        User usrResp = new User();
        usrResp.setId(1L);
        usrResp.setName("User1");
        BDDMockito.given(userService.save(usrReq)).willReturn(usrResp);

        mvc.perform(MockMvcRequestBuilders
                        .post("/user")
                        .content(Utils.objectToJson(usrReq))
                        .contentType(MediaType.APPLICATION_JSON))
                .andExpect(MockMvcResultMatchers.status().is2xxSuccessful())
                .andExpect(MockMvcResultMatchers.jsonPath("$.id").value(1L));

    }

    @Test
    void givenUser_WhenSaveWithoutEmail_ThenReturnStatusNoOk() throws Exception {
        User usrReq = new User();
        usrReq.setName("User1");
        User usrResp = new User();
        usrResp.setId(1L);
        usrResp.setName("User1");
        BDDMockito.given(userService.save(usrReq)).willReturn(usrResp);

        mvc.perform(MockMvcRequestBuilders
                        .post("/user")
                        .content(Utils.objectToJson(usrReq))
                        .contentType(MediaType.APPLICATION_JSON))
                .andExpect(MockMvcResultMatchers.status().is4xxClientError());
    }

    @Test
    void givenUser_WhenEdit_thenReturnStatusOk() throws Exception {

        User usrReq = new User();
        usrReq.setId(1L);
        usrReq.setName("UserChange");
        usrReq.setEmail("user1@user1");
        User usrResp = new User();
        usrResp.setId(1L);
        usrResp.setName("User1");
        BDDMockito.given(userService.edit(1l,usrReq)).willReturn(usrResp);

        mvc.perform(MockMvcRequestBuilders
                        .put("/user/1")
                        .content(Utils.objectToJson(usrReq))
                        .contentType(MediaType.APPLICATION_JSON))
                .andExpect(MockMvcResultMatchers.status().is2xxSuccessful())
                .andExpect(MockMvcResultMatchers.jsonPath("$.id").value(1L));
    }

    @Test
    void givenUser_WhenPartialEdit_thenReturnStatusOk() throws Exception {

        User usrReq = new User();
        usrReq.setId(1L);
        usrReq.setEmail("user_change@user1");
        User usrResp = new User();
        usrResp.setId(1L);
        usrResp.setName("User1");
        usrResp.setEmail("user_change@user1");
        BDDMockito.given(userService.partialEdit(1l,usrReq)).willReturn(usrResp);

        mvc.perform(MockMvcRequestBuilders
                        .patch("/user/1")
                        .content(Utils.objectToJson(usrReq))
                        .contentType(MediaType.APPLICATION_JSON))
                .andExpect(MockMvcResultMatchers.status().is2xxSuccessful())
                .andExpect(MockMvcResultMatchers.jsonPath("$.name").value("User1"));

    }
}