package com.odinn.application.screens.common

import android.support.v7.util.DiffUtil

class SimpleCallback<T>(private val oldItems: List<T>,
                        private val newItems: List<T>,
                        private val itemIdGetter: (T) -> Any) : DiffUtil.Callback() {
    // сравниваем ID
    override fun areItemsTheSame(oldItemPosition: Int, newItemPosition: Int): Boolean =
        itemIdGetter(oldItems[oldItemPosition]) == itemIdGetter(newItems[newItemPosition])

    //сравниваем содержимое
    override fun areContentsTheSame(oldItemPosition: Int, newItemPosition: Int): Boolean =
            oldItems[oldItemPosition] == newItems[newItemPosition]

    override fun getOldListSize(): Int = oldItems.size

    override fun getNewListSize(): Int = newItems.size
}