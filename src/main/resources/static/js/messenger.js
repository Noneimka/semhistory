$(document).on("click", '.dialog', function () {
    if (!$(this).hasClass('selected')) {
        $('.dialog').removeClass('selected');
        $(this).addClass('selected');
        (document).getElementById('messanger').setAttribute('style', 'display: inline-block;')
        let recipientId = (this).getAttribute("id")
        writeMessage(recipientId)
    }
})

$(document).on("click", ".search", function () {
    let recipientId = (this).getAttribute("id")
    $('.dialog').removeClass("displayNone")
    $('#searchResult').removeClass("displayInline");
    $('#searchResult').addClass("displayNone");
    (document).getElementById('messanger').setAttribute('style', 'display: inline-block;')
    writeMessage(recipientId)
});

function writeMessage(recipientId) {
    $.get("/message?user=" + recipientId + "&sender=" + userId + "", function (json) {
        var $messanger = $('#messanger')
        $messanger.empty()
        $('#MessageForm').remove()
        $.each(json, function (index, ms) {
            if (ms.sender == userId) {
                $messanger.append(
                    '<div class="col-12 float-right" style="margin: 0 0 0.5em 0; padding: 0">' +
                    '<div class="float-right" style="display: inline-block; background-color: #2D3440; border-radius: 2em; min-width: 3em; padding: 0 1em 0 1em">' +
                    '<p style="text-align: left; margin-bottom: 0">' + ms.message + '</p>' +
                    '<p class="text-muted" style="text-align: right; bottom: 0; font-size: x-small; float: bottom; margin-bottom: 0.1em">' + ms.sent_time + '</p>' +
                    '</div>' +
                    '</div>')
            } else {
                $messanger.append(
                    '<div class="col-12 float-left" style="margin: 0 0 0.5em 1em">' +
                    '<div style="display: inline-block; background-color: #262B2F; border-radius: 2em; min-width: 3em; padding: 0 1em 0 1em">' +
                    '<p style="text-align: left; margin-bottom: 0">' + ms.message + '</p>' +
                    '<p class="text-muted" style="text-align: right; bottom: 0; font-size: x-small; float: bottom; margin-bottom: 0.1em">' + ms.sent_time + '</p>' +
                    '</div>' +
                    '</div>')
            }

            $messanger.scrollTop($messanger.prop('scrollHeight'));
        });
        $('#forForm').append(
            '<form id="MessageForm" action="/message" method="post">' +
            '<div class="container-fluid row" style="border-top: 1px solid #aaaaaa; margin: 0; padding: 1em 0 0 0;">' +
            '<div class="col-10" style="margin: 0; padding-right: 0">' +
            '<textarea id="inputMessage" required name="message" placeholder="Write message" maxlength="512"></textarea>' +
            '<input name="recipientId" style="display: none" id="recipientId" value="' + recipientId + '">' +
            '<p id="limit" class="text-muted" style="font-size: small; float: right; color: #aaaaaa;"></p>' +
            '</div>' +
            '<div class="col-2" style="margin: 0; padding-left: 0.2em;">' +
            '<input id="sent" type="submit" style="outline: 0; float: right; border-radius: 2em; padding: 0.5em; background-color: #2b539e; width: 100%" value="Sent">' +
            '</div>' +
            '</div>' +
            '</form>'
        )
    });
}

$(document).on("submit", "#MessageForm", function (event) {
    var $form = $(this);
    $.post($form.attr("action"), $form.serialize(), function (response) {
    });
    writeMessage($('#recipientId').attr('value'))
    event.preventDefault();
});

$(document).on("submit", "#searchUser", function (event) {
    $.get("/message?search=1&nickname=" + $('#nickname').val(), function (response) {
        if (response.length > 0) {
            let search = $('#searchResult')
            search.removeClass("displayNone")
            search.addClass("displayInline")
            search.empty()
            $('.dialog').addClass("displayNone")

            $.each(response, function (index, user) {
                $('#searchResult').append(
                    '<div class="row container-fluid search"' +
                    'style="cursor: pointer; margin: 0 0 0.5em 0; border-bottom: 1px solid #0a0a0a;" id="' + user.id + '">' +
                    '<div class="col-4">' +
                    '<img src="/file/' + user.photo + '" width="35" ' +
                    'height="35" style="border-radius: 5em">' +
                    '</div>' +
                    '<div class="col-7" style="text-align: left; padding: 0">' +
                    '<h5 style="text-align: end; color: #0a0a0a">' + user.nickname + '</h5>' +
                    '</div>' +
                    '</div>'
                )
            })
        }
    });
    event.preventDefault();
});

document.addEventListener('click', function (event) {
    let search = document.getElementById('searchResult');
    let dialog = $('.dialog')
    if (!search.contains(event.target)) {
        dialog.removeClass("selected");
        dialog.removeClass("displayNone")
        $('#searchResult').removeClass("displayInline")
        $('#searchResult').addClass("displayNone")
    }
});

$(document).on("keyup", '#inputMessage', function() {
    current = this.value.length;
    $("#limit").text(current + "/512")
})


