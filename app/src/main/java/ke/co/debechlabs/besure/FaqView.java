package ke.co.debechlabs.besure;

import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;

public class FaqView extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_faq_view);

        Bundle extras = getIntent().getExtras();
        String question = extras.getString("question");
    }
}
