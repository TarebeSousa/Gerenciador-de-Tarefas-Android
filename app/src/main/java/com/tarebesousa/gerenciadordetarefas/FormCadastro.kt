package com.tarebesousa.gerenciadordetarefas

import android.graphics.Color
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import com.google.android.material.snackbar.Snackbar
import com.google.firebase.FirebaseNetworkException
import com.google.firebase.auth.FirebaseAuth
import com.google.firebase.auth.FirebaseAuthInvalidCredentialsException
import com.google.firebase.auth.FirebaseAuthUserCollisionException
import com.google.firebase.auth.FirebaseAuthWeakPasswordException
import com.tarebesousa.gerenciadordetarefas.databinding.ActivityFormCadastroBinding
import com.tarebesousa.gerenciadordetarefas.databinding.ActivityFormLoginBinding


class FormCadastro : AppCompatActivity() {

    private lateinit var binding: ActivityFormCadastroBinding
    private val auth = FirebaseAuth.getInstance()

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = ActivityFormCadastroBinding.inflate(layoutInflater)
        setContentView(binding.root)

        binding.btCadastrar.setOnClickListener{ view ->
            val nome = binding.editNome.text.toString()
            val email = binding.editEmail.text.toString()
            val senha = binding.editSenha.text.toString()

            if(nome.isEmpty() || email.isEmpty() || senha.isEmpty()){
                val snackbar = Snackbar.make(view, "Preencha todos os campos!", Snackbar.LENGTH_SHORT)
                snackbar.setBackgroundTint(Color.RED)
                snackbar.show()
            }
            else{
                auth.createUserWithEmailAndPassword(email, senha).addOnCompleteListener{ cadastro ->
                    if(cadastro.isSuccessful){
                        val snackbar = Snackbar.make(view, "Sucesso ao cadastrar", Snackbar.LENGTH_SHORT)
                        snackbar.setBackgroundTint(Color.GREEN)
                        snackbar.show()
                        binding.editEmail.setText("")
                        binding.editSenha.setText("")
                    }
                }.addOnFailureListener{ excecao ->
                    val mensagemErro = when(excecao){ /*Escolha caso*/
                        is FirebaseAuthWeakPasswordException -> "Digite uma senha com no minimo 6 caracteres!"
                        is FirebaseAuthInvalidCredentialsException -> "Digite um email válido!"
                        is FirebaseAuthUserCollisionException -> "Esta conta ja foi cadastrada!"
                        is FirebaseNetworkException -> "Sem conexão com a internet!"
                        else -> "Erro ao cadastrar usuario"
                    }
                    val snackbar = Snackbar.make(view, mensagemErro, Snackbar.LENGTH_SHORT)
                    snackbar.setBackgroundTint(Color.RED)
                    snackbar.show()
                }
            }
        }
    }
}