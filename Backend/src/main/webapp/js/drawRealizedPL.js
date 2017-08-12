function drawRealizedPL(url, instrument, counterparty, divname) {
    var svg = dimple.newSvg(divname, "100%", "600");

    d3.json(url, function (data) {

        if (instrument !== "All") {
            data = dimple.filterData(data, "instrument_name", instrument)
        }

        if (counterparty !== "All") {
            data = dimple.filterData(data, "counterparty_name", counterparty)
        }


        let myChart = new dimple.chart(svg, data);
        myChart.setBounds("10%", "10%", "80%", "70%");
        var x = myChart.addCategoryAxis("x", "counterparty_name");
        var y = myChart.addMeasureAxis("y", "difference");
        //x.addOrderRule("instrument_name");
        x.addOrderRule("counterparty_name");
        y.tickFormat = ',.3f';
        y.title = "Ending Position";
        x.title = "Instrument Name";
        myChart.addSeries("instrument_name", dimple.plot.bar);
        myChart.lineMarkers = true;
        var myLegend = myChart.addLegend("95%", "70%", 60, 200, "Right");
        myChart.draw();

        svg.append("text")
            .attr("x", myChart._xPixels() + myChart._widthPixels() / 2)
            .attr("y", myChart._yPixels() - 30)
            .style("text-anchor", "middle")
            .style("font-family", "sans-serif")
            .style("font-weight", "bold")
            .style("font-size", "20")
            .text("Realized Profit/Loss for each Counterparty" );
        svg.selectAll(".dimple-axis")
            .style("font-family", "sans-serif")
            .style("font-size", "10");

        function change() {


            console.log(x);
            myChart.draw(1000);
        }

    });
}