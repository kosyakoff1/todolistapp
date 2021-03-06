package com.kosyakoff.todolistapp.fragments.list.adapter

import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.core.content.ContextCompat
import androidx.navigation.findNavController
import androidx.recyclerview.widget.DiffUtil
import androidx.recyclerview.widget.RecyclerView
import com.kosyakoff.todolistapp.R
import com.kosyakoff.todolistapp.data.models.Priority
import com.kosyakoff.todolistapp.data.models.ToDoData
import com.kosyakoff.todolistapp.databinding.RowLayoutBinding
import com.kosyakoff.todolistapp.fragments.list.ListFragmentDirections

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
            rowBackground.setOnClickListener {
                holder.itemView.findNavController()
                    .navigate(ListFragmentDirections.actionListFragmentToUpdateFragment(item))
            }
            val priorityColor = when (item.priority) {
                Priority.HIGH -> {
                    ContextCompat.getColor(priorityIndicator.context, R.color.red)
                }
                Priority.MEDIUM -> {
                    ContextCompat.getColor(priorityIndicator.context, R.color.yellow)
                }
                Priority.LOW -> {
                    ContextCompat.getColor(priorityIndicator.context, R.color.green)
                }
            }
            priorityIndicator.setCardBackgroundColor(priorityColor)
        }

    }

    override fun getItemCount(): Int = dataList.size

    fun setData(list: List<ToDoData>) {
        val toDoDiffUtil = ToDoDiffUtil(dataList, list)
        val diffResult = DiffUtil.calculateDiff(toDoDiffUtil)
        dataList = list
        diffResult.dispatchUpdatesTo(this)
    }

    fun getItem(index: Int): ToDoData = dataList[index]

    class MyViewHolder(val binding: RowLayoutBinding) : RecyclerView.ViewHolder(binding.root) {


    }
}