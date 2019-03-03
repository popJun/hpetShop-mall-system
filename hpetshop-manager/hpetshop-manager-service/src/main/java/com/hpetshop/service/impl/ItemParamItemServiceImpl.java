package com.hpetshop.service.impl;

import java.util.List;
import java.util.Map;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.hpetshop.mapper.HpItemParamItemMapper;
import com.hpetshop.pojo.HpItemParamItem;
import com.hpetshop.pojo.HpItemParamItemExample;
import com.hpetshop.pojo.HpItemParamItemExample.Criteria;
import com.hpetshop.service.ItemParamItemService;
import com.hpetshop.utils.JsonUtils;

/**
 * 
 * <ul>
 * <li>文件名称: ItemParamItemServiceImpl</li>
 * <li>文件描述:用于对ItemParamItem表商品和商品规格映射表进行操作</li>
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
public class ItemParamItemServiceImpl implements ItemParamItemService {

	@Autowired
	private HpItemParamItemMapper hpItemParamItemMapper;

	/**
	 * 用于显示商品规格
	 * 
	 * @param itemId
	 * @return
	 */
	@Override
	public String getItemParamItemById(Long itemId) {
		HpItemParamItemExample example = new HpItemParamItemExample();
		Criteria criteria = example.createCriteria();
		criteria.andItemIdEqualTo(itemId);
		List<HpItemParamItem> list = hpItemParamItemMapper.selectByExampleWithBLOBs(example);
		if (list == null || list.size() == 0) {
			return "";
		} else {
			HpItemParamItem hpItemParamItem = list.get(0);
			String paramData = hpItemParamItem.getParamData();
			List<Map> JsonParam = JsonUtils.jsonToList(paramData, Map.class);
			StringBuffer sb = new StringBuffer();
			// 拆封json并拼装html对象
			sb.append("<div class=\"Ptable-item\"><p> ");
			for (Map map1 : JsonParam) {// 进行取规格头
				sb.append("  <h3> ");
				sb.append(map1.get("group"));
				sb.append("</h3>");
				List<Map> list2 = (List<Map>) map1.get("params");
				for (Map map2 : list2) {
					sb.append("  <dl> ");
					sb.append("    <dt> ");
					sb.append(map2.get("k"));
					sb.append("    </dt>");
					sb.append("     <dd> ");
					sb.append(map2.get("v"));
					sb.append("    </dd>");
					sb.append("  </dl> ");
				}
			}
			sb.append("</p></div> ");
			return sb.toString();
		}

	}
}
