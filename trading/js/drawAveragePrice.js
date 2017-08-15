function preProcess(data){
    let result = {
        bulks: []
    };
    data.forEach(function(item) {
        let temp;
        temp = {};
        temp["instrument_name"] = item["instrument_name"];
        temp["deal_instrument_id"] = item["deal_instrument_id"];
        temp["deal_type"] = "buy";
        temp["price"] = item["average_buy"];
        result.bulks.push(temp);
        temp = {};
        temp["instrument_name"] = item["instrument_name"];
        temp["deal_instrument_id"] = item["deal_instrument_id"];
        temp["deal_type"] = "sell";
        temp["price"] = item["average_sell"];
        result.bulks.push(temp);
    });
    return result.bulks;
}


function drawAveragePrice(url, instrument, counterparty, divname) {
    var svg = dimple.newSvg(divname, "100%", "600");

    d3.json(url, function (predata) {

        if (instrument !== "All") {
            predata = dimple.filterData(predata, "instrument_name", instrument)
        }

        if (counterparty !== "All") {
            predata = dimple.filterData(predata, "counterparty_name", counterparty)
        }

        // d3.select("#sort").on("change", change);

        let data = preProcess(predata);
        let myChart = new dimple.chart(svg, data);
        myChart.defaultColors = [
            new dimple.color("#3498db", "#2980b9", 1), // blue
            new dimple.color("#e74c3c", "#c0392b", 1) // red
        ];
        myChart.setBounds("10%", "10%", "80%", "70%");
        var x = myChart.addCategoryAxis("x", ["instrument_name", "deal_type"]);
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