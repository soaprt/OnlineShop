package prt.sostrovsky.onlineshopapp.network.response

import retrofit2.Response
import timber.log.Timber
import java.io.IOException

suspend fun <T : Any> safeApiCall(call: suspend () -> Response<T>, error: String): T? {
    val result = webResponseOutput(
        call,
        error
    )
    var output: T? = null
    when (result) {
        is WebResponse.Success -> output = result.output
        is WebResponse.Error -> Timber.e("${result.exception}")
    }
    return output

}

private suspend fun <T : Any> webResponseOutput(call: suspend () -> Response<T>, error: String):
        WebResponse<T> {
    val response = call.invoke()
    return if (response.isSuccessful)
        WebResponse.Success(response.body()!!)
    else
        WebResponse.Error(
            IOException(
                error
            )
        )
}