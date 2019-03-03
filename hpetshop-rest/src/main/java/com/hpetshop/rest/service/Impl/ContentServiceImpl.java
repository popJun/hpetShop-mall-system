package com.hpetshop.rest.service.Impl;

import java.util.List;

import org.apache.log4j.Logger;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Service;

import com.hpetshop.mapper.HpContentMapper;
import com.hpetshop.pojo.HpContent;
import com.hpetshop.pojo.HpContentExample;
import com.hpetshop.pojo.HpContentExample.Criteria;
import com.hpetshop.rest.dao.RedisClientDAO;
import com.hpetshop.rest.service.ContentService;
import com.hpetshop.utils.JsonUtils;
import com.hpetshop.utils.StringUtils;

/**
 * 
 * <ul>
 * <li>文件名称: ContentServiceImpl</li>
 * <li>文件描述:用于对分类内容进行处理</li>
 * <li>版权所有: 版权所有(C) 2016</li>
 * <li>公 司: 厦门市中软件科技有限公司</li>
 * <li>内容摘要:</li>
 * <li>其他说明:</li>
 * <li>创建日期:2018年3月15日</li>
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
public class ContentServiceImpl implements ContentService {
	private static Logger log = Logger.getLogger(ContentService.class);
	// 和jdk中 Resources的区别在于Resources是byName只有找不到才byType
	// 而spring中 Autowired是ByType
	@Autowired
	HpContentMapper hpContentMapper;
	@Autowired
	RedisClientDAO redisClientDAO;
	@Value("${INDEX_CONTENT_REDIS_KEY}")
	private String INDEX_CONTENT_REDIS_KEY;
	/**
	 * 用于利用categroyId查询分类内容
	 * 
	 * @param categoryId
	 * @return
	 */
	@Override
	public List<HpContent> getContent(Long categoryId) {
		// 从缓存中取数据
		try {
			String result = redisClientDAO.hget(INDEX_CONTENT_REDIS_KEY, String.valueOf(categoryId));// 对redis进行命中
			log.info("已经从redis取出数据" + result);
			if (StringUtils.isNotEmpty(result)) {
				List resultList = JsonUtils.jsonToList(result, HpContent.class);
				return resultList;
			}
		} catch (Exception e) {// 使用缓存不能影响正常的业务逻辑所以要抛异常
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		HpContentExample contentExample = new HpContentExample();
		Criteria criteria = contentExample.createCriteria();
		criteria.andCategoryIdEqualTo(categoryId);
		List<HpContent> hpContent = hpContentMapper.selectByExampleWithBLOBs(contentExample);
		// 存数据到redis中
		String cacheString = JsonUtils.objectToJson(hpContent);
		try {
			// 用Hset可更好避免redis中存储的混乱
			redisClientDAO.hset(INDEX_CONTENT_REDIS_KEY, String.valueOf(categoryId), cacheString);
		} catch (Exception e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		return hpContent;
	}
}
