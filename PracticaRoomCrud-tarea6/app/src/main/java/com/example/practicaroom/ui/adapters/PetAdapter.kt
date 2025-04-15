package com.example.practicaroom.ui.adapters

import android.util.Log
import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.recyclerview.widget.RecyclerView
import com.example.practicaroom.databinding.PersonItemViewBinding
import com.example.practicaroom.db.models.Pet

class PetAdapter(
    private var data: MutableList<Pet>
) : RecyclerView.Adapter<PetAdapter.ViewHolder>() {
    private var listener: OnPetClickListener? = null
    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): ViewHolder {
        val inflater = LayoutInflater.from(parent.context)
        val binding = PersonItemViewBinding.inflate(inflater, parent, false)
        return ViewHolder(binding)
    }

    override fun getItemCount(): Int {
        return data.size
    }

    override fun onBindViewHolder(holder: ViewHolder, position: Int) {
        val item = data[position]
        holder.bind(item, listener)
    }

    fun setData(newData: MutableList<Pet>) {
        this.data = newData
        notifyDataSetChanged()
    }

    fun setOnPetClickListener(listener: OnPetClickListener) {
        this.listener = listener
    }

    fun removeItem(pet: Pet) {
        val index = data.indexOfFirst { it.id == pet.id }
        Log.d("Pet", "Removing item at index $index")
        data.removeAt(index)
        notifyItemRemoved(index)
    }

    fun updateItem(petChanged: Pet?) {
        val index = data.indexOfFirst { it.id == petChanged?.id }
        if (index != -1) {
            data[index] = petChanged!!
            notifyItemChanged(index)
        }
    }

    fun addItem(petChanged: Pet?) {
        if(data.size == 0) {
            data.add(petChanged!!)
            notifyItemInserted(0)
            return
        }
        data.add(1, petChanged!!)
        notifyItemInserted(1)
    }

    class ViewHolder(private val binding: PersonItemViewBinding) :
        RecyclerView.ViewHolder(binding.root) {
        fun bind(item: Pet, listener: OnPetClickListener?) {
            binding.lblPhone.text = " ${item.type}"
            binding.lblFullName.text = "${item.name}"
            binding.root.setOnClickListener {
                listener?.onPetClick(item)
            }
            binding.btnDeletePerson.setOnClickListener {
                listener?.onPetDeleteClick(item)
            }
        }
    }

    interface OnPetClickListener {
        fun onPetClick(person: Pet)
        fun onPetDeleteClick(person: Pet)
    }
}

