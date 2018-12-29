<%@ page language="java" contentType="text/html; charset=utf-8"
    pageEncoding="utf-8"%>

<!DOCTYPE html>
<html lang="ko">

<head>
    <meta charset="utf-8" />
    <title>Dashboard</title>
    <link rel="stylesheet" href="./css/bootstrap.css">
    <link href="./css/style.css" rel="stylesheet">
    <meta name="viewport" content="width=device-width, initial-scale=1">
</head>

<body>

    <div class="container-fluid">
        <h1 class="text-center">채팅 과제</h1>
        <button type="button" class="start_button btn btn-default btn-lg">
            <span class="glyphicon glyphicon-play" aria-hidden="true"></span> Start
        </button>
        <p class="result">결과 : </p>
    </div>

    <script src="https://cdnjs.cloudflare.com/ajax/libs/sockjs-client/1.3.0/sockjs.js"></script>
    <script src="https://cdnjs.cloudflare.com/ajax/libs/stomp.js/2.3.3/stomp.js"></script>
    <script src="https://code.jquery.com/jquery-3.3.1.js"></script>
    <script src="./js/bootstrap.js"></script>
    <script>

        let socket = new SockJS("./sock");
        let stompClient = Stomp.over(socket);

        function connect() {
            
            stompClient.debug = null;   //console log off

            stompClient.connect({}, function (frame) {
                console.log("connected : " + frame);
                stompClient.subscribe("/sub/dashboard", function (result) {
                    console.log("result : " + result);
                    $(".result").text = result;
                });
            });
        }

        function disconnect() {
            stompClient.disconnect();
            console.log("disconnected!!");
        }

        function send() {
            console.log("button");
            stompClient.send("/info/message", {}, "test message");
        }

        connect();

        $(document).ready(function() {
            $(".start_button").click(function() {
                send();
            });
        });


    </script>
</body>

</html>