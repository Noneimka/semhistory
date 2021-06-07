var connected = false;
var stompClient = null;

if (!connected) {
    connect()
}

window.onbeforeunload = function() {
    disconnect();
}

$(function () {
    $('#messanger').scrollTop($('#messanger').prop('scrollHeight'));

    $("form").on('submit', function (e) {
        e.preventDefault();
    });

    $("#send").click(function () {
        sendMsg();
        console.error("SENDED")
    });

    $(document).on("keyup", '#inputMessage', function() {
        current = this.value.length;
        $("#limit").text(current + "/512")
    })
});

function connect() {
    let socket = new SockJS('/messages-websocket');
    stompClient = Stomp.over(socket);
    stompClient.connect({}, function (frame) {
        console.error("CONNECTED")
        connected = true;
        console.log('Connected: ' + frame);
        stompClient.subscribe('/topic/messages', function (message) {
            showMessage(JSON.parse(message.body));
        });
    });
}

function disconnect() {
    if (stompClient !== null) {
        stompClient.disconnect();
    }
    connected = false;
    console.log("Disconnected");
}

function sendMsg() {
    stompClient.send("/app/messages", {}, JSON.stringify({'text': $("#inputMessage").val(), 'sender' : userId}));
}

function showMessage(message) {
    userDto = message.userDto
    var $messanger = $('#messanger')
    if (message.sender === userId) {
        $messanger.append(
            '<div class="col-12 float-right" style="margin: 0 0 0.5em 0; padding: 0">' +
            '<div class="float-right" style="display: inline-block; background-color: #2D3440; border-radius: 2em; min-width: 3em; padding: 0 1em 0 1em">' +
            '<p style="text-align: left; margin-bottom: 0">' + message.text + '</p>' +
            '<p class="text-muted" style="text-align: right; bottom: 0; font-size: x-small; float: bottom; margin-bottom: 0.1em">' + message.date + '</p>' +
            '</div>' +
            '</div>')
    } else {
        $messanger.append(
            '<div class="col-12">' +
            '<p style="text-align: left; margin-bottom: 0">' + userDto.name + '</p>' +
            '</div>' +
            '<div class="col-1">' +
            '<img src="/file?type=users&src=' +userDto.photo + '" width="35" height="35" ' +
            'style="float: left; border-radius: 5em">' +
            '</div>' +
            '<div class="col-10 float-left" style="margin: 0 0 0.5em 0">' +
            '<div style="display: inline-block; background-color: #262B2F; border-radius: 2em; min-width: 3em; padding: 0 1em 0 1em">' +
            '<p style="text-align: left; margin-bottom: 0">' + message.text + '</p>' +
            '<p class="text-muted" style="text-align: right; bottom: 0; font-size: x-small; float: bottom; margin-bottom: 0.1em">' + message.date + '</p>' +
            '</div>' +
            '</div>')
    }
}

