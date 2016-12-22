package fightwith.osh.com.fightwith

import android.content.Context
import android.support.v7.widget.RecyclerView
import android.support.v7.widget.RecyclerView.ViewHolder
import android.view.View
import android.view.ViewGroup
import kotlinx.android.synthetic.main.grid_item.view.*
import org.jetbrains.anko.layoutInflater
import org.jetbrains.anko.onClick

class SubjectsAdapter(context: Context, val listener: (Subject) -> Unit) : RecyclerView.Adapter<SubjectsAdapter.VH>() {
    private val items = (context.applicationContext as App).subjects
    override fun getItemCount(): Int = items.size

    override fun onBindViewHolder(holder: VH, position: Int) {
        val item = items[position]
        holder.image.setImageDrawable(item.image)
        holder.categoryName.text = item.title

        holder.itemView.onClick {
            listener(item)
        }
    }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): VH {
        return VH(parent.context.layoutInflater.inflate(R.layout.grid_item, parent, false))
    }

    class VH(view: View) : ViewHolder(view) {
        val image = view.image
        val categoryName = view.categoryName
    }
}