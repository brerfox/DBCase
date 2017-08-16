
$(document).ready( function() {

    const API_URL = "http://" + window.location.host +
        window.location.pathname;

    $(document).on('click', '#logout', function() {
        console.log(API_URL + 'api/logout');
        $.get(API_URL + 'api/logout', {}, function(data){
            console.log(API_URL + 'api/logout');
            window.location.replace(API_URL);
        });
    });

    $(document).on('click', '#login-form-button', function() {

        var login = $('#login-form-login').val();
        var pass = $('#login-form-password').val();

        console.log(login, pass);
        $.post(API_URL + 'api/auth', {login: login, password: pass}).done(function(data){
            $('#login-form').html('<strong>You are authenticated!</strong>');
            afterAuth();
        }).fail(function(xhr, status, error) {
            alert("Incorrect login and password!");
        });
    });

    function checkAuth() {
        $.get(API_URL + 'api/auth', {}, function(data){
            console.log("checkAuth() " + data);
            if (data === true) afterAuth();
        });
    }

    function afterAuth() {
        $('#auth-form').hide();
        $('#charts-container').show();
        console.log("I'm really here!")
    let url = API_URL + "api/get/deal?page_id=1&page_size=300";
    drawTrends(url, "All", "All", "#chartContainer");

    }

    function dbCheck () {
        $.get(API_URL + 'api/db_is_alive', {}, function(data){
            console.log(data);
            if(data == true) {
                $('#db_info').removeClass("alert-warning");
                $('#db_info').addClass("alert-success");
                $('#db_info').html('<strong id="db_info_text">DATA BASE is available!!!</strong>');
            }
        });
    }

    checkAuth();
    dbCheck();

});