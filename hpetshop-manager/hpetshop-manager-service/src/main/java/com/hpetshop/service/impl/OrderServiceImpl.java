package com.hpetshop.service.impl;

import com.hpetshop.dto.AllPageDTO;
import com.hpetshop.service.OrderService;
import com.hpetshop.utils.HttpClientUtil;
import com.hpetshop.utils.Result;

import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Service;

/**
 * 订单相关操作
 *
 * @ProjectName: hpetshop-parent
 * @Package: com.hpetshop.service.impl
 * @Author: wushaochuan
 * @CreateDate: 2018/5/7 10:21
 * @UpdateUser:
 * @UpdateDate: 2018/5/7 10:21
 * @Version: 1.0
 **/
@Service
public class OrderServiceImpl implements OrderService {
    @Value("${ORDER_BASE_URL}")
    private String ORDER_BASE_URL;//订单基础URL
    @Value("${ORDER_GETALL_URL}")
    private String ORDER_GETALL_URL;//获取所有订单的URL
    @Value("${ORDER_GETORDERITME_URL}")
    private String ORDER_GETORDERITME_URL;//用于获取订单详情的URL
    @Value("${ORDER_UPDATEORDER_URL}")
    private String ORDER_UPDATEORDER_URL;//订单发货URL
    @Value("${OEDER_UPDATESHIPPING_URL}")
    private String OEDER_UPDATESHIPPING_URL;//订单物流URL

    /**
     * 用于获得所有订单
     */
    @Override
    public AllPageDTO getAllOrder(Integer page, Integer rows) {
        AllPageDTO allPageDTO = null;
        String json = HttpClientUtil.doGet(ORDER_BASE_URL + ORDER_GETALL_URL + "?page=" + page + "&rows=" + rows);
        Result result = Result.formatToPojo(json, AllPageDTO.class);
        if (result.getStatus().intValue() == 200) {//主要Integer和Int类型比较也是为false
            allPageDTO = (AllPageDTO) result.getData();
        }
        return allPageDTO;
    }

    /**
     * 用于显示订单详情
     */
    @Override
    public AllPageDTO getOrderItem(String orderId, Integer page, Integer rows) {
        AllPageDTO allPageDTO = null;
        String json = HttpClientUtil.doGet(ORDER_BASE_URL + ORDER_GETORDERITME_URL + "/" + orderId + "?page=" + page + "&rows=" + rows);
        Result result = Result.formatToPojo(json, AllPageDTO.class);
        if (result.getStatus().intValue() == 200) {//主要Integer和Int类型比较也是为false
            allPageDTO = (AllPageDTO) result.getData();
        }
        return allPageDTO;
    }

    /**
     * 用于发货
     */
    @Override
    public Result updateOrder(String ids) {
        String json = HttpClientUtil.doGet(ORDER_BASE_URL + ORDER_UPDATEORDER_URL + "/" + ids);
        Result result = Result.format(json);
        return result;
    }

    /**
     * 用于物流发货
     */
    @Override
    public Result updateOrderByShippingName(String ids, String shippingName) {
        String json = HttpClientUtil.doGet(ORDER_BASE_URL + OEDER_UPDATESHIPPING_URL + "/" + ids + "?shippingName=" + shippingName);
        Result result = Result.format(json);
        return result;
    }
}