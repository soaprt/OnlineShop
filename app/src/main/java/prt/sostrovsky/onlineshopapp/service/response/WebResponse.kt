package prt.sostrovsky.onlineshopapp.service.response

sealed class WebResponse <out T : Any> {
    data class Success<out T : Any>(val output : T) : WebResponse<T>()
    data class Error(val exception: Exception)  : WebResponse<Nothing>()
}