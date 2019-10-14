<%@page isELIgnored="false" contentType="text/html; utf-8" pageEncoding="UTF-8" %>
<%@taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
<c:set var="app" value="${pageContext.request.contextPath}"></c:set>

<script>
    $(function () {
        $("#albumList").jqGrid({
            url: "${app}/album/findAll",
            colNames: ["ID", "标题", "分数", "作者", "波音员", "章节数", "专辑简介", "状态", "发行时间", "上传时间", "插图"],
            caption: "",
            editurl: "${app}/album/edit",
            datatype: "json",
            styleUI: "Bootstrap",
            autowidth: true,
            height: "40%",
            pager: "#albumPager",
            rowNum: 2,
            page: 1,
            rowList: [2, 3, 4],
            viewrecords: true,
            multiselect: true,
            subGrid: true,
            colModel: [
                {
                    name: "id", hidden: true
                },
                {
                    name: "title", editable: true
                },
                {
                    name: "fraction", editable: true
                },
                {
                    name: "author", editable: true
                },
                {
                    name: "beam", editable: true
                },
                {
                    name: "count"
                },
                {
                    name: "content", editable: true
                },
                {
                    name: "state", editable: true, edittype: "select", editoptions: {value: "未冻结:未冻结;冻结:冻结"}
                },
                {
                    name: "publish_date", formatter: "date"
                },
                {
                    name: "create_date", formatter: "date"
                },
                {
                    name: "cover", editable: true, editable: true, edittype: "file",
                    formatter: function (a, b, c) {
                        return "<img style='width:100px;height:50px' src='${app}/img/" + a + "'/>"
                    }
                }
            ],
            subGridRowExpanded: function (subgrid_id, albumId) {
                addSubGrid(subgrid_id, albumId);
            }
        }).jqGrid("navGrid", "#albumPager",
            {//处理页面几个按钮的样式
                search: false
            }, {//在编辑之前或者之后进行额外的操作
                closeAfterEdit: true,
                beforeShowForm: function (obj) {
                    obj.find("#fraction").attr("readonly", true);
                    obj.find("#cover").attr("disabled", true);
                }
            }, {//在添加数据 之前或者之后进行额外的操作
                closeAfterAdd: true,
                afterSubmit: function (response) {
                    var bannerId = response.responseText;
                    $.ajaxFileUpload({
                        url: "${app}/album/update",
                        fileElementId: "cover",
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

        function addSubGrid(subgrid_id, albumId) {
            var tableId = subgrid_id + "table";
            var divId = subgrid_id + "div";
            $("#" + subgrid_id).html(
                "<table id='" + tableId + "' ></table>" + "<div id='" + divId + "' ></div>"
            );
            $("#" + tableId).jqGrid({
                url: "${app}/chapter/findAll?id=" + albumId,
                colNames: ["ID", "标题", "大小", "时长", "上传时间", "音频", "操作"],
                caption: "",
                editurl: "${app}/chapter/edit?album_id=" + albumId,
                datatype: "json",
                styleUI: "Bootstrap",
                autowidth: true,
                height: "40%",
                pager: "#" + divId,
                rowNum: 2,
                page: 1,
                rowList: [2, 3, 4],
                viewrecords: true,
                multiselect: true,
                colModel: [
                    {
                        name: "id", hidden: true
                    },
                    {
                        name: "title", editable: true
                    },
                    {
                        name: "size"
                    },
                    {
                        name: "longs"
                    },
                    {
                        name: "create_date", formatter: "date"
                    },
                    {
                        name: "filepath", editable: true, editable: true, edittype: "file",
                        formatter: function (a, b, c) {
                            return "<p style='color: red'>" + a + "</p s>"
                        }
                    },
                    {
                        name: "", formatter: function (cellValue, options, rowObject) {
                            console.log(cellValue);
                            console.log(options);
                            console.log(rowObject);
                            return "<h4><a href='${app}/audio/" + rowObject.filepath + "'><span class='glyphicon glyphicon-play-circle'></span></a>" + "                   " +
                                "<a href='${app}/chapter/download?filepath=" + rowObject.filepath + "' ><span class='glyphicon glyphicon-download'></span></a></h4>"
                        }
                    },
                ]
            }).jqGrid("navGrid", "#" + divId,
                {//处理页面几个按钮的样式
                    search: false
                }, {//在编辑之前或者之后进行额外的操作
                    closeAfterEdit: true,
                    beforeShowForm: function (obj) {
                    }
                }, {//在添加数据 之前或者之后进行额外的操作
                    closeAfterAdd: true,
                    afterSubmit: function (response) {
                        var bannerId = response.responseText;
                        $.ajaxFileUpload({
                            url: "${app}/chapter/update",
                            fileElementId: "filepath",
                            data: {bannerId: bannerId},
                            success: function (data) {
                                $("#" + tableId).trigger("reloadGrid");
                                $("#albumList").trigger("reloadGrid");
                            }
                        });
                        return response
                    }

                }, {//在删除数据之前或者之后进行额外的操作
                    afterSubmit: function () {
                        $("#" + tableId).trigger("reloadGrid");
                        $("#albumList").trigger("reloadGrid");
                        return "aaa";
                    }

                })
        }
    })

</script>

<h3>专辑于章节管理</h3>
<div>
    <!-- Nav tabs -->
    <ul class="nav nav-tabs" role="tablist">
        <li role="presentation" class="active"><a href="#home" aria-controls="home" role="tab"
                                                  data-toggle="tab">专辑于章节信息</a></li>
    </ul>
    <!-- Tab panes -->
    <div class="tab-content">
        <div role="tabpanel" class="tab-pane active" id="home">
            <table id="albumList"></table>
            <div id="albumPager"></div>
        </div>
    </div>
</div>