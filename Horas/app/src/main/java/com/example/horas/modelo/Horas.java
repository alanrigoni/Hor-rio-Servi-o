package com.example.horas.modelo;

public class Horas {

    private String uid;
    private String data;
    private String hora_inicial;
    private String hora_final;
    private String hora_cobrada;


    public Horas() {
    }
    public String getUid(){
        return uid;
    }

    public void setUid(String uid) {
        this.uid = uid;
    }

    public String getData() {
        return data;
    }

    public void setData(String data) {
        this.data = data;
    }

    public String getHora_inicial() {
        return hora_inicial;
    }

    public void setHora_inicial(String hora_inicial) {
        this.hora_inicial = hora_inicial;
    }

    public String getHora_final() {
        return hora_final;
    }

    public void setHora_final(String hora_final) {
        this.hora_final = hora_final;
    }

    public String getHora_cobrada(){ return hora_cobrada;}
    public void setHora_cobrada(String hora_cobrada) {
        this.hora_cobrada = hora_cobrada;
    }


    @Override
    public String toString(){
        return "Data: " + data + "\n" + "Hora Inical: " + hora_inicial+ "\n" + "Hora final: " + hora_final + "\n" + "Hora Cobrada: " + hora_cobrada;

    }
}