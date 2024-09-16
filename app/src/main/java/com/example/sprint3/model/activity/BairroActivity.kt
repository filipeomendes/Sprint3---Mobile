package com.example.sprint3.model.activity

import android.annotation.SuppressLint
import android.app.Activity
import android.os.Bundle
import android.widget.Button
import android.widget.EditText
import android.widget.Toast
import com.example.sprint3.R
import com.example.sprint3.model.model.Bairro
import com.example.sprint3.repository.BairroRepository

class BairroActivity : Activity() {

    private lateinit var bairroRepository: BairroRepository

    @SuppressLint("MissingInflatedId")
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.cadastro_bairro) // Substitua pelo nome do seu layout XML

        // Referências para EditTexts
        val edtNomeBairro = findViewById<EditText>(R.id.edtNomeBairro)
        val edtZonaNome = findViewById<EditText>(R.id.edtZonaNome)
        val btnCadastrar = findViewById<Button>(R.id.btnCadastrar)

        bairroRepository = BairroRepository()

        btnCadastrar.setOnClickListener {
            val nomeBairro = edtNomeBairro.text.toString()
            val zonaNome = edtZonaNome.text.toString()

            // Cria um novo objeto Bairro
            val bairro = Bairro(nome = nomeBairro, znNome = zonaNome)

            // Salva o bairro usando o repository
            bairroRepository.cadastrarBairro(bairro) { sucesso, mensagem ->
                runOnUiThread {
                    if (sucesso) {
                        Toast.makeText(this, "Bairro cadastrado com sucesso!", Toast.LENGTH_SHORT).show()
                        // Limpa os campos após sucesso
                        edtNomeBairro.text.clear()
                        edtZonaNome.text.clear()
                    } else {
                        Toast.makeText(this, "Erro ao cadastrar bairro: $mensagem", Toast.LENGTH_SHORT).show()
                    }
                }
            }
        }
    }
}
