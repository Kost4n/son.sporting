package com.bets.sons.porting.plug.room

import android.graphics.Rect
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.TextView
import androidx.recyclerview.widget.RecyclerView
import com.bets.sons.porting.R

class RecAdapter( private val onRecClickListener: OnRecordClickListener
) : RecyclerView.Adapter<RecAdapter.RecViewHolder>() {
    private var itemList = mutableListOf<Rec>()

    inner class RecViewHolder(itemView: View) : RecyclerView.ViewHolder(itemView) {
        private val recordText = itemView.findViewById<TextView>(R.id.score_db)

        fun bind(rec: Rec, position: Int) {
            recordText.text = rec.score.toString()

            itemView.setOnClickListener {
                onRecClickListener.onRecordClick(rec, position)
            }
        }
    }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): RecViewHolder {
        val view = LayoutInflater.from(parent.context)
            .inflate(R.layout.item, parent, false)

        return RecViewHolder(view)
    }

    override fun getItemCount(): Int = itemList.size

    override fun onBindViewHolder(holder: RecViewHolder, position: Int) =
        holder.bind(itemList[position], position)

    fun update(items: MutableList<Rec>) {
        itemList = items
        notifyDataSetChanged()
    }
    interface OnRecordClickListener {
        fun onRecordClick(record: Rec, position: Int)
    }
}

class RecycleItemDecoration(
    private val spaceHeight: Int
): RecyclerView.ItemDecoration() {

    override fun getItemOffsets(outRect: Rect, view: View,
                                parent: RecyclerView, state: RecyclerView.State) {
        outRect.bottom = spaceHeight
    }
}