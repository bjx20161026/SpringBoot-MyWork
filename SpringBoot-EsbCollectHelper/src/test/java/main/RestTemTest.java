package main;

import org.springframework.web.client.RestTemplate;

public class RestTemTest {
	public static void main(String[] args) {
		
//		RestTemplate restTemplate = new RestTemplate();
//		String body = "{\n" + 
//				"\"startTime\":\"201710120000\",\n" + 
//				"\"endTime\":\"201710121000\",\n" + 
//				"\"timeType\":\"hour\",\n" + 
//				"\"queryType\":\"select\",\n" + 
//				"\"limit\":\"3\",\n" + 
//				"\"page\":\"1\"\n" + 
//				"}";
//		String str = restTemplate.postForObject("http://10.221.235.17:8080/INAS/sml/query/cachecdn-query-tis-bf-byTime", body, String.class);
//		
//		System.out.println(str);
		
        
		String str = "dadfa.gz";
		
		System.out.println(str.endsWith("gz"));
	}
}
