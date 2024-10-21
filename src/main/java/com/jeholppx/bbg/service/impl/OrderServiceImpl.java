package com.jeholppx.bbg.service.impl;

import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import com.jeholppx.bbg.model.entity.Order;
import com.jeholppx.bbg.service.OrderService;
import com.jeholppx.bbg.mapper.OrderMapper;
import org.springframework.stereotype.Service;

/**
* @author billz
* @description 针对表【order】的数据库操作Service实现
* @createDate 2024-10-20 16:51:34
*/
@Service
public class OrderServiceImpl extends ServiceImpl<OrderMapper, Order>
    implements OrderService{

}




