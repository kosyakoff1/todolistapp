package com.kosyakoff.todolistapp.fragments.list

import android.app.AlertDialog
import android.os.Bundle
import android.view.*
import android.widget.Toast
import androidx.core.view.isVisible
import androidx.fragment.app.Fragment
import androidx.fragment.app.viewModels
import androidx.recyclerview.widget.LinearLayoutManager
import com.kosyakoff.todolistapp.R
import com.kosyakoff.todolistapp.data.viewmodel.ToDoViewModel
import com.kosyakoff.todolistapp.databinding.FragmentListBinding

class ListFragment : Fragment() {

    private lateinit var binding: FragmentListBinding
    private val listAdapter: ListAdapter by lazy { ListAdapter() }
    private val toDoViewModel: ToDoViewModel by viewModels()

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?,
    ): View {
        // Inflate the layout for this fragment
        binding = FragmentListBinding.inflate(layoutInflater, container, false)
        binding.lifecycleOwner = viewLifecycleOwner
        binding.todoViewModel = toDoViewModel

        binding.recyclerView.apply {
            adapter = listAdapter
            layoutManager = LinearLayoutManager(requireContext())
        }
        toDoViewModel.getAllData.observe(viewLifecycleOwner) { data ->
            listAdapter.setData(data)
//            binding.noDataTextView.isVisible = data.isEmpty()
//            binding.noDataImageView.isVisible = data.isEmpty()
        }


//        binding.apply {
//            floatingActionButton.setOnClickListener {
//                findNavController().navigate(R.id.action_listFragment_to_addFragment)
//            }
//        }

        setHasOptionsMenu(true)
        return binding.root
    }

    override fun onCreateOptionsMenu(menu: Menu, inflater: MenuInflater) {
        inflater.inflate(R.menu.list_fragment_menu, menu)
    }

    override fun onOptionsItemSelected(item: MenuItem): Boolean {

        if (item.itemId == R.id.menu_delete_all) {
            confirmRemoval()
        }
        return super.onOptionsItemSelected(item)
    }

    private fun confirmRemoval() {
        val builder = AlertDialog.Builder(requireContext())

        with(builder) {
            setPositiveButton(getString(R.string.frg_update_txt_question_yes)) { _, _ ->
                toDoViewModel.deleteAll()
                Toast.makeText(requireContext(),
                    getString(R.string.frg_update_txt_record_removed),
                    Toast.LENGTH_SHORT).show()
            }
            setNegativeButton(R.string.frg_update_txt_question_no) { _, _ -> }
            setTitle((getString(R.string.frg_list_txt_question_delete_simple)))
            setMessage(getString(R.string.frg_list_txt_question_delete))
            create().show()
        }
    }

}