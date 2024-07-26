package com.example.myapplication.Repository.FireBase

import android.text.Editable
import android.text.TextWatcher
import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.recyclerview.widget.RecyclerView
import com.example.myapplication.databinding.EditBinding

class EditableItemAdapter(
    private val items: MutableList<EditableItem>
) : RecyclerView.Adapter<EditableItemAdapter.EditableItemViewHolder>() {

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): EditableItemViewHolder {
        val binding = EditBinding.inflate(LayoutInflater.from(parent.context), parent, false)
        return EditableItemViewHolder(binding)
    }

    override fun onBindViewHolder(holder: EditableItemViewHolder, position: Int) {
        holder.bind(items[position])
    }

    override fun getItemCount(): Int = items.size

    inner class EditableItemViewHolder(private val binding: EditBinding) : RecyclerView.ViewHolder(binding.root) {

        fun bind(item: EditableItem) {
            binding.edit1.setText(item.text)
            binding.edit2.setText(item.text2)
            binding.edit3.setText(item.text3)
            binding.edit4.setText(item.text4)
            binding.edit5.setText(item.text5)
            binding.edit6.setText(item.text6)

            val textWatcher = object : TextWatcher {
                override fun beforeTextChanged(s: CharSequence?, start: Int, count: Int, after: Int) {}

                override fun onTextChanged(s: CharSequence?, start: Int, before: Int, count: Int) {}

                override fun afterTextChanged(s: Editable?) {
                    val text = s.toString()
                    if (binding.edit1.hasFocus()) {
                        item.text = text
                    } else if (binding.edit2.hasFocus()) {
                        item.text2 = text
                    } else if (binding.edit3.hasFocus()) {
                        item.text3 = text
                    } else if (binding.edit4.hasFocus()) {
                        item.text4 = text
                    } else if (binding.edit5.hasFocus()) {
                        item.text5 = text
                    } else if (binding.edit6.hasFocus()) {
                        item.text6 = text
                    }
                }
            }

            binding.edit1.addTextChangedListener(textWatcher)
            binding.edit2.addTextChangedListener(textWatcher)
            binding.edit3.addTextChangedListener(textWatcher)
            binding.edit4.addTextChangedListener(textWatcher)
            binding.edit5.addTextChangedListener(textWatcher)
            binding.edit6.addTextChangedListener(textWatcher)

            binding.edit1.setOnFocusChangeListener { _, hasFocus ->
                if (hasFocus) {
                    binding.edit1.setText("")
                } else {
                    binding.edit1.setText(item.text)
                }
            }
            binding.edit2.setOnFocusChangeListener { _, hasFocus ->
                if (hasFocus) {
                    binding.edit2.setText("")
                } else {
                    binding.edit2.setText(item.text2)
                }
            }
            binding.edit3.setOnFocusChangeListener { _, hasFocus ->
                if (hasFocus) {
                    binding.edit3.setText("")
                } else {
                    binding.edit3.setText(item.text3)
                }
            }
            binding.edit4.setOnFocusChangeListener { _, hasFocus ->
                if (hasFocus) {
                    binding.edit4.setText("")
                } else {
                    binding.edit4.setText(item.text4)
                }
            }
            binding.edit5.setOnFocusChangeListener { _, hasFocus ->
                if (hasFocus) {
                    binding.edit5.setText("")
                } else {
                    binding.edit5.setText(item.text5)
                }
            }
            binding.edit6.setOnFocusChangeListener { _, hasFocus ->
                if (hasFocus) {
                    binding.edit6.setText("")
                } else {
                    binding.edit6.setText(item.text6)
                }
            }
        }
    }
}