package com.example.demolecturatarjeta;

import android.util.Log;

public class ConsumeData {
    private static final String TAG = "Prueba - Lectura de tarjeta - Clase: ConsumeData";

    public static final int CONSUME_TYPE_CARD = 1;
    public static final int CONSUME_TYPE_SCAN = 2;

    public static final int CARD_TYPE_MAG = 100;
    public static final int CARD_TYPE_IC = 101;
    public static final int CARD_TYPE_RF = 102;

    private String mAmount;
    private String consume5F34;
    private String documento;
    private String CVV;
    private String tipoDeCuenta;
    private String tipoDeLectura;
    private String tipoDeTarjeta;
    private String authorizationId;
    private String codeResponse;
    private String tipoDeOperacion;
    //
    private String referenciaSeleccionada;
    private String montoSeleccionado;

    private int mConsumeType;
    private int mCardType;

    private String mScanResult;
    private String mCardno;
    private String mSerialNum;
    private String mExpiryData;
    private byte[] mFirstTrackData;
    private String mSecondTrackData;
    private String mThirdTrackData;
    private byte[] mPinBlock;
    private byte[] mICData;
    private byte[] mICPositiveData;
    private byte[] mKsnValue;

    public byte[] getKsnValue() {
        return mKsnValue;
    }

    public void setKsnValue(byte[] ksnValue) {
        mKsnValue = ksnValue;
    }

    public void setConsumeType(int type) {
        mConsumeType = type;
    }

    public int getConsumeType() {
        return mConsumeType;
    }

    public void setAmount(String amount) {
        mAmount = amount;
    }

    public String getAmount() {
        return mAmount;
    }

    public void setCardType(int cardType) {
        mCardType = cardType;
    }

    public int getCardType() {
        return mCardType;
    }

    public void setCardno(String cardno) {
        mCardno = cardno;
    }

    public String getCardno() {
        return mCardno;
    }

    public void setExpiryData(String expiryData) {
        mExpiryData = expiryData;
    }

    public String getExpiryData() {
        return mExpiryData;
    }

    public void setSerialNum(String expiryData) {
        mSerialNum = expiryData;
    }

    public String getSerialNum() {
        return mSerialNum;
    }


    public void setSecondTrackData(String secondTrackData) {
        Log.i("lakaladebug", "secondTrackData = "+secondTrackData);
        mSecondTrackData = secondTrackData;
    }

    public String getSecondTrackData() {
        return mSecondTrackData;
    }

    public void setThirdTrackData(String thirdTrackData) {

        mThirdTrackData = thirdTrackData;
    }

    public String getThirdTrackData() {
        return mThirdTrackData;
    }

    public void setPin(byte[] pin) {
        mPinBlock = pin;
    }

    public byte[] getPin() {
        return mPinBlock;
    }

    public void setICData(byte[] icData) {
        mICData = icData;
    }

    public byte[] getICData() {
        return mICData;
    }

    public void setICPositiveData(byte[] icPositiveData) {
        mICPositiveData = icPositiveData;
    }

    public byte[] getICPositiveData() {
        return mICPositiveData;
    }

    public void setScanResult(String scanResult) {
        mScanResult = scanResult;
    }

    public String getScanResult() {
        return mScanResult;
    }

    public void clearConsumeDate() {
        mAmount = null;
        mConsumeType = 0;
        mCardType = 0;
        mScanResult = null;
        mCardno = null;
        mSerialNum = null;
        mExpiryData = null;
        mFirstTrackData = null;
        mSecondTrackData = null;
        mThirdTrackData = null;
    }

    public String getConsume5F34() {
        return consume5F34;
    }

    public void setConsume5F34(String consume5F34) {
        this.consume5F34 = consume5F34;
    }

    public String getDocumento() {
        return documento;
    }

    public void setDocumento(String documento) {
        this.documento = documento;
    }

    public String getTipoDeLectura() {
        return tipoDeLectura;
    }

    public void setTipoDeLectura(String tipoDeLectura) {
        this.tipoDeLectura = tipoDeLectura;
    }

    public String getTipoDeTarjeta() {
        return tipoDeTarjeta;
    }

    public void setTipoDeTarjeta(String tipoDeTarjeta) {
        this.tipoDeTarjeta = tipoDeTarjeta;
    }

    public String getAuthorizationId() {
        return authorizationId;
    }

    public void setAuthorizationId(String authorizationId) {
        this.authorizationId = authorizationId;
    }

    public String getCodeResponse() {
        return codeResponse;
    }

    public void setCodeResponse(String codeResponse) {
        this.codeResponse = codeResponse;
    }

    public String getTipoDeOperacion() {
        return tipoDeOperacion;
    }

    public void setTipoDeOperacion(String tipoDeOperacion) {
        this.tipoDeOperacion = tipoDeOperacion;
    }

    public String getTipoDeCuenta() {
        return tipoDeCuenta;
    }

    public void setTipoDeCuenta(String tipoDeCuenta) {
        this.tipoDeCuenta = tipoDeCuenta;
    }

    public String getReferenciaSeleccionada() {
        return referenciaSeleccionada;
    }

    public void setReferenciaSeleccionada(String referenciaSeleccionada) {
        this.referenciaSeleccionada = referenciaSeleccionada;
    }

    public String getMontoSeleccionado() {
        return montoSeleccionado;
    }

    public void setMontoSeleccionado(String montoSeleccionado) {
        this.montoSeleccionado = montoSeleccionado;
    }

    public String getCVV() {
        return CVV;
    }

    public void setCVV(String CVV) {
        this.CVV = CVV;
    }

    public byte[] getmFirstTrackData() {
        return mFirstTrackData;
    }

    public void setmFirstTrackData(byte[] mFirstTrackData) {
        this.mFirstTrackData = mFirstTrackData;
    }
}