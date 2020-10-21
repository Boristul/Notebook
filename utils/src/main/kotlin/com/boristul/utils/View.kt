package com.boristul.utils

import android.view.View
import android.view.ViewGroup
import androidx.core.view.children

/**
 * Set the number of children [View] in [ViewGroup]
 *
 * If [count] is more than the current number of children [View],
 * the extra ones will be deleted from the end of the list,
 * if less, then the missing ones will be created using [creator].
 *
 * @param count the required number of children [View]
 * @param creator creates new [View], while you can easily return the created [View]
 * ([ViewGroup.addView] will be called automatically), and manually add it to the child
 * will be thrown [IllegalStateException]
 * @param transformation will be called to transform child [View],
 * both those that already existed and those that have just been created using [creator]
 * @since 0.0.0
 */
fun <V : View> ViewGroup.setViewCount(
    count: Int,
    creator: ViewGroup.(index: Int) -> V,
    transformation: (V.(index: Int) -> Unit)? = null
) {
    require(count >= 0) { "Count must be >= 0" }

    // Remove unnecessary View
    val oldCount = childCount
    val remaining =
        if (count <= oldCount) {
            removeViews(count, oldCount - count)
            count
        } else oldCount

    // Create required number of View
    for (position in remaining until count) {
        val view = creator(position)
        when (view.parent) {
            null -> addView(view)
            this -> Unit
            else -> throw IllegalStateException("View must not be attached to any other ViewGroup")
        }
    }

    // Start transformation
    transformation?.let { trans ->
        children.forEachIndexed { index, view ->
            @Suppress("UNCHECKED_CAST")
            (view as V).trans(index)
        }
    }
}
