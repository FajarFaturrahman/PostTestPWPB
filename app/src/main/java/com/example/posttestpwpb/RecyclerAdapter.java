package com.example.posttestpwpb;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextClock;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import java.util.ArrayList;
import java.util.List;

public class RecyclerAdapter extends RecyclerView.Adapter<RecyclerAdapter.UserViewHolder> {

    Context context;
    OnUserActionListener Listener;
    List<DataNotes> listNoteInfo = new ArrayList<>();

    public RecyclerAdapter(Context context, List<DataNotes> listNoteInfo, OnUserActionListener listener) {
        this.context = context;
        this.listNoteInfo = listNoteInfo;
        this.Listener = listener;
    }

    public interface OnUserActionListener {
        void onUserAction(DataNotes dataNotes);
    }

    @NonNull
    @Override
    public UserViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View v = LayoutInflater.from(parent.getContext()).inflate(R.layout.notes_row_item, parent, false);
        UserViewHolder userViewHolder = new UserViewHolder(v);

        return userViewHolder;
    }

    @Override
    public void onBindViewHolder(@NonNull UserViewHolder holder, int position) {
        final DataNotes currentNote = listNoteInfo.get(position);

        holder.txtJudul.setText(currentNote.getJudul());
        holder.txtDeskripsi.setText(currentNote.getDeskripsi());
        holder.txtJam.setText(currentNote.getTanggal());
        holder.itemView.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Listener.onUserAction(currentNote);
            }
        });
    }

    @Override
    public int getItemCount() {

        return listNoteInfo.size();
    }

    public class UserViewHolder extends RecyclerView.ViewHolder {

        TextView txtJudul, txtDeskripsi,txtJam;


        public UserViewHolder(@NonNull View itemView) {
            super(itemView);
            txtJam = itemView.findViewById(R.id.date);
            txtJudul = itemView.findViewById(R.id.txt_judul);
            txtDeskripsi = itemView.findViewById(R.id.txt_deskripsi);
        }
    }
}
