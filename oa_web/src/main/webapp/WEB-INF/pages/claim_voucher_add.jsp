<%@ taglib prefix="form" uri="http://www.springframework.org/tags/form" %>
<%@ page contentType="text/html;charset=UTF-8" language="java" %>

<jsp:include page="top.jsp"/>

<section id="content" class="table-layout animated fadeIn">
    <div class="tray tray-center">
        <div class="content-header">
            <h2> 填写报销单 </h2>
            <p class="lead"></p>
        </div>
        <div class="admin-form theme-primary mw1000 center-block" style="padding-bottom: 175px;">
            <div class="panel heading-border">
                <form:form id="admin-form" name="addForm" action="/claim_voucher/add" modelAttribute="info" enctype="multipart/form-data">
                    <div class="panel-body bg-light">
                        <div class="section-divider mt20 mb40">
                            <span> 基本信息 </span>
                        </div>
                        <div class="section">
                            <label for="claimVoucher.cause" class="field prepend-icon">
                                <form:input path="claimVoucher.cause" cssClass="gui-input" placeholder="事由..."/>
                                <label for="claimVoucher.cause" class="field-icon">
                                    <i class="fa fa-pencil-square"></i>
                                </label>
                            </label>
                        </div>
                        <div class="section-divider mt20 mb40">
                            <span> 费用明细 </span>
                        </div>
                        <div id="items">
                            <div>
                                <div class="section row">
                                    <div class="col-md-3">
                                        <label for="items[0].item" class="field prepend-icon">
                                            <label for="items[0].item" class="field-icon">
                                                <i class="fa fa-reorder"></i>
                                            </label>
                                            <form:select cssStyle="padding-left: 17%" path="items[0].item" cssClass="gui-input" placeholder="人员..." items="${items}"/>
                                        </label>
                                    </div>
                                    <div class="col-md-3">
                                        <label for="items[0].amount" class="field prepend-icon">
                                            <form:input path="items[0].amount" cssClass="gui-input money" placeholder="金额..."/>
                                            <label for="items[0].amount" class="field-icon">
                                                <i class="fa fa-jpy"></i>
                                            </label>
                                        </label>
                                    </div>
                                    <div class="col-md-5">
                                        <label for="items[0].comment" class="field prepend-icon">
                                            <form:input path="items[0].comment" cssClass="gui-input" placeholder="备注..." />
                                            <label for="items[0].comment" class="field-icon">
                                                <i class="fa fa-edit"></i>
                                            </label>
                                        </label>
                                    </div>
                                    <div class="col-md-1" style="text-align:right;">
                                        <button type="button" class="button"> X </button>
                                    </div>
                                </div>
                                <div class="section row">
                                    <div class="col-md-3">
                                        <label for="items[0].guahaoType" class="field prepend-icon">
                                            <form:input path="items[0].guahaoType" cssClass="gui-input" placeholder="挂号类型..." />
                                            <label for="items[0].guahaoType" class="field-icon">
                                                <i class="fa fa-h-square"></i>
                                            </label>
                                        </label>
                                    </div>
                                    <div class="col-md-3">
                                        <label for="items[0].hospital" class="field prepend-icon">
                                            <form:input path="items[0].hospital" cssClass="gui-input" placeholder="医院..."/>
                                            <label for="items[0].hospital" class="field-icon">
                                                <i class="fa fa-hospital-o"></i>
                                            </label>
                                        </label>
                                    </div>
                                    <div class="col-md-5">
                                        <label for="items[0].keshi" class="field prepend-icon">
                                            <form:input path="items[0].keshi" cssClass="gui-input" placeholder="科室..." />
                                            <label for="items[0].keshi" class="field-icon">
                                                <i class="fa fa-bed"></i>
                                            </label>
                                        </label>
                                    </div>
                                </div>
                                <div class="section row">

                                    <div class="col-md-3">
                                            <input type="file" id="pictures" name="pictures[0]"   multiple="multiple" accept="image/gif,image/jpeg,image/jpg,image/png,image/svg">
<%--                                            <form:input path="items[0].document" type="file" cssClass="btn btn-primary" placeholder="文件..."/>--%>
<%--                                            <label for="items[0].document" class="field-icon">--%>
<%--                                                <i class="fa fa-picture-o"></i>--%>
<%--                                            </label>--%>

                                    </div>
                                </div>
                            </div>
                        </div>

                        <div class="section row">
                            <div class="col-md-3">
                                <label for="totalMoney" class="field prepend-icon">
                                    <form:input id="totalMoney" path="claimVoucher.totalAmount" cssClass="gui-input" placeholder="总金额..." readonly="true"/>
                                    <label for="totalMoney" class="field-icon">
                                        <i class="fa fa-jpy"></i>
                                    </label>
                                </label>
                            </div>
                            <div class="section" style="text-align:right;">
                                <div class="col-md-9">
                                    <button type="button" class="button" id="addItemButton"> + </button>
                                </div>
                            </div>
                        </div>
                        <div class="panel-footer text-right">
                            <button type="submit" class="button"> 保存 </button>
                            <button type="button" class="button" onclick="javascript:window.history.go(-1);"> 返回 </button>
                        </div>
                    </div>
                </form:form>
            </div>
        </div>
    </div>
</section>

<jsp:include page="bottom.jsp"/>