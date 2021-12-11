package ir.mreonline.movieinfo;

import androidx.annotation.NonNull;
import androidx.appcompat.app.ActionBarDrawerToggle;
import androidx.appcompat.app.AppCompatActivity;
import androidx.appcompat.widget.Toolbar;
import androidx.core.view.GravityCompat;
import androidx.drawerlayout.widget.DrawerLayout;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import android.os.Bundle;
import android.util.DisplayMetrics;
import android.util.Log;
import android.view.MenuItem;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ImageButton;
import android.widget.QuickContactBadge;
import android.widget.TextView;
import android.widget.Toast;

import com.google.android.material.navigation.NavigationView;
import com.google.gson.Gson;
import com.loopj.android.http.AsyncHttpClient;
import com.loopj.android.http.JsonHttpResponseHandler;

import org.json.JSONObject;

import cz.msebera.android.httpclient.Header;
import ir.mreonline.movieinfo.models.MovieSearch;

//https://www.geeksforgeeks.org/navigation-drawer-in-android/
//git remote add origin https://github.com/ebrahimimr/MovieInfo.git
//git branch -M main
//git push -u origin main
//http://www.omdbapi.com/?s=car&apikey=ffd51f39
//http://www.omdbapi.com/?i=tt0075809&apikey=ffd51f39
public class MainActivity extends AppCompatActivity {
    //0 online 1 offline
    public int mode = 0;
    public DrawerLayout drawerLayout;
    public ActionBarDrawerToggle actionBarDrawerToggle;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        ImageButton btnSearch = findViewById(R.id.btnSearch);
        TextView txtMode=  findViewById(R.id.txtMode);
        txtMode.setText("Online Search");

        drawerLayout = findViewById(R.id.drawer_layout);
        actionBarDrawerToggle = new ActionBarDrawerToggle(this, drawerLayout, R.string.nav_open, R.string.nav_close);
        drawerLayout.addDrawerListener(actionBarDrawerToggle);
        actionBarDrawerToggle.syncState();
        getSupportActionBar().setDisplayHomeAsUpEnabled(true);

        NavigationView navigationview = (NavigationView) findViewById(R.id.navigation_view);
        navigationview.setNavigationItemSelectedListener(new NavigationView.OnNavigationItemSelectedListener() {
            @Override
            public boolean onNavigationItemSelected(@NonNull MenuItem item) {
                //Log.d("Tagm", item.getTitle().toString());
                int itemId = item.getItemId();
                switch (itemId) {
                    case R.id.nav_online:
                        txtMode.setText("Online Search");
                        mode = 0;
                        drawerLayout.closeDrawer(GravityCompat.START);
                        break;
                    case R.id.nav_offline:
                        txtMode.setText("Local DataBase Search");
                        mode = 1;
                        drawerLayout.closeDrawer(GravityCompat.START);
                        break;

                }
                return false;
            }
        });

        //DisplayMetrics displayMetrics = getResources().getDisplayMetrics();
        //float dpHeight = displayMetrics.heightPixels / displayMetrics.density;
        //float dpWidth = displayMetrics.widthPixels / displayMetrics.density;
        EditText txtSearch = findViewById(R.id.txtSearch);



        MovieSearch moviesearch = new MovieSearch();


        RecyclerView recycler = findViewById(R.id.recycler);
        LinearLayoutManager manager = new LinearLayoutManager(this, RecyclerView.VERTICAL, false);
        recycler.setLayoutManager(manager);

        btnSearch.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if (mode == 0) {
                    String url = "http://www.omdbapi.com/?s=" + txtSearch.getText() + "&apikey=ffd51f39";
                    AsyncHttpClient client = new AsyncHttpClient();
                    client.get(url, new JsonHttpResponseHandler() {
                        @Override
                        public void onSuccess(int statusCode, Header[] headers, JSONObject response) {
                            super.onSuccess(statusCode, headers, response);
                            Gson gson = new Gson();
                            MovieSearch moviesearch = gson.fromJson(response.toString(), MovieSearch.class);
                            RecyclerAdapter adapter = new RecyclerAdapter(moviesearch,mode);
                            recycler.setAdapter(adapter);
                        }

                        @Override
                        public void onFailure(int statusCode, Header[] headers, Throwable throwable, JSONObject errorResponse) {
                            super.onFailure(statusCode, headers, throwable, errorResponse);
                        }
                    });
                }
                if (mode == 1)
                {
                    SQLiteHelper helper = new SQLiteHelper(getApplicationContext(),"MovieInfo",null,1);
                    RecyclerAdapter adapter = new RecyclerAdapter(helper.GetMoviesByTitle(txtSearch.getText().toString()) ,mode);
                    recycler.setAdapter(adapter);
                }
            }
        });
    }

    @Override
    protected void onResume() {
        super.onResume();

    }

    @Override
    public boolean onOptionsItemSelected(@NonNull MenuItem item) {

        if (actionBarDrawerToggle.onOptionsItemSelected(item)) {
            return true;
        }
        return super.onOptionsItemSelected(item);
    }
}