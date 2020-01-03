package com.ssm.dto;

import com.ssm.entity.ProductCategory;
import com.ssm.enums.ProductCatgoryStateEnum;

import java.util.List;

public class ProductCategoryExecution {

    //结果状态
    private int state;

    //状态标识
    private String stateInfo;

    //productCatgory列表（查询商品类别列表的时候使用）
    private List<ProductCategory> productCategoryList;

    public int getState() {
        return state;
    }

    public void setState(int state) {
        this.state = state;
    }

    public String getStateInfo() {
        return stateInfo;
    }

    public void setStateInfo(String stateInfo) {
        this.stateInfo = stateInfo;
    }

    public List<ProductCategory> getProductCategoryList() {
        return productCategoryList;
    }

    public void setProductCategoryList(List<ProductCategory> productCategoryList) {
        this.productCategoryList = productCategoryList;
    }

    public ProductCategoryExecution(){

    }

    //操作失败的时候使用的构造器
    public ProductCategoryExecution(ProductCatgoryStateEnum stateEnum){
        this.state = stateEnum.getState();
        this.stateInfo = stateEnum.getStateInfo();
    }

    //操作成功的时候使用的构造器
    public ProductCategoryExecution(ProductCatgoryStateEnum stateEnum,List<ProductCategory> productCategoryList){
        this.state = stateEnum.getState();
        this.stateInfo = stateEnum.getStateInfo();
        this.productCategoryList = productCategoryList;
    }

}
