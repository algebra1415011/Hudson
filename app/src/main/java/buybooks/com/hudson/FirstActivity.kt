package buybooks.com.hudson

import android.content.Intent
import android.support.v7.app.AppCompatActivity
import android.os.Bundle
import android.view.View
import android.widget.Button
import android.widget.TextView

class FirstActivity : AppCompatActivity() {
    private var btn: Button? = null

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_first)

        btn = findViewById(R.id.btnqr) as Button

        btn!!.setOnClickListener {
            val intent = Intent(this@FirstActivity, ScanActivity::class.java)
            startActivity(intent)
            finish()
        }


    }
}