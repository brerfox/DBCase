
$(document).ready( function() {

    const API_URL = "http://" + window.location.host +
        window.location.pathname;

    $(document).on('click', '#menu-overview', function() {
        $('#overview-filters').show();
        let url = API_URL + "api/get/overview";
        // let url = "data/overview.json";
        drawTrends(url, "All", "All", "#chartContainer");
        $(".active").removeClass("active");
        $(this).addClass("active");
    });

    $(document).on('click', '#menu-average-price', function() {
        $('#overview-filters').hide();

        // let url = "data/average.json";
        let url = API_URL + "api/get/averagebuysell";
        drawAveragePrice(url, "All", "All", "#chartContainer");
        $(".active").removeClass("active");
        $(this).addClass("active");
    });

    $(document).on('click', '#menu-ending-position', function() {
        $('#overview-filters').hide();
        // let url = "data/net_trade.json";
        let url = API_URL + "api/get/endingposition";
        console.log(url);
        drawEndingPosition(url, "All", "All", "#chartContainer");
        $(".active").removeClass("active");
        $(this).addClass("active");
    });

    $(document).on('click', '#menu-realized-pl', function() {
        $('#overview-filters').hide();
        //http://localhost:8080/api/get/realizedprofitloss
        // let url = "data/realized_pl.json";
        let url = API_URL + "api/get/realizedprofitloss";
        drawPL(url, "All", "All", "#chartContainer", "Realized", "realized_profit", "instrumentName","counterpartyName");
        $(".active").removeClass("active");
        $(this).addClass("active");
    });

    $(document).on('click', '#menu-effective-pl', function() {
        $('#overview-filters').hide();
        // let url = "data/realized_pl.json";
        let url = API_URL + "api/get/effectiveprofitloss";
        drawPL(url, "All", "All", "#chartContainer", "Effective", "effective_profit", "instrumentName", "counterpartyName");
        $(".active").removeClass("active");
        $(this).addClass("active");
    });

});