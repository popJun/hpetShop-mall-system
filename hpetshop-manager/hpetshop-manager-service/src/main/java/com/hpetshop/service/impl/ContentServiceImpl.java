package com.hpetshop.service.impl;

import com.github.pagehelper.PageHelper;
import com.github.pagehelper.PageInfo;
import com.hpetshop.dto.AllPageDTO;
import com.hpetshop.mapper.HpContentMapper;
import com.hpetshop.pojo.HpContent;
import com.hpetshop.pojo.HpContentExample;
import com.hpetshop.pojo.HpContentExample.Criteria;
import com.hpetshop.service.ContentService;
import com.hpetshop.utils.HttpClientUtil;
import com.hpetshop.utils.Result;

import org.joda.time.DateTime;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Service;

import java.util.List;

/**
 * <ul>
 * <li>文件名称: ContentServiceImpl</li>
 * <li>文件描述: 对首页的内容进行管理</li>
 * <li>版权所有: 版权所有(C) 2016</li>
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
    @Autowired
    HpContentMapper hpContentMapper;
    @Value("${REDIS_BASE}")
    private String REDIS_BASE;// 注释文件读出的url基础
    @Value("${REDIS_CONTENT}")
    private String REDIS_CONTENT;// 注释文件读出的类目url

    /**
     * 通过内容类目的id查询内容
     *
     * @param page       当前页数
     * @param row        每页条数
     * @param categoryId 类目id
     */
    @Override
    public AllPageDTO getContent(int page, int row, Long categoryId) {
        HpContentExample hpContentExample = new HpContentExample();
        Criteria criteria = hpContentExample.createCriteria();
        criteria.andCategoryIdEqualTo(categoryId);
        PageHelper pageHepler = new PageHelper();
        pageHepler.startPage(page, row);
        List<HpContent> contents = hpContentMapper.selectByExampleWithBLOBs(hpContentExample);// withBLBS才能查到大文本内容
        PageInfo<HpContent> pageInfo = new PageInfo<HpContent>(contents);// 可以获得分页的一些信息比如总页数，
        AllPageDTO allPageDTO = new AllPageDTO();
        allPageDTO.setRows(contents);
        allPageDTO.setTotal(pageInfo.getTotal());
        return allPageDTO;
    }

    /**
     * 用于保存内容
     */
    @Override
    public Result saveContent(HpContent hpContent) {
        hpContent.setCreated(new DateTime().toDate());
        hpContent.setUpdated(new DateTime().toDate());
        hpContentMapper.insertSelective(hpContent);
        sycnRedis(hpContent);
        return Result.ok();
    }

    /**
     * 用于删除内容
     */
    @Override
    public Result deleteContent(Long id) {
        HpContent hpContent = hpContentMapper.selectByPrimaryKey(id);
        hpContentMapper.deleteByPrimaryKey(id);
        sycnRedis(hpContent);
        return Result.ok();
    }

    /**
     * 用于更新内容
     */
    @Override
    public Result updateContent(HpContent hpContent) {
        hpContent.setUpdated(new DateTime().toDate());
        hpContentMapper.updateByPrimaryKeyWithBLOBs(hpContent);
        sycnRedis(hpContent);
        return Result.ok();
    }

    /**
     * 用于同步缓存
     */
    private void sycnRedis(HpContent hpContent) {
        StringBuffer syncRedisUrl = new StringBuffer();
        syncRedisUrl.append(REDIS_BASE);
        syncRedisUrl.append(REDIS_CONTENT);
        syncRedisUrl.append(hpContent.getCategoryId());
        HttpClientUtil.doGet(syncRedisUrl.toString());// 调用系统服务做同步redis的操作
    }
}
