package com.example.spring.test;


import java.util.HashMap;
import java.util.Map;

import org.json.JSONArray;
import org.json.JSONObject;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.http.HttpEntity;
import org.springframework.http.HttpHeaders;
import org.springframework.http.HttpMethod;
import org.springframework.web.client.RestTemplate;
import org.springframework.web.util.UriComponentsBuilder;


@SpringBootApplication
public class OpenDotaPoc {

	public static void main(String[] args) {
		SpringApplication.run(OpenDotaPoc.class, args);
		OpenDotaPoc openDota = new OpenDotaPoc();
		JSONArray getPlayersWithAccountId=openDota.getPlayersWithAccountId(211511642);
//		System.out.println(openDota.getDetailsOfHeroesByIdgetPlayersWithAccountId(211511642));
//		JSONArray getDetailsOfHeroesById=openDota.getDetailsOfHeroesById(getPlayersWithAccountId);
		System.out.println(openDota.getDetailsOfHeroesById(openDota.getPlayersWithAccountId(211511642)));
	}

	public JSONArray getPlayersWithAccountId(int accountId) {
		RestTemplate restTemplate = new RestTemplate();
		String accessUrl = "https://api.opendota.com/api/players/" + accountId + "/heroes";
		HttpHeaders headers = new HttpHeaders();
		headers.set("Accept", "application/json");
		headers.add("user-agent",
				"Mozilla/5.0 (Windows NT 10.0; Win64; x64) AppleWebKit/537.36 (KHTML, like Gecko) Chrome/54.0.2840.99 Safari/537.36");

		UriComponentsBuilder builder = UriComponentsBuilder.fromHttpUrl(accessUrl);
		HttpEntity<?> entity = new HttpEntity<>(headers);

		HttpEntity<String> response1 = restTemplate.exchange(builder.toUriString(), HttpMethod.GET, entity,
				String.class);
		String responseStr = response1.getBody();
		JSONArray jsonArr = new JSONArray(responseStr);
		JSONArray jsonArray = new JSONArray();
		int count = 0;
		for (int i = 0; i < jsonArr.length(); i++) {
			if (count < 3) {
				JSONObject josnObject = jsonArr.getJSONObject(i);
				jsonArray.put(jsonArr.getJSONObject(i));
				count++;
			}
		}

		return jsonArray;

	}
	
	public JSONArray getDetailsOfHeroesById(JSONArray jsonArray) {
		JSONArray jsonArrayOfHeroes = new JSONArray();
		RestTemplate restTemplate = new RestTemplate();
		String accessUrl = "https://api.opendota.com/api/heroes";
		HttpHeaders headers = new HttpHeaders();
		headers.set("Accept", "application/json");
		headers.add("user-agent",
				"Mozilla/5.0 (Windows NT 10.0; Win64; x64) AppleWebKit/537.36 (KHTML, like Gecko) Chrome/54.0.2840.99 Safari/537.36");

		UriComponentsBuilder builder = UriComponentsBuilder.fromHttpUrl(accessUrl);
		HttpEntity<?> entity = new HttpEntity<>(headers);

		HttpEntity<String> response1 = restTemplate.exchange(builder.toUriString(), HttpMethod.GET, entity,
				String.class);
		String responseStr = response1.getBody();
		JSONArray jsonArr = new JSONArray(responseStr);
		Map<Integer,Object> map=new HashMap<Integer,Object>();
		for (int i = 0; i < jsonArr.length(); i++) {
			Integer heroId=(Integer) jsonArr.getJSONObject(i).get("id");
//			System.out.println(heroId);
				map.put(heroId, jsonArr.getJSONObject(i));
		}
		for(int j=0;j<jsonArray.length();j++) {
			Integer heroIds=Integer.parseInt((String) jsonArray.getJSONObject(j).get("hero_id"));
			if(map.containsKey(heroIds)) {
				jsonArrayOfHeroes.put(map.get(heroIds));
			}
		}
		return jsonArrayOfHeroes;
		
	}

}
