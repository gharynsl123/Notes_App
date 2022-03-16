package com.example.notesapp.ui.add

import android.os.Bundle
import android.view.*
import androidx.appcompat.widget.AppCompatImageButton
import androidx.fragment.app.Fragment
import androidx.navigation.fragment.findNavController
import com.example.notesapp.R
import com.example.notesapp.databinding.FragmentAddBinding
import com.example.notesapp.utils.HelperFunctions
import com.example.notesapp.utils.setActionBar


class AddFragment : Fragment() {

    private var _binding: FragmentAddBinding? = null
    private val binding get() = _binding as FragmentAddBinding

    override fun onCreateView(inflater: LayoutInflater, container: ViewGroup?, savedInstanceState: Bundle?): View {
        // Inflate the layout for this fragment
        _binding = FragmentAddBinding.inflate(layoutInflater)
        return binding.root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        //set This Fragment Have Option Menu
        setHasOptionsMenu(true)
        //Change Costume Back Arrow
        binding.toolbarAdd.setActionBar(requireActivity())
        //Set The Priority Spinner
        binding.spinnerPriorities.onItemSelectedListener = HelperFunctions.spinnerListener(context, binding.priorityIndicator)
    }

    override fun onCreateOptionsMenu(menu: Menu, inflater: MenuInflater) {
        inflater.inflate(R.menu.menu_save, menu)
        super.onCreateOptionsMenu(menu, inflater)
        val action = menu.findItem(R.id.action_save)
        action.actionView.findViewById<AppCompatImageButton>(R.id.btn_save).setOnClickListener {
            insertNotes()
        }
    }

    private fun insertNotes() {
        binding.apply {
            val title = edtTitle.text.toString()
            val priority = spinnerPriorities.selectedItem.toString()
            val description = edtDescription.text.toString()
        }
    }

    /*
    override fun onContextItemSelected(item: MenuItem): Boolean {
        when(item.itemId){
            R.id.action_save ->
        }
       return super.onContextItemSelected(item)
    }*/
}