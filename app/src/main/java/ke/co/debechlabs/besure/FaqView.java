package ke.co.debechlabs.besure;

import android.support.design.widget.BottomNavigationView;
import android.support.v7.app.ActionBar;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.support.v7.widget.Toolbar;
import android.widget.LinearLayout;
import android.widget.TextView;
import android.widget.Toast;

public class FaqView extends AppCompatActivity {
    TextView faqImage, faqID, faqQuestion, faqAnswer, faqStatus;

    private Toolbar toolbar;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_faq_view);

        toolbar= (Toolbar) findViewById(R.id.toolbar);
        setSupportActionBar(toolbar);

        toolbar.setTitle("FAQs");

        ActionBar actionBar = getSupportActionBar();
        actionBar.setHomeButtonEnabled(true);
        actionBar.setDisplayHomeAsUpEnabled(true);

        faqImage = (TextView) findViewById(R.id.faqImage);
        faqQuestion = (TextView) findViewById(R.id.faqQuestion);
        faqAnswer = (TextView) findViewById(R.id.faqAnswer);

        Bundle extras = getIntent().getExtras();
        String faqid = extras.getString("faqid");
        String question = extras.getString("question");
        String answer = extras.getString("answer");
        String image = extras.getString("image");
        String status = String.valueOf(extras.getInt("status"));

//        Toast.makeText(getApplicationContext(),question,Toast.LENGTH_LONG).show();

        faqImage.setText(image);
        faqQuestion.setText(question);
        faqAnswer.setText(answer);


    }
}
