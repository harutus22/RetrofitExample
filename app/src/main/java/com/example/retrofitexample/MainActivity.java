package com.example.retrofitexample;

import androidx.appcompat.app.AppCompatActivity;
import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

import android.os.Bundle;
import android.util.Log;
import android.widget.TextView;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

public class MainActivity extends AppCompatActivity {
    private TextView textView;
    private static final String TAG = "MainActivity";
    private ApiManager apiManager;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        textView = findViewById(R.id.text);

//        getPosts();
//        getComments();
//        getPost();
//        getSpecificPost();
//        createPost();
        putPost();
//        patchPost();
//        deletePost();
    }

    private void getSpecificPost() {
        apiManager = ApiManager.getInstance();
        Call<List<Post>> call = apiManager.getJson().getSpecificPost(new Integer[]{3, 7, 11}, "id", "desc");
        call.enqueue(new Callback<List<Post>>() {
            @Override
            public void onResponse(Call<List<Post>> call, Response<List<Post>> response) {
                if(response.isSuccessful()){
                    List<Post> postList = response.body();
                    String content = "";
                    for(Post post: postList){
                        content += "ID " + post.getId() + "\n" +
                                "USER_ID " + post.getUserId() + "\n" +
                                "Title " + post.getTitle() + "\n" +
                                "Body " + post.getText() + "\n";
                        textView.setText(content);
                        Log.d(TAG, "onResponse: " + content);
                    }
                } else {
                    textView.setText("Code " + response.code());
                }
            }

            @Override
            public void onFailure(Call<List<Post>> call, Throwable t) {
                textView.append("Code " + t.getMessage());
            }
        });
    }

    private void getPost() {
        apiManager = ApiManager.getInstance();
        Call<List<Post>> call = apiManager.getJson().getPost(3);
        call.enqueue(new Callback<List<Post>>() {
            @Override
            public void onResponse(Call<List<Post>> call, Response<List<Post>> response) {
                if(response.isSuccessful()){
                    List<Post> postList = response.body();
                    String content = "";
                    for(Post post: postList){
                        content += "ID " + post.getId() + "\n" +
                                "USER_ID " + post.getUserId() + "\n" +
                                "Title " + post.getTitle() + "\n" +
                                "Body " + post.getText() + "\n";
                        textView.setText(content);
                        Log.d(TAG, "onResponse: " + content);
                    }
                } else {
                    textView.setText("Code " + response.code());
                }
            }

            @Override
            public void onFailure(Call<List<Post>> call, Throwable t) {
                textView.append("Code " + t.getMessage());
            }
        });
    }

    private void getComments() {
        apiManager = ApiManager.getInstance();
        Call<List<Comment>> call = apiManager.getJson().getComments(
                "https://jsonplaceholder.typicode.com/posts/3/comments");
//        Call<List<Comment>> call = apiManager.getJson().getComments(2);
        call.enqueue(new Callback<List<Comment>>() {
            @Override
            public void onResponse(Call<List<Comment>> call, Response<List<Comment>> response) {
                if(response.isSuccessful()){
                    List<Comment> commentList = response.body();
                    String content = "";
                    for(Comment comment: commentList){
                        content += "ID " + comment.getId() + "\n" +
                                "COMMENT_ID " + comment.getPostId() + "\n" +
                                "Name " + comment.getName() + "\n" +
                                "Email " + comment.getEmail() + "\n" +
                                "Body " + comment.getText() + "\n";
                        Log.d(TAG, "onResponse: " + content);
                        textView.setText(content);
                    }
                } else {
                    textView.append("Code " + response.code());
                }
            }

            @Override
            public void onFailure(Call<List<Comment>> call, Throwable t) {
                textView.setText(t.getMessage());
            }
        });
    }

    private void getPosts() {
        Map<String, String> parameters = new HashMap<>();
        parameters.put("userId", "1");
        parameters.put("_sort", "id");
        parameters.put("_order", "desc");

        apiManager = ApiManager.getInstance();
        Call<List<Post>> call = apiManager.getJson().getPosts(parameters);
        call.enqueue(new Callback<List<Post>>() {
            @Override
            public void onResponse(Call<List<Post>> call, Response<List<Post>> response) {
                if(response.isSuccessful()){
                    List<Post> postList = response.body();
                    String content = "";
                    for(Post post: postList){
                        content += "ID " + post.getId() + "\n" +
                                "USER_ID " + post.getUserId() + "\n" +
                                "Title " + post.getTitle() + "\n" +
                                "Body " + post.getText() + "\n";
                        textView.setText(content);
                        Log.d(TAG, "onResponse: " + content);
                    }
                } else {
                    textView.setText("Code " + response.code());
                }
            }

            @Override
            public void onFailure(Call<List<Post>> call, Throwable t) {
                textView.setText(t.getMessage());
            }
        });
    }

    private void createPost(){
        Post post = new Post(23, "New title", "new Text");
        Map<String, String> fields = new HashMap<>();
        fields.put("userId", "25");
        fields.put("title", "Good Title");

        apiManager = ApiManager.getInstance();
//        Call<Post> call = apiManager.getJson().createPost(23, "new Title", "new Text");
        Call<Post> call = apiManager.getJson().createPost(fields);
        call.enqueue(new Callback<Post>() {
            @Override
            public void onResponse(Call<Post> call, Response<Post> response) {

                if(response.isSuccessful()){
                    Post postResponse = response.body();
                    String content = "";
                    content += "Code " + response.code() + "\n" +
                            "ID " + postResponse.getId() + "\n" +
                            "USER_ID " + postResponse.getUserId() + "\n" +
                            "Title " + postResponse.getTitle() + "\n" +
                            "Body " + postResponse.getText() + "\n";
                    textView.setText(content);
                } else {
                    textView.append("response: " + response.code());
                }
            }

            @Override
            public void onFailure(Call<Post> call, Throwable t) {
                textView.setText(t.getMessage());
            }
        });
    }
    private void putPost(){
        apiManager = ApiManager.getInstance();
        Post post = new Post(12, null, "new Text");
        Call<Post> call = apiManager.getJson().putPost("789",5, post);
        call.enqueue(new Callback<Post>() {
            @Override
            public void onResponse(Call<Post> call, Response<Post> response) {
                if(!response.isSuccessful()){
                    textView.setText(response.code());
                    return;
                }
                Post postResponse = response.body();
                String content = "";
                content += "Code " + response.code() + "\n" +
                        "ID " + postResponse.getId() + "\n" +
                        "USER_ID " + postResponse.getUserId() + "\n" +
                        "Title " + postResponse.getTitle() + "\n" +
                        "Body " + postResponse.getText() + "\n";
                textView.setText(content);
            }

            @Override
            public void onFailure(Call<Post> call, Throwable t) {
                textView.setText(t.getMessage());
            }
        });
    }

    private void patchPost(){
        apiManager = ApiManager.getInstance();
        Post post = new Post(12, null, "new Text");
        Map<String, String> headers = new HashMap<>();
        headers.put("Map-Header1", "dfg");
        headers.put("Map-Header2", "bnm");
        Call<Post> call = apiManager.getJson().patchPost(headers, 5, post);
        call.enqueue(new Callback<Post>() {
            @Override
            public void onResponse(Call<Post> call, Response<Post> response) {
                if(!response.isSuccessful()){
                    textView.setText(response.code());
                    return;
                }
                Post postResponse = response.body();
                String content = "";
                content += "Code " + response.code() + "\n" +
                        "ID " + postResponse.getId() + "\n" +
                        "USER_ID " + postResponse.getUserId() + "\n" +
                        "Title " + postResponse.getTitle() + "\n" +
                        "Body " + postResponse.getText() + "\n";
                textView.setText(content);
            }

            @Override
            public void onFailure(Call<Post> call, Throwable t) {
                textView.setText(t.getMessage());
            }
        });
    }

    private void deletePost(){
        apiManager = ApiManager.getInstance();
        Call<Void> call = apiManager.getJson().deletePost(5);
        call.enqueue(new Callback<Void>() {
            @Override
            public void onResponse(Call<Void> call, Response<Void> response) {
                textView.append("Code: " + response.code());
            }

            @Override
            public void onFailure(Call<Void> call, Throwable t) {
                textView.setText(t.getMessage());
            }
        });
    }

}
