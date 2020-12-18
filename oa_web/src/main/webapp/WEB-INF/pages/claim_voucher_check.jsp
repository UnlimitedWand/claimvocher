<%@ taglib prefix="spring" uri="http://www.springframework.org/tags" %>
<%@ taglib prefix="C" uri="http://java.sun.com/jsp/jstl/core" %>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
<%@ taglib prefix="form" uri="http://www.springframework.org/tags/form" %>
<%@page import="com.cmj.oa.global.Contant" %>
<%@ page contentType="text/html;charset=UTF-8" language="java" %>

<jsp:include page="top.jsp"/>

<section id="content" class="table-layout animated fadeIn">
    <div class="tray tray-center">
        <div class="content-header">
            <h2> 处理报销单 </h2>
            <p class="lead"></p>
        </div>
        <div class="admin-form theme-primary mw1000 center-block" style="padding-bottom: 175px;">
            <div class="panel heading-border">
                <div class="panel-body bg-light">
                    <div class="section-divider mt20 mb40">
                        <span> 基本信息 </span>
                    </div>
                    <div class="section row">
                        <div class="col-md-1"></div>
                        <div class="col-md-4">事由：<span>${claimVoucher.cause}</span></div>
                        <div class="col-md-4">创建时间：<span><spring:eval expression="claimVoucher.createTime"/></span></div>
                    </div>
                    <div class="section row">
                        <div class="col-md-1"></div>
                        <div class="col-md-4">创建人：<span>${claimVoucher.creater.name}</span></div>
                        <div class="col-md-4">待处理人：<span>${claimVoucher.dealer.name}</span></div>
                        <div class="col-md-3">当前状态：<span>${claimVoucher.status}</span></div>
                    </div>
                    <div class="section-divider mt20 mb40">
                        <span> 费用明细 </span>
                    </div>
                    <div class="section row">
                        <C:forEach items="${items}" var="item">
                            <div class="col-md-10">
                                <div class="section row">
                                    <div class="col-md-1"></div>
                                    <div class="col-md-3">报销类型：<span>${item.item}</span></div>
                                    <div class="col-md-3">金额：<span>${item.amount}</span></div>
                                    <div class="col-md-3">备注：<span>${item.comment}</span></div>
                                </div>
                                <div class="section row">
                                    <div class="col-md-1"></div>
                                    <div class="col-md-3">医院：<span>${item.hospital}</span></div>
                                    <div class="col-md-3">科室：<span>${item.keshi}</span></div>
                                    <div class="col-md-3">挂号类型：<span>${item.guahaoType}</span></div>
                                </div>
                            </div>
                            <div class="col-md-2">
                                <button type="button" class="button" data-toggle="modal" data-target="#myModal">
                                    查看报销凭证
                                </button>
                            </div>
                            <!-- Modal 查看报销凭证的模态框 -->
                            <div class="modal fade bs-example-modal-lg" id="myModal" tabindex="-1" role="dialog" aria-labelledby="myModalLabel" >
                                <div class="modal-dialog modal-lg" role="document" >
                                    <div class="modal-content"  >
                                        <div class="modal-header">
                                            <button type="button" class="close" data-dismiss="modal" aria-label="Close"><span aria-hidden="true">&times;</span></button>
                                            <h4 class="modal-title" id="myModalLabel">报销凭证</h4>
                                        </div>
                                            <%--   报销凭证的轮播图  --%>
                                        <div class="modal-body" >
                                            <div id="carousel-example-generic" class="carousel slide" data-ride="carousel">
                                                <!-- Indicators -->
                                                <ol class="carousel-indicators">
                                                    <C:forEach items="${item.documentLists}" varStatus="st">
                                                        <c:if test="${st.index == 0}">
                                                            <li data-target="#carousel-example-generic" data-slide-to="${st.index}" class="active" ></li>
                                                        </c:if>
                                                        <c:if test="${st.index != 0}">
                                                            <li data-target="#carousel-example-generic" data-slide-to="${st.index}"></li>
                                                        </c:if>
                                                    </C:forEach>
                                                </ol>

                                                <!-- Wrapper for slides -->
                                                <div class="carousel-inner" role="listbox">
                                                    <C:forEach items="${item.documentLists}" var="list" varStatus="st">
                                                        <c:if test="${st.index == 0}">
                                                            <div class="item active">
                                                                <img src="${pageContext.request.contextPath}/assets/img/${list}" alt="图片不见了" style="width: 100%">
                                                                <div class="carousel-caption">
                                                                        ${list}  ${st.index}
                                                                </div>
                                                            </div>
                                                        </c:if>
                                                        <c:if test="${st.index != 0}">
                                                            <div class="item">
                                                                <img src="${pageContext.request.contextPath}/assets/img/${list}" alt="图片不见了" style="width: 100%">
                                                                <div class="carousel-caption">
                                                                        ${list}  ${st.index}
                                                                </div>
                                                            </div>
                                                        </c:if>
                                                    </C:forEach>
                                                </div>

                                                <!-- Controls -->
                                                <a class="left carousel-control" href="#carousel-example-generic" role="button" data-slide="prev">
                                                    <span class="glyphicon glyphicon-chevron-left" aria-hidden="true"></span>
                                                    <span class="sr-only">Previous</span>
                                                </a>
                                                <a class="right carousel-control" href="#carousel-example-generic" role="button" data-slide="next">
                                                    <span class="glyphicon glyphicon-chevron-right" aria-hidden="true"></span>
                                                    <span class="sr-only">Next</span>
                                                </a>
                                            </div>
                                        </div>
                                        <div class="modal-footer">
                                            <button type="button" class="btn btn-primary" data-dismiss="modal">关闭</button>
                                        </div>
                                    </div>
                                </div>
                            </div>


                        </C:forEach>
                    </div>
                    <div class="section row">
                        <div class="col-md-1"></div>
                        <div class="col-md-6">总金额：<span>${claimVoucher.totalAmount}</span></div>
                    </div>
                    <div class="section-divider mt20 mb40">
                        <span> 处理流程 </span>
                    </div>
                    <div class="section row">
                        <c:forEach items="${records}" var="record">
                            <div class="col-md-1"></div>
                            <div class="col-md-2">处理人：<span>${record.dealer.name}</span></div>
                            <div class="col-md-3">处理时间：<span><spring:eval expression="record.dealTime"/></span></div>
                            <div class="col-md-2">状态：<span>${record.dealWay}</span></div>
                            <div class="col-md-4">备注：<span>${record.comment}</span></div>
                        </c:forEach>
                    </div>
                    <form:form id="admin-form" name="addForm" action="/claim_voucher/check" modelAttribute="record">
                        <form:hidden path="claimVoucherId" />
                        <div class="panel-body bg-light">
                            <div class="section">
                                <label for="comment" class="field prepend-icon">
                                    <form:input path="comment" cssClass="gui-input" placeholder="备注..."/>
                                    <label for="comment" class="field-icon">
                                        <i class="fa fa-edit"></i>
                                    </label>
                                </label>
                            </div>
                            <div class="panel-footer text-right">

<%--                                <c:if test="${sessionScope.employee.post==Contant.POST_FM || sessionScope.employee.post==Contant.POST_GM}">--%>
<%--                                    <button type="submit" class="button" name="dealWay" value="${Contant.DEAL_PASS}" >${Contant.DEAL_PASS}</button>--%>
<%--                                    <button type="submit" class="button" name="dealWay" value="${Contant.DEAL_BACK}" >${Contant.DEAL_BACK}</button>--%>
<%--                                    <button type="submit" class="button" name="dealWay" value="${Contant.DEAL_REJECT}" >${Contant.DEAL_REJECT}</button>--%>
<%--                                </c:if>--%>
<%--                                <c:if test="${sessionScope.employee.post==Contant.POST_CAHIER}">--%>
<%--                                    <button type="submit" class="button" name="dealWay" value="${Contant.DEAL_PAID}" >${Contant.DEAL_PAID}</button>--%>
<%--                                </c:if>--%>
                                <%--    审核人员的审核操作--%>
                                <c:if test="${sessionScope.employee.post==Contant.POST_CAHIER}">
                                    <button type="submit" class="button" name="dealWay" value="${Contant.DEAL_APPROVE}" >${Contant.DEAL_APPROVE}</button>
                                    <button type="submit" class="button" name="dealWay" value="${Contant.DEAL_BACK}" >${Contant.DEAL_BACK}</button>
                                </c:if>
                                    <%--    审核负责人的审核操作--%>
                                <c:if test="${sessionScope.employee.post==Contant.POST_AUDITLEADER}">
                                    <button type="submit" class="button" name="dealWay" value="${Contant.DEAL_PASS}" >${Contant.DEAL_PASS}</button>
                                    <button type="submit" class="button" name="dealWay" value="${Contant.DEAL_REJECT}" >${Contant.DEAL_REJECT}</button>
                                </c:if>
                                     <%--    收单员的打款操作--%>
                                <c:if test="${sessionScope.employee.post==Contant.POST_FM}">
                                    <button type="submit" class="button" name="dealWay" value="${Contant.DEAL_PAID}" >${Contant.DEAL_PAID}</button>
                                </c:if>
                                <button type="button" class="button" onclick="javascript:window.history.go(-1);"> 返回 </button>
                            </div>
                        </div>
                    </form:form>

                </div>
            </div>
        </div>
    </div>
</section>

<jsp:include page="bottom.jsp"/>
