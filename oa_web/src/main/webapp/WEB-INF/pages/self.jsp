<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
<%@page import="com.cmj.oa.global.Contant" %>
<%@ page contentType="text/html;charset=UTF-8" language="java" %>
<jsp:include page="top.jsp"/>

<section id="content" class="table-layout animated fadeIn">
    <div class="tray tray-center">
        <div class="content-header">
            <h2> 个人信息 </h2>
            <p class="lead"></p>
        </div>
        <div class="admin-form theme-primary mw450 center-block" style="padding-bottom: 175px;">
            <div class="panel heading-border">
                <div class="panel-body bg-light">
                    <div class="section-divider mt20 mb40">
                        <span> 基本信息 </span>
                    </div>
                    <div class="section row text-center">
                        <div>工号：${sessionScope.employee.sn}</div>
                    </div>
                    <div class="section row text-center">
                        <div>姓名：${sessionScope.employee.name}</div>
                    </div>
                    <div class="section row text-center">
                        <div >所属部门：${sessionScope.employee.department.name}</div>
                    </div>
                    <div class="section row text-center">
                        <div>职务：${sessionScope.employee.post}</div>
                    </div>
<%--                    <c:if test="${sessionScope.employee.post==Contant.POST_CAHIER}">--%>
                    <c:if test="${sessionScope.employee.post==Contant.POST_STAFF}">
                    <div>
                        <div class="section-divider mt40 mb40">
                            <span> 报销信息 </span>
                        </div>
                        <div class="section row text-center">
                            <div>年度累计报销总额：${sessionScope.employee.yearTotal}</div>
                        </div>
                    </div>
                    </c:if>

                    <div class="panel-footer text-right">
                        <button type="button" class="button" onclick="javascript:window.history.go(-1);"> 返回 </button>
                    </div>
                </div>
            </div>
        </div>
    </div>
</section>

<jsp:include page="bottom.jsp"/>
