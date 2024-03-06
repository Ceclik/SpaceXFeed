package com.yyi.spacexfeed.viewModels

import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel

class SpaceEventsViewModel : ViewModel() {
    val data: MutableLiveData<String> = MutableLiveData()
}