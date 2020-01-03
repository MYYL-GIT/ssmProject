$(function(){
    getlist();
    function getlist() {
        $.ajax({
            url:"/productadmin/getproductlist",
            type:"get",
            dataType:"json",
            success:function (data) {
                if (data.success){
                    handlerList(data.productList);
                }
            }
        });
    }

    function handlerList(data) {
        var  html = '';
        data.map(function (item,index) {
            var textOp = '下架';
            var contraryStatus = 0;
            if(item.enableStatus == 0){
                textOp = '上架';
                contraryStatus = 1;
            }else{
                contraryStatus = 0;
            }
            html += '<div class = "row row-product"><div class="col-40">'
                +item.productName+'</div><div class="col-40">' +item.priority +'</div>'
                +'<div class="col-5">'
                +'<a href="/productadmin/productoperation?productId='+item.productId+'">编辑</a>'
                +'</div><div class="col-5">'
                +'<a href="#" class="status" data-id='+item.productId+' data-status='+contraryStatus+'>'+textOp+'</a>'
                +'</div><div class="col-5">'
                +'<a href="/productadmin/productoperation?productId='+item.productId+'">预览</a></div></div>';
        });
        $('.product-wrap').html(html);
    }


    $('.product-wrap').on('click','.row-product .status',
        function (e) {
            var target = e.currentTarget;
            $.confirm('确定修改吗?',function () {
                var product = {};
                var formData = new FormData();
                product.productId = target.dataset.id;
                product.enableStatus = target.dataset.status;
                formData.append('statusChange',true);
                formData.append('productStr',JSON.stringify(product));
                $.ajax({
                    url:'/productadmin/modifyproduct',
                    type:'POST',
                    data:formData,
                    contentType:false,
                    processData:false,
                    cache:false,
                    success:function (data) {
                        if (data.success){
                            $.toast('提交成功！');
                            getlist();
                        } else {
                            $.toast('提交失败！');
                        }
                    }
                });
            })
        });
});