package com.alphaomardiallo.freewifiparis.common.domain

sealed class DomainResponse<T> {

    class Success<T>(val response: Any) : DomainResponse<T>()
    class Error<T>(val errorCode: Int?, val description: String?) : DomainResponse<T>()
    class Loading<T> : DomainResponse<T>()
}
