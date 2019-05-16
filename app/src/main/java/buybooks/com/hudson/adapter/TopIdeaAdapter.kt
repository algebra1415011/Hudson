package buybooks.com.hudson.adapter

import android.content.Context
import android.support.v7.widget.RecyclerView
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import buybooks.com.hudson.R
import buybooks.com.hudson.connection.DatabaseHelper
import buybooks.com.hudson.model.User
import kotlinx.android.synthetic.main.view_item.view.*

class TopIdeaAdapter(val context: Context, val userData: List<User>, var clickListener: View.OnClickListener?): RecyclerView.Adapter<TopIdeaAdapter.TopIdeaViewholder>()
{
    private var dbHandler: DatabaseHelper? = null
//    private val lastSelectedPosition = -1
    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): TopIdeaViewholder {
        val view: View = LayoutInflater.from(context).inflate(R.layout.view_item,parent,false)
        return TopIdeaViewholder(view)
    }

    override fun getItemCount(): Int {
        return userData.size
    }

    override fun onBindViewHolder(holder: TopIdeaViewholder, position: Int) {
        val user = userData[position]
        holder.setData(user,position)

    }


    inner class TopIdeaViewholder(itemView:View) : RecyclerView.ViewHolder(itemView)
    {
        fun setData(user: User, position: Int) {
            itemView.usertopidea.setText(userData[position].name)
//            itemView.topidearadiobt.setChecked(lastSelectedPosition == position)
            val id: Int= context.getResources().getIdentifier(userData[position].userID, "drawable", context.getPackageName())
            itemView.usertopimg.setImageResource(id)
            itemView.usertopimg.setTag(userData[position].userID)


        }

        init {
            if (clickListener != null) {
                itemView.setOnClickListener(clickListener)
                itemView.usertopimg.setOnClickListener(clickListener)
                itemView.usertopidea.setOnClickListener(clickListener)
//                itemView.topidearadiobt.setOnClickListener(clickListener)
            }
        }


    }
}