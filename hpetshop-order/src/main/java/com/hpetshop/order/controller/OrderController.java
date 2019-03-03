package com.hpetshop.order.controller;

import com.hpetshop.order.dto.OrderDTO;
import com.hpetshop.order.service.OrderService;
import com.hpetshop.utils.ExceptionUtil;
import com.hpetshop.utils.Result;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.ResponseBody;

/**
 * 用于对订单处理的Controller
 *
 * @ProjectName: hpetshop-parent
 * @Package: com.hpetshop.order.controller
 * @Author: wushaochuan
 * @CreateDate: 2018/4/27 14:54
 * @UpdateUser:
 * @UpdateDate: 2018/4/27 14:54
 * @Version: 1.0
 **/
@Controller
public class OrderController {
    @Autowired
    private OrderService orderService;

    /**
     * @return com.hpetshop.utils.Result
     * @author wushaochuan
     * @date 2018/4/27 14:55
     */
    @RequestMapping(value = "/create", method = RequestMethod.POST)
    @ResponseBody
    public Result createOrder(@RequestBody OrderDTO order) {
        try {
            Result result = orderService.createOrder(order, order.getOrderItems(), order.getOrderShipping());
            return result;
        } catch (Exception e) {
            return Result.error(500, ExceptionUtil.getStackTrace(e));
        }
    }

    /**
     * 根据用户id获得该用户所有订单
     *
     * @return com.hpetshop.utils.Result
     * @author wushaochuan
     * @date 2018/5/2 15:35
     */
    @RequestMapping("/get")
    @ResponseBody
    public Result getOrder(String userId) {
        try {
            Result result = orderService.getOrder(Long.valueOf(userId));
            return result;
        } catch (Exception e) {
            return Result.error(500, ExceptionUtil.getStackTrace(e));
        }
    }

    /**
     * 提供接口获取所有的订单信息
     *
     * @return com.hpetshop.utils.Result
     * @author wushaochuan
     * @date 2018/5/7 10:35
     */
    @RequestMapping("/getAll")
    @ResponseBody
    public Result getAllOrder(Integer page, Integer rows) {
        try {
            Result result = orderService.getAllOrder(page, rows);
            return result;
        } catch (Exception e) {
            return Result.error(500, ExceptionUtil.getStackTrace(e));
        }
    }

    /**
     * 通过订单id查询该订单下的商品
     */
    @RequestMapping("/getOrderItem/{orderId}")
    @ResponseBody
    public Result getOrderItem(@PathVariable String orderId, Integer page, Integer rows) {
        try {
            Result result = orderService.getOrderItem(orderId, page, rows);
            return result;
        } catch (Exception e) {
            return Result.error(500, ExceptionUtil.getStackTrace(e));
        }
    }

    /**
     * 用于发货处理
     */
    @RequestMapping("/updateOrder/{ids}")
    @ResponseBody
    public Result updateOrder(@PathVariable String ids) {
        try {
            Result result = orderService.updateOrder(ids);
            return result;
        } catch (Exception e) {
            return Result.error(500, ExceptionUtil.getStackTrace(e));
        }
    }

    /**
     * 确认收货处理
     */
    @RequestMapping("/updateOrderWithFina/{id}")
    @ResponseBody
    public Result updateOrderWithFina(@PathVariable String id) {
        try {
            Result result = orderService.updateOrderWithFina(id);
            return result;
        } catch (Exception e) {
            return Result.error(500, ExceptionUtil.getStackTrace(e));
        }
    }

    /**
     * 用于提供物流单号
     */
    @RequestMapping("/updateOrderByshippingName/{ids}")
    @ResponseBody
    public Result updateOrderByshippingName(@PathVariable String ids, String shippingName) {
        try {
            shippingName = new String(shippingName.getBytes("iso8859-1"), "utf-8");
            Result result = orderService.updateOrderByshippingName(ids, shippingName);
            return result;
        } catch (Exception e) {
            return Result.error(500, ExceptionUtil.getStackTrace(e));
        }
    }
}
