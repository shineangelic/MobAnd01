package it.eng.moband;

import android.content.Intent;
import android.net.Uri;
import android.os.Bundle;
import android.support.design.widget.FloatingActionButton;
import android.support.design.widget.Snackbar;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.CardView;
import android.support.v7.widget.Toolbar;
import android.view.View;
import android.webkit.WebSettings;
import android.webkit.WebView;
import android.webkit.WebViewClient;

public class SlidesActivity extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_slide_show);
        Toolbar toolbar = (Toolbar) findViewById(R.id.toolbar);
        setSupportActionBar(toolbar);

        CardView wid1 = (CardView) findViewById(R.id.card_view1);
        wid1.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent i = new Intent(Intent.ACTION_VIEW);
                i.setData(Uri.parse("https://drive.google.com/open?id=1ckCnNotL7BB4DSbRrxMYKlKkYcGTFiQNboKALmfJcKY"));
                startActivity(i);
            }
        });

        CardView wid2 = (CardView) findViewById(R.id.card_view2);
        wid2.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent i = new Intent(Intent.ACTION_VIEW);
                i.setData(Uri.parse("https://drive.google.com/open?id=1HxPKFzxbvI9U8cSbKznPD82vxKGkQqZ4qEBZIh491_M"));
                startActivity(i);
            }
        });

        CardView wid3 = (CardView) findViewById(R.id.card_view3);
        wid3.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent i = new Intent(Intent.ACTION_VIEW);
                i.setData(Uri.parse("https://drive.google.com/open?id=1prHLI_9mKMclEdMkUeSYXxeXm4pRg2zejkutkc60MV0"));
                startActivity(i);
            }
        });

        CardView wid4 = (CardView) findViewById(R.id.card_view4);
        wid4.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent i = new Intent(Intent.ACTION_VIEW);
                i.setData(Uri.parse("https://drive.google.com/open?id=1xPSqksYAhrcYWP--LYXBGK-V1NH4VtW9prJ_jtY9hEk"));
                startActivity(i);
            }
        });

        CardView wid5 = (CardView) findViewById(R.id.card_view5);
        wid5.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent i = new Intent(Intent.ACTION_VIEW);
                i.setData(Uri.parse("https://drive.google.com/open?id=1axcyy_UG3dufzJv7-YzwxM_V83QnQLGDqoOlkwI4lJ4"));
                startActivity(i);
            }
        });

        CardView wid6 = (CardView) findViewById(R.id.card_view6);
        wid6.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent i = new Intent(Intent.ACTION_VIEW);
                i.setData(Uri.parse("https://drive.google.com/open?id=1Kf2gk0Ya4h8ToKx51eQecCRqyyWU-7wa8wmFw9YgpiU"));
                startActivity(i);
            }
        });

        CardView wid7 = (CardView) findViewById(R.id.card_view7);
        wid7.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent i = new Intent(Intent.ACTION_VIEW);
                i.setData(Uri.parse("https://drive.google.com/open?id=18-YR6oSjdpwlpd2QDpIgl0oaoFokPThr3q0lGQX1lDA"));
                startActivity(i);
            }
        });

        CardView wid8 = (CardView) findViewById(R.id.card_view8);
        wid8.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent i = new Intent(Intent.ACTION_VIEW);
                i.setData(Uri.parse("https://drive.google.com/open?id=18Z14Qct_2qHsmD3_3gduEZYnhN7L09p_XfTUncDw2Xw"));
                startActivity(i);
            }
        });

        CardView wid9 = (CardView) findViewById(R.id.card_view9);
        wid9.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent i = new Intent(Intent.ACTION_VIEW);
                i.setData(Uri.parse("https://drive.google.com/open?id=1-4mfPQraQtuUvVY1VtSVvdHoUlIt7yv7vXKIxcvXdMw"));
                startActivity(i);
            }
        });

        CardView wid10 = (CardView) findViewById(R.id.card_view10);
        wid10.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent i = new Intent(Intent.ACTION_VIEW);
                i.setData(Uri.parse("https://drive.google.com/open?id=1fOwiLRz_p51zxgurOL0KiTUGWLJiSs3E43Sz751nTGk"));
                startActivity(i);
            }
        });

        CardView wid11 = (CardView) findViewById(R.id.card_view11);
        wid11.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent i = new Intent(Intent.ACTION_VIEW);
                i.setData(Uri.parse("https://drive.google.com/open?id=10SMe0FQHxleXvnxfNPqFzxg7FWZkHlVBbhmKTGCRgbI"));
                startActivity(i);
            }
        });

        CardView wid12 = (CardView) findViewById(R.id.card_view12);
        wid12.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent i = new Intent(Intent.ACTION_VIEW);
                i.setData(Uri.parse("https://drive.google.com/open?id=1s3-r3rJI7VueeYX6C_Bo_58HEj6yr9GeLEl_3F4HYtg"));
                startActivity(i);
            }
        });

        CardView wid13 = (CardView) findViewById(R.id.card_view13);
        wid13.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent i = new Intent(Intent.ACTION_VIEW);
                i.setData(Uri.parse("https://drive.google.com/open?id=1Bx4ABWpT7pFQrHBQsIr_GsezBzdwpUSwi7K3QmRy0wk"));
                startActivity(i);
            }
        });

        CardView wid14 = (CardView) findViewById(R.id.card_view14);
        wid14.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent i = new Intent(Intent.ACTION_VIEW);
                i.setData(Uri.parse("https://drive.google.com/open?id=1HBuE_zFyTRjKKXjy8olyxa6ltN5Aw6a5t-hL-0i1Pvg"));
                startActivity(i);
            }
        });

        //Esercizio: Mostrare le slides contenute in https://drive.google.com/open?id=0B-pzmp8AqC8JV3ZJanl3dzQyOWc
        // con una breve descrizione all'interno di CardViews

    }

}
