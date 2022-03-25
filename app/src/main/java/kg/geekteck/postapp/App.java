package kg.geekteck.postapp;

import android.app.Application;

import kg.geekteck.postapp.data.remote.PostApi;
import kg.geekteck.postapp.data.remote.RetrofitClient;

public class App extends Application {

    private RetrofitClient retrofitClient;
    public static PostApi api;

    @Override
    public void onCreate() {
        super.onCreate();
        retrofitClient = new RetrofitClient();
        api= retrofitClient.createApi();
    }
}
