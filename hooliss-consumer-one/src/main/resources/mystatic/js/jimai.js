$(function(){
    $('#keyword').autocomplete("htindexSearch/suggestKeyword/search?callback=?", {
        width: 268,
        height:500,
        max: 10,//下拉列表显示的最大数据条数
        highlight: false,
        //multiple: true,多选
        //multipleSeparator: " ",
        scroll: true,
        scrollHeight: 300,
        dataType: "jsonp",
        jsonp: "callback",
        parse:function(data){//对返回的JSON串处理
            var rows=new Array();
            for(var i=0;i<data.length;i++){
                rows[rows.length]={
                    data:data[i],
                    value:data[i].content,
                    result:data[i].content
                };
            }
            return rows;
        },
        formatItem: function(row, i, max) {
            return  row.content ;
        }
    });
});
var enterSearchTop = function (){
    var event = arguments.callee.caller.arguments[0]||window.event;//消除浏览器差异
    if (event.keyCode == 13){
        searchForm();
    }
    /******slider*****/
    $.fn.lazyhover = function(fuc_on, de_on, de_out) {
        var self = $(this);
        var flag = 1;
        var h;
        var handle = function(elm){
            clearTimeout(h);
            if(!flag) self.removeData('timer');
            return flag ? fuc_on.apply(elm) : null;
        };
        var time_on  = de_on  || 0;
        var time_out = de_out || 500;
        var timer = function(elm){
            h && clearTimeout(h);
            h = setTimeout(function() { handle(elm);  }, flag ? time_on : time_out);
            self.data('timer', h);
        }
        self.hover(
            function(){
                flag = 1 ;
                timer(this);
            },
            function(){
                flag = 0 ;
                timer(this);
            }
        );
    }
    function HtmlSlidePlayer(config){
        this.n =0;
        this.j =0;
        this.first_show = 1;
        var _this = this;
        var t;
        var defaults = {fontsize:12,right:10,bottom:15,time:5000,autosize:0,slidearrow:false};
        this.config = $.extend(defaults,config);
        this.count = $("#mtsBanner" + " li").length;

        this.factory = function(){
            //元素定位
            $("#mtsBanner").css({position:"relative",zIndex:"0",overflow:"hidden", height: $("#mtsBanner" + " ul").height() + "px" });
            $("#mtsBanner").prepend("<div class='slide_control'></div>");

            $("#mtsBanner" + " ul").css({position:"relative",zIndex:"0",margin:"0",padding:"0",overflow:"hidden", width:"100%" })
            $("#mtsBanner" + " li").css({position:"absolute",top:"0",left:"0",width:"100%",height: "100%" ,overflow:"hidden"}).hide().each(function(i){
                $("#mtsBanner" + " .slide_control").append("<a>"+(i+1)+"</a>");
            });

            this.resetclass("#mtsBanner" + " .slide_control a",0);

            //第一张图片lazyload
            var img =$("#mtsBanner" + " li").first().find('img');
            if ( img.length > 0 && !!img.attr('lazyload') ){
                $.each(img,function(i){
                    $(this).attr("src",$(this).attr("lazyload")).removeAttr("lazyload");
                });
            }


            this.slide();

            t = setInterval(this.autoplay,this.config.time);
            //force the first slide to show
            $("#mtsBanner" + " .slide_control a").eq(0).triggerHandler('mouseover');

            var slideContorlWidth = $(".slide_control").width();
            $(".slide_control").css({marginLeft : -slideContorlWidth/2})
        };
        //加一个上一页下一页的按钮



        var prev_button = $('<em class="slidearrow slidearrow_l"><</em>');
        var next_button = $('<em class="slidearrow slidearrow_r">></em>');
        $("#mtsBanner").append(prev_button).append(next_button);
        next_button.bind('click',function(){
            if(_this.n == _this.count-1){
                $("#mtsBanner" + " .slide_control a").eq(0).triggerHandler('mouseover');
            }else{
                $("#mtsBanner" + " .slide_control a").eq(_this.n+1).triggerHandler('mouseover');
            }
        });
        prev_button.bind('click',function(){
            if(_this.n == 0){

                $("#mtsBanner" + " .slide_control a").eq(_this.count-1).triggerHandler('mouseover');
            }else{
                $("#mtsBanner" + " .slide_control a").eq(_this.n-1).triggerHandler('mouseover');
            }

        });


        //图片渐影
        this.slide = function(){

            $("#mtsBanner" + " .slide_control a").lazyhover(function(){
                _this.j = $(this).text() - 1;
                _this.n = _this.j;
                if (_this.j >= _this.count){return;}

                //防止闪一下
                if(_this.first_show) {
                    _this.first_show = 0;
                    $("#mtsBanner" + " li:eq(" + _this.j + ")").show().siblings("li").hide();
                }else {
                    var li =$("#mtsBanner" + " li:eq(" + _this.j + ")");
                    var next_li ;
                    if ( _this.count >= _this.j + 1 ){
                        next_li = $("#mtsBanner" + " li:eq(" + (_this.j+1) + ")");
                    }

                    li.fadeIn("200").siblings("li").fadeOut("200");
                    //添加图片延迟加载
                    var img=$("img[lazyload]",li);
                    $.each(img,function(i){
                        $(this).attr("src",$(this).attr("lazyload")).removeAttr("lazyload");
                    });
                    //背景图延迟加载
                    var bg_src = li.attr("lazyload");
                    if(bg_src!=undefined){
                        li.css('background-image','url('+bg_src+')').removeAttr("lazyload");

                        if ( next_li != undefined && next_li.length >= 1 ){
                            var next_bg_src = next_li.attr("lazyload");
                            if ( next_bg_src != undefined ){
                                next_li.css('background-image','url('+next_bg_src+')').removeAttr("lazyload");
                            }

                        }

                    }

                };
                _this.resetclass("#mtsBanner" + " .slide_control a",_this.j);
            },200,500);
        };

        //滑过停止
        $("#mtsBanner").mouseover(function(){
            clearInterval(t);
        });

        $("#mtsBanner").mouseout(function(){
            t = setInterval(_this.autoplay,_this.config.time)
        });


        //自动播放
        this.autoplay = function(){
            _this.n = _this.n >= (_this.count - 1) ? 0 : ++_this.n;
            $("#mtsBanner" + " .slide_control a").eq(_this.n).triggerHandler('mouseover');
        }
        //翻页函数
        this.resetclass =function(obj,i){
            $(obj).removeClass('mall_dot_hover').addClass('mall_dot');
            $(obj).eq(i).addClass('mall_dot_hover');
            if($.browser.msie && $.browser.version == 6.0){
                $('.img_slider_trigger').css("zoom","1");
            }
        };
        this.factory();
    }
    HtmlSlidePlayer({autosize:0,fontsize:12,time:5000});

    /******随动栏*****/
    var ofixed = $("#fixed");
    var oHeight= ofixed.offset().top;
    function fixFun() {
        var st = $(document).scrollTop(), winh = $(window).height();
        var winW = $(window).width();
        var maxHeight = $(".b_details").height() + $("#header").height() - $(".fixed").height();
        ofixed.css("position", "absolute");
        ofixed.css("left", 0);
        ofixed.css("top",0);
        if(st > oHeight){
            if (!window.XMLHttpRequest) {
                ofixed.css("position", "absolute");
                ofixed.css("left",0 );
                ofixed.css("top",st-440);
            }
            else{
                ofixed.css("position", "fixed");
                ofixed.css("left",0 );
                ofixed.css("top",0);
            }
        }
        if(st < $(".content_box").offset().top ){
            $(".popbox").fadeOut();
            $(".mask").hide();
            $(".nav li").removeClass("active");
        }
    };
    $(window).bind("scroll", fixFun);
    $(window).bind("resize", fixFun);

    /******弹出层*****/
    $(".popbox").each(function(){
        var popBoxheight = $(this).height();
        $(this).css({marginTop : -popBoxheight/2})
    })
    $(".mask").width($("#floor").width());
    $(".mask").height($("#floor").height());
    var t = $("#floor").offset().top;

    $("a.aStyle").each(function(i){
        $("a.aStyle").eq(i).attr("indexs",i+1)
        var is = i+1
        $("a.aStyle").click(function(){
            $(".master li").removeClass("active")
            $(this).parent().addClass("active")
            $("#layer05").hide();
            $("#layer0" + is ).hide();
            var I = $(this).attr("indexs")
            if($.browser.msie){
                var url = "#floor";
                var id = url.split("#")[1];
                if(id){
                    $(window).scrollTop(t);
                    $("#layer0" + I ).show();
                    $(".mask").show();
                }
            }else{
                $("html,body").stop().animate({scrollTop: t},500,function(){
                    $("#layer0" + I ).show();
                    $(".mask").show();
                });
            }
        })
    });

    $(".close").click(function(){
        $(this).parent().fadeOut("fast");
        $(".mask").hide();
    });

    $(".nav li").click(function(){
        $(".nav li").removeClass("active");
        $(this).addClass("active");
    })

    $(".nav_btn02").click(function(){
        $("#layer01,#layer02,#layer03,#layer04").hide();
        $(this).addClass("nav_btn_active")
        if($.browser.msie){
            var url = "#floor";
            var id = url.split("#")[1];
            if(id){
                $(window).scrollTop(t);
                $("#layer05").show();
                $(".mask").show();
            }
        }else{
            $("html,body").stop().animate({scrollTop: t},500,function(){
                $("#layer05").show();
                $(".mask").show();
            });
        }
    })

    /*******************/
    $(".master li").hover(function(){
        $(".master li").removeClass("active")
        $(this).addClass("active");
    })

    $(".aStyle").hover(function(){
        $(this).addClass("hover");
    },function(){
        $(this).removeClass("hover")
    });

    $(".nav_btn02,.nav_btn01").hover(function(){
        $(this).addClass("nav_btn_hover");
    },function(){
        $(this).removeClass("nav_btn_hover");
    })
};
