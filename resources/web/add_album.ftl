<#import "common/bootstrap.ftl" as common>
<@common.page>

<br>
<h1>Add album</h1>
<br>

<div class="panel-body">
    <form method="post" action="/add_album">
        <div class="col-6">

            <!--  Year  -->
            <div class="form-group row">
                <label for="year" class="col-sm-2 col-form-label">Year</label>
                <div class="col-sm-8">
                    <select class="form-control" id="year" name="year">
                        <option>2010</option>
                        <option>2011</option>
                        <option>2012</option>
                        <option>2013</option>
                        <option>2014</option>
                        <option>2015</option>
                        <option>2016</option>
                        <option>2017</option>
                        <option>2018</option>
                        <option>2019</option>
                        <option>2020</option>
                    </select>
                </div>
            </div>

            <!--  Month  -->
            <div class="form-group row">
                <label for="month" class="col-sm-2 col-form-label">Month</label>
                <div class="col-sm-8">
                    <select class="form-control" id="month" name="month">
                        <option>JANUARY</option>
                        <option>FEBRUARY</option>
                        <option>MARCH</option>
                        <option>APRIL</option>
                        <option>MAY</option>
                        <option>JUNE</option>
                        <option>JULY</option>
                        <option>AUGUST</option>
                        <option>SEPTEMBER</option>
                        <option>OCTOBER</option>
                        <option>NOVEMBER</option>
                        <option>DECEMBER</option>
                    </select>
                </div>
            </div>

            <!--  Band  -->
            <div class="form-group row">
                <label for="band" class="col-sm-2 col-form-label">Band</label>
                <div class="col-sm-8">
                    <input type="text" class="form-control" id="band" name="band" placeholder="Band">
                </div>
            </div>

            <!--  Album  -->
            <div class="form-group row">
                <label for="album" class="col-sm-2 col-form-label">Album</label>
                <div class="col-sm-8">
                    <input type="text" class="form-control" id="album" name="album" placeholder="Album">
                </div>
            </div>

            <!--  Genre  -->
            <div class="form-group row">
                <label for="genre" class="col-sm-2 col-form-label">Genre</label>
                <div class="col-sm-8">
                    <input type="text" class="form-control" id="genre" name="genre" placeholder="Genre">
                </div>
            </div>

            <!--  Rating  -->
            <div class="form-group row">
                <label for="rating" class="col-sm-2 col-form-label">Rating</label>
                <div class="col-sm-8">
                    <input type="range" class="form-control custom-range" id="rating" name="rating" min="0" max="100" step="5" placeholder="Rating">
                </div>
            </div>

            <!--  Best song  -->
            <div class="form-group row">
                <label for="bestSong" class="col-sm-2 col-form-label">Best song</label>
                <div class="col-sm-8">
                    <input type="text" class="form-control" id="bestSong" name="bestSong" placeholder="Best song">
                </div>
            </div>

            <div class="form-group row">
                <div class="col-sm-10">
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
