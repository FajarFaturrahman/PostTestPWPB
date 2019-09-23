package com.example.posttestpwpb;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AlertDialog;
import androidx.appcompat.app.AppCompatActivity;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import android.content.Context;
import android.content.DialogInterface;
import android.content.Intent;
import android.os.Bundle;
import android.os.Parcelable;
import android.view.View;
import android.widget.Toast;

import com.google.android.material.floatingactionbutton.FloatingActionButton;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;

import java.io.Serializable;
import java.util.ArrayList;
import java.util.List;

public class MainActivity extends AppCompatActivity implements RecyclerAdapter.OnUserActionListener{

    RecyclerView recyclerView;
    RecyclerView.LayoutManager layoutManager;
    Context context;
    List<DataNotes> listNoteInfo = new ArrayList<>();
    FloatingActionButton fab;

    DatabaseReference databaseNotes;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        context = this;

        databaseNotes = FirebaseDatabase.getInstance().getReference("notes");

        recyclerView = findViewById(R.id.recyclerview);
        layoutManager = new LinearLayoutManager(context);
        recyclerView.setLayoutManager(layoutManager);
        fab = findViewById(R.id.fab);

        fab.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent intent = new Intent(context, InsertUpdateActivity.class);
                startActivity(intent);
            }
        });


    }


    @Override
    protected void onStart() {
        super.onStart();

        databaseNotes.addValueEventListener(new ValueEventListener() {
            @Override
            public void onDataChange(@NonNull DataSnapshot dataSnapshot) {

                listNoteInfo.clear();

                for (DataSnapshot noteSnapShot : dataSnapshot.getChildren()){

                    DataNotes dataNotes = noteSnapShot.getValue(DataNotes.class);

                    listNoteInfo.add(dataNotes);
                }

                RecyclerAdapter adapter= new RecyclerAdapter(MainActivity.this,listNoteInfo,MainActivity.this);
                recyclerView.setAdapter(adapter);
                adapter.notifyDataSetChanged();



            }

            @Override
            public void onCancelled(@NonNull DatabaseError databaseError) {

            }
        });
    }

    @Override
    public void onUserAction(final DataNotes dataNotes) {

        final CharSequence[] dialogItem = {"Edit", "Delete"};
        AlertDialog.Builder builder = new AlertDialog.Builder(context);
        builder.setTitle("Choose Option");
        builder.setItems(dialogItem, new DialogInterface.OnClickListener() {
            @Override
            public void onClick(DialogInterface dialogInterface, int i) {
                switch (i){
                    case 0 :
                        Intent edit = new Intent(context,InsertUpdateActivity.class);
                        edit.putExtra("UPDATE_INTENT", (Parcelable) dataNotes);
                        edit.putExtra("UPDATE_ACTION", "Edit");
                        startActivity(edit);
                        break;

                    case 1:
                        String id = dataNotes.getNoteId();
                        DatabaseReference drNote = FirebaseDatabase.getInstance().getReference("notes").child(id);

                        drNote.removeValue();

                        onStart();
                    break;
                }
            }
        });

        builder.create().show();
    }


}
