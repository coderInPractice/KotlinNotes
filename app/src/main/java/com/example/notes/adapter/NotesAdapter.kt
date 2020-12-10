package com.example.notes.adapter

import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.TextView
import androidx.recyclerview.widget.RecyclerView
import com.example.notes.R
import com.example.notes.data.Notes

class NotesAdapter(val notesList:List<Notes>) : RecyclerView.Adapter<NotesAdapter.NotesViewHolder>() {

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): NotesViewHolder {
       val v = LayoutInflater.from(parent.context).inflate(R.layout.notes_row_items,parent,false)
        return NotesViewHolder(v)
    }

    override fun onBindViewHolder(holder: NotesViewHolder, position: Int) {
        val notes: Notes = notesList.get(position)
        holder.titleTv.setText(notes.title)
        holder.descTv.setText(notes.desc)
    }

    override fun getItemCount(): Int {
        return notesList.size
    }

    class NotesViewHolder(itemView:View) : RecyclerView.ViewHolder(itemView){

        val titleTv = itemView.findViewById<TextView>(R.id.title_tv)
        val descTv = itemView.findViewById<TextView>(R.id.desc_tv)

    }
}