<#import "common/bootstrap.ftl" as common>
<@common.page>

<br>
<h1>Add article</h1>
<br>

<div class="panel-body">
    <form method="post" action="/add_article">
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

            <!--  Title  -->
            <div class="form-group row">
                <label for="title" class="col-sm-1 col-form-label">Title</label>
                <div class="col-sm-7">
                    <input type="text" class="form-control" id="title" name="title" placeholder="Title" required>
                </div>
            </div>

            <!--  Img  -->
            <div class="form-group row">
                <label for="img" class="col-sm-1 col-form-label">Image</label>
                <div class="col-sm-7">
                    <input type="text" class="form-control" id="img" name="img" placeholder="Image" required>
                </div>
            </div>

            <!--  Href  -->
            <div class="form-group row">
                <label for="href" class="col-sm-1 col-form-label">href</label>
                <div class="col-sm-7">
                    <input type="text" class="form-control" id="href" name="href" placeholder="href">
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
