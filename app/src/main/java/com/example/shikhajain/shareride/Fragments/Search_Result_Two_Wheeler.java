package com.example.shikhajain.shareride.Fragments;

import android.app.Activity;
import android.net.Uri;
import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import com.example.shikhajain.shareride.R;


/**
 * A simple {@link Fragment} subclass.
 * Activities that contain this fragment must implement the
 * {@link Search_Result_Two_Wheeler.OnFragmentInteractionListener} interface
 * to handle interaction events.
 * Use the {@link Search_Result_Two_Wheeler#newInstance} factory method to
 * create an instance of this fragment.
 */
public class Search_Result_Two_Wheeler extends Fragment {
    // TODO: Rename parameter arguments, choose names that match
    // the fragment initialization parameters, e.g. ARG_ITEM_NUMBER
    private static final String ARG_PARAM1 = "param1";
    private static final String ARG_PARAM2 = "param2";

    // TODO: Rename and change types of parameters
    private String mParam1;
    private String mParam2;
    RecyclerView SeachView;

    private OnFragmentInteractionListener mListener;

    /**
     * Use this factory method to create a new instance of
     * this fragment using the provided parameters.
     *
     * @param param1 Parameter 1.
     * @param param2 Parameter 2.
     * @return A new instance of fragment Search_Result_Two_Wheelr.
     */
    // TODO: Rename and change types and number of parameters
    public static Search_Result_Two_Wheeler newInstance(String param1, String param2) {
        Search_Result_Two_Wheeler fragment = new Search_Result_Two_Wheeler();
        Bundle args = new Bundle();
        args.putString(ARG_PARAM1, param1);
        args.putString(ARG_PARAM2, param2);
        fragment.setArguments(args);
        return fragment;
    }

    public Search_Result_Two_Wheeler() {
        // Required empty public constructor
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
        View v=inflater.inflate(R.layout.fragment_home, container, false);

        /*SeachView = (RecyclerView) v.findViewById(R.id.rv);
        SeachView.setLayoutManager(new LinearLayoutManager(getActivity()));
        SeachView.setHasFixedSize(true);*/
//        AdapterSearchResult adapterSearchResult = new AdapterSearchResult(getActivity());
        //DoJsonParsing();

  //      SeachView.setAdapter(adapterSearchResult);

        Bundle b=getArguments();
        if(b!=null) {
//            Log.d("ride type is", b.getString("ride"));
            // Log.d("position is", b.getInt("position") + "");
        }
           /* result= (ListView) v.findViewById(R.id.listView);
        ArrayAdapter<String> l=new ArrayAdapter<String>(getActivity(),android.R.layout.simple_list_item_1,data);
        result.setAdapter(l);
        result.setOnItemClickListener(this);*/


        return v;
    }

    // TODO: Rename method, update argument and hook method into UI event
    public void onButtonPressed(Uri uri) {
        if (mListener != null) {
   //         mListener.onFragmentInteraction(uri);
        }
    }

    @Override
    public void onAttach(Activity activity) {
        super.onAttach(activity);
        try {
     //       mListener = (OnFragmentInteractionListener) activity;
        } catch (ClassCastException e) {
            throw new ClassCastException(activity.toString()
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
     * <p/>
     * See the Android Training lesson <a href=
     * "http://developer.android.com/training/basics/fragments/communicating.html"
     * >Communicating with Other Fragments</a> for more information.
     */
    public interface OnFragmentInteractionListener {
        // TODO: Update argument type and name
        public void onFragmentInteraction(Uri uri);
    }

}
