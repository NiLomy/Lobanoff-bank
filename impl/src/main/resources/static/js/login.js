let email = $("#email");
let password = $("#password");

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

$("#submit-button").on("click", function () {
    validateEmail();
    validatePassword();

    if (
        emailError === true &&
        passwordError === true
    ) {
        let email = $("#email").val();
        let password = $("#password").val();
        let rememberMe = $("#remember-me").val();

        let csrfHeader = $("meta[name='_csrf_header']").attr("content");
        let csrfToken = $("meta[name='_csrf']").attr("content");

        $.ajax({
            type: "POST",
            url: "/login",
            beforeSend: function(xhr) {
                xhr.setRequestHeader(csrfHeader, csrfToken);
            },
            data: {
                "email": email,
                "password": password,
                "remember-me": rememberMe
            },
            statusCode: {
                200: function () {
                    window.location.replace("/profile")
                }
            }
        });
    }
});


const form = document.querySelector("form");
eField = form.querySelector(".email"),
    eInput = eField.querySelector("input"),
    pField = form.querySelector(".password"),
    pInput = pField.querySelector("input");
form.onsubmit = (e)=>{
    e.preventDefault(); //preventing from form submitting
    //if email and password is blank then add shake class in it else call specified function
    (eInput.value == "") ? eField.classList.add("shake", "error") : checkEmail();
    (pInput.value == "") ? pField.classList.add("shake", "error") : checkPass();
    setTimeout(()=>{ //remove shake class after 500ms
        eField.classList.remove("shake");
        pField.classList.remove("shake");
    }, 500);
    eInput.onkeyup = ()=>{checkEmail();} //calling checkEmail function on email input keyup
    pInput.onkeyup = ()=>{checkPass();} //calling checkPassword function on pass input keyup
    function checkEmail(){ //checkEmail function
        let pattern = /^[^ ]+@[^ ]+\.[a-z]{2,3}$/; //pattern for validate email
        if(!eInput.value.match(pattern)){ //if pattern not matched then add error and remove valid class
            eField.classList.add("error");
            eField.classList.remove("valid");
            let errorTxt = eField.querySelector(".error-txt");
            errorTxt.style.display = "block";
            $("#e-error").style.display = "block";
            //if email value is not empty then show please enter valid email else show Email can't be blank
            (eInput.value != "") ? errorTxt.innerText = "Enter a valid email address" : errorTxt.innerText = "Email can't be blank";
        }else{ //if pattern matched then remove error and add valid class
            eField.classList.remove("error");
            eField.classList.add("valid");
        }
    }
    function checkPass(){ //checkPass function
        if(pInput.value == ""){ //if pass is empty then add error and remove valid class
            pField.classList.add("error");
            pField.classList.remove("valid");
        }else{ //if pass is empty then remove error and add valid class
            pField.classList.remove("error");
            pField.classList.add("valid");
        }
    }
    //if eField and pField doesn't contains error class that mean user filled details properly
    if(!eField.classList.contains("error") && !pField.classList.contains("error")){
        window.location.href = form.getAttribute("action"); //redirecting user to the specified url which is inside action attribute of form tag
    }
}

