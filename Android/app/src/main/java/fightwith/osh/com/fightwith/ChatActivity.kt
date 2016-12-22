package fightwith.osh.com.fightwith

import android.content.Intent
import android.os.Bundle
import android.support.v7.app.AlertDialog
import android.support.v7.app.AppCompatActivity
import android.support.v7.widget.LinearLayoutManager
import android.view.View
import kotlinx.android.synthetic.main.activity_chat.*
import org.jetbrains.anko.onClick
import org.jetbrains.anko.toast

class ChatActivity : AppCompatActivity() {
    lateinit var chatAdapter: ChatAdapter
    lateinit var chatThread: ChatThread
    var isWaiting = false
    var lastTime = 0L

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_chat)
        val index = intent.extras.getInt(Intent.EXTRA_TEXT)
        val subject = App.instance.subjects[index]
        val selection = intent.extras.getString(Intent.EXTRA_ASSIST_CONTEXT)

        image.setImageDrawable(subject.image)
        text.text = subject.title

        recyclerView.layoutManager = LinearLayoutManager(this)
        recyclerView.setHasFixedSize(true)
        chatAdapter = ChatAdapter(selection == subject.left)

        recyclerView.adapter = chatAdapter

        sendMessage.onClick {
            val messageText = message.text.toString()
            if (messageText.isNotBlank()) {
                sendMessage(messageText)
                addMessage(ChatMessage(messageText, System.currentTimeMillis(), sender = true))
            }
            message.setText("")
        }

        exitButton.onClick {
            finish()
        }

        chatThread = ChatThread(index, if (selection == subject.left) 1 else 0, onClose = {
            runOnUiThread {
                messageLayout.visibility = View.GONE
                exitButton.visibility = View.VISIBLE
                statusText.text = getString(R.string.chat_has_been_closed)
            }
        }) {
            runOnUiThread {
                addMessage(it)
            }
        }

        chatThread.start()
    }

    override fun onDestroy() {
         super.onDestroy()
        chatThread.close()
    }

    override fun onBackPressed() {
        if (isWaiting && System.currentTimeMillis() - lastTime < 2000) {
            super.onBackPressed()
        } else {
            isWaiting = true
            lastTime = System.currentTimeMillis()
            toast(getString(R.string.back_again_to_exit))
        }
    }

    fun addMessage(chatMessage: ChatMessage) {
        chatAdapter.messages += chatMessage
        chatAdapter.notifyItemInserted(chatAdapter.messages.size - 1)
        recyclerView.smoothScrollToPosition(chatAdapter.itemCount)
    }

    fun sendMessage(text: String) {
        chatThread.write(text)
    }


}
