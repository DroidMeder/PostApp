package kg.geekteck.postapp.ui.posts;

import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.fragment.app.Fragment;
import androidx.navigation.NavController;
import androidx.navigation.Navigation;
import androidx.navigation.fragment.NavHostFragment;

import java.util.List;

import kg.geekteck.postapp.App;
import kg.geekteck.postapp.R;
import kg.geekteck.postapp.data.models.Post;
import kg.geekteck.postapp.databinding.FragmentPostsBinding;
import kg.geekteck.postapp.interfaces.Click;
import okhttp3.ResponseBody;
import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

public class PostsFragment extends Fragment implements Click{
    private FragmentPostsBinding binding;
    private PostAdapter adapter;
    private NavHostFragment navHostFragment;
    private NavController controller;

    public PostsFragment() {
    }

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        adapter= new PostAdapter(this);
        navHostFragment = (NavHostFragment) requireActivity()
                .getSupportFragmentManager()
                .findFragmentById(R.id.nav_host_fragment);
        controller= navHostFragment.getNavController();
    }

    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        binding = FragmentPostsBinding.inflate(inflater, container, false);
        return binding.getRoot();
    }

    @Override
    public void onViewCreated(@NonNull View view, @Nullable Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);
        binding.rec.setAdapter(adapter);
        binding.fab.setOnClickListener(new View.OnClickListener() {
            Bundle bundle = new Bundle();
            @Override
            public void onClick(View view) {
                controller.navigate(R.id.action_postsFragment_to_formFragment, bundle);
            }
        });

        App.api.getPosts().enqueue(new Callback<List<Post>>() {
            @Override
            public void onResponse(@NonNull Call<List<Post>> call,
                                   @NonNull Response<List<Post>> response) {
                if (response.isSuccessful() && response.body() !=null){
                    adapter.setPosts(response.body());
                }
            }

            @Override
            public void onFailure(@NonNull Call<List<Post>> call, @NonNull Throwable t) {

            }
        });
    }

    @Override
    public void simple_click(int userId) {
        Bundle bundle = new Bundle();
        bundle.putInt("id", userId);
        controller.navigate(R.id.action_postsFragment_to_formFragment, bundle);
    }

    @Override
    public void long_click(int userId) {
        System.out.println("long "+ userId);
        App.api.deletePost(userId).enqueue(new Callback<ResponseBody>() {
            @Override
            public void onResponse(Call<ResponseBody> call, Response<ResponseBody> response) {
                if (response.isSuccessful() && response.body()!=null){
                    App.api.getPosts().enqueue(new Callback<List<Post>>() {
                        @Override
                        public void onResponse(@NonNull Call<List<Post>> call,
                                               @NonNull Response<List<Post>> response) {
                            if (response.isSuccessful() && response.body() !=null){
                                adapter.setPosts(response.body());
                            }
                        }

                        @Override
                        public void onFailure(@NonNull Call<List<Post>> call, @NonNull Throwable t) {

                        }
                    });
                }
            }

            @Override
            public void onFailure(Call<ResponseBody> call, Throwable t) {

            }
        });
    }
}