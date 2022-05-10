package com.kotlin.unsplash.viewmodel

import android.app.Application
import androidx.lifecycle.*
import com.kotlin.unsplash.domain.Topic
import com.kotlin.unsplash.service.CLIENT_ID
import com.kotlin.unsplash.service.UnsplashService
import kotlinx.coroutines.launch

class TopicViewModel(private val unsplashService: UnsplashService, application: Application): AndroidViewModel(application) {

    private val _listTopic = MutableLiveData<List<Topic>>()
    val listTopic: LiveData<List<Topic>>
    get() = _listTopic

    init {
        getListTopic()
    }

    private fun getListTopic(){
        viewModelScope.launch {
            try {
                val listResult = unsplashService.getListTopic(CLIENT_ID, 30)
                if(listResult.isNotEmpty()){
                    _listTopic.value = listResult
                }
            }catch (e: Exception){
                _listTopic.value = ArrayList()
            }
        }
    }

    private val _navigateToSelectedTopic = MutableLiveData<Topic?>()
    val navigateToSelectedTopic: LiveData<Topic?>
    get() = _navigateToSelectedTopic

    fun topicDetails(topic: Topic){
        _navigateToSelectedTopic.value = topic
    }

    fun navigateTopicDetailsComplete(){
        _navigateToSelectedTopic.value = null
    }
}

class TopicViewModelFactory(private val unsplashService: UnsplashService, private val application: Application): ViewModelProvider.Factory{
    override fun <T : ViewModel?> create(modelClass: Class<T>): T {
        if(modelClass.isAssignableFrom(TopicViewModel::class.java)){
            return TopicViewModel(unsplashService, application) as T
        }
        throw IllegalArgumentException("Unknown ViewModel class")
    }
}