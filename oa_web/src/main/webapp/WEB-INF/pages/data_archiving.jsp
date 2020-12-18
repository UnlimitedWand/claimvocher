<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
<%@ page contentType="text/html;charset=UTF-8" language="java" %>
<jsp:include page="top.jsp"/>

<script src="/javascript/click.js"></script>
<script src="/javascript/jquery.min.js"></script>
<script src="/javascript/echarts.js"></script>
<%--<body style=" text-align: center; ">--%>
<section>
    <div id="bar" style="width: 750px;height: 450px;"></div>
    <a href ="../self">返回首页</a>
</section>


<script>
    //初始化echarts
    var barCharts = echarts.init(document.getElementById("bar"));
    //设置属性
    barCharts.setOption({
        title: {
            text: "用户报销统计",
            subtext: "人员类型"
        },
        tooltip: {
            trigger: "axis",
            show: true
        },
        toolbox: {
            show: true,
            feature: {
                mark: {
                    show: true
                },
                dataView: {
                    show: true,
                    readOnly: true
                },
                magicType: {
                    show: false,
                    type: ["line", "bar"]
                },
                restore: {
                    show: true
                },
                saveAsImage: {
                    show: true
                }
            }
        },
        calculable: true,
        xAxis: [
            {
                type: "category",
                data: ["学生", "在职职工", "退休人员", "离休人员", "特殊群体"]
            }
        ],
        yAxis: [
            {
                type: "value"
            }
        ],
        series: [
            {
                type: "bar",
                name: "人员类型",
                data: [225, 230, 71, 33, 25]
            }
        ]
    });
</script>
<jsp:include page="bottom.jsp"/>

