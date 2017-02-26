
<body class="book toc2 toc-left">
<div id="header">
<h1>RESTful Events Getting Started Guide</h1>
<div class="details">
<span id="author" class="author">Kouhei Toki</span><br>
</div>
<div id="toc" class="toc2">
<div id="toctitle">Table of Contents</div>
<ul class="sectlevel1">
<li><a href="#_introduction">Introduction</a></li>
<li><a href="#_getting_started">Getting started</a>
<ul class="sectlevel1">
<li><a href="#_running_the_service">Running the service</a></li>
<li><a href="#_api_usage">Api Usage</a></li>
</ul>
</li>
</ul>
</div>
</div>
<div id="content">
<div class="sect1">
<h2 id="_introduction"><a class="link" href="#_introduction">Introduction</a></h2>
<div class="sectionbody">
<div class="paragraph">
<p>RESTful Events is a RESTful web service for creating and showing events. It uses hypermedia
to describe the relationships between resources and to allow navigation between them.</p>
</div>
</div>
</div>
<div class="sect1">
<h2 id="_getting_started"><a class="link" href="#_getting_started">Getting started</a></h2>
<div class="sectionbody">
<div class="sect1">
<h2 id="_running_the_service"><a class="link" href="#_running_the_service">Running the service</a></h2>
<div class="sectionbody">
<div class="paragraph">
<p>RESTful Events is written using <a href="http://projects.spring.io/spring-boot">Spring Boot</a> which
makes it easy to get it up and running so that you can start exploring the REST API.</p>
</div>
<div class="paragraph">
<p>The first step is to clone the Git repository:</p>
</div>
<div class="listingblock">
<div class="content">
<pre class="highlight"><code class="language-bash" data-lang="bash">$ git clone https://github.com/jsug-projects/flaky-events</code></pre>
</div>
</div>
<div class="paragraph">
<p>Once the clone is complete, you&#8217;re ready to get the service up and running:</p>
</div>
<div class="listingblock">
<div class="content">
<pre class="highlight"><code class="language-bash" data-lang="bash">$ cd flaky-events
$ ./mvnw clean package
$ java -jar -Dspring.profiles.active=development target/*.jar</code></pre>
</div>
</div>
<div class="admonitionblock tip">
<table>
<tr>
<td class="icon">
<i class="fa icon-tip" title="Tip"></i>
</td>
<td class="content">
If you omit "development" profile, authorization needed as resource server.
</td>
</tr>
</table>
</div>
<div class="paragraph">
<p>You can check that the service is up and running by executing a simple request using
cURL:</p>
</div>
<div class="listingblock">
<div class="content">
<pre class="highlight"><code class="language-bash" data-lang="bash">$ curl 'http://localhost:8083/' -i -H 'Accept: application/hal+json'</code></pre>
</div>
</div>
<div class="paragraph">
<p>This request should yield the following response in the
<a href="http://stateless.co/hal_specification.html">Hypertext Application Language (HAL)</a> format:</p>
</div>
<div class="listingblock">
<div class="content">
<pre class="highlight nowrap"><code class="language-http" data-lang="http">HTTP/1.1 200 OK
Content-Type: application/hal+json;charset=UTF-8
Content-Length: 664

{
  "_links" : {
    "sessions" : {
      "href" : "http://localhost:8083/sessions{?page,size,sort}",
      "templated" : true
    },
    "events" : {
      "href" : "http://localhost:8083/events{?page,size,sort}",
      "templated" : true
    },
    "speakers" : {
      "href" : "http://localhost:8083/speakers{?page,size,sort}",
      "templated" : true
    },
    "attendees" : {
      "href" : "http://localhost:8083/attendees{?page,size,sort}",
      "templated" : true
    },
    "attendances" : {
      "href" : "http://localhost:8083/attendances"
    },
    "profile" : {
      "href" : "http://localhost:8083/profile"
    }
  }
}</code></pre>
</div>
</div>
<div class="paragraph">
<p>Note the <code>_links</code> in the JSON response. They are key to navigating the API.</p>
</div>
</div>
</div>
<div class="sect1">
<h2 id="_api_usage"><a class="link" href="#_api_usage">Api Usage</a></h2>
<div class="sectionbody">
<div class="paragraph">
<p>You can see api reference at <a href="http://localhost:8083/docs/api-guide.html" class="bare">http://localhost:8083/docs/api-guide.html</a> .</p>
</div>
</div>
</div>
</div>
</div>
</div>
<div id="footer">
<div id="footer-text">
Last updated 2017-02-26 01:40:34 JST
</div>
</div>
</body>
