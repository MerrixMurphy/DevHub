package game.one.devhub

import android.content.Context
import android.content.Intent
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.TextView
import android.widget.Toast
import androidx.recyclerview.widget.RecyclerView


class NotesList(c: Context) : RecyclerView.Adapter<NotesList.ViewHolder?>() {
    private var appsList: MutableList<Note>

    inner class ViewHolder(itemView: View) : RecyclerView.ViewHolder(itemView),
        View.OnClickListener, View.OnLongClickListener {
        var img: TextView = itemView.findViewById(R.id.content)
        var text: TextView = itemView.findViewById(R.id.title)
        override fun onClick(v: View) {
            val pos: Int = adapterPosition
            val context: Context = v.context
            val launchIntent = (appsList[pos].title.toString())
            val editnotes = context.getSharedPreferences("whichedit", Context.MODE_PRIVATE)
            val selectnotes = editnotes.getString("which", "0")
            val editnoteseditor = editnotes.edit()
                editnoteseditor.putString("which", selectnotes.toString())
                editnoteseditor.apply()
                editnoteseditor.putString("which", launchIntent)
                editnoteseditor.apply()

                val intent: Intent? = Intent(context, update::class.java)
                context.startActivity(intent)
            }

        override fun onLongClick(v: View): Boolean {
            val pos: Int = adapterPosition
            val context: Context = v.context
            val launchIntent = (appsList[pos].content.toString())
            val preferencesnotenumber = context.getSharedPreferences("notenumber", Context.MODE_PRIVATE)
            val editornotenumber = preferencesnotenumber.edit()
            val selectnum = preferencesnotenumber.getString("num", "0")
            val x = selectnum
            var y = Integer.parseInt(x!!)
            val preferencesnotes = context.getSharedPreferences("notes", Context.MODE_PRIVATE)
            val editornotes = preferencesnotes.edit()
            val list:ArrayList<String> = arrayListOf()
            for(s in 0..y) {
                    val notes = preferencesnotes.getString("note$s", "defaultValue")
                    list.add(notes.toString())
                }
            val undolist = context.getSharedPreferences("undo", Context.MODE_PRIVATE)
            val editundo = undolist.edit()
            editundo.putString("un", launchIntent)
            editundo.apply()
            editornotes.clear()
            editornotes.apply()
            list.remove(launchIntent)
            val max = list.size-1
            for (q in 0..max){
                    val yi = list[q]
                    editornotes.putString("note$q", yi)
                    editornotes.apply()
            }
            y--
            editornotenumber.putString("num", y.toString())
            editornotenumber.apply()
            list.clear()
            appsList = ArrayList()
            appsList.clear()
            for(s in 0..y) {
                if (s != 0) {
                    val app = Note()
                    app.title = ("$s")
                    val notes = preferencesnotes.getString("note$s", "defaultValue")
                    app.content = notes
                    appsList.add(app)
                }
            }
            notifyDataSetChanged()
            Toast.makeText(context, "Deleted", Toast.LENGTH_SHORT).show()
            return true
        }

        init {
            itemView.setOnClickListener(this)
            itemView.setOnLongClickListener(this)
        }
    }

    override fun onBindViewHolder(viewHolder: ViewHolder, i: Int) {
        val appPackage = appsList[i].content.toString()
        val appThing = appsList[i].title.toString()
        val img = viewHolder.img
        val text = viewHolder.text
        img.text = appPackage
        text.text = appThing
    }

    override fun getItemCount(): Int {
        return appsList.size
    }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): ViewHolder {
        val inflater = LayoutInflater.from(parent.context)
        val view: View = inflater.inflate(R.layout.list_row, parent, false)
        return ViewHolder(view)
    }

    init {
        val preferencesnotes = c.getSharedPreferences("notes", Context.MODE_PRIVATE)
        val preferencesnotenumber = c.getSharedPreferences("notenumber", Context.MODE_PRIVATE)
        val selectnum = preferencesnotenumber.getString("num", "0")
        val y = Integer.parseInt(selectnum!!)

        appsList = ArrayList()
        for(s in 0..y) {
            if (s != 0) {
                val app = Note()
                app.title = ("$s")
                val notes = preferencesnotes.getString("note$s", "defaultValue")
                app.content = notes
                appsList.add(app)
            }
        }
    }
}