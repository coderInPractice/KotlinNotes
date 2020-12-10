package com.example.notes.fragments

import android.os.Bundle
import android.util.Log
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.Toast
import androidx.fragment.app.Fragment
import androidx.lifecycle.Observer
import androidx.lifecycle.ViewModelProvider
import androidx.navigation.fragment.findNavController
import androidx.recyclerview.widget.ItemTouchHelper
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView
import com.example.notes.R
import com.example.notes.adapter.NotesAdapter
import com.example.notes.data.Notes
import com.example.notes.viewmodel.NotesListViewModel
import com.google.android.material.floatingactionbutton.FloatingActionButton


class NotesListFragment : Fragment() {

    private  val TAG = "NotesListFragment"

    private lateinit var add_note: FloatingActionButton

    private lateinit var viewModel: NotesListViewModel

    private lateinit var listRv: RecyclerView

    private lateinit var listAdapter: NotesAdapter

    private lateinit var addNoteList:List<Notes>

    override fun onCreateView(
            inflater: LayoutInflater, container: ViewGroup?,
            savedInstanceState: Bundle?
    ): View? {
        val view =  inflater.inflate(R.layout.notes_list_fragment, container, false)

        add_note = view.findViewById(R.id.add_notes_fab)
        listRv = view.findViewById(R.id.notes_rv)

        listRv.layoutManager = LinearLayoutManager(context)

        add_note.setOnClickListener(View.OnClickListener {
            findNavController().navigate(R.id.action_notesListFragment_to_addNotesBottomSheetFragment)
        })
        return view
    }

    override fun onActivityCreated(savedInstanceState: Bundle?) {
        super.onActivityCreated(savedInstanceState)
        viewModel = ViewModelProvider(this).get(NotesListViewModel::class.java)
        // TODO: Use the ViewModel

        viewModel.getAllNotes().observe(viewLifecycleOwner, Observer{ it ->


            addNoteList = it
            listAdapter = NotesAdapter(addNoteList)

            listRv.adapter = listAdapter


        })

        ItemTouchHelper(object : ItemTouchHelper.SimpleCallback(0, ItemTouchHelper.LEFT.or(ItemTouchHelper.RIGHT)) {
            override fun onMove(
                    recyclerView: RecyclerView,
                    viewHolder: RecyclerView.ViewHolder,
                    target: RecyclerView.ViewHolder
            ): Boolean {
                return false
            }

            override fun onSwiped(viewHolder: RecyclerView.ViewHolder, direction: Int) {
                val position = viewHolder.adapterPosition
                val id = viewModel.getId(position)
                Log.d(TAG, "onSwiped: ${id}")
                viewModel.deleteNote(id)

                listAdapter.notifyItemRemoved(position)
                Toast.makeText(context, "Note Deleted!", Toast.LENGTH_SHORT).show()
            }
        }
        ).attachToRecyclerView(listRv)



    }



}