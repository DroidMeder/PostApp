package kg.geekteck.postapp.ui.form;

import android.annotation.SuppressLint;
import android.os.Bundle;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.fragment.app.Fragment;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import kg.geekteck.postapp.App;
import kg.geekteck.postapp.R;
import kg.geekteck.postapp.data.models.Post;
import kg.geekteck.postapp.databinding.FragmentFormBinding;
import okhttp3.ResponseBody;
import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

public class FormFragment extends Fragment {
    private static final int GROUP_ID = 40;
    private int USER_ID = 4;

    private FragmentFormBinding binding;


    public FormFragment() {
    }

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        binding = FragmentFormBinding.inflate(inflater, container, false);
        return binding.getRoot();
    }

    @SuppressLint("SetTextI18n")
    @Override
    public void onViewCreated(@NonNull View view, @Nullable Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);

        Bundle bundle = getArguments();
        System.out.println("----------------------------"+bundle.isEmpty());

        binding.btnSend.setText(bundle.isEmpty() ? "Send" : "Update");

        binding.btnSend.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                //Bundle bundle = getArguments();
                if (bundle.isEmpty()){
                    post();
                }else {
                    int id = bundle.getInt("id");
                    System.out.println("----------=======------"+id);
                    put(id);
                }
            }
        });
    }

    private void post() {
        System.out.println("+++++++++++++++++++++++post");
        String title = binding.etTitle.getText().toString();
        String content = binding.etContent.getText().toString();

        Post post = new Post(
                title,
                content,
                USER_ID,
                GROUP_ID
        );
        App.api.createPost(post).enqueue(new Callback<Post>() {
            @Override
            public void onResponse(@NonNull Call<Post> call, Response<Post> response) {
                if (response.isSuccessful()){
                    requireActivity().onBackPressed();
                }
            }

            @Override
            public void onFailure(Call<Post> call, Throwable t) {

            }
        });
    }

    @SuppressLint("SetTextI18n")
    private void put(int bundle) {
        binding.btnSend.setText("Update");
        String title = binding.etTitle.getText().toString();
        String content = binding.etContent.getText().toString();

        Post post = new Post(
                title,
                content,
                USER_ID,
                GROUP_ID
        );
        App.api.updateDate(bundle, post).enqueue(new Callback<ResponseBody>() {
            @Override
            public void onResponse(Call<ResponseBody> call, Response<ResponseBody> response) {
                if (response.isSuccessful()){
                    requireActivity().onBackPressed();
                }
            }

            @Override
            public void onFailure(Call<ResponseBody> call, Throwable t) {

            }
        });
    }
}