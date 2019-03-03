package com.hpetshop.rest.service.Impl;

import org.apache.log4j.Logger;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Service;

import com.hpetshop.mapper.HpItemMapper;
import com.hpetshop.pojo.HpItem;
import com.hpetshop.rest.dao.RedisClientDAO;
import com.hpetshop.rest.service.ItemService;
import com.hpetshop.utils.JsonUtils;
import com.hpetshop.utils.Result;
import com.hpetshop.utils.StringUtils;
/**
 * 
 * <ul>
 * <li>文件名称: ItemServiceImpl</li>
 * <li>文件描述:用于对Item的操作</li>
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
public class ItemServiceImpl implements ItemService {
	private static Logger log = Logger.getLogger(ItemService.class);
	@Autowired
	private HpItemMapper hpItemMapper;
	@Autowired
	private RedisClientDAO redisClientDAO;
	@Value("${REDIS_ITEM_KEY}")
	private String REDIS_ITEM_KEY;// redis基础的命名
	@Value("${REDIS_ITEM_EXPIRE}")
	private long REDIS_ITEM_EXPIRE;// redis的过期时间
	/**
	 * 获得商品详情
	 * 
	 * @param itemId
	 * @return
	 */
	@Override
	public Result getItem(long itemId) {
		StringBuffer sb = new StringBuffer();
		// 拼装redis命名规则:和数据库映射,表名:主键:用处:值
		sb.append(REDIS_ITEM_KEY).append(":").append(itemId).append(":base");
		// 从缓存中取商品信息
		try {
			String json = redisClientDAO.get(sb.toString());
			if (StringUtils.isNotEmpty(json)) {
				log.info(" 从缓存中取得的值:" + json);
				HpItem hpItem = JsonUtils.jsonToPojo(json, HpItem.class);
				return Result.ok(hpItem);
			}
		} catch (Exception e) {
			e.printStackTrace();
		}
		// 查看商品详情
		HpItem hpItem = hpItemMapper.selectByPrimaryKey(itemId);

		// 把商品放回缓存,设置key的过期时间--不能用hash应为不能设置过期时间
		try {
			redisClientDAO.set(sb.toString(), JsonUtils.objectToJson(hpItem));
			// 设置有效时间
			redisClientDAO.expire(sb.toString(), REDIS_ITEM_EXPIRE);
		} catch (Exception e) {
			e.printStackTrace();
		}
		return Result.ok(hpItem);
	}

}
