package com.srmanager.core.common.state

import com.srmanager.core.common.exception.Failure
import com.srmanager.core.common.util.INTERNAL_ERROR

sealed class DataResponse<T>

data class DataSuccessResponse<T>(
    val data: T? = null,
) : DataResponse<T>()

data class DataErrorResponse<T>(
    val statusCode: Int = INTERNAL_ERROR,
    val reason: Failure = Failure.None,
    val errorMessage: String? = null,
) : DataResponse<T>()