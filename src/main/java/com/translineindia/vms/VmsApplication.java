package com.translineindia.vms;


import java.util.ArrayList;
import java.util.Arrays;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.scheduling.annotation.EnableScheduling;
import org.springframework.web.servlet.config.annotation.EnableWebMvc;

@SpringBootApplication
public class VmsApplication {

	public static void main(String[] args) {
		SpringApplication.run(VmsApplication.class, args);
		 String s = "ADOBECODEBANC";
	     String t = "ABC";
	     Map<Character,Integer> tMap = new HashMap<Character,Integer>();
	     HashMap<Character,Integer> windowCount = new HashMap<Character,Integer>();
	     
	     for(char c :t.toCharArray()) {
	    	 tMap.put(c, tMap.getOrDefault(c, 0)+1);
	     }
	     
	     System.out.println("tMap :"+tMap);
	     int right = 0;
	     int left =0;
	     int formed=0;
	     System.out.println("s length:"+s.length());
	     while(right<s.length()) {
	    	 char c = s.charAt(right);
//	    	 System.out.println("c :"+c);
	    	 windowCount.put(c, windowCount.getOrDefault(c, 0)+1);
//	    	 System.out.println("window count:"+windowCount);
	    	 if(tMap.containsKey(c) && windowCount.get(c).intValue()==tMap.get(c).intValue()) {
	    		 formed++;
	    	 }
	    	 
	    	 while(left<=right && formed==tMap.size()) {
	    		 char leftChar = s.charAt(left);
	    		 System.out.println("leftChar :"+leftChar);
	    		 left++;
	    	 }
	    	 right++;
	     }
	     System.out.println("windowCount :"+windowCount.toString());
	     System.out.println("formed :"+formed);
	     int[][] balloons = {{10,16}, {2,8}, {1,6}, {7,12}};
	     Arrays.sort(balloons,(a,b)->Integer.compare(a[1], b[1]));
	     int arrows=1;
	     int arrowPosition = balloons[0][1];
	     System.out.println("balloons: "+balloons.toString());
	     for(int i=0;i<balloons.length;i++) {
	    	 if(balloons[i][0]>arrowPosition) {
	    		 arrows++;
	    		 arrowPosition=balloons[i][1];
	    		 
	    	 }
	     }
	     System.out.println("no of Arrows: "+arrows);
	     int[][] intervals = {{1,3},{2,6},{8,10},{15,18}};
	     int[][] newInterval= {{0,0}};
	     int initialPosFirst = intervals[0][0];
	     int initialPosSecond = intervals[0][1];
	     int lastPos = intervals[0][1];
	     int index=0;
	     List<int[]> mergedList = new ArrayList<>();
	     System.out.println("intervals :"+intervals);
	     Arrays.sort(intervals,(a,b)->Integer.compare(a[1], b[1]));
	     for(int i=1;i<intervals.length;i++) {
	    	 if(intervals[i][1]<lastPos) {
//	    		 newInterval[index][1]=Math.max(intervals[index][1], intervals[i][1]);
//	    		 newInterval[i-1][0]=initialPosFirst;
//	    		 newInterval[i-1][1]=intervals[i][1];
	    		 mergedList.add(index, null);
	    		 initialPosFirst =  intervals[i][0];
	    		 initialPosSecond = intervals[i][1];
	    	 }
	     }
	     System.out.println("new interval");
//	     for(int[] i:newInterval) {
//	    	 System.out.println("new item :"+newInterval.toString());
//	     }
	     for (int[] i : newInterval) {
	    	    System.out.println("new item: " + Arrays.toString(i));
	    	}
	     
	     
//	     int[] nums = {1, 2, 3, 1};
	     int[] nums = {5,7,7,8,8,10};
	     int target=8;
	     int left1 =0;
	     int right1 = nums.length-1;
	     while(left1<right1) {
	    	 int mid = left1 + (right1-left1)/2;
	    	 System.out.println("mid "+mid);
//	    	 if(nums[mid]>nums[mid]+1) {
	    	 if(nums[mid]>target) {
	    		 right1 = mid;
	    	 }else {
	    		 left1 = mid+1;
	    	 }
	    	
	     }
	     System.out.println("left 1:"+left1);
	    
	}	
	
//	@Bean
//    public MultipartConfigElement multipartConfigElement() {
//        MultipartConfigFactory factory = new MultipartConfigFactory();
//        factory.setMaxFileSize(DataSize.ofMegabytes(10));
//        factory.setMaxRequestSize(DataSize.ofMegabytes(20));
//        return factory.createMultipartConfig();
//    }
}
