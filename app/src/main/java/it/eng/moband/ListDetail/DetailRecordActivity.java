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
    private long idRecord = -1;

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
            renderDetailRecord(cr);
        }

    }


    private void renderDetailRecord(CptRecord cr)
    {
        ((TextView)findViewById(R.id.detail_activity_epicentro)).setText(cr.EPICENTRAL_AREA);

        ((TextView)findViewById(R.id.detail_activity_data)).setText(cr.getDateQuake());
        ((TextView)findViewById(R.id.detail_activity_ora)).setText(cr.getTimeQuake());
        ((TextView)findViewById(R.id.detail_activity_magnitudo)).setText(cr.INTENSITY_DEF);

        ((TextView)findViewById(R.id.detail_activity_profondita)).setText(cr.DEPTH + " Km");
        ((TextView)findViewById(R.id.detail_activity_longitudine)).setText(cr.LONGITUDE + "°");
        ((TextView)findViewById(R.id.detail_activity_latitudine)).setText(cr.LATITUDE + "°");
    }



    private long getIdFromIntent(Intent intent)
    {
        long id = -1;
        try
        {
            id = (long)intent.getExtras().get(ITEM_ID);
        }
        catch (Exception ex)
        {
            id = -1;
        }

        return  id;
    }


}





