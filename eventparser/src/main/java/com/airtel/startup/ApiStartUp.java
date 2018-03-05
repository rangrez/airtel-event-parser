package com.airtel.startup;

import java.io.BufferedReader;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;

import org.json.JSONObject;

import models.Events;
import models.FileJson;

public class ApiStartUp {
	public Events initializeEvents() {
		StringBuilder sb = new StringBuilder();
		Events eventsObj = new Events();
		List<FileJson> ls = new ArrayList<>();
		try {
			InputStream is = getClass().getClassLoader().getResourceAsStream("/eventData.txt");
			BufferedReader br = new BufferedReader(new InputStreamReader(is));
			String line = br.readLine();
			while (line != null) {
				sb.append(line);
				line = br.readLine();
				JSONObject jobj = new JSONObject(sb.toString());

				FileJson fileJsonObj = new FileJson();
				fileJsonObj.setEventName(jobj.getString("eventName"));
				fileJsonObj.setEventType(jobj.getString("eventType"));
				fileJsonObj.setUserId(jobj.getString("userId"));
				fileJsonObj.setGender(jobj.getString("gender"));
				fileJsonObj.setTs(jobj.getString("ts"));
				ls.add(fileJsonObj);

				sb.setLength(0);
			}
			br.close();
			eventsObj.setEvents(ls);
		} catch (Exception e) {
			e.printStackTrace();
		}
		return eventsObj;
	}

	public HashMap<String, List<FileJson>> initializeHashMap(Events eventsObj) {
		List<FileJson> eventsList = eventsObj.getEvents();
		HashMap<String, List<FileJson>> hMapPerUser = new HashMap<>();
		for (int i = 0; i < eventsList.size(); i++) {
			FileJson jsonObj = eventsList.get(i);
			List<FileJson> eventsListAsPerUser = new ArrayList<>();
			String userId = jsonObj.getUserId();
			if (hMapPerUser.get(userId) != null) {
				eventsListAsPerUser = hMapPerUser.get(userId);
				eventsListAsPerUser.add(jsonObj);
				hMapPerUser.put(userId, eventsListAsPerUser);
			} else {
				eventsListAsPerUser.add(jsonObj);
				hMapPerUser.put(userId, eventsListAsPerUser);
			}
		}
		
		return hMapPerUser;
	}
}
