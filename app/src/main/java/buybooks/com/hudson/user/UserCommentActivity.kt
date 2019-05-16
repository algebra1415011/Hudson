package buybooks.com.hudson.user

import android.content.Intent
import android.os.Bundle
import android.support.design.widget.Snackbar
import android.support.v7.app.AppCompatActivity
import android.support.v7.widget.LinearLayoutManager
import android.text.method.ScrollingMovementMethod
import android.util.Log
import android.view.View
import android.widget.*
import buybooks.com.hudson.R
import buybooks.com.hudson.ThanksActivity
import buybooks.com.hudson.adapter.UserCommentActivityAdapter
import buybooks.com.hudson.connection.DatabaseHelper
import buybooks.com.hudson.controller.APIController
import buybooks.com.hudson.model.Comment
import buybooks.com.hudson.model.User
import buybooks.com.hudson.volley.ServiceVolley

import kotlinx.android.synthetic.main.activity_user_comment.*
import kotlinx.android.synthetic.main.content_user_comment.view.*

import org.json.JSONArray
import org.json.JSONObject

class UserCommentActivity : AppCompatActivity(), View.OnClickListener {
    private var dbHandler: DatabaseHelper? = null
    override fun onClick(p0: View?) {


        when (p0?.getId()) {
            R.id.usercommentidea ->{
                Toast.makeText(getApplicationContext(),p0.usercommentidea.text, Toast.LENGTH_SHORT).show();
//                val intent= Intent(this,UserCommentActivity::class.java)
//                intent.putExtra("usercommentimg", p0.username.text.toString())
//                startActivity(intent)

            }

            R.id.userimg ->{
                Toast.makeText(getApplicationContext(),p0.usercomimg.getTag().toString(), Toast.LENGTH_SHORT).show();
//                val intent= Intent(this, UserCommentActivity::class.java)
//                intent.putExtra("usercommentimg", p0.usercomimg.getTag().toString())
//                startActivity(intent)


            }
            else -> {
                Toast.makeText(getApplicationContext(),"", Toast.LENGTH_SHORT).show();
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
        setContentView(R.layout.activity_user_comment)
        dbHandler = DatabaseHelper(this)
        var usercommentname:String =intent.getStringExtra("usercommentimg")
        val id: Int= this.resources.getIdentifier(usercommentname, "drawable", this.packageName)
        usercomimg.setImageResource(id)
        useridea.movementMethod = ScrollingMovementMethod();
        dbHandler = DatabaseHelper(this)
        var isRated = dbHandler!!.isRated(usercommentname)
        if(isRated.equals("true")){
            (findViewById<RatingBar>(R.id.ratingBar3)).visibility=(View.GONE)
            (findViewById<Button>(R.id.ratingbt)).visibility=(View.GONE)
        }
        val service = ServiceVolley()
        val apiController = APIController(service)
//        var localuser = dbHandler!!.getUsers()
//        Toast.makeText(this,localuser, Toast.LENGTH_LONG).show()
        val path1 = "comments/$usercommentname"
        val params1 = JSONArray()
        val commentData = ArrayList<Comment>()

        val linearLayout = LinearLayoutManager(this)
        linearLayout.orientation=LinearLayout.VERTICAL
        usercommentrid.layoutManager=linearLayout

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
            for (i in 0..(response!!.length() - 1)) {
                val user = response.getJSONObject(i)
                    commentData.add(Comment(user.getString("commentID"),user.getString("comment"),user.getString("commentorID")))

                // Your code here
                Log.d("finaltable response $i", "/post request OK! Response: $user "+commentData.size.toString())

            }
            val userAdapter = UserCommentActivityAdapter(this,commentData,this@UserCommentActivity)
            usercommentrid.adapter = userAdapter

        }



        //rating button

        ratingbt.setOnClickListener{
            val ratingBar = findViewById<RatingBar>(R.id.ratingBar3)
            dbHandler = DatabaseHelper(this)
            var isRated = dbHandler!!.addRating(intent.getStringExtra("usercommentimg"),"true")
            Toast.makeText(this,"rating is added"+isRated.toString(), Toast.LENGTH_LONG).show()
            val service = ServiceVolley()
            val apiController = APIController(service)
            val path1 = "user/${intent.getStringExtra("usercommentimg")}"
            val params1 = JSONObject()
            apiController.getJsonObject(path1, params1) {response1 ->
                val path2 = "user/${response1?.getString("userID")}"
                val params2 = JSONObject()
                params2.put("userID","${response1?.getString("userID")}")
                params2.put("name","${response1?.getString("name")}")
                params2.put("isLeader","${response1?.getBoolean("isLeader")}")
                params2.put("tableID","${response1?.getInt("tableID")}")
                params2.put("idea","${response1?.getString("idea")}")
                params2.put("rating","${response1!!.getInt("rating")+ratingBar.rating}")
                params2.put("ideaRateCount","${response1!!.getInt("ideaRateCount")+1}")
                apiController.put(path2, params2) { response2 ->
                    if(response2 != null){
                        Toast.makeText(this,"Rating submitted to Database", Toast.LENGTH_LONG).show()
                    }
                }
            }


            val intent= Intent(this,ThanksActivity::class.java)
            intent.putExtra("activity","ratingact")


            startActivity(intent)
        }

        addcommentbutton.setOnClickListener{
            val inputComment = findViewById(R.id.addcomment) as EditText
            val service = ServiceVolley()
            dbHandler = DatabaseHelper(this)
            val commentorID = dbHandler!!.getUsers()
            val apiController = APIController(service)
            val path1 = "comments/${usercommentname}"
            val params1 = JSONArray()
            apiController.getJsonArray(path1, params1) { response1 ->
                if (response1 != null && response1.length() > 0){
                val path2 = "comments/${usercommentname}"
                val params2 = JSONObject()
                params2.put("commentID","${intent.getStringExtra("usercommentimg")}_${(response1.length()+1)}")
                params2.put("comment","${inputComment.text}")
                params2.put("commentorID","${commentorID}")
                apiController.put(path2,params2){response2 ->
                    if(response2 != null){
                        Toast.makeText(this,"Comment submitted to Database", Toast.LENGTH_LONG).show()
                    }
                }

            }else{
                    val path2 = "comments/${usercommentname}"
                    val params2 = JSONObject()
                    params2.put("commentID","${intent.getStringExtra("usercommentimg")}_1")
                    params2.put("comment","${inputComment.text}")
                    params2.put("commentorID","${commentorID}")
                    apiController.put(path2,params2){response2 ->
                        if(response2 != null){
                            Toast.makeText(this,"Comment submitted to Database", Toast.LENGTH_LONG).show()
                        }
                    }
                }
                startActivity(getIntent())
                finish()
            }
        }

    }

}
