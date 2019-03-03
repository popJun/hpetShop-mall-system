package com.hpetshop.order.service.impl;

import com.github.pagehelper.PageHelper;
import com.github.pagehelper.PageInfo;
import com.hpetshop.mapper.HpOrderItemMapper;
import com.hpetshop.mapper.HpOrderMapper;
import com.hpetshop.mapper.HpOrderShippingMapper;
import com.hpetshop.order.dao.RedisClientDAO;
import com.hpetshop.order.dto.AllPageDTO;
import com.hpetshop.order.dto.OrderDTO;
import com.hpetshop.order.dto.ShowOrderDTO;
import com.hpetshop.order.service.OrderService;
import com.hpetshop.pojo.HpOrder;
import com.hpetshop.pojo.HpOrderExample;
import com.hpetshop.pojo.HpOrderItem;
import com.hpetshop.pojo.HpOrderItemExample;
import com.hpetshop.pojo.HpOrderShipping;
import com.hpetshop.utils.Result;
import com.hpetshop.utils.StringUtils;

import org.joda.time.DateTime;
import org.springframework.beans.BeanUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.Date;
import java.util.List;

/**
 * 用于管理订单
 *
 * @ProjectName: hpetshop-parent
 * @Package: com.hpetshop.order.service.impl
 * @Author: wushaochuan
 * @CreateDate: 2018/4/27 14:11
 * @UpdateUser:
 * @UpdateDate: 2018/4/27 14:11
 * @Version: 1.0
 **/
@Service
public class OrderServiceImpl implements OrderService {
    @Autowired
    private HpOrderMapper hpOrderMapper;
    @Autowired
    private HpOrderItemMapper hpOrderItemMapper;
    @Autowired
    private HpOrderShippingMapper hpOrderShippingMapper;
    @Autowired
    private RedisClientDAO redisClientDAO;
    @Value("${ORDER_ID_KEY}")
    private String ORDER_ID_KEY;

    @Value("${ORDER_INIT_ID}")
    private String ORDER_INIT_ID;

    /**
     * 创建订单
     *
     * @param hpOrder 订单, hpOrderItemList 订单对应的商品列表, hpOrderShipping 物流信息
     * @return com.hpetshop.utils.Result
     * @author wushaochuan
     * @date 2018/4/27 14:09
     */
    @Override
    public Result createOrder(HpOrder hpOrder, List<HpOrderItem> hpOrderItemList, HpOrderShipping hpOrderShipping) {
        //向订单表插入数据
        String hasValue = redisClientDAO.get(ORDER_ID_KEY);//判断是否有值
        if (StringUtils.isEmpty(hasValue)) {
            redisClientDAO.set(ORDER_ID_KEY, ORDER_INIT_ID);
        }
        Long orderId = redisClientDAO.incr(ORDER_ID_KEY);
        hpOrder.setOrderId(String.valueOf(orderId));
        hpOrder.setStatus(1);//1未付款2已付款3未发货4已发货5交易成功6交易关闭
        hpOrder.setCreateTime(new Date());
        hpOrder.setUpdateTime(new Date());
        hpOrder.setBuyerRate(0);//1为评价0为未评价
        hpOrderMapper.insert(hpOrder);
        //向订单明细表插入数据
        for (int i = 0; i < hpOrderItemList.size(); i++) {
            HpOrderItem hpOrderItem = hpOrderItemList.get(i);
            hpOrderItem.setId(orderId + "" + i);//订单商品id为订单id+i的字符串
            hpOrderItem.setOrderId(orderId + "");
            try {
                hpOrderItemMapper.insert(hpOrderItem);//插入订单明细表
            } catch (Exception e) {
                e.printStackTrace();
            }
        }
        return Result.ok(orderId);
    }

    /**
     * 根据用户id获得该用户所有订单
     *
     * @return com.hpetshop.utils.Result
     * @author wushaochuan
     * @date 2018/5/2 15:35
     */
    @Override
    public Result getOrder(Long userId) {
        HpOrderExample orderExample = new HpOrderExample();
        HpOrderExample.Criteria criteria = orderExample.createCriteria();
        criteria.andUserIdEqualTo(userId);
        List<HpOrder> hpOrders = hpOrderMapper.selectByExample(orderExample);
        List<OrderDTO> orderDTOS = new ArrayList<OrderDTO>();
        for (int i = 0; i < hpOrders.size(); i++) {
            OrderDTO orderDTO = new OrderDTO();
            HpOrder hpOrder = hpOrders.get(i);
            orderDTO.setBuyerNick(hpOrder.getBuyerNick());
            orderDTO.setOrderId(hpOrder.getOrderId());
            orderDTO.setOrderDate(new DateTime(hpOrder.getCreateTime()).toString("yyyy-MM-dd"));
            orderDTO.setPayment(hpOrder.getPayment());
            orderDTO.setShippingName(hpOrder.getShippingName());
            List<HpOrderItem> hpOrderItem = getHpOrderItem(hpOrder.getOrderId());
            orderDTO.setOrderItems(hpOrderItem);
            if (hpOrder.getStatus() == 1) {
                orderDTO.setStatusName("未发货");
            } else if (hpOrder.getStatus() == 2) {
                orderDTO.setStatusName("已发货");
            } else if (hpOrder.getStatus() == 3) {
                orderDTO.setStatusName("交易成功");
            }
            orderDTOS.add(orderDTO);
        }
        return Result.ok(orderDTOS);
    }

    /**
     * 获取全部订单
     */
    @Override
    public Result getAllOrder(Integer page, Integer rows) {
        HpOrderExample hpOrderExample = new HpOrderExample();
        //设置分页
        PageHelper.startPage(page, rows);
        List<HpOrder> list = hpOrderMapper.selectByExample(hpOrderExample);
        List<ShowOrderDTO> showOrderDTOS = new ArrayList<ShowOrderDTO>();
        for (HpOrder hpOrder : list) {
            ShowOrderDTO showOrderDTO = new ShowOrderDTO();
            BeanUtils.copyProperties(hpOrder, showOrderDTO);//a赋值给b
            showOrderDTOS.add(showOrderDTO);
        }
        for (ShowOrderDTO showOrderDTO : showOrderDTOS) {
            if (showOrderDTO.getStatus() == 1) {//未发货
                showOrderDTO.setOrderStatus("未发货");
            } else if (showOrderDTO.getStatus() == 2) {//已发货
                showOrderDTO.setOrderStatus("已发货");
            } else if (showOrderDTO.getStatus() == 3) {//确认收货
                showOrderDTO.setOrderStatus("交易完成");
            }
            if (showOrderDTO.getPaymentType() == 1) {
                showOrderDTO.setOrderType("货到付款");
            } else if (showOrderDTO.getPaymentType() == 2) {
                showOrderDTO.setOrderType("在线支付");
            }
        }
        //取分页信息
        PageInfo<ShowOrderDTO> pageInfo = new PageInfo<>(showOrderDTOS);
        long total = pageInfo.getTotal();
        AllPageDTO result = new AllPageDTO();
        result.setRows(showOrderDTOS);
        result.setTotal(total);
        return Result.ok(result);
    }

    /**
     * 通过订单id查询该订单下的商品
     */
    @Override
    public Result getOrderItem(String orderId, Integer page, Integer rows) {
        HpOrderItemExample hpOrderItemExample = new HpOrderItemExample();
        HpOrderItemExample.Criteria criteria = hpOrderItemExample.createCriteria();
        criteria.andOrderIdEqualTo(orderId);
        //设置分页
        PageHelper.startPage(page, rows);
        List<HpOrderItem> orderItems = hpOrderItemMapper.selectByExample(hpOrderItemExample);
        PageInfo<HpOrderItem> hpOrderItemPageInfo = new PageInfo<HpOrderItem>(orderItems);
        long total = hpOrderItemPageInfo.getTotal();
        AllPageDTO result = new AllPageDTO();
        result.setRows(orderItems);
        result.setTotal(total);
        return Result.ok(result);
    }

    /**
     * 订单发货处理
     */
    @Override
    public Result updateOrder(String ids) {
        String[] idList = ids.split(",");
        for (String s : idList) {
            HpOrder hpOrder = getOrder(s);
            hpOrder.setStatus(2);
            HpOrderExample hpOrderExample = new HpOrderExample();
            HpOrderExample.Criteria criteria = hpOrderExample.createCriteria();
            criteria.andOrderIdEqualTo(s);
            hpOrderMapper.updateByExampleSelective(hpOrder, hpOrderExample);
        }
        return Result.ok();
    }

    /**
     * 确认收货处理
     */
    @Override
    public Result updateOrderWithFina(String id) {
        HpOrder hpOrder = getOrder(id);
        hpOrder.setStatus(3);
        HpOrderExample hpOrderExample = new HpOrderExample();
        HpOrderExample.Criteria criteria = hpOrderExample.createCriteria();
        criteria.andOrderIdEqualTo(id);
        hpOrderMapper.updateByExampleSelective(hpOrder, hpOrderExample);
        return Result.ok();
    }

    /**
     * 为订单提供物流信息
     */
    @Override
    public Result updateOrderByshippingName(String ids, String shippingName) {
        HpOrder hpOrder = getOrder(ids);
        hpOrder.setShippingName(shippingName);
        HpOrderExample hpOrderExample = new HpOrderExample();
        HpOrderExample.Criteria criteria = hpOrderExample.createCriteria();
        criteria.andOrderIdEqualTo(ids);
        hpOrderMapper.updateByExampleSelective(hpOrder, hpOrderExample);
        return Result.ok();
    }

    /**
     * 通过用户id查询所有订单
     */
    private HpOrder getOrder(String id) {
        HpOrder hpOrder = hpOrderMapper.selectByPrimaryKey(id);
        return hpOrder;
    }

    /**
     * 用于查询订单内商品
     *
     * @return java.util.List<com.hpetshop.pojo.HpOrderItem>
     * @author wushaochuan
     * @date 2018/5/2 16:27
     */
    private List<HpOrderItem> getHpOrderItem(String orderId) {
        HpOrderItemExample orderItemExample = new HpOrderItemExample();
        HpOrderItemExample.Criteria criteria = orderItemExample.createCriteria();
        criteria.andOrderIdEqualTo(orderId);
        List<HpOrderItem> orderItems = hpOrderItemMapper.selectByExample(orderItemExample);
        return orderItems;
    }
}
