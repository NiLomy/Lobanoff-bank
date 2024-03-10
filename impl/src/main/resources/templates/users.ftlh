<!DOCTYPE html>
<html xmlns="http://www.w3.org/1999/xhtml"
      lang="eng">
<head>
    <title>Lobanoff bank</title>
</head>
<body>
<h1>Admin panel</h1>
<#if users ?has_content>
    <#list users as user2>
        <form method="post" action="/admin">
            <p>User №${user2.id} ${user2.email} ${user2.role}</p>
            <input type="submit" value="BAN">
            <input type="hidden" name="${_csrf.parameterName}" value="${_csrf.token}"/>
        </form>
    </#list>
</#if>
</body>
</html>
