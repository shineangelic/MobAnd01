package it.eng.moband.db;

import android.database.Cursor;
import android.provider.BaseColumns;
import android.util.Log;

import java.io.Serializable;

/**
 * Created by mamurrone on 08/09/2016.
 */
public class CptRecord implements Serializable{
    public CptRecord() {}

    //TODO questa classe non è encapsulata. Rendere private le variabili
    //ed aggiungere metodi getter/setter
    public String ID = "_ID";
    public String SECT = "Sect";
    public String REFNAME = "MainRef";
    public String YEAR = "Year";
    public String MONTH = "Mo";
    public String DAY = "Da";
    public String HOUR = "Ho";
    public String MINUTE = "Mi";
    public String EPICENTRAL_AREA = "EpicentralArea";
    public String INTENSITY_DEF = "IoDef";
    public String INTENSITY_MAX = "Imax";
    public String LATITUDE = "LatDef";
    public String LONGITUDE = "LonDef";
    public String DEPTH = "DepDef";

    public void extractRecord(Cursor c)
    {
        c.moveToFirst();
        ID = c.getString( c.getColumnIndex(CptContract.CatalogoParametricoTerremoti._ID));

        SECT = c.getString( c.getColumnIndex(CptContract.CatalogoParametricoTerremoti.COLUMN_NAME_SECT));
        REFNAME = c.getString( c.getColumnIndex(CptContract.CatalogoParametricoTerremoti.COLUMN_NAME_REFNAME));

        YEAR = c.getString( c.getColumnIndex(CptContract.CatalogoParametricoTerremoti.COLUMN_NAME_YEAR));
        MONTH = c.getString( c.getColumnIndex(CptContract.CatalogoParametricoTerremoti.COLUMN_NAME_MONTH));
        DAY = c.getString( c.getColumnIndex(CptContract.CatalogoParametricoTerremoti.COLUMN_NAME_DAY));
        HOUR = c.getString( c.getColumnIndex(CptContract.CatalogoParametricoTerremoti.COLUMN_NAME_HOUR));
        MINUTE = c.getString( c.getColumnIndex(CptContract.CatalogoParametricoTerremoti.COLUMN_NAME_MINUTE));

        EPICENTRAL_AREA = c.getString( c.getColumnIndex(CptContract.CatalogoParametricoTerremoti.COLUMN_NAME_EPICENTRAL_AREA));
        INTENSITY_MAX = c.getString( c.getColumnIndex(CptContract.CatalogoParametricoTerremoti.COLUMN_NAME_INTENSITY_MAX));
        INTENSITY_DEF = c.getString( c.getColumnIndex(CptContract.CatalogoParametricoTerremoti.COLUMN_NAME_INTENSITY_DEF));

        LATITUDE = c.getString( c.getColumnIndex(CptContract.CatalogoParametricoTerremoti.COLUMN_NAME_LATITUDE));
        LONGITUDE = c.getString( c.getColumnIndex(CptContract.CatalogoParametricoTerremoti.COLUMN_NAME_LONGITUDE));
        DEPTH = c.getString( c.getColumnIndex(CptContract.CatalogoParametricoTerremoti.COLUMN_NAME_DEPTH));
    }

    //TODO questo metodo dovrebbe tornare Date pura, da formattare successivamente sulla view
    public String getDateTimeQuake()
    {
        return YEAR + "/" + MONTH + "/" + DAY + " " + HOUR + ":" + MINUTE;
    }
    //TODO come sopra
    public String getDateQuake()
    {
        return YEAR + "/" + MONTH + "/" + DAY;
    }

    // questo è OK
    public String getTimeQuake()
    {
        return HOUR + ":" + MINUTE;
    }

}
