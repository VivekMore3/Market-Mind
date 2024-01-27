package com.vivek.konbanegacrorepati;

import java.util.List;

import retrofit2.Call;
import retrofit2.http.Field;
import retrofit2.http.FormUrlEncoded;
import retrofit2.http.POST;

public interface ApiService {
    @FormUrlEncoded
    @POST("db_admin.php")
    Call<ResponseAdmin> Admin(
            @Field("question") String question,
            @Field("optionA") String optionA,
            @Field("optionB") String optionB,
            @Field("optionC") String optionC,
            @Field("optionD") String optionD,
            @Field("optionE") String optionE,
            @Field("correctAns") char correctAns,
            @Field("complexity") int complexity,
            @Field("time") int time

    );


    @POST("question.php")
    Call<List<Question>> getQuestions();


}