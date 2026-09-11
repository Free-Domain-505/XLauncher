package com.example.xlauncher.api

import retrofit2.http.GET
import retrofit2.Retrofit
import retrofit2.converter.moshi.MoshiConverterFactory
import com.squareup.moshi.Moshi
import com.squareup.moshi.kotlin.reflect.KotlinJsonAdapterFactory

data class VersionManifest(
    val latest: Latest,
    val versions: List<MinecraftVersion>
)

data class Latest(
    val release: String,
    val snapshot: String
)

data class MinecraftVersion(
    val id: String,
    val type: String,
    val url: String,
    val time: String,
    val releaseTime: String
)

interface MojangApi {
    @GET("mc/game/version_manifest.json")
    suspend fun getVersionManifest(): VersionManifest
}

object RetrofitInstance {
    private const val MOJANG_BASE_URL = "https://launchermeta.mojang.com/"

    private val moshi = Moshi.Builder()
        .add(KotlinJsonAdapterFactory())
        .build()

    val mojangApi: MojangApi by lazy {
        Retrofit.Builder()
            .baseUrl(MOJANG_BASE_URL)
            .addConverterFactory(MoshiConverterFactory.create(moshi))
            .build()
            .create(MojangApi::class.java)
    }
}
