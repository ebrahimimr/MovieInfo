package ir.mreonline.movieinfo;

import android.content.ContentValues;
import android.content.Context;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteOpenHelper;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;

import java.util.ArrayList;
import java.util.Collection;
import java.util.Iterator;
import java.util.List;
import java.util.ListIterator;

import ir.mreonline.movieinfo.models.Movie;
import ir.mreonline.movieinfo.models.MovieSearch;
import ir.mreonline.movieinfo.models.Search;

public class SQLiteHelper extends SQLiteOpenHelper {

    public static final String Table_Name = "FavMovies";
    public static final String KEY_DetailTitle ="DetailTitle";
    public static final String KEY_DetailYear ="DetailYear";
    public static final String KEY_DetailRated ="DetailRated";
    public static final String KEY_DetailReleased ="DetailReleased";
    public static final String KEY_DetailRuntime ="DetailRuntime";
    public static final String KEY_DetailGenre ="DetailGenre";
    public static final String KEY_DetailDirector ="DetailDirector";
    public static final String KEY_DetailWriter ="DetailWriter";
    public static final String KEY_DetailActors ="DetailActors";
    public static final String KEY_DetailPlot ="DetailPlot";
    public static final String KEY_DetailLanguage  ="DetailLanguage";
    public static final String KEY_DetailCountry  ="DetailCountry";
    public static final String KEY_DetailAwards  ="DetailAwards";
    public static final String KEY_DetailMetascore  ="DetailMetascore";
    public static final String KEY_DetailImdbRating  ="DetailImdbRating";
    public static final String KEY_DetailImdbVotes  ="DetailImdbVotes";
    public static final String KEY_DetailImdbID  ="DetailImdbID";
    public static final String KEY_DetailType  ="DetailType";
    public static final String KEY_DetailDVD  ="DetailDVD";
    public static final String KEY_DetailBoxOffice  ="DetailBoxOffice";
    public static final String KEY_DetailProduction  ="DetailProduction";
    public static final String KEY_DetailWebsite  ="DetailWebsite";
    public static final String KEY_DetailPoster  ="DetailPoster";


    public SQLiteHelper(@Nullable Context context, @Nullable String name, @Nullable SQLiteDatabase.CursorFactory factory, int version) {
        super(context, name, factory, version);

    }

    @Override
    public void onCreate(SQLiteDatabase db) {
        String Create_Table_Query = "CREATE TABLE FavMovies (\n" +
                                        "DetailTitle text ,\n" +
                                        "DetailYear text  ,\n" +
                                        "DetailRated text  ,\n" +
                                        "DetailReleased text  ,\n" +
                                        "DetailRuntime text  ,\n" +
                                        "DetailGenre text  ,\n" +
                                        "DetailDirector text  ,\n" +
                                        "DetailWriter text  ,\n" +
                                        "DetailActors text  ,\n" +
                                        "DetailPlot text  ,\n" +
                                        "DetailLanguage  text  ,\n" +
                                        "DetailCountry  text  ,\n" +
                                        "DetailAwards  text  ,\n" +
                                        "DetailMetascore  text  ,\n" +
                                        "DetailImdbRating  text  ,\n" +
                                        "DetailImdbVotes  text  ,\n" +
                                        "DetailImdbID  text  PRIMARY KEY ,\n" +
                                        "DetailType  text  ,\n" +
                                        "DetailDVD  text  ,\n" +
                                        "DetailBoxOffice  text  ,\n" +
                                        "DetailProduction  text  ,\n" +
                                        "DetailWebsite  text  ,\n" +
                                        "DetailPoster  text  \n" +
                                        "\n" +
                                        ")";
        db.execSQL(Create_Table_Query);
    }

    @Override
    public void onUpgrade(SQLiteDatabase db, int oldVersion, int newVersion) {

    }

    public void InsertMovie(Movie m) {
        //String Insert_Movie_Query = "";
        //this.getWritableDatabase().execSQL(Insert_Movie_Query);
        SQLiteDatabase db = this.getWritableDatabase();
        ContentValues values = new ContentValues();
        values.put(KEY_DetailTitle,m.getTitle());
        values.put(KEY_DetailYear,m.getYear());
        values.put(KEY_DetailRated,m.getRated());
        values.put(KEY_DetailReleased,m.getReleased());
        values.put(KEY_DetailRuntime,m.getRuntime());
        values.put(KEY_DetailGenre,m.getGenre());
        values.put(KEY_DetailDirector,m.getDirector());
        values.put(KEY_DetailWriter,m.getWriter());
        values.put(KEY_DetailActors,m.getActors());
        values.put(KEY_DetailPlot,m.getPlot());
        values.put(KEY_DetailLanguage ,m.getLanguage());
        values.put(KEY_DetailCountry ,m.getCountry());
        values.put(KEY_DetailAwards ,m.getAwards());
        values.put(KEY_DetailMetascore ,m.getMetascore());
        values.put(KEY_DetailImdbRating ,m.getImdbRating());
        values.put(KEY_DetailImdbVotes ,m.getImdbVotes());
        values.put(KEY_DetailImdbID ,m.getImdbID());
        values.put(KEY_DetailType ,m.getType());
        values.put(KEY_DetailDVD ,m.getDvd());
        values.put(KEY_DetailBoxOffice ,m.getBoxOffice());
        values.put(KEY_DetailProduction ,m.getProduction());
        values.put(KEY_DetailWebsite ,m.getWebsite());
        values.put(KEY_DetailPoster ,m.getPoster());
        db.insert(Table_Name, null, values);
        db.close();
    }

    public int getMovieCount() {
        String countQuery = "SELECT  * FROM " + Table_Name;
        SQLiteDatabase db = this.getReadableDatabase();
        Cursor cursor = db.rawQuery(countQuery, null);
        int iCount = cursor.getCount();
        cursor.close();
        // return count
        return iCount;
    }

    public boolean deleteMovie(String imdbId) {
        SQLiteDatabase db = this.getWritableDatabase();
        Integer result =  db.delete(Table_Name, KEY_DetailImdbID + " = \"" + imdbId+"\"", null) ;
        return result> 0;
    }

    public MovieSearch GetMoviesByTitle(String title) {

        ArrayList<Search> movies =new ArrayList<>();


        String Get_Movies_Query = "SELECT  * FROM "+ Table_Name +" WHERE DetailTitle LIKE \"%" + title +"%\"" ;
        Cursor cursor = this.getReadableDatabase().rawQuery(Get_Movies_Query,null);
        while (cursor.moveToNext())
        {
            String StrDetailTitle = cursor.getString( cursor.getColumnIndexOrThrow("DetailTitle"));
            String StrDetailYear  = cursor.getString( cursor.getColumnIndexOrThrow("DetailYear"));
            String StrDetailImdbID = cursor.getString( cursor.getColumnIndexOrThrow("DetailImdbID"));
            String StrDetailType   = cursor.getString( cursor.getColumnIndexOrThrow("DetailType"));
            String StrDetailPoster = cursor.getString( cursor.getColumnIndexOrThrow("DetailPoster"));
            Search m =new Search();
            m.setTitle(StrDetailTitle);
            m.setYear(StrDetailYear );
            m.setImdbID(StrDetailImdbID);
            m.setType(StrDetailType  );
            m.setPoster(StrDetailPoster);
            movies.add(m);
        }
        MovieSearch movieSearch = new MovieSearch();
        movieSearch.setSearch(movies);
        return movieSearch;
    }

    public Movie GetMoviesByimdbId(String imdbId) {
        Movie m =new Movie();
        String Get_Movies_Query = "SELECT  * FROM "+ Table_Name  +" WHERE DetailImdbID = \"" + imdbId +"\"" ;
        Cursor cursor = this.getReadableDatabase().rawQuery(Get_Movies_Query,null);
        while (cursor.moveToNext())
        {
            String StrDetailTitle = cursor.getString( cursor.getColumnIndexOrThrow("DetailTitle"));
            String StrDetailYear  = cursor.getString( cursor.getColumnIndexOrThrow("DetailYear"));
            String StrDetailRated  = cursor.getString( cursor.getColumnIndexOrThrow("DetailRated"));
            String StrDetailReleased  = cursor.getString( cursor.getColumnIndexOrThrow("DetailReleased"));
            String StrDetailRuntime  = cursor.getString( cursor.getColumnIndexOrThrow("DetailRuntime"));
            String StrDetailGenre  = cursor.getString( cursor.getColumnIndexOrThrow("DetailGenre"));
            String StrDetailDirector  = cursor.getString( cursor.getColumnIndexOrThrow("DetailDirector"));
            String StrDetailWriter  = cursor.getString( cursor.getColumnIndexOrThrow("DetailWriter"));
            String StrDetailActors  = cursor.getString( cursor.getColumnIndexOrThrow("DetailActors"));
            String StrDetailPlot  = cursor.getString( cursor.getColumnIndexOrThrow("DetailPlot"));
            String StrDetailLanguage   = cursor.getString( cursor.getColumnIndexOrThrow("DetailLanguage"));
            String StrDetailCountry   = cursor.getString( cursor.getColumnIndexOrThrow("DetailCountry"));
            String StrDetailAwards   = cursor.getString( cursor.getColumnIndexOrThrow("DetailAwards"));
            String StrDetailMetascore   = cursor.getString( cursor.getColumnIndexOrThrow("DetailMetascore"));
            String StrDetailImdbRating   = cursor.getString( cursor.getColumnIndexOrThrow("DetailImdbRating"));
            String StrDetailImdbVotes   = cursor.getString( cursor.getColumnIndexOrThrow("DetailImdbVotes"));
            String StrDetailImdbID = cursor.getString( cursor.getColumnIndexOrThrow("DetailImdbID"));
            String StrDetailType   = cursor.getString( cursor.getColumnIndexOrThrow("DetailType"));
            String StrDetailDVD   = cursor.getString( cursor.getColumnIndexOrThrow("DetailDVD"));
            String StrDetailBoxOffice   = cursor.getString( cursor.getColumnIndexOrThrow("DetailBoxOffice"));
            String StrDetailProduction   = cursor.getString( cursor.getColumnIndexOrThrow("DetailProduction"));
            String StrDetailWebsite   = cursor.getString( cursor.getColumnIndexOrThrow("DetailWebsite"));
            String StrDetailPoster = cursor.getString( cursor.getColumnIndexOrThrow("DetailPoster"));
            m.setTitle(StrDetailTitle);
            m.setYear(StrDetailYear );
            m.setRated(StrDetailRated );
            m.setReleased(StrDetailReleased );
            m.setRuntime(StrDetailRuntime );
            m.setGenre(StrDetailGenre );
            m.setDirector(StrDetailDirector );
            m.setWriter(StrDetailWriter );
            m.setActors(StrDetailActors );
            m.setPlot(StrDetailPlot );
            m.setLanguage(StrDetailLanguage  );
            m.setCountry(StrDetailCountry  );
            m.setAwards(StrDetailAwards  );
            m.setMetascore(StrDetailMetascore  );
            m.setImdbRating(StrDetailImdbRating  );
            m.setImdbVotes(StrDetailImdbVotes  );
            m.setImdbID(StrDetailImdbID);
            m.setType(StrDetailType  );
            m.setDvd(StrDetailDVD  );
            m.setBoxOffice(StrDetailBoxOffice  );
            m.setProduction(StrDetailProduction  );
            m.setWebsite(StrDetailWebsite  );
            m.setPoster(StrDetailPoster);
        }
        return m;
    }
}
