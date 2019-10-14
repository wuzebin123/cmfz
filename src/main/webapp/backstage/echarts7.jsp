<%@page contentType="text/html; utf-8" pageEncoding="UTF-8" isELIgnored="false" %>

<body>
<!-- 为 ECharts 准备一个具备大小（宽高）的 DOM -->
<div class="container-fluid">
    <div class="row">
        <div class="col-sm-1"></div>
        <div class="col-sm-10">
            <div id="main" style="width: 1200px;height:400px;"></div>
        </div>
        <div class="col-sm-1"></div>
    </div>
</div>
<script type="text/javascript">
    var days = new Array();
    //当天
    var Date8 = new Date();
    var Date9 = new Date(Date8.getTime()).toLocaleDateString();
    //前一天
    var Date7 = new Date(Date8.getTime() - 24 * 60 * 60 * 1000).toLocaleDateString();
    //前两天
    var Date6 = new Date(Date8.getTime() - 48 * 60 * 60 * 1000).toLocaleDateString();
    //前三天
    var Date5 = new Date(Date8.getTime() - 72 * 60 * 60 * 1000).toLocaleDateString();
    //前四天
    var Date4 = new Date(Date8.getTime() - 96 * 60 * 60 * 1000).toLocaleDateString();
    //前五天
    var Date3 = new Date(Date8.getTime() - 120 * 60 * 60 * 1000).toLocaleDateString();
    //前六天
    var Date2 = new Date(Date8.getTime() - 144 * 60 * 60 * 1000).toLocaleDateString();
    //前七天
    var Date1 = new Date(Date8.getTime() - 168 * 60 * 60 * 1000).toLocaleDateString();
    days.push(Date9, Date7, Date6, Date5, Date4, Date3, Date2);
    // 基于准备好的dom，初始化echarts实例
    var myChart = echarts.init(document.getElementById('main'));

    // 指定图表的配置项和数据
    var option = {
        title: {
            text: '最近7日用户注册信息'
        },
        tooltip: {},
        legend: {
            data: ['注册数量']
        },
        xAxis: {
            data: ["今日", "昨日", "2天前", "3天前", "4天前", "5天前", "6天前"]
        },
        yAxis: {},
        series: [{
            name: '注册数量',
            type: 'line'
        }]
    };

    // 使用刚指定的配置项和数据显示图表。
    myChart.setOption(option);

    $.ajax({
        url: "${pageContext.request.contextPath}/user/query",
        datatype: "json",
        type: "POST",
        success: function (da) {
            myChart.setOption({
                series: [{data: da}],
                xAxis: {data: days}
            });
        }
    });

</script>