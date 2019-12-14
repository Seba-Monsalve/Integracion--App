package com.example.app_inacap.db;

import android.content.Context;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteOpenHelper;

public class DBAdmin extends SQLiteOpenHelper {

    public DBAdmin(Context context, String name, SQLiteDatabase.CursorFactory factory, int version) {
        super(context, name, factory, version);
    }

    @Override
    public void onCreate(SQLiteDatabase sqLiteDatabase) {



        sqLiteDatabase.execSQL("CREATE TABLE USUARIO(" +
                "USUARIO_ID INTEGER PRIMARY KEY AUTOINCREMENT," +
                "USUARIO_PASSWORD TEXT NOT NULL," +
                "USUARIO_EMAIL TEXT NOT NULL," +
                "USUARIO_NOMBRE TEXT NOT NULL);");

        sqLiteDatabase.execSQL("CREATE TABLE PUBLICACION(" +
                "PUBLICACION_ID INTEGER PRIMARY KEY AUTOINCREMENT," +
                "USUARIO_ID INTEGER NOT NULL," +
                "PUBLICACION_TEXTO TEXT NOT NULL," +
                "CONSTRAINT FK_PUBLICACION" +
                "   FOREIGN KEY (USUARIO_ID)" +
                "   REFERENCES USUARIO(USUARIO_ID));");
    }

    @Override
    public void onUpgrade(SQLiteDatabase sqLiteDatabase, int i, int i1) {
    }
}
