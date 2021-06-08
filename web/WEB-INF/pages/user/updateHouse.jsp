<%@ page language="java" contentType="text/html; charset=utf-8"
         pageEncoding="utf-8" %>
<%@taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
<!DOCTYPE html>
<html lang="en">
<head>
    <meta charset="UTF-8">
    <title>修改房源信息</title>
    <link rel="stylesheet" href="${pageContext.request.contextPath}/layui/css/layui.css">
</head>
<body>
<div class="wrapper" style="width: 900px;margin-top: 40px">
    <fieldset class="layui-elem-field layui-field-title">
        <legend style="font-size: 26px">修改我发布的房源信息</legend>
    </fieldset>
    <form id="updateHouse" class="layui-form">
        <div class="layui-form-item">
            <label class="layui-form-label">房源标题</label>
            <div class="layui-input-block">
                <input type="hidden" value="${House.houseId}" name="houseId">
                <textarea name="houseDesc" placeholder="请输入内容" class="layui-textarea" required
                          lay-verify="required">${House.houseDesc}</textarea>
            </div>
        </div>
        <div class="layui-form-item">
            <label class="layui-form-label">房屋类型</label>
            <div class="layui-input-block">
                <input type="text" name="houseModel" required lay-verify="required" placeholder="几室几厅"
                       autocomplete="off" value="${House.houseModel}" class="layui-input">
            </div>
        </div>
        <div class="layui-form-item">
            <label class="layui-form-label">房屋面积</label>
            <div class="layui-input-block">
                <input type="text" name="houseArea" required lay-verify="required" placeholder=""
                       autocomplete="off" value="${House.houseArea}" class="layui-input">
            </div>
        </div>
        <div class="layui-form-item">
            <label class="layui-form-label">房屋楼层</label>
            <div class="layui-input-block">
                <input type="text" name="houseFloor" required lay-verify="required" placeholder="高层/低层/几楼"
                       autocomplete="off" value="${House.houseFloor}" class="layui-input">
            </div>
        </div>
        <div class="layui-form-item">
            <label class="layui-form-label">出租方式</label>
            <div class="layui-input-block">
                <select name="houseType" lay-verify="required">
                    <option value=""></option>
                    <option value="整租"<c:if test="${House.houseType == '整租'}"> selected</c:if>>整租</option>
                    <option value="合租"<c:if test="${House.houseType == '合租'}"> selected</c:if>>合租</option>
                </select>
            </div>
        </div>
        <div class="layui-form-item">
            <label class="layui-form-label">地&emsp;&emsp;址</label>
            <div class="layui-input-block">
                <input type="text" name="houseAddress" required lay-verify="required" placeholder=""
                       autocomplete="off" value="${House.houseAddress}" class="layui-input">
            </div>
        </div>
        <div class="layui-form-item">
            <label class="layui-form-label">小区名字</label>
            <div class="layui-input-block">
                <input type="text" name="communityName" required lay-verify="required" placeholder=""
                       autocomplete="off" value="${House.communityName}" class="layui-input">
            </div>
        </div>
        <div class="layui-form-item">
            <label class="layui-form-label">租房价格</label>
            <div class="layui-input-block">
                <input type="text" name="housePrice" required lay-verify="required|number" placeholder=""
                       autocomplete="off" value="${House.housePrice}" class="layui-input">
            </div>
        </div>
        <div class="layui-form-item">
            <label class="layui-form-label">联系电话</label>
            <div class="layui-input-block">
                <input type="text" name="houseLinkMan" required lay-verify="required|phone" placeholder=""
                       autocomplete="off" value="${House.houseLinkMan}" class="layui-input">
            </div>
        </div>
        <div class="layui-form-item">
            <label class="layui-form-label">房屋朝向</label>
            <div class="layui-input-block">
                <select name="houseOriented" lay-verify="required">
                    <option value=""></option>
                    <option value="南北"<c:if test="${House.houseOriented == '南北'}"> selected</c:if>>南北</option>
                    <option value="东西"<c:if test="${House.houseOriented == '东西'}"> selected</c:if>>东西</option>
                </select>
            </div>
        </div>
        <div class="layui-form-item">
            <div class="layui-input-block">
                <button class="layui-btn" lay-submit lay-filter="updateHouse" id="updateHouse-btn">立即修改</button>
                <button type="reset" class="layui-btn layui-btn-primary">重置</button>
            </div>
        </div>
    </form>

    <fieldset class="layui-elem-field layui-field-title" style="margin-top: 30px;">
        <legend>上传简介图片</legend>
    </fieldset>
    <div class="layui-form-item">
        <label class="layui-form-label">简介图片</label>
        <div class="layui-upload layui-input-block">
            <button type="button" class="layui-btn layui-btn-primary" id="briefImage">上传图片</button>
            <img id="simpleImg" width="60px" height="60px" src="${briefImage}">
        </div>
    </div>

    <fieldset class="layui-elem-field layui-field-title" style="margin-top: 30px;">
        <legend>上传多张详细图片</legend>
    </fieldset>
    <div class="layui-form-item">
        <label class="layui-form-label">详细图片</label>
        <div class="layui-upload layui-input-block">
            <button type="button" class="layui-btn layui-btn-primary" id="MultipleUpload">选择详细图片</button>
            <button type="button" class="layui-btn" id="testListAction">开始上传</button>
            <div class="layui-upload-list" lay-filter="demo">
                <table class="layui-table">
                    <thead>
                    <tr>
                        <th>文件名</th>
                        <th>预览</th>
                        <th>状态</th>
                        <th>操作</th>
                    </tr>
                    </thead>
                    <tbody id="detailsList">
                    <c:forEach items="${DetailsImg}" var="d" varStatus="ds">
                        <tr>
                            <td>已上传</td>
                            <td><img src="${d}" width="60px" height="60px"></td>
                            <td><span style="color: #1a62b8;">上传成功</span></td>
                            <td>
                                <button class="layui-btn layui-btn-xs layui-btn-danger"
                                        onclick="delDetailImg(${ds.index})">删除
                                </button>
                            </td>
                        </tr>
                    </c:forEach>
                    </tbody>
                </table>
            </div>
        </div>
    </div>
    <fieldset class="layui-elem-field layui-field-title" style="margin-top: 30px;">
        <legend>上传房产证与登记人身份证</legend>
    </fieldset>
    <div class="layui-form-item">
        <label class="layui-form-label">详细图片</label>
        <div class="layui-upload layui-input-block">
            <button type="button" class="layui-btn layui-btn-primary" id="privacyUpload">选择详细图片</button>
            <button type="button" class="layui-btn" id="privacyAction">开始上传</button>
            <div class="layui-upload-list">
                <table class="layui-table">
                    <thead>
                    <tr>
                        <th>文件名</th>
                        <th>预览</th>
                        <th>状态</th>
                        <th>操作</th>
                    </tr>
                    </thead>
                    <tbody id="privacyList">
                    <c:forEach items="${privacyImg}" var="d" varStatus="ds">
                        <tr>
                            <td>已上传</td>
                            <td><img src="${d}" width="60px" height="60px"></td>
                            <td><span style="color: #1a62b8;">上传成功</span></td>
                            <td>
                                <button class="layui-btn layui-btn-xs layui-btn-danger"
                                        onclick="delPrivacyImg(${ds.index})">删除
                                </button>
                            </td>
                        </tr>
                    </c:forEach>
                    </tbody>
                </table>
            </div>
        </div>
    </div>
    <div class="layui-form-item">
        <div class="layui-input-block">
            <a class="layui-btn layui-btn-primary" href="${pageContext.request.contextPath }/user/userRental.html">返回</a>
        </div>
    </div>
</div>
<script src="${pageContext.request.contextPath }/layui/layui.js"></script>
<script src="http://libs.baidu.com/jquery/1.9.0/jquery.js"></script>
<script>
    var layer = layui.layer;

    /**
     * 删除详细图片
     * @param index
     */
    function delDetailImg(index) {
        const url = "${pageContext.request.contextPath}/house/deleteHouseDetailsImg?houseId=${House.houseId}&index=" + index;
        layer.confirm('确认删除当前数据吗？', {icon: 5, shade: 0.1}, function () {
            $.post(url, function (success) {
                if (success === "OK") {
                    layer.msg("删除成功", {time: 1500});
                    location.reload();
                }
            })
        });
    }

    /**
     * 删除隐私图片
     * @param index
     */
    function delPrivacyImg(index) {
        const url = "${pageContext.request.contextPath}/house/deleteHousePrivacyImg?houseId=${House.houseId}&index=" + index;
        layer.confirm('确认删除当前数据吗？', {icon: 5, shade: 0.1}, function () {
            $.post(url, function (success) {
                if (success === "OK") {
                    layer.msg("删除成功", {time: 1500});
                    location.reload();
                }
            })
        });
    }

    layui.use(['element', 'form', 'layer', 'upload'], function () {
        let element = layui.element,
            form = layui.form,
            layer = layui.layer,
            upload = layui.upload,
            $ = layui.jquery;

        //简介图片上传
        upload.render({
            elem: "#briefImage"
            , url: "${pageContext.request.contextPath}/house/updateBriefImage?HouseId=${House.houseId}"
            , field: 'brief'
            , choose: function (obj) { //图片预览
                obj.preview(function (file, result) {
                    var reader = new FileReader();
                    reader.readAsDataURL(result);
                    reader.onload = function (event) {
                        $('#simpleImg').attr('src', event.target.result)
                    };
                });
            }
            , done: function (res, index, upload) {
                //假设code = 0代表上传成功
                const oSingleUp = $("#simpleImg");
                if (res.code === 0) {
                    layer.msg("文件异步加载成功！", {icon: 1});
                    //oSingleUp.attr("src", res.image);
                    oSingleUp.addClass("layui-btn-disabled");
                    oSingleUp.off("click");
                }
            }
        });

        //详细图
        let demoListView = $('#detailsList')
            , uploadListIns = upload.render({
            elem: '#MultipleUpload'
            , url: '${pageContext.request.contextPath}/house/updateDetailsImage?HouseId=${House.houseId}'
            , accept: 'file'
            , multiple: true
            , field: "detailsImage"
            , auto: false
            , bindAction: '#testListAction'
            , choose: function (obj) {
                let files = this.files = obj.pushFile(); //将每次选择的文件追加到文件队列
                //读取本地文件
                obj.preview(function (index, file, result) {
                    //文件展示表格
                    let tr = $(['<tr id="upload-' + index + '">'
                        , '<td>' + file.name + '</td>'
                        , '<td>' + (file.size / 1014).toFixed(1) + 'kb</td>'
                        , '<td>等待上传</td>'
                        , '<td>'
                        , '<button class="layui-btn layui-btn-xs demo-reload layui-hide">重传</button>'
                        , '<button class="layui-btn layui-btn-xs layui-btn-danger demo-delete">删除</button>'
                        , '</td>'
                        , '</tr>'].join(''));

                    //单个重传
                    tr.find('.demo-reload').on('click', function () {
                        obj.upload(index, file);
                    });

                    //删除
                    tr.find('.demo-delete').on('click', function () {
                        delete files[index]; //删除对应的文件
                        tr.remove();
                        uploadListIns.config.elem.next()[0].value = ''; //清空 input file 值，以免删除后出现同名文件不可选
                    });

                    demoListView.append(tr);
                });
            }
            , done: function (res, index, upload) {
                if (res.code === 0) { //上传成功
                    let tr = demoListView.find('tr#upload-' + index)
                        , tds = tr.children();
                    tds.eq(2).html('<span style="color: #5FB878;">上传成功</span>');
                    tds.eq(3).html(''); //清空操作
                    return delete this.files[index]; //删除文件队列已经上传成功的文件
                }
                this.error(index, upload);
            }
            , error: function (index, upload) {
                let tr = demoListView.find('tr#upload-' + index)
                    , tds = tr.children();
                tds.eq(2).html('<span style="color: #FF5722;">上传失败</span>');
                tds.eq(3).find('.demo-reload').removeClass('layui-hide'); //显示重传
            }
        });

        //相关证件图
        let privacyListView = $('#privacyList')
            , privacyUploadListIns = upload.render({
            elem: '#privacyUpload'
            , url: '${pageContext.request.contextPath}/house/updatePrivacyImage?HouseId=${House.houseId}'      //接口
            , accept: 'file'
            , multiple: true
            , field: "privacyImage"         //参数名
            , auto: false
            , bindAction: '#privacyAction'
            , choose: function (obj) {
                let files = this.files = obj.pushFile(); //将每次选择的文件追加到文件队列
                //读取本地文件
                obj.preview(function (index, file, result) {
                    //文件展示表格
                    let tr = $(['<tr id="upload-' + index + '">'
                        , '<td>' + file.name + '</td>'
                        , '<td>' + (file.size / 1014).toFixed(1) + 'kb</td>'
                        , '<td>等待上传</td>'
                        , '<td>'
                        , '<button class="layui-btn layui-btn-xs demo-reload layui-hide">重传</button>'
                        , '<button class="layui-btn layui-btn-xs layui-btn-danger demo-delete">删除</button>'
                        , '</td>'
                        , '</tr>'].join(''));

                    //单个重传
                    tr.find('.demo-reload').on('click', function () {
                        obj.upload(index, file);
                    });

                    //删除
                    tr.find('.demo-delete').on('click', function () {
                        delete files[index]; //删除对应的文件
                        tr.remove();
                        privacyUploadListIns.config.elem.next()[0].value = ''; //清空 input file 值，以免删除后出现同名文件不可选
                    });

                    privacyListView.append(tr);
                });
            }
            , done: function (res, index, upload) {
                if (res.code === 0) { //上传成功
                    let tr = privacyListView.find('tr#upload-' + index)
                        , tds = tr.children();
                    tds.eq(2).html('<span style="color: #5FB878;">上传成功</span>');
                    tds.eq(3).html(''); //清空操作
                    return delete this.files[index]; //删除文件队列已经上传成功的文件
                }
                this.error(index, upload);
            }
            , error: function (index, upload) {
                let tr = privacyListView.find('tr#upload-' + index)
                    , tds = tr.children();
                tds.eq(2).html('<span style="color: #FF5722;">上传失败</span>');
                tds.eq(3).find('.demo-reload').removeClass('layui-hide'); //显示重传
            }
        });

        form.on("submit(updateHouse)", function () {
            $.post("${pageContext.request.contextPath}/house/updateHouse", $("#updateHouse").serialize(), function (res) {
                $("#updateHouse")[0].reset();
                if (res === "OK") {
                    layer.msg("修改成功！", {
                        time: 1000, end: function () {
                            window.history.back(-1);
                        }
                    });
                }
            });
            return false;
        })
    });


</script>
</body>
</html>
