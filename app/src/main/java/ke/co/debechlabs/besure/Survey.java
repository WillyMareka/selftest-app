package ke.co.debechlabs.besure;

import android.os.Bundle;
import android.support.design.widget.FloatingActionButton;
import android.support.design.widget.Snackbar;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.Toolbar;
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

import java.util.HashMap;
import java.util.Map;

public class Survey extends AppCompatActivity {

    EditText etAge,etComments;
    CheckBox cbOral,cbBlood;
    RadioGroup rgGender;
    Button btnSave,btnCancel;


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
    }

    public void SaveClicked(){
        btnSave.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                sendSurvey();
            }
        });
    }

    private void sendSurvey(){
        String survey_url = "http://besure.co.ke/Home/Survey/";
        final String age = etAge.getText().toString().trim();
        final String comments = etComments.getText().toString().trim();
        final String kit1 = cbOral.getText().toString().trim();
        final String kit2 = cbBlood.getText().toString().trim();

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
                Map<String,String> params = new HashMap<String, String>();
                params.put("age",age);
                params.put("comments",comments);
                params.put("gender", "male");
                return params;
            }

        };

        RequestQueue requestQueue = Volley.newRequestQueue(this);
        requestQueue.add(stringRequest);
    }

}
