function drawEndingPosition(url, instrument, counterparty, divname) {
    var svg = dimple.newSvg(divname, "100%", "600");

    d3.json(url, function (data) {

        if (instrument !== "All") {
            data = dimple.filterData(data, "instrument_name", instrument)
        }

        if (counterparty !== "All") {
            data = dimple.filterData(data, "counterparty_name", counterparty)
        }


        let myChart = new dimple.chart(svg, data);
        myChart.defaultColors = [
            new dimple.color("#3498db", "#2980b9", 1), // blue
            new dimple.color("#e74c3c", "#c0392b", 1), // red
            new dimple.color("#2ecc71", "#27ae60", 1), // green
            new dimple.color("#9b59b6", "#8e44ad", 1), // purple
            new dimple.color("#e67e22", "#d35400", 1), // orange
            new dimple.color("#f1c40f", "#f39c12", 1), // yellow
            new dimple.color("#1abc9c", "#16a085", 1), // turquoise
            new dimple.color("#95a5a6", "#7f8c8d", 1),
            new dimple.color("#35a79c", "#009688", 1),
            new dimple.color("#d7c6cf", "#a2798f", 1)
        ];
        myChart.setBounds("10%", "10%", "80%", "70%");
        var x = myChart.addCategoryAxis("x", "instrument_name");
        var y = myChart.addMeasureAxis("y", "ending_position");
        //x.addOrderRule("instrument_name");
        x.addOrderRule("ending_position");
        y.tickFormat = ',.3f';
        y.title = "Ending Position";
        x.title = "Instrument Name";
        myChart.addSeries("counterparty_name", dimple.plot.bar);
        myChart.lineMarkers = true;
        var myLegend = myChart.addLegend("90%", "5%", 120, 400, "Right");
        myLegend.fontSize = "13px";
        myChart.draw();

        svg.append("text")
            .attr("x", myChart._xPixels() + myChart._widthPixels() / 2)
            .attr("y", myChart._yPixels() - 30)
            .style("text-anchor", "middle")
            .style("font-family", "sans-serif")
            .style("font-weight", "bold")
            .style("font-size", "20")
            .text("Ending Position for each Instrument" );
        svg.selectAll(".dimple-axis")
            .style("font-family", "sans-serif")
            .style("font-size", "15");

        svg.selectAll("couterpartyclicklabel")
            .data(["*Click Couterparty color box to show/hide counterparty"])
            .enter()
            .append("text")
            .attr("x","80%")
            .attr("y", "2%")
            .style("font-size", "10px")
            .style("font-style", "italic")
            .text(function (d) { return d; });

        myChart.legends = [];

        var counterparyValues = dimple.getUniqueValues(data, "counterparty_name");

        myLegend.shapes.selectAll("rect")
        //Add a click event to each rectangle
            .on("click", function (e)  {
                //Is rectangle already visible?
                var hideRect = false;
                var newFilters = [];
                //if filters contain clicked shape then hide it
                counterparyValues.forEach(function (f) {
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
                counterparyValues = newFilters;
                if (counterparyValues.length > 0){
                    myChart.data = dimple.filterData(data, "counterparty_name", counterparyValues);
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

        function change() {


            console.log(x);
            myChart.draw(1000);
        }

    });
}