function drawEndingPosition(url, instrument, counterparty, divname) {
    // new line
    d3.select("svg").remove();
    var svg = dimple.newSvg(divname, "100%", "600");

    d3.json(url, function (data) {

        if (instrument !== "All") {
            data = dimple.filterData(data, "instrumentName", instrument)
        }

        if (counterparty !== "All") {
            data = dimple.filterData(data, "counterpartyName", counterparty)
        }


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
        var x = myChart.addCategoryAxis("x", "instrumentName");
        var y = myChart.addMeasureAxis("y", "endingPosition");
        //x.addOrderRule("instrument_name");
        x.addOrderRule("endingPosition");
        y.tickFormat = ',.3f';
        y.title = "Ending Position";
        x.title = "Instrument Name";
        myChart.addSeries("counterpartyName", dimple.plot.bar);
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

        var counterparyValues = dimple.getUniqueValues(data, "counterpartyName");

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
                    myChart.data = dimple.filterData(data, "counterpartyName", counterparyValues);
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