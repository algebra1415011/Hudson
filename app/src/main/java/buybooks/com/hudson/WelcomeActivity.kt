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

    val userID: String="pfa12"
    val tableID: Int=1
    private var dbHandler: DatabaseHelper? = null
    override fun onClick(p0: View) {



        when (p0.getId()) {
            R.id.username ->{
                Toast.makeText(getApplicationContext(),p0.username.text,Toast.LENGTH_SHORT).show();
                val intent= Intent(this,UserCommentActivity::class.java)
                intent.putExtra("usercommentimg", p0.username.text.toString())
                startActivity(intent)

            }

            R.id.userimg ->{
                Toast.makeText(getApplicationContext(),p0.userimg.getTag().toString(),Toast.LENGTH_SHORT).show();
                val intent= Intent(this, UserCommentActivity::class.java)
                intent.putExtra("usercommentimg", p0.userimg.getTag().toString())
                startActivity(intent)


            }
            else -> {
                Toast.makeText(getApplicationContext(),"",Toast.LENGTH_SHORT).show();
            }
        }

    }

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.commentlayout)
//        userrecycleviewId
        dbHandler = DatabaseHelper(this)
        val gridLayoutManager = GridLayoutManager(this,2)
        gridLayoutManager.orientation=GridLayoutManager.VERTICAL
        userrecycleviewId.layoutManager=gridLayoutManager

        val userData= listOf<User>(User("1","navneet",true,23,"AI is the best","5","1_1"),
                User("2","shunya1",false,24,"Serverless is the best","5","1_2"),
                User("3","dhaval",false,24,"Serverless is the best","5","1_2"),
                User("4","nishi",false,24,"Serverless is the best","5","1_2"),
                User("5","vikrant",false,24,"Serverless is the best","5","1_2"),
                User("6","bhawna",false,24,"Serverless is the best","5","1_2"),
                User("7","anjali",false,24,"Serverless is the best","5","1_2"),
                User("8","rajen",false,24,"Serverless is the best","5","1_2"),
                User("9","venky",false,24,"Serverless is the best","5","1_2"))
        val userAdapter = UserAdapter(this,userData,this@WelcomeActivity)
        userrecycleviewId.adapter = userAdapter
        submitidea.setOnClickListener{
            val intent= Intent(this,UserIdeaActivity::class.java)

            startActivity(intent)
        }

        val service = ServiceVolley()
        val apiController = APIController(service)
//        https://hudson-server.herokuapp.com/hudson/user/pfa12

        val path = "$userID/pfa12"
//        val path = "comments/pfa12"
        val params = JSONObject()
////        foo1=bar1&foo2=bar2
////        params.put("userID", "pfa12")
////        params.put("foo2", "bar2")
//
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
            Log.d("finalobject response", "/post request OK! Response: $response")
        }

    val path1 = "comments/$userID"
    val params1 = JSONArray()
        apiController.getJsonArray(path1, params1) { response ->
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


            Log.d("finaljsonarray response", "/post request OK! Response: $response")
        }

        val path2 = "users/$tableID"
        val params2 = JSONArray()
        apiController.getJsonArray(path1, params1) { response ->
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


            Log.d("finaltable response", "/post request OK! Response: $response")
        }







        var success: Boolean = false
        success = dbHandler!!.addUser(userID)

        if (success){
            val toast = Toast.makeText(this,"Saved Successfully", Toast.LENGTH_LONG).show()
        }
        else
        {
            val toast = Toast.makeText(this,"already present ", Toast.LENGTH_LONG).show()
        }


        var user = dbHandler!!.getUsers()
        Toast.makeText(this,user, Toast.LENGTH_LONG).show()









    }
}
