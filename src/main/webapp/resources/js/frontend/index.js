$(function () {
    //获取头条和一级类别列表
    var url = '/frontend/listmainpageinfo';
    $.getJSON(url,function (data) {
        if (data.success){
            //获取后台传递过来的头条列表
            var headLineList = data.headLineList;
            var swiperHtml = '';
            //遍历头条列表
            headLineList.map(function (item,index) {
                swiperHtml += ''+'<div class="swiper-slide img-wrap">'
                    +'<a href=" '+item.lineLink
                    +'"external><img class="banner-img" src="'+item.lineImg
                    +'"alt="'+item.lineName+'"></a></div>';
            });
            $('.swiper-wrapper').html(swiperHtml);
            //设定轮播图轮换时间为3秒
            $('.swiper-container').swiper({
                autoplay:3000,
                //用户对轮播图进行操作时，是否自动停止autoplay
                autoplayDisableOnInteraction:false
            });
            var shopCategoryList = data.shopCategoryList;
            var categoryHtml = '';
            shopCategoryList.map(function (item,index) {
                categoryHtml += ''+'<div class="col-50 shop-classify" data-category='
                    +item.shopCategoryId+'>'+'<div class="word">'
                    +'<p class="shop-title">'+item.shopCategoryName
                    +'</p><p class="shop-desc">'
                    +item.shopCategoryDesc+'</p></div><div class="shop-classify-img-warp">'
                    +'<img class="shop-img" src=" '+item.shopCategoryImg
                    +'"></div></div>';
            });
            $('.row').html(categoryHtml);
        }
    });

    //点击“我的”，显示侧栏
    $('#me').click(function () {
        $.openPanel('#panel-right-demo');
    });

    $('.row').on('click','.shop-classify',function (e) {
        var shopCategoryId = e.currentTarget.dataset.categroy;
        var newUrl = '/frontend/shoplist?parentId='+shopCategoryId;
        window.location.href=newUrl;
    });
})