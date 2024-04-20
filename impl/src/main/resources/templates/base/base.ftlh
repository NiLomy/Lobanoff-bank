<!DOCTYPE html>
<html lang="en">
<head>
    <meta charset="UTF-8">
    <#if _csrf.token? has_content>
        <meta name="_csrf" content="${_csrf.token}"/>
        <meta name="_csrf_header" content="${_csrf.headerName}"/>
    </#if>
    <title><@title></@title></title>

    <@styles></@styles>
    <script src="https://kit.fontawesome.com/b8991598b2.js"></script>
    <script src="https://cdn.jsdelivr.net/npm/@popperjs/core@2.11.8/dist/umd/popper.min.js"></script>
    <script src="https://cdn.jsdelivr.net/npm/bootstrap@5.3.2/dist/js/bootstrap.bundle.min.js"></script>
    <link rel="stylesheet" href="https://cdn.jsdelivr.net/npm/bootstrap@5.3.2/dist/css/bootstrap.min.css">
</head>
<body>

<div class="content">
    <@content></@content>
</div>
</body>
<@scripts></@scripts>
</html>
