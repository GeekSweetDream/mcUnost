package com.dreamsofpines.mcunost.data.database;

import android.content.Context;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteQueryBuilder;
import android.util.Log;

import com.dreamsofpines.mcunost.data.storage.help.menu.InformExcursion;
import com.readystatesoftware.sqliteasset.SQLiteAssetHelper;

import java.util.ArrayList;
import java.util.List;

/**
 * Created by ThePupsick on 04.08.17.
 */

public class MyDataBase extends SQLiteAssetHelper {
    private static final String DATABASE_NAME = "mcUnost.db";
    private static final int DATABASE_VERSION = 1;

    public MyDataBase(Context context){
        super(context, DATABASE_NAME, null, DATABASE_VERSION);
    }

    public List<InformExcursion> getCategories(){
        SQLiteDatabase db = getReadableDatabase();
        SQLiteQueryBuilder qb = new SQLiteQueryBuilder();
        String [] sqSelect = {"0 _id", "name"};
        String sqlTables = "category";
        qb.setTables(sqlTables);
        Cursor cursor = qb.query(db,sqSelect,null,null,null,null,null);
        cursor.moveToFirst();
        final List<InformExcursion> excursions = new ArrayList<>();
        while(cursor.moveToNext()){
            excursions.add(new InformExcursion(cursor.getString(cursor.getColumnIndex("name")),cursor.getString(cursor.getColumnIndex("name"))));
        }
        return excursions;
    }

    public List<InformExcursion> getPackExcursion(String category){

        String query = "select category.name, pack_excur.name_excursion, pack_excur.cost from un_category_pack_excur" +
                "    left join category" +
                "        on un_category_pack_excur.fk_categor = category._id" +
                "    left join pack_excur" +
                "        on un_category_pack_excur.fk_excur = pack_excur._id" +
                "    where category.name = \""+category +"\"";

        SQLiteDatabase db = getReadableDatabase();
        Cursor cursor = db.rawQuery(query,null);
        Log.i("Myapp",""+cursor.getCount());
        cursor.moveToFirst();
        final List<InformExcursion> excursions = new ArrayList<>();
        boolean fl = true;
        while(fl){
            excursions.add(new InformExcursion(cursor.getString(cursor.getColumnIndex("name_excursion")),""+cursor.getInt(cursor.getColumnIndex("cost"))));
            fl = cursor.moveToNext();
        }
        cursor.close();
        return excursions;
    }
}
//