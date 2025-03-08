package com.issog.core.utils

import androidx.fragment.app.Fragment
import androidx.lifecycle.LiveData
import androidx.lifecycle.Observer

object ArchUtils {
    fun <T>Fragment.observe(livedata: LiveData<T>, observer: Observer<T>) {
        val mObserver = Observer<T> { value ->
            if (value is UiState.Loading) {
                observer.onChanged(value)
            } else {
                observer.onChanged(value)
                livedata.removeObservers(viewLifecycleOwner)
            }
        }
        livedata.observe(viewLifecycleOwner, mObserver)
    }
}