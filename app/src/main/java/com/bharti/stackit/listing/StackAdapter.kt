package com.bharti.stackit.listing

import android.content.Context
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.TextView
import androidx.recyclerview.widget.RecyclerView
import com.bharti.stackit.R
import com.bharti.stackit.retrofit.model.StackItem

class StackAdapter(
    private val list: MutableList<StackItem>,
    private val onItemClick: (url: String) -> Unit
) :
    RecyclerView.Adapter<RecyclerView.ViewHolder>() {
    private lateinit var context: Context

    override fun onAttachedToRecyclerView(recyclerView: RecyclerView) {
        super.onAttachedToRecyclerView(recyclerView)
        context = recyclerView.context
    }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): RecyclerView.ViewHolder {
        val layoutInflater = LayoutInflater.from(parent.context)
        return StackItemViewHolder(layoutInflater.inflate(R.layout.stack_item_view, parent, false))
    }

    override fun onBindViewHolder(holder: RecyclerView.ViewHolder, position: Int) {
        if (holder is StackItemViewHolder) {
            holder.bind(list[position], onItemClick)
        }
    }

    override fun getItemCount(): Int = list.size

    fun updateList(data: List<StackItem>) {
        list.clear()
        list.addAll(data)
        notifyDataSetChanged()
    }

    /*   ---------------------------------View Holders---------------------------- */

    inner class StackItemViewHolder(private val view: View) : RecyclerView.ViewHolder(view) {
        private val title: TextView get() = view.findViewById(R.id.title)
        private val askedBy: TextView get() = view.findViewById(R.id.askedBy)
        private val viewed: TextView get() = view.findViewById(R.id.viewed)
        private val answerCount: TextView get() = view.findViewById(R.id.answerCount)

        fun bind(item: StackItem, onItemClick: (url: String) -> Unit) {
            title.text = item.title
            askedBy.text = context.getString(R.string.asked_by, item.owner.displayName)
            viewed.text = item.viewCount.toString()
            answerCount.text = context.getString(R.string.answer_count, item.answerCount)
            view.rootView.setOnClickListener { onItemClick.invoke(item.link) }
        }
    }
}