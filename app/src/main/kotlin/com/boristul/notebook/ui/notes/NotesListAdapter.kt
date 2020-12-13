package com.boristul.notebook.ui.notes

import android.view.ViewGroup
import androidx.constraintlayout.widget.ConstraintLayout
import androidx.recyclerview.widget.RecyclerView
import com.boristul.entity.Note
import com.boristul.entity.NoteWithTags
import com.boristul.uikit.NoteCard
import org.joda.time.DateTimeZone
import org.joda.time.format.DateTimeFormat

class NotesListAdapter : RecyclerView.Adapter<NotesListAdapter.ItemViewHolder>() {

    var notes: List<NoteWithTags> = listOf()
        set(value) {
            field = value
            notifyDataSetChanged()
        }

    private val dateTimePattern = DateTimeFormat.forPattern("dd.MM.yyyy HH:mm")
        .withZone(DateTimeZone.getDefault())

    var onDeleteClickListener: ((NoteWithTags) -> Unit)? = null
    var onClickListener: ((NoteWithTags) -> Unit)? = null

    override fun getItemCount(): Int = notes.size

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): ItemViewHolder = ItemViewHolder(
        NoteCard(parent.context)
    ).apply {
        itemView.layoutParams = ConstraintLayout.LayoutParams(
            ViewGroup.LayoutParams.MATCH_PARENT,
            ViewGroup.LayoutParams.WRAP_CONTENT
        )

        card.onClickListener = { onClickListener?.invoke(notes[adapterPosition]) }
        card.onDeleteClickListener = { onDeleteClickListener?.invoke(notes[adapterPosition]) }
    }

    override fun onBindViewHolder(holder: ItemViewHolder, position: Int) =
        notes[position].note.let { note ->
            holder.card.run {
                title = getTitle(note)
                datetime = note.creationTime.toString(dateTimePattern)
            }
        }

    private fun getTitle(note: Note): String = note.run { if (title.isNotBlank()) title else description }

    class ItemViewHolder(val card: NoteCard) : RecyclerView.ViewHolder(card)
}
