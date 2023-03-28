package com.in28minutes.springboot.springbootwebapp.fb;

import lombok.RequiredArgsConstructor;
import org.json.JSONException;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.net.HttpURLConnection;
import java.net.URL;
import org.json.JSONArray;
import org.json.JSONObject;

@RestController
@RequestMapping("fb-events")
@RequiredArgsConstructor
public class EventsController {
    private static final String ACCESS_TOKEN = "EABZAeArheW7cBAHABOSTAM8JNXXMebNEB0YvJwBtRoZCuYYdQE8mZCjrUZC0lDSZCdZCOhhc11vEFDkOELn2tAkPnHm0qotsZB9RXHdQTe8RqQFpo8bzGLvD76x5Hh3oxKdv5iKrdhfQNJTrlFA2hDDOeIG3MCa3oSfspq9g2IvvqBT7I3xf1pZAzVrlHX2QcWZBaRjVZC1137fMMMPM4oQUw0";

    @GetMapping
    void test() throws IOException, JSONException {
        String query = "coffee";
        double latitude = 47.0667; // Oradea
        double longitude = 21.9333;
        int distance = 10000; // 10km

        // First, get a list of all public pages within the specified location
        String pageUrl = String.format("https://graph.facebook.com/v16.0/search?type=page&q=%s&center=%f,%f&distance=%d&access_token=%s",
                query, latitude, longitude, distance, ACCESS_TOKEN);

        HttpURLConnection pageConnection = (HttpURLConnection) new URL(pageUrl).openConnection();
        pageConnection.setRequestMethod("GET");

        BufferedReader pageReader = new BufferedReader(new InputStreamReader(pageConnection.getInputStream()));
        StringBuilder pageResponse = new StringBuilder();
        String pageLine;
        while ((pageLine = pageReader.readLine()) != null) {
            pageResponse.append(pageLine);
        }
        pageReader.close();

        JSONObject pageJson = new JSONObject(pageResponse.toString());
        JSONArray pages = pageJson.getJSONArray("data");

        System.out.println(pages);
    }
}
