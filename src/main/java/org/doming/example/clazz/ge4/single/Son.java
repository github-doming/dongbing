package org.doming.example.clazz.ge4.single;

/**
 * @Description:
 * @Author: null
 * @Date: 2019-12-30 18:12
 * @Version: v1.0
 */
public class Son extends Parent {
    private static final Son INSTANCE = new Son();
    public static Son getInstance(){
        return INSTANCE;
    }

    @Override
    public void Show() {
        System.out.println("is my son");
    }
}
