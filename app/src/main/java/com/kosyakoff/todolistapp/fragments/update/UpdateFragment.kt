package com.kosyakoff.todolistapp.fragments.update

import android.app.AlertDialog
import android.content.DialogInterface
import android.os.Bundle
import android.view.*
import android.widget.Toast
import androidx.fragment.app.Fragment
import androidx.fragment.app.viewModels
import androidx.navigation.fragment.findNavController
import androidx.navigation.fragment.navArgs
import com.kosyakoff.todolistapp.R
import com.kosyakoff.todolistapp.data.models.Priority
import com.kosyakoff.todolistapp.data.models.ToDoData
import com.kosyakoff.todolistapp.data.viewmodel.ToDoViewModel
import com.kosyakoff.todolistapp.databinding.FragmentUpdateBinding
import com.kosyakoff.todolistapp.fragments.SharedViewModel

class UpdateFragment : Fragment() {

    private lateinit var binding: FragmentUpdateBinding
    private val args by navArgs<UpdateFragmentArgs>()
    private val sharedViewModel: SharedViewModel by viewModels()
    private val toDoViewModel: ToDoViewModel by viewModels()

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?,
    ): View {

        setHasOptionsMenu(true)

        binding = FragmentUpdateBinding.inflate(layoutInflater, container, false)

        initViews()

        return binding.root
    }

    private fun initViews() {
        val item = args.currentItem
        with(binding) {
            currentTitleEt.setText(item.title)
            currentDescriptionEt.setText(item.description)
            currentPrioritiesSpinner.setSelection(Priority.values().indexOf(item.priority))
            currentPrioritiesSpinner.onItemSelectedListener = sharedViewModel.listener
        }
    }

    override fun onCreateOptionsMenu(menu: Menu, inflater: MenuInflater) {
        inflater.inflate(R.menu.update_fragment_menu, menu)
    }

    override fun onOptionsItemSelected(item: MenuItem): Boolean {

        when (item.itemId) {
            R.id.menu_save -> updateItem()
            R.id.menu_delete -> confirmItemRemoval()
        }
        return super.onOptionsItemSelected(item)
    }

    private fun confirmItemRemoval() {
        val builder = AlertDialog.Builder(requireContext())

        with(builder) {
            setPositiveButton(getString(R.string.frg_update_txt_question_yes)) { _, _ ->
                toDoViewModel.deleteData(args.currentItem)
                Toast.makeText(requireContext(),
                    getString(R.string.frg_update_txt_record_removed),
                    Toast.LENGTH_SHORT).show()
                findNavController().navigate(R.id.action_updateFragment_to_listFragment)
            }
            setNegativeButton(R.string.frg_update_txt_question_no) { _, _ -> }
            setTitle(String.format(getString(R.string.frg_update_txt_question_delete_simple),
                args.currentItem.title))
            setMessage(String.format(getString(R.string.frg_update_txt_question_delete),
                args.currentItem.title))
            create().show()
        }

    }

    private fun updateItem() {
        with(binding) {
            val title = currentTitleEt.text.toString()
            val description = currentDescriptionEt.text.toString()
            val priority = currentPrioritiesSpinner.selectedItem.toString()
            val validation = sharedViewModel.verifyDataFromUser(title, description)

            if (validation) {
                val updatedItem =
                    ToDoData(args.currentItem.id,
                        title,
                        sharedViewModel.parsePriorityString(priority),
                        description)

                toDoViewModel.updateData(updatedItem)

                Toast.makeText(requireContext(),
                    getString(R.string.frg_update_txt_record_updated),
                    Toast.LENGTH_SHORT).show()
                findNavController().navigate(R.id.action_updateFragment_to_listFragment)
            } else {
                Toast.makeText(requireContext(),
                    getString(R.string.frg_update_txt_fill_all),
                    Toast.LENGTH_SHORT).show()
            }
        }
    }

}