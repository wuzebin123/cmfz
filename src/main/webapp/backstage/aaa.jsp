<%@page contentType="text/html; utf-8" pageEncoding="UTF-8" isELIgnored="false" %>
<!doctype html>
<html>
<head>
    <meta charset="UTF-8">
    <meta name="viewport"
          content="width=device-width, user-scalable=no, initial-scale=1.0, maximum-scale=1.0, minimum-scale=1.0">
    <meta http-equiv="X-UA-Compatible" content="ie=edge">
    <title>Document</title>
    <script src="../boot/js/jquery-2.2.1.min.js"></script>
    <script src="../echarts/echarts.min.js"></script>
    <script src="../echarts/china.js"></script>
    <script src=" http://cdn-hangzhou.goeasy.io/goeasy.js"></script>
    <script>

        var goEasy = new GoEasy({
            appkey: "BC-1d636fd677f043a183ea7b2437aba87b"
        });
        goEasy.subscribe({
            channel: "164channel",
            onMessage: function (message) {
                $("#div").append("<h1>" + message.content + "<h1>");
            }
        });

        function aa() {
            var val = $("#qq").val();
            $.ajax({
                url: "${pageContext.request.contextPath}/user/aaa?aa=" + val,
                datatype: "json",
                type: "POST",
                success: function (da) {
                    $("#qq").val("");
                }
            });
        }

        function bb() {
            $("#div").empty();
        }
    </script>
</head>
<body>
<center>
    <input id="qq" name="qq" type="text">
    <button onclick="aa()">提交</button>
    <button onclick="bb()">清除</button>
    <hr>
    <div id="div">

    </div>
</center>
</body>
</html>