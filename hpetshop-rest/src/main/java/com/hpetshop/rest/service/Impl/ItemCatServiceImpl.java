package com.hpetshop.rest.service.Impl;

import com.hpetshop.mapper.HpItemCatMapper;
import com.hpetshop.mapper.HpItemMapper;
import com.hpetshop.pojo.HpItem;
import com.hpetshop.pojo.HpItemCat;
import com.hpetshop.pojo.HpItemCatExample;
import com.hpetshop.pojo.HpItemCatExample.Criteria;
import com.hpetshop.pojo.HpItemExample;
import com.hpetshop.rest.dao.RedisClientDAO;
import com.hpetshop.rest.dto.CatNodeDTO;
import com.hpetshop.rest.dto.CatResultDTO;
import com.hpetshop.rest.service.ItemCatService;
import com.hpetshop.utils.JsonUtils;
import com.hpetshop.utils.Result;
import com.hpetshop.utils.StringUtils;

import org.apache.log4j.Logger;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.List;

/**
 * <ul>
 * <li>文件名称: ItemCatServiceImpl</li>
 * <li>文件描述:用于处理商品分类</li>
 * <li>版权所有: 版权所有(C) 2016</li>
 * <li>内容摘要:</li>
 * <li>其他说明:</li>
 * <li>创建日期:2018年3月6日</li>
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
    private static Logger log = Logger.getLogger(ItemCatService.class);
    @Autowired
    HpItemCatMapper hpItemCatMapper;
    @Autowired
    RedisClientDAO redisClientDAO;
    @Autowired
    HpItemMapper hpItemMapper;
    @Value("${INDEX_ITEMCAT_REDIS_KEY}")
    private String INDEX_ITEMCAT_REDIS_KEY;

    /**
     * 用于返回分类信息
     */
    @Override
    public CatResultDTO getCatList() {
        CatResultDTO catResult = new CatResultDTO();
        // 查询并封装成相应格式
        catResult.setData(getItemCatList((long) 0));
        return catResult;
    }

    /**
     * 获得该分类下所有商品的列表
     * 改进方案：加入redis减少对数据库的访问
     * 分页。
     */
    @Override
    public Result getCatItemList1(Long id) {
        List<HpItem> hpItems = new ArrayList<HpItem>();
        getCatItemList(id, hpItems);
        return Result.ok(hpItems);
    }

    /**
     * 递归读取分类内容
     */
    private void getCatItemList(Long id, List<HpItem> hpItems) {//也需放入redis
        HpItemCat hpItemCat = hpItemCatMapper.selectByPrimaryKey(id);
        if (hpItemCat.getIsParent()) {
            HpItemCatExample hpItemCatExample = new HpItemCatExample();
            Criteria criteria = hpItemCatExample.createCriteria();
            criteria.andParentIdEqualTo(id);
            List<HpItemCat> hpItemCats = hpItemCatMapper.selectByExample(hpItemCatExample);
            for (HpItemCat itemCat : hpItemCats) {//递归判断
                getCatItemList(itemCat.getId(), hpItems);
            }//循环结束返回结果
        } else {
            HpItemExample example = new HpItemExample();
            HpItemExample.Criteria criteria = example.createCriteria();
            criteria.andCidEqualTo(hpItemCat.getId());
            List<HpItem> hpItems1 = hpItemMapper.selectByExample(example);
            hpItems.addAll(hpItems1);
        }
    }

    /**
     * 查找HpItemCat类目表的数据
     */
    private List<HpItemCat> getItemCatList(Long parentId) {
        String result = "";
        List<HpItemCat> list = null;
        try {
            // 从redis中取数据
            result = redisClientDAO.hget(INDEX_ITEMCAT_REDIS_KEY, String.valueOf(parentId));
            log.info("已经从redis中取出数据" + result);
        } catch (Exception e1) {
            // TODO Auto-generated catch block
            e1.printStackTrace();
        }
        if (StringUtils.isEmpty(result)) {
            // 创建查询条件
            HpItemCatExample example = new HpItemCatExample();
            Criteria criteria = example.createCriteria();
            criteria.andParentIdEqualTo(parentId);
            // 执行查询
            list = hpItemCatMapper.selectByExample(example);
        } else {
            list = JsonUtils.jsonToList(result, HpItemCat.class);
        }
        // 存数据到redis中
        String cacheString = JsonUtils.objectToJson(list);
        try {
            // 用Hset可更好避免redis中存储的混乱
            redisClientDAO.hset(INDEX_ITEMCAT_REDIS_KEY, String.valueOf(parentId), cacheString);
        } catch (Exception e) {
            // TODO Auto-generated catch block
            e.printStackTrace();
        }
        List resultList = new ArrayList();
        int count = 0;
        for (HpItemCat itemCat : list) {
            if (itemCat.getIsParent()) {// 判断是否为父节点
                CatNodeDTO catNodeDTO = new CatNodeDTO();
                count++;
                if (itemCat.getParentId() == 0) {// 是否为顶端节点
                    StringBuffer parentTopsb = new StringBuffer();
                    parentTopsb.append("<a href='/carList/");
                    parentTopsb.append(itemCat.getId());
                    parentTopsb.append(".html?cat=");
                    parentTopsb.append(itemCat.getName());
                    parentTopsb.append("'>");
                    parentTopsb.append(itemCat.getName());
                    parentTopsb.append("</a>");
                    log.info("顶端节点的name为" + parentTopsb.toString());
                    catNodeDTO.setName(parentTopsb.toString());

                } else {
                    catNodeDTO.setName(itemCat.getName());
                }
                StringBuffer parentURLsb = new StringBuffer();// 拼装父节点的url
                parentURLsb.append("/carList/");
                parentURLsb.append(itemCat.getId());
                parentURLsb.append(".html?cat=");
                parentURLsb.append(itemCat.getName());
                log.info("父节点的url为" + parentURLsb.toString());
                catNodeDTO.setUrl(parentURLsb.toString());
                // 递归调用封装该顶端节点的所有子节点和子子节点并加入List，itemCat.getId()将此id作为parentid来寻找子节点
                catNodeDTO.setItem(getItemCatList(itemCat.getId()));
                resultList.add(catNodeDTO);
                log.info("父resultList" + resultList.toString());
            } else {
                StringBuffer childUrlsb = new StringBuffer();
                childUrlsb.append("/carList/");
                childUrlsb.append(itemCat.getId());
                childUrlsb.append(".html?cat=");
                childUrlsb.append(itemCat.getName());
                childUrlsb.append("|");
                childUrlsb.append(itemCat.getName());
                log.info("子节点的url为" + childUrlsb.toString());
                resultList.add(childUrlsb.toString());
                // 只有找到子节点才会结束catNodeDTO.setItem(getItemCatList(itemCat.getId()));循环
                log.info("子resultList" + resultList.toString());
            }
            if (count >= 14) {
                break;
            }
        }
        log.info("总或者子resultList" + resultList.toString());
        return resultList;
    }
}
