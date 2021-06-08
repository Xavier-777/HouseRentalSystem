<%@ page language="java" contentType="text/html; charset=utf-8"
         pageEncoding="utf-8" %>
<%@taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
<!DOCTYPE html>
<html lang="en">
<head>
    <meta charset="UTF-8">
    <link rel="stylesheet" href="${pageContext.request.contextPath }/layui/css/layui.css"/>
    <title>欢迎来到房屋租赁网</title>
    <link rel="stylesheet" href="${pageContext.request.contextPath }/css/global.css">
    <script src="${pageContext.request.contextPath }/layui/layui.js"></script>
</head>
<body>

<div class="layui-carousel" id="bg-item">
    <%--顶部logo与登录按钮--%>
    <div class="layui-header title">
        <div class="layui-container">
            <div class="layui-logo layui-pull-left">
                <img src="${pageContext.request.contextPath }/img/logo.png" height="50px" alt="房屋租赁系统">
            </div>
            <c:if test="${not empty loginUser }">
                <div class="personalCenter layui-pull-right">
                    <a href="${pageContext.request.contextPath}/user/home.html" target="_blank" style="color:#fff">
                        <i class="layui-icon layui-icon-tree"></i> ${loginUser.userNickName}</a>
                </div>
            </c:if>
            <c:if test="${empty loginUser }">
                <div class="operation layui-pull-right"><i class="layui-icon layui-icon-tree"></i> 登录 - 注册</div>
            </c:if>
        </div>
    </div>


    <div class="layui-container">
        <!--搜索框-->
        <div class="search-input">
            <form class="search-form layui-form" method="post" action="${pageContext.request.contextPath }/fuzzy">
                <div class="layui-pull-left input">
                    <input type="text" placeholder="请输入房源特征、房型、小区名..." name="keywords" class="search layui-input"
                           lay-verify="">
                </div>
                <div class="layui-pull-left button">
                    <button class="btn search-btn" lay-submit>
                        <i class="layui-icon layui-icon-search" style="font-size: 24px;"></i>
                    </button>
                </div>
            </form>
        </div>

        <%--筛选框--%>
        <div class="search-bd search-bd-font">
            <form id="formDemo" class="layui-form">
                <div class="layui-form-item">
                    <label class="layui-form-label">租金：</label>
                    <div class="layui-input-block">
                        <input type="radio" name="housePriceLimit" value="null" title="不限" checked >
                        <input type="radio" name="housePriceLimit" value="0-500" title="500以下" <c:if test="${HouseFilter.housePriceLimit == '0-500'}"> checked </c:if>>
                        <input type="radio" name="housePriceLimit" value="500-1000" title="500-1000" <c:if test="${HouseFilter.housePriceLimit == '500-1000'}"> checked </c:if>>
                        <input type="radio" name="housePriceLimit" value="1000-2000" title="1000-2000" <c:if test="${HouseFilter.housePriceLimit == '1000-2000'}"> checked </c:if>>
                        <input type="radio" name="housePriceLimit" value="2000-3000" title="2000-3000" <c:if test="${HouseFilter.housePriceLimit == '2000-3000'}"> checked </c:if>>
                        <input type="radio" name="housePriceLimit" value="3000-4000" title="3000-4000" <c:if test="${HouseFilter.housePriceLimit == '3000-4000'}"> checked </c:if>>
                    </div>
                </div>

                <div class="layui-form-item">
                    <label class="layui-form-label">厅室：</label>
                    <div class="layui-input-block">
                        <input type="radio" name="houseModel" value="null" title="不限" checked>
                        <input type="radio" name="houseModel" value="1室" title="一室" <c:if test="${HouseFilter.houseModel == '1室'}"> checked </c:if>>
                        <input type="radio" name="houseModel" value="2室" title="二室" <c:if test="${HouseFilter.houseModel == '2室'}"> checked </c:if>>
                        <input type="radio" name="houseModel" value="3室" title="三室" <c:if test="${HouseFilter.houseModel == '3室'}"> checked </c:if>>
                        <input type="radio" name="houseModel" value="4室" title="四室" <c:if test="${HouseFilter.houseModel == '4室'}"> checked </c:if>>
                        <input type="radio" name="houseModel" value="5室" title="四室以上" <c:if test="${HouseFilter.houseModel == '5室'}"> checked </c:if>>
                    </div>
                </div>

                <div class="layui-form-item">
                    <label class="layui-form-label">租赁方式：</label>
                    <div class="layui-input-block">
                        <input type="radio" name="houseType" value="null" title="不限" checked>
                        <input type="radio" name="houseType" value="整租" title="整租" <c:if test="${HouseFilter.houseType == '整租'}"> checked </c:if>>
                        <input type="radio" name="houseType" value="合租" title="合租" <c:if test="${HouseFilter.houseType == '合租'}"> checked </c:if>>
                    </div>
                </div>

                <div class="layui-form-item">
                    <label class="layui-form-label">朝向：</label>
                    <div class="layui-input-block">
                        <input type="radio" name="houseOriented" value="null" title="不限" checked>
                        <input type="radio" name="houseOriented" value="东西" title="东西" <c:if test="${HouseFilter.houseOriented == '东西'}"> checked </c:if>>
                        <input type="radio" name="houseOriented" value="南北" title="南北" <c:if test="${HouseFilter.houseOriented == '南北'}"> checked </c:if>>
                    </div>
                </div>

                <div class="layui-form-item">
                    <label class="layui-form-label">价格排序：</label>
                    <div class="layui-input-block">
                        <input type="radio" name="housePriceSort" value="null" title="默认" checked>
                        <input type="radio" name="housePriceSort" value="ASC" title="由低到高" <c:if test="${HouseFilter.housePriceSort == 'ASC'}"> checked </c:if>>
                        <input type="radio" name="housePriceSort" value="DESC" title="由高到低" <c:if test="${HouseFilter.housePriceSort == 'DESC'}"> checked </c:if>>
                    </div>
                </div>

                <div class="layui-form-item">
                    <div class="layui-input-block">
                        <button class="layui-btn" lay-submit lay-filter="formDemo"
                                style="border-radius: 50px 50px 50px 50px;">确定
                        </button>
                        <a type="reset" class="layui-btn layui-btn-primary"
                                style="border-radius: 50px 50px 50px 50px;"
                           href="${pageContext.request.contextPath}/resetFilter">重置
                        </a>
                    </div>
                </div>
            </form>
        </div>
    </div>

    <%--轮播图--%>
    <div carousel-item>
        <div style="background: url('/img/banner-1.jpg')no-repeat center/cover"></div>
        <div style="background: url('/img/banner-2.jpg')no-repeat center/cover"></div>
        <div style="background: url('/img/banner-3.jpg')no-repeat center/cover"></div>
        <div style="background: url('/img/banner-4.jpg')no-repeat center/cover"></div>
    </div>
</div>


<%--登录 & 注册的隐藏框--%>
<div class="layui-container">
    <div class="layui-tab layui-tab-brief" id="sign" lay-filter="" style="display: none;">
        <ul class="layui-tab-title">
            <li class="layui-this">登录</li>
            <li>注册</li>
        </ul>
        <div class="layui-tab-content">
            <div class="layui-tab-item layui-show">
                <div class="" style="margin: 40px 20px;">
                    <form class="layui-form layui-form-pane" id="login">
                        <div class="layui-form-item">
                            <label class="layui-form-label">用户名</label>
                            <div class="layui-input-block">
                                <input type="text" name="userName" required lay-verify="required" placeholder="请输入用户名"
                                       autocomplete="off" class="layui-input">
                            </div>
                        </div>
                        <br>
                        <div class="layui-form-item">
                            <label class="layui-form-label">密码</label>
                            <div class="layui-input-block">
                                <input type="password" name="userPassword" required lay-verify="required"
                                       placeholder="请输入密码" autocomplete="off" class="layui-input">
                            </div>
                        </div>
                        <br>
                        <div class="layui-form-item">
                            <button class="layui-btn layui-btn-fluid layui-btn-normal layui-btn-radius" lay-submit
                                    lay-filter="login">立即登录
                            </button>
                        </div>
                    </form>
                </div>
            </div>
            <div class="layui-tab-item">
                <div class="" style="margin: 30px 20px;">
                    <form class="layui-form layui-form-pane form">
                        <div class="layui-form-item">
                            <label class="layui-form-label">用户名</label>
                            <div class="layui-input-block">
                                <input type="text" name="userName" required lay-verify="required" placeholder="请输入用户名"
                                       autocomplete="off" class="layui-input">
                            </div>
                        </div>
                        <div class="layui-form-item">
                            <label class="layui-form-label">密码</label>
                            <div class="layui-input-block">
                                <input type="text" name="userPassword" required lay-verify="required"
                                       placeholder="请输入密码"
                                       autocomplete="off" class="layui-input">
                            </div>
                        </div>
                        <div class="layui-form-item">
                            <label class="layui-form-label">手机号</label>
                            <div class="layui-input-block">
                                <input type="text" name="userPhoneNumber" required lay-verify="required|phone"
                                       placeholder="注册后不能修改" autocomplete="off" class="layui-input">
                            </div>
                        </div>
                        <div class="layui-form-item">
                            <label class="layui-form-label">昵称</label>
                            <div class="layui-input-block">
                                <input type="text" name="userNickName" required lay-verify="required"
                                       placeholder="注册后不能修改"
                                       autocomplete="off" class="layui-input">
                            </div>
                        </div>
                    </form>
                    <div class="layui-form-item">
                        <input type="submit"
                               class="layui-btn layui-btn-fluid layui-btn-radius layui-btn-normal regist-btn"
                               value="立即注册"/>
                    </div>
                </div>
            </div>
        </div>
    </div>
</div>

<!--房屋表头-->
<div class="list_control_bar layui-container">
    <div>
        <div class="list_title layui-pull-left"><span>全部房源</span></div>
        <%--        <div class="list_more">--%>
        <%--            <ul class="layui-pull-right list-item">--%>
        <%--                <li class="click-this"><a href="${pageContext.request.contextPath}/index.html">默认排序</a></li>--%>
        <%--                <li><a href="${pageContext.request.contextPath}/priceAsc">价格升序</a></li>--%>
        <%--                <li><a href="${pageContext.request.contextPath}/priceDesc">价格降序</a></li>--%>
        <%--            </ul>--%>
        <%--        </div>--%>
    </div>
</div>

<%--房屋展示--%>
<section class="layui-container">
    <hr>
    <h2>共找到<span style="color: #ffc601;margin: 0 5px;">${House.size()}</span>套出租房源</h2>
    <div class="house-detail">
        <ul>
            <c:forEach items="${House }" var="h">
                <li>
                    <a href="${pageContext.request.contextPath}/detail.html?id=${h.houseId }" class="show-image">
                        <img src="${h.houseImage }" width="240px" height="180px">
                    </a>
                    <div class="show-details">
                        <p class="house-title">
                            <a href="${pageContext.request.contextPath}/detail.html?id=${h.houseId }">${h.houseDesc }</a>
                        </p>
                        <p class="house-about">
                            <span class="layui-icon layui-icon-home"></span>
                            <span>${h.houseModel}</span>
                            <span class="flag-line">|</span>
                            <span>${h.houseArea } m<sup>2</sup></span>
                            <span class="flag-line">|</span>
                            <span>普通装修</span>
                            <span class="flag-line">|</span>
                            <span>${h.houseFloor } 楼</span>
                            <span class="flag-line">|</span>
                            <span>${h.houseType }</span>
                        </p>
                        <p class="house-address clearfix">
                            <span class="layui-icon layui-icon-location"></span>
                            <span class="whole-line">
			    		        <span>${h.houseAddress}</span>
					        </span>
                        </p>
                        <div class="show-price">
                            <span class="sale-price">${h.housePrice}</span>
                            <span class="sale-unit">元/月</span>
                        </div>
                    </div>
                </li>
            </c:forEach>
        </ul>
    </div>
</section>

<%--底部信息--%>
<footer>
    <a href="${pageContext.request.contextPath}/admin/index.html">管理员登录</a>
    <p>2018-2021 &copy; 房屋租赁网 懂您的需求 服务于心</p>
</footer>

<script>
    layui.use(['element', 'carousel', 'layer', 'form'], function () {
        let element = layui.element,
            carousel = layui.carousel,
            $ = layui.jquery,
            layer = layui.layer,
            form = layui.form;

        let layer_index;
        carousel.render({
            elem: "#bg-item",
            width: "100%",
            height: "800px",
            anim: "fade"
        });
        $('.operation').click(function () {
            layer_index = layer.open({
                type: 1,
                content: $('#sign'),
                area: ['360px', '460px'],
                title: "房屋租赁网",
                closeBtn: 2
            });
        });

        //房屋筛选的高亮，但是好像因为会刷新一下
        $('.list-item li').click(function () {
            $('.list-item li').removeClass('click-this');
            $(this).addClass('click-this');
        });

        $('.regist-btn').click(function () {
            $.post("${pageContext.request.contextPath}/user/register", $('.form').serialize(), function (res) {
                if (res === 'OK') {
                    layer.close(layer_index);
                    layer.alert("注册成功", {icon: 1, time: 2000});
                    $('.form')[0].reset();
                } else {
                    layer.msg("注册失败,用户名以存在");
                }
            })
        });

        form.on("submit(login)", function () {
            $.post("${pageContext.request.contextPath}/user/login", $('#login').serialize(), function (res) {
                if (res === "OK") {
                    window.location.href = "/index.html";
                } else {
                    layer.msg("用户名或者密码错误");
                }
            });
            return false;
        });

        form.on("submit(formDemo)", function () {
            $.post("${pageContext.request.contextPath}/findHouseByHouseFilter", $("#formDemo").serialize(), function (res) {
                if (res === "OK") {
                    window.location.href = "/toIndex.html";
                }
            });
            return false;
        });
    });
</script>
</body>
</html>
