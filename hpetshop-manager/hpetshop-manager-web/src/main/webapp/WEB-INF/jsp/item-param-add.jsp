<%@ page language="java" contentType="text/html; charset=UTF-8"
         pageEncoding="UTF-8" %>
订单id： <input class="easyui-textbox" type="text" id="orderId" data-options="readonly:true" style="width: 200px;"/>
<hr>
<table class="easyui-datagrid" id="orderItemList" title="订单详情列表"
       data-options="singleSelect:false,collapsible:true,pagination:true,url:'',method:'get',pageSize:30">
    <thead>
    <tr>
        <th data-options="field:'itemId',width:120">商品ID</th>
        <th field='picPath' style="width: 80px;height: 50px" align="left" data-options="formatter:formatImg">图片</th>
        <th data-options="field:'title',width:350">商品名称</th>
        <th data-options="field:'num',width:50 ">数量</th>
        <th field='price' style="width: 80px;" data-options="formatter:formatPrice">单价</th>
        <th field="totalFee" style="width: 80px;" data-options="formatter:formatPrice">总价</th>

    </tr>
    </thead>
</table>
<script type="text/javascript">
    function formatImg(val, row) {
        if (val) {
            return '<img src=' + val + ' style=width:80px;height:50px>'
        }
    }
    function formatPrice(val, row) {
        if (val) {
            return val / 100;
        }
    }

</script>
