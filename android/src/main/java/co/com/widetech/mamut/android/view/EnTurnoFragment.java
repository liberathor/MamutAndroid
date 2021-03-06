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
 * {@link EnTurnoFragment.OnFragmentInteractionListener} interface
 * to handle interaction events.
 * Use the {@link EnTurnoFragment#newInstance} factory method to
 * create an instance of this fragment.
 */
public class EnTurnoFragment extends Fragment implements View.OnClickListener {
    public static final String TAG = "EnTurnoFragment";
    // the fragment initialization parameters, e.g. ARG_ITEM_NUMBER
    private static final String ARG_PARAM1 = "param1";
    private static final String ARG_PARAM2 = "param2";
    private Button mButtonFinalizarTurno;
    private Button mButtonChat;
    private Button mButtonOpciones;
    // TODO: Rename and change types of parameters
    private String mParam1;
    private String mParam2;

    private OnFragmentInteractionListener mListener;

    public EnTurnoFragment() {
        // Required empty public constructor
    }

    /**
     * Use this factory method to create a new instance of
     * this fragment using the provided parameters.
     *
     * @param param1 Parameter 1.
     * @param param2 Parameter 2.
     * @return A new instance of fragment EnTurnoFragment.
     */
    // TODO: Rename and change types and number of parameters
    public static EnTurnoFragment newInstance(String param1, String param2) {
        EnTurnoFragment fragment = new EnTurnoFragment();
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
        return inflater.inflate(R.layout.fragment_en_turno, container, false);
    }

    @Override
    public void onActivityCreated(Bundle savedInstanceState) {
        super.onActivityCreated(savedInstanceState);
        Activity activity = getActivity();
        mButtonFinalizarTurno = (Button) activity.findViewById(R.id.ButtonFinalizarTurno);
        mButtonChat = (Button) activity.findViewById(R.id.ButtonChat);
        mButtonOpciones = (Button) activity.findViewById(R.id.ButtonOpciones);
        mButtonFinalizarTurno.setOnClickListener(this);
        mButtonChat.setOnClickListener(this);
        mButtonOpciones.setOnClickListener(this);
    }

    @Override
    public void onClick(View view) {
        int id = view.getId();
        InicioTurnoActivity parentActivity = ((InicioTurnoActivity) getActivity());
        Uri uri = null;
        switch (id) {
            case R.id.ButtonFinalizarTurno:
                if (parentActivity.sendData(InicioTurnoActivity.StateInicioTurno.SEND_DATA_STATE_FINALIZAR_TURNO)) {
                    uri = Uri.parse(InicioTurnoActivity.BASE_URI + "/" + "finalizar_turno");
                }
                break;
            case R.id.ButtonChat:
                getActivity().startActivity(new Intent(getActivity(), ChatActivity.class));
                break;
            case R.id.ButtonOpciones:
                break;
            default:
                break;
        }
        if (uri != null) {
            mListener.onFragmentInteraction(uri);
        }
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
