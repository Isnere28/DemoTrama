package com.example.demolecturatarjeta.card.object;

public class Anulacion {
    private String PAN;
    private String monto;
    private String fechaExpiracion;
    private String numeroReferenciaEmisor;
    private String codigoAutorizacion;
    private String codigoRespuesta;

    public Anulacion() {
        this.PAN = null;
        this.monto = null;
        this.fechaExpiracion = null;
        this.numeroReferenciaEmisor = null;
        this.codigoAutorizacion = null;
        this.codigoRespuesta = null;
    }

    public String getPAN() {
        return PAN;
    }

    public void setPAN(String PAN) {
        this.PAN = PAN;
    }

    public String getMonto() {
        return monto;
    }

    public void setMonto(String monto) {
        this.monto = monto;
    }

    public String getFechaExpiracion() {
        return fechaExpiracion;
    }

    public void setFechaExpiracion(String fechaExpiracion) {
        this.fechaExpiracion = fechaExpiracion;
    }

    public String getNumeroReferenciaEmisor() {
        return numeroReferenciaEmisor;
    }

    public void setNumeroReferenciaEmisor(String numeroReferenciaEmisor) {
        this.numeroReferenciaEmisor = numeroReferenciaEmisor;
    }

    public String getCodigoAutorizacion() {
        return codigoAutorizacion;
    }

    public void setCodigoAutorizacion(String codigoAutorizacion) {
        this.codigoAutorizacion = codigoAutorizacion;
    }

    public String getCodigoRespuesta() {
        return codigoRespuesta;
    }

    public void setCodigoRespuesta(String codigoRespuesta) {
        this.codigoRespuesta = codigoRespuesta;
    }
}
