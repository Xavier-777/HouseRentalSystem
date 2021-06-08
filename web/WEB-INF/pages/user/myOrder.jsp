<%@ page language="java" contentType="text/html; charset=utf-8"
         pageEncoding="utf-8" %>
<%@taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
<!DOCTYPE html>
<html lang="en">
<head>
    <meta charset="UTF-8">
    <title>我的收藏</title>
    <link rel="stylesheet" href="${pageContext.request.contextPath}/layui/css/layui.css">
</head>
<body>
<div class="layui-fluid" style="margin-top: 40px;">
    <fieldset class="layui-elem-field layui-field-title">
        <legend style="font-size: 26px">我的租房信息</legend>
    </fieldset>

    请在15分内支付订单，逾期自动取消

    <table id="houseList" lay-filter="order"></table>
</div>
<script src="${pageContext.request.contextPath}/layui/layui.js"></script>
<script>
    layui.use(['element', 'form', 'table', 'util'], function () {
        let element = layui.element,
            $ = layui.jquery,
            table = layui.table,
            util = layui.util;

        let dt = table.render({
            elem: "#houseList",
            url: "${pageContext.request.contextPath}/order/myOrderInfo",
            page: true,
            method: 'post',
            limit: 5,
            limits: [5, 10],
            loading: true,
            cols: [[
                {
                    field: 'orderId', title: '序号', align: 'center', templet: function (d) {
                        return d.LAY_INDEX;
                    }
                }
                , {field: 'houseDesc', title: '房屋详情', align: 'center'}
                , {field: 'houseModel', title: '几室几厅', align: 'center'}
                , {field: 'houseArea', title: '面积', align: 'center'}
                , {field: 'houseFloor', title: '楼层', align: 'center'}
                , {field: 'houseType', title: '出租方式', align: 'center'}
                , {
                    field: 'housePrice', title: '价格', align: 'center', templet: function (data) {
                        var price = parseFloat(data.housePrice).toFixed(2);
                        return price;
                    }
                }
                , {field: 'houseAddress', title: '地址', align: 'center'}
                , {field: 'houseLinkMan', title: '联系人', align: 'center'}
                , {field: 'communityName', title: '小区名', align: 'center'}
                , {field: 'houseOriented', title: '朝向', align: 'center'}
                , {
                    field: 'orderTime', title: '订单时间', align: 'center',
                    templet: function (d) {
                        return util.toDateString(d.orderTime)
                    }
                }
                , {field: 'orderUser', title: '订单人', align: 'center'}
                , {
                    field: 'orderStatus', title: '订单状态', align: 'center', templet: function (d) {
                        if (d.orderStatus == 'paid')
                            return "已支付";
                        if (d.orderStatus == 'ordered')
                            return "已下单";
                        if (d.orderStatus == 'not_pay')
                            return "已取消";
                    }
                }
                , {title: '操作', align: 'center', toolbar: "#tools", width: 190}
            ]],
        });

        table.on('tool(order)', function (obj) {
            let data = obj.data;
            let layEvent = obj.event;
            let tr = obj.tr;
            let currPage = dt.config.page.curr;

            if (layEvent === 'view') {
                window.location.href = "${pageContext.request.contextPath}/order/detailOrder?orderId=" + data.orderId;
            }

            if (layEvent === 'pay') {
                window.open("${pageContext.request.contextPath}/order/payOrder?orderId=" + data.orderId + "&housePrice=" + data.housePrice);
            }

            if (layEvent === 'delete') {
                layer.confirm('确认删除当前数据吗？', {icon: 5, shade: 0.1}, function (index) {
                    $.post("${pageContext.request.contextPath}/order/deleteOrder", {orderId: data.orderId}, function (success) {
                        if (success === "OK") {
                            obj.del();
                            dt.reload({
                                page: {curr: 1}
                            });
                            layer.msg("删除成功");
                        }
                    })
                });
            }
        });
    });
</script>
<script type="text/html" id="tools">
    <a class="layui-btn layui-btn-xs layui-btn-normal" lay-event="view">查看</a>
    <%--已支付的就点击不了--%>
    {{#  if(d.orderStatus == 'paid' || d.orderStatus == 'not_pay'){  }}
    <a class="layui-btn layui-btn-xs layui-btn-disabled" lay-event="pay" target="_blank" style="pointer-events: none;">支付</a>
    {{#  }else{  }}
    <a class="layui-btn layui-btn-xs" lay-event="pay" target="_blank">支付</a>
    {{#  }  }}
    <a class="layui-btn layui-btn-xs layui-btn-danger" lay-event="delete">删除</a>
</script>
</body>
</html>