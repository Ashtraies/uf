package org.ash.test.uf.ui.adapter

import android.content.Intent
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.paging.PagedListAdapter
import androidx.recyclerview.widget.DiffUtil
import androidx.recyclerview.widget.RecyclerView
import com.squareup.picasso.Picasso
import kotlinx.android.synthetic.main.item_user.view.*
import org.ash.test.uf.R
import org.ash.test.uf.model.User
import org.ash.test.uf.ui.UserDetailActivity
import org.ash.test.uf.util.Constants
import org.ash.test.uf.util.Logger

class PagedUserAdapter : PagedListAdapter<User, PagedUserAdapter.UserViewHolder>(diffCallback) {

    var user: User? = null

    companion object {
        private val diffCallback = object : DiffUtil.ItemCallback<User>() {

            override fun areItemsTheSame(oldItem: User, newItem: User): Boolean =
                oldItem.id == newItem.id

            override fun areContentsTheSame(oldItem: User, newItem: User): Boolean =
                oldItem == newItem
        }
    }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): UserViewHolder {
        val contentView = LayoutInflater.from(parent.context).inflate(
            R.layout.item_user,
            parent,
            false
        )
        return UserViewHolder(contentView)
    }

    override fun onBindViewHolder(holder: UserViewHolder, position: Int) {
        user = getItem(position)
        val context = holder.contentView.context

        val url = user?.thumbnail

        Picasso.Builder(context).build()
            .load(url)
            .resize(160, 160)
            .centerCrop()
            .into(holder.contentView.thumbnail)
        holder.contentView.title.text = user?.title
        holder.contentView.last_name.text = user?.lastName
        holder.contentView.gender.text = user?.gender
        holder.contentView.dob.text = user?.getDOBString()
        val id = user?.id

        holder.contentView.setOnClickListener {
            Logger.L("Click position: $position, user id is: $id")
            context.startActivity(
                Intent(context, UserDetailActivity::class.java)
                    .addFlags(Intent.FLAG_ACTIVITY_NEW_TASK)
                    .putExtra(Constants.INTENT_USER_ID, id)
            )
        }
    }

    class UserViewHolder(val contentView: View) :
        RecyclerView.ViewHolder(contentView)
}