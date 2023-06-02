package com.nelc.cakesizer.data

import android.content.Context
import androidx.compose.runtime.MutableState
import com.moandjiezana.toml.Toml
import com.nelc.cakesizer.R
import dagger.hilt.android.qualifiers.ApplicationContext
import io.ktor.client.HttpClient
import io.ktor.client.call.body
import io.ktor.client.plugins.onDownload
import io.ktor.client.request.request
import io.ktor.client.statement.bodyAsText
import io.ktor.http.ContentType
import io.ktor.http.HttpMethod
import io.ktor.http.URLProtocol
import io.ktor.http.contentType
import io.ktor.http.isSuccess
import io.ktor.http.path
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.withContext
import timber.log.Timber
import java.io.File
import javax.inject.Inject

class CakesServiceImpl @Inject constructor(
    private val client: HttpClient,
    @ApplicationContext private val context: Context,
) : CakesService {
    override suspend fun getCakes(): Result<List<Cake>> {
        return try {
            val response = client.request {
                url {
                    protocol = URLProtocol.HTTPS
                    host = context.resources.getString(R.string.server_host)
                    path("cakes.toml")
                }
                method = HttpMethod.Get
                contentType(ContentType.Application.Any)
            }

            when (response.status.value) {
                in 200..299 -> {
                    val responseDecoded =
                        Toml().read(response.bodyAsText()).to(CakesResponse::class.java)

                    Result.success(responseDecoded.cakes)
                }

                else -> {
                    Result.failure(Throwable("Request failed with ${response.status.value}"))
                }
            }
        } catch (e: Exception) {
            Timber.e(e)
            Result.failure(e)
        }
    }

    override suspend fun getCakeModel(
        modelId: String,
        modelPath: String,
        progress: MutableState<Long>
    ): Result<String> {
        return withContext(Dispatchers.IO) {
            return@withContext try {
                val file = File(context.filesDir, "${modelId}.glb")

                if (file.exists()) {
                    return@withContext Result.success(file.path)
                }

                val response = client.request {
                    url {
                        protocol = URLProtocol.HTTPS
                        host = context.resources.getString(R.string.server_host)
                        path(modelPath)
                    }
                    method = HttpMethod.Get
                    contentType(ContentType.Application.Any)
                    onDownload { bytesSentTotal, _ ->
                        progress.value = bytesSentTotal
                    }
                }

                if (response.status.isSuccess()) {
                    val responseBody: ByteArray = response.body()
                    file.writeBytes(responseBody)
                    Timber.i("Model file saved to ${file.path}")
                    Result.success(file.path)
                } else {
                    Result.failure(Throwable("Request failed with ${response.status.value}"))
                }
            } catch (e: Exception) {
                Timber.e(e)
                Result.failure(e)
            }
        }
    }
}