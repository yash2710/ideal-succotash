package me.yashtrivedi.ideal_succotash;

import android.app.Activity;
import android.app.AlertDialog;
import android.app.Dialog;
import android.content.DialogInterface;
import android.content.SharedPreferences;
import android.content.res.Resources;
import android.os.Bundle;
import android.preference.Preference;
import android.preference.PreferenceManager;
import android.support.v4.app.DialogFragment;
import android.text.Editable;
import android.text.TextWatcher;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.WindowManager;
import android.widget.CheckBox;
import android.widget.EditText;
import android.widget.Toast;

import com.firebase.client.Firebase;
import com.firebase.client.FirebaseError;

import java.util.HashMap;
import java.util.Map;

/**
 * Created by amit on 20-May-16.
 */
public class ShowRequestFormFragment extends DialogFragment {

    EditText editTextCarCapacity, editTextArea, editTextCarNo1, editTextCarNo2, editTextCarNo3, editTextCarNo4;
    CheckBox checkBoxToFromNirma;
    Firebase firebase;

    String carNo, userName;

    /**
     * Public static constructor that creates fragment and
     * passes a bundle with data into it when adapter is created
     */
    public static ShowRequestFormFragment newInstance() {
        ShowRequestFormFragment showRequestFormFragment = new ShowRequestFormFragment();
        Bundle bundle = new Bundle();
        showRequestFormFragment.setArguments(bundle);
        return showRequestFormFragment;
    }

    /**
     * Initialize instance variables with data from bundle
     */
    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
    }

    /**
     * Open the keyboard automatically when the dialog fragment is opened
     */
    @Override
    public void onActivityCreated(Bundle savedInstanceState) {
        super.onActivityCreated(savedInstanceState);
        getDialog().getWindow().setSoftInputMode(WindowManager.LayoutParams.SOFT_INPUT_STATE_VISIBLE);
    }


    @Override
    public Dialog onCreateDialog(Bundle savedInstanceState) {
        /* Use the Builder class for convenient dialog construction */

        firebase = new Firebase(Constants.FIREBASE_URL_RIDES);

        AlertDialog.Builder builder = new AlertDialog.Builder(getActivity(), R.style.CustomTheme_Dialog);

        /* Get the layout inflater */
        LayoutInflater inflater = getActivity().getLayoutInflater();
        View rootView = inflater.inflate(R.layout.dialog_request_form, null);

        editTextCarNo1 = (EditText) rootView.findViewById(R.id.edit_text_car_no1);
        editTextCarNo2 = (EditText) rootView.findViewById(R.id.edit_text_car_no2);
        editTextCarNo3 = (EditText) rootView.findViewById(R.id.edit_text_car_no3);
        editTextCarNo4 = (EditText) rootView.findViewById(R.id.edit_text_car_no4);
        editTextCarCapacity = (EditText) rootView.findViewById(R.id.edit_text_capacity);
        editTextCarNo1.addTextChangedListener(new TextWatcher() {
            @Override
            public void beforeTextChanged(CharSequence charSequence, int i, int i1, int i2) {

            }

            @Override
            public void onTextChanged(CharSequence charSequence, int i, int i1, int i2) {

            }

            @Override
            public void afterTextChanged(Editable editable) {
                if (editable.length() == 2) {
                    editTextCarNo2.requestFocus();
                }
            }
        });

        editTextCarNo2.addTextChangedListener(new TextWatcher() {
            @Override
            public void beforeTextChanged(CharSequence charSequence, int i, int i1, int i2) {

            }

            @Override
            public void onTextChanged(CharSequence charSequence, int i, int i1, int i2) {

            }

            @Override
            public void afterTextChanged(Editable editable) {
                if (editable.length() == 2) {
                    editTextCarNo3.requestFocus();
                } else if (editable.length() == 0) {
                    editTextCarNo1.requestFocus();
                }
            }
        });

        editTextCarNo3.addTextChangedListener(new TextWatcher() {
            @Override
            public void beforeTextChanged(CharSequence charSequence, int i, int i1, int i2) {

            }

            @Override
            public void onTextChanged(CharSequence charSequence, int i, int i1, int i2) {

            }

            @Override
            public void afterTextChanged(Editable editable) {
                if (editable.length() == 2) {
                    editTextCarNo4.requestFocus();
                } else if (editable.length() == 0) {
                    editTextCarNo2.requestFocus();
                }
            }
        });

        editTextCarNo4.addTextChangedListener(new TextWatcher() {
            @Override
            public void beforeTextChanged(CharSequence charSequence, int i, int i1, int i2) {

            }

            @Override
            public void onTextChanged(CharSequence charSequence, int i, int i1, int i2) {

            }

            @Override
            public void afterTextChanged(Editable editable) {

                if (editable.length() == 0) {
                    editTextCarNo3.requestFocus();
                }
            }
        });


        editTextArea = (EditText) rootView.findViewById(R.id.edit_text_area);
        checkBoxToFromNirma = (CheckBox) rootView.findViewById(R.id.to_from_check_box);
        checkBoxToFromNirma.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                if (!checkBoxToFromNirma.isChecked()) {
                    checkBoxToFromNirma.setText("Going Home");
                    editTextArea.setHint("Enter the going to area");

                } else {

                    checkBoxToFromNirma.setText("Going Nirma");
                    editTextArea.setHint("Enter the going from area");

                }

            }
        });


        builder.setView(rootView)
                /* Add action buttons */
                .setPositiveButton("Submit", new DialogInterface.OnClickListener() {
                    @Override
                    public void onClick(DialogInterface dialog, int id) {
                        postRequest();
                    }
                })
                .setNegativeButton("Cancel", new DialogInterface.OnClickListener() {
                    @Override
                    public void onClick(DialogInterface dialogInterface, int i) {

                    }
                });


        return builder.create();
    }

    public void postRequest() {

        if (editTextArea.getText().length() == 0 || editTextCarCapacity.getText().length() == 0 || editTextCarNo1.getText().length() == 0 || editTextCarNo2.getText().length() == 0 || editTextCarNo3.getText().length() == 0 || editTextCarNo4.getText().length() == 0) {

            Toast.makeText(getContext(), "E madar", Toast.LENGTH_SHORT).show();


        } else {
            SharedPreferences sharedPrefrences = PreferenceManager.getDefaultSharedPreferences(getContext());
            carNo = editTextCarNo1.getText().toString() + "-" + editTextCarNo2.getText().toString() + "-" + editTextCarNo3.getText().toString() + "-" + editTextCarNo4.getText().toString();
            userName = sharedPrefrences.getString(Constants.KEY_NAME, "no name");

            Map<String, Object> parentData = new HashMap<>();
            Map<String, Object> rideData = new HashMap<>();

            rideData.put("userName", userName);
            rideData.put("toNirma", checkBoxToFromNirma.isChecked());
            rideData.put("carNo", carNo);
            rideData.put("area", editTextArea.getText().toString());

            parentData.put(sharedPrefrences.getString(Constants.KEY_ENCODED_EMAIL, "null"), rideData);


            firebase.updateChildren(parentData, new Firebase.CompletionListener() {
                @Override
                public void onComplete(FirebaseError firebaseError, Firebase firebase) {

                }
            });

        }
    }
}
