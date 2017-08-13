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
        myChart.setBounds("12%", "10%", "80%", "70%");
        var x = myChart.addCategoryAxis("x", "counterparty_name");
        var y = myChart.addMeasureAxis("y", "difference");
        //x.addOrderRule("instrument_name");
        x.addOrderRule("counterparty_name");
        y.tickFormat = ',.3f';
        y.title = "Realized P/L";
        x.title = "Instrument Name";
        myChart.addSeries("instrument_name", dimple.plot.bar);
        myChart.lineMarkers = true;
        var myLegend = myChart.addLegend("90%", "5%", 130, 400, "Right");
        myLegend.fontSize = "13px";
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
            .style("font-size", "15");


        myChart.legends = [];

        svg.selectAll("instrumentclicklabel")
            .data(["*Click Instrument color box to show/hide instrument"])
            .enter()
            .append("text")
            .attr("x","80%")
            .attr("y", "2%")
            .style("font-size", "10px")
            .style("font-style", "italic")
            .text(function (d) { return d; });

        var instrumentValues = dimple.getUniqueValues(data, "instrument_name");

        myLegend.shapes.selectAll("rect")
        //Add a click event to each rectangle
            .on("click", function (e)  {
                //Is rectangle already visible?
                var hideRect = false;
                var newFilters = [];
                //if filters contain clicked shape then hide it
                instrumentValues.forEach(function (f) {
                    if (f === e.aggField.slice(-1)[0]) {
                        hideRect = true;
                    } else {
                        newFilters.push(f);
                    }
                });
                // Either hide the shape or show it now
                if (hideRect) {
                    d3.select(this).style("opacity", 0.2);
                } else {
                    newFilters.push(e.aggField.slice(-1)[0]);
                    d3.select(this).style("opacity", 0.8);
                }
                //Update our filters now
                instrumentValues = newFilters;
                if (instrumentValues.length > 0){
                    myChart.data = dimple.filterData(data, "instrument_name", instrumentValues);
                    myChart.draw(1000);
                } else {
                    myChart.data = data;
                    myChart.draw(1000);
                }


            });
        d3.select('#button').on('click', function() {
            myLegend.shapes.selectAll("rect").style("opacity", 0.8);
            myChart.data = data;
            myChart.draw(1000);

        });


    });
}