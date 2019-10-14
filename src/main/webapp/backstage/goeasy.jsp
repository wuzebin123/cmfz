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
    <script src=" http://cdn-hangzhou.goeasy.io/goeasy.js "></script>
    <script>
        var goEasy = new GoEasy({
            appkey: "BC-41105467e17d4745869fc7051df656ee"
        });
        /*goEasy.subscribe({
            channel: "164channel",
            onMessage: function (message) {
                alert("Channel:" + message.channel + " content:" + message.content);
            }
        });*/
        $(function () {
            var pubMsg = "";

            $("#message-send").click(function () {
                var msg = $("#message-input").val();
                pubMsg = msg;
                goEasy.publish({
                    channel: "lts",
                    message: msg,
                    onSuccess: function () {
                        var msgDiv = $(
                            "<div style='float: right;background: green'>" +
                            msg +
                            "</div><br/><br/>"
                        );
                        $("#message-show").append(msgDiv);
                    }

                });
                $("#message-input").val("");


            });

            goEasy.subscribe({
                channel: "lts",
                onMessage: function (message) {
                    var subMsg = message.content;
                    if (pubMsg != subMsg) {
                        var msgDiv = $(
                            "<div style='float: left;background: gray'>" +
                            subMsg +
                            "</div><br/><br/>"
                        );
                        $("#message-show").append(msgDiv);
                    } else {
                        pubMsg = ""
                    }


                }
            });

        });


    </script>

</head>
<body>
<div style="width: 500px; height: 600px;border: solid red 1px ">
    <div id="message-show" style=" overflow-y:auto; width: 500px; height: 400px;border: solid blue 1px "></div>

    <div style="width: 500px; height: 200px;border: solid green 1px ">
        <textarea id="message-input" style="width: 493px;height: 150px"></textarea>

        <button id="message-send" style="float: right">发送</button>

    </div>
</div>
</body>
</html>