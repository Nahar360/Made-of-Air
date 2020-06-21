<#import "common/bootstrap.ftl" as common>
<@common.page>

<br>
<h1>Add Pitchfork Best New Album</h1>
<br>

<div class="panel-body">
    <form method="post" action="/add_album_pitchfork">
        <div class="col-12">

            <!--  Year  -->
            <div class="form-group row">
                <label for="year" class="col-sm-1 col-form-label">Year</label>
                <div class="col-sm-7">
                    <select class="form-control" id="year" name="year" required>
                        <#list years as year>
                            <#if year == currentYear>
                                <option selected>${year}</option>
                            <#else>
                                <option>${year}</option>
                            </#if>
                        </#list>
                    </select>
                </div>
            </div>

            <!--  Month  -->
            <div class="form-group row">
                <label for="month" class="col-sm-1 col-form-label">Month</label>
                <div class="col-sm-7">
                    <select class="form-control" id="month" name="month" required>
                        <#list months as month>
                            <#if month == currentMonth>
                                <option selected>${month}</option>
                            <#else>
                                <option>${month}</option>
                            </#if>
                        </#list>
                    </select>
                </div>
            </div>

            <!--  Day  -->
            <div class="form-group row">
                <label for="day" class="col-sm-1 col-form-label">Day</label>
                <div class="col-sm-7">
                    <input type="number" step="1" class="form-control" id="day" name="day" placeholder="Day" required>
                </div>
            </div>

            <!--  Band  -->
            <div class="form-group row">
                <label for="band" class="col-sm-1 col-form-label">Band</label>
                <div class="col-sm-7">
                    <input type="text" class="form-control" id="band" name="band" placeholder="Band" required>
                </div>
            </div>

            <!--  Album  -->
            <div class="form-group row">
                <label for="album" class="col-sm-1 col-form-label">Album</label>
                <div class="col-sm-7">
                    <input type="text" class="form-control" id="album" name="album" placeholder="Album" required>
                </div>
            </div>

            <!--  Rating  -->
            <div class="form-group row">
                <label for="rating" class="col-sm-1 col-form-label">Rating</label>
                <div class="col-sm-7">
                    <div class="btn-group btn-group-toggle" data-toggle="buttons">
                        <#list 81..100 as x>
                            <#if (x >= 81) && (x < 86)>
                                <label class="btn btn-secondary">
                            </#if>
                            <#if (x >= 86) && (x < 90)>
                                <label class="btn btn-info">
                            </#if>
                            <#if (x >= 90) && (x < 95)>
                                <label class="btn btn-success">
                            </#if>
                            <#if (x >= 95)>
                                <label class="btn btn-warning">
                            </#if>
                                    <#assign ratingDividedByTen = x / 10>
                                    <input type="radio" id="rating" name="rating" value="${ratingDividedByTen}" required> ${x}
                                </label>
                        </#list>
                    </div>
                </div>
            </div>

            <div class="form-group row">
                <div class="col-sm-12">
                    <input type="hidden" name="action" value="add">
                    <button type="submit" class="btn btn-success d-flex justify-content-center align-content-between">
                        <i class="material-icons">add</i>
                        Add
                    </button>
                </div>
            </div>

        </div>
    </form>
</div>

</@common.page>
