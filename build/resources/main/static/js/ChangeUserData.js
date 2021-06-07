$('#options').click(function () {
    if (check) {
        check = false
        $('#photo').append(
            '<form id="changePhoto" action="/file/load?type=users&id=' + userId + '" method="post" enctype="multipart/form-data"' +
            '<div class="container justify-content-center" style="max-width: 200px; background-color: #212529; margin: 0; padding: 0">' +
            '<label for="loadFile" style="background-color: #212529; text-align: center;"><a style="color: #aaaaaa">Choose photo</a></label>' +
            '<input required id="loadFile" style="width: 0" type="file" name="file" placeholder="Choose photo"/>' +
            '<br/>' +
            '<input style="color: #aaaaaa; outline: 0; background-color: #212529; text-align: center; width: 200px" type="submit" value="Upload">' +
            '</div>' +
            '</form>' +
            '<div class="warningPhoto" style="color:darkred;"</div>')
        $('#nickname').append(
            '<form id="changeNick" action="/changedata/account?id=' + userId + '" method="post">' +
            '<div style="display: flex">' +
            '<input id= "nick" name="nickname" style="outline: 0; background-color: #212529; color: #aaaaaa" required type="text" maxlength="128" placeholder="New Nickname">' +
            '<button style="color: #aaaaaa" type="submit" class="btn">Change</button>' +
            '</div>' +
            '</form>' +
            '<div class="warningNick" style="color:darkred;"></div>')

        $('#email').append(
            '<form id="changeEmail" action="/changedata/account?id=' + userId + '" method="post">' +
            '<div style="display: flex">' +
            '<input id="changeEmailVal" name="email" style="outline: 0; background-color: #212529; color: #aaaaaa" required type="email" maxlength="256" placeholder="New email">' +
            '<button style="color: #aaaaaa" type="submit" class="btn">Change</button>' +
            '</div>' +
            '</form>' +
            '<div class="warningEmail" style="color:darkred;"></div>')

        $('#password').append(
            '<p>Password</p>' +
            '<form id="changePass" action="/changedata/account?id=' + userId + '" method="post">' +
            '<div style="display: block">' +
            '<input name="currentPassword" style="outline: 0; background-color: #212529; color: #aaaaaa" required type="password" maxlength="256" placeholder="Current pasword"><br>' +
            '<input id="newPassword" name="password" style="outline: 0; background-color: #212529; color: #aaaaaa" required type="password" maxlength="256" placeholder="New password"><br>' +
            '<input id="checkPassword" name="check" style="outline: 0; background-color: #212529; color: #aaaaaa" required type="password" maxlength="256" placeholder="Confirm password"><br>' +
            '<div class="error" style="color: darkred"></div>' +
            '<button id="changePassword" style="color: #aaaaaa" type="submit" class="btn">Change</button>' +
            '</div>' +
            '</form>' +
            '<div class="warningPass" style="color:darkred;"></div>')

        $('#posts').empty()

        $("#checkPassword").on("keyup", function () {

            var value_input1 = $("#newPassword").val();
            var value_input2 = $(this).val();

            if (value_input1 !== value_input2) {

                $(".error").html("Passwords don\'t match");
                $("#changePassword").attr("disabled", "disabled");

            } else {

                $("#changePassword").removeAttr("disabled");
                $(".error").html("");

            }
        });
        $(document).on("submit", "#changeNick", function (event) {
            var $form = $(this);
            $.post($form.attr("action"), $form.serialize(), function (response) {
                if (response == 'true') {
                    $(location).attr('href', '/account?user=' + $('#nick').val());
                } else {
                    $(".warningNick").html("Nickname already in use or consists of spaces!")
                }
            });
            event.preventDefault();
        });

        $(document).on("submit", "#changeEmail", function (event) {
            var $form = $(this);
            $.post($form.attr("action"), $form.serialize(), function (response) {
                if (response == 'true') {
                    $(location).attr('href', '/account?user=' + userNickname);
                } else {
                    $(".warningEmail").html("Email already in use or consists of spaces!")
                }
            });
            event.preventDefault();
        });

        $(document).on("submit", "#changePass", function (event) {
            var $form = $(this);
            $.post($form.attr("action"), $form.serialize(), function (response) {
                if (response == 'true') {
                    $(location).attr('href', '/account?user=' + userNickname);
                } else {
                    $(".warningPass").html("Password can\'t be made up spaces or wrong current")
                }
            });
            event.preventDefault();
        });
    }
})