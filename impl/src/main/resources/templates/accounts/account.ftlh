<#include "../base/base.ftlh">

<#macro styles>
    <link rel="stylesheet" href="/css/account.css">
    <link rel="stylesheet" href="/css/header.css">
</#macro>

<#macro title>Lobanoff bank</#macro>

<#macro content>
    <div class="account-page">
        <#include "../base/header.ftlh">

        <div class="account-container">
            <div class="account-container-header">
                <div class="account-container-header-content">
                    <div class="account-container-go-back">
                        <a href="/profile" class="account-container-go-back-anchor">
                            <span class="account-container-go-back-span">
                                <svg viewBox="0 0 24 24" xmlns="http://www.w3.org/2000/svg" focusable="false"><path fill-rule="evenodd" clip-rule="evenodd" d="M6.867 10.975H20v.528a1.5 1.5 0 0 1-1.497 1.5l-11.636.022 3.422 3.472a1.5 1.5 0 0 1 0 2.106L9.898 19 3 12l6.898-7 .391.397a1.5 1.5 0 0 1 0 2.106l-3.422 3.472Z" fill="currentColor"></path></svg>
                            </span>
                        </a>
                    </div>
                    <div class="account-name-container">
                        <span id="account-name" class="account-name">${currentAccount.name}</span>
                        <div class="account-name-edit-container">
                            <span id="edit-account-name" class="account-name-edit">Edit</span>
                        </div>
                    </div>
                </div>
                <div class="account-deposit">
                    <span>${currentAccount.deposit} ₽</span>
                </div>
            </div>
            <div class="account-actions">
                <div class="account-action">
                    <a href="/transfers/${currentAccount.id}" style="text-decoration: none">
                        <div class="action-card">
                            <div class="action-card-icon"></div>
                            <div class="action-card-extend">
                                <div class="action-card-name">
                                    <span class="action-card-text">Transfer</span>
                                </div>
                            </div>
                        </div>
                    </a>
                </div>
                <div class="account-action">
                    <a href="/replenishes/${currentAccount.id}" style="text-decoration: none">
                        <div class="action-card">
                            <div class="action-card-icon"></div>
                            <div class="action-card-extend">
                                <div class="action-card-name">
                                    <span class="action-card-text">Replenish</span>
                                </div>
                            </div>
                        </div>
                    </a>
                </div>
                <div class="account-action">
                    <a href="/requisites/${currentAccount.id}" style="text-decoration: none">
                        <div class="action-card">
                            <div class="action-card-icon"></div>
                            <div class="action-card-extend">
                                <div class="action-card-name">
                                    <span class="action-card-text">Requisites</span>
                                </div>
                            </div>
                        </div>
                    </a>
                </div>
                <div class="account-action">
                    <a href="/account/${currentAccount.id}/statement" style="text-decoration: none">
                        <div class="action-card">
                            <div class="action-card-icon"></div>
                            <div class="action-card-extend">
                                <div class="action-card-name">
                                    <span class="action-card-text">Account statement</span>
                                </div>
                            </div>
                        </div>
                    </a>
                </div>
            </div>
            <div>
                <br>
                <br>

                <span>Linked cards:</span>
                <div>
                    <#if currentAccount.cards?has_content>
                        <#list currentAccount.cards as card>
                            <div class="account-card">
                                <span>·· ${card.number[card.number?length - 4..]}</span>
                            </div>
                        </#list>
                    </#if>
                    <div class="account-card">
                        <a href="/adding-card/${currentAccount.id}">
                            <span>Add a card</span>
                        </a>
                    </div>
                </div>
            </div>
        </div>

        <#include "../base/footer.ftlh">
    </div>
</#macro>

<#macro scripts>
    <script src="http://code.jquery.com/jquery-latest.min.js"></script>

    <script>
        $(document).ready(function () {
            $("#edit-account-name").on("click", function () {
                let accountName = $("#account-name");
                if (accountName.is('span')) {
                    let input = $("<input id='edit-account-name-input' class='account-name account-name-editor'>", { value: "${currentAccount.name}", type: "text" });
                    accountName.replaceWith(input);
                    input.select();
                    input.val("${currentAccount.name}");
                    let accountNameInput = $("#edit-account-name-input");

                    accountNameInput.keyup(function (event) {
                        if (event.keyCode === 13) {
                            updateName()
                        }
                    });

                    accountNameInput.blur(function () {
                        let spanName = $("<span id='account-name' class='account-name'>");
                        $("#edit-account-name-input").replaceWith(spanName);
                        spanName.text("${currentAccount.name}");
                    });
                }

                function updateName() {
                    let csrfHeader = $("meta[name='_csrf_header']").attr("content");
                    let csrfToken = $("meta[name='_csrf']").attr("content");

                    $.ajax({
                        type: "POST",
                        url: "/account/${currentAccount.id}?name=" + $("#edit-account-name-input").val(),
                        beforeSend: function(xhr) {
                            xhr.setRequestHeader(csrfHeader, csrfToken);
                        },
                        statusCode: {
                            200: function () {
                                window.location.replace("/account/${currentAccount.id}")
                            },
                            <#--404: function () {-->
                            <#--    window.location.replace("/account/${currentAccount.id}")-->
                            <#--}-->
                        }
                    });
                }
            });
        });
    </script>
</#macro>
