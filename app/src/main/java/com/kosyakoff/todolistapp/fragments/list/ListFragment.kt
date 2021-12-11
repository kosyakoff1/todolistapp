package com.kosyakoff.todolistapp.fragments.list

import android.app.AlertDialog
import android.os.Bundle
import android.view.*
import android.widget.Toast
import androidx.appcompat.widget.SearchView
import androidx.fragment.app.Fragment
import androidx.fragment.app.viewModels
import androidx.recyclerview.widget.ItemTouchHelper
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView
import androidx.recyclerview.widget.StaggeredGridLayoutManager
import com.google.android.material.snackbar.Snackbar
import com.kosyakoff.todolistapp.R
import com.kosyakoff.todolistapp.data.models.ToDoData
import com.kosyakoff.todolistapp.data.viewmodel.ToDoViewModel
import com.kosyakoff.todolistapp.databinding.FragmentListBinding
import com.kosyakoff.todolistapp.fragments.list.adapter.ListAdapter
import jp.wasabeef.recyclerview.animators.ScaleInAnimator

class ListFragment : Fragment(), SearchView.OnQueryTextListener {

    private lateinit var binding: FragmentListBinding
    private val listAdapter: ListAdapter by lazy { ListAdapter() }
    private val toDoViewModel: ToDoViewModel by viewModels()

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?,
    ): View {

        binding = FragmentListBinding.inflate(layoutInflater, container, false)
        binding.lifecycleOwner = viewLifecycleOwner
        binding.todoViewModel = toDoViewModel

        binding.recyclerView.apply {
            itemAnimator = ScaleInAnimator().apply { addDuration = 150 }
            adapter = listAdapter
            layoutManager = StaggeredGridLayoutManager(2, StaggeredGridLayoutManager.VERTICAL)
            addSwipeToDelete(this)
        }
        toDoViewModel.getAllData.observe(viewLifecycleOwner) { data ->
            listAdapter.setData(data)
        }

        setHasOptionsMenu(true)
        return binding.root
    }

    private fun addSwipeToDelete(recyclerView: RecyclerView) {
        val swipeToDeleteCallback =
            object : ItemTouchHelper.SimpleCallback(0, ItemTouchHelper.LEFT) {

                override fun onMove(
                    recyclerView: RecyclerView,
                    viewHolder: RecyclerView.ViewHolder,
                    target: RecyclerView.ViewHolder,
                ): Boolean {
                    return false
                }

                override fun onSwiped(viewHolder: RecyclerView.ViewHolder, direction: Int) {
                    val deletedItem = listAdapter.getItem(viewHolder.adapterPosition)
                    toDoViewModel.deleteData(deletedItem)
                    showRestoreDeletedDataSnackBar(viewHolder.itemView,
                        deletedItem)
                }
            }

        val itemTouchHelper = ItemTouchHelper(swipeToDeleteCallback)
        itemTouchHelper.attachToRecyclerView(recyclerView)
    }

    private fun showRestoreDeletedDataSnackBar(view: View, deletedItem: ToDoData) {
        Snackbar.make(view, "Deleted '${deletedItem.title}'", Snackbar.LENGTH_SHORT).apply {
            setAction("Undo") {
                toDoViewModel.insertData(deletedItem)
            }
            show()
        }
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

    private fun searchInDb(query: String) {
        toDoViewModel.searchDatabase(query).observe(viewLifecycleOwner) {
            listAdapter.setData(it)
        }
    }

    override fun onCreateOptionsMenu(menu: Menu, inflater: MenuInflater) {
        inflater.inflate(R.menu.list_fragment_menu, menu)

        (menu.findItem(R.id.menu_search).actionView as? SearchView)?.apply {
            isSubmitButtonEnabled = true
            setOnQueryTextListener(this@ListFragment)
        }
    }

    override fun onOptionsItemSelected(item: MenuItem): Boolean {

        when (item.itemId) {
            R.id.menu_delete_all -> {
                confirmRemoval()
            }
            R.id.menu_priority_high -> {
                toDoViewModel.sortByHighPriority.observe(viewLifecycleOwner) {
                    listAdapter.setData(it)
                }
            }
            R.id.menu_priority_low -> {
                toDoViewModel.sortByLowPriority.observe(viewLifecycleOwner) {
                    listAdapter.setData(it)
                }
            }
        }
        return super.onOptionsItemSelected(item)
    }

    override fun onQueryTextSubmit(query: String?): Boolean {
        if (query != null) {
            searchInDb(query)
        }
        return true
    }

    override fun onQueryTextChange(query: String?): Boolean {
        if (query != null) {
            searchInDb(query)
        }
        return true
    }

}