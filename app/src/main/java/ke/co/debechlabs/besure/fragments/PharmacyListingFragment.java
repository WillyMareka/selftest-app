package ke.co.debechlabs.besure.fragments;

import android.content.Context;
import android.net.Uri;
import android.os.Build;
import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.support.v7.widget.Toolbar;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ListView;

import java.util.ArrayList;
import java.util.List;

import ke.co.debechlabs.besure.Database.DatabaseHandler;
import ke.co.debechlabs.besure.R;
import ke.co.debechlabs.besure.adapter.PharmacyListAdapter;
import ke.co.debechlabs.besure.models.Pharmacy;
import ke.co.debechlabs.besure.util.AnimationUtils;
import ke.co.debechlabs.besure.util.RevealAnimationSetting;

/**
 * A simple {@link Fragment} subclass.
 * Activities that contain this fragment must implement the
 * {@link PharmacyListingFragment.OnFragmentInteractionListener} interface
 * to handle interaction events.
 * Use the {@link PharmacyListingFragment#newInstance} factory method to
 * create an instance of this fragment.
 */
public class PharmacyListingFragment extends Fragment {
    // TODO: Rename parameter arguments, choose names that match
    // the fragment initialization parameters, e.g. ARG_ITEM_NUMBER
    private static final String ARG_PARAM1 = "param1";
    private static final String ARG_PARAM2 = "param2";
    private static final String ARG_REVEAL_SETTINGS = "settings";

    // TODO: Rename and change types of parameters
    private String mParam1;
    private String mParam2;

    private OnFragmentInteractionListener mListener;

    ListView pharmacyListView;
    List<Pharmacy> pharmacyList = new ArrayList<Pharmacy>();
    PharmacyListAdapter adapter;

    Toolbar toolbar;
    public PharmacyListingFragment() {
        // Required empty public constructor
    }

    /**
     * Use this factory method to create a new instance of
     * this fragment using the provided parameters.
     *
     * @param setting
     * @return A new instance of fragment PharmacyListingFragment.
     */
    // TODO: Rename and change types and number of parameters
    public static PharmacyListingFragment newInstance(RevealAnimationSetting setting) {
        PharmacyListingFragment fragment = new PharmacyListingFragment();
        Bundle args = new Bundle();
        args.putParcelable(ARG_REVEAL_SETTINGS, setting);
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
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        View rootView = inflater.inflate(R.layout.fragment_pharmacy_listing, container, false);
        rootView.addOnLayoutChangeListener(new View.OnLayoutChangeListener() {
            @Override
            public void onLayoutChange(View v, int left, int top, int right, int bottom, int oldLeft, int oldTop, int oldRight, int oldBottom) {
                v.removeOnLayoutChangeListener(this);
            }
        });
        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.M) {
            AnimationUtils.registerCircularRevealAnimation(getContext(), rootView, (RevealAnimationSetting) getArguments().getParcelable(ARG_REVEAL_SETTINGS), getActivity().getColor(R.color.colorPrimary), getActivity().getColor(R.color.colorIcons));
        }

        toolbar = (Toolbar) getActivity().findViewById(R.id.toolbar);
        toolbar.setTitle("Pharmacy List");
        toolbar.setSubtitle("You can get your kits from here");

        DatabaseHandler db =new DatabaseHandler(getActivity());
        pharmacyList = db.getAllPharmacies();
        adapter = new PharmacyListAdapter(getActivity(), pharmacyList);
        pharmacyListView = (ListView) rootView.findViewById(R.id.pharmacyListView);
        pharmacyListView.setAdapter(adapter);
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
}
