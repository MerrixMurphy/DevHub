package game.one.devhub

import android.content.ComponentName
import android.content.Context
import android.content.Intent
import android.content.pm.PackageManager
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.ImageView
import android.widget.TextView
import android.widget.Toast
import androidx.recyclerview.widget.RecyclerView

class AppAdapter(c: Context) : RecyclerView.Adapter<AppAdapter.ViewHolder?>() {
    private var appsList: MutableList<Item>

    inner class ViewHolder(itemView: View) : RecyclerView.ViewHolder(itemView),
        View.OnClickListener, View.OnLongClickListener {
        var textView: TextView = itemView.findViewById(R.id.name)
        var img: ImageView = itemView.findViewById(R.id.icon) as ImageView
        override fun onClick(v: View) {
            val pos: Int = adapterPosition
            val context: Context = v.context

            val launchIntent: Intent? = context.packageManager
                .getLaunchIntentForPackage(appsList[pos].packageName.toString())
            context.startActivity(launchIntent)
            Toast.makeText(v.context, appsList[pos].label.toString(), Toast.LENGTH_LONG)
                .show()
        }
        override fun onLongClick(v: View): Boolean {
            val pos: Int = adapterPosition
            val context: Context = v.context
            val label = (appsList[pos].label.toString())
            val apptextlist = context.getSharedPreferences("apptextlist", Context.MODE_PRIVATE)
            val pm: PackageManager = context.packageManager
            val edit = apptextlist.edit()
            edit.remove("apptext$label").apply()
            appsList = ArrayList()
            val i = Intent(Intent.ACTION_MAIN, null)
            i.addCategory(Intent.CATEGORY_LAUNCHER)
            val allApps = pm.queryIntentActivities(i, 0)
            appsList.clear()
            for (ri in allApps) {
                val app = Item()
                app.label = ri.loadLabel(pm)
                app.packageName = ri.activityInfo.packageName
                val ko = app.label
                app.icon = ri.activityInfo.loadIcon(pm)
                val notes = apptextlist.getString("apptext$ko", "defaultValue")
                if (notes == ko) {
                    appsList.add(app)
                }
            }
            notifyDataSetChanged()
            Toast.makeText(v.context, "removed", Toast.LENGTH_SHORT)
                .show()
            return true
        }

        init {
                itemView.setOnClickListener(this)
                itemView.setOnLongClickListener(this)
            }
    }

    override fun onBindViewHolder(viewHolder: ViewHolder, i: Int) {
        val appLabel = appsList[i].label.toString()
        val appIcon = appsList[i].icon
        val textView = viewHolder.textView
        textView.text = appLabel
        val imageView: ImageView = viewHolder.img
        imageView.setImageDrawable(appIcon)
    }

    override fun getItemCount(): Int {
        return appsList.size
    }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): ViewHolder {
        val inflater = LayoutInflater.from(parent.context)
        val view: View = inflater.inflate(R.layout.item, parent, false)
        return ViewHolder(view)
    }

    init {
        val pm: PackageManager = c.packageManager
        appsList = ArrayList()
        val i = Intent(Intent.ACTION_MAIN, null)
        i.addCategory(Intent.CATEGORY_LAUNCHER)
        val allApps = pm.queryIntentActivities(i, 0)
        val apptextlist = c.getSharedPreferences("apptextlist", Context.MODE_PRIVATE)
        for (ri in allApps) {
            val app = Item()
            app.label = ri.loadLabel(pm)
            app.packageName = ri.activityInfo.packageName
            val ko = app.label
            app.icon = ri.activityInfo.loadIcon(pm)
            val notes = apptextlist.getString("apptext$ko", "defaultValue")
            if ( notes == ko) {
                appsList.add(app)
            }
        }
    }
}