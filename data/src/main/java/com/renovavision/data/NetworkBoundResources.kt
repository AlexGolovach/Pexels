package com.renovavision.data

import androidx.lifecycle.MutableLiveData
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.withContext

class NetworkBoundResources<RESULT, REQUEST>(
    private val save: (REQUEST) -> Unit,
    private val local: () -> RESULT,
    private val remote: suspend () -> REQUEST
) {

    private val result = MutableLiveData<RESULT>()

    suspend fun load(): RESULT? {
        withContext(Dispatchers.IO) {
            runCatching {
                val apiResponse = remote.invoke()
                save.invoke(apiResponse)
                setValue(local.invoke())
            }.getOrElse {
                setValue(local.invoke())
            }
        }

        return result.value
    }

    private fun setValue(newValue: RESULT) {
        newValue?.let {
            if (result.value != it) {
                result.postValue(it)
            }
        }
    }
}