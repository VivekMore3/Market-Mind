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

    @FormUrlEncoded
    @POST("db_complexity1.php")
    Call<ResponseAdmin> Complexity1(
            @Field("question") String question,
            @Field("optionA") String optionA,
            @Field("optionB") String optionB,
            @Field("optionC") String optionC,
            @Field("optionD") String optionD,
            @Field("optionE") String optionE,
            @Field("correctAns") char correctAns,
            @Field("time") int time

    );

    @FormUrlEncoded
    @POST("db_complexity2.php")
    Call<ResponseAdmin> Complexity2(
            @Field("question") String question,
            @Field("optionA") String optionA,
            @Field("optionB") String optionB,
            @Field("optionC") String optionC,
            @Field("optionD") String optionD,
            @Field("optionE") String optionE,
            @Field("correctAns") char correctAns,
            @Field("time") int time

    );

    @FormUrlEncoded
    @POST("db_complexity3.php")
    Call<ResponseAdmin> Complexity3(
            @Field("question") String question,
            @Field("optionA") String optionA,
            @Field("optionB") String optionB,
            @Field("optionC") String optionC,
            @Field("optionD") String optionD,
            @Field("optionE") String optionE,
            @Field("correctAns") char correctAns,
            @Field("time") int time

    );

    @FormUrlEncoded
    @POST("db_complexity4.php")
    Call<ResponseAdmin> Complexity4(
            @Field("question") String question,
            @Field("optionA") String optionA,
            @Field("optionB") String optionB,
            @Field("optionC") String optionC,
            @Field("optionD") String optionD,
            @Field("optionE") String optionE,
            @Field("correctAns") char correctAns,
            @Field("time") int time

    );

    @FormUrlEncoded
    @POST("db_complexity5.php")
    Call<ResponseAdmin> Complexity5(
            @Field("question") String question,
            @Field("optionA") String optionA,
            @Field("optionB") String optionB,
            @Field("optionC") String optionC,
            @Field("optionD") String optionD,
            @Field("optionE") String optionE,
            @Field("correctAns") char correctAns,
            @Field("time") int time

    );

    @FormUrlEncoded
    @POST("db_user.php")
    Call<ResponseAdmin> User(
            @Field("firstName") String firstName,
            @Field("lastName") String lastName,
            @Field("mobileNumber") String mobileNumber,
            @Field("email") String email,
            @Field("age") String age,
            @Field("password") String password,
            @Field("complexity1Qno") int complexity1Qno,
            @Field("complexity2Qno") int complexity2Qno,
            @Field("complexity3Qno") int complexity3Qno,
            @Field("complexity4Qno") int complexity4Qno,
            @Field("complexity5Qno") int complexity5Qno

    );

    @FormUrlEncoded
    @POST("db_login.php")
    Call<LoginDetail> Login(
            @Field("mobileNumber") String mobileNumber,
            @Field("password") String password
    );
    @FormUrlEncoded
    @POST("ComplexityWiseQusetion.php")
    Call<List<ComplexityWiseQuestion>> GetAllQuestions(
            @Field("questionId") int questionId,
            @Field("complexity1QuestionId") int complexity1,
            @Field("complexity2QuestionId") int complexity2,
            @Field("complexity3QuestionId") int complexity3,
            @Field("complexity4QuestionId") int complexity4,
            @Field("complexity5QuestionId") int complexity5
    );

    @FormUrlEncoded
    @POST("starting_question_number.php")
    Call<List<GettingQuestionNumbers>> GetAllQuestionNumber(
            @Field("userId") int userId

    );
    @FormUrlEncoded
    @POST("db_settingQuestion.php")
    Call<ResponseAdmin> SettingQuestion(
            @Field("userId") int userId,
            @Field("complexity1Qno") int complexity1Qno,
            @Field("complexity2Qno") int complexity2Qno,
            @Field("complexity3Qno") int complexity3Qno,
            @Field("complexity4Qno") int complexity4Qno,
            @Field("complexity5Qno") int complexity5Qno
    );

    @FormUrlEncoded
    @POST("db_product.php")
    Call<ResponseAdmin> ProductInfo(
            @Field("productCode") String productCode,
            @Field("productName") String productName
    );




   /* @POST("question.php")
    Call<List<Question>> getQuestions();
    */

}