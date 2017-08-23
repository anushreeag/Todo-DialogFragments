package com.cosmic.todo;

import android.content.Context;
import android.content.DialogInterface;
import android.content.Intent;
import android.graphics.drawable.GradientDrawable;
import android.os.AsyncTask;
import android.support.annotation.NonNull;
import android.support.v4.app.FragmentManager;
import android.support.v4.content.ContextCompat;
import android.support.v7.app.ActionBar;
import android.support.v7.app.AlertDialog;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.support.v7.widget.RecyclerView;
import android.util.Log;
import android.view.Gravity;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;
import android.widget.EditText;
import android.widget.ImageButton;
import android.widget.ListView;
import android.widget.TextView;
import android.widget.Toast;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Calendar;
import java.util.Date;
import java.util.List;
import java.util.Locale;

public class TodoActivity extends AppCompatActivity {

    ListView lv ;

    FragmentManager fm;
    EditItemDialog editdia;
    AddItemDialog addItem;
    ListItemAdapter todoAdp;
    TodoDBFunctions dbfunctions;
    Context ctx;
    public static final String TAG = "TodoActivity";
    public static final String ITEM_NAME = "todo-item";

    ActionBar actionbar;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_todo);
        lv = (ListView) findViewById(R.id.todoList);

        ctx = this.getApplicationContext();
        dbfunctions = new TodoDBFunctions(ctx);
        populateList();
        List<TodoBean> todolist = new ArrayList<TodoBean>();
        todolist = dbfunctions.getTodoList();

        todoAdp = new ListItemAdapter(ctx,todolist);
        fm  = getSupportFragmentManager();

        int[] colors = {0, 0xFF003FFF, 0}; // red for the example
        lv.setDivider(new GradientDrawable(GradientDrawable.Orientation.RIGHT_LEFT, colors));
        lv.setDividerHeight(5);
        Log.i(TAG, "TodoActivity ->onCreate adp count" + todoAdp.getCount());
        lv.setAdapter(todoAdp);
    }



    void populateList(){
        ReadTodoTask readTask = new ReadTodoTask();
        readTask.execute();

    }

    @Override
    protected void onResume() {
        super.onResume();
        Log.i(TAG, "TodoActivity ->onResume adp count" + todoAdp.getCount());
        todoAdp.clear();
        todoAdp.addAll(dbfunctions.getTodoList());
        todoAdp.notifyDataSetChanged();
        Log.i(TAG, "TodoActivity ->onResume adp count" + todoAdp.getCount());
    }

    public class ReadTodoTask extends AsyncTask<Void,Void,List<TodoBean>>{


        @Override
        protected List<TodoBean> doInBackground(Void... voids) {

            return dbfunctions.getTodoList();
        }

        @Override
        protected void onPostExecute(List<TodoBean> todoBean) {

            super.onPostExecute(todoBean);

            if(todoAdp.getCount()>0) {
                todoAdp.clear();
                todoAdp.addAll(todoBean);
                todoAdp.notifyDataSetChanged();
            }
            else{
                setEmptyTextForList();
            }

        }
    }

    public void addTodoItem(View v){
        addItem = new AddItemDialog();
        addItem.show(fm,"fragment_add");
    }

    private void setEmptyTextForList() {
        TextView tv = new TextView(ctx);
        tv.setText(getResources().getString(R.string.emptyList));
        tv.setTextSize(30);
        tv.setGravity(Gravity.CENTER);
        tv.setTextColor(ContextCompat.getColor(ctx,android.R.color.holo_blue_dark));
        ((ViewGroup) lv.getParent()).addView(tv);
        lv.setEmptyView(tv);
    }



    public class DeleteTodoTask extends AsyncTask<TodoBean,Void,Boolean>{


        @Override
        protected Boolean doInBackground(TodoBean... params) {

            return dbfunctions.deleteTodo(params[0]);
        }


        @Override
        protected void onPostExecute(Boolean result) {
            super.onPostExecute(result);
            if(result) {
                todoAdp.clear();
                todoAdp.addAll(dbfunctions.getTodoList());
                todoAdp.notifyDataSetChanged();
            }
        }
    }

    static class MyViewHolder {
        private TextView title;
        private ImageButton delete;
        private ImageButton edit;
        private TextView date;
        private TextView priority;
    }

    class ListItemAdapter extends ArrayAdapter<TodoBean>{
        Context ctx;
        List<TodoBean> todolist;

        public ListItemAdapter(@NonNull Context context, @NonNull List<TodoBean> objects) {
            super(context,R.layout.listview_item, objects);
            ctx = context;
            todolist = objects;
        }


        @Override
        public TodoBean getItem(int i) {
            Log.i(TAG, "TodoActivity ->getItem " + todolist.get(i).getTitle_todo());
            return todolist.get(i);
        }

        @Override
        public long getItemId(int i) {
            return 0;
        }

        @Override
        public View getView(int i, View view, ViewGroup viewGroup) {
            Log.i(TAG, "TodoActivity ->getView " + todolist.get(i).getTitle_todo());
            final int pos = i;

            MyViewHolder holder;

            if(view == null) {
                LayoutInflater li = (LayoutInflater) ctx.getSystemService(Context.LAYOUT_INFLATER_SERVICE);
                view = li.inflate(R.layout.listview_item, null);
                holder = new MyViewHolder();
                holder.title = (TextView) view.findViewById(R.id.tv_title);
                holder.date = (TextView) view.findViewById(R.id.date);
                holder.delete = (ImageButton) view.findViewById(R.id.delete);
                holder.edit = (ImageButton) view.findViewById(R.id.edit);
                holder.priority = (TextView) view.findViewById(R.id.priority);
                view.setTag(holder);
            }
            else {
                holder = (MyViewHolder) view.getTag();
            }

                TodoBean tb = todolist.get(i);

                holder.title.setText(tb.getTitle_todo());

                holder.date.setText(tb.getDate());
                Calendar mycalendar = Calendar.getInstance();
                String myFormat = "MM/dd/yy";
                SimpleDateFormat sdf = new SimpleDateFormat(myFormat, Locale.US);

                try {
                    Date today = sdf.parse(sdf.format(mycalendar.getTime()));
                    Date tododate = sdf.parse(tb.getDate());
                    if (tododate.compareTo(today) < 0) {
                        holder.date.setTextColor(ContextCompat.getColor(ctx,android.R.color.holo_red_dark));
                    }
                    else{
                        holder.date.setTextColor(ContextCompat.getColor(ctx,R.color.colorDarkGreen));
                    }
                } catch (ParseException e) {
                    e.printStackTrace();
                }


                holder.delete.setOnClickListener(new View.OnClickListener() {

                    @Override
                    public void onClick(View view) {
                        final TodoBean tb = todolist.get(pos);
                        AlertDialog.Builder builder = new AlertDialog.Builder(TodoActivity.this);
                        builder.setPositiveButton(getResources().getString(R.string.ok), new DialogInterface.OnClickListener() {
                            @Override
                            public void onClick(DialogInterface dialogInterface, int i) {
                                DeleteTodoTask deleteTask = new DeleteTodoTask();
                                deleteTask.execute(tb);
                            }
                        });

                        builder.setNegativeButton(getResources().getString(R.string.cancel), new DialogInterface.OnClickListener() {
                            @Override
                            public void onClick(DialogInterface dialogInterface, int i) {
                                //Do nothing
                            }
                        });
                        builder.setMessage(getResources().getString(R.string.delete_todo));
                        AlertDialog dialog = builder.create();
                        dialog.show();

                    }
                });

                holder.edit.setOnClickListener(new View.OnClickListener() {
                    @Override
                    public void onClick(View view) {
                        final TodoBean tb = todolist.get(pos);
                        Bundle b = new Bundle();
                        b.putSerializable(ITEM_NAME,tb);
                        editdia = new EditItemDialog();
                        editdia.setArguments(b);
                        editdia.show(fm,"fragment_edit");
                    }
                });

                holder.priority.setText(tb.getPriority());

                if(tb.getPriority().equals("HIGH")){
                    holder.priority.setTextColor(getColor(android.R.color.holo_red_dark));
                }
                if(tb.getPriority().equals("MED")){
                    holder.priority.setTextColor(getColor(android.R.color.holo_orange_light));
                }
                if(tb.getPriority().equals("LOW")){
                    holder.priority.setTextColor(getColor(android.R.color.holo_green_dark));
                }


            return view;
        }
    }

}
