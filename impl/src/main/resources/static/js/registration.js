let email = $("#email");
let password = $("#password");
let confirmPassword = $("#confirmPassword");

let emailError = true
email.keyup(function () {
    validateEmail();
})

function validateEmail() {
    let s = email.val().trim();
    if (s.length === 0) {
        // $("#email-error").text("You should enter your email")
        $("#email").addClass("is-invalid");
        emailError = false;
        return false;
    }
    var regex = /(?:[a-z0-9!#$%&'*+/=?^_`{|}~-]+(?:\.[a-z0-9!#$%&'*+/=?^_`{|}~-]+)*|"(?:[\x01-\x08\x0b\x0c\x0e-\x1f\x21\x23-\x5b\x5d-\x7f]|\\[\x01-\x09\x0b\x0c\x0e-\x7f])*")@(?:(?:[a-z0-9](?:[a-z0-9-]*[a-z0-9])?\.)+[a-z0-9](?:[a-z0-9-]*[a-z0-9])?|\[(?:(?:25[0-5]|2[0-4][0-9]|[01]?[0-9][0-9]?)\.){3}(?:25[0-5]|2[0-4][0-9]|[01]?[0-9][0-9]?|[a-z0-9-]*[a-z0-9]:(?:[\x01-\x08\x0b\x0c\x0e-\x1f\x21-\x5a\x53-\x7f]|\\[\x01-\x09\x0b\x0c\x0e-\x7f])+)\])/;
    if (regex.test(s)) {
        // $("#email-error").text("")
        $("#email").removeClass("is-invalid");
        emailError = true;
    } else {
        // $("#email-error").text("Invalid email")
        $("#email").addClass("is-invalid");
        emailError = false;
    }
}

let passwordError = true;
password.keyup(function () {
    validatePassword();
});

function validatePassword() {
    let passwordValue = password.val();
    if (passwordValue.length === 0) {
        // $("#password-error").text("You should enter your password");
        $("#password").addClass("is-invalid");
        passwordError = false;
        return false;
    }
    // if (passwordValue.length < 8) {
    //     // $("#password-error").text("Password should be at least 8 characters long");
    //     $("#password").addClass("is-invalid");
    //     passwordError = false;
    //     return false;
    // } else {
    //     // $("#password-error").text("");
    //     $("#password").removeClass("is-invalid");
    //     passwordError = true;
    // }
    // var regex = /^(?=.*?[a-z])(?=.*?[0-9]).{8,}$/;
    // if (regex.test(passwordValue)) {
    //     $("#password-error").text("")
    //     $("#password").removeClass("is-invalid");
    //     passwordError = true;
    // } else {
    //     $("#password-error").text("Too weak password. Please use letters and digits");
    //     $("#password").addClass("is-invalid");
    //     passwordError = false;
    // }
}

let confirmPasswordError = true;
password.keyup(function () {
    validateConfirmPassword();
});

function validateConfirmPassword() {
    let confirmPasswordValue = password.val();
    if (confirmPasswordValue.length === 0) {
        // $("#password-error").text("You should enter your password");
        $("#password").addClass("is-invalid");
        confirmPasswordError = false;
        return false;
    }
    if (confirmPasswordValue !== password.val()) {
        confirmPasswordError = false;
        return false;
    }
    // if (passwordValue.length < 8) {
    //     // $("#password-error").text("Password should be at least 8 characters long");
    //     $("#password").addClass("is-invalid");
    //     passwordError = false;
    //     return false;
    // } else {
    //     // $("#password-error").text("");
    //     $("#password").removeClass("is-invalid");
    //     passwordError = true;
    // }
    // var regex = /^(?=.*?[a-z])(?=.*?[0-9]).{8,}$/;
    // if (regex.test(passwordValue)) {
    //     $("#password-error").text("")
    //     $("#password").removeClass("is-invalid");
    //     passwordError = true;
    // } else {
    //     $("#password-error").text("Too weak password. Please use letters and digits");
    //     $("#password").addClass("is-invalid");
    //     passwordError = false;
    // }
}

$("#submit-button").on("click", function () {
    validateEmail();
    validatePassword();
    // validateConfirmPassword();

    if (
        emailError === true &&
        passwordError === true
        // confirmPasswordError === true
    ) {
        let email = $("#email").val();
        let password = $("#password").val();

        let csrfHeader = $("meta[name='_csrf_header']").attr("content");
        let csrfToken = $("meta[name='_csrf']").attr("content");

        $.ajax({
            type: "POST",
            url: "/register",
            beforeSend: function(xhr) {
                xhr.setRequestHeader(csrfHeader, csrfToken);
            },
            data: {
                "email": email,
                "password": password,
                "confirmPassword": confirmPassword
            },
            statusCode: {
                200: function () {
                    window.location.replace("/login")
                }
            }
        });
    }
});
