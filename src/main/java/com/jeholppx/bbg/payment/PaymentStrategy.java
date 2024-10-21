package com.jeholppx.bbg.payment;

import com.jeholppx.bbg.model.entity.App;
import com.jeholppx.bbg.model.entity.User;

import java.math.BigDecimal;

/**
 * 支付策略接口
 *
 * @author <a href="https://www.jehol-ppx.com">热河fen青</a>
 * @date 2024/10/20 16:21
 */
public interface PaymentStrategy {


    /**
     * 执行支付
     *
     * @param amount
     * @param credit
     * @param app
     * @param user
     * @return
     * @throws Exception
     */
    Boolean doPay(BigDecimal amount, Integer credit, App app, User user) throws Exception;
}
