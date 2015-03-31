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
 * {@link EnDetencionRutaFragment.OnFragmentInteractionListener} interface
 * to handle interaction events.
 * Use the {@link OtroMotivoDetencionFragment#newInstance} factory method to
 * create an instance of this fragment.
 */
public class OtroMotivoDetencionFragment extends Fragment implements View.OnClickListener {
    // the fragment initialization parameters, e.g. ARG_ITEM_NUMBER
    private static final String ARG_PARAM1 = "param1";
    private static final String ARG_PARAM2 = "param2";
    private Button mButtonEnviar;
    private Button mButtonChat;
    private Button mButtonOpciones;
    // TODO: Rename and change types of parameters
    private String mParam1;
    private String mParam2;

    private EnDetencionRutaFragment.OnFragmentInteractionListener mListener;

    public OtroMotivoDetencionFragment() {
        // Required empty public constructor
    }

    /**
     * Use this factory method to create a new instance of
     * this fragment using the provided parameters.
     *
     * @param param1 Parameter 1.
     * @param param2 Parameter 2.
     * @return A new instance of fragment OtroMotivoDetencionFragment.
     */
    // TODO: Rename and change types and number of parameters
    public static OtroMotivoDetencionFragment newInstance(String param1, String param2) {
        OtroMotivoDetencionFragment fragment = new OtroMotivoDetencionFragment();
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
        return inflater.inflate(R.layout.fragment_otro_motivo_detencion, container, false);
    }

    @Override
    public void onActivityCreated(Bundle savedInstanceState) {
        super.onActivityCreated(savedInstanceState);
        Activity activity = getActivity();
        mButtonEnviar = (Button) activity.findViewById(R.id.ButtonEnviarTanqueo);
        mButtonChat = (Button) activity.findViewById(R.id.ButtonChat);
        mButtonOpciones = (Button) activity.findViewById(R.id.ButtonOptions);
        mButtonEnviar.setOnClickListener(this);
        mButtonChat.setOnClickListener(this);
        mButtonOpciones.setOnClickListener(this);
    }

    @Override
    public void onClick(View view) {
        int id = view.getId();
        Uri uri = null;
        switch (id) {
            case R.id.ButtonEnviarTanqueo:
                uri = Uri.parse(this.getClass().getCanonicalName());
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
            onButtonPressed(uri);
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
            mListener = (EnDetencionRutaFragment.OnFragmentInteractionListener) activity;
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

}
