<#import "common/bootstrap.ftl" as common>
<@common.page>

<style>
    th {text-align: center;}
</style>

<br>
<h1>Pitchfork ${year}</h1>

<#assign numTotalAlbums = 0>
<#list pitchfork as month>
    <#assign numTotalAlbums += month?size>
</#list>
<h6>[${numTotalAlbums} albums]</h6>
<br>

<div class="panel-body">

    <!--  Table/grid switch  -->
    <div class="custom-control custom-switch">
        <input type="checkbox" class="custom-control-input" id="tableGridSwitch" onclick="tableGrid()">
        <label class="custom-control-label" for="tableGridSwitch" id="tableGridSwitchLabel">Table</label>
    </div>

    <br>

    <!--  Table  -->
    <div id="table" style="display: block">
        <ul class="nav nav-pills nav-justified">
            <#list months as month>
                <li class="nav-item">
                    <#if year == currentYear>
                        <#if month == currentMonth>
                            <a class="nav-link active" role="tab" data-toggle="pill" id="pills-${month}-tab-table" href="#pills-${month}-table" aria-controls="pills-${month}-table" aria-selected="true">${month}</a>
                        <#else>
                            <a class="nav-link"        role="tab" data-toggle="pill" id="pills-${month}-tab-table" href="#pills-${month}-table" aria-controls="pills-${month}-table" aria-selected="false">${month}</a>
                        </#if>
                    <#elseif month?counter == 1>
                        <a class="nav-link active" role="tab" data-toggle="pill" id="pills-${month}-tab-table" href="#pills-${month}-table" aria-controls="pills-${month}-table" aria-selected="true">${month}</a>
                    <#else>
                        <a class="nav-link"        role="tab" data-toggle="pill" id="pills-${month}-tab-table" href="#pills-${month}-table" aria-controls="pills-${month}-table" aria-selected="false">${month}</a>
                    </#if>
                </li>
            </#list>
        </ul>

        <br>

        <div class="tab-content">
            <#list pitchfork as month>

                <#assign monthIndex = month?counter - 1>
                <#if year == currentYear>
                    <#if months[monthIndex] == currentMonth>
                        <div class="tab-pane fade show active" role="tabpanel" id="pills-${months[monthIndex]}-table" aria-labelledby="pills-${months[monthIndex]}-tab-table">
                    <#else>
                        <div class="tab-pane fade"             role="tabpanel" id="pills-${months[monthIndex]}-table" aria-labelledby="pills-${months[monthIndex]}-tab-table">
                    </#if>
                <#elseif monthIndex == 0>
                    <div class="tab-pane fade show active" role="tabpanel" id="pills-${months[monthIndex]}-table" aria-labelledby="pills-${months[monthIndex]}-tab-table">
                <#else>
                    <div class="tab-pane fade"             role="tabpanel" id="pills-${months[monthIndex]}-table" aria-labelledby="pills-${months[monthIndex]}-tab-table">
                </#if>

                <#if (month?size > 0)>
                    <h6>${month?size} albums.</h6>
                    <div class="table-responsive">
                        <table class="table table-bordered">
                            <thead class="thead-dark">
                                <tr>
                                    <th scope="col"></th>
                                    <th scope="col">Year</th>
                                    <th scope="col">Month</th>
                                    <th scope="col">Day</th>
                                    <th scope="col">Band</th>
                                    <th scope="col">Album</th>
                                    <th scope="col">Rating</th>
                                    <#if user??>
                                        <th scope="col"></th>
                                        <th scope="col"></th>
                                    </#if>
                                </tr>
                            </thead>
                            <tbody>
                                <#list month as album>
                                        <#if (album.rating != "") && (album.rating?number >= 8.1) && (album.rating?number < 8.6)>
                                            <tr>
                                        </#if>
                                        <#if (album.rating != "") && (album.rating?number >= 8.6) && (album.rating?number < 9.0)>
                                            <tr bgcolor="#A77044">
                                        </#if>
                                        <#if (album.rating != "") && (album.rating?number >= 9.0) && (album.rating?number < 9.5)>
                                            <tr bgcolor="#A7A7AD">
                                        </#if>
                                        <#if (album.rating != "") && (album.rating?number >= 9.5)>
                                            <tr bgcolor="#D6AF36">
                                        </#if>

                                        <#assign
                                        artistFormatted = album.month
                                                        + album.band?replace(" ", "")?replace(".", "")?replace("&", "")?replace("-", "")?replace("'", "")?replace("$", "")?replace("/", "")?replace("+", "")?replace(",", "")?replace("(", "")?replace(")", "")?replace("!", "")
                                                        + album.year[5..]
                                        >

                                        <td style="text-align: center; vertical-align: middle;" id=${artistFormatted}>
                                            <script>
                                                var apiUrl = "http://ws.audioscrobbler.com/2.0/";
                                                var methodAlbumGetInfo = "album.getinfo";
                                                var apiKey = "9ec89d26273968e5f07f6f6d69d09552";
                                                var formatJsonAndCallback = "&format=json&callback=?";

                                                var albumApiCall = apiUrl + "?method=" + methodAlbumGetInfo + "&api_key=" + apiKey + "&artist=" + "${album.band}" + "&album=" + "${album.album}" + formatJsonAndCallback;
                                                $.getJSON(albumApiCall, function(json) {
                                                    if (typeof json.album !== 'undefined') {
                                                        var image = JSON.stringify(json.album.image[1]);
                                                        var imageUrl = image.substring(image.indexOf("text") + 7).slice(0, -18);
                                                        var cover = "<img src=" + imageUrl + ">";

                                                        $(${artistFormatted}).append(cover);
                                                    }
                                                    else {
                                                        var cover = "<img src="//:0">";

                                                        $(${artistFormatted}).append(cover);
                                                    }
                                                });
                                            </script>
                                        </td>

                                        <td style="text-align: center; vertical-align: middle;">${album.year[5..]}</td>
                                        <td style="text-align: center; vertical-align: middle;">${album.month}</td>
                                        <td style="text-align: center; vertical-align: middle;">${album.day}</td>
                                        <td style="text-align: center; vertical-align: middle;"><b>${album.band}</b></td>
                                        <td style="text-align: center; vertical-align: middle;">${album.album}</td>
                                        <td style="text-align: center; vertical-align: middle;">${album.rating}</td>
                                        <#if user??>
                                            <td style="text-align: center; vertical-align: middle;">
                                                <form action="/pitchfork/${album.id}">
                                                    <button type="submit" class="btn btn-default btn-circle" title="Modify"><i class="material-icons">edit</i></button>
                                                </form>
                                            </td>
                                            <td style="text-align: center; vertical-align: middle;">
                                                <form method="post" action="/pitchfork/year/${year}">
                                                    <input type="hidden" name="pitchforkId" value="${album.id}">
                                                    <input type="hidden" name="action" value="delete">
                                                    <button type="submit" class="btn btn-default btn-circle" title="Delete"><i class="material-icons">delete</i></button>
                                                </form>
                                            </td>
                                        </#if>
                                    </tr>
                                </#list>
                            </tbody>
                        </table>
                    </div>
                <#else>
                    <label class="form-check-label">There are no albums for ${year} ${months[monthIndex]}.</label>
                </#if>
                </div>
            </#list>
        </div>
    </div>

    <!--  Grid  -->
    <div id="grid" style="display: none">
        <ul class="nav nav-pills nav-justified">
            <#list months as month>
                <li class="nav-item">
                    <#if year == currentYear>
                        <#if month == currentMonth>
                            <a class="nav-link active" role="tab" data-toggle="pill" id="pills-${month}-tab-grid" href="#pills-${month}-grid" aria-controls="pills-${month}-grid" aria-selected="true">${month}</a>
                        <#else>
                            <a class="nav-link"        role="tab" data-toggle="pill" id="pills-${month}-tab-grid" href="#pills-${month}-grid" aria-controls="pills-${month}-grid" aria-selected="false">${month}</a>
                        </#if>
                    <#elseif month?counter == 1>
                        <a class="nav-link active" role="tab" data-toggle="pill" id="pills-${month}-tab-grid" href="#pills-${month}-grid" aria-controls="pills-${month}-grid" aria-selected="true">${month}</a>
                    <#else>
                        <a class="nav-link"        role="tab" data-toggle="pill" id="pills-${month}-tab-grid" href="#pills-${month}-grid" aria-controls="pills-${month}-grid" aria-selected="false">${month}</a>
                    </#if>
                </li>
            </#list>
        </ul>

        <br>

        <div class="tab-content">
            <#list pitchfork as month>

                <#assign monthIndex = month?counter - 1>
                <#if year == currentYear>
                    <#if months[monthIndex] == currentMonth>
                        <div class="tab-pane fade show active" role="tabpanel" id="pills-${months[monthIndex]}-grid" aria-labelledby="pills-${months[monthIndex]}-tab-grid">
                    <#else>
                        <div class="tab-pane fade"             role="tabpanel" id="pills-${months[monthIndex]}-grid" aria-labelledby="pills-${months[monthIndex]}-tab-grid">
                    </#if>
                <#elseif monthIndex == 0>
                    <div class="tab-pane fade show active" role="tabpanel" id="pills-${months[monthIndex]}-grid" aria-labelledby="pills-${months[monthIndex]}-tab-grid">
                <#else>
                    <div class="tab-pane fade"             role="tabpanel" id="pills-${months[monthIndex]}-grid" aria-labelledby="pills-${months[monthIndex]}-tab-grid">
                </#if>

                    <#if (month?size > 0)>
                        <h6>${month?size} albums.</h6>
                        <div class="row">
                            <#list month as album>
                                <div class="col-sm-3">
                                    <div class="card border-dark text-center">
                                        <div class="card-header">${album.band} - ${album.album}</div>
                                        <div class="card-body">
                                            <p class="card-text">${album.year[5..]} ${album.month} ${album.day}</p>
                                            <h5 class="card-title">${album.rating}</h5>
                                            <#if user??>
                                                <div class="row">
                                                    <div class="col-sm">
                                                        <form action="/pitchfork/${album.id}">
                                                            <button type="submit" class="btn btn-default btn-circle" title="Modify"><i class="material-icons">edit</i></button>
                                                        </form>
                                                    </div>
                                                    <div class="col-sm">
                                                        <form method="post" action="/pitchfork/year/${year}">
                                                            <input type="hidden" name="musicId" value="${album.id}">
                                                            <input type="hidden" name="action" value="delete">
                                                            <button type="submit" class="btn btn-default btn-circle" title="Delete"><i class="material-icons">delete</i></button>
                                                        </form>
                                                    </div>
                                                </div>
                                            </#if>
                                        </div>
                                    </div>
                                </div>
                            </#list>
                        </div>
                    <#else>
                        <label class="form-check-label">There are no albums for ${year} ${months[monthIndex]}.</label>
                    </#if>
                </div>
            </#list>
        </div>
    </div>

    <!--  Clear  -->
    <#if user??>
        <div class="form-group row">
            <form method="post" action="/pitchfork/year/${year}" onsubmit="return confirm('Are you sure you want to clear all the Pitchfork music from ${year}?');">
                <div class="col-sm-10">
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

<script>
    function tableGrid() {
      var table = document.getElementById("table");
      var grid = document.getElementById("grid");
      var tableGridSwitchLabel = document.getElementById("tableGridSwitchLabel");

      if (document.getElementById("tableGridSwitch").checked) {
        tableGridSwitchLabel.innerHTML = "Grid"

        table.style.display = "none";
        grid.style.display = "block";
      }
      else {
        tableGridSwitchLabel.innerHTML = "Table"

        table.style.display = "block";
        grid.style.display = "none";
      }
    }
</script>

</@common.page>
