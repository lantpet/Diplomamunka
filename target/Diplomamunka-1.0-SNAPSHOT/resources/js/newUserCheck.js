function validateForm() {
    var okay = true;
    var emailOkay;

    var name = $("#fullName").val();
    var age = $("#age").val();
    var password = $("#password").val();
    var username = $("#username").val();
    
    
    
    if (name == "" || name == null) {
        $("#fullname_error").html(fullNameEmpty);
        okay = false;
    } else
        $("#fullname_error").html("");
    
    if (age == "" || age == null) {
        $("#age_error").html(ageEmpty);
        okay = false;
    } else
        $("#age_error").html("");
    
    if (password == "" || password == null) {
        $("#password_error").html(passwordEmpty);
        okay = false;
    } else
        $("#password_error").html("");
    
    if (username == "" || username == null) {
        $("#username_error").html(userNameEmpty);
        okay = false;
    } else
        $("#username_error").html("");

    var email = $("#email").val();

    var atpos = email.indexOf("@");
    var dotpos = email.lastIndexOf(".");
    if (atpos < 1 || dotpos < atpos + 2 || dotpos + 2 >= email.length) {
        $("#email_error").html(wrongInput);
        okay = false;
    } else
        $("#email_error").html("");

    $.ajax({
        url: 'email_is_in_use.' + email,
        success: function (data) {
            emailOkay = !(data == "true");
            if (!emailOkay) {
                $("#email_error").html(emailAlreadyExist);
            } else {
                if (okay) {
                    $("#email_error").html("");
                    $("#newUserIdentifier").submit();
                }
            }
        }
    });
}
