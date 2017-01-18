package com.example.juanlabrador.sampleengine;

import android.content.Context;
import android.os.AsyncTask;
import android.util.Log;
import android.widget.Toast;

import com.example.juanlabrador.myapplication.backend.endpoints.quoteApi.QuoteApi;
import com.example.juanlabrador.myapplication.backend.endpoints.quoteApi.model.Quote;
import com.google.api.client.extensions.android.http.AndroidHttp;
import com.google.api.client.extensions.android.json.AndroidJsonFactory;
import com.google.api.client.googleapis.services.AbstractGoogleClientRequest;
import com.google.api.client.googleapis.services.GoogleClientRequestInitializer;

import java.io.IOException;

/**
 * Created by juanlabrador on 11/01/17.
 */

class InsertAsyncTask extends AsyncTask<Quote, Void, String> {

    private static final String TAG = "InsertAsyncTask";
    private static QuoteApi myApiService = null;
    private MainActivity context;

    InsertAsyncTask(Context context) {
        this.context = (MainActivity) context;
    }

    @Override
    protected String doInBackground(Quote... params) {
        if(myApiService == null) {  // Only do this once
            QuoteApi.Builder builder = new QuoteApi.Builder(AndroidHttp.newCompatibleTransport(),
                    new AndroidJsonFactory(), null)
                    // options for running against local devappserver
                    // - 10.0.2.2 is localhost's IP address in Android emulator
                    // - turn off compression when running against local devappserver
                    //.setRootUrl("http://10.0.2.2:8080/_ah/api/")
                    .setRootUrl("https://sampleengine-155320.appspot.com/_ah/api/")
                    .setGoogleClientRequestInitializer(new GoogleClientRequestInitializer() {
                        @Override
                        public void initialize(AbstractGoogleClientRequest<?> abstractGoogleClientRequest) throws IOException {
                            abstractGoogleClientRequest.setDisableGZipContent(true);
                        }
                    });
            // end options for devappserver

            myApiService = builder.build();
        }

        try {
            Quote quotes = myApiService.insertQuote(params[0]).execute();

            if (quotes != null) {
                return "Success";
            } else {
                return "Failed";
            }
        } catch (IOException e) {
            Log.e(TAG, "API: " + e.getMessage());
            return "Failed";
        }
    }

    @Override
    protected void onPostExecute(String result) {
        Toast.makeText(context, result, Toast.LENGTH_SHORT).show();
        new AllAsyncTask(context).execute();
    }
}