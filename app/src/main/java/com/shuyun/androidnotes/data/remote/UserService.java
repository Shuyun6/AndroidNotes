package com.shuyun.androidnotes.data.remote;

import com.shuyun.androidnotes.data.bean.FormResponse;
import com.shuyun.androidnotes.data.bean.User;

import io.reactivex.Observable;
import retrofit2.http.GET;

public interface UserService {

    String url = "http://10.0.2.2:8088";

    @GET("/api/getUser")
    Observable<FormResponse<User>> getUser();

}
