package com.study.service;

import com.study.annotation.NeedSetValueField;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.study.dao.OrderDao;
import com.study.model.Order;
import com.github.pagehelper.Page;
import com.github.pagehelper.PageHelper;

@Service
public class OrderService {

    @Autowired
    private OrderDao orderDao;

    @NeedSetValueField  //被这个注解修饰的方法 一律进入到我们的切面方法中去
    public Page<Order> pageQuery(String customerId, int pageNum, int pageSize) {
        Page<Order> page = PageHelper.startPage(pageNum, pageSize);
        this.orderDao.query(customerId);
        // 设计原则 --  单一职责原则  开闭原则 -- 一段代码好不好
        //是否 违背了设计原则？ 开闭 -- 对修改关闭 对拓展开放 -- 设计-- 代理模式
        //for(Order order: page){
        //   xxxdao.find(order.getCustomerId)
        // order.set (XXX)
        // }
        //xxxUtil.set() ???
        // 需要获得订单的客户姓名
        //加入进来
        return page; //在return 之后 再去对结果集进行一次操作 --AOP --Aspect --定义一个切面点
    }
}
