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
          <#if user??>
            <a class="dropdown-item" href="/add_album">Add album</a>
          </#if>
          <#list years?reverse as year>
            <a class="dropdown-item" href="/music/year/${year}">${year}</a>
          </#list>
        </div>
      </li>
      <li class="nav-item dropdown">
        <a class="nav-link dropdown-toggle" data-toggle="dropdown" href="#" role="button" aria-haspopup="true" aria-expanded="false" style="color: #D3D3D3">Pitchfork</a>
        <div class="dropdown-menu">
          <#if user??>
            <a class="dropdown-item" href="/add_album_pitchfork">Add Pitchfork</a>
          </#if>
          <#list years?reverse as year>
            <a class="dropdown-item" href="/pitchfork/year/${year}">${year}</a>
          </#list>
        </div>
      </li>
      <li class="nav-item dropdown">
        <a class="nav-link dropdown-toggle" data-toggle="dropdown" href="#" role="button" aria-haspopup="true" aria-expanded="false" style="color: #D3D3D3">Stats</a>
        <div class="dropdown-menu">
          <a class="dropdown-item" href="/stats">Music</a>
          <a class="dropdown-item" href="/stats_pitchfork">Pitchfork</a>
        </div>
      </li>
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
