package ir.mreonline.movieinfo;

import android.content.Context;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteOpenHelper;

import androidx.annotation.Nullable;

import java.util.ArrayList;
import java.util.List;

import ir.mreonline.movieinfo.models.MovieSearch;

public class SQLiteHelper extends SQLiteOpenHelper {

    String Table_Name = "FavMovies";

    public SQLiteHelper(@Nullable Context context, @Nullable String name, @Nullable SQLiteDatabase.CursorFactory factory, int version) {
        super(context, name, factory, version);
    }

    @Override
    public void onCreate(SQLiteDatabase db) {
        String Create_Table_Query = "";
        db.execSQL(Create_Table_Query);
    }

    @Override
    public void onUpgrade(SQLiteDatabase db, int oldVersion, int newVersion) {

    }

    public void InsertMovie() {
        String Insert_Movie_Query = "";
        this.getWritableDatabase().execSQL(Insert_Movie_Query);
    }

    public List<MovieSearch> GetMoviesByTitle(String title) {
        ArrayList<MovieSearch> movies = new ArrayList<>();
        String Get_Movies_Query = "";
        Cursor cursor = this.getReadableDatabase().rawQuery(Get_Movies_Query,null);
        while (cursor.moveToNext())
        {
            String Fld1 = cursor.getString(0);
            MovieSearch m =new MovieSearch();

        }
        return movies;
    }
}
