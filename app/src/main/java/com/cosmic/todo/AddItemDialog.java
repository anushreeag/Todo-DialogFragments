package com.cosmic.todo;

import android.annotation.SuppressLint;
import android.app.DatePickerDialog;
import android.content.Context;
import android.content.DialogInterface;
import android.os.AsyncTask;
import android.os.Bundle;
import android.support.v4.app.DialogFragment;
import android.support.v4.app.Fragment;
import android.view.KeyEvent;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.DatePicker;
import android.widget.EditText;
import android.widget.Spinner;
import android.widget.Toast;

import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Calendar;
import java.util.List;
import java.util.Locale;


/**
 * A simple {@link Fragment} subclass.
 *
 */
public class AddItemDialog extends DialogFragment {


    private TodoActivity ta;
    Context ctx;
    TodoDBFunctions dbfunctions;
    EditText etTitle;
    EditText etDate;
    EditText etNotes;
    Calendar myCalendar;
    Button save;
    Spinner spinner;
    Button cancel;
    public static final String TAG = "EditItemDialog";


    @SuppressLint("ValidFragment")
    public AddItemDialog() {
        // Required empty public constructor

    }

    @Override
    public void onAttach(Context context) {
        super.onAttach(context);

        ctx = context;

        ta = (TodoActivity) ctx;
    }

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        View dialogview = inflater.inflate(R.layout.fragment_add_item_dialog, container, false);

//        /getDialog().setTitle(R.string.title_activity_edit_item);
        dbfunctions = new TodoDBFunctions(ctx);

        etTitle = (EditText) dialogview.findViewById(R.id.editNewTitle);
        etTitle.setText("");
        etNotes = (EditText) dialogview.findViewById(R.id.editNewNotes);
        etNotes.setText("");

        etDate = (EditText) dialogview.findViewById(R.id.editNewDate);
        etDate.setFocusable(false);
        etDate.setText("");

        spinner = (Spinner) dialogview.findViewById(R.id.editNewPriority);
        List<String> prioritylist = new ArrayList<>();
        prioritylist.add("HIGH");
        prioritylist.add("MED");
        prioritylist.add("LOW");
        ArrayAdapter<String> priorityAdp = new ArrayAdapter<String>(ctx,android.R.layout.simple_spinner_dropdown_item,prioritylist);
        spinner.setPrompt(getResources().getString(R.string.spinnerTitle));
        spinner.setAdapter(priorityAdp);
        spinner.setSelection(0);
        save = (Button) dialogview.findViewById(R.id.addSave);

        save.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                    addTodoItem();
            }
        });

        cancel = (Button) dialogview.findViewById(R.id.addCancel);
        cancel.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                getDialog().dismiss();
            }
        });

        myCalendar = Calendar.getInstance();

        etDate.setOnClickListener(new View.OnClickListener() {

            @Override
            public void onClick(View v) {
                // TODO Auto-generated method stub


               DatePickerDialog pickerDialog = new DatePickerDialog(getActivity(), date, myCalendar
                        .get(Calendar.YEAR), myCalendar.get(Calendar.MONTH),
                        myCalendar.get(Calendar.DAY_OF_MONTH));
               pickerDialog.getDatePicker().setMinDate(System.currentTimeMillis() - 1000 );
                pickerDialog.show();

            }
        });


        getDialog().setOnKeyListener(new DialogInterface.OnKeyListener() {
            @Override
            public boolean onKey(DialogInterface dialogInterface, int i, KeyEvent keyEvent) {
                if (keyEvent.getAction() == KeyEvent.KEYCODE_BACK
                        && keyEvent.getAction() == KeyEvent.ACTION_UP) {
                    getDialog().dismiss();
                    return true;
                }
                return false;
            }
        });

        return dialogview;
    }

    private void updateLabel() {
        String myFormat = "MM/dd/yy"; //In which you need put here
        SimpleDateFormat sdf = new SimpleDateFormat(myFormat, Locale.US);

        etDate.setText(sdf.format(myCalendar.getTime()));
    }


    DatePickerDialog.OnDateSetListener date = new DatePickerDialog.OnDateSetListener() {

        @Override
        public void onDateSet(DatePicker view, int year, int monthOfYear,
                              int dayOfMonth) {
            // TODO Auto-generated method stub
            myCalendar.set(Calendar.YEAR, year);
            myCalendar.set(Calendar.MONTH, monthOfYear);
            myCalendar.set(Calendar.DAY_OF_MONTH, dayOfMonth);
            updateLabel();
        }

    };

    public void addTodoItem() {
        String text = etTitle.getText().toString();
        String addDate = etDate.getText().toString();
        if (text.equals("") || addDate.equals("") ) {

            Toast.makeText(ctx,getResources().getString(R.string.empty),Toast.LENGTH_SHORT).show();
        }
        else{
            TodoBean tb = new TodoBean(text);
            tb.setNotes(etNotes.getText().toString());
            tb.setDate(addDate);
            tb.setPriority(spinner.getSelectedItem().toString());
            AddTodoTask addTask = new AddTodoTask();
            addTask.execute(tb);

        }
    }

    public class AddTodoTask extends AsyncTask<TodoBean,Void,Boolean>{


        @Override
        protected Boolean doInBackground(TodoBean... params) {

            return dbfunctions.addTodo(params[0]);
        }


        @Override
        protected void onPostExecute(Boolean result) {
            super.onPostExecute(result);
            if(result){
                getDialog().dismiss();
                ta.recreate();
            }

        }
    }

}
