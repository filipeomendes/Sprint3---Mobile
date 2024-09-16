package com.example.sprint3.model.Repository


import android.util.Log
import com.example.sprint3.model.model.User
import com.google.gson.Gson
import com.google.gson.reflect.TypeToken
import okhttp3.*
import okhttp3.MediaType.Companion.toMediaType
import okhttp3.RequestBody.Companion.toRequestBody
import java.io.IOException

class UserRepository {

    private val BASE_URL = "http://10.0.2.2:8080/clientes"
    private val cliente = OkHttpClient()
    private val gson = Gson()

    // Função para cadastrar um novo usuário
    fun cadastrarUsuario(user: User, callback: (Boolean, String?) -> Unit) {
        val userJson = gson.toJson(user)
        val requestBody = userJson.toRequestBody("application/json".toMediaType())

        val request = Request.Builder()
            .url(BASE_URL)
            .post(requestBody)
            .build()

        cliente.newCall(request).enqueue(object : Callback {
            override fun onFailure(call: Call, e: IOException) {
                Log.e("USER_REPOSITORY", "Erro para cadastrar o usuário: ${e.message}")
                callback(false, e.message)
            }

            override fun onResponse(call: Call, response: Response) {
                val respostaBody = response.body?.string()
                if (response.isSuccessful) {
                    Log.i("USER_REPOSITORY", "Usuário cadastrado. Resposta: $respostaBody")
                    callback(true, null)
                } else {
                    Log.e("USER_REPOSITORY", "Erro para cadastrar o usuário: ${response.message}")
                    callback(false, respostaBody ?: "Erro inesperado")
                }
            }
        })
    }

    // Função para buscar usuários (opcional, conforme necessidade)
    fun buscarUsuarios(callback: (List<User>?, String?) -> Unit) {
        val request = Request.Builder()
            .url(BASE_URL)
            .get()
            .build()

        cliente.newCall(request).enqueue(object : Callback {
            override fun onFailure(call: Call, e: IOException) {
                Log.e("USER_REPOSITORY", "Erro para busca de usuário: ${e.message}")
                callback(null, e.message)
            }

            override fun onResponse(call: Call, response: Response) {
                val respostaBody = response.body?.string()
                Log.i("USER_REPOSITORY", "Resposta: $respostaBody")

                if (response.isSuccessful && !respostaBody.isNullOrEmpty()) {
                    val type = object : TypeToken<List<User>>() {}.type
                    val listaUsuarios: List<User> = gson.fromJson(respostaBody, type)
                    callback(listaUsuarios, null)
                } else {
                    callback(emptyList(), "Não encontramos usuário informado")
                }
            }
        })
    }
}