package com.hpetshop.service.impl;

import java.lang.reflect.InvocationTargetException;
import java.lang.reflect.Method;
import java.util.ArrayList;
import java.util.Date;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import org.apache.log4j.Logger;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.hpetshop.dto.CMSTreeNodeDTO;
import com.hpetshop.mapper.HpContentCategoryMapper;
import com.hpetshop.pojo.HpContentCategory;
import com.hpetshop.pojo.HpContentCategoryExample;
import com.hpetshop.pojo.HpContentCategoryExample.Criteria;
import com.hpetshop.service.ContentcategoryService;
import com.hpetshop.utils.Result;

/**
 * 
 * <ul>
 * <li>文件名称: ContentcategoryServiceImpl</li>
 * <li>文件描述:对于首页的栏目异步树管理:不完善之处应该在删除和重命名是先判断该对象是否存在</li>
 * <li>版权所有: 版权所有(C) 2016</li>
 * <li>内容摘要:</li>
 * <li>其他说明:</li>
 * <li>创建日期:2018年3月7日</li>
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
public class ContentcategoryServiceImpl implements ContentcategoryService {
	private static Logger log = Logger.getLogger(ContentcategoryService.class);
	@Autowired
	private HpContentCategoryMapper hpContentCategoryMapper;

	/**
	 * 用于显示首页异步树管理
	 * 
	 * @param parentid
	 * @return
	 */
	@Override
	public List<CMSTreeNodeDTO> getContentcategroy(Long parentid) {
		HpContentCategoryExample example = new HpContentCategoryExample();
		Criteria criteria = example.createCriteria();
		criteria.andParentIdEqualTo(parentid);
		List<HpContentCategory> contentCategories = hpContentCategoryMapper.selectByExample(example);
		List<CMSTreeNodeDTO> resultList = new ArrayList<CMSTreeNodeDTO>();
		for (HpContentCategory category : contentCategories) {
			CMSTreeNodeDTO cmsTreeNodeDTO = new CMSTreeNodeDTO();
			cmsTreeNodeDTO.setId(category.getId());
			cmsTreeNodeDTO.setText(category.getName());
			if (category.getStatus() == 1) {
				cmsTreeNodeDTO.setState("closed");
			} else {
				cmsTreeNodeDTO.setState("open");
			}
			log.info("返回的子节点为：" + cmsTreeNodeDTO.toString());
			resultList.add(cmsTreeNodeDTO);
		}
		log.info("返回的list为：" + resultList);
		return resultList;
	}

	/**
	 * 新增cms系统中异步树的栏目
	 * 
	 * @param parentId
	 * @param name
	 * @return
	 */
	@Override
	public Result insertContentcategroy(long parentId, String name) {
		HpContentCategory category = new HpContentCategory();
		category.setName(name);// 类目名称
		category.setCreated(new Date());// 创建时间为当前时间
		category.setUpdated(new Date());// 更新时间为当前时间
		category.setParentId(parentId);// 设置父节点
		category.setStatus(0);// 设置状态1为文件夹0为文件
		category.setSortOrder(1);// 设置排序序号相同则按名称排序
		category.setIsParent(false);// 是否为父类目，由于是新建类目所以不是父类目
		hpContentCategoryMapper.insert(category);
		log.info("id值为" + category.getId());
		updateContentcategroy(category);

		return Result.ok(category);
	}
	/**
	 * 用于将父类目的参数（是否有父类目）改为true
	 * 
	 * @param category
	 */
	private void updateContentcategroy(HpContentCategory category) {
		HpContentCategory category1 = new HpContentCategory();
		HpContentCategoryExample categoryExample = new HpContentCategoryExample();
		Criteria criteria = categoryExample.createCriteria();
		criteria.andIdEqualTo(category.getParentId());
		category1.setIsParent(true);
		category1.setStatus(1);
		hpContentCategoryMapper.updateByExampleSelective(category1, categoryExample);
	}
	/**
	 * 重命名异步树的类目名称
	 * 
	 * @param id
	 * @param name
	 * @return
	 */
	@Override
	public Result updateContentcategroyName(long id, String name) {
		HpContentCategory category1 = new HpContentCategory();
		HpContentCategoryExample categoryExample = new HpContentCategoryExample();
		Criteria criteria = categoryExample.createCriteria();
		category1.setName(name);
		criteria.andIdEqualTo(id);
		hpContentCategoryMapper.updateByExampleSelective(category1, categoryExample);
		return Result.ok();
	}
	/**
	 * 删除异步树的类目
	 * 
	 * @param parentId
	 * @param id
	 * @return
	 */
	@SuppressWarnings({"unchecked", "rawtypes"})
	@Override
	public Result deleteContentcategroy(long id) {
		HpContentCategory category = hpContentCategoryMapper.selectByPrimaryKey(id);
		hpContentCategoryMapper.deleteByPrimaryKey(id);
		deleteContentcategroyByparentId(id);
		Map map = new HashMap();
		map.put("methodName", "ParentId");
		map.put("param", category.getParentId());
		List list = new ArrayList();// 拼装成方法和参数的字符串
		list.add(map);
		List<HpContentCategory> hpContentCategories = null;
		hpContentCategories = (List<HpContentCategory>) getContentcategories(list);

		if (hpContentCategories.isEmpty()) {
			updateContentcategroy(category.getParentId());
		}
		return Result.ok();
	}
	/**
	 * 删除该类目下所有的子类目
	 * 
	 * @param id
	 * @return
	 */
	private void deleteContentcategroyByparentId(long id) {
		HpContentCategoryExample categoryExample = new HpContentCategoryExample();
		Criteria criteria = categoryExample.createCriteria();
		criteria.andParentIdEqualTo(id);
		hpContentCategoryMapper.deleteByExample(categoryExample);

	}
	/**
	 * 利用java的反射机制查询该条件下的参数//改进方法利用自定义注解的方法改进
	 * 
	 * @map 不定参数条件 /里面包含 methodName 方法名 param 方法参数
	 * @return
	 * @throws InvocationTargetException
	 * @throws IllegalArgumentException
	 * @throws IllegalAccessException
	 * @throws InstantiationException
	 */
	@SuppressWarnings({"unchecked", "rawtypes"})
	private List<?> getContentcategories(List<Map> maps) {
		HpContentCategoryExample categoryExample = new HpContentCategoryExample();
		Criteria criteria = categoryExample.createCriteria();
		Class criteriaClazz = criteria.getClass();
		for (Map map : maps) {
			String methodName = (String) map.get("methodName");
			Object param = map.get("param");
			methodName = "and" + methodName + "EqualTo";
			try {

				Method method = criteriaClazz.getMethod(methodName, param.getClass());
				method.setAccessible(true);
				method.invoke(criteria, param);
			} catch (IllegalAccessException | SecurityException | NoSuchMethodException | IllegalArgumentException | InvocationTargetException e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
			}
		}
		List hpContentCategories = hpContentCategoryMapper.selectByExample(categoryExample);
		return hpContentCategories;
	}
	/**
	 * 用于将父类目的参数（是否有父类目）改为false
	 * 
	 * @param parentId
	 */
	private void updateContentcategroy(Long parentId) {
		HpContentCategory category1 = new HpContentCategory();
		HpContentCategoryExample categoryExample = new HpContentCategoryExample();
		Criteria criteria = categoryExample.createCriteria();
		criteria.andIdEqualTo(parentId);
		category1.setIsParent(false);
		category1.setStatus(0);
		hpContentCategoryMapper.updateByExampleSelective(category1, categoryExample);
	}
}
