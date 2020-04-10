<nav class="navbar navbar-expand-lg navbar-light" style="background-color: #808080;">
  <a class="navbar-brand mb-0 h1" href="/" style="color: #FFFFFF">♫ Made of Air ♫</a>
  <button class="navbar-toggler" type="button" data-toggle="collapse" data-target="#navbarSupportedContent" aria-controls="navbarSupportedContent" aria-expanded="false">
    <span class="navbar-toggler-icon"></span>
  </button>

  <div class="collapse navbar-collapse" id="navbarSupportedContent">
    <ul class="navbar-nav mr-auto">
      <li class="nav-item dropdown">
        <a class="nav-link dropdown-toggle" data-toggle="dropdown" href="#" role="button" aria-haspopup="true" aria-expanded="false" style="color: #D3D3D3">Music</a>
        <div class="dropdown-menu">
          <a class="dropdown-item" href="/music/year/2016">2016</a>
          <a class="dropdown-item" href="/music/year/2017">2017</a>
          <a class="dropdown-item" href="/music/year/2018">2018</a>
          <a class="dropdown-item" href="/music/year/2019">2019</a>
          <a class="dropdown-item" href="/music/year/2020">2020</a>
        </div>
      </li>
      <#if user??>
        <li class="nav-item">
          <a class="nav-link" href="/add_album" style="color: #D3D3D3">Add album</a>
        </li>
      </#if>
      <li class="nav-item">
        <a class="nav-link" href="/about" style="color: #D3D3D3">About</a>
      </li>
    </ul>

    <ul class="nav navbar-nav navbar-right">
      <#if user??>
        <span class="navbar-text" style="color: black;">
          Welcome ${user.userId}!
        </span>

        <li>
          <a class="nav-link" href="/users">Users</a>
        </li>
        <li>
          <a class="nav-link" href="/signout">Sign out</a>
        </li>
      <#else>
        <li>
          <a class="nav-link" href="/signin">Sign in</a>
        </li>
      </#if>
    </ul>
  </div>
</nav>
