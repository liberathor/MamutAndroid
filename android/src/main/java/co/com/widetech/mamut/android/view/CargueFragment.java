package co.com.widetech.mamut.android.view;

import android.app.Activity;
import android.content.Intent;
import android.net.Uri;
import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import co.com.widetech.mamut.android.R;


/**
 * A simple {@link Fragment} subclass.
 * Activities that contain this fragment must implement the
 * {@link CargueFragment.OnFragmentInteractionListener} interface
 * to handle interaction events.
 * Use the {@link CargueFragment#newInstance} factory method to
 * create an instance of this fragment.
 */
public class CargueFragment extends Fragment implements View.OnClickListener {
    private Button mButtonLlegueCargar;
    private Button mButtonChat;

    private OnFragmentInteractionListener mListener;

    public CargueFragment() {
        // Required empty public constructor
    }

    /**
     * Use this factory method to create a new instance of
     * this fragment using the provided parameters.
     *
     * @param param1 Parameter 1.
     * @param param2 Parameter 2.
     * @return A new instance of fragment CargueFragment.
     */
    // TODO: Rename and change types and number of parameters
    public static CargueFragment newInstance(String param1, String param2) {
        CargueFragment fragment = new CargueFragment();
        Bundle args = new Bundle();
        fragment.setArguments(args);
        return fragment;
    }

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        if (getArguments() != null) {
        }
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        return inflater.inflate(R.layout.fragment_cargue, container, false);
    }

    // TODO: Rename method, update argument and hook method into UI event
    public void onButtonPressed(Uri uri) {
        if (mListener != null) {
            mListener.onFragmentInteraction(uri);
        }
    }

    @Override
    public void onAttach(Activity activity) {
        super.onAttach(activity);
        try {
            mListener = (OnFragmentInteractionListener) activity;
        } catch (ClassCastException e) {
            throw new ClassCastException(activity.toString()
                    + " must implement OnFragmentInteractionListener");
        }
    }

    @Override
    public void onActivityCreated(Bundle savedInstanceState) {
        Activity activity = getActivity();
        mButtonLlegueCargar = (Button) activity.findViewById(R.id.ButtonLlegueCargar);
        mButtonChat = (Button) activity.findViewById(R.id.ButtonChat);
        mButtonLlegueCargar.setOnClickListener(this);
        mButtonChat.setOnClickListener(this);
        super.onActivityCreated(savedInstanceState);
    }

    @Override
    public void onClick(View view) {
        int id = view.getId();
        Class activity = null;
        switch (id) {
            case R.id.ButtonLlegueCargar:
                activity = EstadoViajeActivity.class;
                break;
            case R.id.ButtonChat:
                break;
            default:
                break;
        }
        if (activity != null) {
            getActivity().startActivity(new Intent(getActivity(), activity));
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
