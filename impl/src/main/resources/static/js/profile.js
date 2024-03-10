$(".account").click(function () {
    const accountId = $(this).data("account-id");
    const csrfHeader = $("meta[name='_csrf_header']").attr("content");
    const csrfToken = $("meta[name='_csrf']").attr("content");

    $.ajax({
        type: "GET",
        url: "/account/" + accountId,
        beforeSend: function(xhr) {
            xhr.setRequestHeader(csrfHeader, csrfToken);
        },
        statusCode: {
            200: function () {
                window.location.replace("/account/" + accountId)
            }
        }
    });
});