package alfianyusufabdullah.tebakkata

import android.content.Context
import android.support.v7.app.AlertDialog
import android.support.v7.app.AppCompatActivity
import android.widget.Toast

data class Entity(var question: String, var correctAnswer: String)

class Data {
    companion object {
        val getQuizData: List<Entity>
            get() {
                return listOf(
                    Entity("System operasi berbasis mobile yang dikembangkan google", "android"),
                    Entity("Badan Antariksa Amerika", "nasa"),
                    Entity("Ibukota Indonesia", "jakarta"),
                    Entity("Situs berbagi konten video", "youtube")
                )
            }
    }
}

fun Context.showToast(message: String) = Toast.makeText(this, message, Toast.LENGTH_SHORT).show()

fun AppCompatActivity.showAlertDialog(counter: Int, onReset: () -> Unit) {
    AlertDialog.Builder(this).apply {
        setTitle("Quiz Selesai")
        setMessage("Anda berhasil menyelesaikan quiz dengan $counter kali percobaan")
        setCancelable(false)
        setPositiveButton("MAIN LAGI") { _, _ ->
            onReset()
        }
        setNegativeButton("KELUAR") { _, _ ->
            finish()
        }
    }.create().show()
}