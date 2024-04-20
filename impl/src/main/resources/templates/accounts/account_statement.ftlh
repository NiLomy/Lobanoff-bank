<#include "../base/base.ftlh">

<#macro styles>
    <link rel="stylesheet" href="/css/account.css">
    <link rel="stylesheet" href="/css/header.css">
    <link rel="stylesheet" type="text/css" media="screen" href="https://ajax.googleapis.com/ajax/libs/jqueryui/1.7.2/themes/base/jquery-ui.css">

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
                    Statement for February
                </h2>
                <input name="startDate" id="startDate" class="date-picker" />
            </div>

            <div class="account-requisites-content">
                <ul class="account-requisites-table">
                    <li class="account-requisites-table-item">
                        <div class="account-requisites-table-item-content">
                            <p class="account-requisites-table-item-content-title">Balance at the beginning of the period (${dateFrom}):</p>
                            <p class="account-requisites-table-item-content-text">${statement.startBalance}</p>
                        </div>
                    </li>
                    <li class="account-requisites-table-item">
                        <div class="account-requisites-table-item-content">
                            <p class="account-requisites-table-item-content-title">The amount of receipts:</p>
                            <p class="account-requisites-table-item-content-text">${statement.receipts}</p>
                        </div>
                    </li>
                    <li class="account-requisites-table-item">
                        <div class="account-requisites-table-item-content">
                            <p class="account-requisites-table-item-content-title">The amount of accrued interest:</p>
                            <p class="account-requisites-table-item-content-text">${statement.interest}</p>
                        </div>
                    </li>
                    <li class="account-requisites-table-item">
                        <div class="account-requisites-table-item-content">
                            <p class="account-requisites-table-item-content-title">The amount of expenses:</p>
                            <p class="account-requisites-table-item-content-text">${statement.expenses}</p>
                        </div>
                    </li>
                    <li class="account-requisites-table-item">
                        <div class="account-requisites-table-item-content">
                            <p class="account-requisites-table-item-content-title">Balance at the end of the period (${dateTo}):</p>
                            <p class="account-requisites-table-item-content-text">${statement.endBalance}</p>
                        </div>
                    </li>
                </ul>
            </div>
        </div>

        <#include "../base/footer.ftlh">
    </div>
</#macro>

<#macro scripts>
    <script src="https://ajax.googleapis.com/ajax/libs/jquery/1.4.1/jquery.js"></script>

    <script src="https://ajax.googleapis.com/ajax/libs/jqueryui/1.7.2/jquery-ui.min.js"></script>

    <script>
        $('.date-picker').datepicker({
            changeMonth: true,
            changeYear: true,
            showButtonPanel: true,
            dateFormat: 'MM yy',
            onClose(dateText, inst) {
                const month = $("#ui-datepicker-div .ui-datepicker-month :selected").val();
                const year = $("#ui-datepicker-div .ui-datepicker-year :selected").val();
                $(this).datepicker('setDate', new Date(year, month, 1));
                alert($('.date-picker'));
            }
        });
    </script>
</#macro>
