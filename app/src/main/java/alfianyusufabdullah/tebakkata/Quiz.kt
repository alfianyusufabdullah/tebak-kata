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

                /* SOURCE https://www.antwoorden.org */
                return listOf(
                    Entity("System operasi berbasis mobile yang dikembangkan google", "android"),
                    Entity("Badan Antariksa Amerika", "nasa"),
                    Entity("Ibukota Indonesia", "jakarta"),
                    Entity("Situs berbagi konten video", "youtube"),
                    Entity("Jalan pikiran yang masuk akal", "logika"),
                    Entity("Alat yang berbunyi saat mobil akan dicuri", "alarm"),
                    Entity("Sembilan bahan pokok", "sembako"),
                    Entity("Sayuran hijau yang dimakan popeye", "bayam"),
                    Entity("Tempat menaruh makanan yang terbuat dari kaleng", "rantang"),
                    Entity("Karangan seseorang yang belum diterbitkan", "naskah"),
                    Entity("Segala sesuatu yang diberikan oleh tuhan", "rezeki"),
                    Entity("Nasi panjang yang dibungkus daun pisang", "lontong"),
                    Entity("Yang terasa setelah melihat mendengar sesuatu", "kesan"),
                    Entity("Nama negara di asia", "india"),
                    Entity("Permukaan yang dapat membuat tergelincir", "licin"),
                    Entity("Kain penutup baju yang dipakai saat memasak", "celemek"),
                    Entity("Berasal dari masa lampau tapi tak ketinggalan zaman", "klasik"),
                    Entity("Obat yang membuat orang kehilangan kesadaran", "bius"),
                    Entity("Nama selat di semenanjung malaysia", "malaka"),
                    Entity("Tahun baru masyarakat tionghoa", "imlek"),
                    Entity("Ruang yang dipakai untuk menyiarkan siaran televisi", "studio"),
                    Entity("Perpindahan penduduk ke negara lain untuk menetap", "imigrasi"),
                    Entity("Cerita berdialog dalam pertunjukan teater", "drama"),
                    Entity("Lambang atau huruf yang mengandung makna", "logo"),
                    Entity("Kata untuk memanggil seseorang atau sesuatu", "nama"),
                    Entity("Kumpulan air dalam jumlah sangat besar di daratan", "danau"),
                    Entity("Alat yang mengeluarkan suara, musik maupun siaran", "radio"),
                    Entity("Sistem jalur yang rumit, berliku, banyak jalan buntu", "labirin")
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