package com.example.sprint3.repository

import android.util.Log
import com.example.sprint3.model.Estado
import com.google.gson.Gson
import com.google.gson.reflect.TypeToken
import okhttp3.*
import okhttp3.MediaType.Companion.toMediaType
import okhttp3.RequestBody.Companion.toRequestBody
import java.io.IOException

class EstadoRepository {

    private val BASE_URL = "http://10.0.2.2:8080/estados"
    private val cliente = OkHttpClient()
    private val gson = Gson()

    // Função para cadastrar um novo estado
    fun cadastrarEstado(estado: Estado, callback: (Boolean, String?) -> Unit) {
        val estadoJson = gson.toJson(estado)
        val requestBody = estadoJson.toRequestBody("application/json".toMediaType())

        val request = Request.Builder()
            .url(BASE_URL)
            .post(requestBody)
            .build()

        cliente.newCall(request).enqueue(object : Callback {
            override fun onFailure(call: Call, e: IOException) {
                Log.e("ESTADO_REPOSITORY", "Erro para cadastrar o estado: ${e.message}")
                callback(false, e.message)
            }

            override fun onResponse(call: Call, response: Response) {
                val respostaBody = response.body?.string()
                if (response.isSuccessful) {
                    Log.i("ESTADO_REPOSITORY", "Estado cadastrado. Resposta: $respostaBody")
                    callback(true, null)
                } else {
                    Log.e("ESTADO_REPOSITORY", "Erro para cadastrar o estado: ${response.message}")
                    callback(false, respostaBody ?: "Erro inesperado")
                }
            }
        })
    }

    // Função para listar todos os estados
    fun listarEstados(callback: (List<Estado>?, String?) -> Unit) {
        val request = Request.Builder()
            .url(BASE_URL)
            .get()
            .build()

        cliente.newCall(request).enqueue(object : Callback {
            override fun onFailure(call: Call, e: IOException) {
                Log.e("ESTADO_REPOSITORY", "Erro para buscar estados: ${e.message}")
                callback(null, e.message)
            }

            override fun onResponse(call: Call, response: Response) {
                val respostaBody = response.body?.string()
                Log.i("ESTADO_REPOSITORY", "Resposta: $respostaBody")

                if (response.isSuccessful && !respostaBody.isNullOrEmpty()) {
                    val type = object : TypeToken<List<Estado>>() {}.type
                    val listaEstados: List<Estado> = gson.fromJson(respostaBody, type)
                    callback(listaEstados, null)
                } else {
                    callback(emptyList(), "Nenhum estado encontrado")
                }
            }
        })
    }
}
