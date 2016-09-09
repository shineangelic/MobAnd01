package it.eng.moband;

import android.content.Intent;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.os.Bundle;
import android.support.design.widget.FloatingActionButton;
import android.view.ActionMode;
import android.view.ActionMode.Callback;
import android.view.ContextMenu;
import android.view.MenuInflater;
import android.view.View;
import android.support.design.widget.NavigationView;
import android.support.v4.view.GravityCompat;
import android.support.v4.widget.DrawerLayout;
import android.support.v7.app.ActionBarDrawerToggle;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.Toolbar;
import android.view.Menu;
import android.view.MenuItem;
import android.widget.AdapterView;
import android.widget.ListView;
import android.widget.TextView;

import java.io.IOException;

import it.eng.moband.listdetail.DetailRecordActivity;
import it.eng.moband.db.CptHelperClass;
import it.eng.moband.db.QueryHelperClass;

/**
 * Launcher dell'app, Activity che parte con l'avvio della app
 */
public class MainActivity extends AppCompatActivity
        implements NavigationView.OnNavigationItemSelectedListener {

    private SQLiteDatabase db;
    private CptHelperClass cptDatabaseH;
    private QueryHelperClass qhlp;
    private static final String ITEM_ID = "ITEM_ID";
    private ActionMode mActionMode = null;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        //toolbar dell'applicazione, chiamata 'standard'
        Toolbar toolbar = (Toolbar) findViewById(R.id.toolbar);
        setSupportActionBar(toolbar);

        //FloatButton in basso a Dx
        FloatingActionButton fab = (FloatingActionButton) findViewById(R.id.fab);
        fab.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {

                startMapActivity();
            }
        });

     //   TextView textView = (TextView) findViewById(R.id.valore_magnitudo);
      //  textView.setText(getPreferences(Context.MODE_PRIVATE).getString("seekBar","valore Magnitudo non letto"));

        DrawerLayout drawer = (DrawerLayout) findViewById(R.id.drawer_layout);
        ActionBarDrawerToggle toggle = new ActionBarDrawerToggle(
                this, drawer, toolbar, R.string.navigation_drawer_open, R.string.navigation_drawer_close);
        drawer.setDrawerListener(toggle);
        toggle.syncState();

        NavigationView navigationView = (NavigationView) findViewById(R.id.nav_view);
        navigationView.setNavigationItemSelectedListener(this);


        cptDatabaseH = new CptHelperClass(this);
        try {
            cptDatabaseH.preparaDbCopiato();
            qhlp = new QueryHelperClass(db);
        } catch (IOException e) {
            e.printStackTrace();
        }
        //ottieni DB copiato
        db = cptDatabaseH.getWritableDatabase();
    }

    @Override
    public void onCreateContextMenu(ContextMenu menu, View v,
                                    ContextMenu.ContextMenuInfo menuInfo) {
        super.onCreateContextMenu(menu, v, menuInfo);
    }



    private Callback mActionModeCallback = new Callback() {

        // Called when the action mode is created; startActionMode() was called
        @Override
        public boolean onCreateActionMode(ActionMode mode, Menu menu) {
            //TODO l'inflate dovrebbe avvenire in basso, appena sopra i bottoni "fisici"
            MenuInflater inflater = mode.getMenuInflater();
            inflater.inflate(R.menu.main_contextual_menu, menu);
            return true;
        }

        // Called each time the action mode is shown. Always called after onCreateActionMode, but
        // may be called multiple times if the mode is invalidated.
        @Override
        public boolean onPrepareActionMode(ActionMode mode, Menu menu) {
            return false; // Return false if nothing is done
        }

        // Called when the user selects a contextual menu item
        @Override
        public boolean onActionItemClicked(ActionMode mode, MenuItem item) {
            switch (item.getItemId()) {
                case R.id.view_on_map:
                    AdapterView.AdapterContextMenuInfo info = (AdapterView.AdapterContextMenuInfo) item.getMenuInfo();
                    Cursor c = qhlp.getRecordById(info.id);
                  //  startMapActivity(c.getString(c.getColumnIndex(CptContract.CatalogoParametricoTerremoti.COLUMN_NAME_LATITUDE)),
                 //           c.getString(c.getColumnIndex(CptContract.CatalogoParametricoTerremoti.COLUMN_NAME_LONGITUDE)));
                    mode.finish();
                    return true;
                default:
                    return false;
            }
        }

        // Called when the user exits the action mode
        @Override
        public void onDestroyActionMode(ActionMode mode) {
            mActionMode = null;
        }
    };

    /**
     * passa alla activity mappa
     */
    private void startMapActivity() {
        Intent mostraMappa = new Intent(getApplicationContext(), ItalyMapActivity.class);
        startActivity(mostraMappa);
    }

    @Override
    protected void onStart() {
        super.onStart();
        final TextView textViewConteggio = (TextView) findViewById(R.id.dimensione_DB);


        //forma asincrona DI BASE
        Thread aggiornaConteggio = new Thread(new Runnable() {
            @Override
            public void run() {
                final long dim = cptDatabaseH.getTotalRecords(db);
                //siccome solo il main thread può toccare la UI,
                //tocca fare un altro thread
                runOnUiThread(new Runnable() {
                    @Override
                    public void run() {
                        textViewConteggio.setText("Dimensioni DB: "+ dim);
                    }
                });

            }
        });
        aggiornaConteggio.start();


        ListView lista = (ListView) findViewById(R.id.listView);
        Cursor c = cptDatabaseH.getRecordsForList(db);

        lista.setAdapter(new MyCursorAdaper(MainActivity.this, c));

        lista.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView<?> parent, View view, int position, long id) {
                Intent i = new Intent(MainActivity.this, DetailRecordActivity.class);
                i.putExtra(ITEM_ID, id);
                startActivity(i);
            }
        });

        lista.setOnItemLongClickListener(new AdapterView.OnItemLongClickListener() {
            @Override
            public boolean onItemLongClick(AdapterView<?> parent, View view, int position, long id) {
                if (mActionMode != null) {
                    return false;
                }

                // Start the CAB using the ActionMode.Callback defined above
                mActionMode = startActionMode(mActionModeCallback);
                view.setSelected(true);
                return true;
            }
        });

    }


    @Override
    public void onBackPressed() {
        DrawerLayout drawer = (DrawerLayout) findViewById(R.id.drawer_layout);
        if (drawer.isDrawerOpen(GravityCompat.START)) {
            drawer.closeDrawer(GravityCompat.START);
        } else {
            super.onBackPressed();
        }
    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        // Inflate the menu; this adds items to the action bar if it is present.
        getMenuInflater().inflate(R.menu.main, menu);
        return true;
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        // Handle action bar item clicks here. The action bar will
        // automatically handle clicks on the Home/Up button, so long
        // as you specify a parent activity in AndroidManifest.xml.
        int id = item.getItemId();

        //noinspection SimplifiableIfStatement
        if (id == R.id.action_settings) {
            Intent intent = new Intent(MainActivity.this,OpzioniActivity.class);
            startActivity(intent);
            return true;
        }
        return super.onOptionsItemSelected(item);
    }

    @SuppressWarnings("StatementWithEmptyBody")
    @Override
    public boolean onNavigationItemSelected(MenuItem item) {
        // Handle navigation view item clicks here.
        int id = item.getItemId();

        if (id == R.id.nav_camera) {
            // Handle the camera action
        } else if (id == R.id.map_view) {
            startMapActivity();
        } else if (id == R.id.nav_slideshow) {
            Intent mostraSlides = new Intent(getApplicationContext(), SlidesActivity.class);
            startActivity(mostraSlides);
        } else if (id == R.id.nav_manage) {

        }
        //chiudi drawer e ritorna, abbiamo passato il controllo
        DrawerLayout drawer = (DrawerLayout) findViewById(R.id.drawer_layout);
        drawer.closeDrawer(GravityCompat.START);
        return true;
    }
}
