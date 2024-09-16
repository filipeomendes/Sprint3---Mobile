package com.example.sprint3.model.activity
import android.app.Activity
import android.os.Bundle
import android.widget.Button
import android.widget.EditText
import android.widget.Toast
import com.example.sprint3.R
import com.example.sprint3.model.Repository.UserRepository
import com.example.sprint3.model.model.User

class CadastroActivity : Activity() {

    private lateinit var usuarioRepository: UserRepository

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.cadastro_cliente)

        // Referências para EditTexts
        val edtNome = findViewById<EditText>(R.id.edtNome)
        val edtCPF = findViewById<EditText>(R.id.edtCPF)
        val edtRg = findViewById<EditText>(R.id.edtRg)
        val edtLogin = findViewById<EditText>(R.id.edtLogin)
        val edtSenha = findViewById<EditText>(R.id.edtSenha)
        val edtDataNascimento = findViewById<EditText>(R.id.edtDataNascimento)
        val btnCadastrar = findViewById<Button>(R.id.btnCadastrar)

        usuarioRepository = UserRepository()

        btnCadastrar.setOnClickListener {
            val nome = edtNome.text.toString()
            val cpf = edtCPF.text.toString()
            val rg = edtRg.text.toString()
            val login = edtLogin.text.toString()
            val senha = edtSenha.text.toString()
            val dataNascimento = edtDataNascimento.text.toString()

            // Cria um novo objeto User
            val user = User(nome, cpf, rg, login, senha, dataNascimento)

            // Salva o usuário usando o repository
            usuarioRepository.cadastrarUsuario(user) { sucesso, mensagem ->
                runOnUiThread {
                    if (sucesso) {
                        Toast.makeText(this, "v!", Toast.LENGTH_SHORT).show()
                        // Limpa os campos após sucesso
                        edtNome.text.clear()
                        edtCPF.text.clear()
                        edtRg.text.clear()
                        edtLogin.text.clear()
                        edtSenha.text.clear()
                        edtDataNascimento.text.clear()
                    } else {
                        Toast.makeText(this, "Erro para poder cadastrar usuário: $mensagem", Toast.LENGTH_SHORT).show()
                    }
                }
            }
        }
    }
}