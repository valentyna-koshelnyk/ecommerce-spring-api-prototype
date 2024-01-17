//package com.startsteps.ecommerceapi.user.controller;
//
//import com.startsteps.ecommerceapi.user.payload.request.SignupRequest;
//import com.startsteps.ecommerceapi.user.service.UserServiceImpl;
//import org.junit.jupiter.api.Test;
//import org.junit.runner.RunWith;
//import org.mockito.Mockito;
//import org.springframework.beans.factory.annotation.Autowired;
//import org.springframework.boot.test.autoconfigure.web.servlet.WebMvcTest;
//import org.springframework.http.MediaType;
//import org.springframework.test.context.junit4.SpringRunner;
//import org.springframework.test.web.servlet.MockMvc;
//import org.springframework.test.web.servlet.request.MockMvcRequestBuilders;
//import org.springframework.test.web.servlet.result.MockMvcResultHandlers;
//import org.springframework.test.web.servlet.result.MockMvcResultMatchers;
//
//import static org.hibernate.cfg.JdbcSettings.URL;
//import static org.junit.jupiter.api.Assertions.*;
//@RunWith(SpringRunner.class)
//@WebMvcTest(value = AuthController.class)
//
//class AuthControllerTest {
//
//    @Autowired
//    private MockMvc mockMvc;
//
//    @Autowired
//    private UserServiceImpl userService;
//
//    private static final String registrationUrl = "/register";
//    private static final String loginUrl = "/login";
//
//    /** input validation for registration
//     *
//     */
//
//    public void whenRegisterEmpty_shouldFail() throws Exception {
//        //given
//
//        SignupRequest signupRequest = new SignupRequest(
//                "",
//                "",
//                "",
//                "");
//
//    }
//
//
//    public void testInvalidUsername() throws Exception {
//        Mockito.when(userService.registerUser(Mockito.eq("user"), Mockito.eq("user"))).thenReturn("secret");
//
//        String request;
//
//        request = "{ \"username\": null, \"password\": \"user\" }";
//        mockMvc.perform(MockMvcRequestBuilders.post(URL).content(request).contentType(MediaType.APPLICATION_JSON_UTF8))
//                .andDo(MockMvcResultHandlers.print())
//                .andExpect(MockMvcResultMatchers.status().isBadRequest());
//
//        request = "{ \"username\": \"\", \"password\": \"user\" }";
//        mockMvc.perform(MockMvcRequestBuilders.post(URL).content(request).contentType(MediaType.APPLICATION_JSON_UTF8))
//                .andDo(MockMvcResultHandlers.print())
//                .andExpect(MockMvcResultMatchers.status().isBadRequest());
//
//        request = "{ \"username\": \"AAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAA\", \"password\": \"user\" }";
//        mockMvc.perform(MockMvcRequestBuilders.post(URL).content(request).contentType(MediaType.APPLICATION_JSON_UTF8))
//                .andDo(MockMvcResultHandlers.print())
//                .andExpect(MockMvcResultMatchers.status().isBadRequest());
//    }
//
//
//
//
//
//
//
//}