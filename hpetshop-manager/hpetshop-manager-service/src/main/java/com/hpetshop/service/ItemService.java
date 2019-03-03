package com.hpetshop.service;

import com.hpetshop.dto.AllPageDTO;
import com.hpetshop.pojo.HpItem;
import com.hpetshop.utils.Result;

/**
 * <ul>
 * <li>文件名称: ItemService</li>
 * <li>文件描述:用于处理商品的增删改查</li>
 * <li>版权所有: 版权所有(C) 2016</li>
 * <li>内容摘要:</li>
 * <li>其他说明:</li>
 * <li>创建日期:2018年3月1日</li>
 * </ul>
 * <ul>
 * <li>修改记录:</li>
 * <li>版 本 号:</li>
 * <li>修改日期:</li>
 * <li>修 改 人:</li>
 * <li>修改内容:</li>
 * </ul>
 *
 * @author wushaochuan
 * @version 1.0.0
 */
public interface ItemService {
    /**
     * 用于itemId查询一条数据
     *
     * @param itemId 物品id
     */
    HpItem getItemById(long itemId);

    /**
     * @param page 当前页面
     * @param row  每页几行
     */
    AllPageDTO getItemList(int page, int row);

    /**
     * 用于保存商品表和商品规格
     */
    Result saveItem(HpItem hpItem, String desc, String itemParams);

    /**
     * 根据id删除删除商品
     *
     * @param hpItemId 商品id
     */
    Result deleteItem(Long hpItemId);

    /**
     * 用于编辑物品
     */
    Result updateItem(HpItem hpItem, String itemParam, String desc);

    /**
     * 用于物品的上架和下架
     */
    Result updateItem(Long hpItemId, Byte status);

    /**
     * 根据hpItemId取商品描述
     */
    Result getItemDesc(Long hpItemId);

}
