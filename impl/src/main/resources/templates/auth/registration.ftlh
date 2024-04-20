<#import "/spring.ftl" as spring />

<#include "../base/base.ftlh">

<#macro styles>
    <link rel="stylesheet" href="css/login.css" xmlns="http://www.w3.org/1999/html">
    <link rel="stylesheet" href="https://cdnjs.cloudflare.com/ajax/libs/font-awesome/5.15.3/css/all.min.css"/>
</#macro>

<#macro title>Lobanoff bank</#macro>

<#macro content>
<form method="POST" action="/register" id="registerForm">
    <div class="wrapper">
        <header>Registration Form</header>
        <div class="form">
            <div class="field email">
                <div class="input-area">
                    <input id="email" name="email" type="email" placeholder="Email Address">
                    <i class="icon fas fa-envelope"></i>
                    <i class="error error-icon fas fa-exclamation-circle"></i>
                </div>
                <#if registrationForm??>
                    <@spring.bind "registrationForm.email" />
                    <@spring.showErrors "<br/>" />
                    <br/>
                </#if>
            </div>
            <div class="field password">
                <div class="input-area">
                    <input id="password" name="password" type="password" placeholder="Password">
                    <i class="icon fas fa-lock"></i>
                    <i class="error error-icon fas fa-exclamation-circle"></i>
                </div>
                <#if registrationForm??>
                    <@spring.bind "registrationForm.password" />
                    <@spring.showErrors "<br/>" />
                    <br/>
                </#if>
            </div>
            <div class="field password">
                <div class="input-area">
                    <input id="confirmPassword" name="confirmPassword" type="password" placeholder="Confirm Password">
                    <i class="icon fas fa-lock"></i>
                    <i class="error error-icon fas fa-exclamation-circle"></i>
                </div>
                <#if registrationForm??>
                    <@spring.bind "registrationForm.confirmPassword" />
                    <@spring.showErrors "<br/>" />
                    <br/>
                </#if>
            </div>
            <input id="submit-button" type="submit" value="Register">
        </div>
        <div class="sign-txt">Already registered? <a href="/login">Sign in now</a></div>
    </div>
    <input type="hidden" name="${_csrf.parameterName}" value="${_csrf.token}"/>
</form>

<#--    <label for="email">Email: </label>-->
<#--    <input type="email" name="email" id="email"/><br/>-->
<#--    <#if registrationForm??>-->
<#--        <@spring.bind "registrationForm.email" />-->
<#--        <@spring.showErrors "<br/>" />-->
<#--        <br/>-->
<#--    </#if>-->
<#--    <label for="password">Password: </label>-->
<#--&lt;#&ndash;    <@spring.formInput "registrationForm.password"/>&ndash;&gt;-->
<#--&lt;#&ndash;    <@spring.showErrors "<br>"/>&ndash;&gt;-->
<#--&lt;#&ndash;    <br>&ndash;&gt;-->
<#--    <input type="password" name="password" id="password"/><br/>-->
<#--    <#if registrationForm??>-->
<#--        <@spring.bind "registrationForm.password" />-->
<#--        <@spring.showErrors "<br/>" />-->
<#--        <br/>-->
<#--    </#if>-->
<#--    <label for="confirmPassword">Confirm password: </label>-->
<#--    <input type="password" name="confirmPassword" id="confirmPassword"/><br/>-->
<#--    <#if registrationForm??>-->
<#--        <@spring.bind "registrationForm.confirmPassword" />-->
<#--        <@spring.showErrors "<br/>" />-->
<#--        <br/>-->
<#--    </#if>-->
<#--    <input type="submit" value="Register"/>-->
</#macro>

<#macro scripts>
    <script src="http://code.jquery.com/jquery-latest.min.js"></script>
    <script src="js/registration.js"></script>
</#macro>

<#--<!DOCTYPE html>-->
<#--<html xmlns="http://www.w3.org/1999/xhtml"-->
<#--      lang="eng">-->
<#--<head>-->
<#--    <title>Lobanoff bank</title>-->
<#--</head>-->
<#--<body>-->
<#--<h1>Registration</h1>-->
<#--<form method="POST" action="/register" id="registerForm">-->
<#--    <label for="email">Email: </label>-->
<#--    <input type="email" name="email" id="email"/><br/>-->
<#--    <#if registrationForm??>-->
<#--        <@spring.bind "registrationForm.email" />-->
<#--        <@spring.showErrors "<br/>" />-->
<#--        <br/>-->
<#--    </#if>-->
<#--    <label for="password">Password: </label>-->
<#--    <@spring.formInput "registrationForm.password"/>-->
<#--    <@spring.showErrors "<br>"/>-->
<#--    <br>-->
<#--    &lt;#&ndash;    <input type="password" name="password" id="password"/><br/>&ndash;&gt;-->
<#--    &lt;#&ndash;    <#if registrationForm??>&ndash;&gt;-->
<#--    &lt;#&ndash;        <@spring.bind "registrationForm.password" />&ndash;&gt;-->
<#--    &lt;#&ndash;        <@spring.showErrors "<br/>" />&ndash;&gt;-->
<#--    &lt;#&ndash;        <br/>&ndash;&gt;-->
<#--    &lt;#&ndash;    </#if>&ndash;&gt;-->
<#--    <label for="confirmPassword">Confirm password: </label>-->
<#--    <input type="password" name="confirmPassword" id="confirmPassword"/><br/>-->
<#--    <#if registrationForm??>-->
<#--        <@spring.bind "registrationForm.confirmPassword" />-->
<#--        <@spring.showErrors "<br/>" />-->
<#--        <br/>-->
<#--    </#if>-->
<#--    <input type="submit" value="Register"/>-->
<#--    <input type="hidden" name="${_csrf.parameterName}" value="${_csrf.token}"/>-->
<#--</form>-->
<#--</body>-->
<#--</html>-->
