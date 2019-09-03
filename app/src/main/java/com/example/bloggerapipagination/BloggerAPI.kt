package com.example.bloggerapipagination

import retrofit2.Call
import retrofit2.Retrofit
import retrofit2.converter.gson.GsonConverterFactory
import retrofit2.http.GET
import retrofit2.http.Path
import retrofit2.http.Url

object BloggerAPI {

    val key = "[YourApiKey]"
    val url = "https://www.googleapis.com/blogger/v3/blogs/[YourBlogId]/posts/"

    var postService: PostService? = null

    val service: PostService?
        get() {
            if (postService == null) {
                val retrofit = Retrofit.Builder()
                    .baseUrl(url)
                    .addConverterFactory(GsonConverterFactory.create())
                    .build()

                postService = retrofit.create(PostService::class.java)
            }
            return postService
        }

    interface PostService {
        @GET
        fun getPostList(@Url url: String): Call<PostList>
    }


}
