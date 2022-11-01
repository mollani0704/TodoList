let listType = 'all';

load();

function load() {
    $.ajax({
        type: 'get',
        url: `/api/v1/todo/list/${listType}`,
        dataType: 'json',
        success: response => {
            console.log(response.data);
        },

        error: errorMessage,
    });
}

function errorMessage(request, error) {
    alert('요청 실패');
    console.log(request.status);
    console.log(request.responseText);
    console.log(error);
}
