package com.ssm.enums;

public enum ProductCatgoryStateEnum {
    SUCCESS(1,"创建成功"),INNER_ERROR(-1001,"操作失败"),EMPTY_LIST(-1002,"添加数少于1");

    private int state;

    private String stateInfo;

    public int getState() {
        return state;
    }

    public String getStateInfo() {
        return stateInfo;
    }

    private ProductCatgoryStateEnum(int state, String sateInfo){
        this.state = state;
        this.stateInfo = sateInfo;
    }

    /**
     * 依据传入的state返回相应的enum值
     */
    public static ProductCatgoryStateEnum stateOf(int state){
        for (ProductCatgoryStateEnum stateEnum:values()){
            if (stateEnum.getState() == state){
                return stateEnum;
            }
        }
        return null;
    }
}
