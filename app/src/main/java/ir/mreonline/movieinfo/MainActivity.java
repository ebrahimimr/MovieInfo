package ir.mreonline.movieinfo;

import androidx.annotation.NonNull;
import androidx.appcompat.app.ActionBarDrawerToggle;
import androidx.appcompat.app.AppCompatActivity;
import androidx.appcompat.widget.Toolbar;
import androidx.drawerlayout.widget.DrawerLayout;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import android.os.Bundle;
import android.util.Log;
import android.view.MenuItem;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.QuickContactBadge;

import com.google.gson.Gson;
import com.loopj.android.http.AsyncHttpClient;
import com.loopj.android.http.JsonHttpResponseHandler;

import org.json.JSONObject;

import cz.msebera.android.httpclient.Header;
import ir.mreonline.movieinfo.models.MovieSearch;

//git remote add origin https://github.com/ebrahimimr/MovieInfo.git
//git branch -M main
//git push -u origin main
//http://www.omdbapi.com/?s=car&apikey=ffd51f39
//http://www.omdbapi.com/?i=tt0075809&apikey=ffd51f39
public class MainActivity extends AppCompatActivity {
    //0 online 1 offline
    public  int mode =0;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        EditText txtSearch =findViewById(R.id.txtSearch);
        txtSearch.setText("car");
        Button btnSearch=findViewById(R.id.btnSearch);
        MovieSearch moviesearch =new MovieSearch();


        RecyclerView recycler =findViewById(R.id.recycler);
        LinearLayoutManager manager =new LinearLayoutManager(this,RecyclerView.VERTICAL,false);
        recycler.setLayoutManager(manager);

        btnSearch.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                String url ="http://www.omdbapi.com/?s="+txtSearch.getText()+"&apikey=ffd51f39";
                AsyncHttpClient client =new AsyncHttpClient();
                client.get(url,new JsonHttpResponseHandler(){
                    @Override
                    public void onSuccess(int statusCode, Header[] headers, JSONObject response) {
                        super.onSuccess(statusCode, headers, response);
                        Gson gson =new Gson();
                        MovieSearch moviesearch = gson.fromJson(response.toString(),MovieSearch.class);
                        RecyclerAdapter adapter = new RecyclerAdapter(moviesearch);
                        recycler.setAdapter(adapter);
                    }

                    @Override
                    public void onFailure(int statusCode, Header[] headers, Throwable throwable, JSONObject errorResponse) {
                        super.onFailure(statusCode, headers, throwable, errorResponse);
                    }
                });
            }
        });
    }
}