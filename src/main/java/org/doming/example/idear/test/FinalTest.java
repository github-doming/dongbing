package org.doming.example.idear.test;

import org.junit.Test;

import java.util.Random;

/**
 * @Description:
 * @Author: Dongming
 * @Date: 2019-01-26 14:21
 * @Email: job.dongming@foxmail.com
 * @Version: v1.0
 */
public class FinalTest {
    private static Random random = new Random(47);
    private String id;

    public FinalTest(String id) {
        this.id = id;
    }

    private final int valueOne = 9;
    private static final int VALUE_TWO = 99;
    public static final int VALUE_THREE = 39;

    private final int i4 = random.nextInt(20);
    static final int INT_5 = random.nextInt(22);

    private Value v1 = new Value(11);
    private final Value v2 = new Value(22);

    private static final Value VAL_3 = new Value(33);
    private final int a[] = {1, 2, 3, 4, 5, 6};

    @Override
    public String toString() {
        return "FinalTest{" +
                "id='" + id + '\'' +
                ", i4=" + i4 +
                ", INT_5=" + INT_5 +
                '}';
    }

    @Test
    public void test() {
        FinalTest test = new FinalTest("test");
        test.v2.i++;


    }


}

class Value {
    int i;

    public Value(int i) {
        this.i = i;
    }
}