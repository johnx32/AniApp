package org.jbtc.aniapp.ui.anime;

import android.os.Bundle;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.fragment.app.Fragment;
import androidx.lifecycle.ViewModelProvider;
import androidx.navigation.fragment.NavHostFragment;
import androidx.recyclerview.widget.LinearLayoutManager;
import org.jbtc.aniapp.MainActivity;
import org.jbtc.aniapp.R;
import org.jbtc.aniapp.adapter.AnimeAdapter;
import org.jbtc.aniapp.component.PaginationFragment;
import org.jbtc.aniapp.contract.AnimeService;
import org.jbtc.aniapp.databinding.FragmentAnimeBinding;
import org.jbtc.aniapp.model.Anime;
import org.jbtc.aniapp.model.RespuestaAnimes;
import org.jbtc.aniapp.provider.AniApiProvider;
import org.jbtc.aniapp.utils.RecyclerItemDecoration;
import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;
import retrofit2.Retrofit;
import retrofit2.converter.gson.GsonConverterFactory;


public class AnimeFragment extends Fragment implements
        AnimeAdapter.OnClick,
        PaginationFragment.OnClickCallback{

    private FragmentAnimeBinding binding;
    private AnimeAdapter adapter;

    private int page=0;
    private int total_page=0;



    private Callback<RespuestaAnimes> callAnimes = new Callback<RespuestaAnimes>() {
        @Override
        public void onResponse(Call<RespuestaAnimes> call, Response<RespuestaAnimes> response) {
            if(response.isSuccessful())
                adapter.setItems(response.body().getData().getDocuments());
            page = response.body().getData().getCurrent_page();
            total_page = response.body().getData().getLast_page();
            PaginationFragment paginationFragment = (PaginationFragment) getChildFragmentManager().findFragmentById(R.id.frag_anime_pagination);
            paginationFragment.setEnumeration(page,total_page);
        }

        @Override
        public void onFailure(Call<RespuestaAnimes> call, Throwable t) {
            Log.e("TAG", "onFailure: ", t);
        }
    };


    public View onCreateView(@NonNull LayoutInflater inflater,ViewGroup container, Bundle savedInstanceState) {

        AnimeViewModel animeViewModel =
                new ViewModelProvider(this).get(AnimeViewModel.class);

        binding = FragmentAnimeBinding.inflate(inflater, container, false);

        View root = binding.getRoot();

        return root;
    }

    @Override
    public void onViewCreated(@NonNull View view, @Nullable Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);

        initAdapter();

        ( (MainActivity)getActivity() ).setDisplayShowTitleEnabled(false,true);
        ( (MainActivity)getActivity() ).setTitle(getResources().getString(R.string.menu_anime));
        PaginationFragment paginationFragment = (PaginationFragment) getChildFragmentManager().findFragmentById(R.id.frag_anime_pagination);
        paginationFragment.setOnClickCallback(this);

        /*Retrofit retrofit = new Retrofit.Builder()
                .baseUrl("https://api.aniapi.com/")
                .addConverterFactory(GsonConverterFactory.create())
                .build();*/
        AnimeService animeService = AniApiProvider.getInstance().create(AnimeService.class);

        animeService.getAnimes(1).enqueue(callAnimes);



    }

    private void initAdapter() {
        binding.rvAnimeList.setHasFixedSize(true);
        //LinearLayoutManager gridLayoutManager = new LinearLayoutManager(getContext());
        //LinearLayoutManager gridLayoutManager = new LinearLayoutManager(getContext(),2);
        //binding.rvAnimeList.setLayoutManager(gridLayoutManager);


        adapter = new AnimeAdapter(this);
        binding.rvAnimeList.addItemDecoration(
                new RecyclerItemDecoration(getResources()
                        .getDimensionPixelSize(R.dimen.space_item_decoration)));
        binding.rvAnimeList.setAdapter(adapter);
    }

    @Override
    public void onDestroyView() {
        super.onDestroyView();
        binding = null;
    }

    @Override
    public void onClickCard(Anime anime) {
        Bundle b = new Bundle();
        b.putInt("id",anime.getId());
        NavHostFragment.findNavController(this)
                .navigate(R.id.action_nav_anime_to_nav_anime_detail,b);
    }

    @Override
    public void onClickPagination(int page) {
        AnimeService animeService = AniApiProvider.getInstance().create(AnimeService.class);
        animeService.getAnimes(page).enqueue(callAnimes);
    }
}