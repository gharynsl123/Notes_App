package com.example.notesapp.ui.update

import android.os.Bundle
import android.view.*
import android.widget.Toast
import androidx.appcompat.widget.AppCompatImageButton
import androidx.fragment.app.Fragment
import androidx.fragment.app.viewModels
import androidx.navigation.fragment.findNavController
import androidx.navigation.fragment.navArgs
import com.example.notesapp.R
import com.example.notesapp.data.entity.Notes
import com.example.notesapp.databinding.FragmentUpdateBinding
import com.example.notesapp.ui.NotesViewModel
import com.example.notesapp.utils.HelperFunctions.parseToPriority
import com.example.notesapp.utils.HelperFunctions.spinnerListener
import com.example.notesapp.utils.setActionBar
import java.text.SimpleDateFormat
import java.util.*


class UpdateFragment : Fragment() {

    private var _binding : FragmentUpdateBinding? = null
    private val binding get() = _binding as FragmentUpdateBinding

    private val saveArgs: UpdateFragmentArgs by navArgs()

    private val updateViewModel by viewModels<NotesViewModel>()

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        // Inflate the layout for this fragment
        _binding = FragmentUpdateBinding.inflate(layoutInflater)
        return binding.root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        //inisialisasi variabel dataBinding yang ada di XML
        binding.saveArgs = saveArgs

        setHasOptionsMenu(true)

        binding.apply {
            toolbarUpdate.setActionBar(requireActivity())
            spinnerPrioritiesUpdate.onItemSelectedListener = spinnerListener(context, binding.priorityIndicator)
        }
    }

    override fun onCreateOptionsMenu(menu: Menu, inflater: MenuInflater) {
        super.onCreateOptionsMenu(menu, inflater)
        inflater.inflate(R.menu.menu_save, menu)
        val action = menu.findItem(R.id.action_save)
        action.actionView.findViewById<AppCompatImageButton>(R.id.btn_save).setOnClickListener {
            updateNote()
        }
    }

    private fun updateNote() {
        val title = binding.edtTitleUpdate.text.toString()
        val desc = binding.edtDescriptionUpdate.text.toString()
        val priority = binding.spinnerPrioritiesUpdate.selectedItem.toString()
        val date = Calendar.getInstance().time

        val formatedDate = SimpleDateFormat("dd MMM yyyy", Locale.getDefault()).format(date)

        findNavController().navigate(R.id.action_updateFragment_to_detailFragment)
        Toast.makeText(context, "Note has Been Update", Toast.LENGTH_SHORT).show()

        if (title.isEmpty()){
            binding.edtTitleUpdate.error = "Please Fill Field"
        } else if (desc.isEmpty()) {
            Toast.makeText(context, "Your Notes Is still empty", Toast.LENGTH_LONG).show()
        } else {
            updateViewModel.updateNote(Notes(saveArgs.currentItem.id,
                title,
                parseToPriority(priority, context),
                desc,
                formatedDate
            ))
            Toast.makeText(context, "successful add note.", Toast.LENGTH_SHORT).show()
            findNavController().navigate(R.id.action_updateFragment_to_detailFragment)
        }
    }

    override fun onDestroy() {
        super.onDestroy()
        _binding = null
    }


}