package com.house.constant;

/**
 * 订单状态
 */
public enum OrderStatus {
    /**
     * 房屋已下单，生成订单号
     */
    ordered("已下单"),

    /**
     * 订单未支付
     */
    not_pay("已取消"),

    /**
     * 订单已支付
     */
    paid("已支付");

    private String describe;

    OrderStatus(String describe) {
        this.describe = describe;
    }

    public String getDescribe() {
        return describe;
    }

    public void setDescribe(String describe) {
        this.describe = describe;
    }
}
