function drawTrends(url, instrument, counterparty, divname) {
    var svg = dimple.newSvg(divname, "100%", "600");

    d3.json(url, function (data) {

        var inFormat = d3.timeParse("%Y-%m-%dT%H:%M:%S.%L");
        var altinFormat = d3.timeParse("%Y-%m-%dT%H:%M:%S");
        var timeFormat = d3.timeFormat("%S");
        var realFormat = d3.timeFormat("%S.%L");

        if (instrument !== "All") {
            data = dimple.filterData(data, "instrument_name", instrument)
        }

        if (counterparty !== "All") {
            data = dimple.filterData(data, "counterparty_name", counterparty)
        }

        data.forEach(function (d) {
            if (d["deal_type"] === "S"){
                d["deal_amount"] = -d["deal_amount"]
            }
            d["total_amount"] =
                d["deal_amount"] * d["deal_quantity"];

            var inDate = inFormat(d["deal_time"]);

            if (inDate === null){
                inDate = altinFormat(d["deal_time"]);
            }

            d["Time"] = timeFormat(inDate);
            d["Milli"] = realFormat(inDate);

        }, this);

        // Create the chart
        var myChart = new dimple.chart(svg, data);
        myChart.setBounds("10%", "10%", "80%", "70%");

        // Add an x and 2 y-axes.  When using multiple axes it's
        // important to assign them to variables to pass to the series
        var x = myChart.addCategoryAxis("x", ["Milli", "deal_time"]);
        x.addGroupOrderRule("deal_time");
        x.addOrderRule("deal_time");
        x.hidden=true;

//                var x = myChart.addAxis("x", null, null, "deal_time");
//                x.addOrderRule("deal_time", false);
//                x.tickFormat = d3.timeFormat("%S");


        var y1 = myChart.addMeasureAxis("y", "total_amount");
        var y2 = myChart.addMeasureAxis("y", "deal_quantity");
        y1.tickFormat = ',.3f';
        y2.tickFormat = ',.3f';
        y1.title = "Total Amount";
        y2.title = "Total Quantity";


        // Color the sales bars to be highly transparent
        myChart.assignColor("deal_amount", "#222222", "#000000", 0.1);

        // Add the bars mapped to the second y axis
        var deal_amount = myChart.addSeries("Total Quantity", dimple.plot.line, [x, y2]);
        deal_amount.aggregate = dimple.aggregateMethod.sum;

        var total_amount = myChart.addSeries("Total Amount", dimple.plot.circle, [x, y1]);
        total_amount.aggregate = dimple.aggregateMethod.sum;

        myChart.lineMarkers = true;
        var myLegend = myChart.addLegend("95%", "90%", 60, 300, "Right");

        myChart.setStoryboard("counterparty_name");

        myChart.draw();

        svg.append("text")
            .attr("x", myChart._xPixels() + myChart._widthPixels() / 2)
            .attr("y", myChart._yPixels() - 30)
            .style("text-anchor", "middle")
            .style("font-family", "sans-serif")
            .style("font-weight", "bold")
            .style("font-size", "20")
            .text("The data is for instrument " + instrument + " and counterparty " + counterparty )
        svg.selectAll(".dimple-axis")
            .style("font-family", "sans-serif")
            .style("font-size", "15")

    });
}
