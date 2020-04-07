<#import "common/bootstrap.ftl" as common>
<@common.page>

<br>
<h1>Music</h1>
<br>

<div class="panel-body">
    <!-- Retrieve music -->
    <#if music?? && (music?size > 0)>
    <table class="table table-striped table-bordered">
        <thead class="thead-dark">
        <tr>
            <th scope="col">Id</th>
            <th scope="col">Year</th>
            <th scope="col">Month</th>
            <th scope="col">Band</th>
            <th scope="col">Album</th>
            <th scope="col">Genre</th>
            <th scope="col">Rating</th>
            <th scope="col">Best song</th>
            <#if user??>
                <th scope="col"></th>
                <th scope="col"></th>
            </#if>
        </tr>
        </thead>
        <tbody>
            <#list music as album>
                <tr>
                    <th scope="row" style="vertical-align: middle;">${album.id}</th>
                    <td style="text-align: center; vertical-align: middle;">${album.year}</td>
                    <td style="text-align: center; vertical-align: middle;">${album.month}</td>
                    <td style="text-align: center; vertical-align: middle;">${album.band}</td>
                    <td style="text-align: center; vertical-align: middle;">${album.album}</td>
                    <td style="text-align: center; vertical-align: middle;">${album.genre}</td>
                    <td style="text-align: center; vertical-align: middle;">${album.rating}</td>
                    <td style="text-align: center; vertical-align: middle;">${album.bestSong}</td>
                    <#if user??>
                        <td style="text-align: center; vertical-align: middle;">
                            <form action="/music/${album.id}">
                                <button type="submit" class="btn btn-default btn-circle" title="Modify"><i class="material-icons">edit</i></button>
                            </form>
                        </td>
                        <td style="text-align: center; vertical-align: middle;">
                            <form method="post" action="/music">
                                <input type="hidden" name="musicId" value="${album.id}">
                                <input type="hidden" name="action" value="delete">
                                <button type="submit" class="btn btn-default btn-circle" title="Delete"><i class="material-icons">delete</i></button>
                            </form>
                        </td>
                    </#if>
                </tr>
            </#list>
        </tbody>
    </table>

    <#if user??>
        <div class="form-group row">
            <form method="post" action="/music" onsubmit="return confirm('Are you sure you want to clear all the music?');">
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

    <#else>
        <label class="form-check-label">No music added.</label>
    </#if>
</div>

</@common.page>
