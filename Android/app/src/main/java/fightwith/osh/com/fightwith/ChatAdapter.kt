package fightwith.osh.com.fightwith

import android.support.v7.widget.RecyclerView
import android.view.View
import android.view.ViewGroup
import kotlinx.android.synthetic.main.chat_item_left.view.*
import me.himanshusoni.chatmessageview.ChatMessageView
import org.jetbrains.anko.layoutInflater

class ChatAdapter(val isSenderLeft: Boolean) : RecyclerView.Adapter<ChatAdapter.VH>() {
    val messages = mutableListOf<ChatMessage>()

    override fun getItemCount(): Int = messages.size
    override fun onBindViewHolder(holder: VH, position: Int) {
        val item = messages[position]
        holder.text.text = item.message
        holder.chatMessageView.setArrowPosition(if (item.sender) ChatMessageView.ArrowPosition.RIGHT else ChatMessageView.ArrowPosition.LEFT)
        if (item.sender == isSenderLeft)
            holder.chatMessageView.setBackgroundColors(R.color.colorLeft, R.color.colorLeft)
        else
            holder.chatMessageView.setBackgroundColors(R.color.colorRight, R.color.colorRight)

    }

    override fun getItemViewType(position: Int): Int = if (messages[position].sender) 1 else 0

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): VH {
        if (viewType == 0)
            return VH(parent.context.layoutInflater.inflate(R.layout.chat_item_left, parent, false))
        else
            return VH(parent.context.layoutInflater.inflate(R.layout.chat_item_right, parent, false))

    }

    class VH(view: View) : RecyclerView.ViewHolder(view) {
        val text = view.text
        val chatMessageView = view.chatMessageView
    }
}