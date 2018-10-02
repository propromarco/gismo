var $item = $('.item');
var $wHeight = $(window).height();
$item.eq(0).addClass('active');
$item.height($wHeight);
$item.addClass('full-screen');

$(window).on('resize', function () {
    $wHeight = $(window).height();
    $item.height($wHeight);
});

$('.carousel').carousel({
    interval: 15000,
    pause: "false"
});

setInterval(function () {
    window.location.reload();
}, 1800000);
setInterval(function () {
    var d = new Date();
    $('h1.date').each(function () {
            var y = d.getFullYear();
            var m = d.getMonth();
            m = m + 1;
            if (m < 10) {
                m = '0' + m;
            }
            var dd = d.getDate();
            var t = dd + '.' + m + '.' + y;
            $(this).text(t);
        }
    );
    $('h1.time').each(function () {
            var h = d.getHours();
            var m = d.getMinutes();
            if (m < 10) {
                m = '0' + m;
            }
            var t = h + ':' + m;
            $(this).text(t);
        }
    );
}, 10000);
setInterval(function () {
    $.getJSON('/news.json', function (data) {
        var newsItems = $('div.News > div');
        var articles = data.articles;
        for (i = 0; i < 4; i++) {
            var newsItem = newsItems.get(i);
            var article = articles[i];
            var img = $(newsItem).find("img");
            var h3 = $(newsItem).find("h3");
            var p = $(newsItem).find("p");
            // debugger;
            $(p).html(article.description);
            $(h3).html(article.title);
            $(img).attr("src",article.urlToImage);
        }
    });
}, 60000);
