package kg.geekteck.postapp.ui.posts;

import android.annotation.SuppressLint;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import kg.geekteck.postapp.data.models.Post;
import kg.geekteck.postapp.databinding.ListPostBinding;
import kg.geekteck.postapp.interfaces.Click;

public class PostAdapter extends RecyclerView.Adapter<PostAdapter.PostViewHolder> {
    private Map<Integer, String> mapOfStudents = new HashMap<>();
    private Click clicked;
    private List<Post> posts = new ArrayList<>();


    public PostAdapter(Click clicked) {
        this.clicked = clicked;
    }

    @SuppressLint("NotifyDataSetChanged")
    public void setPosts(List<Post> posts) {
        this.posts = posts;
        notifyDataSetChanged();
    }

    @NonNull
    @Override
    public PostViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        ListPostBinding binding = ListPostBinding.inflate(
                LayoutInflater.from(parent.getContext()),
                parent, false);
        setDataToMap();
        return new PostViewHolder(binding);
    }

    private void setDataToMap() {
        mapOfStudents.put(0, "хз");
        mapOfStudents.put(1, "Султан Джумалиев");
        mapOfStudents.put(2, "Бекжан Маданбеков");
        mapOfStudents.put(3, "Бакай Белеков");
        mapOfStudents.put(4, "Медербек Шермаматов");
        mapOfStudents.put(5, "Адахан Касымалиев");
        mapOfStudents.put(6, "Жумалиев Мурат");
        mapOfStudents.put(7, "Альберт Жумаев");
        mapOfStudents.put(8, "Милана Анарбекова");
        mapOfStudents.put(9, "Таиров Сагыналы");
        mapOfStudents.put(10, "Уланбек уулу Расул");
        mapOfStudents.put(11, "Жакипов Абдулла");
        mapOfStudents.put(12, "Мыктарбекова Бермет");
        mapOfStudents.put(13, "Айпери Ашыралиева");
        mapOfStudents.put(14, "Гулбарчын Алиева");
        mapOfStudents.put(15, "Эрнис уулу Альберт");
        mapOfStudents.put(16, "Джапаркулов Ахмад");
        mapOfStudents.put(17, "Акедос Мукашев");
        mapOfStudents.put(18, "Касымов Рафкат");
        mapOfStudents.put(19, "Максим Катунин");
        mapOfStudents.put(20, "Жанышев Султанкул");
    }

    @Override
    public void onBindViewHolder(@NonNull PostViewHolder holder, @SuppressLint("RecyclerView") int position) {
        holder.onBind(posts.get(position));
        holder.itemView.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                clicked.simple_click(posts.get(position).getId());
            }
        });
        holder.itemView.setOnLongClickListener(new View.OnLongClickListener() {
            @Override
            public boolean onLongClick(View view) {
                clicked.long_click(posts.get(position).getId());
                return true;
            }
        });
    }

    @Override
    public int getItemCount() {
        return posts.size();
    }

    protected class PostViewHolder extends  RecyclerView.ViewHolder{
        private final ListPostBinding binding;

        public PostViewHolder(@NonNull ListPostBinding itemView) {
            super(itemView.getRoot());
            this.binding = itemView;
        }

        public void onBind(Post post) {
            binding.tvUserId.setText(mapOfStudents.get(post.getUserId()));
            binding.tvTitle.setText(post.getTitle());
            binding.tvContent.setText(post.getContent());
        }
    }
}
