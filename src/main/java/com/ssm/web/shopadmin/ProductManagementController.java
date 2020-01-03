package com.ssm.web.shopadmin;

import com.fasterxml.jackson.databind.ObjectMapper;
import com.ssm.dto.ProductCategoryExecution;
import com.ssm.dto.ProductExecution;
import com.ssm.entity.Product;
import com.ssm.entity.Shop;
import com.ssm.enums.ProductStateEnum;
import com.ssm.exceptions.ProductOperationException;
import com.ssm.service.ProductCategoryService;
import com.ssm.service.ProductService;
import com.ssm.util.CodeUtil;
import com.ssm.util.HttpServletRequestUtil;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.multipart.MultipartHttpServletRequest;
import org.springframework.web.multipart.commons.CommonsMultipartFile;
import org.springframework.web.multipart.commons.CommonsMultipartResolver;

import javax.servlet.http.HttpServletRequest;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

@Controller
@RequestMapping("/productadmin")
public class ProductManagementController {
    @Autowired
    private ProductService productService;
    @Autowired
    private ProductCategoryService productCategoryService;

    //支持上传商品详情图的最大数量
    private static final int IMAGEMAXCOUNT = 6;

    @PostMapping(value = "addproduct")
    @ResponseBody
    private Map<String,Object> addProduct(HttpServletRequest request){
        Map<String,Object> modelMap = new HashMap<String,Object>();
        //验证码校验
        if(!CodeUtil.checkVerifyCode(request)){
            modelMap.put("success",false);
            modelMap.put("errMsg","验证码输入错误");
            return modelMap;
        }

        Product product = null;
        List<CommonsMultipartFile> imgList = new ArrayList<CommonsMultipartFile>();
        CommonsMultipartFile productImg = null;

        //接收前端参数的变量的初始化，包括商品，缩略图，详情图列表实体类
        try {
            CommonsMultipartResolver commonsMultipartResolver = new CommonsMultipartResolver(request.getSession().getServletContext());
            //如果有文件流，就尝试获取图片
            if (commonsMultipartResolver.isMultipart(request)) {
                MultipartHttpServletRequest multipartHttpServletRequest = (MultipartHttpServletRequest) request;
                //获取缩略图文件
                productImg = (CommonsMultipartFile) multipartHttpServletRequest.getFile("productImg");
                for (int i = 0; i < IMAGEMAXCOUNT; i++) {
                    CommonsMultipartFile img = (CommonsMultipartFile) multipartHttpServletRequest.getFile("img" + i);
                    if (img != null) {
                        //有图片就加进去
                        imgList.add(img);
                    } else {
                        break;
                    }
                }
            } else {
                modelMap.put("success", false);
                modelMap.put("errMsg", "上传图片不能为空");
                return modelMap;
            }
        }catch (Exception e){
            modelMap.put("success", false);
            modelMap.put("errMsg", e.toString());
            return modelMap;
        }

        try {
            //尝试将前端传回来的json数据转换成Product实体类
            ObjectMapper mapper = new ObjectMapper();
            String productStr = HttpServletRequestUtil.getString(request, "productStr");
            product = mapper.readValue(productStr,Product.class);

        }catch (Exception e){
            modelMap.put("success", false);
            modelMap.put("errMsg", e.toString());
            return modelMap;
        }
        if (product != null && productImg != null && imgList.size() > 0){
            try {
                Shop currentShop = (Shop) request.getSession().getAttribute("currentShop");
                product.setShop(currentShop);
                ProductExecution pe = productService.addProduct(product,productImg,imgList);
                if (pe.getState() == ProductStateEnum.SUCCESS.getState()){
                    modelMap.put("success",true);
                }else {
                    modelMap.put("success", false);
                    modelMap.put("errMsg", pe.getStateInfo());
                }

            }catch (ProductOperationException e){
                modelMap.put("success", false);
                modelMap.put("errMsg", e.toString());
                e.printStackTrace();
                return modelMap;
            }
        }else {
            modelMap.put("success", false);
            modelMap.put("errMsg", "请输入商品信息");
        }
        return modelMap;
    }

    @GetMapping(value = "/getproductbyid")
    @ResponseBody
    private Map<String,Object> getProductById(HttpServletRequest request){
        Map<String,Object> modelMap = new HashMap<String,Object>();
        Long productId = HttpServletRequestUtil.getLong(request,"productId");
        Shop currentShop = (Shop) request.getSession().getAttribute("currentShop");
        if (productId > -1){
            try {
                Product product = productService.queryProductById(productId);
                ProductCategoryExecution pc = productCategoryService.getProductCategoryListById(currentShop.getShopId());
                modelMap.put("product",product);
                modelMap.put("productCategoryList",pc.getProductCategoryList());
                modelMap.put("success",true);
            }catch (Exception e){
                modelMap.put("success",false);
                modelMap.put("errMsg",e.toString());
            }
        }else{
            modelMap.put("success",false);
            modelMap.put("errMsg","empty shopId");
        }
        return modelMap;
    }

    @PostMapping(value = "modifyproduct")
    @ResponseBody
    private Map<String,Object> modifyProduct(HttpServletRequest request){
        Map<String,Object> modelMap = new HashMap<String,Object>();
        boolean statusChange = HttpServletRequestUtil.getBoolean(request,"statusChange");
        //验证码校验
        if(!statusChange&&!CodeUtil.checkVerifyCode(request)){
            modelMap.put("success",false);
            modelMap.put("errMsg","验证码输入错误");
            return modelMap;
        }

        Product product = null;
        List<CommonsMultipartFile> imgList = new ArrayList<CommonsMultipartFile>();
        CommonsMultipartFile productImg = null;

        //接收前端参数的变量的初始化，包括商品，缩略图，详情图列表实体类
        try {
            CommonsMultipartResolver commonsMultipartResolver = new CommonsMultipartResolver(request.getSession().getServletContext());
            //如果有文件流，就尝试获取图片
            if (commonsMultipartResolver.isMultipart(request)) {
                MultipartHttpServletRequest multipartHttpServletRequest = (MultipartHttpServletRequest) request;
                //获取缩略图文件
                productImg = (CommonsMultipartFile) multipartHttpServletRequest.getFile("productImg");
                for (int i = 0; i < IMAGEMAXCOUNT; i++) {
                    CommonsMultipartFile img = (CommonsMultipartFile) multipartHttpServletRequest.getFile("img" + i);
                    if (img != null) {
                        //有图片就加进去
                        imgList.add(img);
                    } else {
                        break;
                    }
                }
            }
        }catch (Exception e){
            modelMap.put("success", false);
            modelMap.put("errMsg", e.toString());
            return modelMap;
        }

        try {
            //尝试将前端传回来的json数据转换成Product实体类
            ObjectMapper mapper = new ObjectMapper();
            String productStr = HttpServletRequestUtil.getString(request, "productStr");
            product = mapper.readValue(productStr,Product.class);

        }catch (Exception e){
            modelMap.put("success", false);
            modelMap.put("errMsg", e.toString());
            return modelMap;
        }
        if (product != null){
            try {
                Shop currentShop = (Shop) request.getSession().getAttribute("currentShop");
                product.setShop(currentShop);
                ProductExecution pe = productService.modifyProduct(product,productImg,imgList);
                if (pe.getState() == ProductStateEnum.SUCCESS.getState()){
                    modelMap.put("success",true);
                }else {
                    modelMap.put("success", false);
                    modelMap.put("errMsg", pe.getStateInfo());
                }

            }catch (ProductOperationException e){
                modelMap.put("success", false);
                modelMap.put("errMsg", e.toString());
                return modelMap;
            }
        }else {
            modelMap.put("success", false);
            modelMap.put("errMsg", "请输入商品信息");
        }
        return modelMap;
    }

    @GetMapping(value = "/getproductlist")
    @ResponseBody
    private Map<String,Object> getProductList(HttpServletRequest request){
        Map<String,Object> modelMap = new HashMap<String,Object>();
        try {
            Shop currentShop = (Shop) request.getSession().getAttribute("currentShop");
            Product productCondition = new Product();
            productCondition.setShop(currentShop);
            ProductExecution pe = productService.getProductList(productCondition,0,100);
            modelMap.put("productList",pe.getProductList());
            modelMap.put("success",true);
        }catch (Exception e){
            modelMap.put("success",false);
            modelMap.put("errMsg",e.getMessage());
        }
        return modelMap;
    }
}
