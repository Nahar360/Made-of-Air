<#import "common/bootstrap.ftl" as common>
<@common.page>

<br>
<h2>${album.band} - ${album.album}</h2>
<br>

<div class="panel-body">
    <form method="post" action="/music/${album.id}">
        <div class="row">

            <!-- Album information -->
            <div class="col-6">

                <!-- Year -->
                <div class="form-group row">
                    <label for="year" class="col-sm-2 col-form-label">Year</label>
                    <div class="col-sm-8">
                        <select class="form-control" id="year" name="year">
                            <#list years as year>
                                <#if year == album.year>
                                    <option selected>${album.year}</option>
                                <#else>
                                    <option>${year}</option>
                                </#if>
                            </#list>
                        </select>
                    </div>
                </div>

                <!-- Month -->
                <div class="form-group row">
                    <label for="year" class="col-sm-2 col-form-label">Month</label>
                    <div class="col-sm-8">
                        <select class="form-control" id="month" name="month">
                            <#list months as month>
                                <#if month == album.month>
                                    <option selected>${album.month}</option>
                                <#else>
                                    <option>${month}</option>
                                </#if>
                            </#list>
                        </select>
                    </div>
                </div>

                <!-- Band -->
                <div class="form-group row">
                    <label for="band" class="col-sm-2 col-form-label">Band</label>
                    <div class="col-sm-8">
                        <input type="text" class="form-control" id="band" name="band" value="${album.band}" placeholder="Band">
                    </div>
                </div>

                <!-- Album -->
                <div class="form-group row">
                    <label for="album" class="col-sm-2 col-form-label">Band</label>
                    <div class="col-sm-8">
                        <input type="text" class="form-control" id="album" name="album" value="${album.album}" placeholder="Album">
                    </div>
                </div>

                <!-- Genre -->
                <div class="form-group row">
                    <label for="genre" class="col-sm-2 col-form-label">Genre</label>
                    <div class="col-sm-8">
                        <input type="text" class="form-control" id="genre" name="genre" value="${album.genre}" placeholder="Genre">
                    </div>
                </div>

                <!-- Rating -->
                <div class="form-group row">
                    <label for="rating" class="col-sm-2 col-form-label">Rating</label>
                    <div class="col-sm-8">
                        <input type="range" class="form-control custom-range" id="rating" name="rating" value="${album.rating}" min="0" max="100" step="5" placeholder="Rating">
                    </div>
                </div>

                <!-- Best song -->
                <div class="form-group row">
                    <label for="bestSong" class="col-sm-2 col-form-label">Best song</label>
                    <div class="col-sm-8">
                        <input type="text" class="form-control" id="bestSong" name="bestSong" value="${album.bestSong}" placeholder="Best song">
                    </div>
                </div>

                <div class="form-group row">
                    <div class="col-sm-10">
                        <input type="hidden" name="id" value="${album.id}">
                        <input type="hidden" name="action" value="modify">
                        <button type="submit" class="btn btn-success">Modify</button>
                    </div>
                </div>

            </div>

        </div>
    </form>
</div>

</@common.page>
