package com.shuyun.androidnotes.activity;

import android.annotation.SuppressLint;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v7.app.AppCompatActivity;

import com.shuyun.androidnotes.data.bean.FormResponse;
import com.shuyun.androidnotes.data.bean.User;
import com.shuyun.androidnotes.data.remote.UserService;
import com.shuyun.androidnotes.utils.Log;

import java.util.concurrent.TimeUnit;

import io.reactivex.Observable;
import io.reactivex.schedulers.Schedulers;
import okhttp3.OkHttpClient;
import okhttp3.logging.HttpLoggingInterceptor;
import retrofit2.Retrofit;
import retrofit2.adapter.rxjava2.RxJava2CallAdapterFactory;
import retrofit2.converter.gson.GsonConverterFactory;

/**
 * A Activity for Retrofit
 * @see <a href="https://github.com/Shuyun6/morenotes">The relative backend service project</a>
 *
 * @Author shuyun
 * @Create at 2018/11/3 0003 9:50
 * @Update at 2018/11/3 0003 9:50
*/
public class RetrofitActivity extends AppCompatActivity {

    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

        OkHttpClient client = new OkHttpClient.Builder()
                .readTimeout(5, TimeUnit.SECONDS)
                .connectTimeout(5, TimeUnit.SECONDS)
                .addInterceptor(new HttpLoggingInterceptor())
                .build();

        Retrofit retrofit = new Retrofit.Builder()
                .baseUrl(UserService.url)
                .addConverterFactory(GsonConverterFactory.create())
                .addCallAdapterFactory(RxJava2CallAdapterFactory.create())
                .client(client)
                .build();

        UserService service = retrofit.create(UserService.class);
        service.getUser()
                .subscribeOn(Schedulers.io())
                .observeOn(Schedulers.io())
                .subscribe(userFormResponse -> {
                    if (null != userFormResponse) {
                        Log.Companion.e("the response is "+userFormResponse.toString());
                    }
                }, throwable -> Log.Companion.e(throwable.getMessage()));

    }

}
