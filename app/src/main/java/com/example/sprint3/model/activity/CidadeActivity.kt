package com.example.sprint3.model.activity

import android.annotation.SuppressLint
import android.app.Activity
import android.os.Bundle
import android.widget.Button
import android.widget.EditText
import android.widget.Toast
import com.example.sprint3.R
import com.example.sprint3.model.Cidade
import com.example.sprint3.model.model.Cidade
import com.example.sprint3.repository.CidadeRepository

class CidadeActivity : Activity() {

    private lateinit var cidadeRepository: CidadeRepository

    @SuppressLint("MissingInflatedId")
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.cadastro_cidade) // Substitua pelo nome do seu layout XML

        // Referências para EditTexts
        val edtNomeCidade = findViewById<EditText>(R.id.edtNomeCidade)
        val edtCodigoIBGE = findViewById<EditText>(R.id.edtCodigoIBGE)
        val edtNumeroDDD = findViewById<EditText>(R.id.edtNumeroDDD)
        val btnCadastrar = findViewById<Button>(R.id.btnCadastrar)

        cidadeRepository = CidadeRepository()

        btnCadastrar.setOnClickListener {
            val nomeCidade = edtNomeCidade.text.toString()
            val codigoIBGE = edtCodigoIBGE.text.toString()
            val numeroDDD = edtNumeroDDD.text.toString()

            // Cria um novo objeto Cidade
            val cidade = Cidade(nome = nomeCidade, codigoIBGE = codigoIBGE, numeroDDD = numeroDDD)

            // Salva a cidade usando o repository
            cidadeRepository.cadastrarCidade(cidade) { sucesso, mensagem ->
                runOnUiThread {
                    if (sucesso) {
                        Toast.makeText(this, "Cidade cadastrada com sucesso!", Toast.LENGTH_SHORT).show()
                        // Limpa os campos após sucesso
                        edtNomeCidade.text.clear()
                        edtCodigoIBGE.text.clear()
                        edtNumeroDDD.text.clear()
                    } else {
                        Toast.makeText(this, "Erro ao cadastrar cidade: $mensagem", Toast.LENGTH_SHORT).show()
                    }
                }
            }
        }
    }
}
