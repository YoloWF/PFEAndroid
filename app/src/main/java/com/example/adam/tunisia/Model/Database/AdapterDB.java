package com.example.adam.tunisia.Model.Database;

import android.content.Context;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteOpenHelper;
import android.util.Log;

public class AdapterDB {

    private static final String TAG = "AdapterDB";

    public static final String DATABASE_NAME = "stuffIOwn";

    public static final int DATABASE_VERSION =23;

    private static final String TABLE_SOCIETE = "societe";
    private static final String CREATE_TABLE_SOCIETE =
            "create table " + TABLE_SOCIETE + " (_id integer primary key autoincrement, "
                    + DBAdapterSociete.DATE+ " TEXT,"
                    + DBAdapterSociete.DETAILS+ " TEXT,"
                    + DBAdapterSociete.FORMEJURIDIQUE+ " TEXT,"
                    + DBAdapterSociete.IDENTIFICATEUR+ " TEXT,"
                    + DBAdapterSociete.LOGIN+ " TEXT,"
                    + DBAdapterSociete.LOGO+ " TEXT,"
                    + DBAdapterSociete.MOTDEPASSE+ " TEXT,"
                    + DBAdapterSociete.NOMCOMPLET+ " TEXT,"
                    + DBAdapterSociete.SIEGESOCIAL+ " TEXT,"
                    + DBAdapterSociete.SIGLE+ " TEXT,"
                    + DBAdapterSociete.SITEWEB+ " TEXT" + ");";

    private static final String TABLE_FEEDBACK = "feedback";
    private static final String CREATE_TABLE_FEEDBACK =
            "create table " + TABLE_FEEDBACK + " (_id integer primary key autoincrement, "
                    + DBAdapterFeedback.DATE+ " TEXT,"
                    + DBAdapterFeedback.NOTE+ " INT,"
                    + DBAdapterFeedback.TEXTE+ " TEXT,"
                    + DBAdapterFeedback.EMAIL+ " TEXT,"
                    + DBAdapterFeedback.SOC_ROW_ID+ " INT " + ");";

    private static final String TABLE_STATION = "station";
    private static final String CREATE_TABLE_STATION =
            "create table " + TABLE_STATION + " (_id integer primary key autoincrement, "
                    + DBAdapterStation.NOM+ " TEXT,"
                    + DBAdapterStation.TYPE+ " TEXT,"
                    + DBAdapterStation.LAT+ " REAL,"
                    + DBAdapterStation.LNG+ " REAL,"
                    + DBAdapterStation.MAJEURE+ " INT,"
                    + DBAdapterStation.SOC_ROW_ID+ " INT " + ");";

    private static final String TABLE_LIGNE = "ligne";
    private static final String CREATE_TABLE_LIGNE =
            "create table " + TABLE_LIGNE + " (_id integer primary key autoincrement, "
                    + DBAdapterLigne.DIRECTION+ " TEXT,"
                    + DBAdapterLigne.IDENTIFIANT+ " TEXT,"
                    + DBAdapterLigne.TYPE+ " TEXT,"
                    + DBAdapterStation.SOC_ROW_ID+ " INT " + ");";



    private final Context context;
    private DatabaseHelper DBHelper;
    protected SQLiteDatabase db;

    private static class DatabaseHelper extends SQLiteOpenHelper
    {
        DatabaseHelper(Context context)
        {
            super(context, DATABASE_NAME, null, DATABASE_VERSION);
        }

        @Override
        public void onCreate(SQLiteDatabase db)
        {   Log.v(TAG, "Database CREATED. onCreate method EXECUTED.");
            db.execSQL(CREATE_TABLE_SOCIETE);
            db.execSQL(CREATE_TABLE_FEEDBACK);
            db.execSQL(CREATE_TABLE_STATION);
            db.execSQL(CREATE_TABLE_LIGNE);
         // Solution de backup manquante
        }

        @Override
        public void onUpgrade(SQLiteDatabase db, int oldVersion,
                              int newVersion)
        {   Log.v(TAG, "Database UPGRADED. onUpgrade method EXECUTED.");
            db.execSQL("DROP TABLE IF EXISTS " + TABLE_SOCIETE);
            db.execSQL("DROP TABLE IF EXISTS " + TABLE_FEEDBACK);
            db.execSQL("DROP TABLE IF EXISTS " + TABLE_STATION);
            db.execSQL("DROP TABLE IF EXISTS " + TABLE_LIGNE);
            this.onCreate(db);

        }
    }

    // _____ CONSTRUCTOR - OPEN - CLOSE _____

    public AdapterDB(Context ctx){
        this.context = ctx;
        this.DBHelper = new DatabaseHelper(this.context);
    }

    public AdapterDB open(){
        this.db = this.DBHelper.getWritableDatabase();
        return this;
    }

    public void close(){
        this.DBHelper.close();
    }


}