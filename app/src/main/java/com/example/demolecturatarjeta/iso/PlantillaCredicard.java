package com.example.demolecturatarjeta.iso;

import org.jpos.iso.IFA_NUMERIC;
import org.jpos.iso.IFB_BITMAP;
import org.jpos.iso.IFB_NUMERIC;
import org.jpos.iso.ISOBasePackager;
import org.jpos.iso.ISOFieldPackager;

public class PlantillaCredicard extends ISOBasePackager {

    public ISOFieldPackager fld[] = {
                                    /*000*/ new IFA_NUMERIC(2, "Message Type Indicator"),
                                    /*001*/ new IFB_BITMAP(16, "Bitmap"),
                                    /*002*/ new IFB_NUMERIC(),//PAN
                                    /*003*/ new IFA_NUMERIC(3, "Processing Code"),
                                    /*004*/ new IFB_NUMERIC(),//Monto
            /*005*/ new IFA_NUMERIC(3, "Processing Code"),
            /*006*/ new IFA_NUMERIC(3, "Processing Code"),
            /*007*/ new IFA_NUMERIC(3, "Processing Code"),
            /*008*/ new IFA_NUMERIC(3, "Processing Code"),
            /*019*/ new IFA_NUMERIC(3, "Processing Code"),
            /*010*/ new IFA_NUMERIC(3, "Processing Code"),
                                    /*011*/ new IFB_NUMERIC(),//Trace
                                    /*012*/ new IFA_NUMERIC(3, "Local Time"),
                                    /*013*/ new IFA_NUMERIC(2, "Local Date"),
                                    /*014*/ new IFB_NUMERIC(),//Fecha De Vencimiento
            /*015*/ new IFA_NUMERIC(3, "Processing Code"),
            /*016*/ new IFA_NUMERIC(3, "Processing Code"),
            /*017*/ new IFA_NUMERIC(3, "Processing Code"),
            /*018*/ new IFA_NUMERIC(3, "Processing Code"),
            /*019*/ new IFA_NUMERIC(3, "Processing Code"),
            /*020*/ new IFA_NUMERIC(3, "Processing Code"),
            /*021*/ new IFA_NUMERIC(3, "Processing Code"),
                                    /*022*/ new IFA_NUMERIC(2, "POS Entry Mode"),
                                    /*023*/ new IFA_NUMERIC(2, "Card Sequence Number"),
                                    /*024*/ new IFA_NUMERIC(2, "NII"),
                                    /*025*/ new IFA_NUMERIC(1, "POS Condition Code"),
            /*026*/ new IFA_NUMERIC(3, "Processing Code"),
            /*027*/ new IFA_NUMERIC(3, "Processing Code"),
            /*028*/ new IFA_NUMERIC(3, "Processing Code"),
            /*029*/ new IFA_NUMERIC(3, "Processing Code"),
            /*030*/ new IFA_NUMERIC(3, "Processing Code"),
            /*031*/ new IFA_NUMERIC(3, "Processing Code"),
                                    /*032*/ new IFB_NUMERIC(),//Codigo De Adquiriente
            /*033*/ new IFA_NUMERIC(3, "Processing Code"),
            /*034*/ new IFA_NUMERIC(3, "Processing Code"),
                                    /*035*/ new IFA_NUMERIC(21,"Track 2"),
            /*036*/ new IFA_NUMERIC(3, "Processing Code"),
                                    /*037*/ new IFA_NUMERIC(12, "RRN"),
                                    /*038*/ new IFA_NUMERIC(6, "Codigo De Autorizaci??n"),
                                    /*039*/ new IFA_NUMERIC(2, "Response Code"),
                                    /*040*/ new IFA_NUMERIC(8, "Terminal Serial"),
                                    /*041*/ new IFA_NUMERIC(8, "Campo 41 Terminal Bancario"),
                                    /*042*/ new IFA_NUMERIC(15, "Campo 42 Numero de Afiliado"),
            /*043*/ new IFA_NUMERIC(3, "Processing Code"),
            /*044*/ new IFA_NUMERIC(3, "Processing Code"),
            /*045*/ new IFA_NUMERIC(3, "Processing Code"),
            /*046*/ new IFA_NUMERIC(3, "Processing Code"),
                                    /*047*/ new IFA_NUMERIC(12, "Campo 47"),
                                    /*048*/ new IFA_NUMERIC(16, "CVC2/CVV2 ??? CEDULA DE IDENTIDAD"),
                                    /*049*/ new IFA_NUMERIC(3, "Codigo De Moneda"),
            /*050*/ new IFA_NUMERIC(3, "Processing Code"),
            /*051*/ new IFA_NUMERIC(3, "Processing Code"),
            /*052*/ new IFA_NUMERIC(8, "Processing Code"),
            /*053*/ new IFA_NUMERIC(3, "Processing Code"),
            /*054*/ new IFA_NUMERIC(3, "Processing Code"),
                                    /*055*/ new IFA_NUMERIC(132, "Data EMV"),
                                    /*056*/ new IFA_NUMERIC(6, "Literal de Moneda"),//Tiene quemada la longitud
                                    /*057*/ new IFA_NUMERIC(41, "Data Adicional POS"),
            /*058*/ new IFA_NUMERIC(3, "Processing Code"),
            /*059*/ new IFA_NUMERIC(3, "Processing Code"),
            /*060*/ new IFA_NUMERIC(8, "Numero Lote"),
            /*061*/ new IFA_NUMERIC(3, "Processing Code"),
                                    /*062*/ new IFA_NUMERIC(8, "Numero De Referencia"),//Tiene quemada la longitud
                                    /*063*/ new IFA_NUMERIC(62, "DUKPT KEY SERIAL NUMBER (KSN)")};//Tiene quemada la longitud
    public PlantillaCredicard() {
        super();
        setFieldPackager(fld);
    }
}