package ke.co.debechlabs.besure;

import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.os.AsyncTask;
import android.support.design.widget.BottomNavigationView;
import android.support.v7.app.ActionBar;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.support.v7.widget.Toolbar;
import android.util.Log;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.TextView;
import android.widget.Toast;

import java.io.IOException;
import java.io.InputStream;
import java.net.HttpURLConnection;
import java.net.URL;

public class FaqView extends AppCompatActivity {
    TextView txtfaqImage, faqID, faqQuestion, faqAnswer, faqStatus;
    ImageView faqImage;
    String image = "";

    private Toolbar toolbar;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_faq_view);

        toolbar = (Toolbar) findViewById(R.id.toolbar);
        setSupportActionBar(toolbar);

        toolbar.setTitle("FAQs");

        ActionBar actionBar = getSupportActionBar();
        actionBar.setHomeButtonEnabled(true);
        actionBar.setDisplayHomeAsUpEnabled(true);

//        faqImage = (TextView) findViewById(R.id.faqImage);
        faqImage = (ImageView) findViewById(R.id.faqImage);
        faqQuestion = (TextView) findViewById(R.id.faqQuestion);
        faqAnswer = (TextView) findViewById(R.id.faqAnswer);

        Bundle extras = getIntent().getExtras();
        String faqid = extras.getString("faqid");
        String question = extras.getString("question");
        String answer = extras.getString("answer");
        image = extras.getString("image");
        String status = String.valueOf(extras.getInt("status"));

//        Toast.makeText(getApplicationContext(),question,Toast.LENGTH_LONG).show();

//        faqImage.setText(image);
        faqQuestion.setText(question);
        faqAnswer.setText(answer);

//        getBitmapFromURL(image);
        new ImageLoadTask().execute();
//        faqImage.setImageBitmap(getBitmapFromURL(image));

    }


    public class ImageLoadTask extends AsyncTask<Void, Void, Bitmap> {

        private String url;
        private ImageView imageView;

        public ImageLoadTask() {
            this.url = url;
            this.imageView = imageView;
        }

        @Override
        protected Bitmap doInBackground(Void... params) {
            try {
                URL urlConnection = new URL(image);
                HttpURLConnection connection = (HttpURLConnection) urlConnection
                        .openConnection();
                connection.setDoInput(true);
                connection.connect();
                InputStream input = connection.getInputStream();
                Bitmap myBitmap = BitmapFactory.decodeStream(input);
                return myBitmap;
            } catch (Exception e) {
                e.printStackTrace();
            }
            return null;
        }

        @Override
        protected void onPostExecute(Bitmap result) {
            super.onPostExecute(result);
            faqImage.setImageBitmap(result);
        }

    }


}



