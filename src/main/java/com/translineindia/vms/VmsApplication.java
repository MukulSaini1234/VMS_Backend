package com.translineindia.vms;


import java.util.ArrayList;
import java.util.HashMap;
import java.util.Map;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.web.servlet.config.annotation.EnableWebMvc;

@SpringBootApplication
public class VmsApplication {

	public static void main(String[] args) {
		SpringApplication.run(VmsApplication.class, args);
		
		String str1 = "foo";
		String str2 = "fooBar";  // str1 is part of str2 ?
//		Map<String,String> mapForCnt = Map<String,String>();
		Map<Character,Integer> charMap = new HashMap<>();
		ArrayList arrList = new ArrayList();
		for(char c: str2.toCharArray()) {
			charMap.put(c, charMap.getOrDefault(c, 0) + 1);
//			arrList.add
		}
		
//		System.out.println("chrMap :"+charMap);
//		pubic static boolean checkCh(String str1) {
//		for(char ch: str1.toCharArray()) {
//			if( !charMap.containsKey(ch) || charMap.get(ch)) ==0){
//				return "no";
//			}
		
//		}
//		}
	}	
	
//	@Bean
//    public MultipartConfigElement multipartConfigElement() {
//        MultipartConfigFactory factory = new MultipartConfigFactory();
//        factory.setMaxFileSize(DataSize.ofMegabytes(10));
//        factory.setMaxRequestSize(DataSize.ofMegabytes(20));
//        return factory.createMultipartConfig();
//    }
}
