<script>
var ctxPerGenre = document.getElementById('numberOfAlbumsPerGenreChart').getContext('2d');

var labelsArrayPerGenre = [<#list genres as genre>'${genre}',</#list>];
var dataArrayPerGenre = [<#list musicByGenres as genre>${genre?size},</#list>];
var backgroundColorsPerGenre = []
var borderColorsPerGenre = []
for (var i = 0; i < ${musicByGenres?size}; i++)
{
    var r = Math.floor(Math.random() * (256));
    var g = Math.floor(Math.random() * (256));
    var b = Math.floor(Math.random() * (256));

    var bgColor = 'rgba(' + r + ',' + g + ',' + b + ', 0.2)'
    var bdColor = 'rgba(' + r + ',' + g + ',' + b + ', 1.0)'

    backgroundColorsPerGenre.push(bgColor)
    borderColorsPerGenre.push(bdColor)
}

var numberOfAlbumsPerGenreChart = new Chart(ctxPerGenre, {
    type: 'bar',
    data: {
        labels: labelsArrayPerGenre,
        datasets: [{
            label: 'Number of Albums per Genre',
            data: dataArrayPerGenre,
            backgroundColor: backgroundColorsPerGenre,
            borderColor: borderColorsPerGenre,
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
