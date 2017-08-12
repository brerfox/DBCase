
$(document).ready( function() {

    $(document).on('click', '#logout', function() {
        console.log(window.location.host + '/api/logout');
        $.get("http://" + window.location.host + '/api/logout', {}, function(data){
            console.log(window.location.host + '/api/logout');
            window.location.replace("/");
        });
    });
    $(document).on('click', '#login-form-button', function() {

        var login = $('#login-form-login').val();
        var pass = $('#login-form-password').val();

        console.log(login, pass);
        $.post(window.location.pathname + 'api/auth', {login: login, password: pass}).done(function(data){
            $('#login-form').html('<strong>You are authenticated!</strong>');
            afterAuth();
        }).fail(function(xhr, status, error) {
            alert("Incorrect login and password!");
        });
    });

    function afterAuth() {
        // $.get(window.location.pathname + 'api/get/deal', {page_id: 1, page_size: 100}, function(data){
        //     // console.log(data);
        //     // data.forEach(function(element) {
        //     //     $('#data_box').append('<li class="list-group-item">Deal ID: ' + element.deal_id + ', Deal amount: ' + element.deal_amount + ', </li>')
        //     // })
        //
        // });
        window.location.replace("/overview.html");
    }

    function dbCheck () {
        $.get('http://' + window.location.host + '/api/db_is_alive', {}, function(data){
            console.log(data);
            if(data == true) {
                $('#db_info').removeClass("alert-warning");
                $('#db_info').addClass("alert-success");
                $('#db_info').html('<strong id="db_info_text">DATA BASE is available!!!</strong>');
            }
        });
    }

    dbCheck();

});