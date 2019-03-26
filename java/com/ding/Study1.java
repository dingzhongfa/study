package com.ding;

import java.time.Instant;

/**
 * <p>
 * todo
 * </p>
 * <p>Company: www.kktalkee.com</p>
 *
 * @author dingzhongfa
 * @date 2018-10-09 下午5:22
 */
public class Study1 {


    public static void main(String[] args) {
        Long sum1 = 0L;
        Long start1 = Instant.now().toEpochMilli();
        for (long i = 1; i < Integer.MAX_VALUE; i++) {
            sum1 += i;
        }
        System.out.println("sum = " + sum1);
        System.out.println("Long 执行时间为: " + (Instant.now().toEpochMilli() - start1) + " ms");


        long sum2 = 0L;
        Long start2 = Instant.now().toEpochMilli();
        for (long i = 1; i < Integer.MAX_VALUE; i++) {
            sum2 += i;
        }
        System.out.println("sum = " + sum2);
        System.out.println("long 执行时间为: " + (Instant.now().toEpochMilli() - start2) + " ms");
    }
}
