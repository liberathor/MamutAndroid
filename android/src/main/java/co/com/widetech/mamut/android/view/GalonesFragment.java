package co.com.widetech.mamut.android.view;

import android.app.Activity;
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
 * {@link InicioMantenimientoFragment.OnFragmentInteractionListener} interface
 * to handle interaction events.
 * Use the {@link GalonesFragment#newInstance} factory method to
 * create an instance of this fragment.
 */
public class GalonesFragment extends Fragment implements View.OnClickListener {
    // the fragment initialization parameters, e.g. ARG_ITEM_NUMBER
    private static final String ARG_PARAM1 = "param1";
    private static final String ARG_PARAM2 = "param2";
    private Button mButtonEnvar;
    private EditText mEditTextGalones;
    // TODO: Rename and change types of parameters
    private String mParam1;
    private String mParam2;

    private InicioMantenimientoFragment.OnFragmentInteractionListener mListener;

    public GalonesFragment() {
        // Required empty public constructor
    }

    /**
     * Use this factory method to create a new instance of
     * this fragment using the provided parameters.
     *
     * @param param1 Parameter 1.
     * @param param2 Parameter 2.
     * @return A new instance of fragment GalonesFragment.
     */
    // TODO: Rename and change types and number of parameters
    public static GalonesFragment newInstance(String param1, String param2) {
        GalonesFragment fragment = new GalonesFragment();
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
        return inflater.inflate(R.layout.fragment_galones, container, false);
    }

    @Override
    public void onActivityCreated(Bundle savedInstanceState) {
        super.onActivityCreated(savedInstanceState);
        Activity activity = getActivity();
        mButtonEnvar = (Button) activity.findViewById(R.id.ButtonEnviarGalones);
        mEditTextGalones = (EditText) activity.findViewById(R.id.editTextGalones);
        mButtonEnvar.setOnClickListener(this);
    }

    @Override
    public void onClick(View view) {
        int id = view.getId();
        switch (id) {
            case R.id.ButtonEnviarGalones:
                enviarGalones();
                break;
            default:
                break;
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
            mListener = (InicioMantenimientoFragment.OnFragmentInteractionListener) activity;
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

    private void enviarGalones() {
        if (mEditTextGalones != null) {
            try {
                SolicitudMantenimientoActivity parentFragment = ((SolicitudMantenimientoActivity) getActivity());
                if (parentFragment.sendData(SolicitudMantenimientoActivity.StatusSolicitudMantenimiento.SEND_ENVIAR_GALONES)) {
                    Uri uri = Uri.parse(SolicitudMantenimientoActivity.BASE_URI + "/" + "finalizar_mantenimiento");
                    mListener.onFragmentInteraction(uri);
                }
            } catch (NumberFormatException e) {
                e.printStackTrace();
            }
        }
    }

    public String getGalones() {
        return mEditTextGalones.getText().toString();
    }

    public void setValidationError(String errorGalones) {
        mEditTextGalones.setError(errorGalones);
    }
}
