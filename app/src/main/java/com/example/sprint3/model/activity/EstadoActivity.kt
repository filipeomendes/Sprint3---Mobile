package com.example.sprint3.model.activity

import android.annotation.SuppressLint
import android.app.Activity
import android.os.Bundle
import android.widget.Button
import android.widget.EditText
import android.widget.Toast
import com.example.sprint3.R
import com.example.sprint3.model.Estado
import com.example.sprint3.repository.EstadoRepository

class EstadoActivity : Activity() {

    private lateinit var estadoRepository: EstadoRepository

    @SuppressLint("MissingInflatedId")
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.estado_cadastro) // Substitua pelo nome do seu layout XML

        // Referências para EditTexts
        val edtSigla = findViewById<EditText>(R.id.edtSigla)
        val edtNome = findViewById<EditText>(R.id.edtNome)
        val btnCadastrar = findViewById<Button>(R.id.btnCadastrar)

        estadoRepository = EstadoRepository()

        btnCadastrar.setOnClickListener {
            val sigla = edtSigla.text.toString()
            val nome = edtNome.text.toString()

            // Cria um novo objeto Estado
            val estado = Estado(sigla = sigla, nome = nome)

            // Salva o estado usando o repository
            estadoRepository.cadastrarEstado(estado) { sucesso, mensagem ->
                runOnUiThread {
                    if (sucesso) {
                        Toast.makeText(this, "Estado cadastrado com sucesso!", Toast.LENGTH_SHORT).show()
                        // Limpa os campos após sucesso
                        edtSigla.text.clear()
                        edtNome.text.clear()
                    } else {
                        Toast.makeText(this, "Erro ao cadastrar estado: $mensagem", Toast.LENGTH_SHORT).show()
                    }
                }
            }
        }
    }
}