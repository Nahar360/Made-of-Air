<#import "common/bootstrap.ftl" as common>
<@common.page>

<div class="panel-body">
    <br>
    <h4>Users</h4>
    <#list users as user>
        <ul>
            <li>
                <form method="post" action="/users">
                    ${user.userId}
                    <input type="hidden" name="userId" value="${user.userId}">
                    <input type="hidden" name="action" value="delete">
                    <button type="submit" class="btn btn-default btn-circle" title="Delete"><i class="material-icons">delete</i></button>
                </form>
            </li>
        </ul>
    </#list>

    <h4>Create new user</h4>

    <#if error??>
        <p style="color:red;">${error}</p>
    </#if>

    <form method="post" action="/users">
        <!-- User Id -->
        <div class="form-group row">
            <label for="userId" class="col-sm-2 col-form-label">Username</label>
            <div class="col-sm-4">
                <input type="text" class="form-control" id="userId" name="userId" placeholder="Must contain at least 5 characters" required>
            </div>
        </div>

        <!-- Password -->
        <div class="form-group row">
            <label for="password" class="col-sm-2 col-form-label">Password</label>
            <div class="col-sm-4">
                <input type="text" class="form-control" id="password" name="password" placeholder="Must contain at least 6 characters" required>
            </div>
        </div>

        <div class="form-group row">
            <div class="col-sm-10">
                <input type="hidden" name="action" value="add">
                <button type="submit" class="btn btn-success d-flex justify-content-center align-content-between">
                    <i class="material-icons">add</i>
                    Create
                </button>
            </div>
        </div>
    </form>
</div>

</@common.page>
