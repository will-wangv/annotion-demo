package com.study.aspect;

import com.study.util.BeanUtil;
import org.aspectj.lang.ProceedingJoinPoint;
import org.aspectj.lang.annotation.Around;
import org.aspectj.lang.annotation.Aspect;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import java.util.Collection;

@Component
@Aspect
public class SetFeildValueAspect {
    @Autowired
    BeanUtil beanUtil;

    @Around("@annotation(com.study.annotation.NeedSetValueField)")
    public Object doSetFeildValue(ProceedingJoinPoint pjp) throws Throwable {
        Object ret = pjp.proceed();// 执行原方法 获取结果集
        //List == [{id:1,customerId:1,customerName:xxx @NeedSetValue()}]
        //操作结果集 ，将值设置进结果中
        if (ret instanceof Collection) {
            this.beanUtil.setFieldVlaueForCol((Collection) ret);
        } else {
            //不是集合 待完善 TODO
        }
        return ret;
    }


}
