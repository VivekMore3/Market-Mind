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
            @Field("productName") String productName,
            @Field("productDiscount") int productDiscount
    );

    @FormUrlEncoded
    @POST("db_discountPattern.php")
    Call<ResponseAdmin> ProductPattern(
            @Field("max_discount") int max_discount,
            @Field("Q1") int Q1,
            @Field("Q2") int Q2,
            @Field("Q3") int Q3,
            @Field("Q4") int Q4,
            @Field("Q5") int Q5,
            @Field("Q6") int Q6,
            @Field("Q7") int Q7,
            @Field("Q8") int Q8,
            @Field("Q9") int Q9,
            @Field("Q10") int Q10
    );


    @POST("db_gettingProducts.php")
    Call<List<ProductGetting>> GetProducts();
    @POST("db_gettingPattern.php")
    Call<List<GetPattern>> GettingPattern();

    @FormUrlEncoded
    @POST("db_UpdateProduct.php")
    Call<ResponseAdmin> UpdateProduct(
            @Field("productCode") String productCode,
            @Field("productName") String productName,
            @Field("productDiscount") int productDiscount
    );

    @FormUrlEncoded
    @POST("db_DeleteProduct.php")
    Call<ResponseAdmin> DeleteProduct(
            @Field("productCode") String productCode,
            @Field("productName") String productName,
            @Field("productDiscount") int productDiscount
    );

    @FormUrlEncoded
    @POST("db_UpdateDiscountPattern.php")
    Call<ResponseAdmin> UpdatePattern(
            @Field("max_discount") int max_discount,
            @Field("Q1") int Q1,
            @Field("Q2") int Q2,
            @Field("Q3") int Q3,
            @Field("Q4") int Q4,
            @Field("Q5") int Q5,
            @Field("Q6") int Q6,
            @Field("Q7") int Q7,
            @Field("Q8") int Q8,
            @Field("Q9") int Q9,
            @Field("Q10") int Q10
    );

    @FormUrlEncoded
    @POST("db_DeleteDiscountPattern.php")
    Call<ResponseAdmin> DeletePattern(
            @Field("max_discount") int max_discount,
            @Field("Q1") int Q1,
            @Field("Q2") int Q2,
            @Field("Q3") int Q3,
            @Field("Q4") int Q4,
            @Field("Q5") int Q5,
            @Field("Q6") int Q6,
            @Field("Q7") int Q7,
            @Field("Q8") int Q8,
            @Field("Q9") int Q9,
            @Field("Q10") int Q10
    );


   /* @POST("question.php")
    Call<List<Question>> getQuestions();
    */

}