package com.hpetshop.rest.service.Impl;

import org.apache.log4j.Logger;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Service;

import com.hpetshop.mapper.HpItemDescMapper;
import com.hpetshop.pojo.HpItemDesc;
import com.hpetshop.rest.dao.RedisClientDAO;
import com.hpetshop.rest.service.ItemDescService;
import com.hpetshop.utils.JsonUtils;
import com.hpetshop.utils.Result;
import com.hpetshop.utils.StringUtils;
/**
 * 
 * <ul>
 * <li>文件名称: ItemDescServiceImpl</li>
 * <li>文件描述:对商品描述表进行操作</li>
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
public class ItemDescServiceImpl implements ItemDescService {
	private Logger log = Logger.getLogger(ItemDescService.class);
	@Autowired
	private HpItemDescMapper hpItemDescMapper;
	@Autowired
	private RedisClientDAO redisClientDAO;
	@Value("${REDIS_ITEM_KEY}")
	private String REDIS_ITEM_KEY;
	@Value("${REDIS_ITEM_EXPIRE}")
	private Long REDIS_ITEM_EXPIRE;
	/**
	 * 获得商品详情
	 * 
	 * @param itemId
	 * @return
	 */
	@Override
	public Result getItemDesc(long itemId) {
		StringBuffer sb = new StringBuffer();
		// 拼装redis命名规则:和数据库映射,表名:主键:用处:值
		sb.append(REDIS_ITEM_KEY).append(":").append(itemId).append(":desc");
		// 从缓存中取商品信息
		try {
			String json = redisClientDAO.get(sb.toString());
			log.info("redis中获取的数据" + json);
			if (StringUtils.isNotEmpty(json)) {
				HpItemDesc hpItemDesc = JsonUtils.jsonToPojo(json, HpItemDesc.class);
				return Result.ok(hpItemDesc);
			}
		} catch (Exception e) {
			e.printStackTrace();
		}
		// 取出商品描述
		HpItemDesc hpItemDesc = hpItemDescMapper.selectByPrimaryKey(itemId);
		log.info("hpItemDesc" + hpItemDesc.getItemDesc());
		// 为商品描述添加缓存

		try {
			redisClientDAO.set(sb.toString(), JsonUtils.objectToJson(hpItemDesc));
			// 设置有效时间
			redisClientDAO.expire(sb.toString(), REDIS_ITEM_EXPIRE);
		} catch (Exception e) {
			e.printStackTrace();
		}
		return Result.ok(hpItemDesc);
	}
}
