package game.one.devhub

import android.content.Intent
import android.os.Bundle
import androidx.appcompat.app.AppCompatActivity
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView

class AppList : AppCompatActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_app_list)

        val recyclerView = findViewById<RecyclerView>(R.id.list)
        val radapter = RAdapter(this)
        recyclerView.adapter = radapter
        recyclerView.layoutManager = LinearLayoutManager(this)
    }

    override fun onBackPressed() {
        super.onBackPressed()
        val intent = Intent(this@AppList, MainActivity::class.java)
        startActivity(intent)
    }
}