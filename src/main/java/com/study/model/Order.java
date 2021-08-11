package com.study.model;

import com.study.annotation.NeedSetValue;
import com.study.dao.UserDao;

import java.io.Serializable;


public class Order implements Serializable {

    /**
     *
     */
    private static final long serialVersionUID = -8821751371277937944L;

    private String id;

    private String customerId;

    @NeedSetValue(beanClass = UserDao.class, param = "customerId", method = "find", targetFiled = "name")
    private String customerName; //反射 --> find --反射怎么去调用方法的？-- User user = find.invoke()
    // 通过反射去拿到method  -- bean --拿注解 --.getAnnotation() --Order结果集. --怎么拿 ？


    public String getId() {
        return id;
    }

    public void setId(String id) {
        this.id = id;
    }

    public String getCustomerId() {
        return customerId;
    }

    public void setCustomerId(String customerId) {
        this.customerId = customerId;
    }

    public String getCustomerName() {
        return customerName;
    }

    public void setCustomerName(String customerName) {
        this.customerName = customerName;
    }

}
