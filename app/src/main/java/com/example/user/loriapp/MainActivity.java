package com.example.user.loriapp;

import android.content.Context;
import android.os.Bundle;
import android.support.design.widget.FloatingActionButton;
import android.support.design.widget.Snackbar;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.support.v7.widget.Toolbar;
import android.view.LayoutInflater;
import android.view.View;
import android.view.Menu;
import android.view.MenuItem;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.LinearLayout;
import android.widget.Spinner;
import android.widget.TextView;


import java.io.*;

import java.util.ArrayList;
import java.util.List;

import retrofit.Call;
import retrofit.Callback;
import retrofit.Response;
import retrofit.Retrofit;
import retrofit.http.Body;
import retrofit.http.GET;
import retrofit.http.Headers;
import retrofit.http.POST;
import retrofit.http.Path;

public class MainActivity extends AppCompatActivity {

    List<String> data = new ArrayList<String>();
    Context context;
    //ArrayAdapter<String> adapter;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        Toolbar toolbar = (Toolbar) findViewById(R.id.toolbar);
        setSupportActionBar(toolbar);
        context = MainActivity.this;

        Button buttonAdd = (Button)findViewById(R.id.buttonAdd);
        buttonAdd.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {

            }
        });

        for (int i = 0; i < 20; i++) {
            data.add("Item " + i);
        }
        ////////////////////////////////////////////////////////////

        RecyclerView recyclerView = (RecyclerView) findViewById(R.id.recycler);
        recyclerView.setAdapter(new Adapter());
        recyclerView.setLayoutManager(new LinearLayoutManager(this));
        ////////////////////////////////////////////////////////////


        TextView textTest = (TextView)findViewById(R.id.textTest);


        Button butTest = (Button)findViewById(R.id.button_test);
        butTest.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {

                Retrofit retrofit = new Retrofit.Builder().baseUrl("http://localhost:8080").build();
                API api = retrofit.create(API.class);

                RegistrationBody body = new RegistrationBody();
                body.login = "admin";
                body.password = "admin";

                //api.registerUser(body);

                Call<Object> call = api.registerUser(body);
                try {
                    Response<Object> resp = call.execute();
                } catch (IOException e) {
                    e.printStackTrace();
                }
                /*call.enqueue(new Callback<RegistrationResponse>() {
                    @Override
                    public void onResponse(Response<RegistrationResponse> response) {
                        if (response.isSuccess()) {
                            // tasks available
                        } else {
                            // error response, no access to resource?
                        }
                    }

                    @Override
                    public void onFailure(Throwable t) {
                    }
                });*/
            }
        });

    }

    public interface API {
        @Headers("Content-Type:application/json")
        @POST("/app/dispatch/api")
        Call<Object> registerUser(@Body RegistrationBody registrationBody);
    }

    public class RegistrationBody{
        public String login;
        public String password;
    }

    public class RegistrationResponse {
        public String token;
    }

    ///////////////////////////////////////////////////////////////////////////////////

    class ViewHolder extends RecyclerView.ViewHolder {//implements View.OnClickListener{

        TextView tv;
        Spinner spinnerProject;

        public ViewHolder(View itemView) {
            super(itemView);
            //ll = (LinearLayout) itemView.findViewById(R.id.ll);
            tv = (TextView) itemView.findViewById(R.id.tv);
            spinnerProject = (Spinner)itemView.findViewById(R.id.spinner_project);
        }

        /*
        @Override
        public void onClick(View v) {

        }*/
    }

    class Adapter extends RecyclerView.Adapter<ViewHolder>{

        @Override
        public ViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
            return new ViewHolder(View.inflate(parent.getContext(), R.layout.list_item, null));
        }

        @Override
        public void onBindViewHolder(ViewHolder holder, int position) {
            String s = data.get(position);
            holder.tv.setText(s);


            ArrayAdapter<String> adapter = new ArrayAdapter<String>(holder.itemView.getContext(),android.R.layout.simple_spinner_item,new String[]{"r","t"});
            adapter.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);

            holder.spinnerProject.setAdapter(adapter);
;
        }



        @Override
        public int getItemCount() {
            return data.size();
        }
    }


    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        // Inflate the menu; this adds items to the action bar if it is present.
        getMenuInflater().inflate(R.menu.menu_main, menu);
        return true;
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        // Handle action bar item clicks here. The action bar will
        // automatically handle clicks on the Home/Up button, so long
        // as you specify a parent activity in AndroidManifest.xml.
        int id = item.getItemId();

        //noinspection SimplifiableIfStatement
        if (id == R.id.action_settings) {
            return true;
        }

        return super.onOptionsItemSelected(item);
    }
}
