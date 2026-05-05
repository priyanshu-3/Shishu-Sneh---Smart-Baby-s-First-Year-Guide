package com.shishusneh.data.repository

import com.shishusneh.data.api.*

class BabyRepository {
    private val api = ApiClient.api

    suspend fun register(name: String, dob: String, weight: Double): Result<BabyProfile> = runCatching {
        api.registerBaby(BabyProfile(name = name, dateOfBirth = dob, birthWeight = weight))
    }

    suspend fun getBaby(id: Long): Result<BabyProfile> = runCatching { api.getBaby(id) }

    suspend fun getAllBabies(): Result<List<BabyProfile>> = runCatching { api.getAllBabies() }

    suspend fun getVaccines(id: Long): Result<List<VaccineItem>> = runCatching { api.getVaccines(id) }

    suspend fun addHealthLog(id: Long, log: HealthLog): Result<HealthLog> = runCatching {
        api.addHealthLog(id, log)
    }

    suspend fun getHealthLogs(id: Long): Result<List<HealthLog>> = runCatching { api.getHealthLogs(id) }

    suspend fun getNutrition(age: Int, ingredients: List<String>): Result<NutritionResponse> = runCatching {
        api.getNutrition(NutritionRequest(age, ingredients))
    }
}
