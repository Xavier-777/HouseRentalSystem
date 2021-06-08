package com.house.constant;


/**
 * 房屋状态
 */
public enum HouseStatus {
    /**
     * 未审核
     */
    unchecked("未审核"),

    /**
     * 已审核通过
     */
    pass("已审核通过"),

    /**
     * 审核未通过
     */
    not_pass("审核未通过"),

    /**
     * 房屋已被下单，但未支付，只锁定房屋
     */
    ordered("已下单"),

    /**
     * 被使用，正在出租
     */
    using("正在被使用");

    private String describe;    //描述房屋状态

    HouseStatus(String describe) {
        this.describe = describe;
    }

    public String getDescribe() {
        return describe;
    }

    public void setDescribe(String describe) {
        this.describe = describe;
    }
}
