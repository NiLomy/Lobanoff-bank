<#include "../base/base.ftlh">

<#macro styles>
    <link rel="stylesheet" href="/css/profile.css">
    <link rel="stylesheet" href="/css/header.css">
</#macro>

<#macro title>Lobanoff bank</#macro>

<#macro content>
    <div class="profile-page">
        <#include "../base/header.ftlh">

        <div class="container">
            <div class="title">
                <div class="title-container">
                    <div class="accounts-title-text">
                        <h2>Accounts</h2>
                    </div>
                </div>
            </div>
            <div class="main-content">
                <#if accounts ?has_content>
                    <div class="accounts-info">
                        <div class="accounts-container">
                            <div>
                                <ul class="accounts">
                                    <#list accounts as account>
                                        <li data-account-id="${account.id}" class="account">
<#--                                            <a href="/account/${account.id}" class="account-content">-->
                                                <div class="account-info">
                                                    <div class="account-icon">
                                                        <b>Account #${account_index + 1}</b>
                                                    </div>
                                                    <div class="account-info-extend">
                                                        <div class="account-info-deposit">
                                                            <div class="account-info-deposit-link">
                                                                <span class="account-info-deposit-text">${account.deposit}</span>
                                                            </div>
                                                        </div>
                                                        <div class="account-info-name">
                                                            <span class="account-info-name-text">${account.name}</span>
                                                        </div>
                                                    </div>
                                                </div>
<#--                                            </a>-->
                                        </li>
                                    </#list>
                                </ul>
                            </div>
                        </div>
                    </div>
                </#if>
                <div class="main-content-container">
                    <#if transactions?has_content>
                        <div class="line-chart">
                            <canvas id="myChart"></canvas>
                        </div>
                    </#if>
                </div>
            </div>
        </div>
    </div>
<#--    <h1>Profile</h1>-->
<#--    <#if currentUser ?has_content>-->
<#--        <p>Email: ${currentUser.email}</p>-->
<#--        <p>Password: ${currentUser.password}</p>-->
<#--    </#if>-->
<#--    <h2>Accounts</h2>-->
<#--    <#if accounts ?has_content>-->
<#--        <#list accounts as account>-->
<#--            <b>Account #${account_index + 1}</b>-->
<#--            <p>Name: ${account.name}</p>-->
<#--            <p>Deposit: ${account.deposit}</p>-->
<#--            <#if account.card ?has_content>-->
<#--                <p>Card bind: ${account.card.number}</p>-->
<#--            </#if>-->
<#--            <#if account_has_next>-->
<#--                <br>-->
<#--            </#if>-->
<#--        </#list>-->
<#--    </#if>-->
    <#include "../base/footer.ftlh">
</#macro>

<#macro scripts>
    <script src="http://code.jquery.com/jquery-latest.min.js"></script>

    <script src="js/profile.js"></script>

    <script src="https://cdn.jsdelivr.net/npm/chart.js@4.4.1/dist/chart.umd.min.js"></script>

    <script>
        const ctx = document.getElementById('myChart');

        let labels =[<#if transactions?has_content><#list transactions as transaction>"${transaction.date.toString()?substring(5)}",</#list></#if>].reverse()

        const data = {
            labels: labels,
            datasets: [{
                label: 'Money in the account',
                data: [<#if transactions?has_content><#list transactions as transaction>${transaction.amount?c},</#list></#if>].reverse(),
                fill: false,
                borderColor: 'rgb(75, 192, 192)',
                tension: 0.1
            }]
        };

        const config = {
            type: 'line',
            data: data,
            options: {
                plugins: {
                    legend: {
                        display: false,
                    },
                },
            },
        };
        new Chart(ctx, config);
    </script>
</#macro>
