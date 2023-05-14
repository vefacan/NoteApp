package com.example.noteapp.recyclerview

import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.TextView
import androidx.recyclerview.widget.RecyclerView
import com.example.noteapp.R
import com.example.noteapp.models.Note

class NoteAdapter() : RecyclerView.Adapter<NoteAdapter.NoteViewHolder>() {
    private var noteList: ArrayList<Note> = ArrayList()
    private var onClickItem: ((Note) ->Unit)? = null


    fun addItems(items: ArrayList<Note>) {
        this.noteList = items
        notifyDataSetChanged()
    }

    fun setOnClickItem(callback:((Note)->Unit)){
        this.onClickItem = callback
    }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int) = NoteViewHolder(

        LayoutInflater.from(parent.context).inflate(R.layout.card_items_notes, parent, false)
    )

    override fun onBindViewHolder(holder: NoteViewHolder, position: Int) {
        val note = noteList[position]
        holder.bindView(note)
        holder.itemView.setOnClickListener { onClickItem?.invoke(note)


        }
    }

    override fun getItemCount(): Int {
        return noteList.size
    }

    class NoteViewHolder(var view: View) : RecyclerView.ViewHolder(view) {

        private var title = view.findViewById<TextView>(R.id.txtTitle)

        fun bindView(note: Note) {
            title.text = note.title.toString()
        }

    }

}


