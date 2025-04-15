package com.example.practicaintents.ui.activities

import android.content.Intent
import android.os.Bundle
import android.widget.Toast
import androidx.activity.viewModels
import androidx.appcompat.app.AppCompatActivity
import androidx.core.view.ViewCompat
import androidx.core.view.WindowInsetsCompat
import com.example.practicaintents.databinding.ActivitySecondBinding
import com.example.practicaintents.viewmodels.LoginViewModel

class SecondActivity : AppCompatActivity() {

    private lateinit var binding: ActivitySecondBinding
    private val viewModel: LoginViewModel by viewModels()

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = ActivitySecondBinding.inflate(layoutInflater)
        setContentView(binding.root)

        ViewCompat.setOnApplyWindowInsetsListener(binding.root) { v, insets ->
            val systemBars = insets.getInsets(WindowInsetsCompat.Type.systemBars())
            v.setPadding(systemBars.left, systemBars.top, systemBars.right, systemBars.bottom)
            insets
        }
        val viewModel: LoginViewModel by viewModels()

        binding.btnOpenThirdActivity.setOnClickListener {
            val username = binding.txtName.text.toString()
            val password = binding.txtPassword.text.toString()
            viewModel.login(username, password)
        }

        viewModel.loginSuccess.observe(this) { success ->
            if (success) {
                val usernameFromInput = binding.txtName.text.toString()
                val intent = Intent(this, ThirdActivity::class.java)
                intent.putExtra("username", usernameFromInput)
                startActivity(intent)
            } else {
                Toast.makeText(this, "Usuario o contraseña incorrectos", Toast.LENGTH_SHORT).show()
            }
        }

    }
}
