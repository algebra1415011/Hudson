package buybooks.com.hudson.adapter

import android.content.Context
import android.content.DialogInterface
import android.graphics.Color
import android.support.v7.widget.RecyclerView
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.Toast
import buybooks.com.hudson.R
import buybooks.com.hudson.connection.DatabaseHelper
import buybooks.com.hudson.model.User
import buybooks.com.hudson.user.UserIdeaActivity
import kotlinx.android.synthetic.main.gdmodel_layout.view.*

class UserAdapter(val context: Context, val userData: List<User>,var clickListener: View.OnClickListener?): RecyclerView.Adapter<UserAdapter.UserViewholder>()
{
    private var dbHandler: DatabaseHelper? = null

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): UserViewholder {
      val view: View= LayoutInflater.from(context).inflate(R.layout.gdmodel_layout,parent,false)
        return UserViewholder(view)
    }

    override fun getItemCount(): Int {
        return userData.size
    }

    override fun onBindViewHolder(holder: UserViewholder, position: Int) {
        val user = userData[position]
        holder.setData(user,position)

    }


    inner class UserViewholder(itemView:View) : RecyclerView.ViewHolder(itemView)
    {
        fun setData(user: User, position: Int) {
            itemView.username.setText(userData[position].name)
            val id: Int= context.getResources().getIdentifier(userData[position].userID, "drawable", context.getPackageName())
            itemView.userimg.setImageResource(id)
            itemView.userimg.setTag(userData[position].userID)
            dbHandler = DatabaseHelper(context)
            var isRated = dbHandler!!.isRated(userData[position].userID)
            Toast.makeText(context,isRated, Toast.LENGTH_LONG).show()
            if(!userData[position].idea.equals("null") && isRated.equals("true"))
            {
                itemView.indicator.setTag("yellow")
                itemView.indicator.setBackgroundColor(Color.GREEN)
            }
            else if(!userData[position].idea.equals("null"))
            {
                itemView.indicator.setBackgroundColor(Color.YELLOW)
            }

        }

        init {
            if (clickListener != null) {
                itemView.setOnClickListener(clickListener)
                itemView.userimg.setOnClickListener(clickListener)
                itemView.username.setOnClickListener(clickListener)
                itemView.indicator.setOnClickListener(clickListener)
            }
        }


    }
}