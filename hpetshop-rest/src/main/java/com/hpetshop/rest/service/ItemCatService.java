package com.hpetshop.rest.service;

import com.hpetshop.rest.dto.CatResultDTO;
import com.hpetshop.utils.Result;

/**
 * <ul>
 * <li>文件名称: ItemCatService</li>
 * <li>文件描述:用于处理商品分类</li>
 * <li>版权所有: 版权所有(C) 2016</li>
 * <li>内容摘要:</li>
 * <li>其他说明:</li>
 * <li>创建日期:2018年3月6日</li>
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
public interface ItemCatService {
    /**
     * 获取分类列表
     */
    CatResultDTO getCatList();

    /**
     * 获得该分类下所有商品列表
     */
    Result getCatItemList1(Long id);
}
