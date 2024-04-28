package com.sydney.recipemanagaer.networking;

import android.content.Context;
import com.android.volley.RequestQueue;
import com.android.volley.toolbox.Volley;

public class NetworkingClient {
    private static NetworkingClient instance;
    private RequestQueue requestQueue;
    private static Context applicationContext; // Rename to applicationContext for clarity

    private NetworkingClient(Context context) {
        // Use the application context to avoid memory leaks
        applicationContext = context.getApplicationContext();
        requestQueue = getRequestQueue();
    }

    public static synchronized NetworkingClient getInstance(Context context) {
        if (instance == null) {
            // Use application context to ensure that the singleton instance is not tied to any specific activity
            instance = new NetworkingClient(context);
        }
        return instance;
    }

    public RequestQueue getRequestQueue() {
        if (requestQueue == null) {
            // Initialize the request queue only once
            requestQueue = Volley.newRequestQueue(applicationContext);
        }
        return requestQueue;
    }
}
