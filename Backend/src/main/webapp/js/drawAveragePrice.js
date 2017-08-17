function preProcess(data){
    let result = {
        bulks: []
    };
    data.forEach(function(item) {
        let temp;
        temp = {};
        temp["instrumentName"] = item["instrumentName"];
        temp["deal_instrument_id"] = item["deal_instrument_id"];
        temp["deal_type"] = "buy";
        temp["price"] = item["averageBuy"];
        result.bulks.push(temp);
        temp = {};
        temp["instrumentName"] = item["instrumentName"];
        temp["deal_instrument_id"] = item["deal_instrument_id"];
        temp["deal_type"] = "sell";
        temp["price"] = item["averageSell"];
        result.bulks.push(temp);
    });
    return result.bulks;
}


function drawAveragePrice(url, instrument, counterparty, divname) {
    //new line
    d3.select("svg").remove();

    var svg = dimple.newSvg(divname, "100%", "600");

    d3.json(url, function (predata) {

        if (instrument !== "All") {
            predata = dimple.filterData(predata, "instrumentName", instrument)
        }

        if (counterparty !== "All") {
            predata = dimple.filterData(predata, "counterpartyName", counterparty)
        }

        // d3.select("#sort").on("change", change);

        let data = preProcess(predata);
        let myChart = new dimple.chart(svg, data);
        myChart.defaultColors = [
            new dimple.color("#003399", "#0068ae", 1),
            new dimple.color("#009ee0", "#6d9fbd", 1),
            new dimple.color("#667292", "#8d9db6", 1),
            new dimple.color("#fdab18", "#fdd686", 1),
            new dimple.color("#cb501b", "#e5a7bd", 1),
            new dimple.color("#d7cb89", "#ebe6ca", 1),
            new dimple.color("#52397a", "#ab9cbc", 1),
            new dimple.color("#772379", "#bb91bc", 1),
            new dimple.color("#b1be2d", "#d8de96", 1),
            new dimple.color("#007a9e", "#7fbcce", 1),
            new dimple.color("#b9936c", "#dac292", 1),
            new dimple.color("#f7786b", "#f7cac9", 1)
        ];
        myChart.setBounds("10%", "10%", "80%", "70%");
        var x = myChart.addCategoryAxis("x", ["instrumentName", "deal_type"]);
        var y = myChart.addMeasureAxis("y", "price");
        x.addOrderRule("price");
        y.tickFormat = ',.3f';
        y.title = "Average Price";
        x.title = "Instrument Name / Deal Type";
        myChart.addSeries("deal_type", dimple.plot.bar);
        myChart.lineMarkers = true;
        var myLegend = myChart.addLegend("90%", "0%", 40, 400, "Right");
        myLegend.fontSize = "13px";
        myChart.draw();

        svg.append("text")
            .attr("x", myChart._xPixels() + myChart._widthPixels() / 2)
            .attr("y", myChart._yPixels() - 30)
            .style("text-anchor", "middle")
            .style("font-family", "sans-serif")
            .style("font-weight", "bold")
            .style("font-size", "20")
            .text("Average Buy and Sell Price for each Instrument" );
        svg.selectAll(".dimple-axis")
            .style("font-family", "sans-serif")
            .style("font-size", "15");

        function change() {
            x._orderRules.push({"price": false});
            myChart.draw(1500);
        }

    });
}