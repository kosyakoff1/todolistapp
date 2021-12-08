package com.kosyakoff.todolistapp.fragments.add

import android.os.Bundle
import android.view.*
import android.widget.Toast
import androidx.fragment.app.Fragment
import androidx.fragment.app.viewModels
import androidx.navigation.fragment.findNavController
import com.kosyakoff.todolistapp.R
import com.kosyakoff.todolistapp.data.models.Priority
import com.kosyakoff.todolistapp.data.models.ToDoData
import com.kosyakoff.todolistapp.data.viewmodel.ToDoViewModel
import com.kosyakoff.todolistapp.databinding.FragmentAddBinding

class AddFragment : Fragment() {

    private val toDoViewModel: ToDoViewModel by viewModels()

    private lateinit var binding: FragmentAddBinding
    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?,
    ): View? {

        inflater.inflate(R.layout.fragment_add, container, false)
        binding = FragmentAddBinding.inflate(layoutInflater, container, false)
        setHasOptionsMenu(true)
        // Inflate the layout for this fragment
        return binding.root
    }

    override fun onCreateOptionsMenu(menu: Menu, inflater: MenuInflater) {
        inflater.inflate(R.menu.add_fragment_menu, menu)
    }

    override fun onOptionsItemSelected(item: MenuItem): Boolean {
        if (item.itemId == R.id.menu_add) {
            insertDataToDb()
        }
        return super.onOptionsItemSelected(item)
    }

    private fun insertDataToDb() {
        with(binding) {
            val title = binding.titleEt.text.toString()
            val priority = prioritiesSpinner.selectedItem.toString()
            val description = descriptionEt.text.toString()

            val validation = verifyDataFromUser(title, description)

            if (validation) {
                val newData = ToDoData(0, title, priority.parsePriorityString(), description)
                toDoViewModel.insertData(newData)
                Toast.makeText(
                    requireContext(),
                    getString(R.string.frg_add_txt_record_added),
                    Toast.LENGTH_SHORT
                ).show()
                findNavController().navigate(R.id.action_addFragment_to_listFragment)
            } else {
                Toast.makeText(
                    requireContext(),
                    getString(R.string.frg_add_txt_fill_all),
                    Toast.LENGTH_SHORT
                ).show()
            }
        }
    }

    private fun String.parsePriorityString(): Priority {
        val index = resources.getStringArray(R.array.priorities).indexOfFirst { x -> x == this }
        return Priority.values()[index]
    }

    private fun verifyDataFromUser(title: String, description: String): Boolean =
        title.isNotEmpty() && description.isNotEmpty()

}