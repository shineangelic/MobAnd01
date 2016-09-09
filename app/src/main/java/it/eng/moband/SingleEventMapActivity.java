package it.eng.moband;

import android.content.Intent;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.support.v4.app.FragmentActivity;
import android.os.Bundle;

import com.google.android.gms.maps.CameraUpdateFactory;
import com.google.android.gms.maps.GoogleMap;
import com.google.android.gms.maps.OnMapReadyCallback;
import com.google.android.gms.maps.SupportMapFragment;
import com.google.android.gms.maps.model.LatLng;
import com.google.android.gms.maps.model.Marker;
import com.google.android.gms.maps.model.MarkerOptions;

import java.io.IOException;

import it.eng.moband.db.CptContract;
import it.eng.moband.db.CptHelperClass;
import it.eng.moband.db.CptQueryHelperClass;
import it.eng.moband.exceptions.DbClosedException;
import it.eng.moband.exceptions.NullObjectException;
import it.eng.moband.listdetail.DetailRecordActivity;

public class SingleEventMapActivity extends FragmentActivity implements OnMapReadyCallback {

    private GoogleMap mGoogleMap;
    private SQLiteDatabase db;
    private CptHelperClass cptDatabaseH;
    private CptQueryHelperClass cptQueryHelperClass;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_single_event_map);

        Intent i = getIntent();
        final long id_terr = i.getLongExtra("ITEM_ID",0);

        if(id_terr !=0){
            //TODO caricamento asincrono, lanciato subito dopo mapFragment.getMapAsync(this);
            cptDatabaseH = new CptHelperClass(this);
            try {
                cptDatabaseH.preparaDbCopiato();
            } catch (IOException e) {
                e.printStackTrace();
            }
            db = cptDatabaseH.getReadableDatabase();
            try {
                Cursor cursor = cptQueryHelperClass.getRecordById(id_terr);

                if (cursor.moveToFirst()) {
                    do {String latitudine = cursor.getString(cursor.getColumnIndex(CptContract.CatalogoParametricoTerremoti.COLUMN_NAME_LATITUDE)).replace(",",".");
                        String longitudine = cursor.getString(cursor.getColumnIndex(CptContract.CatalogoParametricoTerremoti.COLUMN_NAME_LONGITUDE)).replace(",",".");
                        if(latitudine!=null && !latitudine.equals("") && longitudine!=null && !longitudine.equals("")){
                            MarkerOptions markerOptions = new MarkerOptions();
                            LatLng latLng = new LatLng(Double.parseDouble(latitudine),Double.parseDouble(longitudine));
                            markerOptions.position(latLng);
                            markerOptions.title(cursor.getString(cursor.getColumnIndex(CptContract.CatalogoParametricoTerremoti.COLUMN_NAME_EPICENTRAL_AREA)));
                            mGoogleMap.setOnMarkerClickListener(new GoogleMap.OnMarkerClickListener()
                            {
                                @Override
                                public boolean onMarkerClick(Marker arg0) {
                                    Intent i = new Intent(SingleEventMapActivity.this, DetailRecordActivity.class);
                                    i.putExtra("ITEM_ID", id_terr);
                                    startActivity(i);
                                    return true;
                                }
                            });
                                /*markerOptions.icon(BitmapDescriptorFactory.defaultMarker(BitmapDescriptorFactory.HUE_GREEN));*/
                            mGoogleMap.addMarker(markerOptions);
                            mGoogleMap.setMinZoomPreference(5);
                            mGoogleMap.moveCamera(CameraUpdateFactory.newLatLng(latLng));
                        }
                    } while (cursor.moveToNext());


                }
            } catch (NullObjectException e) {
                e.printStackTrace();
            } catch (DbClosedException e) {
                e.printStackTrace();
            }
        }

        // Obtain the SupportMapFragment and get notified when the map is ready to be used.
        SupportMapFragment mapFragment = (SupportMapFragment) getSupportFragmentManager()
                .findFragmentById(R.id.map);
        mapFragment.getMapAsync(this);
    }


    @Override
    public void onMapReady(GoogleMap googleMap) {
        mGoogleMap = googleMap;

        // Add a marker in Sydney and move the camera
       /* LatLng sydney = new LatLng(-34, 151);
        mGoogleMap.addMarker(new MarkerOptions().position(sydney).title("Marker in Sydney"));
        mGoogleMap.moveCamera(CameraUpdateFactory.newLatLng(sydney));*/


    }
}
