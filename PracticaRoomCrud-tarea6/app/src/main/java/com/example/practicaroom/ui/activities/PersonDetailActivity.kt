package com.example.practicaroom.ui.activities

import android.content.Context
import android.content.Intent
import android.os.Bundle
import android.widget.Toast
import androidx.activity.enableEdgeToEdge
import androidx.activity.viewModels
import androidx.appcompat.app.AppCompatActivity
import androidx.core.view.ViewCompat
import androidx.core.view.WindowInsetsCompat
import com.example.practicaroom.R
import com.example.practicaroom.databinding.ActivityPersonDetailBinding
import com.example.practicaroom.db.models.Pet
import com.example.practicaroom.ui.viewmodels.PersonDetailViewModel

class PersonDetailActivity : AppCompatActivity() {
    private var id: Int = 0
    private lateinit var binding: ActivityPersonDetailBinding
    private val viewModel: PersonDetailViewModel by viewModels()
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        enableEdgeToEdge()
        binding = ActivityPersonDetailBinding.inflate(layoutInflater)
        setContentView(binding.root)
        ViewCompat.setOnApplyWindowInsetsListener(findViewById(R.id.main)) { v, insets ->
            val systemBars = insets.getInsets(WindowInsetsCompat.Type.systemBars())
            v.setPadding(systemBars.left, systemBars.top, systemBars.right, systemBars.bottom)
            insets
        }
        setupViewModelObservers()
        setupEventListeners()
        id = intent.getIntExtra(PARAM_ID, 0)
        if (id == 0) {
            return
        }
        viewModel.loadPerson(this, id)
    }

    private fun setupEventListeners() {
        binding.btnDetailSave.setOnClickListener { doSave() }
        binding.btnDetailCancel.setOnClickListener { finish() }
    }

    private fun doSave() {
        val pet = viewModel.person.value ?: Pet("", "", 0)
        pet.apply {
            name = binding.txtName.text.toString()
            type = binding.txtType.text.toString()
            personId = binding.txtId.text.toString().toIntOrNull() ?:0

        }
        viewModel.savePerson(this, pet)

    }

    private fun setupViewModelObservers() {
        viewModel.person.observe(this) {
            if (it == null) {
                return@observe
            }
            binding.txtName.setText(it.name)
            binding.txtId.setText(it.personId)
        }
        viewModel.hasErrorSaving.observe(this) {
            if (it) {
                Toast.makeText(this, getString(R.string.error_saving_person), Toast.LENGTH_SHORT)
                    .show()
            }
        }
        viewModel.personSaved.observe(this) {
            if (it != null) {
                val isInsert = id == 0
                val resultIntent = MainActivity.returnIntent(isInsert, it)
                setResult(RESULT_OK, resultIntent)
                finish()
            }
        }
    }

    companion object {
        fun detailIntent(context: Context, id: Int): Intent {
            val intent = Intent(context, PersonDetailActivity::class.java)
            intent.putExtra(PARAM_ID, id)
            return intent
        }

        fun createIntent(context: Context): Intent {
            return Intent(context, PersonDetailActivity::class.java)
        }

        private const val PARAM_ID = "id"
    }
}