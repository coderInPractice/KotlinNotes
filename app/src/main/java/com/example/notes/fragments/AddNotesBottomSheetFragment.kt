package com.example.notes.fragments

import android.os.Bundle
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.EditText
import android.widget.TextView
import androidx.lifecycle.ViewModelProvider
import com.example.notes.R
import com.example.notes.data.Notes
import com.example.notes.viewmodel.NotesListViewModel
import com.google.android.material.bottomsheet.BottomSheetDialogFragment

class AddNotesBottomSheetFragment : BottomSheetDialogFragment() {

    private lateinit var viewModel: NotesListViewModel

    private lateinit var titleEt:EditText
    private lateinit var descEt:EditText

    private lateinit var saveText:TextView

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        // Inflate the layout for this fragment
        val v =  inflater.inflate(R.layout.fragment_add_notes_bottom_sheet, container, false)

        titleEt = v.findViewById(R.id.note_title_et)
        descEt = v.findViewById(R.id.note_desc_et)
        saveText = v.findViewById(R.id.note_save_tv)
        return v
    }

    override fun onActivityCreated(savedInstanceState: Bundle?) {
        super.onActivityCreated(savedInstanceState)

        viewModel = ViewModelProvider(this).get(NotesListViewModel::class.java)

    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        saveText.setOnClickListener {
            addNotes()
        }
    }

    fun addNotes(){
        var title = titleEt.text.toString()
        var desc = descEt.text.toString()
        if(title.isNullOrEmpty() || desc.isNullOrEmpty()){
            if(title.isNullOrEmpty()){
                titleEt.error = "Cant be empty"
            }
            else{
                descEt.error = "Cant be empty"
            }
        }else{
            val note:Notes = Notes(title,desc)

            viewModel.addNotes(note)
            dismiss()
        }

    }
}