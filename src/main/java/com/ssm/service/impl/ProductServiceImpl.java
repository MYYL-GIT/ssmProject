package com.ssm.service.impl;

import com.ssm.dao.ProductDao;
import com.ssm.dao.ProductImgDao;
import com.ssm.dto.ProductExecution;
import com.ssm.entity.Product;
import com.ssm.entity.ProductImg;
import com.ssm.enums.ProductStateEnum;
import com.ssm.exceptions.ProductCategoryOperationException;
import com.ssm.exceptions.ProductOperationException;
import com.ssm.service.ProductService;
import com.ssm.util.ImageUtil;
import com.ssm.util.PageCalculator;
import com.ssm.util.PathUtil;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.web.multipart.commons.CommonsMultipartFile;

import java.util.ArrayList;
import java.util.Date;
import java.util.List;

@Service
public class ProductServiceImpl implements ProductService {
    @Autowired
    private ProductDao productDao;

    @Autowired
    private ProductImgDao productImgDao;

    @Override
    @Transactional
    //1.处理缩略图，获取缩略图相对路径并赋值给product
    //2.往tb_product写入商品信息，获取productId
    //3.结合productId批量处理商品详情图
    //4.将商品详情图列表批量插入tb_product_img中
    public ProductExecution addProduct(Product product, CommonsMultipartFile productImg, List<CommonsMultipartFile> productImgList)
            throws ProductCategoryOperationException {
        //空值判断
        if (product != null && product.getShop() != null && product.getShop().getShopId() != null) {
            try {
                //给商品信息赋初始值
                //默认为上架的状态
                product.setEnableStatus(1);
                product.setCreateTime(new Date());
                product.setLastEditTime(new Date());
                //若商品缩略图不为空则添加
                if (productImg != null) {
                    //存储图片
                    try {
                        addProductImg(product, productImg);
                    } catch (Exception e) {
                        throw new ProductOperationException("addproductImg error:" + e.toString());
                    }
                }
                try {
                    //添加商品信息
                    int effectedNum = productDao.insertProduct(product);
                    if (effectedNum <= 0) {
                        throw new ProductOperationException("商品添加失败");
                    }
                } catch (Exception e) {
                    throw new ProductOperationException("商品添加失败" + e.toString());
                }
            } catch (Exception e) {
                throw new ProductOperationException("addProduct error:" + e.toString());
            }
            //若商品详情图不为空则添加
            if (productImgList.size() > 0){
                //存储图片
                try {
                    addProductImgList(product,productImgList);
                } catch (Exception e) {
                    throw new ProductOperationException("addproductImg error:" + e.toString());
                }
            }
            return new ProductExecution(ProductStateEnum.SUCCESS, product);
        }else{
            return new ProductExecution(ProductStateEnum.EMPTY);
        }
    }

    @Override
    public Product queryProductById(long productId) {
        return productDao.queryProductById(productId);
    }

    @Override
    @Transactional
    //1.处理缩略图，先删除商品相关的所有图片，获取缩略图相对路径并赋值给product
    //2.往tb_product写入商品信息，获取productId
    //3.结合productId批量处理商品详情图
    //4.将商品详情图列表批量插入tb_product_img中
    public ProductExecution modifyProduct(Product product, CommonsMultipartFile productImg, List<CommonsMultipartFile> productImgList) throws ProductCategoryOperationException {
        //空值判断
        if (product != null && product.getShop() != null && product.getShop().getShopId() != null) {
            try {
                //设置商品信息更新时间
                product.setLastEditTime(new Date());
                //若商品缩略图不为空则添加
                if (productImg != null) {
                    //将原来存在的图片删除
                    try {
                        deleteProductImg(product);
                    } catch (Exception e) {
                        throw new ProductOperationException("updateProductImg error:" + e.toString());
                    }
                    //存储图片
                    try {
                        addProductImg(product, productImg);
                    } catch (Exception e) {
                        throw new ProductOperationException("updateProductImg error:" + e.toString());
                    }
                }
                try {
                    //更新商品信息
                    int effectedNum = productDao.updateProduct(product);
                    if (effectedNum <= 0) {
                        throw new ProductOperationException("商品更新失败");
                    }
                } catch (Exception e) {
                    throw new ProductOperationException("商品更新失败" + e.toString());
                }
            } catch (Exception e) {
                throw new ProductOperationException("updateProduct error:" + e.toString());
            }
            //若商品详情图不为空则添加
            if (productImgList.size() > 0){
                //删除数据库中的图片记录
                try {
                    productImgDao.batchDeleteProductImg(product.getProductId());
                }catch (Exception e) {
                    throw new ProductOperationException("deleteproductImg error");
                }
                //存储图片
                try {
                    addProductImgList(product,productImgList);
                } catch (Exception e) {
                    throw new ProductOperationException("addproductImg error:" + e.toString());
                }
            }
            return new ProductExecution(ProductStateEnum.SUCCESS, product);
        }else{
            return new ProductExecution(ProductStateEnum.EMPTY);
        }
    }

    @Override
    public ProductExecution getProductList(Product productCondition, int pageIndex, int pageSize) {
        int rowIndex = PageCalculator.calculateRowIndex(pageIndex,pageSize);
        List<Product> productList = productDao.queryProductList(productCondition,rowIndex,pageSize);
        int count = productDao.queryProductCount(productCondition);
        ProductExecution pe = new ProductExecution();
        if (productList != null){
            pe.setProductList(productList);
            pe.setCount(count);
        }else {
            pe.setState(ProductStateEnum.INNER_ERROR.getState());
        }
        return pe;
    }

    private void addProductImg(Product product,CommonsMultipartFile productImg) {
        //获取product图片目录的相对值路径
        String dest = PathUtil.getShopImagePath(product.getShop().getShopId());
        String productImgAddr = ImageUtil.generateThumbnail(productImg,dest);
        product.setImgAddr(productImgAddr);
    }

    private void addProductImgList(Product product,List<CommonsMultipartFile> productImgList){
        //获取图片的存储路径
        String dest = PathUtil.getShopImagePath(product.getShop().getShopId());
        List<ProductImg> productImgs = new ArrayList<ProductImg>();
        //遍历所有的图片
        for (CommonsMultipartFile commonsMultipartFile : productImgList){
            String productImgAddr = ImageUtil.generateThumbnail(commonsMultipartFile,dest);
            ProductImg productImg = new ProductImg();
            productImg.setImgAddr(productImgAddr);
            productImg.setProductId(product.getProductId());
            productImg.setCreateTime(new Date());
            productImgs.add(productImg);
        }
        //如果有数据，就批量添加
        if (productImgs.size() > 0 ){
            try{
                int effectedNum = productImgDao.batchInsertProductImg(productImgs);
                if (effectedNum <= 0){
                    throw new ProductCategoryOperationException("创建商品详情图片失败");
                }
            }catch (Exception e){
                throw new ProductCategoryOperationException("创建商品详情图片失败："+e.toString());
            }
        }
    }

    private void deleteProductImg(Product product){
        //删除商品缩略图
        Product productWaitDelete = productDao.queryProductById(product.getProductId());
        //获取图片的相对路径
        String dest = productWaitDelete.getImgAddr();
        //删除图片
        ImageUtil.deleteFileOrPath(dest);
        //删除商品详情图
        List<ProductImg> productImgList = productImgDao.queryProductImgById(product.getProductId());
        for (ProductImg productImg : productImgList){
            ImageUtil.deleteFileOrPath(productImg.getImgAddr());
        }
    }
}

