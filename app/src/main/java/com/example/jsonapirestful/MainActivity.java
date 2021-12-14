package com.example.jsonapirestful;

import androidx.appcompat.app.AppCompatActivity;

import android.os.Bundle;
import android.widget.TextView;

import com.android.volley.AuthFailureError;
import com.android.volley.Request;
import com.android.volley.RequestQueue;
import com.android.volley.Response;
import com.android.volley.VolleyError;
import com.android.volley.toolbox.JsonObjectRequest;
import com.android.volley.toolbox.Volley;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.util.HashMap;
import java.util.Map;

public class MainActivity extends AppCompatActivity{

    private TextView resultText;
    private RequestQueue queue;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        resultText = findViewById(R.id.txtvResult);
        queue = Volley.newRequestQueue(this);

        jsonGet();
    }

    private void jsonGet(){
        String url = "https://gorest.co.in/public/v1/comments";
        JsonObjectRequest requestJson = new JsonObjectRequest(Request.Method.GET, url, null,
                new Response.Listener<JSONObject>() {
                    @Override
                    public void onResponse(JSONObject response) {
                        try {
                            JSONArray jsonArray = response.getJSONArray("data");

                            for (int i=0 ; i < jsonArray.length(); i++){
                                //Lo Guardo en un objeto el arreglo para obtener sus parámetros
                                JSONObject objJson=new JSONObject(jsonArray.get(i).toString());

                                resultText.append("ID: " + objJson.getString("id") + "\n\n");
                                resultText.append("Nombre: " + objJson.getString("name") + "\n\n");
                                resultText.append("Email: " + objJson.getString("email") + "\n\n");
                                resultText.append("Body: " + objJson.getString("body") + "\n");
                                resultText.append("───────────────────────\n\n");
                            }

                        } catch (JSONException e){
                            e.printStackTrace();
                        }
                    }
                },
                new Response.ErrorListener() {
                    @Override
                    public void onErrorResponse(VolleyError error) {
                        error.printStackTrace();
                    }
                }
        ){
            /** Passing some request headers**/

            public Map getHeaders() throws AuthFailureError{
                HashMap headers = new HashMap();
                headers.put("Content-Type", "application/json");
                headers.put("Authorization", "Bearer YOUR_ACCESS_TOKEN");
                return headers;
            }
        };
        queue.add(requestJson);
    }
}