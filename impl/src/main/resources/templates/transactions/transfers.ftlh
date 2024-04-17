<#include "../base/base.ftlh">

<#macro styles>
    <link rel="stylesheet" href="/css/transfers.css">
    <link rel="stylesheet" href="/css/header.css">
</#macro>

<#macro title>Lobanoff bank</#macro>

<#macro content>
    <div class="transfer-page">
        <#include "../base/header.ftlh">

        <div class="transfer-phone-container">
            <div class="transfer-phone-title">
                <a href="/account/${currentAccount.id}" class="transfer-phone-title-back">
                    <svg viewBox="0 0 24 24" xmlns="http://www.w3.org/2000/svg" focusable="false"><path fill-rule="evenodd" clip-rule="evenodd" d="M6.867 10.975H20v.528a1.5 1.5 0 0 1-1.497 1.5l-11.636.022 3.422 3.472a1.5 1.5 0 0 1 0 2.106L9.898 19 3 12l6.898-7 .391.397a1.5 1.5 0 0 1 0 2.106l-3.422 3.472Z" fill="currentColor"></path></svg>
                </a>
                <h2 class="transfer-phone-title-content">
                    Transfers
                </h2>
            </div>
        </div>
        <div class="transfer-content">
            <div class="transfer-choice">
                <div class="transfer-choice-table">
                    <div class="transfer-choice-table-elem">
                        <div class="transfer-choice-table-elem-icon"></div>
                        <a href="/transfers/phone?accountId=${currentAccount.id}" class="transfer-choice-table-elem-text">
                            <span>By phone number</span>
                        </a>
                    </div>
                    <div class="transfer-choice-table-elem">
                        <div class="transfer-choice-table-elem-icon"></div>
                        <a href="/transfers/card?accountId=${currentAccount.id}" class="transfer-choice-table-elem-text">
                            <span>By card number</span>
                        </a>
                    </div>
                    <div class="transfer-choice-table-elem">
                        <div class="transfer-choice-table-elem-icon"></div>
                        <a href="/transfers/between-accounts?accountId=${currentAccount.id}" class="transfer-choice-table-elem-text">
                            <span>Between accounts</span>
                        </a>
                    </div>
                    <div class="transfer-choice-table-elem">
                        <div class="transfer-choice-table-elem-icon"></div>
                        <a href="/transfers/account-details?accountId=${currentAccount.id}" class="transfer-choice-table-elem-text">
                            <span>By account details</span>
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
    </script>
</#macro>
