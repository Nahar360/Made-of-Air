<script>

var ctxPerYearAverageRating = document.getElementById('numberOfAlbumsPerYearAverageRatingChart').getContext('2d');

var labelsArrayPerYearAverageRating = [<#list years as year>${year},</#list>];
var dataArrayPerYearAverageRating = [<#list listOfAverageRatings as avgRating>${avgRating},</#list>];
var backgroundColorsPerYearAverageRating = []
var borderColorsPerYearAverageRating = []
for (var i = 0; i < ${musicByYears?size}; i++)
{
    var r = Math.floor(Math.random() * (256));
    var g = Math.floor(Math.random() * (256));
    var b = Math.floor(Math.random() * (256));

    var bgColor = 'rgba(' + r + ',' + g + ',' + b + ', 0.2)'
    var bdColor = 'rgba(' + r + ',' + g + ',' + b + ', 1.0)'

    backgroundColorsPerYearAverageRating.push(bgColor)
    backgroundColorsPerYearAverageRating.push(bdColor)
}

var numberOfAlbumsPerYearAverageRatingChart = new Chart(ctxPerYearAverageRating, {
    type: 'bar',
    data: {
        labels: labelsArrayPerYearAverageRating,
        datasets: [{
            label: 'Average Rating of Albums per Year',
            data: dataArrayPerYearAverageRating,
            backgroundColor: backgroundColorsPerYearAverageRating,
            borderColor: borderColorsPerYearAverageRating,
            borderWidth: 1
        }]
    },
    options: {
        scales: {
            yAxes: [{
                ticks: {
                    beginAtZero: true
                }
            }]
        }
    }
});

</script>
