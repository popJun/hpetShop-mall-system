package com.hpetshop.service.impl;

import com.hpetshop.dto.TypeNodeDTO;
import com.hpetshop.mapper.HpItemCatMapper;
import com.hpetshop.pojo.HpItemCat;
import com.hpetshop.pojo.HpItemCatExample;
import com.hpetshop.pojo.HpItemCatExample.Criteria;
import com.hpetshop.service.ItemCatService;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.List;

/**
 * <ul>
 * <li>文件名称: ItemCatServiceImpl</li>
 * <li>文件描述:读取商品类别</li>
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
@Service
public class ItemCatServiceImpl implements ItemCatService {
    @Autowired
    private HpItemCatMapper hpItemCatMapper;

    /**
     * 读取商品类别表ItemCatList
     */
    @Override
    public List<TypeNodeDTO> getItemCatList(Long parentId) {
        HpItemCatExample example = new HpItemCatExample();
        Criteria criteria = example.createCriteria();
        criteria.andParentIdEqualTo(parentId);
        List<HpItemCat> list = hpItemCatMapper.selectByExample(example);
        List<TypeNodeDTO> typeNodeDTOs = new ArrayList<TypeNodeDTO>();
        for (HpItemCat hpItemCat : list) {
            // 创建一个TypeNodeDTO对象
            TypeNodeDTO typeNodeDTO = new TypeNodeDTO();
            typeNodeDTO.setId(hpItemCat.getId());
            typeNodeDTO.setText(hpItemCat.getName());
            if (hpItemCat.getIsParent()) {
                typeNodeDTO.setState("closed");
            } else {
                typeNodeDTO.setState("open");
            }
            typeNodeDTOs.add(typeNodeDTO);
        }
        return typeNodeDTOs;
    }
}
