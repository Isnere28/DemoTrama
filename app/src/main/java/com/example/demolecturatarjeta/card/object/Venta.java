package com.example.demolecturatarjeta.card.object;

public class Venta {
    private String PAN;
    private String monto;
    private String documento;
    private String fechaExpiracion;
    private String CVV;
    private String numeroReferenciaEmisor;
    private String codigoAutorizacion;
    private String codigoRespuesta;
    private byte[] tramaEMV;
    private byte[] tramaISO;

    public Venta() {
        this.PAN = null;
        this.monto = null;
        this.documento = null;
        this.fechaExpiracion = null;
        this.CVV = null;
        this.numeroReferenciaEmisor = null;
        this.codigoAutorizacion = null;
        this.codigoRespuesta = null;
        this.tramaEMV = null;
        this.tramaISO = null;
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

    public String getDocumento() {
        return documento;
    }

    public void setDocumento(String documento) {
        this.documento = documento;
    }

    public String getFechaExpiracion() {
        return fechaExpiracion;
    }

    public void setFechaExpiracion(String fechaExpiracion) {
        this.fechaExpiracion = fechaExpiracion;
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

    public byte[] getTramaEMV() {
        return tramaEMV;
    }

    public void setTramaEMV(byte[] tramaEMV) {
        this.tramaEMV = tramaEMV;
    }

    public String getNumeroReferenciaEmisor() {
        return numeroReferenciaEmisor;
    }

    public void setNumeroReferenciaEmisor(String numeroReferenciaEmisor) {
        this.numeroReferenciaEmisor = numeroReferenciaEmisor;
    }

    public byte[] getTramaISO() {
        return tramaISO;
    }

    public void setTramaISO(byte[] tramaISO) {
        this.tramaISO = tramaISO;
    }

    public String getCVV() {
        return CVV;
    }

    public void setCVV(String CVV) {
        this.CVV = CVV;
    }

    /////////////ADVICE//////////////
    //PAN +
    //Campo 14 -
    //Campo 32 -
    //Campo 35 -
    //Campo 38 +
    //Campo 39 +
    //Campo 48 -
    ////////////////////////////////

    /////////////REVERSO//////////////
    //PAN +
    //Campo 35 -
    //Campo 48 -
    ////////////////////////////////

}
