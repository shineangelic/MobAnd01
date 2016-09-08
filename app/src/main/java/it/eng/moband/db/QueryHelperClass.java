package it.eng.moband.db;

import android.content.Context;
import android.database.Cursor;
import android.database.DatabaseUtils;
import android.database.sqlite.SQLiteDatabase;
import android.util.Log;

import it.eng.moband.MainActivity;

/**
 * Created by mamurrone on 08/09/2016.
 */
public class QueryHelperClass {

    private SQLiteDatabase mDB;

    public QueryHelperClass(SQLiteDatabase db)
    {
        mDB = db;
    }



    public Cursor getRecordById(long id)
    {
        String[] ids = new String[1];
        ids[0] = "" + id ;

        Cursor c = mDB.query(CptContract.CatalogoParametricoTerremoti.TABLE_NAME,null,"_ID = ?",ids,null,null,null);

        if (c!= null)
            if (c.getCount() == 1)
                return c;

        return null;
    }

    public Cursor getAllRecords()
    {
        Cursor c = mDB.query(CptContract.CatalogoParametricoTerremoti.TABLE_NAME,null,null,null,null,null,null);
        return c;
    }


}
