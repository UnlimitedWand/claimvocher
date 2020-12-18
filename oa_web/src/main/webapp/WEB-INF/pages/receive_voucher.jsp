<%@ taglib prefix="spring" uri="http://www.springframework.org/tags" %>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
<%@ page import="com.cmj.oa.global.Contant" %>
<%@ page contentType="text/html;charset=UTF-8" language="java" %>
<jsp:include page="top.jsp"/>
<!--添加 layui  支持加载-->
<link rel="stylesheet"	href="/static/layui-v2.4.5/layui/css/layui.css">
<script src="/static/layui-v2.4.5/layui/layui.js" charset="UTF-8"></script>

<script type="text/javascript" src="/javascript/click.js"></script>
<section id="content" class="table-layout animated fadeIn">
    <div class="tray tray-center">
        <div class="content-header">
            <h2> 收单 </h2>
            <p class="lead"></p>
        </div>
        <link rel="stylesheet" type="text/css" href="/javascript/my.css">
        <div class="panel-footer text-center">
            <div class="qr-btn" node-type="qr-btn" >扫描二维码
                <input node-type="jsbridge" type="file" name="image" id="upload" value="扫描二维码" />
            </div>
        </div>
        <div class="result-qrcode text-center"></div>
        <script src="/javascript/compress.js" charset="GB2312"></script>
        <script src="/javascript/lrz.bundle.js"></script>
        <script src="/vendor/jquery/jquery_2_1.min.js" charset="UTF-8"></script>
        <script src="/javascript/qrcode.js"></script>
        <script>
            $(function() {
                Qrcode.init($('[node-type=qr-btn]'));
            });

                const upload = document.getElementById('upload')
                upload.onchange = uploadImage



        </script>
    </div>
</section>
<jsp:include page="bottom.jsp"/>
