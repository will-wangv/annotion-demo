package com.study.reflection;


import com.study.annotation.Study;
import com.study.model.Person;

import java.lang.reflect.Field;
import java.lang.reflect.InvocationTargetException;
import java.lang.reflect.Method;

public class ReflectionDemo {

    public static void main(String[] args) throws ClassNotFoundException, IllegalAccessException, InstantiationException, NoSuchMethodException, InvocationTargetException {
        //示例1 ：通过反射获取Class
        Person person = new Person();
        Class clz = person.getClass();//获取方式1
        Class<?> aClass = Class.forName("com.study.model.Person"); //Spring中解析xml后生成对象采用的机制
        System.out.println(clz);
        System.out.println(aClass);

        //实例2： 通过反射获取Class的类名，包名等
        String name = clz.getName();//获取类名，含包名
        String simpleName = clz.getSimpleName();//获取类名，不含包名
        System.out.println("含有包名的类名为：" + name + "/不含包名的类名为:" + simpleName);

        //实例3：获取当前Class的类属性
        Field[] declaredFields = clz.getDeclaredFields();
        System.out.println("当前类一共有属性：" + declaredFields.length + "个");
        for (int i = 0; i < declaredFields.length; i++) {
            System.out.println(declaredFields[i]);
        }

//        //实例4：获取当前class属性的具体值
//
        person.setName("tony");
        Field[] declaredFields1 = clz.getDeclaredFields();
        for (int i = 0; i < declaredFields1.length; i++) {
            declaredFields1[i].setAccessible(true);
            System.out.println(declaredFields1[i].get(person)); //传入被实例的类

        }
//        //实例4的另外一种写法
        Object p = aClass.newInstance();//相当于实例化
        for (Field declaredField : declaredFields) {
            declaredField.setAccessible(true);
            //通过反射设置值 --这里我们通过属性名去获取和设置值
            if (declaredField.getName().equals("name")) {
                declaredField.set(p, "kane");
            } else {
                declaredField.set(p, 20);
            }
            System.out.println(declaredField.get(p));
        }
//
//        //实例5： 反射获得当前类的方法
        //1.获取全部方法
        Method[] methods = clz.getMethods();
        for (Method method : methods) {
            System.out.println(method.getName());
        }

        Method getName = clz.getMethod("getString");//获取方法时，需要传入方法名
        Object invoke = getName.invoke(p); //执行方法
        System.out.println("可执行的方法返回的结果是:" + invoke);
//
//        //实例6：获得注解
//        //1.从类上获得注解
//        Study annotation = (Study) clz.getAnnotation(com.study.annotation.Study.class);
//        System.out.println(annotation);
//        //同时可以获取注解上的内容
//        String[] mores = annotation.mores();
//        String aName = annotation.name();
//        int type = annotation.type();
//        System.out.println("从注解上获取的属性,aName=" + aName + ";mores=" + mores + ",type=" + type);
//
//        //2.从方法上获得注解
//        for (Method method : methods) {
//            Study annotation1 = method.getAnnotation(Study.class);
//            if (annotation1 == null)
//                continue;  //注意，这上面可以去获取到制定的注解
//            String name1 = annotation1.name();
//            String[] mores1 = annotation1.mores();
//            int type1 = annotation1.type();
//            System.out.println("从注解上获取的属性,aName=" + name1 + ";mores=" + mores1 + ",type=" + type1);
//        }
//
//        //3.从属性上获得注解
//        for (int i = 0; i < declaredFields.length; i++) {
////            Study annotation1 = declaredFields[i].getAnnotation(Study.class);
////            if (annotation1 == null)
////                continue;
////            String name1 = annotation1.name();
//            String[] mores1 = annotation1.mores();
//            int type1 = annotation1.type();
//            System.out.println("从注解上获取的属性,aName=" + name1 + ";mores=" + mores1 + ",type=" + type1);
//        }
//

    }
}
