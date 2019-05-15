package buybooks.com.hudson.user

import android.content.Intent
import android.os.Bundle
import android.support.v7.app.AppCompatActivity
import android.support.v7.widget.LinearLayoutManager
import android.text.method.ScrollingMovementMethod
import android.util.Log
import android.view.View
import android.widget.LinearLayout
import android.widget.Toast
import buybooks.com.hudson.R
import buybooks.com.hudson.ThanksActivity
import buybooks.com.hudson.adapter.UserCommentActivityAdapter
import buybooks.com.hudson.connection.DatabaseHelper
import buybooks.com.hudson.controller.APIController
import buybooks.com.hudson.model.Comment
import buybooks.com.hudson.volley.ServiceVolley

import kotlinx.android.synthetic.main.activity_user_comment.*
import kotlinx.android.synthetic.main.content_user_comment.view.*

import org.json.JSONArray

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
                Toast.makeText(getApplicationContext(),p0.usertopimg.getTag().toString(), Toast.LENGTH_SHORT).show();
//                val intent= Intent(this, UserCommentActivity::class.java)
//                intent.putExtra("usercommentimg", p0.usercomimg.getTag().toString())
//                startActivity(intent)


            }
            else -> {
                Toast.makeText(getApplicationContext(),"", Toast.LENGTH_SHORT).show();
            }
        }

    }

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_user_comment)
        dbHandler = DatabaseHelper(this)
        var usercommentname:String =intent.getStringExtra("usercommentimg")
        val id: Int= this.resources.getIdentifier(usercommentname, "drawable", this.packageName)
        usertopimg.setImageResource(id)
        useridea.movementMethod = ScrollingMovementMethod();





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
            
            dbHandler = DatabaseHelper(this)
            var isRated = dbHandler!!.addRating(intent.getStringExtra("usercommentimg"),"true")
            Toast.makeText(this,"rating is added"+isRated.toString(), Toast.LENGTH_LONG).show()
            val intent= Intent(this,ThanksActivity::class.java)
            intent.putExtra("activity","ratingact")


            startActivity(intent)
        }


    }

}
