package com.example.ashrafulhassan.mdmfly;

import android.content.Context;
import android.net.Uri;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.util.Log;
import android.widget.TextView;
import android.widget.Toast;

import com.android.volley.Request;
import com.android.volley.RequestQueue;
import com.android.volley.Response;
import com.android.volley.VolleyError;
import com.android.volley.toolbox.JsonArrayRequest;
import com.example.ashrafulhassan.mdmfly.model.EmployeeInfo;
import com.example.ashrafulhassan.mdmfly.networks.VolleySingleton;

import org.flyve.inventory.InventoryTask;
import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import com.example.ashrafulhassan.mdmfly.keys.*;

import java.util.ArrayList;
import java.util.List;

public class MainActivity extends AppCompatActivity {

    TextView showLOG, showLOGSc;
    static String sLog="please click the link first from your inbox";

    //For server
    private RequestQueue requestQueue;
    private static MainActivity sInstance;
    private VolleySingleton volleySingleton;

    private ArrayList<EmployeeInfo> employeeInfos = new ArrayList<>();

    public static Context getAppContext() {
        return sInstance.getApplicationContext();
    }

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        sInstance = this; //for volley request

        showLOG = (TextView) findViewById(R.id.showLOG);  showLOGSc = (TextView) findViewById(R.id.showLOGSc);
        //coolExecution();

        //http://www.coolme.com/mobile/hassan  extractng data from link

        Uri data = getIntent().getData();
        if (data != null){
            String scheme = data.getScheme(); // "http"
            String host = data.getHost(); // "coolme.com"
            List<String> params = data.getPathSegments();
            String first = params.get(0); // "mobile"
            String second = params.get(1); // "hassan"
            sLog = "scheme: " + scheme + "\n host: " + host + "\n first: " + first + "\n second: " + second;
        }


        showLOGSc.setText(sLog);

        callingHOST();
       // printDemo();

    }



    //getting data from server
    public void callingHOST(){

        volleySingleton = volleySingleton.getInstance();  requestQueue = volleySingleton.getRequestQueue();

        JsonArrayRequest jsonArrayRequest = new JsonArrayRequest(Request.Method.GET, ServerLink.URL_GET_EMPLOYEE_DATA, null, new Response.Listener<JSONArray>() {
            @Override
            public void onResponse(JSONArray response) {
                parseJsonResponse(response); //parsing json data

                Log.d("$%#$%#$%#%#   ","res:  "+response);

            }
        }, new Response.ErrorListener() {
            @Override
            public void onErrorResponse(VolleyError error) {
                Toast.makeText(getAppContext(), "Error connection", Toast.LENGTH_SHORT).show();
            }
        });
        requestQueue.add(jsonArrayRequest);

    }

    //parsing json respone
    private void parseJsonResponse(JSONArray array) {
      //  Log.d("%%%%%  ",""+array);
        if (array == null || array.length() == 0){return;}

        for (int i = 0; i<array.length(); i++){
            EmployeeInfo employeeInfo = new EmployeeInfo();
            JSONObject jsonObject = null;

            try {
                jsonObject = array.getJSONObject(i);

                //Adding them to EmployeeInof object
                employeeInfo.setName(jsonObject.getString(ServerLink.KEY_NAME));
                employeeInfo.setAge(jsonObject.getInt(ServerLink.KEY_AGE));
                employeeInfo.setEmail(jsonObject.getString(ServerLink.KEY_EMAIL));

              //  Log.d("value77777  ", ""+employeeInfo.getEmail());

            }catch (JSONException e){
                e.printStackTrace();
            }

            //adding employee object to list
            employeeInfos.add(employeeInfo);
        }
        printDemo(employeeInfos);
        Log.d("first5555 ", ""+employeeInfos.get(0).getName()+"77777777"+employeeInfos.get(1).getName());
    }


    private void coolExecution(){
        InventoryTask inventoryTask = new InventoryTask(MainActivity.this, "");
        inventoryTask.getJSON(new InventoryTask.OnTaskCompleted() {
            @Override
            public void onTaskSuccess(String s) {
                sLog = s;
                Log.d("SHOWING%%%%", sLog);
            }

            @Override
            public void onTaskError(Throwable throwable) {
                Log.d("SHOWING%%%%   ", "EROOR *******************************");
            }
        });
    }

    private void printDemo(ArrayList<EmployeeInfo> employeeInfos){
        String s = "";
        for (EmployeeInfo employeeInfo : employeeInfos){
            s += " ..... "+employeeInfo.getName()+ "  " + employeeInfo.getEmail() + "   "+employeeInfo.getAge()+"\n";
        }
        showLOG.setText(s);
        Log.d("@@@@@ showing ", ""+employeeInfos.get(0).getName());
    }
}
