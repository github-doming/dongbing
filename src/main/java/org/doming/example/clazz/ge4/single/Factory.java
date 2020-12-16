package org.doming.example.clazz.ge4.single;

/**
 * @Description:
 * @Author: null
 * @Date: 2019-12-30 18:13
 * @Version: v1.0
 */
public class Factory {



    public static void show(){
        Parent parent = Son.getInstance();
        parent.Show();
    }

    public static void main(String[] args) {
        show();
    }
}
