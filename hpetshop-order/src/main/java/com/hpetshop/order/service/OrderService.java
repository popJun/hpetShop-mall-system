package com.hpetshop.order.service;

import com.hpetshop.pojo.HpOrder;
import com.hpetshop.pojo.HpOrderItem;
import com.hpetshop.pojo.HpOrderShipping;
import com.hpetshop.utils.Result;

import java.util.List;

/**
 * 用于管理订单
 *
 * @ProjectName: hpetshop-parent
 * @Package: com.hpetshop.order.service
 * @Author: wushaochuan
 * @CreateDate: 2018/4/27 14:02
 * @UpdateUser:
 * @UpdateDate: 2018/4/27 14:02
 * @Version: 1.0
 **/
public interface OrderService {
    /**
     * 创建订单
     *
     * @param hpOrder 订单, hpOrderItemList 订单对应的商品列表, hpOrderShipping 物流信息
     * @return com.hpetshop.utils.Result
     * @author wushaochuan
     * @date 2018/4/27 14:09
     */
    Result createOrder(HpOrder hpOrder, List<HpOrderItem> hpOrderItemList, HpOrderShipping hpOrderShipping);

    /**
     * 根据用户id获得该用户所有订单
     *
     * @return com.hpetshop.utils.Result
     * @author wushaochuan
     * @date 2018/5/2 15:35
     */
    Result getOrder(Long userId);

    /**
     * 获得用户所有订单
     */
    Result getAllOrder(Integer page, Integer rows);

    /**
     * 通过订单id查询该订单下的商品
     */
    Result getOrderItem(String orderId, Integer page, Integer rows);

    /**
     * 订单发货处理
     */
    Result updateOrder(String ids);

    /**
     * 确认收货处理
     */
    Result updateOrderWithFina(String id);

    /**
     * 用于为订单提供物流信息
     */
    Result updateOrderByshippingName(String ids, String shippingName);
}
