package ir.mreonline.movieinfo;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Context;
import android.content.Intent;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;

import com.bumptech.glide.Glide;
import com.google.gson.Gson;
import com.loopj.android.http.AsyncHttpClient;
import com.loopj.android.http.JsonHttpResponseHandler;

import org.json.JSONObject;

import cz.msebera.android.httpclient.Header;
import ir.mreonline.movieinfo.models.Movie;
import ir.mreonline.movieinfo.models.MovieSearch;

public class MovieDetailActivity extends AppCompatActivity {
    Movie movie = new Movie();

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_movie_detail);


        Intent intent = getIntent();
        String imdbID = intent.getStringExtra("imdbID");
        Integer mode = intent.getIntExtra("mode", 0);
        Button btnAction = findViewById(R.id.btnAction);
        btnAction.setText("Save movie in local Database");

        if (mode == 1) {
            SQLiteHelper helpr = new SQLiteHelper(getApplicationContext(), "MovieInfo", null, 1);
            movie = helpr.GetMoviesByimdbId(imdbID);
            fillComponent(movie);
            btnAction.setText("Delete From Database");
        }
        if (mode == 0) {
            btnAction.setText("Save Movie Local");
            String url = "http://www.omdbapi.com/?i=" + imdbID + "&apikey=ffd51f39";
            AsyncHttpClient client = new AsyncHttpClient();
            client.get(url, new JsonHttpResponseHandler() {
                @Override
                public void onSuccess(int statusCode, Header[] headers, JSONObject response) {
                    super.onSuccess(statusCode, headers, response);

                    Gson gson = new Gson();
                    movie = gson.fromJson(response.toString(), Movie.class);
                    fillComponent(movie);
                }

                @Override
                public void onFailure(int statusCode, Header[] headers, Throwable throwable, JSONObject errorResponse) {
                    super.onFailure(statusCode, headers, throwable, errorResponse);
                }
            });
        }


        btnAction.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                SQLiteHelper helper = new SQLiteHelper(getApplicationContext(), "MovieInfo", null, 1);
                if (mode == 0) {
                    if (movie != null) {
                        helper.InsertMovie(movie);
                    }
                }
                if (mode == 1) {
                    if (movie != null) {
                        if (helper.deleteMovie(movie.getImdbID())) {
                            finish();
                        } else {
                            Toast.makeText(getApplicationContext(), "Can't Delete", Toast.LENGTH_SHORT).show();
                        }
                    }
                }
            }
        });


    }

    private void fillComponent(Movie movie)
    {
        TextView txtDetailTitle = findViewById(R.id.txtDetailTitle);
        TextView txtDetailYear = findViewById(R.id.txtDetailYear);
        TextView txtDetailRated = findViewById(R.id.txtDetailRated);
        TextView txtDetailReleased = findViewById(R.id.txtDetailReleased);
        TextView txtDetailRuntime = findViewById(R.id.txtDetailRuntime);
        TextView txtDetailGenre = findViewById(R.id.txtDetailGenre);
        TextView txtDetailDirector = findViewById(R.id.txtDetailDirector);
        TextView txtDetailWriter = findViewById(R.id.txtDetailWriter);
        TextView txtDetailActors = findViewById(R.id.txtDetailActors);
        TextView txtDetailPlot = findViewById(R.id.txtDetailPlot);
        TextView txtDetailLanguage = findViewById(R.id.txtDetailLanguage);
        TextView txtDetailCountry = findViewById(R.id.txtDetailCountry);
        TextView txtDetailAwards = findViewById(R.id.txtDetailAwards);
        TextView txtDetailMetascore = findViewById(R.id.txtDetailMetascore);
        TextView txtDetailImdbRating = findViewById(R.id.txtDetailImdbRating);
        TextView txtDetailImdbVotes = findViewById(R.id.txtDetailImdbVotes);
        TextView txtDetailImdbID = findViewById(R.id.txtDetailImdbID);
        TextView txtDetailType = findViewById(R.id.txtDetailType);
        TextView txtDetailDVD = findViewById(R.id.txtDetailDVD);
        TextView txtDetailBoxOffice = findViewById(R.id.txtDetailBoxOffice);
        TextView txtDetailProduction = findViewById(R.id.txtDetailProduction);
        TextView txtDetailWebsite = findViewById(R.id.txtDetailWebsite);
        ImageView imgDetailPoster = findViewById(R.id.imgDetailPoster);

        txtDetailTitle.setText("Title : " + movie.getTitle());
        txtDetailYear.setText("Year : " +movie.getYear());
        txtDetailRated.setText("Rated : " +movie.getRated());
        txtDetailReleased.setText("Released : " +movie.getReleased());
        txtDetailRuntime.setText("Runtime : " +movie.getRuntime());
        txtDetailGenre.setText("Genre : " +movie.getGenre());
        txtDetailDirector.setText("Director : " +movie.getDirector());
        txtDetailWriter.setText("Writer : " +movie.getWriter());
        txtDetailActors.setText("Actors : " +movie.getActors());
        txtDetailPlot.setText("Plot : " +movie.getPlot());
        txtDetailLanguage.setText("Language : " +movie.getLanguage());
        txtDetailCountry.setText("Country : " +movie.getCountry());
        txtDetailAwards.setText("Awards : " +movie.getAwards());
        txtDetailMetascore.setText("Metascore : " +movie.getMetascore());
        txtDetailImdbRating.setText("ImdbRating : " +movie.getImdbRating());
        txtDetailImdbVotes.setText("ImdbVotes : " +movie.getImdbVotes());
        txtDetailImdbID.setText("ImdbID : " +movie.getImdbID());
        txtDetailType.setText("Type : " +movie.getType());
        txtDetailDVD.setText("Dvd : " +movie.getDvd());
        txtDetailBoxOffice.setText("BoxOffice : " +movie.getBoxOffice());
        txtDetailProduction.setText("Production : " +movie.getProduction());
        txtDetailWebsite.setText("Website : " +movie.getWebsite());
        Glide.with(MovieDetailActivity.this).load(movie.getPoster()).into(imgDetailPoster);
    }
}