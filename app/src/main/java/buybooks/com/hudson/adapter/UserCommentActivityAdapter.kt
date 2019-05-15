package buybooks.com.hudson.adapter

import android.content.Context
import android.support.v7.widget.RecyclerView
import android.text.method.ScrollingMovementMethod
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import buybooks.com.hudson.R
import buybooks.com.hudson.model.Comment
import kotlinx.android.synthetic.main.content_user_comment.view.*

class UserCommentActivityAdapter(val context: Context, val commentData: List<Comment>, var clickListener: View.OnClickListener?): RecyclerView.Adapter<UserCommentActivityAdapter.UserCommentActivityViewholder>()
{
    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): UserCommentActivityViewholder {
        val view: View= LayoutInflater.from(context).inflate(R.layout.content_user_comment,parent,false)
        return UserCommentActivityViewholder(view)
    }

    override fun getItemCount(): Int {
        return commentData.size
    }

    override fun onBindViewHolder(holder: UserCommentActivityViewholder, position: Int) {
        val comment = commentData[position]
        holder.setData(comment,position)

    }


    inner class UserCommentActivityViewholder(itemView:View) : RecyclerView.ViewHolder(itemView)
    {
        fun setData(comment: Comment, position: Int) {
            itemView.usercommentidea.setText(comment.comment)
            itemView.usercommentidea.movementMethod = ScrollingMovementMethod();
            val id: Int= context.getResources().getIdentifier(comment.commentorID, "drawable", context.getPackageName())
            itemView.usertopimg.setImageResource(id)
            itemView.usertopimg.setTag(comment.commentorID)

        }

        init {
            if (clickListener != null) {
                itemView.setOnClickListener(clickListener)
                itemView.usertopimg.setOnClickListener(clickListener)
                itemView.usercommentidea.setOnClickListener(clickListener)
            }
        }


    }
}