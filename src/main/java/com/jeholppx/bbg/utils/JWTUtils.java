package com.jeholppx.bbg.utils;

import cn.hutool.json.JSONUtil;
import cn.hutool.jwt.JWT;
import cn.hutool.jwt.JWTUtil;
import cn.hutool.jwt.signers.JWTSigner;
import cn.hutool.jwt.signers.JWTSignerUtil;
import com.jeholppx.bbg.common.ErrorCode;
import com.jeholppx.bbg.exception.BusinessException;
import com.jeholppx.bbg.model.entity.User;
import org.apache.commons.lang3.StringUtils;
import org.springframework.data.redis.core.RedisTemplate;
import org.springframework.stereotype.Component;

import javax.annotation.Resource;
import java.util.Calendar;
import java.util.concurrent.TimeUnit;

/**
 * JWT 工具
 *
 * @author <a href="https://www.jehol-ppx.com">热河fen青</a>
 * @base HuTool
 * @date 2024/10/21 16:48
 */
@Component
public class JWTUtils {

    /**
     * hs256签名
     */
    private static final byte[] SIGN = "JEHOLFENQ".getBytes();

    private static RedisTemplate<String, Object> redisTemplate;

    @Resource
    private void setRedisTemplate(RedisTemplate<String, Object> redisTemplate) {
        JWTUtils.redisTemplate = redisTemplate;
    }

    /**
     * 默认过期时长 单位：天
     */
    private static final int EXPIRATION_DATE = 3;

    /**
     * 生成token，并存储用户信息到Redis
     *
     * @param user
     * @return
     */
    public static String getter(User user) {
        Calendar instance = Calendar.getInstance();
        instance.add(Calendar.DATE, EXPIRATION_DATE);
        // 创建jwt对象
        JWT jwt = JWT.create();
        // 设置payload
        user.setUserPassword(null);
        String jsonStr = JSONUtil.toJsonStr(user);
        jwt.setPayload("user", jsonStr);
        // 设置过期时间并签名，此签名并不安全，请勿在分布式架构的项目中使用
        JWTSigner signer = JWTSignerUtil.hs256(SIGN);
        String token = jwt.setExpiresAt(instance.getTime())
                .sign(signer);
        // 存储到Redis
        redisTemplate.opsForValue().set(token, jsonStr, EXPIRATION_DATE, TimeUnit.DAYS);
        return token;
    }

    /**
     * 修改token里的用户信息
     *
     * @param token
     * @param user
     */
    public static void setter(String token, User user) {
        // 先判断token是否有效，无效直接要求重新登录
        boolean invalid = !JWTUtil.verify(token, JWTSignerUtil.hs256(SIGN));
        if (invalid) {
            redisTemplate.opsForValue().getAndDelete(token);
            throw new BusinessException(ErrorCode.FORBIDDEN_ERROR, "请重新登录");
        }
        // 获取token过期时间，并求出剩余时间
        long expires = (long) ((int) JWTUtil.parseToken(token).getPayload(JWT.EXPIRES_AT));
        long current = System.currentTimeMillis() / 1000L;
        long rest = expires - current;
        // 更新token中的信息
        user.setUserPassword(null);
        redisTemplate.opsForValue().set(token, JSONUtil.toJsonStr(user), rest, TimeUnit.SECONDS);
    }

    /**
     * 解析token
     *
     * @param token
     * @return
     */
    public static User decode(String token) throws BusinessException {
        // 先判断token是否有效，无效直接要求重新登录
        boolean invalid = !JWTUtil.verify(token, JWTSignerUtil.hs256(SIGN));
        if (invalid) {
            redisTemplate.opsForValue().getAndDelete(token);
            throw new BusinessException(ErrorCode.FORBIDDEN_ERROR, "请重新登录");
        }
        // 解析token
        String userJsonStr = (String) redisTemplate.opsForValue().get(token);
        if (StringUtils.isBlank(userJsonStr)) {
            throw new BusinessException(ErrorCode.FORBIDDEN_ERROR, "请重新登录");
        }
        return JSONUtil.toBean(userJsonStr, User.class);
    }


    /**
     * 移除token
     *
     * @param token
     */
    public static void remove(String token) {
        redisTemplate.opsForValue().getAndDelete(token);
    }


}
