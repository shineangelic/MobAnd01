package it.eng.moband.db;

import android.database.Cursor;

import java.io.Serializable;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.Date;

import it.eng.moband.exceptions.NullObjectException;
import it.eng.moband.exceptions.TooManyRecordsException;

/**
 * Created by mamurrone on 08/09/2016.
 */
public class CptRecord implements Serializable{
    public CptRecord() {}

    private String ID = "_ID";
    private String SECT = "Sect";
    private String REFNAME = "MainRef";
    private String YEAR = "Year";
    private String MONTH = "Mo";
    private String DAY = "Da";
    private String HOUR = "Ho";
    private String MINUTE = "Mi";
    private String EPICENTRAL_AREA = "EpicentralArea";
    private String INTENSITY_DEF = "IoDef";
    private String INTENSITY_MAX = "Imax";
    private String LATITUDE = "LatDef";
    private String LONGITUDE = "LonDef";
    private String DEPTH = "DepDef";


    public String getID(){return ID;}
    public void setID(String value){ID=value;}

    public String getSECT() {return SECT;}
    public void setSECT(String value) { SECT=value;}

    public String getREFNAME() {return REFNAME;}
    public void setREFNAME(String value) { REFNAME=value;}

    public String getYEAR() {return YEAR;}
    public void setYEAR(String value) { YEAR=value;}

    public String getMONTH() {return MONTH;}
    public void setMONTH(String value) { MONTH=value;}

    public String getDAY() {return DAY;}
    public void setDAY(String value) { DAY=value;}

    public String getHOUR() {return HOUR;}
    public void setHOUR(String value) { HOUR=value;}

    public String getMINUTE() {return MINUTE;}
    public void setMINUTE(String value) { MINUTE=value;}

    public String getEPICENTRAL_AREA() {return EPICENTRAL_AREA;}
    public void setEPICENTRAL_AREA(String value) {EPICENTRAL_AREA=value;}

    public String getINTENSITY_MAX() {return INTENSITY_MAX;}
    public void setINTENSITY_MAX(String value) { INTENSITY_MAX=value;}

    public String getINTENSITY_DEF() {return INTENSITY_DEF;}
    public void setINTENSITY_DEF(String value) { INTENSITY_DEF=value;}

    public String getLATITUDE() {return LATITUDE;}
    public void setLATITUDE(String value) { LATITUDE=value;}

    public String getLONGITUDE() {return LONGITUDE;}
    public void setLONGITUDE(String value) { LONGITUDE=value;}

    public String getDEPTH() {return DEPTH;}
    public void setDEPTH(String value) { DEPTH=value;}



    public Date getDateTimeQuake() throws ParseException {
        SimpleDateFormat sdf = new SimpleDateFormat("yyyy/MM/dd HH:mm:ss"); // creo l'oggetto
        String dtQuake =  YEAR + "/" + MONTH + "/" + DAY + " " + HOUR + ":" + MINUTE + ":00";
        return sdf.parse(dtQuake);
    }


    public Date getDateQuake() throws ParseException {
        SimpleDateFormat sdf = new SimpleDateFormat("yyyy/MM/dd"); // creo l'oggetto
        String dtQuake =  YEAR + "/" + MONTH + "/" + DAY;
        return sdf.parse(dtQuake);

    }

    // questo è OK
    public String getTimeQuake()
    {
        return HOUR + ":" + MINUTE;
    }

}
