package com.example.stuplan.network

import okhttp3.MultipartBody
import retrofit2.Response
import retrofit2.http.Header
import retrofit2.http.Multipart
import retrofit2.http.POST
import retrofit2.http.Part

data class ImgurResponse(
    val data: ImgurData,
    val success: Boolean,
    val status: Int
)

data class ImgurData(
    val id: String,
    val title: String?,
    val description: String?,
    val datetime: Long,
    val type: String,
    val animated: Boolean,
    val width: Int,
    val height: Int,
    val size: Int,
    val views: Int,
    val bandwidth: Long,
    val deletehash: String?,
    val name: String?,
    val link: String
)

interface ImgurServices {
    @Multipart
    @POST("3/image")
    suspend fun uploadImage(
        @Part image: MultipartBody.Part,
        @Header("Authorization") clientId: String
    ): Response<ImgurResponse>
}