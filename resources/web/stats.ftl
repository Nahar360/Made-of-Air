<#import "common/bootstrap.ftl" as common>
<@common.page>

<!-- Chart.js -->
<script type="text/javascript" src="https://cdnjs.cloudflare.com/ajax/libs/Chart.js/2.9.3/Chart.min.js"></script>

<br>
<h1>Stats</h1>
<br>

<ul class="nav nav-pills nav-justified">
    <li class="nav-item">
        <a class="nav-link active" role="tab" data-toggle="pill" id="pills-all-time-tab" href="#pills-all-time" aria-controls="pills-all-time" aria-selected="true">ALL TIME</a>
    </li>
    <#list years as year>
        <li class="nav-item">
            <a class="nav-link" role="tab" data-toggle="pill" id="pills-${year}-tab" href="#pills-${year}" aria-controls="pills-${year}" aria-selected="false">${year}</a>
        </li>
    </#list>
</ul>

<div class="tab-content">
    <div class="tab-pane fade show active" role="tabpanel" id="pills-all-time" aria-labelledby="pills-all-time-tab">
        <div class="col-sm-6">
            <canvas id="numberOfAlbumsPerYearChart"></canvas>
        </div>
    </div>
    <#list years as year>
        <div class="tab-pane fade"     role="tabpanel" id="pills-${year}" aria-labelledby="pills-${year}-tab">
            <div class="col-sm-6">
                <canvas id="numberOfAlbumsPerMonthsChart"></canvas>
<!--                <canvas id="numberOfAlbumsPerMonthsChart${year}"></canvas>-->
            </div>
        </div>
    </#list>
</div>

<script type="text/javascript">
//
// numberOfAlbumsPerYearChart
//
var ctx = document.getElementById('numberOfAlbumsPerYearChart').getContext('2d');

var labelsArray = [<#list years as year>${year},</#list>];
var dataArray = [<#list musicByYears as year>${year?size},</#list>];
var backgroundColors = []
var borderColors = []
for (var i = 0; i < ${musicByYears?size}; i++)
{
    var r = Math.floor(Math.random() * (256));
    var g = Math.floor(Math.random() * (256));
    var b = Math.floor(Math.random() * (256));

    var bgColor = 'rgba(' + r + ',' + g + ',' + b + ', 0.2)'
    var bdColor = 'rgba(' + r + ',' + g + ',' + b + ', 1.0)'

    backgroundColors.push(bgColor)
    backgroundColors.push(bdColor)
}

var numberOfAlbumsPerYearChart = new Chart(ctx, {
    type: 'bar',
    data: {
        labels: labelsArray,
        datasets: [{
            label: '# of Albums per Year',
            data: dataArray,
            backgroundColor: backgroundColors,
            borderColor: borderColors,
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

//
// numberOfAlbumsPerMonthsChart
//
var ctx1 = document.getElementById('numberOfAlbumsPerMonthsChart').getContext('2d');

var labelsArray1 = [<#list years as year>${year},</#list>];
var dataArray1 = [<#list musicByYears as year>${year?size},</#list>];
var backgroundColors1 = []
var borderColors1 = []
for (var i = 0; i < ${musicByYears?size}; i++)
{
    var r = Math.floor(Math.random() * (256));
    var g = Math.floor(Math.random() * (256));
    var b = Math.floor(Math.random() * (256));

    var bgColor = 'rgba(' + r + ',' + g + ',' + b + ', 0.2)'
    var bdColor = 'rgba(' + r + ',' + g + ',' + b + ', 1.0)'

    backgroundColors1.push(bgColor)
    backgroundColors1.push(bdColor)
}

var numberOfAlbumsPerMonthsChart = new Chart(ctx1, {
    type: 'bar',
    data: {
        labels: labelsArray1,
        datasets: [{
            label: '# of Albums per Year',
            data: dataArray1,
            backgroundColor: backgroundColors1,
            borderColor: borderColors1,
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

</@common.page>
