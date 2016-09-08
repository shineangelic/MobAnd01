package it.eng.moband;

import android.content.Context;
import android.database.Cursor;
import android.support.v4.widget.CursorAdapter;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import it.eng.moband.db.CptContract;

/**
 * Created by academy on 08/09/2016.
 */
public class MyCursorAdaper extends CursorAdapter {

    public MyCursorAdaper(Context context, Cursor cursor){
        super(context, cursor, 0);
    }

    @Override
    public View newView(Context context, Cursor cursor, ViewGroup parent) {
        return LayoutInflater.from(context).inflate(R.layout.view_item, parent, false);
    }

    @Override
    public void bindView(View view, Context context, Cursor cursor) {
        TextView area = (TextView) view.findViewById(R.id.area);
        area.setText(cursor.getString(cursor.getColumnIndex(CptContract.CatalogoParametricoTerremoti.COLUMN_NAME_EPICENTRAL_AREA)));
        TextView intensity = (TextView) view.findViewById(R.id.intensity);
        intensity.setText(cursor.getString(cursor.getColumnIndex(CptContract.CatalogoParametricoTerremoti.COLUMN_NAME_INTENSITY)));
    }
}
