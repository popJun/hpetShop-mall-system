package com.hpetshop.service;

import com.hpetshop.dto.AllPageDTO;
import com.hpetshop.utils.Result;

/**
 * 用于对订单的修改
 *
 * @ProjectName: hpetshop-parent
 * @Package: com.hpetshop.service
 * @Author: wushaochuan
 * @CreateDate: 2018/5/7 10:19
 * @UpdateUser:
 * @UpdateDate: 2018/5/7 10:19
 * @Version: 1.0
 **/
public interface OrderService {
    /**
     * 获得所有订单
     */
    AllPageDTO getAllOrder(Integer page, Integer rows);

    /**
     * 用于获取订单详情
     */
    AllPageDTO getOrderItem(String orderId, Integer page, Integer rows);

    /**
     * 用于商品上架
     */
    Result updateOrder(String ids);

    /**
     * 用于商品物流
     */
    Result updateOrderByShippingName(String ids, String shippingName);

}
