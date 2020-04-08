banner();

$(function(){

    $(".swiper-wrapper a").hover(function () {
        $(this).children("img").hide() && $(this).children(".brand_hover").show()
    }, function () {
        $(this).children("img").show() && $(this).children(".brand_hover").hide()
    });


});
