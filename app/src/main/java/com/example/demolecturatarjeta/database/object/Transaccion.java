package com.example.demolecturatarjeta.database.object;

public class Transaccion {

    private Integer codigo;
    private Long trace;
    private Long referencia;
    private String tipoTarjeta;
    private String tipoCuenta;
    private Long lote;
    private String tipoTransaccion;
    private String transaccionJSON;
    private Integer estado;

    public Transaccion(Integer codigo, Long trace, Long referencia, String tipoTarjeta, String tipoCuenta, Long lote, String tipoTransaccion, String transaccionJSON, Integer estado) {
        this.codigo = codigo;
        this.trace = trace;
        this.referencia = referencia;
        this.tipoTarjeta = tipoTarjeta;
        this.tipoCuenta = tipoCuenta;
        this.lote = lote;
        this.tipoTransaccion = tipoTransaccion;
        this.transaccionJSON = transaccionJSON;
        this.estado = estado;
    }

    public Integer getCodigo() {
        return codigo;
    }

    public void setCodigo(Integer codigo) {
        this.codigo = codigo;
    }

    public Long getTrace() {
        return trace;
    }

    public void setTrace(Long trace) {
        this.trace = trace;
    }

    public Long getReferencia() {
        return referencia;
    }

    public void setReferencia(Long referencia) {
        this.referencia = referencia;
    }

    public String getTipoTarjeta() {
        return tipoTarjeta;
    }

    public void setTipoTarjeta(String tipoTarjeta) {
        this.tipoTarjeta = tipoTarjeta;
    }

    public String getTipoCuenta() {
        return tipoCuenta;
    }

    public void setTipoCuenta(String tipoCuenta) {
        this.tipoCuenta = tipoCuenta;
    }

    public Long getLote() {
        return lote;
    }

    public void setLote(Long lote) {
        this.lote = lote;
    }

    public String getTipoTransaccion() {
        return tipoTransaccion;
    }

    public void setTipoTransaccion(String tipoTransaccion) {
        this.tipoTransaccion = tipoTransaccion;
    }

    public String getTransaccionJSON() {
        return transaccionJSON;
    }

    public void setTransaccionJSON(String transaccionJSON) {
        this.transaccionJSON = transaccionJSON;
    }

    public Integer getEstado() {
        return estado;
    }

    public void setEstado(Integer estado) {
        this.estado = estado;
    }

    /*public Integer getCodigo() {
        return codigo;
    }

    public void setId(Integer codigo) {
        this.codigo = codigo;
    }

    public Long getTrace() {
        return trace;
    }

    public void setTrace(Long trace) {
        this.trace = trace;
    }

    public Long getReferencia() {
        return referencia;
    }

    public void setReferencia(Long referencia) {
        this.referencia = referencia;
    }

    public String getTipoTransaccion() {
        return tipoTransaccion;
    }

    public void setTipoTransaccion(String tipoTransaccion) {
        this.tipoTransaccion = tipoTransaccion;
    }

    public Long getLote() {
        return lote;
    }

    public void setLote(Long lote) {
        this.lote = lote;
    }

    public String getTipoObjeto() {
        return tipoObjeto;
    }

    public void setTipoObjeto(String tipoObjeto) {
        this.tipoObjeto = tipoObjeto;
    }

    public String getObjetoJSON() {
        return objetoJSON;
    }

    public void setObjetoJSON(String objetoJSON) {
        this.objetoJSON = objetoJSON;
    }

    public Integer getEstado() {
        return estado;
    }

    public void setEstado(Integer estado) {
        this.estado = estado;
    }*/
}
