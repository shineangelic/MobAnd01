package it.eng.moband.db;

import android.content.Context;
import android.database.Cursor;
import android.database.DatabaseUtils;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteOpenHelper;
import android.util.Log;

import java.io.FileOutputStream;
import java.io.IOException;
import java.io.InputStream;
import java.io.OutputStream;

/**
 * Created by shine@angelic.it on 30/08/2016.
 */
public class CptHelperClass extends SQLiteOpenHelper {

    private static String DB_NAME = "cpt";

    private final Context myContext;
    private String dbPath;

    /**
     * Constructor
     * Takes and keeps a reference of the passed context in order to access to the application assets and resources.
     *
     * @param context
     */
    public CptHelperClass(Context context) {

        super(context, DB_NAME, null, 1);
        this.myContext = context;
        this.dbPath = "/data/data/" + myContext.getPackageName() + "/databases/" + DB_NAME;
    }

    public long getTotalRecords(SQLiteDatabase db) {
        //contatutto
        long pip = DatabaseUtils.queryNumEntries(db, CptContract.CatalogoParametricoTerremoti.TABLE_NAME, null, null);
        Log.i("MOBAND", "getTotalRecords():" + pip);
        return pip;
    }

    public Cursor getRecords(SQLiteDatabase db) {
        //contatutto
        String[] columns = new String[]{"_id",
                CptContract.CatalogoParametricoTerremoti.COLUMN_NAME_SECT,
                CptContract.CatalogoParametricoTerremoti.COLUMN_NAME_REFNAME,
                CptContract.CatalogoParametricoTerremoti.COLUMN_NAME_YEAR,
                CptContract.CatalogoParametricoTerremoti.COLUMN_NAME_MONTH,
                CptContract.CatalogoParametricoTerremoti.COLUMN_NAME_DAY,
                CptContract.CatalogoParametricoTerremoti.COLUMN_NAME_MINUTE,
                CptContract.CatalogoParametricoTerremoti.COLUMN_NAME_EPICENTRAL_AREA,
                CptContract.CatalogoParametricoTerremoti.COLUMN_NAME_INTENSITY_DEF,
                CptContract.CatalogoParametricoTerremoti.COLUMN_NAME_INTENSITY_MAX,
                CptContract.CatalogoParametricoTerremoti.COLUMN_NAME_LATITUDE,
                CptContract.CatalogoParametricoTerremoti.COLUMN_NAME_LONGITUDE,
                CptContract.CatalogoParametricoTerremoti.COLUMN_NAME_INTENSITY
        };

        Cursor data = db.query(true, CptContract.CatalogoParametricoTerremoti.TABLE_NAME, columns, null, null, null, null, CptContract.CatalogoParametricoTerremoti.COLUMN_NAME_EPICENTRAL_AREA, null);

       /* data.moveToFirst();
        for (int i=0; i<data.getCount(); i++) {
            Log.i("MOBAND", "Epicentro:" + data.getString(6) + ", Magnitudo Max: " + data.getString(8));
            data.moveToNext();
        }*/
        return data;
    }

    public Cursor getRecordsForList(SQLiteDatabase db) {
        //contatutto
        String[] columns = new String[]{"_id",
                CptContract.CatalogoParametricoTerremoti.COLUMN_NAME_EPICENTRAL_AREA,
                CptContract.CatalogoParametricoTerremoti.COLUMN_NAME_INTENSITY
        };

        Cursor data = db.query(
                CptContract.CatalogoParametricoTerremoti.TABLE_NAME,
                columns,
                CptContract.CatalogoParametricoTerremoti.COLUMN_NAME_INTENSITY + " IS NOT NULL AND " +
                        CptContract.CatalogoParametricoTerremoti.COLUMN_NAME_INTENSITY + " != '' AND " +
                        CptContract.CatalogoParametricoTerremoti.COLUMN_NAME_LATITUDE + " IS NOT NULL AND "+
                        CptContract.CatalogoParametricoTerremoti.COLUMN_NAME_LATITUDE + " != '' " ,
                null,
                null,
                null,
                CptContract.CatalogoParametricoTerremoti.COLUMN_NAME_EPICENTRAL_AREA,
                null);

       /* data.moveToFirst();
        for (int i=0; i<data.getCount(); i++) {
            Log.i("MOBAND", "Epicentro:" + data.getString(6) + ", Magnitudo Max: " + data.getString(8));
            data.moveToNext();
        }*/
        return data;
    }

    /**
     * Creates a empty database on the system and rewrites it with your own database.
     */
    public void preparaDbCopiato() throws IOException {

        if (!checkDataBase()) {//SSE non esiste
            Log.i("MOBAND", "CREATE DB");

            //Crea stub per poi sovrascriverlo
            //hack, perche mettere la copia fisica in onCreate() non si puo`
            this.getReadableDatabase();


            copyDataBase();

        }

    }

    /**
     * Controlla se il DB esiste sul path settato in costruzione
     *
     * @return true if it exists, false if it doesn't
     */
    private boolean checkDataBase() {

        SQLiteDatabase checkDB = null;

        boolean ret;
        try {
            checkDB = SQLiteDatabase.openDatabase(dbPath, null, SQLiteDatabase.OPEN_READONLY);
            if (getTotalRecords(checkDB) <= 0)
                ret = false;
            checkDB.close();
            ret = true;
        } catch (Exception e) {
            Log.w("MOBAND", "IL DB NON ESISTE? " + e.getMessage());
            ret = false;
        }
        return ret;
    }

    /**
     * Copia a basso livello per sovrascrivere il DB creato
     * NON proprio accademico, ma funziona
     */
    private void copyDataBase() throws IOException {

        //Copia read-only in assets/
        InputStream myInput = myContext.getAssets().open(DB_NAME);

        // Path ricavato da onCreate()
        String outFileName = dbPath;

        //alla vecchia
        OutputStream myOutput = new FileOutputStream(outFileName);
        byte[] buffer = new byte[1024];
        int length;
        while ((length = myInput.read(buffer)) > 0) {
            myOutput.write(buffer, 0, length);
        }
        Log.i("MOBAND", "COPIA FISICA FILE DB SU: " + outFileName);
        myOutput.flush();
        myOutput.close();
        myInput.close();
    }

    @Override
    public void onCreate(SQLiteDatabase db) {
        Log.w("MOBAND", "DB onCreate() vuota");
    }

    @Override
    public void onUpgrade(SQLiteDatabase db, int oldVersion, int newVersion) {
        Log.w("MOBAND", "DB onUpgrade() vuota");
    }

}


