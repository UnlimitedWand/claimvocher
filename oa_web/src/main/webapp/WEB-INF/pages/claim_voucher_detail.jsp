<%@ taglib prefix="spring" uri="http://www.springframework.org/tags" %>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
<%@ page import="com.cmj.oa.global.Contant" %>
<%@ page contentType="text/html;charset=UTF-8" language="java" %>

<jsp:include page="top.jsp"/>
<script type="text/javascript" src="/javascript/jquery.min.js"></script>
<script type="text/javascript" src="/javascript/generate_qrcode.js"></script>

<section id="content" class="table-layout animated fadeIn">
    <div class="tray tray-center">
        <div class="content-header">
            <h2> 报销单详情 </h2>
            <p class="lead"></p>
        </div>
        <div class="admin-form theme-primary mw1000 center-block" style="padding-bottom: 175px;">
            <div class="panel heading-border">
                <div class="panel-body bg-light">
                    <div class="section-divider mt20 mb20">
                        <span> 基本信息 </span>
                    </div>
                    <div class="section row">
                        <div class="col-md-9 mt30">
                            <div class="section row">
                                <div class="col-md-1"></div>
                                <div class="col-md-5">事由：<span>${claimVoucher.cause}</span></div>
                                <div class="col-md-3">创建人：<span>${claimVoucher.creater.name}</span></div>
                            </div>
                            <div class="section row">
                                <div class="col-md-1"></div>
                                <div class="col-md-5">创建时间：<span><spring:eval expression="claimVoucher.createTime"/></span></div>
                                <div class="col-md-3">当前状态：<span>${claimVoucher.status}</span></div>
                            </div>
                        </div>
                        <div class=" col-md-3">
<%--                            <div id="qrcode" style="width:100px; height:100px; margin-top:15px;" onload="makeCode()"></div>--%>
                            <%--这里有二维码 需要引用服务器的生成二维码的服务--%>
                            <c:if test="${sessionScope.employee.post==Contant.POST_STAFF}">
<%--                                <div id="qrcode" style="width:100px; height:100px; margin-top:15px;" onload="makeCode()"></div>--%>
                                <c:if test="${claimVoucher.status==Contant.CLAIMVOCHER_PASSED || claimVoucher.status==Contant.CLAIMVOCHER_PAID}">
                                    <div id="qrcode" style="width:100px; height:100px; margin-top:15px;"></div>
<%--                                    <img src="..."></img>--%>
                                </c:if>
                            </c:if>

                        </div>
                    </div>
                    <div class="section-divider mt20 mb40">
                        <span> 费用明细 </span>
                    </div>
                    <div class="section row">
                        <C:forEach items="${items}" var="item" varStatus="index">
                            <div class="col-md-10">
                                <div class="section row">
                                    <div class="col-md-1"></div>
                                    <div class="col-md-3">人员：<span>${item.item}</span></div>
                                    <div class="col-md-3">消费金额：<span>${item.amount}</span></div>
                                    <div class="col-md-3">备注：<span>${item.comment}</span></div>
                                </div>
                                <div class="section row">
                                    <div class="col-md-1"></div>
                                    <div class="col-md-3">医院：<span>${item.hospital}</span></div>
                                    <div class="col-md-3">科室：<span>${item.keshi}</span></div>
                                    <div class="col-md-3">挂号类型：<span>${item.guahaoType}</span></div>

                                </div>
                            </div>
                            <div>
                                <div class="col-md-2">
                                    <%-- 必须把模态框按钮的id赋予唯一的id 对应于模态框的id 否则容易多个按钮共同指向同一个模态框--%>
                                    <button type="button" class="button" data-toggle="modal" data-target="#myModal${item.id}">
                                        查看报销凭证
                                    </button>
                                </div>
                                <!-- Modal 查看报销凭证的模态框 -->
                                <div class="modal fade bs-example-modal-lg" id="myModal${item.id}" tabindex="-1" role="dialog" aria-labelledby="myModalLabel" >
                                    <div class="modal-dialog modal-lg" role="document" >
                                        <div class="modal-content"  >
                                            <div class="modal-header">
                                                <button type="button" class="close" data-dismiss="modal" aria-label="Close"><span aria-hidden="true">&times;</span></button>
                                                <h4 class="modal-title" id="myModalLabel">报销凭证</h4>
                                            </div>
                                                <%--   报销凭证的轮播图  --%>
                                            <div class="modal-body" >
                                                <div id="carousel-example-generic${item.id}" class="carousel slide" data-ride="carousel">
                                                    <!-- Indicators -->
                                                    <ol class="carousel-indicators">
                                                        <C:forEach items="${item.documentLists}" varStatus="st">
                                                            <c:if test="${st.index == 0}">
                                                                <li data-target="#carousel-example-generic${item.id}" data-slide-to="${st.index}" class="active" ></li>
                                                            </c:if>
                                                            <c:if test="${st.index != 0}">
                                                                <li data-target="#carousel-example-generic${item.id}" data-slide-to="${st.index}"></li>
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
                                                    <a class="left carousel-control" href="#carousel-example-generic${item.id}" role="button" data-slide="prev">
                                                        <span class="glyphicon glyphicon-chevron-left" aria-hidden="true"></span>
                                                        <span class="sr-only">Previous</span>
                                                    </a>
                                                    <a class="right carousel-control" href="#carousel-example-generic${item.id}" role="button" data-slide="next">
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
                            </div>

                        </C:forEach>
                    </div>
                    <div class="section row">
                        <div class="col-md-1"></div>
                        <div class="col-md-5">总金额：<span>${claimVoucher.totalAmount}</span></div>
                        <div class="col-md-4">可报销金额：<span id="yourMoney"></span></div>
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
                    <div class="panel-footer text-right">
                        <button type="button" class="button" onclick="javascript:window.history.go(-1);"> 返回 </button>
                    </div>
                </div>
            </div>
        </div>
    </div>
</section>
<script>
    function makeCode () {
        //var elText = document.getElementById("text");
        if(document.getElementById("qrcode")!=null){
            var qrcode = new QRCode(document.getElementById("qrcode"), {
                width : 100,
                height : 100
            });
            var elText ="/claim_voucher/to_check?id="+${claimVoucher.id};
            console.log(${claimVoucher.id});
            console.log("${sessionScope.employee.post}");
            console.log("${claimVoucher.status}");
            qrcode.makeCode(elText);
        }
    }
    <%--function calculateYourMoney() {--%>
    <%--    var p = document.getElementById("yourMoney");--%>
    <%--    var yearTotal = ${sessionScope.employee.yearTotal};--%>
    <%--    var yourMoney = 0;--%>
    <%--    for( let index in ${items}){--%>
    <%--        var amount = ${items}[index].amount;--%>
    <%--        switch(${items}[index].item){--%>
    <%--            case "学生":--%>
    <%--            case "在职人员":--%>
    <%--            case "特殊人员":--%>
    <%--                if(yearTotal >= 1300){--%>
    <%--                    yourMoney += amount*0.9;--%>
    <%--                }else--%>
    <%--                    yourMoney += amount*0.8;--%>
    <%--                break;--%>
    <%--            case "退休人员":--%>
    <%--                if(yearTotal >= 1300){--%>
    <%--                    yourMoney += amount;--%>
    <%--                }else--%>
    <%--                    yourMoney += amount*0.9;--%>
    <%--                break;--%>
    <%--            case "离休人员":--%>
    <%--                yourMoney += amount*0.9;--%>
    <%--                break;--%>
    <%--        }--%>
    <%--    }--%>
    <%--    p.appendChild("<div>"+yourMoney+"</div>");--%>
    <%--}--%>

    makeCode();
    // calculateYourMoney();
</script>
<jsp:include page="bottom.jsp"/>
