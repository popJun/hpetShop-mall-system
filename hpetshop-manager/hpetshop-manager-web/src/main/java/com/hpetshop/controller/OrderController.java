package com.hpetshop.controller;

import com.hpetshop.dto.AllPageDTO;
import com.hpetshop.service.OrderService;
import com.hpetshop.utils.Result;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.ResponseBody;

/**
 * @ProjectName: hpetshop-parent
 * @Package: com.hpetshop.controller
 * @Author: wushaochuan
 * @CreateDate: 2018/5/7 10:54
 * @UpdateUser:
 * @UpdateDate: 2018/5/7 10:54
 * @Version: 1.0
 **/
@Controller
@RequestMapping("order")
public class OrderController {
    @Autowired
    private OrderService orderService;

    @RequestMapping("/getAll")
    @ResponseBody
    public AllPageDTO getAllOrder(@RequestParam(defaultValue = "1") Integer page, @RequestParam(defaultValue = "30") Integer rows) {
        AllPageDTO allOrder = orderService.getAllOrder(page, rows);
        return allOrder;
    }

    /**
     * 通过订单id查询该订单下的商品
     */
    @RequestMapping("/getOrderItem/{orderId}")
    @ResponseBody
    public AllPageDTO getOrderItem(@PathVariable String orderId, @RequestParam(defaultValue = "1") Integer page, @RequestParam(defaultValue = "30") Integer rows) {
        AllPageDTO allOrderItem = orderService.getOrderItem(orderId, page, rows);
        return allOrderItem;
    }

    /**
     * 用于发货
     */
    @RequestMapping("/updateOrder")
    @ResponseBody
    public Result updateOrder(String ids) {
        Result result = orderService.updateOrder(ids);
        return result;
    }

    /**
     * 用于提供物流
     */
    @RequestMapping("updateOrderByshippingName")
    @ResponseBody
    public Result updateOrderByshippingName(String ids, String shippingName) {
        Result result = orderService.updateOrderByShippingName(ids, shippingName);
        return result;
    }
}
