package ke.co.debechlabs.besure.fragments;

import android.annotation.TargetApi;
import android.app.Dialog;
import android.content.Context;
import android.content.DialogInterface;
import android.net.Uri;
import android.os.Build;
import android.os.Bundle;
import android.support.annotation.RequiresApi;
import android.support.v4.app.Fragment;
import android.support.v7.app.AlertDialog;
import android.support.v7.widget.Toolbar;
import android.text.TextUtils;
import android.view.LayoutInflater;
import android.view.Menu;
import android.view.MenuInflater;
import android.view.MenuItem;
import android.view.View;
import android.view.ViewGroup;
import android.view.Window;
import android.widget.ListView;
import android.widget.RadioButton;
import android.widget.RadioGroup;
import android.widget.Toast;

import java.util.ArrayList;
import java.util.List;

import ke.co.debechlabs.besure.Database.DatabaseHandler;
import ke.co.debechlabs.besure.R;
import ke.co.debechlabs.besure.SharedPreference.Manager;
import ke.co.debechlabs.besure.adapter.CountyListAdapter;
import ke.co.debechlabs.besure.adapter.ReferralListAdapter;
import ke.co.debechlabs.besure.models.County;
import ke.co.debechlabs.besure.models.Facility;
import ke.co.debechlabs.besure.util.AnimationUtils;
import ke.co.debechlabs.besure.util.RevealAnimationSetting;

/**
 * A simple {@link Fragment} subclass.
 * Activities that contain this fragment must implement the
 * {@link ReferralListFragment.OnFragmentInteractionListener} interface
 * to handle interaction events.
 * Use the {@link ReferralListFragment#newInstance} factory method to
 * create an instance of this fragment.
 */
public class ReferralListFragment extends Fragment {
    // TODO: Rename parameter arguments, choose names that match
    // the fragment initialization parameters, e.g. ARG_ITEM_NUMBER
    private static final String ARG_PARAM1 = "param1";
    private static final String ARG_PARAM2 = "param2";
    private static final String ARG_REVEAL_SETTINGS = "reveal_settings";

    // TODO: Rename and change types of parameters
    private String mParam1;
    private String mParam2;

    List<Facility> facilityList = new ArrayList<Facility>();
    ReferralListAdapter referralListAdapter;
    ListView referralListView;
    DatabaseHandler db;

    private static final String ALL_FACILITIES_TITLE = "All Counties";
    private String faciltyNumbers;

    Toolbar toolbar;

    private OnFragmentInteractionListener mListener;

    public ReferralListFragment() {
        // Required empty public constructor
    }


    // TODO: Rename and change types and number of parameters
    public static ReferralListFragment newInstance(RevealAnimationSetting setting) {

        ReferralListFragment fragment = new ReferralListFragment();
        Bundle args = new Bundle();
        args.putParcelable(ARG_REVEAL_SETTINGS, setting);
//        args.putString(ARG_PARAM1, param1);
//        args.putString(ARG_PARAM2, param2);
        fragment.setArguments(args);
        return fragment;
    }

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        if (getArguments() != null) {
            mParam1 = getArguments().getString(ARG_PARAM1);
            mParam2 = getArguments().getString(ARG_PARAM2);
        }
        setHasOptionsMenu(true);
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        View rootView = inflater.inflate(R.layout.fragment_referral_list, container, false);
        rootView.addOnLayoutChangeListener(new View.OnLayoutChangeListener() {
            @Override
            public void onLayoutChange(View v, int left, int top, int right, int bottom, int oldLeft, int oldTop, int oldRight, int oldBottom) {
                v.removeOnLayoutChangeListener(this);
            }
        });
        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.M) {
            AnimationUtils.registerCircularRevealAnimation(getContext(), rootView, (RevealAnimationSetting) getArguments().getParcelable(ARG_REVEAL_SETTINGS), getActivity().getColor(R.color.colorPrimary), getActivity().getColor(R.color.colorIcons));
        }
        db = new DatabaseHandler(getActivity());
        String county_name = Manager.getInstance(getActivity()).getCountyName();
        if(TextUtils.isEmpty(county_name)){
            facilityList = db.getAllFacilities();
        }else{
            facilityList = db.getFacilities(county_name);
        }

        faciltyNumbers = String.valueOf(facilityList.size());

        toolbar = (Toolbar) getActivity().findViewById(R.id.toolbar);
        toolbar.setTitle(county_name);
        toolbar.setSubtitle(faciltyNumbers + " Facilities");
        referralListView = (ListView) rootView.findViewById(R.id.referralList);

        referralListAdapter = new ReferralListAdapter(getActivity(), facilityList);
        referralListView.setAdapter(referralListAdapter);
        return rootView;
    }

    // TODO: Rename method, update argument and hook method into UI event
    public void onButtonPressed(Uri uri) {
        if (mListener != null) {
            mListener.onFragmentInteraction(uri);
        }
    }

    @Override
    public void onAttach(Context context) {
        super.onAttach(context);
        if (context instanceof OnFragmentInteractionListener) {
            mListener = (OnFragmentInteractionListener) context;
        } else {
            throw new RuntimeException(context.toString()
                    + " must implement OnFragmentInteractionListener");
        }
    }

    @Override
    public void onDetach() {
        super.onDetach();
        mListener = null;
    }

    /**
     * This interface must be implemented by activities that contain this
     * fragment to allow an interaction in this fragment to be communicated
     * to the activity and potentially other fragments contained in that
     * activity.
     * <p>
     * See the Android Training lesson <a href=
     * "http://developer.android.com/training/basics/fragments/communicating.html"
     * >Communicating with Other Fragments</a> for more information.
     */
    public interface OnFragmentInteractionListener {
        // TODO: Update argument type and name
        void onFragmentInteraction(Uri uri);
    }

    @Override
    public void onCreateOptionsMenu(Menu menu, MenuInflater inflater) {
        super.onCreateOptionsMenu(menu, inflater);
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {

        switch (item.getItemId()){
            case R.id.action_filter:
                showCountiesFilter();
                return true;
        }
        return super.onOptionsItemSelected(item);
    }

    public void showCountiesFilter(){
        final AlertDialog.Builder builder = new AlertDialog.Builder(getActivity());
        builder.setTitle("Select a County");
//        builder.setView(R.layout.countyfilter_dialog);
        final List<County> countyList = this.db.getAllCounties();
        final CountyListAdapter countyListAdapter = new CountyListAdapter(getActivity(), countyList);
        builder.setAdapter(countyListAdapter, new DialogInterface.OnClickListener() {
            @Override
            public void onClick(DialogInterface dialog, int which) {
                County county = countyList.get(which);

                String countyName = county.getCounty_name();
                facilityList = db.getFacilities(countyName);
                toolbar.setTitle(countyName);
                toolbar.setSubtitle(facilityList.size() + " Facilities");


                referralListAdapter = new ReferralListAdapter(getActivity(), facilityList);
                referralListView.invalidate();
                referralListView.setAdapter(referralListAdapter);
                referralListView.invalidate();
                referralListAdapter.notifyDataSetChanged();
            }
        })
        .setPositiveButton("VIEW ALL FACILITIES", new DialogInterface.OnClickListener() {
            @Override
            public void onClick(DialogInterface dialog, int which) {
                toolbar.setTitle(ALL_FACILITIES_TITLE);
                facilityList = db.getAllFacilities();
                toolbar.setSubtitle(facilityList.size() + " Facilities");
                referralListAdapter = new ReferralListAdapter(getActivity(), facilityList);
                referralListView.invalidate();
                referralListView.setAdapter(referralListAdapter);
                referralListView.invalidate();
                referralListAdapter.notifyDataSetChanged();
            }
        })
        .setCancelable(true)
        .setNegativeButton("CANCEL", new DialogInterface.OnClickListener() {
            @Override
            public void onClick(DialogInterface dialog, int which) {
                dialog.dismiss();
            }
        });

        final AlertDialog dialog = builder.create();
        dialog.show();
    }

    public void createFacilityList(){

    }
}
