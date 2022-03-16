package com.example.notesapp.ui.home

import android.os.Bundle
import android.view.*
import androidx.fragment.app.Fragment
import androidx.navigation.fragment.findNavController
import androidx.navigation.ui.AppBarConfiguration
import androidx.navigation.ui.setupWithNavController
import com.example.notesapp.ui.MainActivity
import com.example.notesapp.R
import com.example.notesapp.databinding.FragmentHomeBinding

class HomeFragment : Fragment() {

    private var _binding: FragmentHomeBinding? = null
    private val binding get() = _binding as FragmentHomeBinding

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

        binding.btnDetail.setOnClickListener {
            findNavController().navigate(R.id.action_homeFragment_to_detailFragment2)
        }
    }
    //membuat menu dari menu home
    override fun onCreateOptionsMenu(menu: Menu, inflater: MenuInflater) {
        inflater.inflate(R.menu.menu_home, menu)
        super.onCreateOptionsMenu(menu, inflater)
    }
}