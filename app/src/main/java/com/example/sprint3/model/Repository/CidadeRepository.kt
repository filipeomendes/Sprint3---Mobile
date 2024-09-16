package com.example.sprint3.repository

import android.util.Log
import com.example.sprint3.model.model.Cidade
import com.google.gson.Gson
import com.google.gson.reflect.TypeToken
import okhttp3.*
import okhttp3.MediaType.Companion.toMediaType
import okhttp3.RequestBody.Companion.toRequestBody
import java.io.IOException

class CidadeRepository {

    private val BASE_URL = "http://10.0.2.2:8080/cidades"
    private val cliente = OkHttpClient()
    private val gson = Gson()

    // Função para cadastrar uma nova cidade
    fun cadastrarCidade(cidade: Cidade, callback: (Boolean, String?) -> Unit) {
        val cidadeJson = gson.toJson(cidade)
        val requestBody = cidadeJson.toRequestBody("application/json".toMediaType())

        val request = Request.Builder()
            .url(BASE_URL)
            .post(requestBody)
            .build()

        cliente.newCall(request).enqueue(object : Callback {
            override fun onFailure(call: Call, e: IOException) {
                Log.e("CIDADE_REPOSITORY", "Erro ao cadastrar a cidade: ${e.message}")
                callback(false, e.message)
            }

            override fun onResponse(call: Call, response: Response) {
                val respostaBody = response.body?.string()
                if (response.isSuccessful) {
                    Log.i("CIDADE_REPOSITORY", "Cidade cadastrada. Resposta: $respostaBody")
                    callback(true, null)
                } else {
                    Log.e("CIDADE_REPOSITORY", "Erro ao cadastrar a cidade: ${response.message}")
                    callback(false, respostaBody ?: "Erro inesperado")
                }
            }
        })
    }

    // Função para listar todas as cidades
    fun listarCidades(callback: (List<Cidade>?, String?) -> Unit) {
        val request = Request.Builder()
            .url(BASE_URL)
            .get()
            .build()

        cliente.newCall(request).enqueue(object : Callback {
            override fun onFailure(call: Call, e: IOException) {
                Log.e("CIDADE_REPOSITORY", "Erro ao buscar cidades: ${e.message}")
                callback(null, e.message)
            }

            override fun onResponse(call: Call, response: Response) {
                val respostaBody = response.body?.string()
                Log.i("CIDADE_REPOSITORY", "Resposta: $respostaBody")

                if (response.isSuccessful && !respostaBody.isNullOrEmpty()) {
                    val type = object : TypeToken<List<Cidade>>() {}.type
                    val listaCidades: List<Cidade> = gson.fromJson(respostaBody, type)
                    callback(listaCidades, null)
                } else {
                    callback(emptyList(), "Nenhuma cidade encontrada")
                }
            }
        })
    }
}
