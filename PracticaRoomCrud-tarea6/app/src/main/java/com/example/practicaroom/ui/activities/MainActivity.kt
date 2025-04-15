package com.example.practicaroom.ui.activities

import android.content.Intent
import android.os.Build
import android.os.Bundle
import android.view.View
import androidx.activity.enableEdgeToEdge
import androidx.activity.result.ActivityResult
import androidx.activity.result.contract.ActivityResultContracts
import androidx.activity.viewModels
import androidx.appcompat.app.AppCompatActivity
import androidx.core.view.ViewCompat
import androidx.core.view.WindowInsetsCompat
import androidx.recyclerview.widget.DividerItemDecoration
import androidx.recyclerview.widget.LinearLayoutManager
import com.example.practicaroom.R
import com.example.practicaroom.databinding.ActivityMainBinding
import com.example.practicaroom.db.models.Pet
import com.example.practicaroom.ui.adapters.PetAdapter
import com.example.practicaroom.ui.viewmodels.MainViewModel

class MainActivity : AppCompatActivity(), PetAdapter.OnPetClickListener {
    private lateinit var binding: ActivityMainBinding
    private val viewModel: MainViewModel by viewModels()

    private val startForResult =
        registerForActivityResult(ActivityResultContracts.StartActivityForResult()) { result: ActivityResult ->
            result.data?.let {
                it.extras?.let { extras ->
                    onActivityResultCallback(extras)
                }
            }
        }


    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        enableEdgeToEdge()
        binding = ActivityMainBinding.inflate(layoutInflater)
        setContentView(binding.root)
        ViewCompat.setOnApplyWindowInsetsListener(findViewById(R.id.main)) { v, insets ->
            val systemBars = insets.getInsets(WindowInsetsCompat.Type.systemBars())
            v.setPadding(systemBars.left, systemBars.top, systemBars.right, systemBars.bottom)
            insets
        }

        setupRecyclerView()
        setupViewModelObservers()
        setupEventListeners()
        viewModel.loadData(this)

    }

    private fun setupEventListeners() {
        binding.fabCreatePerson.setOnClickListener {
            val intent = PersonDetailActivity.createIntent(this)
            startForResult.launch(intent)
        }
    }

    private fun setupViewModelObservers() {
        viewModel.personList.observe(this) {
            if (it == null || it.isEmpty()) {
                binding.lblEmptyText.visibility = View.VISIBLE
                binding.lstPersons.visibility = View.GONE
                return@observe
            }
            binding.lblEmptyText.visibility = View.GONE
            binding.lstPersons.visibility = View.VISIBLE
            val adapter = binding.lstPersons.adapter as PetAdapter
            adapter.setData(it)
        }
    }

    private fun setupRecyclerView() {
        val adapter = PetAdapter(mutableListOf())
        val dividerItemDecoration = DividerItemDecoration(
            binding.lstPersons.context,
            LinearLayoutManager.VERTICAL
        )
        binding.lstPersons.apply {
            this.adapter = adapter
            layoutManager = LinearLayoutManager(this@MainActivity).apply {
                orientation = LinearLayoutManager.VERTICAL
            }
            addItemDecoration(dividerItemDecoration)
        }
        adapter.setOnPetClickListener(this)
    }

    private fun onActivityResultCallback(extras: Bundle) {
        val personChanged = if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.TIRAMISU) {
            extras.getSerializable(PARAM_UPDATED_OBJECT, Pet::class.java)
        } else {
            extras.getSerializable(PARAM_UPDATED_OBJECT) as Pet
        }
        val inserted = extras.getBoolean(PARAM_INSERTED)
        if (inserted) {
            petInserted(personChanged)
        } else {
            petChanged(personChanged)
        }
    }

    private fun petInserted(petChanged: Pet?) {
        val adapter = binding.lstPersons.adapter as PetAdapter
        adapter.addItem(petChanged)
        if (adapter.itemCount > 0) {
            binding.lblEmptyText.visibility = View.GONE
            binding.lstPersons.visibility = View.VISIBLE
        }
    }

    private fun petChanged(petChanged: Pet?) {
        val adapter = binding.lstPersons.adapter as PetAdapter
        adapter.updateItem(petChanged)
    }

    override fun onPetClick(pet: Pet) {
        val id = pet.id
        val intent = PersonDetailActivity.detailIntent(this, id)
        startForResult.launch(intent)
    }

    override fun onPetDeleteClick(pet: Pet) {
        viewModel.deletePerson(this, pet)
        val adapter = binding.lstPersons.adapter as PetAdapter
        adapter.removeItem(pet)
        if (adapter.itemCount == 0) {
            binding.lblEmptyText.visibility = View.VISIBLE
            binding.lstPersons.visibility = View.GONE
        }
    }

    companion object {
        private const val PARAM_INSERTED = "inserted"
        private const val PARAM_UPDATED_OBJECT = "updatedPet"
        fun returnIntent(isInsert: Boolean, pet: Pet): Intent {
            return Intent().apply {
                putExtra(PARAM_INSERTED, isInsert)
                putExtra(PARAM_UPDATED_OBJECT, pet)
            }
        }
    }

}