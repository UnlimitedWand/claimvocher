<%--
  Created by IntelliJ IDEA.
  User: 范少
  Date: 2020/12/13
  Time: 21:05
  To change this template use File | Settings | File Templates.
--%>
<%@ page contentType="text/html;charset=UTF-8" language="java" %>
<html lang="en">
<head>
    <!-- Required meta tags -->
    <meta charset="utf-8">
    <meta name="viewport" content="width=device-width, initial-scale=1, shrink-to-fit=no">

    <!-- Bootstrap CSS -->
    <link rel="stylesheet" href="/assets/skin/default_skin/css/bootstrap.min.css">

    <!-- Loding font -->
    <link href="https://fonts.googleapis.com/css?family=Montserrat:300,700" rel="stylesheet">

    <!-- Custom Styles -->
    <link rel="stylesheet" type="text/css" href="/assets/skin/default_skin/css/styles.css">

    <title>登录</title>
</head>
<body>

<!-- Backgrounds -->

<div id="login-bg" class="container-fluid">

    <div class="bg-img"></div>
    <div class="bg-color"></div>
</div>

<!-- End Backgrounds -->

<div class="container" id="login">
    <div class="row justify-content-center">
        <div class="col-lg-8">
            <div class="login">

                <h1>欢迎使用报销系统</h1>

                <!-- Loging form -->
                <form>
                    <div class="form-group">
                        <input type="text" class="form-control" id="sn" name="sn"  placeholder="请输入工号：">

                    </div>
                    <div class="form-group">
                        <input type="password" class="form-control" id="password" name="password" placeholder="请输入密码：">
                    </div>

                    <div class="form-check">

                        <label class="switch">
                            <input type="checkbox" name="remember" id="remember">
                            <span class="slider round"></span>
                        </label>
                        <label class="form-check-label" >Remember me</label>

                        <!--				  <label class="forgot-password"><a href="#">Forgot Password?<a></label>-->

                    </div>

                    <br>
                    <button type="button" class="btn btn-lg btn-block btn-success" onclick="login()">登录</button>
                </form>
                <!-- End Loging form -->

            </div>Copyright &copy; 2020.你说的都队 All rights reserved.</a>
        </div>
    </div>
</div>
<script src="/javascript/login.js"></script>
<script src="/vendor/jquery/jquery_2_1.min.js" charset="UTF-8"></script>
<script src="/vendor/jquery/jquery_ui/jquery-ui.min.js"></script>

</body>
</html>