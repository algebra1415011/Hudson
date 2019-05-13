package buybooks.com.hudson.user

import android.content.Intent
import android.os.Bundle
import android.support.design.widget.Snackbar
import android.support.v7.app.AppCompatActivity
import android.support.v7.widget.LinearLayoutManager
import android.text.method.ScrollingMovementMethod
import android.view.View
import android.widget.LinearLayout
import android.widget.Toast
import buybooks.com.hudson.R
import buybooks.com.hudson.ThanksActivity
import buybooks.com.hudson.adapter.UserCommentActivityAdapter
import buybooks.com.hudson.model.User

import kotlinx.android.synthetic.main.activity_user_comment.*
import kotlinx.android.synthetic.main.content_user_comment.view.*
import kotlinx.android.synthetic.main.gdmodel_layout.view.*

class UserCommentActivity : AppCompatActivity(), View.OnClickListener {
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

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_user_comment)
        var usercommentname:String =intent.getStringExtra("usercommentimg")
        val id: Int= this.resources.getIdentifier(usercommentname, "drawable", this.packageName)
        usercomimg.setImageResource(id)
        useridea.movementMethod = ScrollingMovementMethod();


        //rating button

        ratingbt.setOnClickListener{
            val intent= Intent(this,ThanksActivity::class.java)
            intent.putExtra("activity","ratingact")

            startActivity(intent)
        }

//        userrecycleviewId
        val linearLayout = LinearLayoutManager(this)
        linearLayout.orientation=LinearLayout.VERTICAL
        usercommentrid.layoutManager=linearLayout
        val userData= listOf<User>(User("1","navneet",true,23,"AI is the best","5","1_1"),
                User("2","shunya1",false,24,"Serverless is the best","5","1_2"),
                User("3","dhaval",false,24,"Serverless is the best","5","1_2"),
                User("4","nishi",false,24,"Serverless is the best","5","1_2"),
                User("5","vikrant",false,24,"Serverless is the best","5","1_2"),
                User("6","bhawna",false,24,"Serverless is the best","5","1_2"),
                User("7","anjali",false,24,"Serverless is the best","5","1_2"),
                User("8","rajen",false,24,"Serverless is the best","5","1_2"),
                User("9","venky",false,24,"Serverless is the best","5","1_2"))
        val userAdapter = UserCommentActivityAdapter(this,userData,this@UserCommentActivity)
        usercommentrid.adapter = userAdapter



    }

}
