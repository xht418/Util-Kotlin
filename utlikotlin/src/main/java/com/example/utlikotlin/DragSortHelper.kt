package com.example.utlikotlin

import androidx.recyclerview.widget.ItemTouchHelper
import androidx.recyclerview.widget.RecyclerView

class DragSortHelper(items: List<ListItem>, dragDirection: Int, dropAction: (List<ListItem>) -> Unit) {
    companion object {
        const val HORIZONTAL = ItemTouchHelper.LEFT or ItemTouchHelper.RIGHT
        const val VERTICAL = ItemTouchHelper.UP or ItemTouchHelper.DOWN
        const val ALL = ItemTouchHelper.LEFT or ItemTouchHelper.RIGHT or ItemTouchHelper.UP or ItemTouchHelper.DOWN
    }

    private val itemTouchHelper: ItemTouchHelper

    init {
        itemTouchHelper = ItemTouchHelper(object : ItemTouchHelper.SimpleCallback(dragDirection, 0) {
            val previousPositions = mutableListOf<Int>()

            override fun onMove(recyclerView: RecyclerView, previousViewHolder: RecyclerView.ViewHolder, targetViewHolder: RecyclerView.ViewHolder): Boolean {
                val previousPosition = previousViewHolder.adapterPosition
                val targetPosition = targetViewHolder.adapterPosition

                previousPositions.add(previousPosition)

                recyclerView.adapter!!.notifyItemMoved(previousPosition, targetPosition)

                return false
            }

            override fun onSwiped(viewHolder: RecyclerView.ViewHolder, direction: Int) {
            }

            override fun clearView(recyclerView: RecyclerView, targetViewHolder: RecyclerView.ViewHolder) {
                super.clearView(recyclerView, targetViewHolder)

                if (previousPositions.isNotEmpty()) {
                    val affectedItems = getAffectedItems(items, previousPositions[0], targetViewHolder.adapterPosition)

                    dropAction(affectedItems)

                    previousPositions.clear()
                }
            }

            override fun isLongPressDragEnabled() = false
        })
    }

    private fun getAffectedItems(items: List<ListItem>, draggedPosition: Int, targetPosition: Int): List<ListItem> {
        val affectedItems = getItemsByRange(items, draggedPosition, targetPosition)
        val isDragDown = targetPosition > draggedPosition

        if (isDragDown) {
            affectedItems[0].sortOrder = affectedItems.last().sortOrder

            affectedItems.forEach {
                if (it != affectedItems.first()) {
                    it.sortOrder--
                }
            }
        } else {
            affectedItems.last().sortOrder = affectedItems[0].sortOrder

            affectedItems.forEach {
                if (it != affectedItems.last()) {
                    it.sortOrder++
                }
            }
        }

        return affectedItems
    }

    private fun getItemsByRange(items: List<ListItem>, draggedPosition: Int, targetPosition: Int): List<ListItem> {
        val startPosition = if (targetPosition > draggedPosition) draggedPosition else targetPosition
        val endPosition = if (targetPosition > draggedPosition) targetPosition else draggedPosition

        return items.range(startPosition, endPosition)
    }

    fun startDrag(viewHolder: RecyclerView.ViewHolder) {         //this view is the drag handle button, not the item view
        itemTouchHelper.startDrag(viewHolder)
    }

    fun attachToRecyclerView(recyclerView: RecyclerView) {
        itemTouchHelper.attachToRecyclerView(recyclerView)
    }
}