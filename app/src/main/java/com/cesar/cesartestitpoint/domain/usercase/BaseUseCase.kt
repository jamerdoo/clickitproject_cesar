package com.cesar.cesartestitpoint.domain.usercase

import kotlinx.coroutines.*
import kotlin.jvm.Throws


abstract class BaseUseCase<Response, in Request> {

    private var job = Job()
    private var uiScope = CoroutineScope(Dispatchers.Main + job)

    @Throws(Exception::class)
    abstract suspend fun run(params: Request? = null): Response

    open fun invoke(
        params: Request? = null,
        onResult: (Response) -> Unit,
        onError: (java.lang.Exception) -> Unit
    ) {
        uiScope.launch {
            try {
                val result = withContext(Dispatchers.IO) { run(params) }
                onResult(result)
            } catch (e: Exception) {
                onError(e)
            }
        }
    }

    open fun dispose() {
        job.cancel()
    }
}