package com.example.sprint3.repository

import android.util.Log
import com.example.sprint3.model.model.Bairro
import com.google.gson.Gson
import com.google.gson.reflect.TypeToken
import okhttp3.*
import okhttp3.MediaType.Companion.toMediaType
import okhttp3.RequestBody.Companion.toRequestBody
import java.io.IOException

class BairroRepository {

    private val BASE_URL = "http://10.0.2.2:8080/bairros"
    private val cliente = OkHttpClient()
    private val gson = Gson()

    // Função para cadastrar um novo bairro
    fun cadastrarBairro(bairro: Bairro, callback: (Boolean, String?) -> Unit) {
        val bairroJson = gson.toJson(bairro)
        val requestBody = bairroJson.toRequestBody("application/json".toMediaType())

        val request = Request.Builder()
            .url(BASE_URL)
            .post(requestBody)
            .build()

        cliente.newCall(request).enqueue(object : Callback {
            override fun onFailure(call: Call, e: IOException) {
                Log.e("BAIRRO_REPOSITORY", "Erro ao cadastrar o bairro: ${e.message}")
                callback(false, e.message)
            }

            override fun onResponse(call: Call, response: Response) {
                val respostaBody = response.body?.string()
                if (response.isSuccessful) {
                    Log.i("BAIRRO_REPOSITORY", "Bairro cadastrado com sucesso. Resposta: $respostaBody")
                    callback(true, null)
                } else {
                    Log.e("BAIRRO_REPOSITORY", "Erro ao cadastrar o bairro: ${response.message}")
                    callback(false, respostaBody ?: "Erro inesperado")
                }
            }
        })
    }

    // Função para listar todos os bairros
    fun listarBairros(callback: (List<Bairro>?, String?) -> Unit) {
        val request = Request.Builder()
            .url(BASE_URL)
            .get()
            .build()

        cliente.newCall(request).enqueue(object : Callback {
            override fun onFailure(call: Call, e: IOException) {
                Log.e("BAIRRO_REPOSITORY", "Erro ao buscar bairros: ${e.message}")
                callback(null, e.message)
            }

            override fun onResponse(call: Call, response: Response) {
                val respostaBody = response.body?.string()
                Log.i("BAIRRO_REPOSITORY", "Resposta: $respostaBody")

                if (response.isSuccessful && !respostaBody.isNullOrEmpty()) {
                    val type = object : TypeToken<List<Bairro>>() {}.type
                    val listaBairros: List<Bairro> = gson.fromJson(respostaBody, type)
                    callback(listaBairros, null)
                } else {
                    callback(emptyList(), "Nenhum bairro encontrado")
                }
            }
        })
    }
}
