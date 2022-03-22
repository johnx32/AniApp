package org.jbtc.aniapp;

import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.view.Menu;

import com.google.android.material.snackbar.Snackbar;
import com.google.android.material.navigation.NavigationView;

import androidx.annotation.NonNull;
import androidx.navigation.NavController;
import androidx.navigation.Navigation;
import androidx.navigation.ui.AppBarConfiguration;
import androidx.navigation.ui.NavigationUI;
import androidx.drawerlayout.widget.DrawerLayout;
import androidx.appcompat.app.AppCompatActivity;

import org.jbtc.aniapp.contract.AnimeService;
import org.jbtc.aniapp.databinding.ActivityMainBinding;
import org.jbtc.aniapp.model.RespuestaAnimes;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;
import retrofit2.Retrofit;
import retrofit2.converter.gson.GsonConverterFactory;

public class MainActivity extends AppCompatActivity {

    private AppBarConfiguration mAppBarConfiguration;
    private ActivityMainBinding binding;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);



        Log.i("TAG", "onCreate: ");



        binding = ActivityMainBinding.inflate(getLayoutInflater());
        setContentView(binding.getRoot());

        setSupportActionBar(binding.appBarMain.toolbar);
        binding.appBarMain.fab.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Snackbar.make(view, "Boton de funcion correo", Snackbar.LENGTH_LONG)
                        .setAction("Action", null).show();
            }
        });
        DrawerLayout drawer = binding.drawerLayout;
        NavigationView navigationView = binding.navView;
        // Passing each menu ID as a set of Ids because each
        // menu should be considered as top level destinations.
        mAppBarConfiguration = new AppBarConfiguration.Builder(
                R.id.nav_anime,
                R.id.nav_episode,
                R.id.nav_song,
                R.id.nav_user)
                .setOpenableLayout(drawer)
                .build();
        NavController navController = Navigation.findNavController(this, R.id.nav_host_fragment_content_main);
        NavigationUI.setupActionBarWithNavController(this, navController, mAppBarConfiguration);
        NavigationUI.setupWithNavController(navigationView, navController);
    }
    private void initRetrofit() {
        Retrofit retrofit = new Retrofit.Builder()
                .baseUrl("https://api.aniapi.com/")
                .addConverterFactory(GsonConverterFactory.create())
                .build();

        AnimeService animeService = retrofit.create(AnimeService.class);

        animeService.getAnimes().enqueue(new Callback<RespuestaAnimes>() {
            @Override
            public void onResponse(Call<RespuestaAnimes> call, Response<RespuestaAnimes> response) {
                //Log.i("TAG", "onResponse: animes: "+response.body());

                System.out.println("animes: "+response.body().getData().getDocuments());
                System.out.println("respuesta: "+response);
            }

            @Override
            public void onFailure(Call<RespuestaAnimes> call, Throwable t) {

            }
        });

        /*animeService.getAnime(100).enqueue(new Callback<RespuestaAnime>() {
            @Override
            public void onResponse(Call<RespuestaAnime> call, Response<RespuestaAnime> response) {
                Log.i("TAG", "onResponse: anime: "+response.body());
            }

            @Override
            public void onFailure(Call<RespuestaAnime> call, Throwable t) {
                Log.e("TAG", "onFailure: ", t);
            }
        });*/

        /*animeService.autenticacion("Bearer eyJhbGciOiJIUzI1NiIsInR5cCI6IkpXVCJ9.eyJpZCI6IjE0MjIiLCJuYmYiOjE2NDc0NzY0NDUsImV4cCI6MTY1MDA2ODQ0NSwiaWF0IjoxNjQ3NDc2NDQ1fQ.V5Agn5pUvTcBtDHsf3a26GW5VaUl_l0jVR6GgYkQ4Xs")
                .enqueue(new Callback<JsonObject>() {
                    @Override
                    public void onResponse(Call<JsonObject> call, Response<JsonObject> response) {
                        Log.i("TAG", "onResponse: response: "+response+" body: "+response.body() );
                    }

                    @Override
                    public void onFailure(Call<JsonObject> call, Throwable t) {
                        Log.e("TAG", "onFailure: ", t);
                    }
                });*/

    }
    @Override
    protected void onRestoreInstanceState(@NonNull Bundle savedInstanceState) {
        super.onRestoreInstanceState(savedInstanceState);

    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        // Inflate the menu; this adds items to the action bar if it is present.
        getMenuInflater().inflate(R.menu.main, menu);
        return true;
    }

    @Override
    public boolean onSupportNavigateUp() {
        NavController navController = Navigation.findNavController(this, R.id.nav_host_fragment_content_main);
        return NavigationUI.navigateUp(navController, mAppBarConfiguration)
                || super.onSupportNavigateUp();
    }

    @Override
    protected void onStart() {
        super.onStart();
        Log.i("TAG", "onStart: ");
    }

    @Override
    protected void onResume() {
        super.onResume();
        Log.i("TAG", "onResume: ");
    }

    @Override
    protected void onPause() {
        super.onPause();
        Log.i("TAG", "onPause: ");
    }

    @Override
    protected void onStop() {
        super.onStop();
        Log.i("TAG", "onStop: ");
    }

    @Override
    protected void onDestroy() {
        super.onDestroy();
        Log.i("TAG", "onDestroy: ");
    }
}