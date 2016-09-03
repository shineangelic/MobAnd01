package it.eng.moband;

import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.icu.text.DecimalFormat;
import android.icu.text.DecimalFormatSymbols;
import android.support.v4.app.FragmentActivity;
import android.os.Bundle;
import android.util.Log;

import com.google.android.gms.maps.CameraUpdateFactory;
import com.google.android.gms.maps.GoogleMap;
import com.google.android.gms.maps.OnMapReadyCallback;
import com.google.android.gms.maps.SupportMapFragment;
import com.google.android.gms.maps.model.LatLng;
import com.google.android.gms.maps.model.MarkerOptions;


import it.eng.moband.db.CptContract;
import it.eng.moband.db.CptHelperClass;

public class ItalyMapActivity extends FragmentActivity implements OnMapReadyCallback {

    private GoogleMap mMap;
    private CptHelperClass cptDatabaseH;
    private SQLiteDatabase db;



    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_italy_map);
        // Obtain the SupportMapFragment and get notified when the map is ready to be used.
        SupportMapFragment mapFragment = (SupportMapFragment) getSupportFragmentManager()
                .findFragmentById(R.id.map);
        mapFragment.getMapAsync(this);
        cptDatabaseH = new CptHelperClass(this);
    }


    /**
     * Manipulates the map once available.
     * This callback is triggered when the map is ready to be used.
     * This is where we can add markers or lines, add listeners or move the camera. In this case,
     * we just add a marker near Sydney, Australia.
     * If Google Play services is not installed on the device, the user will be prompted to install
     * it inside the SupportMapFragment. This method will only be triggered once the user has
     * installed Google Play services and returned to the app.
     */
    @Override
    public void onMapReady(GoogleMap googleMap) {
        mMap = googleMap;

        // Add a marker in Sydney and move the camera
        LatLng roma = new LatLng(41.891, 12.511);
        mMap.addMarker(new MarkerOptions().position(roma).title("Roma"));
        mMap.moveCamera(CameraUpdateFactory.newLatLng(roma));
        mMap.setMinZoomPreference(5);


        db = cptDatabaseH.getWritableDatabase();
        Cursor testCur = cptDatabaseH.getAll(db);

        testCur.moveToFirst();
        while (!testCur.isLast()){
            LatLng equak = new LatLng(Float.parseFloat(testCur.getString( testCur.getColumnIndex(CptContract.CatalogoParametricoTerremoti.COLUMN_NAME_LATITUDE)).replace(",",".") ),
                    Float.parseFloat(testCur.getString(testCur.getColumnIndex(CptContract.CatalogoParametricoTerremoti.COLUMN_NAME_LONGITUDE)).replace(",","."))) ;
            mMap.addMarker(new MarkerOptions().position(equak).title(testCur.getString( testCur.getColumnIndex(CptContract.CatalogoParametricoTerremoti.COLUMN_NAME_EPICENTRAL_AREA) )));
            Log.i("MOBAND",
                    testCur.getString( testCur.getColumnIndex(CptContract.CatalogoParametricoTerremoti.COLUMN_NAME_YEAR) ) +"-"+
                            testCur.getString( testCur.getColumnIndex(CptContract.CatalogoParametricoTerremoti.COLUMN_NAME_EPICENTRAL_AREA) )+
                            " MwDef:"+ testCur.getString( testCur.getColumnIndex(CptContract.CatalogoParametricoTerremoti.COLUMN_NAME_INTENSITY_DEF) )
            );
            testCur.moveToNext();
        }

    }
}
