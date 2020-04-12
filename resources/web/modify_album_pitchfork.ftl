<#import "common/bootstrap.ftl" as common>
<@common.page>

<br>
<h2>${album.band} - ${album.album}</h2>
<br>

<div class="panel-body">
    <form method="post" action="/pitchfork/${album.id}">
        <div class="col-12">

            <!-- Year -->
            <div class="form-group row">
                <label for="year" class="col-sm-1 col-form-label">Year</label>
                <div class="col-sm-7">
                    <select class="form-control" id="year" name="year" required>
                        <#list years as year>
                            <#if year == album.year[5..]>
                                <option selected>${album.year[5..]}</option>
                            <#else>
                                <option>${year}</option>
                            </#if>
                        </#list>
                    </select>
                </div>
            </div>

            <!-- Month -->
            <div class="form-group row">
                <label for="month" class="col-sm-1 col-form-label">Month</label>
                <div class="col-sm-7">
                    <select class="form-control" id="month" name="month" required>
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

            <!-- Day -->
            <div class="form-group row">
                <label for="day" class="col-sm-1 col-form-label">Band</label>
                <div class="col-sm-7">
                    <input type="number" step="1" class="form-control" id="day" name="day" value="${album.day}" placeholder="Day" required>
                </div>
            </div>

            <!-- Band -->
            <div class="form-group row">
                <label for="band" class="col-sm-1 col-form-label">Band</label>
                <div class="col-sm-7">
                    <input type="text" class="form-control" id="band" name="band" value="${album.band}" placeholder="Band" required>
                </div>
            </div>

            <!-- Album -->
            <div class="form-group row">
                <label for="album" class="col-sm-1 col-form-label">Album</label>
                <div class="col-sm-7">
                    <input type="text" class="form-control" id="album" name="album" value="${album.album}" placeholder="Album" required>
                </div>
            </div>

            <!-- Rating -->
            <div class="form-group row">
                <label for="rating" class="col-sm-1 col-form-label">Rating</label>
                <div class="col-sm-7">
                    <div class="btn-group btn-group-toggle" data-toggle="buttons">
                        <#list 81..100 as x>
                            <#assign ratingDividedByTen = x / 10>
                            <#if (x >= 81) && (x < 86)>
                                <#if ratingDividedByTen == album.rating?number>
                                    <label class="btn btn-secondary active">
                                    <input type="radio" id="rating" name="rating" value="${ratingDividedByTen}" checked required> ${x}
                                <#else>
                                    <label class="btn btn-secondary">
                                    <input type="radio" id="rating" name="rating" value="${ratingDividedByTen}" required> ${x}
                                </#if>
                            </#if>
                            <#if (x >= 86) && (x < 90)>
                                <#if ratingDividedByTen == album.rating?number>
                                    <label class="btn btn-info active">
                                    <input type="radio" id="rating" name="rating" value="${ratingDividedByTen}" checked required> ${x}
                                <#else>
                                    <label class="btn btn-info">
                                    <input type="radio" id="rating" name="rating" value="${ratingDividedByTen}" required> ${x}
                                </#if>
                            </#if>
                            <#if (x >= 90) && (x < 95)>
                                <#if ratingDividedByTen == album.rating?number>
                                    <label class="btn btn-success active">
                                    <input type="radio" id="rating" name="rating" value="${ratingDividedByTen}" checked required> ${x}
                                <#else>
                                    <label class="btn btn-success">
                                    <input type="radio" id="rating" name="rating" value="${ratingDividedByTen}" required> ${x}
                                </#if>
                            </#if>
                            <#if (x >= 95)>
                                <#if ratingDividedByTen == album.rating?number>
                                    <label class="btn btn-warning active">
                                    <input type="radio" id="rating" name="rating" value="${ratingDividedByTen}" checked required> ${x}
                                <#else>
                                    <label class="btn btn-warning">
                                    <input type="radio" id="rating" name="rating" value="${ratingDividedByTen}" required> ${x}
                                </#if>
                            </#if>
                            </label>
                        </#list>
                    </div>
                </div>
            </div>

            <div class="form-group row">
                <div class="col-sm-12">
                    <input type="hidden" name="id" value="${album.id}">
                    <input type="hidden" name="action" value="modify">
                    <button type="submit" class="btn btn-success">Modify</button>
                </div>
            </div>

        </div>

    </form>
</div>

</@common.page>
