package com.gavinsappcreations.upcominggames.ui.sort

import android.util.SparseBooleanArray
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.CheckBox
import androidx.core.util.set
import androidx.recyclerview.widget.RecyclerView
import com.gavinsappcreations.upcominggames.R
import com.gavinsappcreations.upcominggames.domain.Game
import com.gavinsappcreations.upcominggames.domain.Platform
import com.gavinsappcreations.upcominggames.domain.SortOptions
import com.gavinsappcreations.upcominggames.utilities.PropertyAwareMutableLiveData
import com.gavinsappcreations.upcominggames.utilities.allPlatforms
import com.gavinsappcreations.upcominggames.utilities.notifyObserver

class PlatformAdapter (private val unsavedSortOptions: PropertyAwareMutableLiveData<SortOptions>) :
    RecyclerView.Adapter<PlatformAdapter.ViewHolder>() {

    val data = allPlatforms

    override fun getItemCount() = data.size

    override fun onBindViewHolder(holder: ViewHolder, position: Int) {
        val item = data[position]

        (holder.itemView as CheckBox).setOnCheckedChangeListener{ _, isChecked ->
            if (isChecked) {
                unsavedSortOptions.value!!.platformIndices.add(position)
                unsavedSortOptions.notifyObserver()
            } else {
                unsavedSortOptions.value!!.platformIndices.remove(position)
                unsavedSortOptions.notifyObserver()
            }
        }

        holder.bind(item)
    }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): ViewHolder {
        return ViewHolder.from(parent)
    }

    class ViewHolder(itemView: View) : RecyclerView.ViewHolder(itemView){
        private val checkBox: CheckBox = itemView.findViewById(R.id.platform_checkBox)

        fun bind(item: Platform) {
            checkBox.text = item.fullName
        }

        companion object {
            fun from(parent: ViewGroup): ViewHolder {
                val layoutInflater = LayoutInflater.from(parent.context)
                val view = layoutInflater
                    .inflate(R.layout.platform_list_item, parent, false)
                return ViewHolder(view)
            }
        }
    }

    class OnCheckedChangeListener (val checkedChangeListener: (platformIndices: MutableSet<Int>) -> Unit) {
        fun onCheck(platformIndices: MutableSet<Int>) = checkedChangeListener(platformIndices)
    }
}