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
 * {@link InicioMantenimientoFragment.OnFragmentInteractionListener} interface
 * to handle interaction events.
 * Use the {@link InicioMantenimientoFragment#newInstance} factory method to
 * create an instance of this fragment.
 */
public class InicioMantenimientoFragment extends Fragment implements View.OnClickListener {
    // TODO: Rename parameter arguments, choose names that match
    // the fragment initialization parameters, e.g. ARG_ITEM_NUMBER
    private static final String ARG_PARAM1 = "param1";
    private static final String ARG_PARAM2 = "param2";
    private Button mButtonIniciarMantenimiento;
    private Button mButtonChat;


    // TODO: Rename and change types of parameters
    private String mParam1;
    private String mParam2;

    private OnFragmentInteractionListener mListener;

    public InicioMantenimientoFragment() {
        // Required empty public constructor
    }

    /**
     * Use this factory method to create a new instance of
     * this fragment using the provided parameters.
     *
     * @param param1 Parameter 1.
     * @param param2 Parameter 2.
     * @return A new instance of fragment InicioMantenimientoFragment.
     */
    // TODO: Rename and change types and number of parameters
    public static InicioMantenimientoFragment newInstance(String param1, String param2) {
        InicioMantenimientoFragment fragment = new InicioMantenimientoFragment();
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
        return inflater.inflate(R.layout.fragment_iniciar_mantenimiento, container, false);
    }

    @Override
    public void onActivityCreated(Bundle savedInstanceState) {
        super.onActivityCreated(savedInstanceState);
        Activity activity = getActivity();
        mButtonIniciarMantenimiento = (Button) activity.findViewById(R.id.ButtonIniciarMantenimiento);
        mButtonChat = (Button) activity.findViewById(R.id.ButtonChat);
        mButtonIniciarMantenimiento.setOnClickListener(this);
        mButtonChat.setOnClickListener(this);
    }

    @Override
    public void onClick(View view) {
        SolicitudMantenimientoActivity parentActivity = ((SolicitudMantenimientoActivity) getActivity());
        int id = view.getId();
        Uri uri = null;
        switch (id) {
            case R.id.ButtonIniciarMantenimiento:
                if (parentActivity.sendData(SolicitudMantenimientoActivity.StatusSolicitudMantenimiento.SEND_INICIAR_MANTENIMIENTO)) {
                    uri = Uri.parse(SolicitudMantenimientoActivity.BASE_URI + "/" + "ingresar_galones");
                }
                break;
            case R.id.ButtonChat:
                getActivity().startActivity(new Intent(getActivity(), ChatActivity.class));
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
