<#include "base.ftlh">

<#macro styles>
    <link rel="stylesheet" href="/css/creating_card.css">
    <link rel="stylesheet" href="/css/header.css">
</#macro>

<#macro title>Lobanoff bank</#macro>

<#macro content>
    <div class="card-page">
        <#include "header.ftlh">

        <div>
            <span id="create-card" style="cursor: pointer">Create card</span>
        </div>

        <#include "footer.ftlh">
    </div>
</#macro>

<#macro scripts>
    <script src="http://code.jquery.com/jquery-latest.min.js"></script>

    <script>
        $(document).ready(function () {
            $("#create-card").on("click", function () {
                let csrfHeader = $("meta[name='_csrf_header']").attr("content");
                let csrfToken = $("meta[name='_csrf']").attr("content");

                $.ajax({
                    type: "POST",
                    url: "/adding-card/${currentAccount.id}",
                    beforeSend: function(xhr) {
                        xhr.setRequestHeader(csrfHeader, csrfToken);
                    },
                    statusCode: {
                        200: function () {
                            window.location.replace("/account/${currentAccount.id}")
                        },
                    }
                });
            });
        });
    </script>
</#macro>
