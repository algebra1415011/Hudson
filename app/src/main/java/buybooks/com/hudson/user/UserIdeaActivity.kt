package buybooks.com.hudson.user

import android.content.Intent
import android.support.v7.app.AppCompatActivity
import android.os.Bundle
import buybooks.com.hudson.R
import buybooks.com.hudson.ThanksActivity
import kotlinx.android.synthetic.main.activity_user_idea.*

class UserIdeaActivity : AppCompatActivity() {

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_user_idea)
        submitideabt.setOnClickListener{
            val intent= Intent(this, ThanksActivity::class.java)
            intent.putExtra("activity","submitact")
            startActivity(intent)
        }
    }
}
