package com.cahyonoz.mysqlcrud.model;

import java.io.Serializable;

public class ModelMhs implements Serializable {
    private String id="";
    private String nim="";
    private String nama="";
    private String alamat = "";

    public String getId(){return id;}

    public void setId(String id){this.id = id;}

    public String getNim() {
        return nim;
    }

    public void setNim(String nim) {
        this.nim = nim;
    }

    public String getNama() {
        return nama;
    }

    public void setNama(String nama) {
        this.nama = nama;
    }

    public String getAlamat() {
        return alamat;
    }

    public void setAlamat(String alamat) {
        this.alamat = alamat;
    }

    @Override
    public  String toString(){
        return "mahasiswa{" +
                "id='" + id + '\''+
                ", nim='" + nim + '\''+
                ", nama='" + nama + '\''+
                ", alamat='" + alamat + '\''+
                '}';
    }
}
