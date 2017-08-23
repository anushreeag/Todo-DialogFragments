package com.cosmic.todo;

import android.annotation.SuppressLint;
import android.app.DatePickerDialog;
import android.content.Context;
import android.content.DialogInterface;
import android.os.AsyncTask;
import android.os.Bundle;
import android.support.v4.app.DialogFragment;
import android.support.v4.app.Fragment;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import android.widget.TextView;



/**
 * A simple {@link Fragment} subclass.
 *
 */
public class HelpItemDialog extends DialogFragment {

    TextView Help;

    public static final String TAG = "EditItemDialog";


    @SuppressLint("ValidFragment")
    public HelpItemDialog() {
        // Required empty public constructor

    }

    @Override
    public void onAttach(Context context) {
        super.onAttach(context);

    }


    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        View dialogview = inflater.inflate(R.layout.help_todo, container, false);

        return dialogview;
    }


}

