<#include "../base/base.ftlh">

<#macro styles>
    <link rel="stylesheet" href="/css/account.css">
    <link rel="stylesheet" href="/css/header.css">
</#macro>

<#macro title>Lobanoff bank</#macro>

<#macro content>
    <div class="account-page">
        <#include "../base/header.ftlh">

        <div class="account-requisites-container">
            <div class="account-requisites-title">
                <a href="/account/${currentAccount.id}" class="account-requisites-title-back">
                    <svg viewBox="0 0 24 24" xmlns="http://www.w3.org/2000/svg" focusable="false"><path fill-rule="evenodd" clip-rule="evenodd" d="M6.867 10.975H20v.528a1.5 1.5 0 0 1-1.497 1.5l-11.636.022 3.422 3.472a1.5 1.5 0 0 1 0 2.106L9.898 19 3 12l6.898-7 .391.397a1.5 1.5 0 0 1 0 2.106l-3.422 3.472Z" fill="currentColor"></path></svg>
                </a>
                <h2 class="account-requisites-title-content">
                    Requisites
                </h2>
            </div>

            <div class="account-requisites-content">
                <ul class="account-requisites-table">
                    <li class="account-requisites-table-item">
                        <div class="account-requisites-table-item-content">
                            <p class="account-requisites-table-item-content-title">Payee</p>
                            <p class="account-requisites-table-item-content-text">${requisites.payee.lastname} ${requisites.payee.name} <#if requisites.payee.patronymic? has_content>${requisites.payee.patronymic}</#if></p>
                        </div>
                    </li>
                    <li class="account-requisites-table-item">
                        <div class="account-requisites-table-item-content">
                            <p class="account-requisites-table-item-content-title">The account of the payee</p>
                            <p class="account-requisites-table-item-content-text">${requisites.accountNumber}</p>
                        </div>
                    </li>
                    <li class="account-requisites-table-item">
                        <div class="account-requisites-table-item-content">
                            <p class="account-requisites-table-item-content-title">Purpose of payment</p>
                            <p class="account-requisites-table-item-content-text">Transfer of funds. VAT is not subject to</p>
                        </div>
                    </li>
                    <li class="account-requisites-table-item">
                        <div class="account-requisites-table-item-content">
                            <p class="account-requisites-table-item-content-title">Bank identification code</p>
                            <p class="account-requisites-table-item-content-text">${requisites.code}</p>
                        </div>
                    </li>
                    <li class="account-requisites-table-item">
                        <div class="account-requisites-table-item-content">
                            <p class="account-requisites-table-item-content-title">The recipient bank</p>
                            <p class="account-requisites-table-item-content-text">${requisites.bankName}</p>
                        </div>
                    </li>
                    <li class="account-requisites-table-item">
                        <div class="account-requisites-table-item-content">
                            <p class="account-requisites-table-item-content-title">Correspondent account</p>
                            <p class="account-requisites-table-item-content-text">${requisites.corrAccount}</p>
                        </div>
                    </li>
                </ul>
            </div>
        </div>

        <#include "../base/footer.ftlh">
    </div>
</#macro>

<#macro scripts>
</#macro>
