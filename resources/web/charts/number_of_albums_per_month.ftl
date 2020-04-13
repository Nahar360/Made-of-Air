<script>
var ctxPerYear = document.getElementById('numberOfAlbumsPerMonthChart' + '${year}').getContext('2d');

var labelsArrayPerYear = [<#list months as month>'${month?capitalize}',</#list>];

var dataArrayPerYear = [
    <#list musicByYears as music>
        <#list months as month>
            <#assign monthSize = 0>
            <#list music as album>
                <#if (year == album.year[5..]) && (month == album.month)>
                    <#assign monthSize += 1>
                </#if>
            </#list>
            ${monthSize},
        </#list>
    </#list>
];

var yearIndex =
    <#list years as allYears>
        <#if year == allYears>
            ${allYears?index}
        </#if>
    </#list>

var dataArrayPerYearToPlot = dataArrayPerYear.slice( 12 * yearIndex, 12 * yearIndex + 12);

var backgroundColorsPerYear = []
var borderColorsPerYear = []
for (var i = 0; i < ${months?size}; i++)
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
            label: 'Number of Albums per Month',
            data: dataArrayPerYearToPlot,
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
