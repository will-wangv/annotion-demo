package com.study.util;

import com.study.annotation.NeedSetValue;
import com.github.pagehelper.StringUtil;
import org.springframework.beans.BeansException;
import org.springframework.context.ApplicationContext;
import org.springframework.context.ApplicationContextAware;
import org.springframework.stereotype.Component;

import java.lang.reflect.Field;
import java.lang.reflect.Method;
import java.util.Collection;
import java.util.HashMap;
import java.util.Map;

@Component
public class BeanUtil implements ApplicationContextAware {
    private ApplicationContext applicationContext;

    @Override
    public void setApplicationContext(ApplicationContext applicationContext) throws BeansException {
        this.applicationContext = applicationContext;
    }
    //name -- find -- 反射--Method --> method.invoke()
    //1 拿注解  --》method -- >java Bean -->Spring -->applicationContext

    public void setFieldVlaueForCol(Collection col) throws Exception {
        // 参数是什么?//List == [{id:1,customerId:1,customerName:xxx @NeedSetValue()}]

        //先拿注解
        Class<?> clazz = col.iterator().next().getClass();

        Field[] fields = clazz.getDeclaredFields();

        Map<String, Object> cache = new HashMap<>();


        for (Field needField : fields) {
            NeedSetValue sv = needField.getAnnotation(NeedSetValue.class);
            if (sv == null)
                continue;

            needField.setAccessible(true);

            Object bean = this.applicationContext.getBean(sv.beanClass());

            Method method = sv.beanClass().getMethod(sv.method(), clazz.getDeclaredField(sv.param()).getType());

            Field paramField = clazz.getDeclaredField(sv.param());
            paramField.setAccessible(true);

            Field targetFiled = null;
            Boolean needInnerField = !StringUtil.isEmpty(sv.targetFiled());

            String keyPrefix = sv.beanClass() + "-" + sv.method() + "-" + sv.targetFiled() + "-";

            for (Object obj : col) {
                //List == [{id:1,customerId:1,customerName:xxx @NeedSetValue()},{id:2,customerId:2,customerName:xxx @NeedSetValue()}]
                Object paramValue = paramField.get(obj);
                if (paramValue == null)
                    continue;

                Object value = null;
                //先拿缓存
                String key = keyPrefix + paramValue;

                if (cache.containsKey(key)) {
                    value = cache.get(key);
                } else {
                    value = method.invoke(bean, paramValue);//User

                    if (needInnerField) {
                        if (value != null) {
                            if (targetFiled == null) {
                                targetFiled = value.getClass().getDeclaredField(sv.targetFiled());
                                targetFiled.setAccessible(true);
                            }
                            value = targetFiled.get(value);
                        }
                    }
                    cache.put(key, value);

                }
                needField.set(obj, value);
            }

        }

    } //5000 90
}
