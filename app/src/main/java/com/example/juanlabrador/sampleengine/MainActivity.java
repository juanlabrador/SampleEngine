package com.example.juanlabrador.sampleengine;

import android.app.AlertDialog;
import android.app.Dialog;
import android.content.DialogInterface;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.view.View;
import android.view.WindowManager;
import android.widget.Button;
import android.widget.EditText;

import com.example.juanlabrador.myapplication.backend.endpoints.quoteApi.model.Quote;

import java.util.List;

public class MainActivity extends AppCompatActivity {

    Button list;
    RecyclerView recyclerView;
    Button add;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);


        list = (Button) findViewById(R.id.button);
        add = (Button) findViewById(R.id.add);
        recyclerView = (RecyclerView) findViewById(R.id.list);
        recyclerView.setLayoutManager(new LinearLayoutManager(this));


        list.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                new AllAsyncTask(MainActivity.this).execute();
            }
        });


        add.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                addQuote();
            }
        });
    }

    public void updateAdapter(List<Quote> quotes) {
        Adapter adapter = new Adapter(this, quotes);
        recyclerView.setAdapter(adapter);
        adapter.notifyDataSetChanged();
    }


    void addQuote() {
        AlertDialog.Builder dialogBuilder = new AlertDialog.Builder(this);
        final View dialogView = getLayoutInflater().inflate(R.layout.custom_edittext, null);
        dialogBuilder.setView(dialogView);

        final EditText who = (EditText) dialogView.findViewById(R.id.who);
        final EditText what = (EditText) dialogView.findViewById(R.id.what);

        dialogBuilder.setTitle("Agregar");
        dialogBuilder.setPositiveButton("Guardar", new DialogInterface.OnClickListener() {
            public void onClick(DialogInterface dialog, int whichButton) {
                if (!who.getText().toString().trim().isEmpty() && !what.getText().toString().trim().isEmpty()) {

                    Quote quote = new Quote();
                    quote.setWho(who.getText().toString());
                    quote.setWhat(what.getText().toString());
                    new InsertAsyncTask(MainActivity.this).execute(quote);
                }
            }
        });
        dialogBuilder.setNegativeButton("Cancelar", new DialogInterface.OnClickListener() {
            public void onClick(DialogInterface dialog, int whichButton) {
                dialog.dismiss();
            }
        });

        Dialog dialog = dialogBuilder.create();
        dialog.getWindow().setSoftInputMode(WindowManager.LayoutParams.SOFT_INPUT_STATE_VISIBLE);
        dialog.show();
    }

    public void updatedQuote(final Quote quote) {
        AlertDialog.Builder dialogBuilder = new AlertDialog.Builder(this);
        final View dialogView = getLayoutInflater().inflate(R.layout.custom_edittext, null);
        dialogBuilder.setView(dialogView);

        final EditText who = (EditText) dialogView.findViewById(R.id.who);
        final EditText what = (EditText) dialogView.findViewById(R.id.what);

        who.setText(quote.getWho());
        what.setText(quote.getWhat());

        dialogBuilder.setTitle("Actualizar");
        dialogBuilder.setPositiveButton("Actualizar", new DialogInterface.OnClickListener() {
            public void onClick(DialogInterface dialog, int whichButton) {
                if (!who.getText().toString().trim().isEmpty() && !what.getText().toString().trim().isEmpty()) {

                    quote.setWho(who.getText().toString());
                    quote.setWhat(what.getText().toString());
                    new UpdateAsyncTask(MainActivity.this).execute(quote);
                }
            }
        });
        dialogBuilder.setNegativeButton("Cancelar", new DialogInterface.OnClickListener() {
            public void onClick(DialogInterface dialog, int whichButton) {
                dialog.dismiss();
            }
        });

        Dialog dialog = dialogBuilder.create();
        dialog.getWindow().setSoftInputMode(WindowManager.LayoutParams.SOFT_INPUT_STATE_VISIBLE);
        dialog.show();
    }
}
