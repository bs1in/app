package de.bs1.inventarisierungsapp.tools;

import java.io.IOException;
import java.net.HttpURLConnection;
import java.net.URL;
import org.json.*;

public class HttpRequest {

    public void getJson(String id) throws IOException {
        String idUrl = "defaultHostname/api/devices/:" + id;
        HttpURLConnection httpCon;

        try {
            URL url = new URL(idUrl);
            httpCon = (HttpURLConnection) url.openConnection();
            httpCon.setRequestMethod("GET");

            httpCon.setConnectTimeout(5000);
            httpCon.setReadTimeout(5000);



            JSONObject jsonOb = new JSONObject();
            //JSON Parse form HTTP

        } catch (Exception e) {
           throw e;
        }
    }
}