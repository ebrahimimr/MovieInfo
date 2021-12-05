package ir.mreonline.movieinfo;

import androidx.annotation.NonNull;
import androidx.appcompat.app.ActionBarDrawerToggle;
import androidx.appcompat.app.AppCompatActivity;
import androidx.appcompat.widget.Toolbar;
import androidx.drawerlayout.widget.DrawerLayout;

import android.os.Bundle;
import android.view.MenuItem;
import android.widget.EditText;

//git remote add origin https://github.com/ebrahimimr/MovieInfo.git
//git branch -M main
//git push -u origin main
//http://www.omdbapi.com/?s=car&apikey=ffd51f39
//http://www.omdbapi.com/?i=tt0075809&apikey=ffd51f39
public class MainActivity extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        EditText txtSearch =findViewById(R.id.txtSearch);


    }
}