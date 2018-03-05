package com.airtel.eventparser;


import java.util.ArrayList;
import java.util.Arrays;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.Set;

import javax.ws.rs.GET;
import javax.ws.rs.Path;
import javax.ws.rs.Produces;
import javax.ws.rs.QueryParam;
import javax.ws.rs.core.MediaType;

import com.airtel.startup.ApiStartUp;
import com.airtel.utils.DateUtils;
import com.google.gson.Gson;
import models.CategoryEvents;
import models.Events;
import models.FileJson;

@Path("app")
public class MyResource {
	private static Events eventsObj;
	private static HashMap<String, List<FileJson>> hMapPerUser;

	public MyResource() {
		ApiStartUp startup = new ApiStartUp();
		if (eventsObj == null)
			eventsObj = startup.initializeEvents();
		if (hMapPerUser == null)
			hMapPerUser = startup.initializeHashMap(eventsObj);
	}

	@GET
	@Path("/users")
	@Produces(MediaType.APPLICATION_JSON)
	public String getUniqueUsersDateSearch(@QueryParam("category") String category,
			@QueryParam("date") String timeStamp) {
		if (category != null && timeStamp != null) {
			return new Gson().toJson(getUniqueUsers(category, timeStamp));
		} else if (timeStamp != null) {
			return new Gson().toJson(getUniqueUsers("Payment", timeStamp));
		} else {
			return new Gson().toJson("Pass a date or category");
		}
	}

	@GET
	@Path("/users/visit")
	@Produces(MediaType.APPLICATION_JSON)
	public String getUniqueUsersVisitedWithInWeek() {
		List<String> userIds = new ArrayList<>();

		for (Map.Entry<String, List<FileJson>> entry : hMapPerUser.entrySet()) {
			String userId = entry.getKey();
			StringBuilder sb = new StringBuilder();
			for (FileJson obj : entry.getValue()) {
				String ts = obj.getTs();
				sb.append(ts + "\n");
			}
			if (isUserVisited3Times(sb.toString())) {
				userIds.add(userId);
			}
		}
		return new Gson().toJson(userIds);
	}

	@GET
	@Path("/users/conversionper")
	@Produces(MediaType.APPLICATION_JSON)
	public String getConversionPercentage(@QueryParam("date") String timeStamp) {
		if (timeStamp != null) {
			Set<String> paymentUser = getUniqueUsers("Payment", timeStamp);
			int numberOfPaymentUsers = paymentUser.size();
			Set<String> allUniqueUsers = hMapPerUser.keySet();
			int numberOfAllUniqueUsers = allUniqueUsers.size();

			float percentage = (numberOfPaymentUsers * 100f) / (numberOfAllUniqueUsers);
			return new Gson().toJson("Conversion Percentage: "+percentage);
		} else
			return new Gson().toJson("Pass a TimeStamp value");
	}

	public boolean isUserVisited3Times(String tsDate) {
		String[] dates = tsDate.split("\n");
		Arrays.sort(dates);
		int count = 1;
		for (int i = 0; i < dates.length-1;i++) {
			if (!dates[i].equals(dates[i + 1])) {
				if (DateUtils.isDatesInaWeek(dates[i], dates[i + 1])) {
					count++;
				}
			}
			if (count >= 3) {
				return true;
			}
		}
		return false;
	}

	@GET
	@Path("/categoryevents")
	@Produces(MediaType.APPLICATION_JSON)
	public String convertJSONToObject() {
		CategoryEvents cEvents = new CategoryEvents();
		Set<String> eventsNameList = null;
		HashMap<String, Integer> hMap = new HashMap<>();
		try {
			List<FileJson> eventsList = eventsObj.getEvents();
			for (int i = 0; i < eventsList.size(); i++) {
				FileJson jsonObj = eventsList.get(i);
				String eventName = jsonObj.getEventName();
				if (hMap.get(eventName) != null) {
					hMap.put(eventName, hMap.get(eventName) + 1);
				} else
					hMap.put(eventName, 1);
			}
			eventsNameList = hMap.keySet();
		} catch (Exception e) {
			e.printStackTrace();
		}
		cEvents.setEventsName(eventsNameList);
		return new Gson().toJson(cEvents);
	}

	public Set<String> getUniqueUsers(String category, String timeStamp) {
		List<FileJson> eventsList = eventsObj.getEvents();
		HashMap<String, Integer> hMap = new HashMap<>();
		for (int i = 0; i < eventsList.size(); i++) {
			FileJson jsonObj = eventsList.get(i);
			if (jsonObj.getEventType().contains(category) && jsonObj.getTs().equals(timeStamp)) {
				String userId = jsonObj.getUserId();
				if (hMap.get(userId) != null) {
					hMap.put(userId, hMap.get(userId) + 1);
				} else
					hMap.put(userId, 1);
			}
		}
		return hMap.keySet();
	}
}
