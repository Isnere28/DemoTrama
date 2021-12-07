package com.example.demolecturatarjeta.database.object;

import java.io.Serializable;

public class RespuestaISO implements Serializable {

    private String codigo;
    private String descripcion;

    public RespuestaISO(String codigo, String descripcion) {
        this.codigo = codigo;
        this.descripcion = descripcion;
    }

    public String getCodigo() {
        return codigo;
    }

    public void setCodigo(String codigo) {
        this.codigo = codigo;
    }

    public String getDescripcion() {
        return descripcion;
    }

    public void setDescripcion(String descripcion) {
        this.descripcion = descripcion;
    }
}
