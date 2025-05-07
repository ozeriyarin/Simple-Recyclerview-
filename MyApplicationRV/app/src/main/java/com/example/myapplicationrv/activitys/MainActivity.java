package com.example.myapplicationrv.activitys;

import android.content.Intent;
import android.os.Bundle;
import android.widget.SearchView;

import androidx.activity.EdgeToEdge;
import androidx.appcompat.app.AppCompatActivity;
import androidx.core.graphics.Insets;
import androidx.core.view.ViewCompat;
import androidx.core.view.WindowInsetsCompat;
import androidx.recyclerview.widget.DefaultItemAnimator;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import com.example.myapplicationrv.R;
import com.example.myapplicationrv.adapters.CustomeAdapter;

import com.example.myapplicationrv.models.CharacterData;
import com.example.myapplicationrv.network.TvMazeService;
import com.example.myapplicationrv.network.ShowResponse;
import com.google.gson.Gson;
import com.google.gson.GsonBuilder;

import java.util.ArrayList;
import java.util.List;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;
import retrofit2.Retrofit;
import retrofit2.converter.gson.GsonConverterFactory;


public class MainActivity extends AppCompatActivity {

    private RecyclerView recyclerView;
    private SearchView searchView;
    private LinearLayoutManager layoutManager;
    private CustomeAdapter customeAdapter;
    private ArrayList<CharacterData> characterList;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        EdgeToEdge.enable(this);
        setContentView(R.layout.activity_main);

        ViewCompat.setOnApplyWindowInsetsListener(findViewById(R.id.main), (v, insets) -> {
            Insets systemBars = insets.getInsets(WindowInsetsCompat.Type.systemBars());
            v.setPadding(systemBars.left, systemBars.top, systemBars.right, systemBars.bottom);
            return insets;
        });

        // RecyclerView setup
        recyclerView = findViewById(R.id.rvcon);
        searchView = findViewById(R.id.searchView);
        layoutManager = new LinearLayoutManager(this);
        recyclerView.setLayoutManager(layoutManager);
        recyclerView.setItemAnimator(new DefaultItemAnimator());

        // Prepare data list and adapter
        characterList = new ArrayList<>();
        customeAdapter = new CustomeAdapter();
        recyclerView.setAdapter(customeAdapter);


        customeAdapter.setOnItemClickListener(item -> {
            Intent intent = new Intent(MainActivity.this, DetailActivity.class);
            intent.putExtra("name",  item.getName());
            intent.putExtra("actor", item.getActor());
            intent.putExtra("imageUrl", item.getImageUrl());
            startActivity(intent);
        });

        searchView.setOnQueryTextListener(new SearchView.OnQueryTextListener() {
            @Override public boolean onQueryTextSubmit(String query) {
                customeAdapter.getFilter().filter(query);
                return true;
            }
            @Override public boolean onQueryTextChange(String newText) {
                customeAdapter.getFilter().filter(newText);
                return true;
            }
        });
        // Build Retrofit instance
        Gson gson = new GsonBuilder().create();
        Retrofit retrofit = new Retrofit.Builder()
                .baseUrl("https://api.tvmaze.com/")
                .addConverterFactory(GsonConverterFactory.create(gson))
                .build();

        TvMazeService service = retrofit.create(TvMazeService.class);

        // Fetch Avatar: The Last Airbender cast (show ID 555) with embedded cast info
        service.getShowWithCast(555, "cast").enqueue(new Callback<ShowResponse>() {
            @Override
            public void onResponse(Call<ShowResponse> call, Response<ShowResponse> response) {
                if (!response.isSuccessful() || response.body() == null) return;

                List<ShowResponse.CastItem> cast = response.body()
                        .getEmbedded()
                        .getCast();
                for (ShowResponse.CastItem item : cast) {
                    String charName  = item.getCharacter().getName();
                    String actorName = item.getPerson().getName();
                    String imageUrl  = null;
                    if (item.getCharacter().getImage() != null) {
                        imageUrl = item.getCharacter().getImage().getMedium();
                    } else if (item.getPerson().getImage() != null) {
                        imageUrl = item.getPerson().getImage().getMedium();
                    }

                    charName = "Character: " + charName;
                    String actor = "Actor: " + actorName;

                    characterList.add(new CharacterData(charName, actor,imageUrl));
                }
                customeAdapter.setData(characterList);
                customeAdapter.notifyDataSetChanged();
            }

            @Override
            public void onFailure(Call<ShowResponse> call, Throwable t) {
                t.printStackTrace();
            }
        });
    }
}