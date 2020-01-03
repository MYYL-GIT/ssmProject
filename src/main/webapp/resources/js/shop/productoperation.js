$(function () {
    //从url里面获取productId
    var productId = getQueryString('productId');
    //获取商品信息
    var infoUrl = '/productadmin/getproductbyid?productId=' + productId;
    //获取商品类别列表
    var catgoryUrl = '/productcategoryadmin/getproductcategorylist';
    //更新商品信息
    var productPostUrl = '/productadmin/modifyproduct';
    var isEdit = false;
    if (productId){
        //若有productId则为编辑操作
        getInfo(productId);
    }else{
        getCategory();
        productPostUrl = '/productadmin/addproduct';
    }

    //获取商品信息
    function getInfo(id) {
        $.getJSON(
            infoUrl,
            function (data) {
                if (data.success){
                    var product = data.product;
                    $('#product-name').val(product.productName);
                    $('#product-desc').val(product.productDesc);
                    $('#product-priority').val(product.priority);
                    $('#product-normalPrice').val(product.normalPrice);
                    $('#product-promotionPrice').val(product.promotionPrice);
                    //获取原本的商品类别以及该店铺的所有商品类别列表
                    var optionHtml = '';
                    var optionArr = data.productCategoryList;
                    var optionSelected = product.productCategory.productCategoryId;
                    //生成商品类别下拉框，并设置默认选项
                    optionArr.map(function (item,index) {
                        var isSelect = optionSelected === item.productCategoryId ? 'selected':'';
                        optionHtml += '<option data-value="'
                            +item.productCategoryId
                            +'"'
                            +isSelect
                            +'>'
                            +item.productCategoryName
                            +'</option>'
                    });
                    $('#product-category').html(optionHtml);
                }
            });
    }

    //查询该店铺下的所有商品类别列表
    function getCategory() {
        $.getJSON(catgoryUrl,function (data) {
            if (data.success) {
                var productCategoryList = data.productCategoryList;
                var optionHtml = '';
                productCategoryList.map(function (item,index) {
                    optionHtml += '<option data-value="'
                        +item.productCategoryId+'">'
                        +item.productCategoryName+'</option>';
                });
                $('#product-category').html(optionHtml);
            }
        });
    }

    //针对商品详情图控件组，若该控件组的最后一个元素发生了变化
    //且控件总数未达到6个，则生成新的一个文件上传控件
    $('.detail-img-div').on('change','.detail-img:last-child',function () {
        if ($('.detail-img').length < 6){
            $('#detail-img').append('<input type="file" class="detail-img">');
        }
    });

    $('#submit').click(
        function () {
            var product = {};
            product.productName = $('#product-name').val();
            product.productDesc = $('#product-desc').val();
            product.priority = $('#product-priority').val();
            product.normalPrice = $('#product-normalPrice').val();
            product.promotionPrice = $('#product-promotionPrice').val();
            product.productCategory = {
                productCategoryId:$('#product-category').find('option').not(
                    function () {
                        return !this.selected;
                    }).data('value')
            };
            product.productId = productId;

            //获取缩略图
            var thumbnail = $('#product-img')[0].files[0];
            var formData = new FormData();
            formData.append('productImg',thumbnail);
            //获取详情图
            $('.detail-img').map(
                function (index,item) {
                    //判断该控件是否已选择了文件
                    if ($('.detail-img')[index].files.length>0) {
                        formData.append('img'+index,
                            $('.detail-img')[index].files[0]);
                    }
                });
            //将product json对象转成字符流保存至表单对象key为productStr的键值对里
            formData.append('productStr',JSON.stringify(product));
            //获取验证码
            var verifyCodeActual = $('#j_kaptcha').val();
            if (!verifyCodeActual){
                $.toast('请输入验证码！');
                return;
            }
            formData.append("verifyCodeActual",verifyCodeActual);
            $.ajax({
                url:productPostUrl,
                type:'POST',
                data:formData,
                contentType:false,
                processData:false,
                cache:false,
                success:function (data) {
                    if (data.success){
                        $.toast('提交成功！');
                        $('#kaptcha_img').click();
                    } else {
                        $.toast('提交失败！');
                        $('#kaptcha_img').click();
                    }

                }
            });
        });
})