package fightwith.osh.com.fightwith

import java.io.EOFException
import java.io.InputStreamReader
import java.io.OutputStreamWriter
import java.net.Socket


class ChatThread(val index: Int, val sub: Int, val onClose: () -> Unit, val onMessage: (ChatMessage) -> Unit) : Thread() {
    private var socket: Socket? = null
    private lateinit var outputStream: OutputStreamWriter

    override fun run() {
        super.run()
        try {
            val socket = Socket(App.Addr, App.Port)
            this.socket = socket
            outputStream = OutputStreamWriter(socket.outputStream)
            val input = InputStreamReader(socket.getInputStream())
            write("$index,$sub", false)
            val stringBuilder = StringBuilder()
            while (true) {
                val text = input.read()
                if (text == -1) throw EOFException()
                else if (text == 0) {
                    onMessage(ChatMessage(stringBuilder.toString(), System.currentTimeMillis(), false))
                    stringBuilder.setLength(0)
                } else stringBuilder.append(text.toChar())
            }
        } catch(e: Exception) {
            e.printStackTrace()
            close()
            onClose()
        }
    }


    fun write(message: String, async: Boolean = true) {
        if (async) {
            Thread(Runnable {
                outputStream.write(message)
                outputStream.flush()
            }).start()
        } else {
            outputStream.write(message)
            outputStream.flush()
        }
    }

    fun close() {
        socket?.close()
        socket = null
    }
}