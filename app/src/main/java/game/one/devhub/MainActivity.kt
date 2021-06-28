package game.one.devhub

import android.annotation.SuppressLint
import android.content.Context
import android.content.Intent
import android.os.Bundle
import android.view.View
import android.widget.Toast
import androidx.appcompat.app.AppCompatActivity
import androidx.recyclerview.widget.GridLayoutManager
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView


class MainActivity : AppCompatActivity() {
    @SuppressLint("ResourceType")
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.main)

        val preferencesnotes = getSharedPreferences("notes", Context.MODE_PRIVATE)
        val preferencesnotenumber = getSharedPreferences("notenumber", Context.MODE_PRIVATE)
        val editornotes = preferencesnotes.edit()
        val editornotenumber = preferencesnotenumber.edit()
        val selectnum = preferencesnotenumber.getString("num", "0")
        val notes = preferencesnotes.getString("note0", "defaultValue")
        editornotenumber.putString("num", selectnum.toString())
        editornotenumber.apply()
        editornotes.putString("note0", notes.toString())
        editornotes.apply()

        val recyclerView = findViewById<RecyclerView>(R.id.listofnotes)
        val radapter = NotesList(this)
        recyclerView.adapter = radapter
        recyclerView.layoutManager = LinearLayoutManager(this)

        val recyclerView2 = findViewById<RecyclerView>(R.id.devapplist)
        val radapter2 = AppAdapter(this)
        recyclerView2.adapter = radapter2
        recyclerView2.layoutManager = GridLayoutManager(this, 4)
    }

    fun openbutton(view: View) {
        val intent = Intent(this@MainActivity, Notes::class.java)
        startActivity(intent)
    }

    fun otherbutton (view: View) {
        val intent = Intent(this@MainActivity, AppList::class.java)
        startActivity(intent)
    }

    fun undo (view: View) {
        val undolist = getSharedPreferences("undo", Context.MODE_PRIVATE)
        val editundo = undolist.getString("un", "defaultValue")
        if (editundo != null) {
            if (editundo != "defaultValue" && editundo.isNotEmpty()) {
                val preferencesnotes = getSharedPreferences("notes", Context.MODE_PRIVATE)
                val preferencesnotenumber = getSharedPreferences("notenumber", Context.MODE_PRIVATE)
                val editornotes = preferencesnotes.edit()
                val editornotenumber = preferencesnotenumber.edit()
                val selectnum = preferencesnotenumber.getString("num", "0")
                var y = Integer.parseInt(selectnum!!)
                y++
                val z = y.toString()
                editornotenumber.putString("num", z)
                editornotenumber.apply()
                editornotes.putString("note$z", editundo)
                editornotes.apply()
                recreate()

                Toast.makeText(this@MainActivity, "Undo", Toast.LENGTH_SHORT).show()
            }
        }
    }
}