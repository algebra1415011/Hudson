package buybooks.com.hudson.user

import android.content.Intent
import android.support.v7.app.AppCompatActivity
import android.os.Bundle
import android.widget.EditText
import android.widget.Toast
import buybooks.com.hudson.R
import buybooks.com.hudson.ThanksActivity
import buybooks.com.hudson.connection.DatabaseHelper
import buybooks.com.hudson.controller.APIController
import buybooks.com.hudson.volley.ServiceVolley
import kotlinx.android.synthetic.main.activity_user_idea.*
import kotlinx.android.synthetic.main.commentlayout.*
import org.json.JSONObject

class UserIdeaActivity : AppCompatActivity() {

    private var dbHandler: DatabaseHelper? = null

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_user_idea)
        submitideabt.setOnClickListener{
            val inputIdea = findViewById(R.id.idea) as EditText
            val service = ServiceVolley()
            dbHandler = DatabaseHelper(this)
            val userID = dbHandler!!.getUsers()
            val apiController = APIController(service)
            val path1 = "user/${userID}"
            val params1 = JSONObject()
            apiController.getJsonObject(path1, params1) {response1 ->
                val path2 = "user/${response1?.getString("userID")}"
                val params2 = JSONObject()
                params2.put("userID","${response1?.getString("userID")}")
                params2.put("name","${response1?.getString("name")}")
                params2.put("isLeader","${response1?.getBoolean("isLeader")}")
                params2.put("tableID","${response1?.getInt("tableID")}")
                params2.put("idea","${inputIdea.text}")
                params2.put("rating","${response1!!.getInt("rating")}")
                params2.put("ideaRateCount","${response1!!.getInt("ideaRateCount")}")
                apiController.put(path2, params2) { response2 ->
                    if(response2 != null){
                        Toast.makeText(this,"Rating submitted to Database", Toast.LENGTH_LONG).show()
                    }
                }
            }
            val intent= Intent(this, ThanksActivity::class.java)
            intent.putExtra("activity","submitact")
            startActivity(intent)
        }
    }
}
