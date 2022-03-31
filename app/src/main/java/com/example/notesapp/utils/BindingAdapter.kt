package com.example.notesapp.utils

import android.widget.Spinner
import androidx.constraintlayout.widget.ConstraintLayout
import androidx.databinding.BindingAdapter
import androidx.navigation.findNavController
import com.example.notesapp.R
import com.example.notesapp.data.entity.Notes
import com.example.notesapp.data.entity.Priority
import com.example.notesapp.ui.detail.DetailFragment
import com.example.notesapp.ui.home.HomeFragmentDirections
import com.google.android.material.card.MaterialCardView

object BindingAdapter {
    @BindingAdapter("android:parsePriorityColor")
    @JvmStatic
    fun parsePriorityColor(cardView: MaterialCardView, priority: Priority) {
        when (priority) {
            Priority.LOW -> cardView.setCardBackgroundColor(cardView.context.getColor(R.color.green))
            Priority.MEDIUM -> cardView.setCardBackgroundColor(cardView.context.getColor(R.color.yellow))
            Priority.HIGH -> cardView.setCardBackgroundColor(cardView.context.getColor(R.color.pink))
        }
    }

    @BindingAdapter("android:sendDataToDetail")
    @JvmStatic
    fun sendDataToDetail(view: ConstraintLayout, currentItem: Notes) {
        view.setOnClickListener {
            val action = HomeFragmentDirections.actionHomeFragmentToDetailFragment2(currentItem)
            view.findNavController().navigate(action)
        }
    }

    @BindingAdapter("android:parsePriorityToInt")
    @JvmStatic
    fun parsePriorityToInt(view: Spinner, priority: Priority) {
        when (priority) {
            Priority.LOW -> view.setSelection(2)
            Priority.MEDIUM -> view.setSelection(1)
            Priority.HIGH -> view.setSelection(0)
        }
    }
}