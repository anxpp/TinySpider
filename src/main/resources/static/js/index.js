/**
 * index
 * Created by anxpp.com on 2017/3/25.
 */
var mcnbetaPage = 1;
/**
 * 入口
 */
$(function () {
    var canvas = document.getElementById('canvas_test');

    function isIE9() {
        var userAgent = navigator.userAgent;
        var isOpera = userAgent.indexOf("Opera") > -1;
        var isIE = userAgent.indexOf("compatible") > -1 && userAgent.indexOf("MSIE") > -1 && !isOpera;
        var reIE = new RegExp("MSIE (\\d+\\.\\d+);");
        reIE.test(userAgent);
        var fIEVersion = parseFloat(RegExp["$1"]);
        return isIE && fIEVersion == 9;
    }

    if (!canvas || !canvas.getContext || isIE9()) {
        $('iframe').hide();
        $('div.header').addClass('intro-header');
        $('#div_header_main').show();
    }
    initCsdnweekly();
    getMcnbetaArticles();
    getMovies();
    //TAB点击事件
    $('#ul_tab_menu li').click(function () {
        if ($(this).hasClass('active'))
            return;
        $('#ul_tab_menu li').removeClass('active');
        $(this).addClass('active');
        $("div.main_content").slideUp();
        $($(this).attr("for")).slideDown();
    });
    $('#ul_tab_menu li').eq(0).click();
});
/**
 * 初始化CSDN菜单
 */
function initCsdnweekly() {
    $('#menu_csdnweekly').children().slideUp();
    for (var i = 54; i >= 1; i--) {
        $('#menu_csdnweekly').append('<button onclick="getCsdnweeklyArticles(this,' + i + ')" style="display: none;width:100%;text-align: center;padding: 5% 0" type="button" class="list-group-item">第' + i + '周</button>');
    }
    $('#menu_csdnweekly').children().slideDown();
    $('#menu_csdnweekly button').eq(0).click();
}
/**
 * 获取CSDN文章
 * @param e
 * @param i
 */
function getCsdnweeklyArticles(e, i) {
    $('#content_csdnweekly').slideUp('fast');
    $('#content_csdnweekly').showLoading();
    $('#menu_csdnweekly button').css("color", "#888");
    $(e).css("color", "#111");
    $.ajax({
        url: '/csdnweekly/article/get/stage/' + i,
        type: "get",
        data: {id: '0'},
        dataType: "json",
        success: function (articles) {
            $('#content_csdnweekly').empty();
            var contents = [];
            for (var i in articles) {
                contents.push('<div class="row">');
                contents.push('<div class="col-xs-2 col-sm-2 col-md-2 col-lg-2" style="padding:2%;">');
                contents.push('     <a target="_blank" href="' + articles[i].type + '"><img style="width: 100%;max-width: 60px; height: auto; overflow: hidden;" src="' + articles[i].img + '" /></a>');
                contents.push('</div>');
                contents.push(' <div class="col-xs-10 col-sm-10 col-md-10 col-lg-10">');
                contents.push('     <div class="row">');
                contents.push('         <div style="padding: 2% 0" class="col-xs-12 col-sm-12 col-md-12 col-lg-12"><a target="_blank" href="' + articles[i].url + '">' + articles[i].name + '</a></div>');
                contents.push('         <div style="padding: 2% 0" class="col-xs-12 col-sm-12 col-md-12 col-lg-12 simple-span">点赞数量 ' + articles[i].views + ' -- 收藏数量' + articles[i].collections + '</div>');
                contents.push('     </div>');
                contents.push(' </div>');
                contents.push(' <div style="height: 2px; background: #eee;" class="col-xs-12 col-sm-12 col-md-12 col-lg-12"></div>');
                contents.push('</div>');
            }
            $('#content_csdnweekly').append(contents.join(''));
            $('#content_csdnweekly').slideDown();
            $('#content_csdnweekly').hideLoading();
        },
        error: function (XMLHttpRequest, textStatus, errorThrown) {
            $('#content_csdnweekly').append('<h1>获取失败</h1>');
        },
        complete: function (XMLHttpRequest, textStatus) {
            $('#content_csdnweekly').hideLoading();
        }
    });
}
/**
 * 获取cnbeta文章
 * @param e
 * @param i
 */
var pre = "http://m.cnbeta.com/touch#";
function getMcnbetaArticles() {
    $('#a_mcnbeta_next').fadeOut();
    $('#content_mcnbeta').showLoading();
    $.ajax({
        url: '/mcnbeta/article/get/page/' + mcnbetaPage,
        type: "get",
        dataType: "json",
        success: function (result) {
            var articles = result.result.list;
            var contents = [];
            for (var i in articles) {
                contents.push('<div class="row">');
                contents.push('<div class="col-xs-2 col-sm-2 col-md-2 col-lg-2" style="padding:2%;">');
                contents.push('     <a target="_blank" href="' + pre + articles[i].url + '"><img style="width: 100%;max-width: 60px; height: auto; overflow: hidden;" src="' + articles[i].thumb + '" /></a>');
                contents.push('</div>');
                contents.push(' <div class="col-xs-10 col-sm-10 col-md-10 col-lg-10">');
                contents.push('     <div class="row">');
                contents.push('         <div style="padding: 2% 0" class="col-xs-12 col-sm-12 col-md-12 col-lg-12"><a target="_blank" href="' + pre + articles[i].url + '">' + articles[i].title + '</a></div>');
                contents.push('         <div style="padding: 2% 0" class="col-xs-12 col-sm-12 col-md-12 col-lg-12 simple-span">类别：' + articles[i].label + ' -- 时间：' + articles[i].inputtime + '</div>');
                contents.push('     </div>');
                contents.push(' </div>');
                contents.push(' <div style="height: 2px; background: #eee;" class="col-xs-12 col-sm-12 col-md-12 col-lg-12"></div>');
                contents.push('</div>');
            }
            $('#content_mcnbeta').append(contents.join(''));
            $('#content_mcnbeta').hideLoading();
            mcnbetaPage++;
        },
        error: function (XMLHttpRequest, textStatus, errorThrown) {
            $('#content_mcnbeta').append('<h1>获取失败</h1>');
        },
        complete: function (XMLHttpRequest, textStatus) {
            $('#content_mcnbeta').hideLoading();
            $('#a_mcnbeta_next').fadeIn();
        }
    });
}
/**
 * 获取豆瓣影评
 * @param e
 * @param i
 */
function getMovies() {
    $('#content_search_result').showLoading();
    $('#content_search_result').slideUp();
    $.ajax({
        url: '/douban/movie/search/' + $('#input_movie_name').val(),
        type: "get",
        dataType: "json",
        success: function (result) {
            var movies = result;
            var contents = [];
            for (var i in movies) {
                contents.push('<div class="row">');
                contents.push('<div class="col-xs-2 col-sm-2 col-md-2 col-lg-2" style="padding:2%;">');
                contents.push('     <a target="_blank" href="' + movies[i].id + '"><img style="width: 100%;max-width: 60px; height: auto; overflow: hidden;" src="' + movies[i].img + '" /></a>');
                contents.push('</div>');
                contents.push(' <div class="col-xs-10 col-sm-10 col-md-10 col-lg-10">');
                contents.push('     <div class="row">');
                contents.push('         <div style="padding: 2% 0" class="col-xs-12 col-sm-12 col-md-12 col-lg-12"><a target="_blank" href="' + movies[i].id + '">' + movies[i].name + '</a></div>');
                contents.push('         <div style="padding: 2% 0" class="simple-span" style="width: 160px;">评分：' + movies[i].rating + ' -- 评论数：' + movies[i].comments + '</div>');
                contents.push('         <button type="button" onclick="getMovieComments(\'' + movies[i].id + '\')" class="btn btn-default">抓取有效影评</button>');
                contents.push('     </div>');
                contents.push(' </div>');
                contents.push(' <div style="height: 2px; background: #eee;" class="col-xs-12 col-sm-12 col-md-12 col-lg-12"></div>');
                contents.push('</div>');
            }
            $('#content_search_result').empty();
            $('#content_search_result').append(contents.join(''));
            $('#content_search_result').slideDown();
        },
        error: function (XMLHttpRequest, textStatus, errorThrown) {
            $('#content_search_result').append('<h1>获取失败</h1>');
        },
        complete: function (XMLHttpRequest, textStatus) {
            $('#content_search_result').hideLoading();
        }
    });
}
/**
 * 提交影评抓取任务
 * @param url 地址
 */
function getMovieComments(id) {
    $.get('/douban/movie/comments/' + id, function (result) {
        var state = parseInt(result.state);
        switch (state) {
            case 0:
                alert('任务提交成功！再次点击获取任务进度');
                break;
            case 1:
                alert('进行中，进度：' + result.current + '/' + result.total);
                break;
            case 2:
                alert('已完成');
                break;
            default:
                alert('状态未知！');
                break;
        }
    });
}