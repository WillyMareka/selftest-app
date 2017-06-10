package ke.co.debechlabs.besure.adapter;

import android.app.Activity;
import android.content.Context;
import android.content.Intent;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.AdapterView;
import android.widget.BaseAdapter;
import android.widget.LinearLayout;
import android.widget.ListView;
import android.widget.TextView;
import android.widget.Toast;

import java.util.List;

import ke.co.debechlabs.besure.FaqView;
import ke.co.debechlabs.besure.R;
import ke.co.debechlabs.besure.models.Faqs;

/**
 * Created by Marewill on 6/8/2017.
 */

public class FaqListAdapter extends BaseAdapter {
    Activity activity;
    List<Faqs> faqList;
    LayoutInflater inflater;


    public FaqListAdapter(Activity a, List<Faqs> f){
        this.activity = a;
        this.faqList = f;
    }

    @Override
    public int getCount() {
        return this.faqList.size();
    }

    @Override
    public Object getItem(int position) {
        return this.faqList.get(position);
    }

    @Override
    public long getItemId(int position) {
        return position;
    }

    @Override
    public View getView(int position, View convertView, ViewGroup parent) {
        if (inflater == null)
            inflater = (LayoutInflater) activity.getSystemService(Context.LAYOUT_INFLATER_SERVICE);
        if (convertView == null)
            convertView = inflater.inflate(R.layout.faq_list_item, null);

        LinearLayout faq_item_lt = (LinearLayout) convertView.findViewById(R.id.faqitem);
        TextView faq_no_txt = (TextView) convertView.findViewById(R.id.txtFaqNo);
        final TextView faq_question_txt = (TextView) convertView.findViewById(R.id.txtFaqQuestion);

        Faqs faq = faqList.get(position);

        faq_no_txt.setText(String.valueOf(faq.get_id()));
        faq_question_txt.setText(faq.get_faq_question());

        faq_item_lt.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(v.getContext(), FaqView.class);
                intent.putExtra("question", String.valueOf(faq_question_txt));
                v.getContext().startActivity(intent);
            }
        });


        return convertView;
    }




}
