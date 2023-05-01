package br.com.fiap25mob.mbamobile.presentation

import android.content.Intent
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.util.Patterns
import android.view.Window
import android.view.WindowManager
import android.widget.Toast
import br.com.fiap25mob.mbamobile.R
import br.com.fiap25mob.mbamobile.databinding.ActivityRegisterBinding
import com.google.firebase.auth.FirebaseAuth

class RegisterActivity : AppCompatActivity() {

    private val binding: ActivityRegisterBinding by lazy { ActivityRegisterBinding.inflate(layoutInflater) }

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        fullScreen()
        setContentView(binding.root)

        binding.btRegister.setOnClickListener {

            val email = binding.inputEmail.text.toString()
            val password = binding.inputPassword.text.toString()
            val passwordConfirm = binding.inputPasswordConfirm.text.toString()

            if ((email.isBlank() || !Patterns.EMAIL_ADDRESS.matcher(email).matches())) {
                toast(getString(R.string.login_invalid_email))
                return@setOnClickListener
            }

            if (password.isBlank()) {
                toast(getString(R.string.login_invalid_password))
                return@setOnClickListener
            }

            if (password.length < 8) {
                toast(getString(R.string.register_invalid_password_len))
                return@setOnClickListener
            }

            if (password != passwordConfirm) {
                toast(getString(R.string.register_invalid_password_check))
                return@setOnClickListener
            }

            FirebaseAuth.getInstance().createUserWithEmailAndPassword(email, password).addOnCompleteListener {
                if (it.isSuccessful) {
                    startActivity(Intent(this, MainActivity::class.java))
                    finish()
                } else {
                    toast(getString(R.string.login_invalid_credentials))
                }
            }
        }

        binding.btBackLogin.setOnClickListener {
            openLogin()
        }
    }

    private fun fullScreen() {
        supportRequestWindowFeature(Window.FEATURE_NO_TITLE)
        window.setFlags(WindowManager.LayoutParams.FLAG_FULLSCREEN, WindowManager.LayoutParams.FLAG_FULLSCREEN
        )
        supportActionBar?.hide()
    }

    override fun onBackPressed() {
        openLogin()
    }

    private fun openLogin(){
        startActivity(Intent(this, LoginActivity::class.java))
        finish()
    }

    private fun toast(message: String) {
        Toast.makeText(applicationContext, message, Toast.LENGTH_SHORT).show()
    }
}