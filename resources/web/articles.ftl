<#import "common/bootstrap.ftl" as common>
<@common.page>

<br>
<h1>Articles</h1>
<br>

<#list articles as article>
    ${article.id}<br>
    ${article.year}<br>
    ${article.month}<br>
    ${article.title}<br>
    ${article.img}.png<br>
    ${article.href}.ftl<br>
</#list>

</@common.page>
