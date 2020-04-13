package com.wearetriple.tripleonboarding.ui.faq

import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import com.wearetriple.tripleonboarding.R
import com.wearetriple.tripleonboarding.model.Faq
import com.wearetriple.tripleonboarding.ui.helper.AbstractAdapter
import kotlinx.android.synthetic.main.item_faq.view.*

/**
 * Used to prepare recyclerview's items.
 */
class FaqAdapter(
    override var items: ArrayList<Faq>
) : AbstractAdapter<Faq>(items) {

    /**
     * Prepares the view before passing it to the RecyclerView.
     */
    inner class ViewHolder(itemView: View) : AbstractAdapter<Faq>.ViewHolder(itemView) {

        override fun bind(item: Faq) {
            itemView.tvQuestion.text = item.question
            itemView.tvAnswer.text = item.answer
        }
    }

    /**
     * Creates and returns a ViewHolder object, inflating a standard layout.
     */
    override fun onCreateViewHolder(
        parent: ViewGroup,
        viewType: Int
    ): AbstractAdapter<Faq>.ViewHolder {
        return ViewHolder(
            LayoutInflater.from(parent.context).inflate(R.layout.item_faq, parent, false)
        )
    }

}