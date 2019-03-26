package com.ding.algorithm;

/**
 * <p>
 *
 * </p>
 * <p>Company: www.kktalkee.com</p>
 *
 * @author dingzhongfa
 * @date 2019-03-12 上午11:27
 */
public class D {

    public static void main(String[] args) {
        int [] arr = new int[]{123,234,23,234,86,54,324,534,534,56,57,787,876,4,242,89,321,3,21,3,4,19,53,45757,87};
        arr = bubbleSort(arr);
        for (int i:arr){
            System.out.print(i+",");
        }
    }

    private static int[] bubbleSort(int arr[]){
        for (int i=0;i<arr.length;i++){
            for (int j=0;j<arr.length-i-1;j++){
                if (arr[j]>arr[j+1]){
                    int temp = arr[j];
                    arr[j]=arr[j+1];
                    arr[j+1]=temp;
                }
            }
        }
        return arr;
    }
}
