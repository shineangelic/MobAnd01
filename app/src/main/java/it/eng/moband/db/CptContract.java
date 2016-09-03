package it.eng.moband.db;

import android.provider.BaseColumns;

/**
 * http://emidius.mi.ingv.it/CPTI15-DBMI15/
 * Catalogo Parametrico dei Terremoti Italiani

   CPTI15
   Release v1.5
 * Rovida A., Locati M., Camassi R., Lolli B., Gasperini P. (eds), 2016. CPTI15, the 2015 version of the Parametric Catalogue of Italian Earthquakes.
 * Istituto Nazionale di Geofisica e Vulcanologia. doi:http://doi.org/10.6092/INGV.IT-CPTI15
 *
 * Created by shine@angelic.it on 03/09/2016.
 */
public class CptContract {

        // To prevent someone from accidentally instantiating the contract class,
        // make the constructor private.
        private CptContract() {}

        /* Inner class that defines the table contents */
        public static class CatalogoParametricoTerremoti implements BaseColumns {
            public static final String TABLE_NAME = "cpt15BIS";
            public static final String COLUMN_NAME_SECT = "Sect";
            public static final String COLUMN_NAME_REFNAME = "MainRef";
            public static final String COLUMN_NAME_YEAR = "Year";
            public static final String COLUMN_NAME_MONTH = "Mo";
            public static final String COLUMN_NAME_DAY = "Da";
            public static final String COLUMN_NAME_HOUR = "Ho";
            public static final String COLUMN_NAME_MINUTE = "Mi";
            public static final String COLUMN_NAME_EPICENTRAL_AREA = "EpicentralArea";
            public static final String COLUMN_NAME_INTENSITY_DEF = "IoDef";
            public static final String COLUMN_NAME_INTENSITY_MAX = "Imax";
            public static final String COLUMN_NAME_LATITUDE = "LatDef";
            public static final String COLUMN_NAME_LONGITUDE = "LonDef";
        }

}
