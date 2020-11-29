package com.boristul.notebook.ui.planner

import android.view.ViewGroup
import androidx.constraintlayout.widget.ConstraintLayout
import androidx.recyclerview.widget.RecyclerView
import com.boristul.entity.TaskPoint
import com.boristul.uikit.TaskPointCard

class TaskListAdapter : RecyclerView.Adapter<TaskListAdapter.ItemViewHolder>() {

    var tasks: List<TaskPoint> = listOf()
        set(value) {
            field = value
            notifyDataSetChanged()
        }

    var onDeleteClickListener: ((TaskPoint) -> Unit)? = null
    var onClickListener: ((TaskPoint) -> Unit)? = null

    override fun getItemCount(): Int = tasks.size

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): ItemViewHolder = ItemViewHolder(
        TaskPointCard(parent.context)
    ).apply {
        itemView.layoutParams = ConstraintLayout.LayoutParams(
            ViewGroup.LayoutParams.MATCH_PARENT,
            ViewGroup.LayoutParams.WRAP_CONTENT
        )

        card.surfaceView.setOnClickListener { onClickListener?.invoke(tasks[adapterPosition]) }
        card.onDeleteClickListener = { onDeleteClickListener?.invoke(tasks[adapterPosition]) }
    }

    override fun onBindViewHolder(holder: ItemViewHolder, position: Int) =
        tasks[position].let { task ->
            holder.card.run {
                title = task.title
                isCompleted = task.isCompleted
            }
        }

    class ItemViewHolder(val card: TaskPointCard) : RecyclerView.ViewHolder(card)
}
