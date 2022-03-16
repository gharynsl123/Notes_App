package com.example.notesapp.utils

import android.content.Context
import android.view.View
import android.widget.Adapter
import android.widget.AdapterView
import androidx.cardview.widget.CardView
import androidx.core.content.ContextCompat
import com.example.notesapp.R

object HelperFunctions {
    fun  spinnerListener(context : Context?, priorityIndicator : CardView):AdapterView.OnItemSelectedListener = object  : AdapterView.OnItemSelectedListener{
        override fun onItemSelected(parent: AdapterView<*>?, view: View?, position: Int, id: Long) {
            context?.let {
                when(position){
                    0 -> {
                        //for chosing color
                        val pink = ContextCompat.getColor(it, R.color.pink)
                        //set background color and set radius
                        priorityIndicator.setCardBackgroundColor(pink)
                    }
                    1 -> {
                        val yellow = ContextCompat.getColor(it, R.color.yellow)
                        priorityIndicator.setCardBackgroundColor(yellow)
                    }
                    2 -> {
                        val green = ContextCompat.getColor(it, R.color.green)
                        priorityIndicator.setCardBackgroundColor(green)
                    }
                }
            }
        }

        override fun onNothingSelected(parent: AdapterView<*>?) {
            TODO("Not yet implemented")
        }
    }
}