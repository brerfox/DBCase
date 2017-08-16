
$(document).ready( function() {

    const API_URL = "http://" + window.location.host +
        window.location.pathname;

    $(document).on('click', '#menu-overview', function() {
        $('#overview-filters').show();
        let url = API_URL + "api/get/deal?page_id=1&page_size=300";
        drawTrends(url, "All", "All", "#chartContainer");
        $(".active").removeClass("active");
        $(this).addClass("active");
    });

    $(document).on('click', '#menu-average-price', function() {
        $('#overview-filters').hide();
        let url = "data/average.json";
        drawAveragePrice(url, "All", "All", "#chartContainer");
        $(".active").removeClass("active");
        $(this).addClass("active");
    });

    $(document).on('click', '#menu-ending-position', function() {
        $('#overview-filters').hide();
        let url = "data/net_trade.json";
        drawEndingPosition(url, "All", "All", "#chartContainer");
        $(".active").removeClass("active");
        $(this).addClass("active");
    });

    $(document).on('click', '#menu-realized-pl', function() {
        $('#overview-filters').hide();
        let url = "data/realized_pl.json";
        drawPL(url, "All", "All", "#chartContainer", "Realized");
        $(".active").removeClass("active");
        $(this).addClass("active");
    });

    $(document).on('click', '#menu-effective-pl', function() {
        $('#overview-filters').hide();
        let url = "data/realized_pl.json";
        drawPL(url, "All", "All", "#chartContainer", "Effective");
        $(".active").removeClass("active");
        $(this).addClass("active");
    });

});