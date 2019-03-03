package com.hpetshop.service.impl;

import java.util.Date;
import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.hpetshop.mapper.HpItemParamMapper;
import com.hpetshop.pojo.HpItemParam;
import com.hpetshop.pojo.HpItemParamExample;
import com.hpetshop.pojo.HpItemParamExample.Criteria;
import com.hpetshop.service.ItemParamService;
import com.hpetshop.utils.Result;

/**
 * 
 * <ul>
 * <li>文件名称: ItemParamServiceImpl</li>
 * <li>文件描述:用作处理Hp_Item_Param表</li>
 * <li>版权所有: 版权所有(C) 2016</li>
 * <li>内容摘要:</li>
 * <li>其他说明:</li>
 * <li>创建日期:2018年3月5日</li>
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
public class ItemParamServiceImpl implements ItemParamService {
	@Autowired
	HpItemParamMapper hpItemParamMapper;

	/**
	 * 通过商品类别查找是否已经建立商品规格
	 * 
	 * @param ItemCatId
	 * @return
	 */
	@Override
	public Result findItemParamByItemCatId(Long ItemCatId) {
		HpItemParamExample example = new HpItemParamExample();
		Criteria criteria = example.createCriteria();
		criteria.andItemCatIdEqualTo(ItemCatId);
		List<HpItemParam> params = hpItemParamMapper.selectByExampleWithBLOBs(example);
		// 为了提交效率mybatis默认不加载大文本（text）要使用selectByExampleWithBLOBs才可以加载
		if (params != null && params.size() > 0) {
			return Result.ok(params.get(0));
		} else {
			return Result.ok();
		}
	}

	/**
	 * 用于插入到商品规格表中
	 * 
	 * @param hpItemParam
	 * @return
	 */
	@Override
	public Result insertItemParam(HpItemParam hpItemParam) {
		hpItemParam.setCreated(new Date());
		hpItemParam.setUpdated(new Date());
		hpItemParamMapper.insert(hpItemParam);
		return Result.ok();
	}

}
