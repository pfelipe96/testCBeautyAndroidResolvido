package xyz.vixandrade.cbeautyandroidtest;

import android.app.ProgressDialog;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.support.v7.widget.CardView;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.util.Log;
import android.view.Gravity;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.ProgressBar;
import android.widget.TextView;
import android.widget.Toast;

import com.bumptech.glide.Glide;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.io.IOException;
import java.util.ArrayList;

import okhttp3.Call;
import okhttp3.Callback;
import okhttp3.OkHttpClient;
import okhttp3.Request;
import okhttp3.Response;

import static android.R.attr.delay;

public class MainActivity extends AppCompatActivity {
    private RecyclerView mRecyclerView;
    private RecyclerView.Adapter mAdapter;
    private RecyclerView.LayoutManager mLayoutManager;
    private CardView userCard;
    private ProgressBar progressBar;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        progressBar = (ProgressBar)findViewById(R.id.progressBar);

        userCard = (CardView) findViewById(R.id.card_user_info);

        mRecyclerView = (RecyclerView) findViewById(R.id.my_recycler_view);

        mRecyclerView.setHasFixedSize(true);

        mLayoutManager = new LinearLayoutManager(this);
        mRecyclerView.setLayoutManager(mLayoutManager);

        Button searchButton = (Button) findViewById(R.id.search_button);
        final EditText searchProfile = (EditText) findViewById(R.id.search_profile);


        searchButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                progressBar.setVisibility(View.VISIBLE);
                userCard.setVisibility(View.GONE);
                mRecyclerView.setVisibility(View.GONE);
                fillProfileInfo(searchProfile.getText().toString());
            }
        });
    }

    void fillProfileInfo(final String profile) {

        String requestUrl = "https://api.github.com/users/" + profile;
        OkHttpClient client = new OkHttpClient();
        Request request = new Request.Builder()
                .get()
                .url(requestUrl)
                .build();

        client.newCall(request).enqueue(new Callback() {
            @Override
            public void onFailure(Call call, IOException e) {
                runOnUiThread(new Runnable() {
                    @Override
                    public void run() {
                       Log.i("getProfileInfo", "FAIL");
                    }
                });
            }

            @Override
            public void onResponse(Call call, final Response response) {

                try {
                    final String jsonData = response.body().string();
                    Log.v("getProfileInfo", jsonData);
                    fillRepoInfo(profile);

                    if(response.message().equalsIgnoreCase("Not Found")){
                        runOnUiThread(new Runnable() {
                            @Override
                            public void run() {
                                progressBar.setVisibility(View.GONE);
                                Toast toastMsg = Toast.makeText(getApplicationContext(), "Usuário não encontrado, por favor insira um usuário valido!", Toast.LENGTH_LONG);
                                toastMsg.show();
                            }
                        });
                    }else if (response.isSuccessful()) {
                        runOnUiThread(new Runnable() {
                            @Override
                            public void run() {
                                try {
                                    JSONObject rootObj = new JSONObject(jsonData);

                                        TextView userName = (TextView) findViewById(R.id.user_name);
                                        TextView userBio = (TextView) findViewById(R.id.user_bio);
                                        TextView userFollowers = (TextView) findViewById(R.id.user_folowers);
                                        TextView userRepos = (TextView) findViewById(R.id.user_repos);
                                        ImageView userAvatar = (ImageView) findViewById((R.id.user_avatar));

                                        userName.setText(rootObj.getString("login"));
                                        userBio.setText(rootObj.getString("bio"));
                                        userFollowers.setText(rootObj.getInt("followers") + " Seguidores");
                                        userRepos.setText(rootObj.getInt("public_repos") + " Repositórios");

                                        try {
                                            Glide.with(MainActivity.this).load(rootObj.getString("avatar_url"))
                                                    .asBitmap()
                                                    .fitCenter()
                                                    .into(userAvatar);
                                        } catch (Exception e) {
                                            e.printStackTrace();
                                        }

                                        progressBar.setVisibility(View.GONE);
                                        userCard.setVisibility(View.VISIBLE);

                                } catch (JSONException e) {
                                    e.printStackTrace();
                                    userCard.setVisibility(View.GONE);
                                }
                            }
                        });
                    }
                } catch (IOException e) {
                    e.printStackTrace();
                }
            }
        });
    }

    void fillRepoInfo(String profile) {

        String requestUrl = "https://api.github.com/users/" + profile + "/repos";
        OkHttpClient client = new OkHttpClient();
        Request request = new Request.Builder()
                .get()
                .url(requestUrl)
                .build();

        client.newCall(request).enqueue(new Callback() {
            @Override
            public void onFailure(Call call, IOException e) {
                runOnUiThread(new Runnable() {
                    @Override
                    public void run() {
                        Log.v("getProfileInfo", "FAIL");
                    }
                });
            }

            @Override
            public void onResponse(Call call, final Response response) {
                try {
                    final String jsonData = response.body().string();
                    Log.i("getRepoInfo", jsonData);
                    if (response.isSuccessful()) {
                        runOnUiThread(new Runnable() {
                            @Override
                            public void run() {
                                try {
                                    JSONArray rootObj = new JSONArray(jsonData);

                                    ArrayList<ConstructorRepoInfo> myDataset = new ArrayList();

                                    for (int i = 0; i < 3; i++) {
                                        ConstructorRepoInfo cRepoInfo = new ConstructorRepoInfo(rootObj.getJSONObject(i).getString("name"), rootObj.getJSONObject(i).getString("html_url"),
                                                rootObj.getJSONObject(i).getInt("watchers"), rootObj.getJSONObject(i).getInt("open_issues"));

                                       myDataset.add(cRepoInfo);
                                    }

                                    mAdapter = new MyAdapter(myDataset);
                                    mRecyclerView.setAdapter(mAdapter);

                                    mRecyclerView.setVisibility(View.VISIBLE);

                                } catch (JSONException e) {
                                    e.printStackTrace();
                                    mRecyclerView.setVisibility(View.GONE);
                                }
                            }
                        });
                    }
                } catch (IOException e) {
                    e.printStackTrace();
                }


            }
        });

    }
}