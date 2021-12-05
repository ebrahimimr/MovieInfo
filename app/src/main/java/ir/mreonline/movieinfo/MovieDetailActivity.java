package ir.mreonline.movieinfo;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Context;
import android.content.Intent;
import android.os.Bundle;
import android.util.Log;
import android.widget.ImageView;
import android.widget.TextView;

import com.bumptech.glide.Glide;
import com.google.gson.Gson;
import com.loopj.android.http.AsyncHttpClient;
import com.loopj.android.http.JsonHttpResponseHandler;

import org.json.JSONObject;

import cz.msebera.android.httpclient.Header;
import ir.mreonline.movieinfo.models.Movie;
import ir.mreonline.movieinfo.models.MovieSearch;

public class MovieDetailActivity extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_movie_detail);

        Movie movie = new Movie();
        Intent intent = getIntent();
        String imdbID = intent.getStringExtra("imdbID");

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

        String url = "http://www.omdbapi.com/?i=" + imdbID + "&apikey=ffd51f39";
        AsyncHttpClient client = new AsyncHttpClient();
        client.get(url, new JsonHttpResponseHandler() {
            @Override
            public void onSuccess(int statusCode, Header[] headers, JSONObject response) {
                super.onSuccess(statusCode, headers, response);
                Gson gson = new Gson();
                Movie movie = gson.fromJson(response.toString(), Movie.class);
                txtDetailTitle.setText(movie.getTitle());
                txtDetailYear.setText(movie.getYear());
                txtDetailRated.setText(movie.getRated());
                txtDetailReleased.setText(movie.getReleased());
                txtDetailRuntime.setText(movie.getRuntime());
                txtDetailGenre.setText(movie.getGenre());
                txtDetailDirector.setText(movie.getDirector());
                txtDetailWriter.setText(movie.getWriter());
                txtDetailActors.setText(movie.getActors());
                txtDetailPlot.setText(movie.getPlot());
                txtDetailLanguage.setText(movie.getLanguage());
                txtDetailCountry.setText(movie.getCountry());
                txtDetailAwards.setText(movie.getAwards());
                txtDetailMetascore.setText(movie.getMetascore());
                txtDetailImdbRating.setText(movie.getImdbRating());
                txtDetailImdbVotes.setText(movie.getImdbVotes());
                txtDetailImdbID.setText(movie.getImdbID());
                txtDetailType.setText(movie.getType());
                txtDetailDVD.setText(movie.getDvd());
                txtDetailBoxOffice.setText(movie.getBoxOffice());
                txtDetailProduction.setText(movie.getProduction());
                txtDetailWebsite.setText(movie.getWebsite());
                Glide.with(MovieDetailActivity.this).load(movie.getPoster()).into(imgDetailPoster);
            }

            @Override
            public void onFailure(int statusCode, Header[] headers, Throwable throwable, JSONObject errorResponse) {
                super.onFailure(statusCode, headers, throwable, errorResponse);
            }
        });


    }
}