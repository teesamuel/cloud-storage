package com.udacity.jwdnd.course1.cloudstorage;

import com.udacity.jwdnd.course1.cloudstorage.controller.tutorial.*;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.boot.web.servlet.ServletComponentScan;
import org.springframework.context.annotation.Bean;
import org.springframework.boot.web.servlet.FilterRegistrationBean;
import org.springframework.boot.web.servlet.ServletListenerRegistrationBean;
import org.springframework.boot.web.servlet.ServletRegistrationBean;
import java.util.*;

@SpringBootApplication
//@ServletComponentScan
public class CloudStorageApplication {

	public static void main(String[] args) {

		String input = "sting Test Hello";
		String vowel = "aeiou";
		StringBuilder sb = new StringBuilder();

		for (char c : input.toCharArray() ){
			if(vowel.contains(String.valueOf(c).toLowerCase())){
				sb.append(c);
			}
		}
		System.out.println(sb.toString());

		int [] arr =  {0,1,2,3,4};
//		System.out.println(binarySearchIterative(arr,3));
		System.out.println(fibonnaci(12));
		//binary search in Java
        List<String> inputList = Arrays.asList("abc", "bcd", "bbb", "ace", "snb", "aaaa", "bbbbb", "eeee");
        List<String>[] output = groupString(inputList);
        System.out.println(output);
        for (List<String> lst : output) {
            System.out.println(lst);
            System.out.println();
        }

//		sb.insert(1, "Friend ");
//		System.out.println(sb.toString());

	SpringApplication.run(CloudStorageApplication.class, args);
	}

//    /// Register Servlet.
//    @Bean
//    public ServletRegistrationBean getServletRegistrationBean() {
//        ServletRegistrationBean servletBean = new ServletRegistrationBean(new HelloServelet());
//        servletBean.addUrlMappings("/helloServlet");
//        return servletBean;
//    }
//
//    /// Register Filter.
//    @Bean
//    public FilterRegistrationBean getFilterRegistrationBean() {
//        FilterRegistrationBean filterBean = new FilterRegistrationBean(new HelloFilter());
//        // Add filter path
//        filterBean.addUrlPatterns("/helloServlet");
//        return filterBean;
//    }
//
//    @Bean
//    public ServletListenerRegistrationBean<HelloListener> getServletListenerRegistrationBean() {
//        ServletListenerRegistrationBean listenerBean =
//                new ServletListenerRegistrationBean(new HelloListener());
//        return listenerBean;
//    }
    public static List<String>[] groupString2(List<String> input) {
        List<String>[] ans = new List[3];
        for (String in : input) {
            if (in.charAt(0) == 'a') {
                List<String> lst;
                if (ans[0] == null) {
                    lst = new ArrayList<>();
                } else {
                    lst = ans[0];
                }
                lst.add(in);
                ans[0] = lst;
            } else if (in.charAt(0) == 'b') {
                List<String> lst;
                if (ans[1] == null) {
                    lst = new ArrayList<>();
                } else {
                    lst = ans[1];
                }
                lst.add(in);
                ans[1] = lst;
            } else {
                List<String> lst;
                if (ans[2] == null) {
                    lst = new ArrayList<>();
                } else {
                    lst = ans[2];
                }
                lst.add(in);
                ans[2] = lst;
            }
        }
        return ans;
    }
    public static List<String>[] groupString(List<String> input) {
        ArrayList<String> setA = new ArrayList<String>();
        ArrayList<String> setB = new ArrayList<String>();
        ArrayList<String> setOthers = new ArrayList<String>();
        for (String data : input
             ) {
            if (data.startsWith("a")){
                setA.add(data);
            }else if( data.startsWith("b")){
                setB.add(data);
            }else{
                setOthers.add(data);
            }
        }
        List<String>[] result = new List[3];
        result[0] = setA;
        result[1] = setB;
        result[2] = setOthers;

        return result;
    }

	public static int binarySearchIterative(int[] arr, int target) {
		if (arr.length == 0) return  -1;
		int end = arr.length - 1;
		int start = 0;

		while (start < end){
			int mid = (start + end) / 2;
			if (arr[mid] == target ){
				return mid;
			} else if (arr[mid] > target){
				end = mid -1 ;
			}else{
				start = mid + 1 ;
			}
			if (arr[start] == target) return  start;
			if (arr[end] == target) return  end;
		}
		return  -1;
	}

	public  static int fibonnaci(int number){
		if (number <=1) return  number;
		return  fibonnaci(number-2) + fibonnaci(number-1);
	}


}
