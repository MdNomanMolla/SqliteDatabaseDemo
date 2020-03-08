package com.example.sqlite_database;

import androidx.appcompat.app.AlertDialog;
import androidx.appcompat.app.AppCompatActivity;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import android.database.Cursor;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.TextView;
import android.widget.Toast;

import com.github.clans.fab.FloatingActionButton;
import com.github.clans.fab.FloatingActionMenu;

import java.util.ArrayList;
import java.util.List;

public class MainActivity extends AppCompatActivity implements View.OnClickListener {
    FloatingActionMenu floatingActionMenu;
    FloatingActionButton createFloatingButton;
    RecycleView recycleView;
    SqliteHelper sqliteHelper;
    RecyclerView recyclerView;
    List<String> arraylist;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        recyclerView = findViewById(R.id.recyclerViewId);
        recyclerView.setLayoutManager(new LinearLayoutManager(MainActivity.this));
        floatingActionMenu = findViewById(R.id.floatingActionMenuId);
        createFloatingButton = findViewById(R.id.floatingCreateId);
        createFloatingButton.setOnClickListener(this);
        sqliteHelper = new SqliteHelper(MainActivity.this);


        Cursor cursor = sqliteHelper.displayData();
        if (cursor.getCount() == 0) {



        }


        arraylist = new ArrayList<String>();
        while (cursor.moveToNext()) {


            arraylist.add(cursor.getString(cursor.getColumnIndex(Variable.NAME)));



        }


        if (arraylist.size() > 0) {
            recycleView = new RecycleView(MainActivity.this, arraylist, new MyClick() {

                TextView updateTextView,deleteTextView;
                AlertDialog alertDialog;
                String Position;
                @Override
                public void OnMyClick(View view, final int position) {
                   // registerForContextMenu(view);
                   AlertDialog.Builder builder=new AlertDialog.Builder(MainActivity.this);
                   LayoutInflater layoutInflater=getLayoutInflater();
                   View view1= layoutInflater.inflate(R.layout.alert_selection_layout,null,false);
                   updateTextView=view1.findViewById(R.id.updateTextViewId);
                   deleteTextView=view1.findViewById(R.id.deleteTextViewId);

                   alertDialog=builder.create();
                   alertDialog.setView(view1);

                    updateTextView.setOnClickListener(new View.OnClickListener() {

                        @Override
                        public void onClick(View view) {




                        }

                    });


                    deleteTextView.setOnClickListener(new View.OnClickListener() {

                        @Override
                        public void onClick(View view) {

                            Position=String.valueOf(position);
                            int id= sqliteHelper.deleteData(Position);
                            if(id==0){
                                Toast.makeText(MainActivity.this, " Not deleted! ", Toast.LENGTH_SHORT).show();
                            }
                            else{
                                Toast.makeText(MainActivity.this, "Deleted! ", Toast.LENGTH_SHORT).show();
                            }

                            alertDialog.dismiss();
                        }

                    });

                  alertDialog.show();


                }
            });
            recyclerView.setAdapter(recycleView);


        }



    }

/*
    @Override
    public void onCreateContextMenu(ContextMenu menu, View v, ContextMenu.ContextMenuInfo menuInfo) {
        super.onCreateContextMenu(menu, v, menuInfo);

        getMenuInflater().inflate(R.menu.menu,menu);
    }*/

    @Override
    public void onClick(View view) {

        final EditText editText;
        Button button;

        if (view.getId() == R.id.floatingCreateId) {

            AlertDialog.Builder builder = new AlertDialog.Builder(MainActivity.this);
            LayoutInflater inflater = getLayoutInflater();
            View view1 = inflater.inflate(R.layout.alertdialoge_layout, null, false);
            editText = view1.findViewById(R.id.titleEditTextId);
            button = view1.findViewById(R.id.okButtonId);
            final AlertDialog alertDialog = builder.create();
            alertDialog.setView(view1);
            button.setOnClickListener(new View.OnClickListener() {
                String title;

                @Override
                public void onClick(View view) {
                    title = editText.getText().toString();
                    if (title.isEmpty()) {
                        editText.setError("Enter Title");
                    } else {

                        long id = sqliteHelper.insertData(title);

                        if (id == -1) {

                            Toast.makeText(MainActivity.this, "Data Not Inserted", Toast.LENGTH_SHORT).show();
                        } else {
                            Toast.makeText(MainActivity.this, "Data  Inserted", Toast.LENGTH_SHORT).show();

                        }
                        alertDialog.dismiss();
                    }


                }
            });

            alertDialog.show();

        }
    }


}
