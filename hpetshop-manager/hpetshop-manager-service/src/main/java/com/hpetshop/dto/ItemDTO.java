package com.hpetshop.dto;

import com.hpetshop.pojo.HpItem;

/**
 * 用于全部显示商品信息
 *
 * @ProjectName: hpetshop-parent
 * @Package: com.hpetshop.dto
 * @Author: wushaochuan
 * @CreateDate: 2018/5/17 10:19
 * @UpdateUser:
 * @UpdateDate: 2018/5/17 10:19
 * @Version: 1.0
 **/
public class ItemDTO extends HpItem {
    private String typeName;//类名

    public String getTypeName() {
        return typeName;
    }

    public void setTypeName(String typeName) {
        this.typeName = typeName;
    }
}
