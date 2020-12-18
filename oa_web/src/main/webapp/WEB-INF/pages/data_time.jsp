<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
<%@ page contentType="text/html;charset=UTF-8" language="java" %>
<jsp:include page="top.jsp"/>

<script src="/javascript/click.js"></script>
<script src="/javascript/jquery.min.js"></script>
<script src="/javascript/echarts.js"></script>
<section>
    <div id="line" style="width: 750px;height: 450px;"></div>
    <a href ="../self">返回首页</a>
</section>


<script>
    //初始化echarts
    var lineCharts = echarts.init(document.getElementById("line"));
    //设置属性
    lineCharts.setOption({
        title: {
            text: "用户报销时间段统计",
            subtext: ""
        },
        tooltip: {
            trigger: "axis"
        },
        legend: {
            data: ["人数"]
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
                boundaryGap: false,
                data: ["0:00", "1:00", "2:00", "3:00", "4:00", "5:00", "6:00", "7:00", "8:00", "9:00", "10:00", "11:00", "12:00", "13:00", "14:00", "15:00", "16:00", "17:00", "18:00", "19:00", "20:00", "21:00", "22:00", "23:00", "24:00"]
            }
        ],
        yAxis: [
            {
                type: "value",
                name: ""
            }
        ],
        series: [
            {
                name: "人数",
                type: "line",
                data: [11, 12, 0, 0, 0, 0, 6, 2, 23, 86, 73, 58, 50, 63, 65, 54, 96, 113, 67, 86, 153, 136, 63, 65, 23]
            }
        ]
    });
</script>
<jsp:include page="bottom.jsp"/>

