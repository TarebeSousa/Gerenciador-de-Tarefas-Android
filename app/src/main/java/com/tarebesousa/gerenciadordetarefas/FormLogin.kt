package com.tarebesousa.gerenciadordetarefas

import android.content.Intent
import android.graphics.Color
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import com.google.android.material.snackbar.Snackbar
import com.google.firebase.auth.FirebaseAuth
import com.tarebesousa.gerenciadordetarefas.databinding.ActivityFormLoginBinding

class FormLogin : AppCompatActivity() {

    private lateinit var binding: ActivityFormLoginBinding
    private val auth = FirebaseAuth.getInstance()

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = ActivityFormLoginBinding.inflate(layoutInflater)
        setContentView(binding.root)

        binding.btEntrar.setOnClickListener(){ view ->
            val email = binding.editEmailLogin.text.toString()
            val senha = binding.editSenhaLogin.text.toString()

            if(email.isEmpty() || senha.isEmpty()){
                val snackbar = Snackbar.make(view, "Preencha todos os dados!", Snackbar.LENGTH_SHORT)
                snackbar.setBackgroundTint(Color.RED)
                snackbar.show()
            }
            else{
                auth.signInWithEmailAndPassword(email, senha).addOnCompleteListener{autenticacao ->
                    if(autenticacao.isSuccessful){
                        val intent = Intent(this, TelaPrincipal::class.java)
                        startActivity(intent)
                        finish()
                    }
                }.addOnFailureListener {
                    val snackbar = Snackbar.make(view, "Erro ao facer o login do usuario", Snackbar.LENGTH_SHORT)
                    snackbar.setBackgroundTint(Color.RED)
                    snackbar.show()
                }
            }
        }

        binding.txtTelaDeCadastro.setOnClickListener{
            val intent = Intent(this, FormCadastro::class.java)
            startActivity(intent)
            finish()
        }
    }
}