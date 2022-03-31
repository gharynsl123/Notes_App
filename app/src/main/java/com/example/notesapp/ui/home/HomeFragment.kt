package com.example.notesapp.ui.home

import android.app.AlertDialog
import android.os.Bundle
import android.view.*
import android.widget.SearchView
import android.widget.Toast
import androidx.fragment.app.Fragment
import androidx.fragment.app.viewModels
import androidx.navigation.fragment.findNavController
import androidx.navigation.ui.AppBarConfiguration
import androidx.navigation.ui.setupWithNavController
import androidx.recyclerview.widget.ItemTouchHelper
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView
import androidx.recyclerview.widget.StaggeredGridLayoutManager
import com.example.notesapp.ui.MainActivity
import com.example.notesapp.R
import com.example.notesapp.data.entity.Notes
import com.example.notesapp.databinding.FragmentHomeBinding
import com.example.notesapp.ui.NotesViewModel

class HomeFragment : Fragment(), SearchView.OnQueryTextListener {

    private var _binding: FragmentHomeBinding? = null
    private val binding get() = _binding as FragmentHomeBinding


    private val homeViewModel by viewModels<NotesViewModel>()
    private val homeAdapter by lazy { HomeAdapter() }

    private var _currentData: List<Notes>? = null
    private val currentData get() = _currentData as List<Notes>

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {
        // Inflate the layout for this fragment
        _binding = FragmentHomeBinding.inflate(layoutInflater)
        return binding.root

    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        //memberitahu kepada fragment bahwa fragment ini memiliki menu sendiri
        setHasOptionsMenu(true)

        //Kodingan tambahan untuk menampilkan Menu di fragment
        val navController = findNavController()
        val appBarConfiguration = AppBarConfiguration(navController.graph)

        binding.toolbarHome.apply {
            //memberitahu Mainactivity Yang tadi tidak ada App bar menjadi ada
            (requireActivity() as MainActivity).setSupportActionBar(this)
            //set Up Nac Controller
            setupWithNavController(navController, appBarConfiguration)
        }

        binding.fabHome.setOnClickListener {
            findNavController().navigate(R.id.action_homeFragment_to_addFragment)
        }

//        binding.btnDetail.setOnClickListener {
//            findNavController().navigate(R.id.action_homeFragment_to_detailFragment2)
//        }

        setUpRecyclerView()
    }

    private fun setUpRecyclerView() {
        binding.rvHome.apply {
            homeViewModel.getAllData().observe(viewLifecycleOwner) {
                checkIsDataEmpty(it)
                homeAdapter.setData(it)
                _currentData = it
            }
            adapter = homeAdapter
            // staggeredGridLayout = mengisi letak kosong terlebih dahulu pada layout
            layoutManager = StaggeredGridLayoutManager(2, LinearLayoutManager.VERTICAL)
            swipeToDelete(this)
        }
    }

    private fun checkIsDataEmpty(data: List<Notes>?) {
        binding.apply {
            if (data?.isEmpty() == true) {
                imgNoNotes.visibility = View.VISIBLE
                rvHome.visibility = View.INVISIBLE
            } else {
                imgNoNotes.visibility = View.INVISIBLE
                rvHome.visibility = View.VISIBLE
            }
        }
    }

    //membuat menu dari menu home
    override fun onCreateOptionsMenu(menu: Menu, inflater: MenuInflater) {
        inflater.inflate(R.menu.menu_home, menu)
        super.onCreateOptionsMenu(menu, inflater)

        val search = menu.findItem(R.id.menu_search)
        val searchAction = search.actionView as? SearchView
        searchAction?.setOnQueryTextListener(this)
    }
    override fun onOptionsItemSelected(item: MenuItem): Boolean {
        when (item.itemId) {
            R.id.menu_priority_high -> homeViewModel.sortByHighPriority()
                .observe(this) { dataHigh ->
                    homeAdapter.setData(dataHigh)
                }
            R.id.menu_priority_low -> homeViewModel.sortByLowPriority().observe(this) {
                homeAdapter.setData(it)
            }
            R.id.menu_delet_all -> confirmDelete()
        }
//        homeViewModel.deleteAllData()
        return super.onOptionsItemSelected(item)
    }

    private fun confirmDelete() {
        if (currentData.isEmpty()) {
            AlertDialog.Builder(requireContext())
                .setTitle(getString(R.string.no_notes)) // cara manggil string
                .setMessage("Gaada data sama sekali !")
                .setPositiveButton("Closed") { dialog, _ ->
                    dialog.dismiss()
                }
                .show()
        } else {
            AlertDialog.Builder(requireContext())
                .setTitle("Delete All Your Notes ?")
                .setMessage("Are You Sure want clear all of this data ?")
                .setPositiveButton("Yes") { _, _ ->
                    homeViewModel.deleteAllData()
                    Toast.makeText(
                        requireContext(),
                        "Successfully deleted data",
                        Toast.LENGTH_SHORT
                    ).show()
                }
                .setNegativeButton("No") { dialog, _ ->
                    dialog.dismiss()
                }.show()
        }
    }

    // ketika dicari
    override fun onQueryTextSubmit(query: String?): Boolean {
        val querySearch = "%$query%"
        query?.let {
            homeViewModel.searchByQuery(querySearch).observe(this){ dataSearch ->
                homeAdapter.setData(dataSearch)
            }
        }
        return true
    }

    // ketika diketik
    override fun onQueryTextChange(newText: String?): Boolean {
        val querySearch = "%$newText%"
        newText?.let {
            homeViewModel.searchByQuery(querySearch).observe(this){ dataSearch ->
                homeAdapter.setData(dataSearch)
            }
        }
        return true
    }

    private fun swipeToDelete(recyclerView: RecyclerView) {
        val swipeToDelete = object : ItemTouchHelper.SimpleCallback(// ItemTouchHelper supaya bisa menggeser kekiri atau kekanan
            0,
            ItemTouchHelper.LEFT or ItemTouchHelper.RIGHT
        ){
            override fun onMove(
                recyclerView: RecyclerView,
                viewHolder: RecyclerView.ViewHolder,
                target: RecyclerView.ViewHolder
            ): Boolean {
                return false
            }

            override fun onSwiped(viewHolder: RecyclerView.ViewHolder, direction: Int) {
                val deletedItem = homeAdapter.listNotes[viewHolder.adapterPosition]
                homeViewModel.deleteNote(deletedItem)
            }
        }
        val itemTouchHelper = ItemTouchHelper(swipeToDelete)
        itemTouchHelper.attachToRecyclerView(recyclerView)
    }

    override fun onDestroy() {
        super.onDestroy()
        _binding = null
    }


}