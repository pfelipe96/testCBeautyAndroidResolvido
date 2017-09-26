//package xyz.vixandrade.cbeautyandroidtest;
//
//import android.app.ProgressDialog;
//import android.content.Context;
//import android.os.AsyncTask;
//import android.util.Log;
//
//import java.io.IOException;
//
//import okhttp3.Call;
//import okhttp3.Callback;
//import okhttp3.OkHttpClient;
//import okhttp3.Request;
//import okhttp3.Response;
//
///**
// * Created by paulofelipeoliveirasouza on 24/09/17.
// */
//
//public class ProfileFileTask extends AsyncTask<String, String, String> {
//    private String jsonData = "aaaaaaaaaa";
//    private Context context;
//    private ProfileFileInterface profileFI;
//    private ProgressDialog progress;
//
//    public ProfileFileTask(Context context, ProfileFileInterface profileFI){
//        this.context = context;
//        this.profileFI = profileFI;
//    }
//
//    @Override
//    protected void onPreExecute() {
//        progress = new ProgressDialog(context);
//        progress.setMessage("Carregando...");
//        progress.show();
//    }
//
//    @Override
//    protected String doInBackground(String... params) {
//
//        String requestUrl = "https://api.github.com/users/" + params[0];
//        OkHttpClient client = new OkHttpClient();
//        Request request = new Request.Builder()
//                .get()
//                .url(requestUrl)
//                .build();
//
//        Call call = client.newCall(request);
//
//        call.enqueue(new Callback() {
//            @Override
//            public void onFailure(Call call, IOException e) {
//                Log.i("getProfileInfo", "FAIL");
//            }
//
//            @Override
//            public void onResponse(Call call, Response response) {
//                try {
//                    jsonData = response.body().string();
//                    Log.v("getInfo ", jsonData.toString());
//                }catch (IOException e){
//                    Log.v("Erro Json ", e.getMessage());
//                }
//            }
//        });
//        Log.v("getInfo 2", jsonData.toString());
//        return(jsonData);
//    }
//
//    @Override
//    protected void onProgressUpdate(String... values) {
//    }
//
//    @Override
//    protected void onPostExecute(String params){
//        progress.setMessage("Finalizado!");
//        profileFI.afterHttp(params);
//        progress.dismiss();
//    }
//}
