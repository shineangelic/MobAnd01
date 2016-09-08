package it.eng.moband.ListDetail;

import android.content.Intent;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.util.Log;
import android.view.ViewGroup;
import android.widget.TextView;

import java.io.IOException;

import it.eng.moband.R;
import it.eng.moband.db.CptContract;
import it.eng.moband.db.CptHelperClass;
import it.eng.moband.db.CptRecord;
import it.eng.moband.db.QueryHelperClass;






public class DetailRecordActivity extends AppCompatActivity {

    private final String ITEM_ID = "ITEM_ID";
    private int idRecord = -1;

    private SQLiteDatabase db;
    private CptHelperClass cptDatabaseH;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.content_dettaglio);

        Log.d("DetailRecordActivity", "onCreate(...)");

        cptDatabaseH = new CptHelperClass(this);
        try {
            cptDatabaseH.preparaDbCopiato();
        } catch (IOException e) {
            e.printStackTrace();
        }

        Log.d("DetailRecordActivity", "Apre db.");
        db = cptDatabaseH.getWritableDatabase();


        Intent intent = getIntent();

        idRecord = getIdFromIntent(intent);
        Log.d("DetailRecordActivity", "idRecord = " + idRecord);



        if (idRecord > 0)
        {
            QueryHelperClass qhc = new QueryHelperClass(db);

            Log.d("DetailRecordActivity", "recupera record con idRecord = " + idRecord);
            Cursor c = qhc.getRecordById(idRecord);

            CptRecord cr = new CptRecord();
            cr.extractRecord(c); // *** in questo punto si possiede il record da renderizzare
        }

    }


    private void renderDetailRecord(CptRecord cr)
    {
        ((TextView)findViewById(R.id.detail_activity_epicentro)).setText(cr.EPICENTRAL_AREA);
        //((TextView)findViewById(R.id.detail_activity_data_label)).setText(cr.EPICENTRAL_AREA);

        /*
        Log.d("cpt",c.getString( c.getColumnIndex(CptContract.CatalogoParametricoTerremoti._ID)));
        Log.d("cpt",c.getString( c.getColumnIndex(CptContract.CatalogoParametricoTerremoti.COLUMN_NAME_SECT)));
        Log.d("cpt",c.getString( c.getColumnIndex(CptContract.CatalogoParametricoTerremoti.COLUMN_NAME_YEAR)));
        Log.d("cpt",c.getString( c.getColumnIndex(CptContract.CatalogoParametricoTerremoti.COLUMN_NAME_MONTH)));
        Log.d("cpt",c.getString( c.getColumnIndex(CptContract.CatalogoParametricoTerremoti.COLUMN_NAME_DAY)));
        Log.d("cpt",c.getString( c.getColumnIndex(CptContract.CatalogoParametricoTerremoti.COLUMN_NAME_HOUR)));
        Log.d("cpt",c.getString( c.getColumnIndex(CptContract.CatalogoParametricoTerremoti.COLUMN_NAME_MINUTE)));
        Log.d("cpt",c.getString( c.getColumnIndex(CptContract.CatalogoParametricoTerremoti.COLUMN_NAME_EPICENTRAL_AREA)));
        Log.d("cpt",c.getString( c.getColumnIndex(CptContract.CatalogoParametricoTerremoti.COLUMN_NAME_INTENSITY_MAX)));
        */
    }



    private int getIdFromIntent(Intent intent)
    {
        int id = -1;
        try
        {
            id = intent.getIntExtra(ITEM_ID, -1);
        }
        catch (Exception ex)
        {
            id = -1;
        }

        return  id;
    }


}





