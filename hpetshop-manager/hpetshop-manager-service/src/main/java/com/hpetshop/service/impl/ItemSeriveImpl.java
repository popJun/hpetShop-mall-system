package com.hpetshop.service.impl;

import com.github.pagehelper.PageHelper;
import com.github.pagehelper.PageInfo;
import com.hpetshop.dto.AllPageDTO;
import com.hpetshop.dto.ItemDTO;
import com.hpetshop.mapper.HpItemCatMapper;
import com.hpetshop.mapper.HpItemDescMapper;
import com.hpetshop.mapper.HpItemMapper;
import com.hpetshop.mapper.HpItemParamItemMapper;
import com.hpetshop.pojo.HpItem;
import com.hpetshop.pojo.HpItemCat;
import com.hpetshop.pojo.HpItemDesc;
import com.hpetshop.pojo.HpItemDescExample;
import com.hpetshop.pojo.HpItemExample;
import com.hpetshop.pojo.HpItemParamItem;
import com.hpetshop.pojo.HpItemParamItemExample;
import com.hpetshop.pojo.HpItemParamItemExample.Criteria;
import com.hpetshop.service.ItemService;
import com.hpetshop.utils.HttpClientUtil;
import com.hpetshop.utils.IDUtils;
import com.hpetshop.utils.Result;

import org.apache.log4j.Logger;
import org.joda.time.DateTime;
import org.springframework.beans.BeanUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.Date;
import java.util.List;

/**
 * <ul>
 * <li>文件名称: ItemSericeImpl</li>
 * <li>文件描述:用于处理商品的增删改查</li>
 * <li>版权所有: 版权所有(C) 2016</li>
 * <li>内容摘要:</li>
 * <li>其他说明:</li>
 * <li>创建日期:2018年2月28日</li>
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
public class ItemSeriveImpl implements ItemService {
    private static Logger logger = Logger.getLogger(ItemService.class);
    @Autowired
    private HpItemMapper hpItemMapper;
    @Autowired
    private HpItemDescMapper hpItemDescMapper;
    @Autowired
    private HpItemParamItemMapper hpItemParamItemMapper;
    @Autowired
    private HpItemCatMapper hpItemCatMapper;
    @Value("${SOLR_BASE_URL}")
    private String SOLR_BASE_URL;// 用于索引库同步调用的基础url
    @Value("${SOLR_UPLOADORSAVE_URL}")
    private String SOLR_UPLOADORSAVE_URL;// 更新或者保存调用的URL
    @Value("${SOLR_DEL_URL}")
    private String SOLR_DEL_URL;// 用于删除调用的url
    @Value("${REDIS_BASE}")
    private String REDIS_BASE;//redis基础Url
    @Value("${REDIS_ITEM}")
    private String REDIS_ITEM;//用于同步redis商品详情页

    /**
     * 通过主键查询一条商品信息
     */
    @Override
    public HpItem getItemById(long itemId) {
        HpItemExample example = new HpItemExample();
        com.hpetshop.pojo.HpItemExample.Criteria criteria = example.createCriteria();
        criteria.andIdEqualTo(itemId);
        List<HpItem> list = hpItemMapper.selectByExample(example);
        if (list != null && list.size() > 0) {
            HpItem item = list.get(0);
            return item;
        }
        return null;
    }

    /**
     * 用于分页显示总商品
     *
     * @param page 当前页面
     * @param row  每页几行
     */
    @Override
    public AllPageDTO getItemList(int page, int row) {
        HpItemExample example = new HpItemExample();
        PageHelper pageHelper = new PageHelper();
        pageHelper.startPage(page, row);// pageHelper的原理利用mybatis拦截机制在执行sql语句前进行拦截
        List<HpItem> hpItems = hpItemMapper.selectByExample(example);
        List<ItemDTO> itemDTOS = new ArrayList<ItemDTO>();
        for (HpItem hpItem : hpItems) {
            ItemDTO itemDTO = new ItemDTO();
            BeanUtils.copyProperties(hpItem, itemDTO);//复制到dto
            itemDTOS.add(itemDTO);
        }
        updateItemDTO(itemDTOS);
        AllPageDTO allHpItemDTO = new AllPageDTO();
        allHpItemDTO.setRows(itemDTOS);
        PageInfo<ItemDTO> info = new PageInfo<ItemDTO>(itemDTOS);// 还必须传入hpItems否则
        allHpItemDTO.setTotal(info.getTotal());// 取出页面总条数
        return allHpItemDTO;
    }

    /**
     * 利用cid找到类目名
     */
    private void updateItemDTO(List<ItemDTO> itemDTOS) {
        for (ItemDTO itemDTO : itemDTOS) {
            Long cid = itemDTO.getCid();
            HpItemCat hpItemCat = hpItemCatMapper.selectByPrimaryKey(cid);
            itemDTO.setTypeName(hpItemCat.getName());
        }
    }

    /**
     * 用于保存商品和商品规格表
     */
    @Override
    public Result saveItem(HpItem hpItem, String desc, String itemParams) {
        Long id = IDUtils.genItemId();
        hpItem.setId(id);
        hpItem.setUpdated(new Date());
        hpItem.setCreated(new Date());
        hpItem.setStatus((byte) 1);
        Result result = Result.ok(hpItem);
        // 插入到商品表
        hpItemMapper.insert(hpItem);
        logger.info("-----------添加到商品表成功-----------------");
        // 插入到商品描述表
        insertHpItemDesc(id, desc);
        // 插入商品规格描述表
        logger.info("商品规格json" + itemParams);
        insertHpItemParamItem(id, itemParams);
        logger.info("---------添加到商品描述表成功，事务结束----------");
        return result;
    }

    /**
     * 用于保存商品描述
     *
     * @param id 商品id
     */
    private void insertHpItemDesc(Long id, String desc) {
        HpItemDesc hpItemDesc = new HpItemDesc();
        hpItemDesc.setItemId(id);
        hpItemDesc.setItemDesc(desc);
        hpItemDesc.setCreated(new Date());
        hpItemDesc.setUpdated(new Date());
        hpItemDescMapper.insert(hpItemDesc);
    }

    /**
     * 插入到商品规格表
     *
     * @param id 商品id
     */
    private void insertHpItemParamItem(Long id, String itemParams) {
        HpItemParamItem hpItemParamItem = new HpItemParamItem();
        hpItemParamItem.setCreated(new Date());
        hpItemParamItem.setUpdated(new Date());
        hpItemParamItem.setItemId(id);
        hpItemParamItem.setParamData(itemParams);
        hpItemParamItemMapper.insert(hpItemParamItem);

    }

    /**
     * 更新到商品规格表
     *
     * @param id 商品id
     */
    private void updateHpItemParamItem(Long id, String itemParams) {

        HpItemParamItemExample example = new HpItemParamItemExample();
        Criteria criteria = example.createCriteria();
        criteria.andItemIdEqualTo(id);
        HpItemParamItem hpItemParamItem = new HpItemParamItem();
        hpItemParamItem.setUpdated(new Date());
        hpItemParamItem.setItemId(id);
        hpItemParamItem.setParamData(itemParams);
        hpItemParamItemMapper.updateByExampleSelective(hpItemParamItem, example);

    }

    /**
     * 根据Itemid删除物品表和物品规格表此项为一个事务
     */
    @Override
    public Result deleteItem(Long hpItemId) {
        Result result = Result.ok(hpItemId);
        // 删除数据hpItem表
        hpItemMapper.deleteByPrimaryKey(hpItemId);
        // 删除数据hpItemParamItem表
        deleteItemParamItem(hpItemId);
        return result;
    }

    /**
     * 根据Itemid物品规格表此项为一个事务
     */
    private void deleteItemParamItem(Long hpItemId) {
        HpItemParamItemExample example = new HpItemParamItemExample();
        Criteria criteria = example.createCriteria();
        criteria.andItemIdEqualTo(hpItemId);
        hpItemParamItemMapper.deleteByExample(example);

    }

    /**
     * 用于物品的更新：由于事务的原子性不能传id后查找数据库：这时候还没更新数据库更新solr在数据库取得的是原来的数据
     * 为了解耦可以使用利用SpringAOP进行拦截更新--同样redis也可使用此方法进行同步redis
     */
    @Override
    public Result updateItem(HpItem hpItem, String itemParam, String desc) {
        HpItemExample example = new HpItemExample();
        com.hpetshop.pojo.HpItemExample.Criteria criteria = example.createCriteria();
        criteria.andIdEqualTo(hpItem.getId());
        hpItem.setUpdated(new DateTime().toDate());
        Result result = Result.ok(hpItem);
        updateHpItemParamItem(hpItem.getId(), itemParam);
        try {
            updateDesc(hpItem.getId(), desc);//更新物品详情
        } catch (Exception e) {
            e.printStackTrace();
        }
        hpItemMapper.updateByExampleSelective(hpItem, example);
        //更新需要清除redis
        try {
            HttpClientUtil.doGet(REDIS_BASE + REDIS_ITEM + hpItem.getId());
        } catch (Exception e) {
            e.printStackTrace();
        }
        return result;
    }

    /**
     * 用于更新物品详情
     */
    private void updateDesc(Long id, String desc) {
        HpItemDesc hpItemDesc = hpItemDescMapper.selectByPrimaryKey(id);
        hpItemDesc.setItemDesc(desc);
        hpItemDesc.setUpdated(new Date());
        HpItemDescExample example = new HpItemDescExample();
        HpItemDescExample.Criteria criteria = example.createCriteria();
        criteria.andItemIdEqualTo(id);
        hpItemDescMapper.updateByExampleSelective(hpItemDesc, example);//对于大文件需要用updateByExampleSelective
    }

    /**
     * 对物品进行上架和下架处理
     */
    @Override
    public Result updateItem(Long hpItemId, Byte status) {
        HpItemExample example = new HpItemExample();
        com.hpetshop.pojo.HpItemExample.Criteria criteria = example.createCriteria();
        criteria.andIdEqualTo(hpItemId);
        HpItem hpItem = new HpItem();
        hpItem.setStatus(status);
        Result result = Result.ok(hpItem);
        hpItemMapper.updateByExampleSelective(hpItem, example);
        if (status == 1) {
            return result;
        } else {
            return result;
        }
    }

    /**
     * 根据hpItemId取商品描述
     */
    @Override
    public Result getItemDesc(Long hpItemId) {
        HpItemDesc hpItemDesc = hpItemDescMapper.selectByPrimaryKey(hpItemId);
        return Result.ok(hpItemDesc);
    }
}
