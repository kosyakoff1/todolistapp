package com.kosyakoff.todolistapp.fragments.list

import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.recyclerview.widget.RecyclerView
import com.kosyakoff.todolistapp.R
import com.kosyakoff.todolistapp.data.models.Priority
import com.kosyakoff.todolistapp.data.models.ToDoData
import com.kosyakoff.todolistapp.databinding.RowLayoutBinding

class ListAdapter : RecyclerView.Adapter<ListAdapter.MyViewHolder>() {

    private var dataList = emptyList<ToDoData>()

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): MyViewHolder {
        val binding = RowLayoutBinding.inflate(LayoutInflater.from(parent.context), parent, false)
        return MyViewHolder(binding)
    }

    override fun onBindViewHolder(holder: MyViewHolder, position: Int) {
        with(holder.binding) {
            val item = dataList[position]
            titleTxt.text = item.title
            descriptionTxt.text = item.description
            val priorityColor = when (item.priority) {
                Priority.HIGH -> {
                    R.color.red
                }
                Priority.MEDIUM -> {
                    R.color.yellow
                }
                Priority.LOW -> {
                    R.color.green
                }
            }
            priorityIndicator.setCardBackgroundColor(priorityColor)
        }

    }

    override fun getItemCount(): Int = dataList.size

    fun setData(list: List<ToDoData>) {
        dataList = list
        notifyDataSetChanged()
    }

    class MyViewHolder(val binding: RowLayoutBinding) : RecyclerView.ViewHolder(binding.root) {


    }
}