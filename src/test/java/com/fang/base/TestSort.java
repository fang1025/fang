package com.fang.base;

import org.junit.Test;

public class TestSort {
	
	@Test
	public void testSort(){
		int[] arr = {3,6,2,7,1,3,9,15,10,4};
//		sort1(arr, 0, arr.length-1);
		sort2(arr,0, arr.length-1);
		for (int i : arr) {
			System.out.println(i);
		}
		System.out.println(arr);
	}
	int a = 1;
	public int[] sort1(int[] nums, int low, int high) {  
        int mid = (low + high) / 2;  
        if (low < high) {  
            // 左边  
            sort1(nums, low, mid);  
            // 右边  
            sort1(nums, mid + 1, high);  
            // 左右归并  
            merge(nums, low, mid, high);  
            System.out.println("第"  + a++ + "次： low=" + low + ",high=" + high + ",mid=" + mid);
        }  
        return nums;  
    }  
  
    public static void merge(int[] nums, int low, int mid, int high) {  
        int[] temp = new int[high - low + 1];  
        int i = low;// 左指针  
        int j = mid + 1;// 右指针  
        int k = 0;  
  
        // 把较小的数先移到新数组中  
        while (i <= mid && j <= high) {  
            if (nums[i] < nums[j]) {  
                temp[k++] = nums[i++];  
            } else {  
                temp[k++] = nums[j++];  
            }  
        }  
  
        // 把左边剩余的数移入数组  
        while (i <= mid) {  
            temp[k++] = nums[i++];  
        }  
  
        // 把右边边剩余的数移入数组  
        while (j <= high) {  
            temp[k++] = nums[j++];  
        }  
  
        // 把新数组中的数覆盖nums数组  
        for (int k2 = 0; k2 < temp.length; k2++) {  
            nums[k2 + low] = temp[k2];  
        }  
    }  
    
    
    
    public static int partition(int []array,int lo,int hi){
        //固定的切分方式
        int key=array[lo];
        while(lo<hi){
            while(array[hi]>=key&&hi>lo){//从后半部分向前扫描
                hi--;
            }
            array[lo]=array[hi];
            while(array[lo]<=key&&hi>lo){//从前半部分向后扫描
                lo++;
            }
            array[hi]=array[lo];
        }
        array[hi]=key;
        return hi;
    }
    
    public static void sort2(int[] array,int lo ,int hi){
        if(lo>=hi){
            return ;
        }
        int index=partition(array,lo,hi);
        sort2(array,lo,index-1);
        sort2(array,index+1,hi); 
    }

}
