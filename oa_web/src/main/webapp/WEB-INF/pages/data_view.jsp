<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
<%@ page contentType="text/html;charset=UTF-8" language="java" %>
<jsp:include page="top.jsp"/>

<script src="/javascript/click.js"></script>
<script src="${pageContext.request.contextPath}/javascript/jquery.min.js"></script>
<script src="${pageContext.request.contextPath}/javascript/echarts.js"></script>

<section>
    <div id="pie" style="width: 750px;height: 450px;"></div>
    <a href ="../self">返回首页</a>
</section>


<script>
    //初始化echarts
    var pieCharts = echarts.init(document.getElementById("pie"));
    //设置属性
    pieCharts.setOption({
        title: {
            text: '性别统计',
            // subtext: '人员比例',
            x: 'center'
        },
        tooltip: {
            trigger: 'item',
            formatter: "{a} <br/>{b} : {c} ({d}%)"
        },
        legend: {
            orient: 'vertical',
            x: 'left',
            data: []
        },
        toolbox: {
            show: true,
            feature: {
                mark: {show: true},
                dataView: {show: true, readOnly: true},
                magicType: {
                    show: true,
                    type: ['bar', 'funnel'],
                    option: {
                        funnel: {
                            x: '25%',
                            width: '50%',
                            funnelAlign: 'left',
                            max: 1548
                        }
                    }
                },
                restore: {show: true},
                saveAsImage: {show: true}
            }
        },
        calculable: true,
        series: [
            {
                name: '性别统计',
                type: 'pie',
                radius: '55%',
                center: ['50%', '60%'],
                data: [{value:274, name:'男'},
                    {value:310, name:'女'}
                ]
            }
        ]
    });
    //显示一段动画
    // pieCharts.showLoading();
    //异步请求数据
    $.ajax({
        type: "post",
        async: true,
        url: '${pageContext.request.contextPath}/claim_voucher',
        data: [],
        dataType: "json",
        success: function (result) {
            pieCharts.hideLoading();//隐藏加载动画
            pieCharts.setOption({
                title: {
                    text: '学生成绩',
                    subtext: '成绩比',
                    x: 'center'
                },
                tooltip: {
                    trigger: 'item',
                    formatter: "{a} <br/>{b} : {c} ({d}%)"
                },
                legend: {
                    orient: 'vertical',
                    x: 'left',
                    data: []
                },
                toolbox: {
                    show: true,
                    feature: {
                        mark: {show: true},
                        dataView: {show: true, readOnly: false},
                        magicType: {
                            show: true,
                            type: ['pie', 'funnel'],
                            option: {
                                funnel: {
                                    x: '25%',
                                    width: '50%',
                                    funnelAlign: 'left',
                                    max: 1548
                                }
                            }
                        },
                        restore: {show: true},
                        saveAsImage: {show: true}
                    }
                },
                calculable: true,
                series: [
                    {
                        name: '成绩',
                        type: 'pie',
                        radius: '55%',
                        center: ['50%', '60%'],
                        data: result
                    }
                ]
            });
        }
    })
</script>

<jsp:include page="bottom.jsp"/>

