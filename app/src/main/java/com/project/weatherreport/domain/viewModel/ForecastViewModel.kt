package com.project.weatherreport.domain.viewModel

import android.util.Log
import androidx.compose.runtime.mutableStateOf
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.project.weatherreport.domain.forecastModel.ForecastModel
import com.project.weatherreport.repository.WeatherApiService
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.FlowPreview
import kotlinx.coroutines.flow.MutableSharedFlow
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.asStateFlow
import kotlinx.coroutines.flow.collectLatest
import kotlinx.coroutines.flow.debounce
import kotlinx.coroutines.flow.distinctUntilChanged
import kotlinx.coroutines.launch

const val ERROR_TYPING = "City name entered incorrectly"
const val ERROR_MESSAGE = "Error executing the request."

@OptIn(FlowPreview::class)
class ForecastViewModel(forecastApi: WeatherApiService) : ViewModel() {

    private val api = forecastApi
    private var _forecastData = MutableStateFlow<ForecastModel?>(null)
    val forecastData = _forecastData

    private val _isPerformingSearch = MutableStateFlow(false)
    val isPerformingSearch = _isPerformingSearch.asStateFlow()

    private val _days = mutableStateOf(5)

    private val _searchText = MutableStateFlow("")
    val searchText = _searchText.asStateFlow()

    private val searchDebouncer = MutableSharedFlow<String>()

    private val _message = MutableStateFlow<String?>(null)
    val message = _message

    init {
        viewModelScope.launch(Dispatchers.IO) {
            searchDebouncer
                .debounce(500L)
                .distinctUntilChanged()
                .collectLatest { searchQuery ->
                    if (searchQuery.length > 2) {
                        performSearch(searchQuery, _days.value)
                    } else {
                        _forecastData.value = null
                    }
                }
        }
    }


    fun onSearchTextChange(text: String) {
        _searchText.value = text
        viewModelScope.launch(Dispatchers.IO) {
            if (!isPerformingSearch.value && text.isNotEmpty()) { // Добавлена проверка на пустой текст
                searchDebouncer.emit(text)
            }
        }
    }


    private fun performSearch(q: String, days: Int) {
        viewModelScope.launch(Dispatchers.IO) {
            try {
                // Очищаем сообщение об ошибке, если запрос успешен
                _isPerformingSearch.value = true
                val response = api.getForecast(q, days)
                if (response.isSuccessful) {
                    _forecastData.value = response.body()
                    _message.value = null // Очищаем сообщение об ошибке, если запрос успешен
                } else {
                    if (response.code() == 400) {
                        _forecastData.value = null
                        _message.value = ERROR_TYPING
                    }
                }
            } catch (e: Exception) {
                Log.e("ForecastViewModel", "Failed to perform search: ${e.message}")
                _message.value = ERROR_MESSAGE
            } finally {
                _isPerformingSearch.value = false
            }
        }
    }


}