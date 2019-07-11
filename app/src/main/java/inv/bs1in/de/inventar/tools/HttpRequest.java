package inv.bs1in.de.inventar.tools;

import org.json.JSONException;
import org.json.JSONObject;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.net.HttpURLConnection;
import java.net.URL;

public class HttpRequest {

    private static final String ENDPOINT = "http://10.244.0.130:8080";

    public static JSONObject ajax(String Methode, String idUrl, JSONObject jsonObject) throws IOException, JSONException {
        //String idUrl = "http://localhost/api/devices/" + id;
        //Initializing Variable
        HttpURLConnection httpCon;


        URL url = new URL( HttpRequest.ENDPOINT + idUrl);
        httpCon = (HttpURLConnection) url.openConnection();
        httpCon.setRequestMethod(Methode);

        //Setting the Connection and the Read Timeout
        httpCon.setConnectTimeout(5000);
        httpCon.setReadTimeout(5000);

        if (jsonObject != null) {
            //Setting Content type of http to json
            httpCon.addRequestProperty("content-type", "application/json");
            String json = jsonObject.toString();

            //Calculating json length in bytes
            byte[] bytes = json.getBytes("UTF-8");
            httpCon.setRequestProperty("content-length", Integer.toString(bytes.length));

            //Saving the transformed json into the httpConnection
            httpCon.getOutputStream().write(bytes);
        }

        //Opens the connection to the server
        httpCon.connect();

        //Gathers output of the http connection
        BufferedReader in = new BufferedReader(new InputStreamReader(httpCon.getInputStream()));
        String inputLine;
        StringBuilder content = new StringBuilder();
        while ((inputLine = in.readLine()) != null) {
            content.append(inputLine);
        }
        in.close();

        //Saves the input type of the answer from the server
        String contentType = httpCon.getHeaderField("content-type");

        //Closes the connection to the server
        httpCon.disconnect();


        //Checks if the content type of the answer is a json
        if (contentType.equalsIgnoreCase("application/json")) {

            JSONObject jsonOb = new JSONObject(content.toString());
            return jsonOb;

        }

        else {
            return null;
        }

    }
}