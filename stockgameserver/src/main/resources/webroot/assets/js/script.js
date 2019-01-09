// var eventbus = new EventBus("/eventbus/");
var eventBus = new EventBus('http://localhost:8081/eventbus');
var stockValue = 0;
var chartCanvas;
var myChart;
var labelValue;
var oldOverview;

var recursiveIndex = 0;
var recursiveStopFlag = false;
var tmpControl;

eventBus.onopen = function () {
    console.log("Opening eventbus");

    eventBus.registerHandler("gameupdates", function (error, message) {
        stockValue = JSON.parse(message.body).stockValue;
        var overview = JSON.parse(message.body).overview;
        overview.sort(function (a,b) {
            return ((b.shares * stockValue)+b.money) -((a.shares * stockValue)+a.money);
        });
        updateCurrentPrice(stockValue);
        updateChart(stockValue);
        updateTiles(overview);

    });
}

function createTile(teamName, shares, money) {
    var hexBackColor = intToRGB(hashCode(teamName));
    console.log(hexBackColor);
    $("#tiles").append($('<div/>', {'id': 'tile' + removeWhiteSpace(teamName), css:{'backgroundColor':'#'+hexBackColor, 'color': invertColor(hexBackColor)} ,'class': 'tile' }).append(
        $('<div/>', {'class': 'container-fluid'}).append(
            $('<div/>', {'class': 'row nameRow'}).append(
                $('<div/>', {'class': 'col-md-12'}).append(
                    $('<span/>', {'text': teamName}),
                    $('<div/>', {'class': 'row'}).append(
                        $('<div/>', {'class': 'col-md-6'}).append(
                            $('<span/>', {'text': '#'+shares})
                        ),
                        $('<div/>', {'class': 'col-md-6'}).append(
                            $('<span/>', {'text': '€' + calculateTotal(shares, money)})
                        )
                    )
                )
            )
        )
        )
    );
}

function onLoad() {
    labelValue = 1;
    chartCanvas = $("#stockChart");
    myChart = new Chart(chartCanvas, {
        type: 'line',
        data: {
            labels: [0],
            datasets: [{
                label: 'Value of stock',
                data: [0],
                backgroundColor: 'rgba(255, 99, 132, 0.2)',
                borderColor: 'rgba(67, 161, 201, 1)',
                borderWidth: 1
            }]
        },
        options: {
            responsive: true,
            scales: {
                xAxes: [{
                    display: false
                }],
                yAxes: [{
                    ticks: {
                        beginAtZero: true,
                        stepSize: 15
                    }
                }]
            }
        }
    });
}

function updateChart(stockValue) {
    var size = myChart.data.datasets[0].data.length;
    myChart.data.datasets[0].data[size] = stockValue;
    myChart.data.labels[size] = labelValue;
    if (size >= 50) {
        myChart.data.datasets[0].data.shift();
        myChart.data.labels.shift();
    }
    labelValue++;
    myChart.update();
}

function updateCurrentPrice(stockValue) {
    $("#currentPrice").text('€' + stockValue);
}

function removeWhiteSpace(teamName) {
    return teamName.replace(/\s+/g, '');
}

function calculateTotal(shares, money) {
    return (shares * stockValue)+money;

}

function hashCode(str) { // java String#hashCode
    var hash = 0;
    for (var i = 0; i < str.length; i++) {
        hash = str.charCodeAt(i) + ((hash << 5) - hash);
    }
    return hash;
}

function intToRGB(i){
    var c = (i & 0x00FFFFFF)
        .toString(16)
        .toUpperCase();

    return "00000".substring(0, 6 - c.length) + c;
}

function invertColor(hexTripletColor) {
    var color = hexTripletColor;
    color = color.substring(1);           // remove #
    color = parseInt(color, 16);          // convert to integer
    color = 0xFFFFFF ^ color;             // invert three bytes
    color = color.toString(16);           // convert to hex
    color = ("000000" + color).slice(-6); // pad with leading zeros
    color = "#" + color;                  // prepend #
    return color;
}

///////////////CREATE NEW TILES/////////////////
function updateTiles(overview) {
    if (overview.length > 0) {
        console.log(overview);
        $("#tiles").html("");
        for (var j = 0; j < overview.length; j++) {
            var teamName = overview[j].username;
            var money = overview[j].money;
            var shares = overview[j].shares;

            createTile(teamName, shares, money);
        }
    }
}

