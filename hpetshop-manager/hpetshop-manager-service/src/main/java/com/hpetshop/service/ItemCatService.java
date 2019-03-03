package com.hpetshop.service;

import com.hpetshop.dto.TypeNodeDTO;

import java.util.List;

/**
 * <ul>
 * <li>文件名称: ItemCatService</li>
 * <li>文件描述:读取商品类别</li>
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
public interface ItemCatService {
    /**
     * 用于读取类目类别
     */
    List<TypeNodeDTO> getItemCatList(Long parentId);
}
