package com.springboot.blog.httprequestservice;

import org.springframework.stereotype.Service;
import java.io.BufferedReader;
import java.io.InputStreamReader;
import java.net.HttpURLConnection;
import java.net.URL;

@Service
public class HttpRequestService {

    public static void makeGetRequest(String url) {
        try {
            // URL endpoint to send the GET request

            URL obj = new URL(url);

            // Open connection
            HttpURLConnection con = (HttpURLConnection) obj.openConnection();

            // Set GET request method
            con.setRequestMethod("GET");

            // Read response
            BufferedReader in = new BufferedReader(new InputStreamReader(con.getInputStream()));
            String inputLine;
            StringBuffer response = new StringBuffer();
            while ((inputLine = in.readLine()) != null) {
                response.append(inputLine);
            }
            in.close();

            // Print response
            System.out.println("Response:");
            System.out.println(response.toString());
        } catch (Exception e) {
            e.printStackTrace();
        }
    }
}
