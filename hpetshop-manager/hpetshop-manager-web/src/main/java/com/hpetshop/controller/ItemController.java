package com.hpetshop.controller;

import com.hpetshop.dto.AllPageDTO;
import com.hpetshop.pojo.HpItem;
import com.hpetshop.service.ItemService;
import com.hpetshop.utils.Result;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.ResponseBody;

/**
 * <ul>
 * <li>文件名称: ItemController</li>
 * <li>文件描述:用于Item操作和前端交互的controller</li>
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
@Controller
public class ItemController {
    @Autowired
    private ItemService itemService;

    /**
     *
     * @param itemId
     * @return
     */
    @RequestMapping("/item/{itemId}")
    @ResponseBody
    public HpItem getItemById(@PathVariable Long itemId) {
        HpItem tbItem = itemService.getItemById(itemId);
        return tbItem;
    }

    /**
     * 用于前端页面item-list.jsp的grid查询所有物品
     */
    @RequestMapping("item/list")
    @ResponseBody
    public AllPageDTO getItemList(Integer page, Integer rows) {
        AllPageDTO allHpItemDTO = itemService.getItemList(page, rows);
        return allHpItemDTO;
    }

    /**
     * 用于保存到商品和商品描述表
     */
    @RequestMapping(value = "item/save", method = RequestMethod.POST)
    @ResponseBody
    public Result saveItem(HpItem hpItem, String desc, String itemParams) {
        Result result = itemService.saveItem(hpItem, desc, itemParams);
        return result;
    }

    /**
     * 用于删除物品
     *
     * @RequestMapping后，返回值通常解析为跳转路径，但是加上
     * @ResponseBody 后返回结果不会被解析为跳转路径
     */
    @RequestMapping(value = "item/delete", method = RequestMethod.POST)
    @ResponseBody
    public Result deleteItem(@RequestParam("ids") String ids) {
        String[] idss = ids.split(",");
        for (String id : idss) {
            itemService.deleteItem(Long.valueOf(id));
        }
        return Result.ok(ids);
    }

    /**
     * 用于更新物品
     */
    @RequestMapping(value = "item/update", method = RequestMethod.POST)
    @ResponseBody
    public Result updateItem(HpItem hpItem, String desc, String itemParam) {
        Result result = itemService.updateItem(hpItem, itemParam, desc);
        return result;
    }

    /**
     * 下架
     */
    @RequestMapping("item/instock")
    @ResponseBody
    public Result updateItemInstock(String ids) {
        String[] idss = ids.split(",");
        for (String id : idss) {
            itemService.updateItem(Long.valueOf(id), Byte.valueOf("2"));
        }
        return Result.ok(ids);
    }

    /**
     * 上架
     */
    @RequestMapping("item/reshelf")
    @ResponseBody
    public Result updateItemReshelf(String ids) {
        String[] idss = ids.split(",");
        for (String id : idss) {
            itemService.updateItem(Long.valueOf(id), Byte.valueOf("1"));
        }
        return Result.ok();
    }

    /**
     * 获取商品描述
     */
    @RequestMapping("item/getItemDesc/{hpItemId}")
    @ResponseBody
    public Result getItemDesc(@PathVariable Long hpItemId) {
        Result itemDesc = itemService.getItemDesc(hpItemId);
        return itemDesc;
    }

}
