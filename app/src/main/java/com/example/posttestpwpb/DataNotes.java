package com.example.posttestpwpb;

import android.os.Parcel;
import android.os.Parcelable;

public class DataNotes implements Parcelable {

    String tanggal,judul, deskripsi, noteId;

    public DataNotes(){

    }

    public DataNotes(String tanggal,String judul, String deskripsi, String noteId) {
        this.tanggal = tanggal;
        this.judul = judul;
        this.deskripsi = deskripsi;
        this.noteId = noteId;
    }

    protected DataNotes(Parcel in) {
        tanggal = in.readString();
        judul = in.readString();
        deskripsi = in.readString();
        noteId = in.readString();
    }

    public static final Creator<DataNotes> CREATOR = new Creator<DataNotes>() {
        @Override
        public DataNotes createFromParcel(Parcel in) {
            return new DataNotes(in);
        }

        @Override
        public DataNotes[] newArray(int size) {
            return new DataNotes[size];
        }
    };

    public String getTanggal() {
        return tanggal;
    }

    public void setTanggal(String tanggal) {
        this.tanggal = tanggal;
    }

    public String getJudul() {
        return judul;
    }

    public void setJudul(String judul) {
        this.judul = judul;
    }

    public String getDeskripsi() {
        return deskripsi;
    }

    public void setDeskripsi(String deskripsi) {
        this.deskripsi = deskripsi;
    }

    public String getNoteId() {
        return noteId;
    }

    public void setNoteId(String noteId) {
        this.noteId = noteId;
    }

    @Override
    public int describeContents() {
        return 0;
    }

    @Override
    public void writeToParcel(Parcel parcel, int i) {
        parcel.writeString(tanggal);
        parcel.writeString(judul);
        parcel.writeString(deskripsi);
        parcel.writeString(noteId);
    }
}
