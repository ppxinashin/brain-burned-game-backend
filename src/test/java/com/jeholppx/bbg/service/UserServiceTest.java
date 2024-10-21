package com.jeholppx.bbg.service;

import com.jeholppx.bbg.model.entity.User;
import com.jeholppx.bbg.utils.JWTUtils;
import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.Test;
import org.springframework.boot.test.context.SpringBootTest;

import javax.annotation.Resource;

/**
 * 用户服务测试
 *
 * @author <a href="https://www.jehol-ppx.com">热河fen青</a>
 * @date 2024/10/14 19:00
 */
@SpringBootTest
public class UserServiceTest {

    @Resource
    private UserService userService;

    @Test
    void userRegister() {
        String userAccount = "yupi";
        String userPassword = "";
        String checkPassword = "123456";
        try {
            long result = userService.userRegister(userAccount, userPassword, checkPassword);
            Assertions.assertEquals(-1, result);
            userAccount = "yu";
            result = userService.userRegister(userAccount, userPassword, checkPassword);
            Assertions.assertEquals(-1, result);
        } catch (Exception e) {

        }
    }

    @Test
    void userProduceToken() {
        User user = userService.getById(1);
        String token = JWTUtils.getter(user);
        System.out.println(token);
    }

    @Test
    void getUserFromToken() {
        String token = "eyJ0eXAiOiJKV1QiLCJhbGciOiJIUzI1NiJ9.eyJ1c2VyIjp7ImlkIjoxLCJ1c2VyQWNjb3VudCI6Inl1cGkiLCJ1c2VyUGFzc3dvcmQiOiJiMGRkMzY5N2ExOTI4ODVkN2MwNTVkYjQ2MTU1YjI2YSIsInVzZXJOYW1lIjoi6bG855quIiwidXNlckF2YXRhciI6Imh0dHBzOi8vay5zaW5haW1nLmNuL24vc2luYWtkMjAxMTAvNTYwL3cxMDgwaDEwODAvMjAyMzA5MzAvOTE1ZC1mM2Q3YjU4MGMzMzYzMmIxOTFlMTlhZmEwYTg1OGQzMS5qcGcvdzcwMGQxcTc1Y21zLmpwZyIsInVzZXJQcm9maWxlIjoi5qyi6L-O5p2l57yW56iL5a-86Iiq5a2m5LmgIiwidXNlclJvbGUiOiJhZG1pbiIsImNyZWF0ZVRpbWUiOjE3MTUyMjQzOTMsInVwZGF0ZVRpbWUiOjE3Mjk0MDc0MjQsImlzRGVsZXRlIjowfSwiZXhwIjoxNzMwMTE4MDYzfQ.GNjDPeerLoEt0BsyPOAUsxBb64gdnZx8IPgR6CFQHCw";
        JWTUtils.decode(token);
    }
}
