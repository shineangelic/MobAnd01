package it.eng.moband.db;

import android.content.Context;
import android.database.Cursor;
import android.database.DatabaseUtils;
import android.database.sqlite.SQLiteDatabase;
import android.util.Log;

import it.eng.moband.MainActivity;
import it.eng.moband.db.CptContract;
import it.eng.moband.exceptions.DbClosedException;
import it.eng.moband.exceptions.NullObjectException;
import it.eng.moband.exceptions.TooManyRecordsException;

/**
 * Created by mamurrone on 08/09/2016.
 */
public class CptQueryHelperClass {

    private SQLiteDatabase mDB;

    public CptQueryHelperClass(SQLiteDatabase db) {
        mDB = db;
    }



    public Cursor getRecordById(long id) throws NullObjectException, DbClosedException {
        if (mDB == null)
            throw new NullObjectException("DB null - it's impossible to get any data.");

        if (!mDB.isOpen())
            throw new DbClosedException("DB closed - it's impossible to get any data.");

        String[] ids = new String[1];
        ids[0] = "" + id;

        Cursor c = mDB.query(CptContract.CatalogoParametricoTerremoti.TABLE_NAME, null, "_ID = ?", ids, null, null, null);

        return c;
    }

    public CptRecord getRecordDaoById(long id) throws NullObjectException, TooManyRecordsException, DbClosedException {
        Cursor c = this.getRecordById(id);

        if (c != null)
            if (c.getCount() == 1)
            {
                CptRecord rec = new CptRecord();
                rec.extractRecord(c);
                return  rec;
            }
        return  null;
    }

    public Cursor getAllRecords() {
        String[] columns = new String[]{
                CptContract.CatalogoParametricoTerremoti._ID,
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

        Cursor data = mDB.query(true, CptContract.CatalogoParametricoTerremoti.TABLE_NAME, columns, null, null, null, null, CptContract.CatalogoParametricoTerremoti.COLUMN_NAME_EPICENTRAL_AREA, null);
        return data;
    }





    public long debugAllRecords() {
        Cursor c = mDB.query(CptContract.CatalogoParametricoTerremoti.TABLE_NAME, null, null, null, null, null, null);
        long rowCount = 0;
        if (c != null) {
            String row;
            c.moveToFirst();
            while (c.moveToNext()) {
                rowCount++;
                row = "";
                Log.d("cpt", "---------------- Row:" + rowCount);
                this.logCurrentRow(c);
            }
        }
        return rowCount;
    }


    private void logCurrentRow(Cursor c) {
        Log.d("cpt", c.getString(c.getColumnIndex(CptContract.CatalogoParametricoTerremoti._ID)));
        Log.d("cpt", c.getString(c.getColumnIndex(CptContract.CatalogoParametricoTerremoti.COLUMN_NAME_SECT)));
        Log.d("cpt", c.getString(c.getColumnIndex(CptContract.CatalogoParametricoTerremoti.COLUMN_NAME_YEAR)));
        Log.d("cpt", c.getString(c.getColumnIndex(CptContract.CatalogoParametricoTerremoti.COLUMN_NAME_MONTH)));
        Log.d("cpt", c.getString(c.getColumnIndex(CptContract.CatalogoParametricoTerremoti.COLUMN_NAME_DAY)));
        Log.d("cpt", c.getString(c.getColumnIndex(CptContract.CatalogoParametricoTerremoti.COLUMN_NAME_HOUR)));
        Log.d("cpt", c.getString(c.getColumnIndex(CptContract.CatalogoParametricoTerremoti.COLUMN_NAME_MINUTE)));
        Log.d("cpt", c.getString(c.getColumnIndex(CptContract.CatalogoParametricoTerremoti.COLUMN_NAME_EPICENTRAL_AREA)));
        Log.d("cpt", c.getString(c.getColumnIndex(CptContract.CatalogoParametricoTerremoti.COLUMN_NAME_INTENSITY_MAX)));
    }

    public Cursor getRecordByArea(String inputText) throws Exception {

        String[] columns = new String[]{
                CptContract.CatalogoParametricoTerremoti._ID,
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

        Cursor c = mDB.query(CptContract.CatalogoParametricoTerremoti.TABLE_NAME, columns,
                CptContract.CatalogoParametricoTerremoti.COLUMN_NAME_INTENSITY + " IS NOT NULL AND " +
                        CptContract.CatalogoParametricoTerremoti.COLUMN_NAME_INTENSITY + " != '' AND " +
                        CptContract.CatalogoParametricoTerremoti.COLUMN_NAME_LATITUDE + " IS NOT NULL AND "+
                        CptContract.CatalogoParametricoTerremoti.COLUMN_NAME_LATITUDE + " != '' AND " +
                        CptContract.CatalogoParametricoTerremoti.COLUMN_NAME_EPICENTRAL_AREA + " LIKE ?", new String[]{inputText}, null, null, CptContract.CatalogoParametricoTerremoti.COLUMN_NAME_EPICENTRAL_AREA, null);
        if (c != null) {
            c.moveToFirst();
        }
        return c;

    }


}
