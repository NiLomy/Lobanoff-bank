<#include "../base/base.ftlh">

<#macro styles>
    <link rel="stylesheet" href="css/login.css">
    <link rel="stylesheet" href="https://cdnjs.cloudflare.com/ajax/libs/font-awesome/5.15.3/css/all.min.css"/>
</#macro>

<#macro title>Lobanoff bank</#macro>

<#macro content>
    <div class="wrapper">
        <header>Login Form</header>
        <div class="form">
            <div class="field email">
                <div class="input-area">
                    <input id="email" name="email" type="email" placeholder="Email Address">
                    <i class="icon fas fa-envelope"></i>
                    <i class="error error-icon fas fa-exclamation-circle"></i>
                </div>
                <div id="e-error" class="error error-txt">Email can't be blank</div>
            </div>
            <div class="field password">
                <div class="input-area">
                    <input id="password" name="password" type="password" placeholder="Password">
                    <i class="icon fas fa-lock"></i>
                    <i class="error error-icon fas fa-exclamation-circle"></i>
                </div>
                <div class="error error-txt">Password can't be blank</div>
            </div>
            <div class="pass-txt"><a href="#">Forgot password?</a></div>
            <br>
            <input id="remember-me" type="checkbox" value="off"
                   name="remember-me">
            <label for="remember-me">Remember me</label>
            <br>
            <input id="submit-button" type="submit" value="Login">
        </div>
<#--        <form method="POST" action="/login" id="loginForm">-->
<#--            <div class="field email">-->
<#--                <div class="input-area">-->
<#--                    <input id="email" type="email" placeholder="Email Address">-->
<#--                    <i class="icon fas fa-envelope"></i>-->
<#--                    <i class="error error-icon fas fa-exclamation-circle"></i>-->
<#--                </div>-->
<#--                <div id="e-error" class="error error-txt">Email can't be blank</div>-->
<#--            </div>-->
<#--            <div class="field password">-->
<#--                <div class="input-area">-->
<#--                    <input id="password" type="password" placeholder="Password">-->
<#--                    <i class="icon fas fa-lock"></i>-->
<#--                    <i class="error error-icon fas fa-exclamation-circle"></i>-->
<#--                </div>-->
<#--                <div class="error error-txt">Password can't be blank</div>-->
<#--            </div>-->
<#--            <div class="pass-txt"><a href="#">Forgot password?</a></div>-->
<#--            <input id="submit-button" type="submit" value="Login">-->
<#--            <input type="hidden" name="${_csrf.parameterName}" value="${_csrf.token}"/>-->
<#--        </form>-->
        <div class="sign-txt">Not yet member? <a href="/register">Signup now</a></div>
    </div>
<#--    <h1>Login</h1>-->
<#--    <div>-->
<#--        Unable to login. Check your username and password.-->
<#--    </div>-->
<#--    <p>New here? Click-->
<#--        <a href="/register">here</a> to register.</p>-->
<#--    <form method="POST" action="/login" id="loginForm">-->
<#--        <label for="username">Username: </label>-->
<#--        <input type="text" name="username" id="username"/><br/>-->
<#--        <label for="password">Password: </label>-->
<#--        <input type="password" name="password" id="password"/><br/>-->
<#--        <label>-->
<#--            <input type="checkbox" name="remember-me">Remember me-->
<#--        </label>-->
<#--        <input id="submit-button" type="submit" value="Login"/>-->
<#--        <input type="hidden" name="${_csrf.parameterName}" value="${_csrf.token}"/>-->
<#--    </form>-->
</#macro>

<#macro scripts>
    <script src="http://code.jquery.com/jquery-latest.min.js"></script>
    <script src="js/login.js"></script>
</#macro>
