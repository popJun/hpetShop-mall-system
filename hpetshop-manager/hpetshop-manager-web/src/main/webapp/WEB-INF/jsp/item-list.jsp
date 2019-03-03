<%@ page language="java" contentType="text/html; charset=UTF-8" pageEncoding="UTF-8" %>
<table class="easyui-datagrid" id="itemList" title="商品列表"
       data-options="singleSelect:false,collapsible:true,pagination:true,url:'item/list',method:'get',pageSize:30,toolbar:toolbar">
    <thead>
    <tr>
        <th data-options="field:'ck',checkbox:true"></th>
        <th data-options="field:'id',width:60">商品ID</th>
        <th data-options="field:'title',width:250">商品标题</th>
        <th data-options="field:'typeName',width:100">类目名</th>
        <th data-options="field:'sellPoint',width:250">卖点</th>
        <th data-options="field:'price',width:70,align:'right',formatter:HP.formatPrice">价格</th>
        <th data-options="field:'num',width:70,align:'right'">库存数量</th>
        <th data-options="field:'status',width:60,align:'center',formatter:HP.formatItemStatus">状态</th>
        <th data-options="field:'created',width:150,align:'center',formatter:HP.formatDateTime">创建日期</th>
        <th data-options="field:'updated',width:150,align:'center',formatter:HP.formatDateTime">更新日期</th>
    </tr>
    </thead>
</table>
<input type="hidden" name="cid" style="width: 280px;"/>
<div id="itemEditWindow" class="easyui-window" title="编辑商品" data-options="modal:true,closed:true,iconCls:'icon-save',href:'item-edit'" style="width:80%;height:80%;padding:10px;">
</div>
<script>

    function getSelectionsIds() {
        var itemList = $("#itemList");
        var sels = itemList.datagrid("getSelections");
        var ids = [];
        for (var i in sels) {
            ids.push(sels[i].id);
        }
        ids = ids.join(",");
        return ids;
    }

    var toolbar = [{
        text: '新增',
        iconCls: 'icon-add',
        handler: function () {
            $(".tree-title:contains('新增商品')").parent().click();
        }
    }, {
        text: '编辑',
        iconCls: 'icon-edit',
        handler: function () {
            var ids = getSelectionsIds();
            if (ids.length == 0) {
                $.messager.alert('提示', '必须选择一个商品才能编辑!');
                return;
            }
            if (ids.indexOf(',') > 0) {
                $.messager.alert('提示', '只能选择一个商品!');
                return;
            }

            $("#itemEditWindow").window({
                                            onLoad: function () {
                                                //回显数据
                                                var data = $("#itemList").datagrid("getSelections")[0];
                                                data.priceView = HP.formatPrice(data.price);
                                                $("#itemeEditForm").form("load", data);

                                                // 加载商品描述
                                                $.getJSON('item/getItemDesc/' + data.id, function (_data) {
                                                    if (_data.status == 200) {
                                                        itemEditEditor.html(_data.data.itemDesc);
                                                    }
                                                });

                                                HP.init({
                                                            "pics": data.image,
                                                            "cid": data.cid,
                                                            fun: function (node) {
                                                                HP.changeItemParam(node, "itemeEditForm");
                                                            }
                                                        });
                                            }
                                        }).window("open");
        }
    }, {
        text: '删除',
        iconCls: 'icon-cancel',
        handler: function () {
            var ids = getSelectionsIds();
            if (ids.length == 0) {
                $.messager.alert('提示', '未选中商品!');
                return;
            }
            $.messager.confirm('确认', '确定删除ID为 ' + ids + ' 的商品吗？', function (r) {
                if (r) {
                    var params = {"ids": ids};
                    $.post("item/delete", params, function (data) {
                        if (data.status == 200) {
                            $.messager.alert('提示', '删除商品成功!', undefined, function () {
                                $("#itemList").datagrid("reload");
                            });
                        }
                    });
                }
            });
        }
    }, '-', {
        text: '下架',
        iconCls: 'icon-remove',
        handler: function () {
            var ids = getSelectionsIds();
            if (ids.length == 0) {
                $.messager.alert('提示', '未选中商品!');
                return;
            }
            $.messager.confirm('确认', '确定下架ID为 ' + ids + ' 的商品吗？', function (r) {
                if (r) {
                    var params = {"ids": ids};
                    $.post("item/instock", params, function (data) {
                        if (data.status == "200") {
                            $.messager.alert('提示', '下架商品成功!', undefined, function () {
                                $("#itemList").datagrid("reload");
                            });
                        }
                    });
                }
            });
        }
    }, {
        text: '上架',
        iconCls: 'icon-remove',
        handler: function () {
            var ids = getSelectionsIds();
            if (ids.length == 0) {
                $.messager.alert('提示', '未选中商品!');
                return;
            }
            $.messager.confirm('确认', '确定上架ID为 ' + ids + ' 的商品吗？', function (r) {
                if (r) {
                    var params = {"ids": ids};
                    $.post("item/reshelf", params, function (data) {
                        if (data.status == "200") {
                            $.messager.alert('提示', '上架商品成功!', undefined, function () {
                                $("#itemList").datagrid("reload");
                            });
                        }
                    });
                }
            });
        }
    }];
</script>