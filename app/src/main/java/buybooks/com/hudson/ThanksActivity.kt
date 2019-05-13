package buybooks.com.hudson

import android.content.Intent
import android.support.v7.app.AppCompatActivity
import android.os.Bundle
import kotlinx.android.synthetic.main.activity_thanks.*
import kotlinx.android.synthetic.main.activity_user_comment.*

class ThanksActivity : AppCompatActivity() {

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_thanks)
        var act:String=intent.getStringExtra("activity")
        if(act.equals("ratingact"))
            thankstv.setText("Thanks for providing rating.")
        else if(act.equals("submitact"))
            thankstv.setText("Thanks for submitting idea.")
        backbt.setOnClickListener{
            backbt.setOnClickListener{


                val intent= Intent(this,WelcomeActivity::class.java)
                startActivity(intent)
            }
        }

    }
}
