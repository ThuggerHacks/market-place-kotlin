package com.example.marketplace.Repository

import com.example.marketplace.Instance.RetrofitClient
import com.example.marketplace.Interface.ProductService
import com.example.marketplace.Model.Product
import retrofit2.Call
import retrofit2.Callback
import retrofit2.Response

class ProductRepository {

    fun createProduct(product:Product, callback: (Product) -> Unit){
        val service = RetrofitClient.createService(ProductService::class.java)
        val product = service.addProduct(product)

        product.enqueue(object :  Callback<Product> {
            override fun onResponse(call: Call<Product>, response: Response<Product>) {
                if(response.isSuccessful){
                    val body = response.body()!!
                    callback(body)
                }else{
                    callback(Product())
                }
            }
            override fun onFailure(call: Call<Product>, t: Throwable) {
                callback(Product())
            }
        })
    }

    fun getOneProduct(id:Int, callback: (Product) -> Unit){
        val service = RetrofitClient.createService(ProductService::class.java)
        val product = service.getOne(id)

        product.enqueue(object :  Callback<Product> {
            override fun onResponse(call: Call<Product>, response: Response<Product>) {
                if(response.isSuccessful){
                    val body = response.body()!!
                    callback(body)
                }else{
                    callback(Product())
                }
            }
            override fun onFailure(call: Call<Product>, t: Throwable) {
                callback(Product())
            }
        })
    }

    fun getAll( callback: (List<Product>) -> Unit){
        val service = RetrofitClient.createService(ProductService::class.java)
        val product = service.getAll()
        var list = ArrayList<Product>()
        product.enqueue(object :  Callback<List<Product>> {
            override fun onResponse(call: Call<List<Product>>, response: Response<List<Product>>) {
                if(response.isSuccessful){
                    val body = response.body()!!
                    callback(body)
                }else{
                    callback(list)
                }
            }
            override fun onFailure(call: Call<List<Product>>, t: Throwable) {
                callback(list)
            }
        })
    }

    fun deleteOneProduct(id:Int, callback: (Product) -> Unit){
        val service = RetrofitClient.createService(ProductService::class.java)
        val product = service.deleteOne(id)

        product.enqueue(object :  Callback<Product> {
            override fun onResponse(call: Call<Product>, response: Response<Product>) {
                if(response.isSuccessful){
                    val body = response.body()!!
                    callback(body)
                }else{
                    callback(Product())
                }
            }
            override fun onFailure(call: Call<Product>, t: Throwable) {
                callback(Product())
            }
        })
    }

    fun updateProduct(id:Int,product:Product,callback: (Product) -> Unit){
        val service = RetrofitClient.createService(ProductService::class.java)
        val product = service.updateOne(id, product)

        product.enqueue(object :  Callback<Product> {
            override fun onResponse(call: Call<Product>, response: Response<Product>) {
                if(response.isSuccessful){
                    val body = response.body()!!
                    callback(body)
                }else{
                    callback(Product())
                }
            }
            override fun onFailure(call: Call<Product>, t: Throwable) {
                callback(Product())
            }
        })
    }

    fun getProductUser(userId:Int, callback: (List<Product>) -> Unit){
        val service = RetrofitClient.createService(ProductService::class.java)
        val product = service.getByUserId(userId)
        var list = ArrayList<Product>()
        product.enqueue(object :  Callback<List<Product>> {
            override fun onResponse(call: Call<List<Product>>, response: Response<List<Product>>) {
                if(response.isSuccessful){
                    val body = response.body()!!
                    callback(body)
                }else{
                    callback(list)
                }
            }
            override fun onFailure(call: Call<List<Product>>, t: Throwable) {
                callback(list)
            }
        })
    }

    fun getProductByCategoryName(name:String, callback: (List<Product>) -> Unit){
        val service = RetrofitClient.createService(ProductService::class.java)
        val product = service.getProductByCategoryName(name)
        var list = ArrayList<Product>()
        product.enqueue(object :  Callback<List<Product>> {
            override fun onResponse(call: Call<List<Product>>, response: Response<List<Product>>) {
                if(response.isSuccessful){
                    val body = response.body()!!
                    callback(body)
                }else{
                    callback(list)
                }
            }
            override fun onFailure(call: Call<List<Product>>, t: Throwable) {
                callback(list)
            }
        })
    }

    fun searchProduct(title:String, callback: (List<Product>) -> Unit){
        val service = RetrofitClient.createService(ProductService::class.java)
        val product = service.getProductByTitle(title)
        var list = ArrayList<Product>()
        product.enqueue(object :  Callback<List<Product>> {
            override fun onResponse(call: Call<List<Product>>, response: Response<List<Product>>) {
                if(response.isSuccessful){
                    val body = response.body()!!
                    callback(body)
                }else{
                    callback(list)
                }
            }
            override fun onFailure(call: Call<List<Product>>, t: Throwable) {
                callback(list)
            }
        })
    }
}