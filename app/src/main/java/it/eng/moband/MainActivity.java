package it.eng.moband;

import android.content.Intent;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.os.Bundle;
import android.support.design.widget.FloatingActionButton;
import android.util.Log;
import android.view.ContextMenu;
import android.view.MenuInflater;
import android.view.View;
import android.support.design.widget.NavigationView;
import android.support.v4.view.GravityCompat;
import android.support.v4.widget.DrawerLayout;
import android.support.v7.app.ActionBarDrawerToggle;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.Toolbar;
import android.widget.SearchView;
import android.view.Menu;
import android.view.MenuItem;
import android.widget.AdapterView;
import android.widget.ListView;
import android.widget.TextView;

import java.io.IOException;

import it.eng.moband.Constants.CptConstants;
import it.eng.moband.db.CptQueryHelperClass;
import it.eng.moband.listdetail.DetailRecordActivity;
import it.eng.moband.db.CptHelperClass;

/**
 * Launcher dell'app, Activity che parte con l'avvio della app
 */
public class MainActivity extends AppCompatActivity
        implements NavigationView.OnNavigationItemSelectedListener,
                   SearchView.OnQueryTextListener {

    //Per rimuove la configurazione del proxy da GitHub eseguire i seguenti comandi da prompt
    //git config --global --unset http.proxy
    //git config --global --unset https.proxy

    private SQLiteDatabase db;
    private CptHelperClass cptDatabaseH;
    private CptQueryHelperClass cptQueryHelper;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        new SimpleEula(this).show();
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

        //ottieni DB copiato
        cptDatabaseH = new CptHelperClass(this);
        try {
            cptDatabaseH.preparaDbCopiato();
            db = cptDatabaseH.getWritableDatabase();
            cptQueryHelper = new CptQueryHelperClass(db);
        } catch (IOException e) {
            e.printStackTrace();
        }

        ListView v = (ListView) findViewById(R.id.listView);
        registerForContextMenu(v);
        v.setAdapter(new MyCursorAdaper(MainActivity.this, cptDatabaseH.getRecordsForList(db)));

        v.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView<?> parent, View view, int position, long id) {
                Intent i = new Intent(MainActivity.this, DetailRecordActivity.class);
                i.putExtra(CptConstants.ITEM_ID, id);
                startActivity(i);
            }
        });

        SearchView searchView = (SearchView) findViewById(R.id.filtroLista);
        searchView.setIconifiedByDefault(true);
        searchView.setOnQueryTextListener(this);
    }

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

    }

    @Override
    public void onCreateContextMenu(ContextMenu menu, View v,
                                    ContextMenu.ContextMenuInfo menuInfo) {
        super.onCreateContextMenu(menu, v, menuInfo);
        MenuInflater inflater = getMenuInflater();
        inflater.inflate(R.menu.main_contextual_menu, menu);
    }

    @Override
    public boolean onContextItemSelected(MenuItem item) {
        AdapterView.AdapterContextMenuInfo info = (AdapterView.AdapterContextMenuInfo) item.getMenuInfo();
        switch (item.getItemId()) {
            case R.id.view_on_map:
                try {
                    Intent i = new Intent(MainActivity.this, SingleEventMapActivity.class);
                    i.putExtra(CptConstants.ITEM_ID, Long.valueOf(info.id).longValue());
                    startActivity(i);
                }
                catch(Exception e) {
                    Log.e("MobAND", e.getMessage());
                }

                return true;
        }
        return false;
    }

    @Override
    public boolean onQueryTextSubmit(String query) {
        try {
            Cursor c = cptQueryHelper.getRecordByArea((query != null ? '%' + query + '%' : "@@@@"));
            ListView v = (ListView) findViewById(R.id.listView);
            v.setAdapter(new MyCursorAdaper(MainActivity.this,c));
            return true;
        }
        catch(Exception e){
            Log.e("MobAND", e.getMessage());
            return false;
        }
    }

    @Override
    public boolean onQueryTextChange(String query) {
        try {
            Cursor c = cptQueryHelper.getRecordByArea((query != null ? '%' + query + '%' : "@@@@"));
            ListView v = (ListView) findViewById(R.id.listView);
            v.setAdapter(new MyCursorAdaper(MainActivity.this,c));
            return true;
        }
        catch(Exception e){
            Log.e("MobAND", e.getMessage());
            return false;
        }
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
            Intent intent = new Intent(MainActivity.this,OpzioniActivity.class);
            startActivity(intent);
        } else if (id == R.id.nav_manage) {

        }
        //chiudi drawer e ritorna, abbiamo passato il controllo
        DrawerLayout drawer = (DrawerLayout) findViewById(R.id.drawer_layout);
        drawer.closeDrawer(GravityCompat.START);
        return true;
    }
}
