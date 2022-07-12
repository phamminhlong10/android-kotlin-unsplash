package com.kotlin.unsplash.features.presentation.viewmodel

import android.app.Application
import androidx.lifecycle.*
import com.kotlin.unsplash.core.paging.CLIENT_ID
import com.kotlin.unsplash.features.domain.entities.Topic
import com.kotlin.unsplash.features.domain.repositories.Repository
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.launch
import javax.inject.Inject

@HiltViewModel
class TopicViewModel @Inject constructor (private val repository: Repository, application: Application): AndroidViewModel(application) {
    private val _listTopic = MutableLiveData<List<Topic>>()
    val listTopic: LiveData<List<Topic>>
    get() = _listTopic

    init {
        getListTopic()
    }

    private fun getListTopic(){
        viewModelScope.launch {
            try {
                val listResult = repository.getTopics(CLIENT_ID, 30)
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
