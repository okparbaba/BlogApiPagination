package com.example.bloggerapipagination

import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.widget.Toast
import retrofit2.Call
import retrofit2.Callback
import retrofit2.Response
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView
import kotlinx.android.synthetic.main.activity_main.*
import android.widget.AbsListView



class MainActivity : AppCompatActivity() {
    lateinit var manager: LinearLayoutManager
    lateinit var adapter: PostAdapter
    lateinit var items: ArrayList<Item>
    var isScrolling: Boolean = false
    var currentItems: Int = 0
    var totalItems:Int = 0
    var scrollOutItems:Int = 0
    var token = ""

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)
        items = ArrayList()
        manager = LinearLayoutManager(this)
        adapter = PostAdapter(this, items)
        recyclerView.layoutManager = manager
        recyclerView.adapter = adapter

        recyclerView.addOnScrollListener(object : RecyclerView.OnScrollListener() {
            override fun onScrollStateChanged(recyclerView: RecyclerView, newState: Int) {
                super.onScrollStateChanged(recyclerView, newState)
                if (newState == AbsListView.OnScrollListener.SCROLL_STATE_TOUCH_SCROLL) {
                    isScrolling = true
                }
            }

            override fun onScrolled(recyclerView: RecyclerView, dx: Int, dy: Int) {
                super.onScrolled(recyclerView, dx, dy)
                currentItems = manager.childCount
                totalItems = manager.itemCount
                scrollOutItems = manager.findFirstVisibleItemPosition()

                if (isScrolling && currentItems + scrollOutItems === totalItems) {
                    isScrolling = false
                    getData()
                }
            }
        })
        getData()
    }
    private fun getData() {
        var url = BloggerAPI.url + "?key=" + BloggerAPI.key
        if (token !== "") {
            url = "$url&pageToken=$token"
        }

        val postList = BloggerAPI.service!!.getPostList(url)
        postList.enqueue(object : Callback<PostList>{
            override fun onResponse(call: Call<PostList>, response: Response<PostList>) {
                val list = response.body()
                token = list!!.nextPageToken
                items.addAll(list.items)
                adapter.notifyDataSetChanged()
                //Toast.makeText(this@MainActivity, "Success", Toast.LENGTH_SHORT).show()

            }

            override fun onFailure(call: Call<PostList>, t: Throwable) {
                Toast.makeText(this@MainActivity, "Error Occured", Toast.LENGTH_SHORT).show()
            }
        })

    }
}
