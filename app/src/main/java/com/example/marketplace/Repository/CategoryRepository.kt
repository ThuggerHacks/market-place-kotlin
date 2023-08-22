package com.example.marketplace.Repository

import com.example.marketplace.Instance.RetrofitClient
import com.example.marketplace.Interface.CategoryService
import com.example.marketplace.Model.Category
import retrofit2.Call
import retrofit2.Callback
import retrofit2.Response

class CategoryRepository {

    fun getCategoryId(name:String, callback:(Category) -> Unit){
        val service = RetrofitClient.createService(CategoryService::class.java)
        val category = service.getCategoryId(name)

        category.enqueue(object :Callback<Category> {
            override fun onResponse(call: Call<Category>, response: Response<Category>) {
                if(response.isSuccessful){
                    val body = response.body()!!
                    callback(body)
                }else{
                    callback(Category())
                }
            }

            override fun onFailure(call: Call<Category>, t: Throwable) {
                callback(Category())
            }
        })
    }

    fun getCategoryAll(callback:(List<Category>) -> Unit){
        val service = RetrofitClient.createService(CategoryService::class.java)
        val category = service.getCategoryAll()
        val list = ArrayList<Category>()

        category.enqueue(object :Callback<List<Category>> {
            override fun onResponse(call: Call<List<Category>>, response: Response<List<Category>>) {
                if(response.isSuccessful){
                    val body = response.body()!!
                    callback(body)
                }else{
                    callback(list)
                }
            }

            override fun onFailure(call: Call<List<Category>>, t: Throwable) {
                callback(list)
            }
        })
    }
}