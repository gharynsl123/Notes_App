package com.example.notesapp.ui.detail

import android.os.Bundle
import android.view.*
import android.widget.Toast
import androidx.appcompat.app.AlertDialog
import androidx.fragment.app.Fragment
import androidx.navigation.fragment.findNavController
import com.example.notesapp.R
import com.example.notesapp.databinding.FragmentDetailBinding
import com.example.notesapp.utils.setActionBar

class DetailFragment : Fragment() {

    private var _binding: FragmentDetailBinding? = null
    private val binding get() = _binding as FragmentDetailBinding

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {
        _binding = FragmentDetailBinding.inflate(layoutInflater)
        return binding.root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        setHasOptionsMenu(true)

        binding.toolbarDetail.setActionBar(requireActivity())
    }

    override fun onCreateOptionsMenu(menu: Menu, inflater: MenuInflater) {
        super.onCreateOptionsMenu(menu, inflater)
        inflater.inflate(R.menu.menu_detail, menu)
    }

    override fun onOptionsItemSelected(item: MenuItem): Boolean {
        when (item.itemId) {
            R.id.menu_edit -> findNavController().navigate(R.id.action_detailFragment_to_updateFragment)
            R.id.menu_delet -> confirmDeleteNote()
        }
        return super.onOptionsItemSelected(item)
    }

    private fun confirmDeleteNote() {
        context?.let {
            AlertDialog.Builder(it).setTitle("Are You Sure ?")
                .setMessage("If you sure want to remove. it will be delete for permanent")
                .setPositiveButton("of course") { _, _ ->
                    findNavController().navigate(R.id.action_detailFragment_to_homeFragment)
                    Toast.makeText(
                        it,
                        "Successfully Delete Note.",
                        Toast.LENGTH_LONG
                    ).show()
                }
                .setNegativeButton("No") { _, _ -> }.show()
        }
    }
}

