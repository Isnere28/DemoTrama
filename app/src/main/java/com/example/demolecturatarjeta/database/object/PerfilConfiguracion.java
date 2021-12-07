package com.example.demolecturatarjeta.database.object;

import java.io.Serializable;

public class PerfilConfiguracion implements Serializable {

    private String id;
    private String clave;
    private String nombreDelAdquiriente;
    private String logoDelAdquiriente;
    private String codigoDeAdquiriente;
    private String tipoRIFAdquiriente;
    private String numeroRIFAdquiriente;
    private String nombreDelComercio;
    private String direccionDelComercio;
    private String numeroAfiliado;
    private String versionAplicacion;
    private String tipoRIFComercio;
    private String numeroRIFComercio;
    private String direccionIp;
    private String puerto;
    private String terminalId;
    private String currencyCode;
    private String countryCode;
    private String posSerialNumber;
    private String trace;
    private String referencia;
    private String loteCredito;
    private String loteDebito;

    public PerfilConfiguracion(String id, String clave, String nombreDelAdquiriente, String logoDelAdquiriente, String codigoDeAdquiriente, String tipoRIFAdquiriente, String numeroRIFAdquiriente, String nombreDelComercio, String direccionDelComercio, String numeroAfiliado, String versionAplicacion, String tipoRIFComercio, String numeroRIFComercio, String direccionIp, String puerto, String terminalId, String currencyCode, String countryCode, String posSerialNumber, String trace, String referencia, String loteCredito, String loteDebito) {
        this.id = id;
        this.clave = clave;
        this.nombreDelAdquiriente = nombreDelAdquiriente;
        this.logoDelAdquiriente = logoDelAdquiriente;
        this.codigoDeAdquiriente = codigoDeAdquiriente;
        this.tipoRIFAdquiriente = tipoRIFAdquiriente;
        this.numeroRIFAdquiriente = numeroRIFAdquiriente;
        this.nombreDelComercio = nombreDelComercio;
        this.direccionDelComercio = direccionDelComercio;
        this.numeroAfiliado = numeroAfiliado;
        this.versionAplicacion = versionAplicacion;
        this.tipoRIFComercio = tipoRIFComercio;
        this.numeroRIFComercio = numeroRIFComercio;
        this.direccionIp = direccionIp;
        this.puerto = puerto;
        this.terminalId = terminalId;
        this.currencyCode = currencyCode;
        this.countryCode = countryCode;
        this.posSerialNumber = posSerialNumber;
        this.trace = trace;
        this.referencia = referencia;
        this.loteCredito = loteCredito;
        this.loteDebito = loteDebito;
    }

    public String getId() {
        return id;
    }

    public void setId(String id) {
        this.id = id;
    }

    public String getClave() {
        return clave;
    }

    public void setClave(String clave) {
        this.clave = clave;
    }

    public String getNombreDelAdquiriente() {
        return nombreDelAdquiriente;
    }

    public void setNombreDelAdquiriente(String nombreDelAdquiriente) {
        this.nombreDelAdquiriente = nombreDelAdquiriente;
    }

    public String getLogoDelAdquiriente() {
        return logoDelAdquiriente;
    }

    public void setLogoDelAdquiriente(String logoDelAdquiriente) {
        this.logoDelAdquiriente = logoDelAdquiriente;
    }

    public String getCodigoDeAdquiriente() {
        return codigoDeAdquiriente;
    }

    public void setCodigoDeAdquiriente(String codigoDeAdquiriente) {
        this.codigoDeAdquiriente = codigoDeAdquiriente;
    }

    public String getTipoRIFAdquiriente() {
        return tipoRIFAdquiriente;
    }

    public void setTipoRIFAdquiriente(String tipoRIFAdquiriente) {
        this.tipoRIFAdquiriente = tipoRIFAdquiriente;
    }

    public String getNumeroRIFAdquiriente() {
        return numeroRIFAdquiriente;
    }

    public void setNumeroRIFAdquiriente(String numeroRIFAdquiriente) {
        this.numeroRIFAdquiriente = numeroRIFAdquiriente;
    }

    public String getNombreDelComercio() {
        return nombreDelComercio;
    }

    public void setNombreDelComercio(String nombreDelComercio) {
        this.nombreDelComercio = nombreDelComercio;
    }

    public String getDireccionDelComercio() {
        return direccionDelComercio;
    }

    public void setDireccionDelComercio(String direccionDelComercio) {
        this.direccionDelComercio = direccionDelComercio;
    }

    public String getNumeroAfiliado() {
        return numeroAfiliado;
    }

    public void setNumeroAfiliado(String numeroAfiliado) {
        this.numeroAfiliado = numeroAfiliado;
    }

    public String getTipoRIFComercio() {
        return tipoRIFComercio;
    }

    public void setTipoRIFComercio(String tipoRIFComercio) {
        this.tipoRIFComercio = tipoRIFComercio;
    }

    public String getNumeroRIFComercio() {
        return numeroRIFComercio;
    }

    public void setNumeroRIFComercio(String numeroRIFComercio) {
        this.numeroRIFComercio = numeroRIFComercio;
    }

    public String getDireccionIp() {
        return direccionIp;
    }

    public void setDireccionIp(String direccionIp) {
        this.direccionIp = direccionIp;
    }

    public String getPuerto() {
        return puerto;
    }

    public void setPuerto(String puerto) {
        this.puerto = puerto;
    }

    public String getTerminalId() {
        return terminalId;
    }

    public void setTerminalId(String terminalId) {
        this.terminalId = terminalId;
    }

    public String getCurrencyCode() {
        return currencyCode;
    }

    public void setCurrencyCode(String currencyCode) {
        this.currencyCode = currencyCode;
    }

    public String getCountryCode() {
        return countryCode;
    }

    public void setCountryCode(String countryCode) {
        this.countryCode = countryCode;
    }

    public String getPosSerialNumber() {
        return posSerialNumber;
    }

    public void setPosSerialNumber(String posSerialNumber) {
        this.posSerialNumber = posSerialNumber;
    }

    public String getTrace() {
        return trace;
    }

    public void setTrace(String trace) {
        this.trace = trace;
    }

    public String getReferencia() {
        return referencia;
    }

    public void setReferencia(String referencia) {
        this.referencia = referencia;
    }

    public String getLoteCredito() {
        return loteCredito;
    }

    public void setLoteCredito(String loteCredito) {
        this.loteCredito = loteCredito;
    }

    public String getLoteDebito() {
        return loteDebito;
    }

    public void setLoteDebito(String loteDebito) {
        this.loteDebito = loteDebito;
    }

    public String getVersionAplicacion() {
        return versionAplicacion;
    }

    public void setVersionAplicacion(String versionAplicacion) {
        this.versionAplicacion = versionAplicacion;
    }
}
