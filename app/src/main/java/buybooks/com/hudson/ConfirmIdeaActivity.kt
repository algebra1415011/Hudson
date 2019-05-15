package buybooks.com.hudson

import android.content.Intent
import android.support.v7.app.AppCompatActivity
import android.os.Bundle
import android.support.v7.widget.LinearLayoutManager
import android.util.Log
import android.view.View
import android.widget.LinearLayout
import android.widget.Toast
import buybooks.com.hudson.adapter.TopIdeaAdapter
import buybooks.com.hudson.controller.APIController
import buybooks.com.hudson.model.User
import buybooks.com.hudson.volley.ServiceVolley
import kotlinx.android.synthetic.main.activity_confirm_idea.*
import kotlinx.android.synthetic.main.view_item.view.*
import org.json.JSONArray

class ConfirmIdeaActivity : AppCompatActivity(), View.OnClickListener {

    override fun onClick(p0: View) {

        when (p0.getId()) {
            R.id.username ->{
                Toast.makeText(getApplicationContext(),p0.usertopidea.text,Toast.LENGTH_SHORT).show();

                val intent= Intent(this,WelcomeActivity::class.java)

//        Toast.makeText(this,localuser, Toast.LENGTH_LONG).show()
                intent.putExtra("topidea", p0.usertopidea.text.toString())
                startActivity(intent)

            }

            R.id.userimg ->{
                Toast.makeText(getApplicationContext(),p0.usertopimg.getTag().toString(),Toast.LENGTH_SHORT).show();
                val intent= Intent(this, WelcomeActivity::class.java)
//        Toast.makeText(this,localuser, Toast.LENGTH_LONG).show()
                intent.putExtra("topidea", p0.usertopimg.getTag().toString())

                startActivity(intent)


            }
            else -> {
                Toast.makeText(getApplicationContext(),"",Toast.LENGTH_SHORT).show();
            }
        }

    }
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_confirm_idea)
        var tableID:String =intent.getStringExtra("tableID")
        val linearLayout = LinearLayoutManager(this)
        linearLayout.orientation= LinearLayout.VERTICAL
        confirmidearecycleview.layoutManager=linearLayout
        val service = ServiceVolley()
        val apiController = APIController(service)
        val path3 = "topidea/$tableID"
//        val path = "comments/pfa12"
        val params3 = JSONArray()
        val userData = ArrayList<User>()
        apiController.getJsonArray(path3, params3) { response ->

            for (i in 0..(response!!.length() - 1)) {
                val user = response.getJSONObject(i)

                    userData.add(User(user.getString("userID"),user.getString("name"),user.getBoolean("isLeader"),user.getInt("tableID"),user.getString("idea"),user.getInt("rating"),user.getInt("ideaRateCount")))



                // Your code here
                Log.d("confirmidea response $i", "/post request OK! Response: $user "+userData.size.toString())

            }
            val userAdapter = TopIdeaAdapter(this,userData,this@ConfirmIdeaActivity)
            confirmidearecycleview.adapter = userAdapter
        }



    }
}
