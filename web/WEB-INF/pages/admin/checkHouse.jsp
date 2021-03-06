<%@ page language="java" contentType="text/html; charset=utf-8"
         pageEncoding="utf-8" %>
<%@taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
<html>
<head>
    <meta charset="UTF-8">
    <title>审核房屋</title>
    <link rel="stylesheet" href="${pageContext.request.contextPath}/layui/css/layui.css">
</head>
<body>
<div class="wrapper" style="width: 900px;margin-top: 40px">
    <fieldset class="layui-elem-field layui-field-title">
        <legend style="font-size: 26px">审核房屋信息</legend>
    </fieldset>
    <form id="checkHouse" class="layui-form">
        <div class="layui-form-item">
            <label class="layui-form-label">房源标题</label>
            <div class="layui-input-block">
                <input type="hidden" value="${House.houseId}" name="houseId">
                <textarea name="houseDesc" placeholder="请输入内容" class="layui-textarea" readonly="readonly"
                          lay-verify="required">${House.houseDesc}</textarea>
            </div>
        </div>
        <div class="layui-form-item">
            <label class="layui-form-label">房屋类型</label>
            <div class="layui-input-block">
                <input type="text" name="houseModel" lay-verify="required" placeholder="几室几厅" readonly="readonly"
                       autocomplete="off" value="${House.houseModel}" class="layui-input">
            </div>
        </div>
        <div class="layui-form-item">
            <label class="layui-form-label">房屋面积</label>
            <div class="layui-input-block">
                <input type="text" name="houseArea" lay-verify="required" placeholder="" readonly="readonly"
                       autocomplete="off" value="${House.houseArea}" class="layui-input">
            </div>
        </div>
        <div class="layui-form-item">
            <label class="layui-form-label">房屋楼层</label>
            <div class="layui-input-block">
                <input type="text" name="houseFloor" readonly="readonly" lay-verify="required" placeholder="高层/低层/几楼"
                       autocomplete="off" value="${House.houseFloor}" class="layui-input">
            </div>
        </div>
        <div class="layui-form-item">
            <label class="layui-form-label">出租方式</label>
            <div class="layui-input-block">
                <%--注意：设置为disabled后，值是无法传递的--%>
                <select name="houseType" lay-verify="required" disabled="disabled">
                    <option value=""></option>
                    <option value="整租" <c:if test="${House.houseType == '整租'}">selected</c:if>>整租</option>
                    <option value="合租" <c:if test="${House.houseType == '合租'}">selected</c:if>>合租</option>
                </select>
            </div>
        </div>
        <div class="layui-form-item">
            <label class="layui-form-label">地&emsp;&emsp;址</label>
            <div class="layui-input-block">
                <input type="text" name="houseAddress" readonly="readonly" lay-verify="required" placeholder=""
                       autocomplete="off" value="${House.houseAddress}" class="layui-input">
            </div>
        </div>
        <div class="layui-form-item">
            <label class="layui-form-label">小区名字</label>
            <div class="layui-input-block">
                <input type="text" name="communityName" readonly="readonly" lay-verify="required" placeholder=""
                       autocomplete="off" value="${House.communityName}" class="layui-input">
            </div>
        </div>
        <div class="layui-form-item">
            <label class="layui-form-label">租房价格</label>
            <div class="layui-input-block">
                <input type="text" name="housePrice" readonly="readonly" lay-verify="required|number" placeholder=""
                       autocomplete="off" value="${House.housePrice}" class="layui-input">
            </div>
        </div>
        <div class="layui-form-item">
            <label class="layui-form-label">联系电话</label>
            <div class="layui-input-block">
                <input type="text" name="houseLinkMan" readonly="readonly" lay-verify="required|phone" placeholder=""
                       autocomplete="off" value="${House.houseLinkMan}" class="layui-input">
            </div>
        </div>
        <div class="layui-form-item">
            <label class="layui-form-label">房屋朝向</label>
            <div class="layui-input-block">
                <%--注意：设置为disabled后，值是无法传递的--%>
                <select name="houseOriented" lay-verify="required" disabled="disabled">
                    <option value=""></option>
                    <option value="南北"<c:if test="${House.houseOriented == '南北'}"> selected</c:if>>南北</option>
                    <option value="东西"<c:if test="${House.houseOriented == '东西'}"> selected</c:if>>东西</option>
                </select>
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
            <label class="layui-form-label">证件图片</label>
            <div class="layui-input-block">
                <c:forEach items="${privacyImg}" var="d">
                    <img src="${d}" width="240px" height="180px">
                </c:forEach>
            </div>
        </div>

        <hr>

        <div class="layui-form-item">
            <label class="layui-form-label">审核结果</label>
            <div class="layui-input-block">
                <input type="radio" name="houseStatus" value="pass" title="通过">
                <input type="radio" name="houseStatus" value="not_pass" title="未通过">
            </div>
        </div>

        <div class="layui-form-item">
            <div class="layui-input-block">
                <button class="layui-btn" lay-submit lay-filter="checkHouse">确定</button>
                <a class="layui-btn layui-btn-primary" href="${pageContext.request.contextPath }/admin/houseList.html">取消</a>
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

        form.on("submit(checkHouse)", function () {
            $.post("${pageContext.request.contextPath}/house/checkHouse", $("#checkHouse").serialize(), function (res) {
                if (res === "OK") {
                    layer.msg("操作成功！", {
                        time: 1000, end: function () {
                            window.history.back(-1);
                        }
                    });
                }
            });
            return false;
        });
    });
</script>
</body>
</html>
