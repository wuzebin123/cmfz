<%@page contentType="text/html; utf-8" pageEncoding="UTF-8" isELIgnored="false" %>

<script>
    $(function () {
        var esvalue = '${param.val}';
        $.ajax({
            url: "${pageContext.request.contextPath}/article/queryByes",
            datatype: "json",
            data: {esvalue: esvalue},
            type: "post",
            success: function (data) {
                $.each(data, function (index, value) {
                    console.log(data);
                    $("#esMsg").append(
                        '<div class="media">\n' +
                        '  <div class="media-left">\n' +
                        '    <a href="#">\n' +
                        '    </a>\n' +
                        '  </div>\n' +
                        '  <div class="media-body">\n' +
                        '    <h4 class="media-heading">' + value.title + '</h4>\n' +
                        '作者:  ' + value.author + '<br/>' +
                        '内容:  ' + value.content +
                        '    ...\n' +
                        '  </div>\n' +
                        '</div>'
                    )
                })
            }
        })
    })


</script>

<div id="esMsg"></div>