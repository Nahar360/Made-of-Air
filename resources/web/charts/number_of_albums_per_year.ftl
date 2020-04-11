<script>
var ctxPerYear = document.getElementById('numberOfAlbumsPerYearChart').getContext('2d');

var labelsArrayPerYear = [<#list years as year>${year},</#list>];
var dataArrayPerYear = [<#list musicByYears as year>${year?size},</#list>];
var backgroundColorsPerYear = []
var borderColorsPerYear = []
for (var i = 0; i < ${musicByYears?size}; i++)
{
    var r = Math.floor(Math.random() * (256));
    var g = Math.floor(Math.random() * (256));
    var b = Math.floor(Math.random() * (256));

    var bgColor = 'rgba(' + r + ',' + g + ',' + b + ', 0.2)'
    var bdColor = 'rgba(' + r + ',' + g + ',' + b + ', 1.0)'

    backgroundColorsPerYear.push(bgColor)
    backgroundColorsPerYear.push(bdColor)
}

var numberOfAlbumsPerYearChart = new Chart(ctxPerYear, {
    type: 'bar',
    data: {
        labels: labelsArrayPerYear,
        datasets: [{
            label: 'Number of Albums per Year',
            data: dataArrayPerYear,
            backgroundColor: backgroundColorsPerYear,
            borderColor: borderColorsPerYear,
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
