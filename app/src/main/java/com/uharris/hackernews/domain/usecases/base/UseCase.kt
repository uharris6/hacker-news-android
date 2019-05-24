package com.uharris.hackernews.domain.usecases.base

import com.uharris.hackernews.data.base.Result
import kotlinx.coroutines.CoroutineScope
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.async
import kotlinx.coroutines.launch

abstract class UseCase<out Type, in Params> where Type: Any {

    abstract suspend fun run(params: Params): Result<Type>

    operator fun invoke(params: Params, onResult: (Result<Type>) -> Unit = {}) {
        val job = CoroutineScope(Dispatchers.IO).async {
            run(params)
        }

        CoroutineScope(Dispatchers.Main).launch {
            onResult(job.await())
        }
    }

    class None
}