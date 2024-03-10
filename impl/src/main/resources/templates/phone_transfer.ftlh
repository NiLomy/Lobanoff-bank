<#include "base.ftlh">

<#macro styles>
    <link rel="stylesheet" href="/css/transfers.css">
    <link rel="stylesheet" href="/css/header.css">
</#macro>

<#macro title>Lobanoff bank</#macro>

<#macro content>
    <div class="transfer-page">
        <#include "header.ftlh">

        <div class="transfer-phone-container">
             <div class="transfer-phone-title">
                 <a href="/transfers/${currentAccount.id}" class="transfer-phone-title-back">
                     <svg viewBox="0 0 24 24" xmlns="http://www.w3.org/2000/svg" focusable="false"><path fill-rule="evenodd" clip-rule="evenodd" d="M6.867 10.975H20v.528a1.5 1.5 0 0 1-1.497 1.5l-11.636.022 3.422 3.472a1.5 1.5 0 0 1 0 2.106L9.898 19 3 12l6.898-7 .391.397a1.5 1.5 0 0 1 0 2.106l-3.422 3.472Z" fill="currentColor"></path></svg>
                 </a>
                 <h2 class="transfer-phone-title-content">
                     Transfer by phone number
                 </h2>
             </div>
            <div class="transfer-phone-main">
                <div class="transfer-phone-main-content">
                    <div class="transfer-phone-main-content-title">
                        <h4>Transfer from</h4>
                    </div>
                    <div class="transfer-phone-main-content-body">
                        <div class="transfer-phone-main-content-body-selector">
                            <div class="transfer-phone-main-content-body-selector-icon"></div>
                            <div class="transfer-phone-main-content-body-selector-body">
                                <div class="transfer-phone-main-content-body-selector-body-account">
                                    <span>${currentAccount.name}</span>
                                </div>
                                <div class="transfer-phone-main-content-body-selector-body-deposit">
                                    <span>${currentAccount.deposit} ₽</span>
                                </div>
                                <div class="transfer-phone-main-content-body-selector-body-expand">
                                    <span iconpath="<svg viewBox=&quot;0 0 16 16&quot; xmlns=&quot;http://www.w3.org/2000/svg&quot; focusable=&quot;false&quot;><path fill-rule=&quot;evenodd&quot; clip-rule=&quot;evenodd&quot; d=&quot;M8 12.184 2 6.342 3.423 5 8 9.5 12.577 5 14 6.342l-6 5.842Z&quot; fill=&quot;currentColor&quot;/></svg>" data-qa-type="uikit/icon" class="abCIV0JCv" style="width: 24px; height: 24px;"><span class="bbCIV0JCv"></span><span data-qa-type="uikit/icon.content" class="cbCIV0JCv" role="presentation" style="width: 16px; height: 16px;"><svg viewBox="0 0 16 16" xmlns="http://www.w3.org/2000/svg" focusable="false"><path fill-rule="evenodd" clip-rule="evenodd" d="M8 12.184 2 6.342 3.423 5 8 9.5 12.577 5 14 6.342l-6 5.842Z" fill="currentColor"></path></svg></span></span>
                                </div>
                            </div>
                        </div>
                    </div>
                </div>
                <div class="transfer-phone-main-content">
                    <div class="transfer-phone-main-content-title">
                        <h4>Transfer to</h4>
                    </div>
                    <div class="transfer-phone-main-content-body" style="cursor: text">
                        <div class="transfer-phone-main-content-body-selector">
                            <div class="transfer-phone-main-content-body-selector-icon"></div>
                            <div class="transfer-phone-main-content-body-selector-body">
                                <input id="phone-input" placeholder="88005553535" class="transfer-phone-main-content-body-selector-body-input" style="padding-top: 10px">
                            </div>
                        </div>
                    </div>
                </div>
                <div class="transfer-phone-main-content">
                    <div class="transfer-phone-main-content-title">
                        <h4>Transfer amount</h4>
                    </div>
                    <div class="transfer-phone-main-content-body-input" style="margin-bottom: 16px;">
                        <div class="transfer-phone-main-content-body-input-2">
                            <input id="amount-input" placeholder="Amount, ₽" class="transfer-phone-main-content-body-selector-body-input">
                        </div>
                    </div>
                    <div class="transfer-phone-main-content-body-input">
                        <div class="transfer-phone-main-content-body-input-2">
                            <input id="message-input" placeholder="Message to the recipient" class="transfer-phone-main-content-body-selector-body-input">
                        </div>
                    </div>
                </div>
                <button id="transfer-button" class="transfer-button">
                    <span class="transfer-button-text">Transfer</span>
                </button>
            </div>
        </div>

        <#include "footer.ftlh">
    </div>
</#macro>

<#macro scripts>
    <script src="http://code.jquery.com/jquery-latest.min.js"></script>

    <script>
        let transferButton = $("#transfer-button");

        transferButton.on("mousedown", function () {
            $(this).css('background-color', '#fab619');
        });

        transferButton.on("mouseup", function () {
            $(this).css('background-color', '#ffdd2d');
        });

        transferButton.hover(function() {
            $(this).css("background-color", "#ffcd33");
        }, function() {
            $(this).css("background-color", "#ffdd2d");
        });

        transferButton.on("click", function () {
            let csrfHeader = $("meta[name='_csrf_header']").attr("content");
            let csrfToken = $("meta[name='_csrf']").attr("content");
            let phoneTransfer = {};
            phoneTransfer["phone"] = $("#phone-input").val();
            phoneTransfer["amount"] = $("#amount-input").val();
            phoneTransfer["message"] = $("#message-input").val();

            $.ajax({
                type: "POST",
                contentType: "application/json",
                url: "/transfers/phone?accountId=${currentAccount.id}",
                beforeSend: function(xhr) {
                    xhr.setRequestHeader(csrfHeader, csrfToken);
                },
                data: JSON.stringify(phoneTransfer),
                dataType: 'json',
                statusCode: {
                    200: function () {
                        window.location.replace("/account/${currentAccount.id}")
                    }
                }
            });
        });
    </script>
</#macro>
