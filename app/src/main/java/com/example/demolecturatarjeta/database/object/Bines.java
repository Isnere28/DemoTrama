package com.example.demolecturatarjeta.database.object;

public class Bines {

    private Integer Codigo;
    private long Inicio;
    private long Fin;
    private String TipoDeTarjeta;
    private String Tarjeta;
    private String Banco;

    public Bines(Integer codigo, long inicio, long fin, String tipoDeTarjeta, String tarjeta, String banco) {
        Codigo = codigo;
        Inicio = inicio;
        Fin = fin;
        TipoDeTarjeta = tipoDeTarjeta;
        Tarjeta = tarjeta;
        Banco = banco;
    }

    public Integer getCodigo() {
        return Codigo;
    }

    public void setCodigo(Integer codigo) {
        Codigo = codigo;
    }

    public long getInicio() {
        return Inicio;
    }

    public void setInicio(long inicio) {
        Inicio = inicio;
    }

    public long getFin() {
        return Fin;
    }

    public void setFin(long fin) {
        Fin = fin;
    }

    public String getTipoDeTarjeta() {
        return TipoDeTarjeta;
    }

    public void setTipoDeTarjeta(String tipoDeTarjeta) {
        TipoDeTarjeta = tipoDeTarjeta;
    }

    public String getTarjeta() {
        return Tarjeta;
    }

    public void setTarjeta(String tarjeta) {
        Tarjeta = tarjeta;
    }

    public String getBanco() {
        return Banco;
    }

    public void setBanco(String banco) {
        Banco = banco;
    }
}
