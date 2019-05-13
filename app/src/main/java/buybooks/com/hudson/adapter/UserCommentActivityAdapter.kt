package buybooks.com.hudson.adapter

import android.content.Context
import android.support.v7.widget.RecyclerView
import android.text.method.ScrollingMovementMethod
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import buybooks.com.hudson.R
import buybooks.com.hudson.model.User
import kotlinx.android.synthetic.main.content_user_comment.view.*

class UserCommentActivityAdapter(val context: Context, val userData: List<User>, var clickListener: View.OnClickListener?): RecyclerView.Adapter<UserCommentActivityAdapter.UserCommentActivityViewholder>()
{
    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): UserCommentActivityViewholder {
        val view: View= LayoutInflater.from(context).inflate(R.layout.content_user_comment,parent,false)
        return UserCommentActivityViewholder(view)
    }

    override fun getItemCount(): Int {
        return userData.size
    }

    override fun onBindViewHolder(holder: UserCommentActivityViewholder, position: Int) {
        val user = userData[position]
        holder.setData(user,position)

    }


    inner class UserCommentActivityViewholder(itemView:View) : RecyclerView.ViewHolder(itemView)
    {
        fun setData(user: User, position: Int) {
//            itemView.usercommentidea.setText(user!!.name)
            itemView.usercommentidea.movementMethod = ScrollingMovementMethod();
            val id: Int= context.getResources().getIdentifier(user.name, "drawable", context.getPackageName())
            itemView.usercomimg.setImageResource(id)
            itemView.usercomimg.setTag(user.name)
        }

        init {
            if (clickListener != null) {
                itemView.setOnClickListener(clickListener)
                itemView.usercomimg.setOnClickListener(clickListener)
                itemView.usercommentidea.setOnClickListener(clickListener)
            }
        }


    }
}