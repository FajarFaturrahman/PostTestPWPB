package com.example.posttestpwpb;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Context;
import android.content.Intent;
import android.os.Bundle;
import android.text.TextUtils;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Toast;

import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;

import java.text.SimpleDateFormat;
import java.util.Date;

public class InsertUpdateActivity extends AppCompatActivity {

    EditText editTextJudul, editTextDesk;
    Button btnSimpan;
    Context context;
    DatabaseReference databaseNotes;
    String nomor = "Submit", submit;
    DataNotes update;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_insert_update);

        context = this;

        databaseNotes = FirebaseDatabase.getInstance().getReference("notes");

        editTextJudul = findViewById(R.id.editTextJudul);
        editTextDesk = findViewById(R.id.editTextDeskripsi);
        btnSimpan = findViewById(R.id.btn_simpan);

        submit = getIntent().getStringExtra("UPDATE_ACTION");
        update = getIntent().getParcelableExtra("UPDATE_INTENT");
        if (submit == null) {
            submit = "Submit";
        } else {
            nomor = String.valueOf(update.getNoteId());
        }


        if (submit.equals("Edit")) {
            btnSimpan.setText("Edit");
            editTextJudul.setText(update.getJudul());
            editTextDesk.setText(update.getDeskripsi());

            Log.d("test", update.getJudul());
        }

        btnSimpan.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                String label = btnSimpan.getText().toString();
                if (label.equals("Simpan")) {
                    TambahNote();
                }

                if (label.equals("Edit")) {
                    String id = update.getNoteId();
                    String judul = editTextJudul.getText().toString().trim();
                    String deskripsi = editTextDesk.getText().toString();
                    SimpleDateFormat tanggal = new SimpleDateFormat("dd/MM/yyyy' 'hh:mm:ss");
                    String tanggalSekarang = tanggal.format(new Date());
                    if (TextUtils.isEmpty(judul)){
                        editTextJudul.setError("Judul Harus Diisi");
                        return;
                    }

                    updateNote(tanggalSekarang,judul,deskripsi,id);
                }
            }
        });

    }



    //tambah
    private void TambahNote() {

        String judul = editTextJudul.getText().toString().trim();
        String deskripsi = editTextDesk.getText().toString();
        SimpleDateFormat tanggal = new SimpleDateFormat("dd/MM/yyyy' 'hh:mm:ss");
        String tanggalSekarang = tanggal.format(new Date());
        if (!TextUtils.isEmpty(judul)) {
            String id = databaseNotes.push().getKey();

            DataNotes dataNotes = new DataNotes(tanggalSekarang, judul, deskripsi, id);

            databaseNotes.child(id).setValue(dataNotes);

            editTextJudul.setText("");
            editTextDesk.setText("");


            Toast.makeText(this, "Data Berhasil Dimasukkan", Toast.LENGTH_SHORT).show();

            Intent pindah = new Intent(context, MainActivity.class);
            context.startActivity(pindah);

        } else {
            Toast.makeText(this, "Masukkan Judul", Toast.LENGTH_SHORT).show();
        }
    }

    private boolean updateNote( String tanggal,String judul, String deskripsi,String id) {

        DatabaseReference databaseReference = FirebaseDatabase.getInstance().getReference("notes").child(id);

        DataNotes dataNotes = new DataNotes(tanggal,judul,deskripsi,id);

        databaseReference.setValue(dataNotes);

        Toast.makeText(this,"Edit Data Berhasil", Toast.LENGTH_SHORT).show();
        Intent pindah = new Intent(context, MainActivity.class);
        context.startActivity(pindah);

        return true;

    }
}
