package de.bs1.inventarisierungsapp;

import android.util.JsonReader;

import org.json.JSONException;
import org.json.JSONObject;

import java.io.IOException;

import de.bs1.inventarisierungsapp.tools.HttpRequest;

public class Ticket {
    //Needed Variables
    public String id;
    public String device;
    public String description;
    public boolean done;


    /**
    *
    */
    public static Ticket createTicket (Ticket ticket) throws JSONException, IOException {
        JSONObject jsonObject = new JSONObject();

        toJson(jsonObject, ticket);

        jsonObject = HttpRequest.ajax("POST", "/api/tickets", jsonObject);

        fromJson(jsonObject, ticket);

        return ticket;

    }

    public static Ticket getTicket (String id) throws IOException, JSONException {
        JSONObject jsonObject = HttpRequest.ajax("GET", "/api/tickets/" + id, null);

        Ticket ticket = new Ticket();

       fromJson(jsonObject, ticket);

        return ticket;
    }

    private static void fromJson (JSONObject jsonObject, Ticket ticket) throws JSONException {
        ticket.id = jsonObject.getString("id");
        ticket.device = jsonObject.getString("device");
        ticket.description = jsonObject.getString("description");
        ticket.done = jsonObject.getBoolean("done");
    }


    private static void toJson (JSONObject jsonObject, Ticket ticket) throws JSONException {
        jsonObject.put("id", ticket.id);
        jsonObject.put("device", ticket.device);
        jsonObject.put("description", ticket.description);
        jsonObject.put("done", ticket.done);
    }
}
