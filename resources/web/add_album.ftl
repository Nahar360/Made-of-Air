<#import "common/bootstrap.ftl" as common>
<@common.page>

<br>
<h1>Add album</h1>
<br>

<div class="panel-body">
    <form method="post" action="/add_album">
        <div class="col-12">

            <!--  Year  -->
            <div class="form-group row">
                <label for="year" class="col-sm-1 col-form-label">Year</label>
                <div class="col-sm-7">
                    <select class="form-control" id="year" name="year" required>
                        <#list years as year>
                            <option>${year}</option>
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
                            <option>${month}</option>
                        </#list>
                    </select>
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

            <!--  Genre  -->
            <div class="form-group row">
                <label for="genre" class="col-sm-1 col-form-label">Genre</label>
                <div class="col-sm-7">
                    <select class="form-control" id="genre" name="genre" required>
                        <#list genres as genre>
                            <option>${genre}</option>
                        </#list>
                    </select>
                </div>
            </div>

            <!--  Rating  -->
            <div class="form-group row">
                <label for="rating" class="col-sm-1 col-form-label">Rating</label>
                <div class="col-sm-7">
                    <div class="btn-group btn-group-toggle" data-toggle="buttons">
                        <#list 1..100 as x>
                            <#if x % 5 == 0>
                                <#if (x >= 0) && (x <= 25)>
                                    <label class="btn btn-danger">
                                </#if>
                                <#if (x > 25) && (x <= 50)>
                                    <label class="btn btn-warning">
                                </#if>
                                <#if (x > 50) && (x <= 75)>
                                    <label class="btn btn-info">
                                </#if>
                                <#if (x > 75) && (x <= 100)>
                                    <label class="btn btn-success">
                                </#if>
                                        <input type="radio" id="rating" name="rating" value="${x}"> ${x}
                                    </label>
                            </#if>
                        </#list>
                    </div>
                </div>
            </div>

            <!--  Best song  -->
            <div class="form-group row">
                <label for="bestSong" class="col-sm-1 col-form-label">Best song</label>
                <div class="col-sm-7">
                    <input type="text" class="form-control" id="bestSong" name="bestSong" placeholder="Best song">
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
