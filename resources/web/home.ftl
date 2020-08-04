<#import "common/bootstrap.ftl" as common>
<@common.page>

<br>
<h2>Top</h2>
<br>

<script>
var apiUrl = "http://ws.audioscrobbler.com/2.0/";
var methodArtists = "user.getTopArtists";
var methodAlbums = "user.getTopAlbums";
var methodTracks = "user.getTopTracks";
var username = "Nahar360";
var apiKey = "9ec89d26273968e5f07f6f6d69d09552";
var limitNumber = "10";
var formatJsonAndCallback = "&format=json&callback=?";

$(document).ready(function() {
    var artistsApiCall = apiUrl + "?method=" + methodArtists + "&user=" + username + "&api_key=" + apiKey + "&limit=" + limitNumber + formatJsonAndCallback;
    $.getJSON(artistsApiCall, function(json) {
        var artists = "<ol>";
        $.each(json.topartists.artist, function(i, item) {
            artists += "<li> <p><a href=" + item.url + " target='_blank'>" + item.name + "</a> [" + item.playcount + "]</p> </li>";
        });
        artists += "</ol>";
        $('#topArtists').append(artists);
    });

    var albumsApiCall = apiUrl + "?method=" + methodAlbums + "&user=" + username + "&api_key=" + apiKey + "&limit=" + limitNumber + formatJsonAndCallback;
    $.getJSON(albumsApiCall, function(json) {
        var albums = "<ol>";
        $.each(json.topalbums.album, function(i, item) {
            var image = JSON.stringify(item.image[1]);
            var imageUrl = image.substring(image.indexOf("text") + 7).slice(0, -2);
            albums += "<li> <p> <img src=" + imageUrl + "> <a href=" + item.url + " target='_blank'>" + item.artist.name + " - " + item.name + "</a> </p> </li>";
        });
        albums += "</ol>";
        $('#topAlbums').append(albums);
    });

    var tracksApiCall = apiUrl + "?method=" + methodTracks + "&user=" + username + "&api_key=" + apiKey + "&limit=" + limitNumber + formatJsonAndCallback;
    $.getJSON(tracksApiCall, function(json) {
        var tracks = "<ol>";
        $.each(json.toptracks.track, function(i, item) {
            tracks += "<li> <p><a href=" + item.url + " target='_blank'>" + item.artist.name + " - " + item.name + "</a></p> </li>";
        });
        tracks += "</ol>";
        $('#topTracks').append(tracks);
    });
});
</script>

<div class="row">
    <div class="col-sm-4" id="topArtists">
        <h4>Artists</h4>
    </div>
    <div class="col-sm-4" id="topAlbums">
        <h4>Albums</h4>
    </div>
    <div class="col-sm-4" id="topTracks">
        <h4>Tracks</h4>
    </div>
</div>

<hr>

<br>
<h2>News</h2>
<br>

<div class="row">
    <div class="col-md-6 text-center">
        <#list postsMusic as post>
            <div class="card border-dark mb-3 text-center">
                <div class="card-header bg-dark border-dark text-white">
                    <#if post.type == "ADD">
                        <b>${post.author}</b> has added a new album
                    <#elseif post.type == "MODIFY">
                        <b>${post.author}</b> has updated an album
                    </#if>
                </div>
                <div class="card-body bg-secondary text-white">
                    <div class="row">
                        <div class="col">
                            <h6 class="card-title">${post.year[5..]} ${post.month}</h6>
                        </div>
                    </div>
                    <div class="row">
                        <div class="col">
                            <h5 class="card-title">${post.band} - ${post.album}</h5>
                        </div>
                    </div>
                    <div class="row">
                        <div class="col">
                            <p class="card-text">${post.genre}</p>
                        </div>
                        <div class="col">
                            <#if post.bestSong != "">
                                <p class="card-text">${post.bestSong}</p>
                            <#else>
                                <p class="card-text">No song highlighted</p>
                            </#if>
                        </div>
                        <div class="col">
                            <#if post.rating != "">
                                <p class="card-text">${post.rating}</p>
                            <#else>
                                <p class="card-text">Not rated</p>
                            </#if>
                        </div>
                    </div>
                </div>
                <div class="card-footer border-dark text-white">
                    <small class="text-muted">${post.date[0..9]} / ${post.date[11..18]}</small>
                    <#if (user??)>
                        <form method="post" action="/">
                            <input type="hidden" name="postId" value="${post.id}">
                            <input type="hidden" name="action" value="delete">
                            <button type="submit" class="btn btn-default btn-circle" title="Delete">
                                <i class="material-icons">delete</i>
                            </button>
                        </form>
                    </#if>
                </div>
            </div>
        </#list>
    </div>
    <div class="col-md-6">
        <#list postsPitchfork as post>
            <div class="card border-dark mb-3 text-center">
                <div class="card-header bg-dark border-dark text-white">
                    <#if post.type == "ADD">
                        <b>${post.author}</b> has added a Pitchfork Best New Album
                    </#if>
                </div>
                <div class="card-body bg-secondary text-white">
                    <div class="row">
                        <div class="col">
                            <h6 class="card-title">${post.year[5..]} ${post.month} ${post.day}</h6>
                        </div>
                    </div>
                    <div class="row">
                        <div class="col">
                            <h5 class="card-title">${post.band} - ${post.album}</h5>
                        </div>
                    </div>
                    <div class="row">
                        <div class="col">
                            <#if post.rating != "">
                                <p class="card-text">${post.rating}</p>
                            <#else>
                                <p class="card-text">Not rated</p>
                            </#if>
                        </div>
                    </div>
                </div>
                <div class="card-footer border-dark text-white">
                    <small class="text-muted">${post.date[0..9]} / ${post.date[11..18]}</small>
                </div>
            </div>
        </#list>
    </div>
</div>

<!--  Clear  -->
<#if (user??) && ((postsMusic?size > 0) || (postsPitchfork?size > 0))>
    <div class="form-group row">
        <form method="post" action="/" onsubmit="return confirm('Are you sure you want to clear all the posts?');">
            <div class="col-sm-10">
                <input type="hidden" name="action" value="clear">
                <button type="submit" class="btn btn-danger d-flex justify-content-center align-content-between">
                    <i class="material-icons">clear</i>
                    Clear
                </button>
            </div>
        </form>
    </div>
</#if>

</@common.page>
