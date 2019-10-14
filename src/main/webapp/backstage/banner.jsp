<%@page isELIgnored="false" contentType="text/html; utf-8" pageEncoding="UTF-8" %>
<%@taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
<c:set var="app" value="${pageContext.request.contextPath}"></c:set>

<script>
    $(function () {
        $("#bannerList").jqGrid({
            url: "${app}/picture/findAll",
            colNames: ["编号", "标题", "状态", "描述", "创建时间", "图片"],
            caption: "",//标题
            editurl: "${app}/picture/edit",
            datatype: "json",
            styleUI: "Bootstrap",
            autowidth: true,
            height: "40%",
            pager: "#bannerPager",
            rowNum: 2,
            page: 1,
            rowList: [2, 3, 4],
            viewrecords: true,
            multiselect: true,
            colModel: [
                {
                    name: "id",
                },
                {
                    name: "title", editable: true
                },
                {
                    name: "state", editable: true, edittype: "select", editoptions: {value: "未冻结:未冻结;冻结:冻结"}
                },
                {
                    name: "describe", editable: true
                },
                {
                    name: "time", formatter: "date"
                },
                {
                    name: "src", editable: true, edittype: "file",
                    formatter: function (a, b, c) {
                        return "<img style='width:100px;height:50px' src='${app}/img/" + a + "'/>"
                    }
                }
            ]
        }).jqGrid("navGrid", "#bannerPager",
            {//处理页面几个按钮的样式
                search: false
            }, {//在编辑之前或者之后进行额外的操作
                closeAfterEdit: true,
                beforeShowForm: function (obj) {
                    obj.find("#title").attr("readonly", true);
                    obj.find("#describe").attr("readonly", true);
                    obj.find("#src").attr("disabled", true);
                }
            }, {//在添加数据 之前或者之后进行额外的操作
                closeAfterAdd: true,
                afterSubmit: function (response) {
                    var bannerId = response.responseText;
                    $.ajaxFileUpload({
                        url: "${app}/picture/update",
                        fileElementId: "src",
                        data: {bannerId: bannerId},
                        success: function (data) {
                            $("#bannerList").trigger("reloadGrid")
                        }
                    });
                    return response
                }

            }, {//在删除数据之前或者之后进行额外的操作

            }
        )
    })

</script>

<h3>轮播图管理</h3>
<div>
    <!-- Nav tabs -->
    <ul class="nav nav-tabs" role="tablist">
        <li role="presentation" class="active"><a href="#home" aria-controls="home" role="tab"
                                                  data-toggle="tab">轮播图信息</a></li>
        <li role="presentation"><a href="${app}/picture/dc">导出</a></li>
    </ul>
    <!-- Tab panes -->
    <div class="tab-content">
        <div role="tabpanel" class="tab-pane active" id="home">
            <table id="bannerList"></table>
            <div id="bannerPager"></div>
        </div>
    </div>
</div>