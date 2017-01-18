package com.example.juanlabrador.sampleengine;

import android.content.Context;
import android.os.AsyncTask;
import android.util.Log;

import com.example.juanlabrador.myapplication.backend.endpoints.quoteApi.model.Quote;
import com.example.juanlabrador.myapplication.backend.endpoints.quoteApi.QuoteApi;
import com.google.api.client.extensions.android.http.AndroidHttp;
import com.google.api.client.extensions.android.json.AndroidJsonFactory;
import com.google.api.client.googleapis.services.AbstractGoogleClientRequest;
import com.google.api.client.googleapis.services.GoogleClientRequestInitializer;

import java.io.IOException;
import java.util.Collections;
import java.util.List;

/**
 * Created by juanlabrador on 11/01/17.
 */

class AllAsyncTask extends AsyncTask<Void, Void, List<Quote>> {

    private static final String TAG = "AllAsyncTask";
    private static QuoteApi myApiService = null;
    private MainActivity context;

    AllAsyncTask(Context context) {
        this.context = (MainActivity) context;
    }

    @Override
    protected List<Quote> doInBackground(Void... params) {
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
            List<Quote> quotes = myApiService.listQuote().execute().getItems();

            if (quotes != null) {
                return quotes;
            } else {
                return Collections.EMPTY_LIST;
            }
        } catch (IOException e) {
            Log.e(TAG, "API: " + e.getMessage());
            return Collections.EMPTY_LIST;
        }
    }

    @Override
    protected void onPostExecute(List<Quote> result) {
        context.updateAdapter(result);
    }
}