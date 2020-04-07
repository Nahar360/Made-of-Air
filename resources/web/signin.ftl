<#import "common/bootstrap.ftl" as common>
<@common.page>

<br>

<div class="container h-100">
    <div class="row h-100 justify-content-center align-items-center">
        <div class="card bg-light mb-3 text-center" style="width: 25rem;">
            <div class="card-body">
                <h2 class="card-title">Sign in</h2>

                <#if error??>
                    <p style="color:red;">${error}</p>
                </#if>

                <form action="/signin" method="POST">
                    <div class="form-group">
                        <label for="userId">Username</label>
                        <input type="text" class="form-control" name="userId" id="userId" placeholder="Must contain at least 5 characters">
                    </div>
                    <div class="form-group">
                        <label for="password">Password</label>
                        <input type="password" class="form-control" name="password" id="password" placeholder="Must contain at least 6 characters">
                    </div>

                    <br>

                    <button type="submit" class="btn btn-success">Sign in</button>
                </form>
            </div>
        </div>
    </div>
</div>

</@common.page>
