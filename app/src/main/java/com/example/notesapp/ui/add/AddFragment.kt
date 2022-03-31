package com.example.notesapp.ui.add

import android.os.Bundle
import android.view.*
import android.widget.Toast
import androidx.appcompat.widget.AppCompatImageButton
import androidx.fragment.app.Fragment
import androidx.fragment.app.viewModels
import androidx.navigation.fragment.findNavController
import com.example.notesapp.R
import com.example.notesapp.data.entity.Notes
import com.example.notesapp.data.entity.Priority
import com.example.notesapp.databinding.FragmentAddBinding
import com.example.notesapp.ui.NotesViewModel
import com.example.notesapp.utils.HelperFunctions
import com.example.notesapp.utils.HelperFunctions.parseToPriority
import com.example.notesapp.utils.setActionBar
import java.text.SimpleDateFormat
import java.util.*


class AddFragment : Fragment() {

    private var _binding: FragmentAddBinding? = null
    private val binding get() = _binding as FragmentAddBinding


    //If You can't Use The one You can make new class "ViewModelFactory"
    //ViewViewModelFactory for make some function to tell this activity Manually
    //but id you can use this you don't have make new class(Optional)
    private val addViewModels by viewModels<NotesViewModel>()

    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {
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
        binding.spinnerPriorities.onItemSelectedListener =
            HelperFunctions.spinnerListener(context, binding.priorityIndicator)
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

            val calender = Calendar.getInstance().time
            val date = SimpleDateFormat("dd MMMM yyyy", Locale.getDefault()).format(calender)

            val notes = Notes(0, title, parseToPriority(priority, context), description, date)

            //Make Some Decicion if title empty show error massage
            //and if the description is empty show alert
            if (edtTitle.text.isEmpty()) {
                edtTitle.error = "Please Fill Field"
            } else if (edtDescription.text.isEmpty()) {
                Toast.makeText(context, "Your Notes Is still empty", Toast.LENGTH_LONG).show()
            } else {
                addViewModels.insertData(notes)
                Toast.makeText(context, "successful add note.", Toast.LENGTH_SHORT).show()
                findNavController().navigate(R.id.action_addFragment_to_homeFragment)
            }
        }

    }

    /*
    override fun onContextItemSelected(item: MenuItem): Boolean {
        when(item.itemId){
            R.id.action_save ->
        }
       return super.onContextItemSelected(item)
    }*/

    override fun onDestroyView() {
        super.onDestroyView()
        _binding = null
    }
}