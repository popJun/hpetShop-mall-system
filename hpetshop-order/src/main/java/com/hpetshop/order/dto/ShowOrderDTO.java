package com.hpetshop.order.dto;

import com.hpetshop.pojo.HpOrder;

/**
 * 用于前台订单展示的DTO
 *
 * @ProjectName: hpetshop-parent
 * @Package: com.hpetshop.dto
 * @Author: wushaochuan
 * @CreateDate: 2018/5/7 18:18
 * @UpdateUser:
 * @UpdateDate: 2018/5/7 18:18
 * @Version: 1.0
 **/
public class ShowOrderDTO extends HpOrder {
    private String orderType;//订单类型
    private String orderStatus;//订单状态

    public String getOrderType() {
        return orderType;
    }

    public void setOrderType(String orderType) {
        this.orderType = orderType;
    }

    public String getOrderStatus() {
        return orderStatus;
    }

    public void setOrderStatus(String orderStatus) {
        this.orderStatus = orderStatus;
    }
}
