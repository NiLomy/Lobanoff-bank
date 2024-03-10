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
                <a href="/replenishes/${currentAccount.id}" class="transfer-phone-title-back">
                    <svg viewBox="0 0 24 24" xmlns="http://www.w3.org/2000/svg" focusable="false"><path fill-rule="evenodd" clip-rule="evenodd" d="M6.867 10.975H20v.528a1.5 1.5 0 0 1-1.497 1.5l-11.636.022 3.422 3.472a1.5 1.5 0 0 1 0 2.106L9.898 19 3 12l6.898-7 .391.397a1.5 1.5 0 0 1 0 2.106l-3.422 3.472Z" fill="currentColor"></path></svg>
                </a>
                <h2 class="transfer-phone-title-content">
                    Replenish by card
                </h2>
            </div>
            <div class="transfer-phone-main">
                <div class="transfer-phone-main-content">
                    <div class="transfer-phone-main-content-title">
                        <h4>Replenish from</h4>
                    </div>
                    <div class="transfer-phone-main-content-body-input" style="margin-bottom: 16px;">
                        <div class="transfer-phone-main-content-body-input-2">
                            <input id="card-input" type="tel" inputmode="numeric" pattern="[0-9\s]{13,19}" autocomplete="cc-number" maxlength="19" required placeholder="Card number" class="transfer-phone-main-content-body-selector-body-input">
                        </div>
                    </div>
                </div>

                <div class="transfer-phone-main-content">
                    <div class="transfer-phone-main-content-title">
                        <h4>Replenish to</h4>
                    </div>
                    <div id="transfer-to" data-transfer-to-account-id="${currentAccount.id}" class="transfer-phone-main-content-body">
                        <div class="transfer-phone-main-content-body-selector">
                            <div class="transfer-phone-main-content-body-selector-icon"></div>
                            <div class="transfer-phone-main-content-body-selector-body">
                                <div class="transfer-phone-main-content-body-selector-body-account">
                                    <span id="transfer-to-name">${currentAccount.name}</span>
                                </div>
                                <div class="transfer-phone-main-content-body-selector-body-deposit">
                                    <span id="transfer-to-deposit">${currentAccount.deposit} ₽</span>
                                </div>
                                <div class="transfer-phone-main-content-body-selector-body-expand">
                                    <span iconpath="<svg viewBox=&quot;0 0 16 16&quot; xmlns=&quot;http://www.w3.org/2000/svg&quot; focusable=&quot;false&quot;><path fill-rule=&quot;evenodd&quot; clip-rule=&quot;evenodd&quot; d=&quot;M8 12.184 2 6.342 3.423 5 8 9.5 12.577 5 14 6.342l-6 5.842Z&quot; fill=&quot;currentColor&quot;/></svg>" data-qa-type="uikit/icon" class="abCIV0JCv" style="width: 24px; height: 24px;"><span class="bbCIV0JCv"></span><span data-qa-type="uikit/icon.content" class="cbCIV0JCv" role="presentation" style="width: 16px; height: 16px;"><svg viewBox="0 0 16 16" xmlns="http://www.w3.org/2000/svg" focusable="false"><path fill-rule="evenodd" clip-rule="evenodd" d="M8 12.184 2 6.342 3.423 5 8 9.5 12.577 5 14 6.342l-6 5.842Z" fill="currentColor"></path></svg></span></span>
                                </div>
                            </div>
                        </div>
                    </div>
                </div>
                <div id="dropdown-to" class="dropdown-content">
                    <#if accountsList? has_content>
                        <#list accountsList as account>
                            <div data-account-id="${account.id}" class="dropdown-item-to">
                                <div class="transfer-phone-main-content-body-2">
                                    <div class="transfer-phone-main-content-body-selector-2">
                                        <div class="transfer-phone-main-content-body-selector-icon"></div>
                                        <div class="transfer-phone-main-content-body-selector-body">
                                            <div class="transfer-phone-main-content-body-selector-body-account">
                                                <span>${account.name}</span>
                                            </div>
                                            <div class="transfer-phone-main-content-body-selector-body-deposit">
                                                <span>${account.deposit} ₽</span>
                                            </div>
                                        </div>
                                    </div>
                                </div>
                            </div>
                        </#list>
                    </#if>
                </div>

                <div class="transfer-phone-main-content">
                    <div class="transfer-phone-main-content-title">
                        <h4>Replenish amount</h4>
                    </div>
                    <div class="transfer-phone-main-content-body-input" style="margin-bottom: 16px;">
                        <div class="transfer-phone-main-content-body-input-2">
                            <input id="amount-input" placeholder="Amount, ₽" class="transfer-phone-main-content-body-selector-body-input">
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

        let transferForm = $("#transfer-from");
        dropdownInit(transferForm, "dropdown-to", ".dropdown-item-to", "transfer-to", "transfer-to-name", "transfer-to-deposit", "transferToAccountId");

        function dropdownInit(transfer, dropdownId, dropdownItemClass, transferId, transferName, transferDeposit, transferAccountId) {
            transfer.on("click", function () {
                document.getElementById(dropdownId).style.display = "block";
                let height = 56 * Number("${accountsList?size}") + 10;
                document.getElementById(dropdownId).style.height = height + "px";
            });

            document.addEventListener('mouseup', function(e) {
                let container = document.getElementById(dropdownId);
                if (!container.contains(e.target)) {
                    container.style.display = 'none';
                }
            });

            $(dropdownItemClass).click(function () {
                const accountId = $(this).data("account-id");
                let accounts = [];
                [<#list accountsList as account>]
                var accountObject = {};
                accountObject.id = '${account.id}';
                accountObject.name = '${account.name}';
                accountObject.deposit = '${account.deposit}';
                accounts.push(accountObject);
                [</#list>]
                accounts.forEach(account => {
                    if (account.id.toString() === accountId.toString()) {
                        document.getElementById(transferId).dataset[transferAccountId] = accountId;
                        document.getElementById(transferName).textContent = account.name;
                        document.getElementById(transferDeposit).textContent = account.deposit;

                        let container = document.getElementById(dropdownId);
                        container.style.display = 'none';
                    }
                });
            });
        }

        transferButton.on("click", function () {
            let csrfHeader = $("meta[name='_csrf_header']").attr("content");
            let csrfToken = $("meta[name='_csrf']").attr("content");

            let betweenTransfer = {};
            betweenTransfer["card"] = $("#card-input").val();
            betweenTransfer["to"] = document.getElementById("transfer-to").dataset["transferFromAccountId"].toString();
            betweenTransfer["amount"] = $("#amount-input").val();

            $.ajax({
                type: "POST",
                contentType: "application/json",
                url: "/transfers/card",
                beforeSend: function(xhr) {
                    xhr.setRequestHeader(csrfHeader, csrfToken);
                },
                data: JSON.stringify(betweenTransfer),
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
