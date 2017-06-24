package ke.co.debechlabs.besure;

import android.content.Intent;
import android.os.Bundle;
import android.support.design.widget.FloatingActionButton;
import android.support.design.widget.Snackbar;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.Toolbar;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.CheckBox;
import android.widget.EditText;
import android.widget.RadioButton;
import android.widget.RadioGroup;
import android.widget.Toast;

import com.android.volley.Request;
import com.android.volley.RequestQueue;
import com.android.volley.Response;
import com.android.volley.VolleyError;
import com.android.volley.toolbox.StringRequest;
import com.android.volley.toolbox.Volley;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.HashMap;
import java.util.Map;

public class Survey extends AppCompatActivity {

    private static final String TAG = "MyActivity";

    EditText etAge,etComments;
    CheckBox cbOral,cbBlood;
    RadioGroup rgGender;
    Button btnSave,btnCancel;
    RadioButton rbChecked;

    String gender = "0";

    boolean oralChecked;
    boolean bloodChecked;

    ArrayList<Integer> mylist = new ArrayList<Integer>();


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_survey);
        Toolbar toolbar = (Toolbar) findViewById(R.id.toolbar);
        setSupportActionBar(toolbar);

        etAge = (EditText) findViewById(R.id.etAge);
        etComments = (EditText) findViewById(R.id.etComments);
        cbOral = (CheckBox) findViewById(R.id.cbOral);
        cbBlood = (CheckBox) findViewById(R.id.cbBlood);
        rgGender = (RadioGroup) findViewById(R.id.rgGender);
        btnSave = (Button) findViewById(R.id.btnSave);
        btnCancel = (Button) findViewById(R.id.btnCancel);

        SaveClicked();
        CancelClicked();
    }

    public void SaveClicked(){
        btnSave.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

                sendSurvey();
            }
        });
    }

    public void CancelClicked(){
        btnCancel.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent surveyintent = new Intent(getApplicationContext(), MainActivity.class);
                startActivity(surveyintent);
            }
        });
    }

    private void sendSurvey(){
        String survey_url = "http://besure.co.ke/Home/Survey/";
        final String age = etAge.getText().toString().trim();
        final String comments = etComments.getText().toString().trim();

        int checkedGender = rgGender.getCheckedRadioButtonId();

        rbChecked = (RadioButton) findViewById(checkedGender);


        final String kit1 = cbOral.getText().toString().trim();
        final String kit2 = cbBlood.getText().toString().trim();


        if(rbChecked.getText().equals("Male") ){
            gender = String.valueOf('1');
        }else if(rbChecked.getText().equals("Female")){
            gender = String.valueOf('2');
        }else{
            gender = String.valueOf('0');
        }

        oralChecked = ((CheckBox) findViewById(R.id.cbOral)).isChecked();
        bloodChecked = ((CheckBox) findViewById(R.id.cbBlood)).isChecked();

        StringRequest stringRequest = new StringRequest(Request.Method.POST, survey_url,
                new Response.Listener<String>() {
                    @Override
                    public void onResponse(String response) {
                        Toast.makeText(Survey.this,response,Toast.LENGTH_LONG).show();
                    }
                },
                new Response.ErrorListener() {
                    @Override
                    public void onErrorResponse(VolleyError error) {
                        Toast.makeText(Survey.this,error.toString(),Toast.LENGTH_LONG).show();
                    }
                }){
            @Override
            protected Map<String,String> getParams(){
                Map<String, String> params = new HashMap<>();



//                Toast.makeText(Survey.this,"reaching", Toast.LENGTH_SHORT).show();
                if(oralChecked && bloodChecked){

                    Log.e(TAG,"reached_both");
                    for(int i=0;i<=1;i++)
                    {


                        mylist.add(i);


                    }
                    Log.e(TAG, String.valueOf(mylist.get(0)));
                    Log.e(TAG, String.valueOf(mylist.get(1)));
                    System.out.println("Data is "+ mylist.get(1));
                }else if(oralChecked){

                    Log.e(TAG,"reached_oral");

                    mylist.add(0);
                    Log.e(TAG, String.valueOf(mylist.get(0)));

                }else if(bloodChecked){

                    Log.e(TAG,"reached_blood");

                    mylist.add(1);
                    Log.e(TAG, String.valueOf(mylist.get(0)));

                }else{
                    Log.e(TAG,"reached_none");
                }

                int[] array = new int[mylist.size()];
                for(int i = 0; i < mylist.size(); i++){
                    array[i] = mylist.get(i);
                }

                params.put("age",age);
                params.put("comments",comments);
                params.put("gender", String.valueOf(Integer.parseInt(gender)));

                int m=0;
                for(int object: array){
                    params.put("kit["+(m++)+"]", String.valueOf(object));
                }
                return params;
            }


        };
        RequestQueue requestQueue = Volley.newRequestQueue(this);
        requestQueue.add(stringRequest);
    }

}
