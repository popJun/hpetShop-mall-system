<%@ page language="java" contentType="text/html; charset=UTF-8" pageEncoding="UTF-8" %>
<table class="easyui-datagrid" id="itemParamList" title="订单列表"
       data-options="singleSelect:false,collapsible:true,pagination:true,url:'order/getAll',method:'get',pageSize:30,toolbar:itemParamListToolbar">
    <thead>
    <tr>
        <th data-options="field:'ck',checkbox:true"></th>
        <th data-options="field:'orderId',width:60">订单ID</th>
        <th data-options="field:'orderType',width:80">支付类型</th>
        <th data-options="field:'payment',width:100">订单价格</th>
        <th data-options="field:'buyerNick',width:100">用户昵称</th>
        <th data-options="field:'orderStatus',width:100">订单状态</th>
        <th data-options="field:'postFee',width:100">收件人</th>
        <th data-options="field:'shippingCode',width:100">手机号码</th>
        <th data-options="field:'buyerMessage',width:200">收货地址</th>
        <th data-options="field:'createTime',width:130,align:'center',formatter:HP.formatDateTime">创建日期</th>
        <th field="shippingName" style="width: 150px" data-options="">物流信息</th>
    </tr>
    </thead>
</table>
<div id="orderWindow" class="easyui-window" title="查看订单详情" data-options="modal:true,closed:true,iconCls:'icon-save',href:'item-param-add'" style="width:80%;height:80%;padding:10px;">
</div>
<script>

    function formatDate(value, index) {
        var json = JSON.parse(value);
        var array = [];
        $.each(json, function (i, e) {
            array.push(e.group);
        });
        return array.join(",");
    }

    function getSelectionsIds() {
        var itemList = $("#itemParamList");
        var sels = itemList.datagrid("getSelections");
        var ids = [];
        for (var i in sels) {
            ids.push(sels[i].orderId);
        }
        ids = ids.join(",");
        return ids;
    }
    function getSelectionsstatus() {
        var itemList = $("#itemParamList");
        var sels = itemList.datagrid("getSelections");
        var status = [];
        for (var i in sels) {
            status.push(sels[i].orderStatus);
        }
        status = status.join(",");
        return status;
    }
    var itemParamListToolbar = [{
        text: '查看订单详情',
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
            $("#orderWindow").window({
                                         onLoad: function () {
                                             //传id到子页面
                                             $('#orderId').textbox('setValue', ids);
                                             $("#orderItemList").datagrid({
                                                                              url: 'order/getOrderItem/' + ids
                                                                          })
                                         },
                                         onClose: function () {
                                         }
                                     }).window("open");
        }

    }, '-', {
        text: '发货',
        iconCls: 'icon-remove',
        handler: function () {
            var ids = getSelectionsIds();
            var status = getSelectionsstatus();
            var status1 = status.split(',');
            if (ids.length == 0) {
                $.messager.alert('提示', '未选中商品!');
                return;
            }
            for (var i = 0; i < status1.length; i++) {
                if (status1[i] == '交易完成') {
                    alert('所选订单包含已完成订单');
                    return;
                }
            }

            $.messager.confirm('确认', '确定发货订单ID为 ' + ids + ' 的订单吗？', function (r) {
                if (r) {
                    var params = {"ids": ids};
                    $.post("order/updateOrder", params, function (data) {
                        if (data.status == 200) {
                            $.messager.alert('提示', '发货成功，请为商家提供物流号!', undefined, function () {
                                $("#itemParamList").datagrid("reload");
                            });
                        }
                    });
                }
            });
        }
    }, '-', {
        text: '提供物流信息',
        iconCls: 'icon-edit',
        handler: function () {
            var ids = getSelectionsIds();
            if (ids.length == 0) {
                $.messager.alert('提示', '未选中商品!');
                return;
            }
            if (ids.indexOf(',') > 0) {
                $.messager.alert('提示', '只能选择一个商品!');
                return;
            }
            $.messager.prompt('确认', '请输入物流信息', function (r) {
                if (r) {
                    $.post("order/updateOrderByshippingName", {ids: ids, shippingName: r}, function (data) {
                               $("#itemParamList").datagrid("reload");
                           }
                    )
                }
            })
        }
    }
    ];
</script>