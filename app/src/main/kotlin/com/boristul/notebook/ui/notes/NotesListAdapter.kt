package com.boristul.notebook.ui.notes

import android.view.ViewGroup
import androidx.recyclerview.widget.RecyclerView
import com.boristul.entity.Note
import com.boristul.uikit.NoteCard
import org.joda.time.DateTimeZone
import org.joda.time.format.DateTimeFormat

class NotesListAdapter() : RecyclerView.Adapter<NotesListAdapter.ItemViewHolder>() {

    var notes: List<Note> = listOf()
        set(value) {
            field = value
            notifyDataSetChanged()
        }

    private val dateTimePattern = DateTimeFormat.forPattern("dd.MM.yyyy (HH:mm)")
        .withZone(DateTimeZone.getDefault())

    override fun getItemCount(): Int = notes.size

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): ItemViewHolder = ItemViewHolder(
        NoteCard(parent.context)
    ).apply {
        itemView.layoutParams = ViewGroup.LayoutParams(
            ViewGroup.LayoutParams.MATCH_PARENT,
            ViewGroup.LayoutParams.WRAP_CONTENT
        )
    }

    override fun onBindViewHolder(holder: ItemViewHolder, position: Int) {
        notes[position].let { note ->
            holder.card.run {
                title = note.title
                text = note.text
                datetime = note.creationTime.toString(dateTimePattern)
            }
        }
    }

    class ItemViewHolder(val card: NoteCard) : RecyclerView.ViewHolder(card)
}