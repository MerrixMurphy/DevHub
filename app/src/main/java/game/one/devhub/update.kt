package game.one.devhub

import android.content.Context
import android.content.Intent
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.text.TextUtils
import android.view.View
import android.widget.EditText
import android.widget.Toast
import com.google.android.material.floatingactionbutton.FloatingActionButton

class update : AppCompatActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_update)
        val EditText1 = findViewById<View>(R.id.text2) as EditText

        val preferencesnotes = getSharedPreferences("notes", Context.MODE_PRIVATE)
        val editornotes = preferencesnotes.edit()


        val editnotes = getSharedPreferences("whichedit", Context.MODE_PRIVATE)
        val selectnotes = editnotes.getString("which", "0")

        val notes = preferencesnotes.getString("note$selectnotes", "defaultValue")
        EditText1.setText(notes)

        val fab: FloatingActionButton =
            findViewById<View>(R.id.fab2) as FloatingActionButton
        fab.setOnClickListener {
            if (!TextUtils.isEmpty(EditText1.text.toString())) {
                editornotes.putString("note$selectnotes", EditText1.text.toString())
                editornotes.apply()
                Toast.makeText(this@update, "Updated", Toast.LENGTH_SHORT).show()
                EditText1.setText("")

                val intent = Intent(this@update, MainActivity::class.java)
                startActivity(intent)
            }
        }
    }
}