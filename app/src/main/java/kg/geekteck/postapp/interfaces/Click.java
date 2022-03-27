package kg.geekteck.postapp.interfaces;

import kg.geekteck.postapp.data.models.Post;

public interface Click {
    void simple_click(Post userId);

    void long_click(int userId);
}
