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
import android.widget.EditText;
import co.com.widetech.mamut.android.R;

/**
 * A simple {@link Fragment} subclass.
 * Activities that contain this fragment must implement the
 * {@link InfoInicioViajeFragment.OnFragmentInteractionListener} interface
 * to handle interaction events.
 * Use the {@link InfoInicioViajeFragment#newInstance} factory method to
 * create an instance of this fragment.
 */
public class InfoInicioViajeFragment extends Fragment implements View.OnClickListener {
    private static final String ARG_PARAM1 = "param1";
    private static final String ARG_PARAM2 = "param2";
    private EditText mEditTextManifiesto;
    private EditText mEditTextTrailer;
    private Button mButtonEnviarInfoViaje;
    private Button mButtonChat;
    private Button mButtonOptions;
    // TODO: Rename and change types of parameters
    private String mParam1;
    private String mParam2;

    private OnFragmentInteractionListener mListener;

    public InfoInicioViajeFragment() {
        // Required empty public constructor
    }

    /**
     * Use this factory method to create a new instance of
     * this fragment using the provided parameters.
     *
     * @param param1 Parameter 1.
     * @param param2 Parameter 2.
     * @return A new instance of fragment InfoInicioViajeFragment.
     */
    // TODO: Rename and change types and number of parameters
    public static InfoInicioViajeFragment newInstance(String param1, String param2) {
        InfoInicioViajeFragment fragment = new InfoInicioViajeFragment();
        Bundle args = new Bundle();
        args.putString(ARG_PARAM1, param1);
        args.putString(ARG_PARAM2, param2);
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
        return inflater.inflate(R.layout.fragment_info_inicio_viaje, container, false);
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
        mButtonEnviarInfoViaje = (Button) activity.findViewById(R.id.ButtonEnviarInfoViaje);
        mButtonChat = (Button) activity.findViewById(R.id.ButtonChat);
        mButtonOptions = (Button) activity.findViewById(R.id.ButtonOptions);
        mEditTextTrailer = (EditText) activity.findViewById(R.id.editTextTrailer);
        mEditTextManifiesto = (EditText) activity.findViewById(R.id.editTextManifiesto);
        mButtonEnviarInfoViaje.setOnClickListener(this);
        mButtonChat.setOnClickListener(this);
        mButtonOptions.setOnClickListener(this);
        super.onActivityCreated(savedInstanceState);
    }

    @Override
    public void onClick(View view) {
        int id = view.getId();
        Class activity = null;
        switch (id) {
            case R.id.ButtonEnviarInfoViaje:
                InicioViajeActivity parentActivity = ((InicioViajeActivity) getActivity());
                if (parentActivity.sendData(mEditTextManifiesto.getText().toString(), mEditTextTrailer.getText().toString())) {
                    activity = EstadoViajeActivity.class;
                }
                break;
            case R.id.ButtonChat:
                activity = ChatActivity.class;
                break;
            case R.id.ButtonOpciones:
                break;
            default:
                break;
        }
        if (activity != null) {
            getActivity().startActivity(new Intent(getActivity(), activity));
        }
    }

    public void setValidationErrors(String errorManifiesto, String errorTrailer) {
        mEditTextManifiesto.setError(errorManifiesto);
        mEditTextTrailer.setError(errorTrailer);
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
        void onFragmentInteraction(Uri uri);
    }

}
