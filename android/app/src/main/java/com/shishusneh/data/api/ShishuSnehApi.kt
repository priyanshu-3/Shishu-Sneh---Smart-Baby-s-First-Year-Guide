package com.shishusneh.data.api

import com.shishusneh.BuildConfig
import okhttp3.OkHttpClient
import okhttp3.logging.HttpLoggingInterceptor
import retrofit2.Retrofit
import retrofit2.converter.gson.GsonConverterFactory
import retrofit2.http.*
import java.util.concurrent.TimeUnit

// ═══ DTOs ═══
data class BabyProfile(
    val id: Long? = null,
    val name: String,
    val dateOfBirth: String,
    val birthWeight: Double,
    val createdAt: String? = null
)

data class HealthLog(
    val id: Long? = null,
    val date: String,
    val weight: Double,
    val height: Double,
    val milestoneAchieved: String? = null
)

data class VaccineItem(
    val vaccineName: String,
    val dueAt: String,
    val dueDate: String,
    val purpose: String,
    val completed: Boolean
)

data class NutritionRequest(
    val ageInMonths: Int,
    val ingredients: List<String>
)

data class NutritionResponse(
    val ageInMonths: Int,
    val feedingGuide: String
)

// ═══ Retrofit Service ═══
interface ShishuSnehApi {

    @POST("api/baby/register")
    suspend fun registerBaby(@Body profile: BabyProfile): BabyProfile

    @GET("api/baby/{id}")
    suspend fun getBaby(@Path("id") id: Long): BabyProfile

    @GET("api/baby/all")
    suspend fun getAllBabies(): List<BabyProfile>

    @GET("api/baby/{id}/vaccines")
    suspend fun getVaccines(@Path("id") id: Long): List<VaccineItem>

    @POST("api/baby/{id}/health-log")
    suspend fun addHealthLog(@Path("id") id: Long, @Body log: HealthLog): HealthLog

    @GET("api/baby/{id}/health-logs")
    suspend fun getHealthLogs(@Path("id") id: Long): List<HealthLog>

    @POST("api/baby/nutrition")
    suspend fun getNutrition(@Body request: NutritionRequest): NutritionResponse

    @GET("api/baby/health")
    suspend fun healthCheck(): Map<String, String>
}

// ═══ Singleton ═══
object ApiClient {
    private val client = OkHttpClient.Builder()
        .connectTimeout(30, TimeUnit.SECONDS)
        .readTimeout(60, TimeUnit.SECONDS)
        .addInterceptor(HttpLoggingInterceptor().apply {
            level = HttpLoggingInterceptor.Level.BODY
        })
        .build()

    val api: ShishuSnehApi = Retrofit.Builder()
        .baseUrl(BuildConfig.API_BASE_URL + "/")
        .client(client)
        .addConverterFactory(GsonConverterFactory.create())
        .build()
        .create(ShishuSnehApi::class.java)
}
