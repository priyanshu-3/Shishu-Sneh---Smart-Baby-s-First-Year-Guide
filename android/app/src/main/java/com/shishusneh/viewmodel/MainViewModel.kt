package com.shishusneh.viewmodel

import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.setValue
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.shishusneh.data.api.*
import com.shishusneh.data.repository.BabyRepository
import kotlinx.coroutines.launch

class MainViewModel : ViewModel() {
    private val repo = BabyRepository()

    // State
    var babies by mutableStateOf<List<BabyProfile>>(emptyList()); private set
    var currentBaby by mutableStateOf<BabyProfile?>(null); private set
    var vaccines by mutableStateOf<List<VaccineItem>>(emptyList()); private set
    var healthLogs by mutableStateOf<List<HealthLog>>(emptyList()); private set
    var nutritionGuide by mutableStateOf(""); private set
    var isLoading by mutableStateOf(false); private set
    var error by mutableStateOf<String?>(null); private set

    init { loadBabies() }

    fun loadBabies() {
        viewModelScope.launch {
            repo.getAllBabies().onSuccess {
                babies = it
                if (currentBaby == null && it.isNotEmpty()) currentBaby = it.first()
            }
        }
    }

    fun selectBaby(baby: BabyProfile) { currentBaby = baby }

    fun registerBaby(name: String, dob: String, weight: Double, onSuccess: () -> Unit) {
        viewModelScope.launch {
            isLoading = true; error = null
            repo.register(name, dob, weight)
                .onSuccess { currentBaby = it; loadBabies(); onSuccess() }
                .onFailure { error = it.message }
            isLoading = false
        }
    }

    fun loadVaccines() {
        val id = currentBaby?.id ?: return
        viewModelScope.launch {
            isLoading = true
            repo.getVaccines(id)
                .onSuccess { vaccines = it }
                .onFailure { error = it.message }
            isLoading = false
        }
    }

    fun loadHealthLogs() {
        val id = currentBaby?.id ?: return
        viewModelScope.launch {
            isLoading = true
            repo.getHealthLogs(id)
                .onSuccess { healthLogs = it }
                .onFailure { error = it.message }
            isLoading = false
        }
    }

    fun addHealthLog(date: String, weight: Double, height: Double, milestone: String?, onSuccess: () -> Unit) {
        val id = currentBaby?.id ?: return
        viewModelScope.launch {
            isLoading = true; error = null
            repo.addHealthLog(id, HealthLog(date = date, weight = weight, height = height, milestoneAchieved = milestone))
                .onSuccess { loadHealthLogs(); onSuccess() }
                .onFailure { error = it.message }
            isLoading = false
        }
    }

    fun generateNutrition(age: Int, ingredients: List<String>) {
        viewModelScope.launch {
            isLoading = true; nutritionGuide = ""; error = null
            repo.getNutrition(age, ingredients)
                .onSuccess { nutritionGuide = it.feedingGuide }
                .onFailure { error = it.message }
            isLoading = false
        }
    }

    fun toggleVaccine(vaccine: VaccineItem, completed: Boolean) {
        val id = currentBaby?.id ?: return
        viewModelScope.launch {
            isLoading = true; error = null
            repo.markVaccine(id, vaccine.vaccineName, completed)
                .onSuccess { loadVaccines() }
                .onFailure { error = it.message }
            isLoading = false
        }
    }

    fun clearError() { error = null }
}
