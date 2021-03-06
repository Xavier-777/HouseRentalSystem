<%@ page language="java" contentType="text/html; charset=utf-8"
         pageEncoding="utf-8" %>
<%@taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
<html>
<head>
    <meta charset="UTF-8">
    <title>订单详情</title>
    <link rel="stylesheet" href="${pageContext.request.contextPath}/layui/css/layui.css">
</head>
<body>
<div class="wrapper" style="width: 900px;margin-top: 40px">
    <fieldset class="layui-elem-field layui-field-title">
        <legend style="font-size: 26px">订单详情</legend>
    </fieldset>
    <form id="checkHouse" class="layui-form">
        <div class="layui-form-item">
            <label class="layui-form-label">房源标题</label>
            <div class="layui-input-block">
                <input type="hidden" value="${detailOrderInfo.orderId}" name="orderId">
                <textarea name="houseDesc" placeholder="请输入内容" class="layui-textarea" readonly="readonly"
                          lay-verify="required">${detailOrderInfo.houseDesc}</textarea>
            </div>
        </div>
        <div class="layui-form-item">
            <label class="layui-form-label">房屋类型</label>
            <div class="layui-input-block">
                <input type="text" name="houseModel" lay-verify="required" placeholder="几室几厅" readonly="readonly"
                       autocomplete="off" value="${detailOrderInfo.houseModel}" class="layui-input">
            </div>
        </div>
        <div class="layui-form-item">
            <label class="layui-form-label">房屋面积</label>
            <div class="layui-input-block">
                <input type="text" name="houseArea" lay-verify="required" placeholder="" readonly="readonly"
                       autocomplete="off" value="${detailOrderInfo.houseArea}" class="layui-input">
            </div>
        </div>
        <div class="layui-form-item">
            <label class="layui-form-label">房屋楼层</label>
            <div class="layui-input-block">
                <input type="text" name="houseFloor" readonly="readonly" lay-verify="required" placeholder="高层/低层/几楼"
                       autocomplete="off" value="${detailOrderInfo.houseFloor}" class="layui-input">
            </div>
        </div>
        <div class="layui-form-item">
            <label class="layui-form-label">出租方式</label>
            <div class="layui-input-block">
                <input type="text" name="houseType" readonly="readonly" lay-verify="required" placeholder="整租/合租"
                       autocomplete="off" value="${detailOrderInfo.houseType}" class="layui-input">
            </div>
        </div>
        <div class="layui-form-item">
            <label class="layui-form-label">地&emsp;&emsp;址</label>
            <div class="layui-input-block">
                <input type="text" name="houseAddress" readonly="readonly" lay-verify="required" placeholder=""
                       autocomplete="off" value="${detailOrderInfo.houseAddress}" class="layui-input">
            </div>
        </div>
        <div class="layui-form-item">
            <label class="layui-form-label">小区名字</label>
            <div class="layui-input-block">
                <input type="text" name="communityName" readonly="readonly" lay-verify="required" placeholder=""
                       autocomplete="off" value="${detailOrderInfo.communityName}" class="layui-input">
            </div>
        </div>
        <div class="layui-form-item">
            <label class="layui-form-label">租房价格</label>
            <div class="layui-input-block">
                <input type="text" name="housePrice" readonly="readonly" lay-verify="required|number" placeholder=""
                       autocomplete="off" value="${detailOrderInfo.housePrice}" class="layui-input">
            </div>
        </div>
        <div class="layui-form-item">
            <label class="layui-form-label">联系电话</label>
            <div class="layui-input-block">
                <input type="text" name="houseLinkMan" readonly="readonly" lay-verify="required|phone" placeholder=""
                       autocomplete="off" value="${detailOrderInfo.houseLinkMan}" class="layui-input">
            </div>
        </div>
        <div class="layui-form-item">
            <label class="layui-form-label">房屋朝向</label>
            <div class="layui-input-block">
                <input type="text" name="houseOriented" readonly="readonly" lay-verify="required" placeholder=""
                       autocomplete="off" value="${detailOrderInfo.houseOriented}" class="layui-input">
            </div>
        </div>

        <hr>

        <div class="layui-form-item">
            <label class="layui-form-label">简介图片</label>
            <div class="layui-input-block">
                <img src="${briefImage}" width="240px" height="180px">
            </div>
        </div>

        <hr>

        <div class="layui-form-item">
            <label class="layui-form-label">详细图片</label>
            <div class="layui-input-block">
                <c:forEach items="${DetailsImg}" var="d">
                    <img src="${d}" width="240px" height="180px">
                </c:forEach>
            </div>
        </div>

        <hr>

        <div class="layui-form-item">
            <div class="layui-input-block">
                <a class="layui-btn <c:if test="${detailOrderInfo.orderStatus == 'paid'|| detailOrderInfo.orderStatus == 'not_pay'}"> layui-btn-disabled</c:if>"
                   target="_blank"
                   href="${pageContext.request.contextPath}/order/payOrder?orderId=${detailOrderInfo.orderId}&housePrice=${detailOrderInfo.housePrice}"
                        <c:if test="${detailOrderInfo.orderStatus == 'paid' || detailOrderInfo.orderStatus == 'not_pay'}"> style="pointer-events: none;"</c:if>>支付</a>
                <a class="layui-btn layui-btn-primary"
                   href="${pageContext.request.contextPath }/order/myOrder.html">取消</a>
            </div>
        </div>
    </form>
</div>
<script src="${pageContext.request.contextPath }/layui/layui.js"></script>
<script>
    layui.use(['element', 'form', 'layer'], function () {
        let element = layui.element,
            form = layui.form,
            layer = layui.layer,
            $ = layui.jquery;
    });
</script>
</body>
</html>
