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

<br>

<div class="tab-content">
    <div class="tab-pane fade show active" role="tabpanel" id="pills-all-time" aria-labelledby="pills-all-time-tab">
        <div class="row">
            <#assign numTotalOfAlbumsPerYear = 0>
            <#list musicByYears as year>
                <#assign numTotalOfAlbumsPerYear += year?size>
            </#list>
            <div class="col-sm-12">
                <h6>[Total number of albums per year: ${numTotalOfAlbumsPerYear}]</h6>
            </div>
        </div>

        <div class="row">
            <div class="col-sm-6">
                <canvas id="numberOfAlbumsPerYearChart"></canvas>
                <#include "charts/number_of_albums_per_year.ftl">
            </div>

            <div class="col-sm-6">
                <canvas id="numberOfAlbumsPerYearAverageRatingChart"></canvas>
                <#include "charts/number_of_albums_per_year_average_rating.ftl">
            </div>
        </div>

        <div class="row">
            <div class="col-sm-12">
                <#assign numTotalOfAlbumsPerGenre = 0>
                <#list musicByGenres as genre>
                    <#assign numTotalOfAlbumsPerGenre += genre?size>
                </#list>
                <h6>[Total number of albums per genre: ${numTotalOfAlbumsPerGenre}]</h6>

                <canvas id="numberOfAlbumsPerGenreChart"></canvas>
                <#include "charts/number_of_albums_per_genre.ftl">
            </div>
        </div>

        <div class="panel-body">
            <!--  Export  -->
            <div class="form-group row float-left">
                <form method="post" action="/stats">
                    <div class="col-sm-12">
                        <input type="hidden" name="action" value="export">
                        <button type="submit" class="btn btn-primary d-flex justify-content-center align-content-between">
                            <i class="material-icons">arrow_downward</i>
                            Export to CSV
                        </button>
                    </div>
                </form>
            </div>

            <!--  Clear  -->
            <#if user??>
                <div class="form-group row float-right">
                    <form method="post" action="/stats" onsubmit="return confirm('Are you sure you want to clear all the music?');">
                        <div class="col-sm-12">
                            <input type="hidden" name="action" value="clear">
                            <button type="submit" class="btn btn-danger d-flex justify-content-center align-content-between">
                                <i class="material-icons">clear</i>
                                Clear
                            </button>
                        </div>
                    </form>
                </div>
            </#if>
        </div>
    </div>
    <#list years as year>
        <div class="tab-pane fade"     role="tabpanel" id="pills-${year}" aria-labelledby="pills-${year}-tab">
            <div class="col-sm-6">
<!--                <canvas id="numberOfAlbumsPerMonthsChart"></canvas>-->
<!--                <canvas id="numberOfAlbumsPerMonthsChart${year}"></canvas>-->
            </div>
        </div>
    </#list>
</div>

</@common.page>
