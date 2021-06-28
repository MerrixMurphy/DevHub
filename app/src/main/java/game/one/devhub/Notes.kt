package game.one.devhub

import android.content.Context
import android.content.Intent
import android.os.Bundle
import android.text.TextUtils
import android.view.View
import android.widget.EditText
import android.widget.Toast
import androidx.appcompat.app.AppCompatActivity
import com.google.android.material.floatingactionbutton.FloatingActionButton


class Notes : AppCompatActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_notes)

        val EditText1 = findViewById<View>(R.id.text) as EditText

        val preferencesnotes = getSharedPreferences("notes", Context.MODE_PRIVATE)
        val preferencesnotenumber = getSharedPreferences("notenumber", Context.MODE_PRIVATE)
        val editornotes = preferencesnotes.edit()
        val editornotenumber = preferencesnotenumber.edit()

        val fab: FloatingActionButton =
            findViewById<View>(R.id.fab) as FloatingActionButton
        fab.setOnClickListener {
            if (!TextUtils.isEmpty(EditText1.text.toString())) {
                val selectnum = preferencesnotenumber.getString("num", "0")
                val notes = preferencesnotes.getString("note$selectnum", "defaultValue")
                editornotenumber.putString("num", selectnum.toString())
                editornotenumber.apply()
                editornotes.putString("note$selectnum", notes.toString())
                editornotes.apply()
                var y = Integer.parseInt(selectnum!!)
                y++
                val z = y.toString()
                editornotenumber.putString("num", z)
                editornotenumber.apply()
                editornotes.putString("note$z", EditText1.text.toString())
                editornotes.apply()
                Toast.makeText(this@Notes, "Saved", Toast.LENGTH_SHORT).show()
                EditText1.setText("")

                val intent = Intent(this@Notes, MainActivity::class.java)
                startActivity(intent)
            }
        }
    }
}