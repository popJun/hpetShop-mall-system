package com.hpetshop.order.dto;

import com.hpetshop.pojo.HpOrder;
import com.hpetshop.pojo.HpOrderItem;
import com.hpetshop.pojo.HpOrderShipping;

import java.util.List;

/**
 * 用于接收前台json串
 *
 * @ProjectName: hpetshop-parent
 * @Package: com.hpetshop.order.dto
 * @Author: wushaochuan
 * @CreateDate: 2018/4/27 14:59
 * @UpdateUser:
 * @UpdateDate: 2018/4/27 14:59
 * @Version: 1.0
 **/
public class OrderDTO extends HpOrder {
    private List<HpOrderItem> orderItems;
    private HpOrderShipping orderShipping;//名字和json串上的要一致
    private String orderDate;//订单时间
    private String statusName;//状态名

    public String getStatusName() {
        return statusName;
    }

    public void setStatusName(String statusName) {
        this.statusName = statusName;
    }

    public String getOrderDate() {
        return orderDate;
    }

    public void setOrderDate(String orderDate) {
        this.orderDate = orderDate;
    }

    public List<HpOrderItem> getOrderItems() {
        return orderItems;
    }

    public void setOrderItems(List<HpOrderItem> orderItems) {
        this.orderItems = orderItems;
    }

    public HpOrderShipping getOrderShipping() {
        return orderShipping;
    }

    public void setOrderShipping(HpOrderShipping orderShipping) {
        this.orderShipping = orderShipping;
    }
}
