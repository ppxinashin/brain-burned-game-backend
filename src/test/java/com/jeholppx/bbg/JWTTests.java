package com.jeholppx.bbg;

import com.jeholppx.bbg.model.entity.User;
import com.jeholppx.bbg.utils.JWTUtils;
import org.junit.jupiter.api.Test;

import java.util.Calendar;
import java.util.HashMap;

public class JWTTests {
    @Test
    public void contextLoads() {

        HashMap<String, Object> map = new HashMap<>();

        Calendar instance = Calendar.getInstance();
        // 1小时后令牌token失效
        instance.add(Calendar.HOUR,1);

//        String token = JWT.create()
//                .setPayload("sub", 1)
//                .setExpiresAt(instance.getTime())
//                .sign(JWTSignerUtil.hs256("jehol_fenq".getBytes()));

//        String token = "eyJ0eXAiOiJKV1QiLCJhbGciOiJIUzI1NiJ9.eyJzdWIiOjEsImV4cCI6MTcyOTUxNzI4OH0.hmoBMS1LCw1s7nKRi_y_zbMyoBPBWkEEWEMwk_1R9hE";
//        System.out.println(token);
//
//        if (JWTUtil.verify(token, JWTSignerUtil.hs256("jehol_fenq".getBytes()))) {
//            final JWT jwt = JWTUtil.parseToken(token);
//            long expires = (long) ((int) jwt.getPayload(JWT.EXPIRES_AT));
//            long current = System.currentTimeMillis() / 1000L;
//            System.out.println("expires: " + expires);
//            System.out.println("current: " + current);
//            System.out.println("expires: " + (expires - current) / 60 + "分钟后过期");
//        }
        String token = "";
        User decode = JWTUtils.decode(token);
        System.out.println(decode);

    }

}
