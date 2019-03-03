package com.hpetshop.rest.service.Impl;

import java.util.List;

import org.apache.log4j.Logger;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Service;

import com.hpetshop.mapper.HpItemParamItemMapper;
import com.hpetshop.pojo.HpItemParamItem;
import com.hpetshop.pojo.HpItemParamItemExample;
import com.hpetshop.pojo.HpItemParamItemExample.Criteria;
import com.hpetshop.rest.dao.RedisClientDAO;
import com.hpetshop.rest.service.ItemParamItemService;
import com.hpetshop.utils.JsonUtils;
import com.hpetshop.utils.Result;
import com.hpetshop.utils.StringUtils;
/**
 * 
 * <ul>
 * <li>文件名称: ItemParamItemServiceImpl</li>
 * <li>文件描述:</li>
 * <li>版权所有: 版权所有(C) 2018</li>
 * <li>内容摘要:</li>
 * <li>其他说明:</li>
 * <li>创建日期:2018年3月31日</li>
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
	private static Logger log = Logger.getLogger(ItemParamItemService.class);
	@Autowired
	private HpItemParamItemMapper hpItemParamItemMapper;
	@Autowired
	private RedisClientDAO redisClientDAO;
	@Value("${REDIS_ITEM_KEY}")
	private String REDIS_ITEM_KEY;
	@Value("${REDIS_ITEM_EXPIRE}")
	private Long REDIS_ITEM_EXPIRE;
	/**
	 * 获取商品规格信息
	 * 
	 * @param itemId
	 * @return
	 */
	@Override
	public Result getItemParamItem(Long itemId) {
		StringBuffer sb = new StringBuffer();
		// 拼装redis命名规则:和数据库映射,表名:主键:用处:值
		sb.append(REDIS_ITEM_KEY).append(":").append(itemId).append(":param");
		// 从缓存中取商品信息
		try {
			String json = redisClientDAO.get(sb.toString());
			if (StringUtils.isNotEmpty(json)) {
				log.info(" 从缓存中取得的值:" + json);
				HpItemParamItem result = JsonUtils.jsonToPojo(json, HpItemParamItem.class);
				return Result.ok(result);
			}
		} catch (Exception e) {
			e.printStackTrace();
		}
		// 查看商品详情
		HpItemParamItemExample example = new HpItemParamItemExample();
		Criteria criteria = example.createCriteria();
		criteria.andItemIdEqualTo(itemId);
		List<HpItemParamItem> hpItemParamItemList = hpItemParamItemMapper.selectByExample(example);

		// 把商品放回缓存,设置key的过期时间--不能用hash应为不能设置过期时间
		try {
			redisClientDAO.set(sb.toString(), JsonUtils.objectToJson(hpItemParamItemList.get(0)));
			// 设置有效时间
			redisClientDAO.expire(sb.toString(), REDIS_ITEM_EXPIRE);
		} catch (Exception e) {
			e.printStackTrace();
		}
		return Result.ok(hpItemParamItemList.get(0));
	}
}
