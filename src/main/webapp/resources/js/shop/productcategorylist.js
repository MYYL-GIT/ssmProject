$(function(){
    getlist();
    function getlist() {
        $.ajax({
            url:"/productcategoryadmin/getproductcategorylist",
            type:"get",
            dataType:"json",
            success:function (data) {
                if (data.success){
                    handlerList(data.productCategoryList);
                }

            }
        });
    }
    function handlerList(data) {
        var  html = '';
        data.map(function (item,index) {
            html += '<div class = "row row-product now"><div class="col-40">'
                +item.productCategoryName+'</div><div class="col-40">'
                +item.priority
                +'</div><div class="col-10">'
                +goproductcategory(item.productCategoryId)+'</div></div>';
        });
        $('.product-wrap').html(html);
    }

    function goproductcategory(id) {
        return '<a href="#" data-id='+id+' class="button delete">删除</a>';
    }

    $('#new')
        .click(
            function () {
                var tempHtml = '<div class = "row row-product temp">'
                    +'<div class="col-40"><input class="product-input category" type="text" placeholder="分类名"></div> '
                    +'<div class="col-40"><input class="product-input priority" type="number" placeholder="优先级"></div> '
                    +'<div class="col-10"><a href="#" class="button delete">删除</a></div>'
                    +'</div>';
                $('.product-wrap').append(tempHtml);
            }
        );

    $('#submit')
        .click(
            function () {
                var tempArr = $('.temp');
                var productCategoryList = [];
                tempArr.map(function (index,item) {
                    var tempObj = {};
                    tempObj.productCategoryName = $(item).find('.category').val();
                    tempObj.priority = $(item).find('.priority').val();
                    if (tempObj.productCategoryName && tempObj.priority) {
                        productCategoryList.push(tempObj);
                    }
                })
                $.ajax({
                    url:'/productcategoryadmin/addproductcategorys',
                    type:'post',
                    data:JSON.stringify(productCategoryList),
                    contentType:'application/json',
                    success:function (data) {
                        if (data.success) {
                            $.toast('提交成功！');
                            getlist();
                        }else{
                            $.toast('提交失败！');
                        }

                    }
                })

            });

    $('.product-wrap').on('click','.row-product.temp .delete',
        function (e) {
            console.log($(this).parent().parent());
            $(this).parent().parent().remove();
        });

    $('.product-wrap').on('click','.row-product.now .delete',
        function (e) {
            var target = e.currentTarget;
            $.confirm('确定么？',function () {
                $.ajax({
                    url:'/productcategoryadmin/removeproductcategory',
                    type:'POST',
                    data:{
                        productCategoryId:target.dataset.id
                    },
                    dataType:'json',
                    success:function (data) {
                        if (data.success){
                            $.toast('删除成功！');
                            getlist();
                        }else{
                            $.toast('删除失败！');
                        }

                    }
                });

            })
        });

});