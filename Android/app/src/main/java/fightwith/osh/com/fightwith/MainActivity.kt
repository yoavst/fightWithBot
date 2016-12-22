package fightwith.osh.com.fightwith

import android.app.ProgressDialog
import android.support.v7.app.AppCompatActivity
import android.os.Bundle
import android.app.AlertDialog
import android.content.Intent
import android.support.v7.widget.GridLayoutManager
import kotlinx.android.synthetic.main.activity_main.*
import kotlinx.android.synthetic.main.dialog_layout.view.*
import org.jetbrains.anko.intentFor
import org.jetbrains.anko.onClick

class MainActivity : AppCompatActivity() {
    private var dialog: AlertDialog? = null

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)

        recyclerView.layoutManager = GridLayoutManager(this, 2)
        recyclerView.setHasFixedSize(true)
        recyclerView.adapter = SubjectsAdapter(this) {
            showDialog(it)
        }
    }

    override fun onPause() {
        super.onPause()
        dialog?.dismiss()
        dialog = null
    }

    private fun showDialog(subject: Subject) {
        val view = layoutInflater.inflate(R.layout.dialog_layout, null, false)
        view.image.setImageDrawable(subject.image)
        view.categoryName.text = subject.title
        view.leftSide.text = subject.left
        view.rightSide.text = subject.right
        view.description.text = subject.description
        dialog = AlertDialog.Builder(this)
                .setView(view)
                .show()

        view.leftSide.onClick {
            dialog?.dismiss()
            dialog = null
            createLoadingDialog()
            connectServer(subject, subject.left)
        }
        view.rightSide.onClick {
            dialog?.dismiss()
            dialog = null
            createLoadingDialog()
            connectServer(subject, subject.right)
        }
    }

    fun createLoadingDialog() {
        dialog = ProgressDialog.show(this, "", getString(R.string.find_random), true)
    }

    fun connectServer(subject: Subject, selection: String) {
        startActivity(intentFor<ChatActivity>(Intent.EXTRA_TEXT to App.instance.subjects.indexOfFirst { it.title == subject.title }, Intent.EXTRA_ASSIST_CONTEXT to selection))
}


}
