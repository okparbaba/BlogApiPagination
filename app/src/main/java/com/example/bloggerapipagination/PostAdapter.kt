package com.example.bloggerapipagination

import android.content.Context
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.ImageView
import android.widget.TextView
import androidx.recyclerview.widget.RecyclerView
import com.bumptech.glide.Glide
import org.jsoup.Jsoup


class PostAdapter(private val context: Context, private val items: List<Item>) :
    RecyclerView.Adapter<PostAdapter.PostViewHolder>() {

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): PostViewHolder {
        val inflater = LayoutInflater.from(context)
        val view = inflater.inflate(R.layout.post_item, parent, false)
        return PostViewHolder(view)
    }

    override fun onBindViewHolder(holder: PostViewHolder, position: Int) {
        val (_, _, content, _, _, _, _, _, _, title, _, url) = items[position]
        holder.postTitle.text = title

        val document = Jsoup.parse(content)
        holder.postDescription.text = document.text()

        val elements = document.select("img")
        Glide.with(context).load(elements[0].attr("src")).into(holder.postImage)

    }

    override fun getItemCount(): Int {
        return items.size
    }

    inner class PostViewHolder(itemView: View) : RecyclerView.ViewHolder(itemView) {
        internal var postImage: ImageView
        internal var postTitle: TextView
        internal var postDescription: TextView

        init {
            postImage = itemView.findViewById(R.id.postImage) as ImageView
            postTitle = itemView.findViewById(R.id.postTitle)
            postDescription = itemView.findViewById(R.id.postDescription)
        }
    }
}
