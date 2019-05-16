package buybooks.com.hudson

import android.content.Intent
import android.support.v7.app.AppCompatActivity
import android.os.Bundle
import android.support.v7.widget.GridLayoutManager
import android.util.Log
import android.view.View
import android.widget.Toast
import buybooks.com.hudson.adapter.UserAdapter
import buybooks.com.hudson.connection.DatabaseHelper
import buybooks.com.hudson.controller.APIController
import buybooks.com.hudson.model.User
import buybooks.com.hudson.user.UserCommentActivity
import buybooks.com.hudson.user.UserIdeaActivity
import buybooks.com.hudson.volley.ServiceVolley
import com.google.gson.Gson
import kotlinx.android.synthetic.main.commentlayout.*
import kotlinx.android.synthetic.main.gdmodel_layout.view.*
import org.json.JSONArray
import org.json.JSONObject

class WelcomeActivity : AppCompatActivity(),View.OnClickListener {

//    tableID

    private var dbHandler: DatabaseHelper? = null
    override fun onClick(p0: View) {

        when (p0.getId()) {
            R.id.username ->{
                var color = dbHandler!!.getcolor()
                if(!color.equals("yellow"))
                {
                    Toast.makeText(getApplicationContext(),"User has not submitted idea",Toast.LENGTH_SHORT).show()
                }
                else
                {
                    val intent= Intent(this,UserCommentActivity::class.java)

//                 Toast.makeText(this,localuser, Toast.LENGTH_LONG).show()
                    intent.putExtra("usercommentimg", p0.username.text.toString())
                    startActivity(intent)

                }




            }

            R.id.userimg ->{
                var color = dbHandler!!.getcolor()
                if(!color.equals("yellow"))
                {
                    Toast.makeText(getApplicationContext(),"User has not submitted idea",Toast.LENGTH_SHORT).show()
                }
                else {

                    val intent = Intent(this, UserCommentActivity::class.java)
//        Toast.makeText(this,localuser, Toast.LENGTH_LONG).show()
                    intent.putExtra("usercommentimg", p0.userimg.getTag().toString())

                    startActivity(intent)
                }


            }
            else -> {
                Toast.makeText(getApplicationContext(),"",Toast.LENGTH_SHORT).show();
            }
        }

    }

    override fun onRestart() {
        super.onRestart()
        finish()
        startActivity(getIntent())
    }

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.commentlayout)
//        userrecycleviewId
        dbHandler = DatabaseHelper(this)




        val service = ServiceVolley()

        val apiController = APIController(service)
        var localuser = dbHandler!!.getUsers()
        val path = "user/${localuser}"
//        val path = "comments/pfa12"
        val params = JSONObject()
        apiController.getJsonObject(path, params) { response ->
            //            // Parse the result

////            progressDialog = ProgressDialog(this)
////            progressDialog.show()
////            val request = StringRequest(HttpConstants.BASE_URL, Listener<String> {
////                response ->
////                var movieModel = Gson().fromJson(response, MovieModel::class.java)
////                var movieList: List<MovieModel.ResultsEntity> = movieModel.results!!
////// for (item: MovieModel.ResultsEntity in movieList) {
////// println(item.original_title)
////// }
////                setAdapter(movieList);
////                progressDialog.dismiss()
//
//
            usernamelocal.setText(response!!.getString("name"))
            localtableno.setText("TABLE NO: "+response.getInt("tableID").toString())
            userimgcomment
            val id: Int= this.getResources().getIdentifier(response.getString("userID"), "drawable", this.getPackageName())
            userimgcomment.setImageResource(id)

            if(response.getBoolean("isLeader"))
                checkandview.visibility = View.VISIBLE
            if(!response.getString("idea").equals("null"))
            {
                submitidea.setText("Your Idea")
            }
//            tableID=response.getInt("tableID")

            if (response != null) {
                Log.d("finalobject response", "/post request OK! Response: $response"+response.getString("name"))
            }
        }

        val gridLayoutManager = GridLayoutManager(this,2)
        gridLayoutManager.orientation=GridLayoutManager.VERTICAL
        userrecycleviewId.layoutManager=gridLayoutManager

        //for users for table

        var tabletest= dbHandler!!.gettableId()
        val path2 = "users/$tabletest"
        Log.d("tableid", "/post request OK! Response: ${tabletest}")
        val params2 = JSONArray()
        val userData = ArrayList<User>()
        apiController.getJsonArray(path2, params2) { response ->
            // Parse the result

//            progressDialog = ProgressDialog(this)
//            progressDialog.show()
//            val request = StringRequest(HttpConstants.BASE_URL, Listener<String> {
//                response ->
//                var movieModel = Gson().fromJson(response, MovieModel::class.java)
//                var movieList: List<MovieModel.ResultsEntity> = movieModel.results!!
//// for (item: MovieModel.ResultsEntity in movieList) {
//// println(item.original_title)
//// }
//                setAdapter(movieList);
//                progressDialog.dismiss()

            Toast.makeText(this,localuser, Toast.LENGTH_LONG).show()

            for (i in 0..(response!!.length() - 1)) {
                val user = response.getJSONObject(i)

                if(!user.getString("userID").equals(localuser))
                {
                    userData.add(User(user.getString("userID"),user.getString("name"),user.getBoolean("isLeader"),user.getInt("tableID"),user.getString("idea"),user.getInt("rating"),user.getInt("ideaRateCount")))
                }
                // Your code here
                Log.d("finaltable response $i", "/post request OK! Response: $user "+userData.size.toString()+" "+user.getBoolean("isLeader"))
            }
            Log.d("usersize", userData.size.toString())
            val userAdapter = UserAdapter(this,userData,this@WelcomeActivity)
            userrecycleviewId.adapter = userAdapter
            Log.d("finaltable response", "/post request OK! Response: $response")
        }
        val path3 = "check/${localuser}"
//        val path = "comments/pfa12"
        val params3 = JSONObject()
        apiController.getJsonObject(path3, params3) { response ->

            if (response != null) {
                if(response.getBoolean("result"))
                    checkandview.isEnabled=true
                checkandview.visibility=View.VISIBLE
            }
            if (response != null) {
                Log.d("topidea response", "/post request OK! Response: $response"+response.getString("result"))
            }
        }
        topidea.setOnClickListener{
            val intent= Intent(this, TopideaActivity::class.java)
//        Toast.makeText(this,localuser, Toast.LENGTH_LONG).show()
            startActivity(intent)
        }
        checkandview.setOnClickListener{
            val intent= Intent(this, ConfirmIdeaActivity::class.java)
//        Toast.makeText(this,localuser, Toast.LENGTH_LONG).show()
            intent.putExtra("tableID", tabletest.toString())
            startActivity(intent)
        }
        submitidea.setOnClickListener{
            val intent= Intent(this,UserIdeaActivity::class.java)
            intent.putExtra("tableID", tabletest.toString())
            startActivity(intent)
        }
    }
}
