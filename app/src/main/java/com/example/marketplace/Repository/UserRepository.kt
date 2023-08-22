package com.example.marketplace.Repository

import android.Manifest
import android.Manifest.permission.WRITE_EXTERNAL_STORAGE
import android.content.ContentResolver
import android.view.View
import androidx.core.app.ActivityCompat
import com.example.marketplace.Instance.RetrofitClient
import com.example.marketplace.Interface.ProfileService
import com.example.marketplace.Interface.UserService
import com.example.marketplace.Model.Profile
import com.example.marketplace.Model.User
import okhttp3.MediaType
import okhttp3.MultipartBody
import okhttp3.RequestBody
import retrofit2.Call
import retrofit2.Callback
import retrofit2.Response

class UserRepository {

    fun login(user:User, callback: (User) -> Unit) {
        val service = RetrofitClient.createService(UserService::class.java)
        val callUser: Call<User> = service.loginUser(user)

        callUser.enqueue(object : Callback<User> {
            override fun onResponse(call: Call<User>, response: Response<User>) {
                if (response.isSuccessful) {
                    val user: User = response.body()!!
                    callback(user)
                } else {
                    callback(User()) // Return an empty user object in case of unsuccessful response
                }
            }

            override fun onFailure(call: Call<User>, t: Throwable) {
                callback(User()) // Return an empty user object on failure
            }
        })
    }

    fun getUserOne(id:Int,callback: (User) -> Unit){
        val service = RetrofitClient.createService(UserService::class.java)
        val callUser: Call<User> = service.getUser(id)

        callUser.enqueue(object : Callback<User> {
            override fun onResponse(call: Call<User>, response: Response<User>) {
                if (response.isSuccessful) {
                    val user: User = response.body()!!
                    callback(user)
                } else {
                    callback(User()) // Return an empty user object in case of unsuccessful response
                }
            }

            override fun onFailure(call: Call<User>, t: Throwable) {
                callback(User()) // Return an empty user object on failure
            }
        })
    }

    fun registerUser(user:User, callback: (User) -> Unit){
        val service = RetrofitClient.createService(UserService::class.java)
        val callUser = service.createUser(user)

        callUser.enqueue(object : Callback<User> {
            override fun onResponse(call: Call<User>, response: Response<User>) {
                if (response.isSuccessful) {
                    val user: User = response.body()!!
                    callback(user)
                } else {
                    callback(User()) // Return an empty user object in case of unsuccessful response
                }
            }

            override fun onFailure(call: Call<User>, t: Throwable) {
                callback(User()) // Return an empty user object on failure
            }
        })
    }

    fun updateUserProfile(id:Int, user:User,callback: (User) -> Unit){
        val service = RetrofitClient.createService(UserService::class.java)
        val callUser = service.updateUser(id,user)

        callUser.enqueue(object : Callback<User> {
            override fun onResponse(call: Call<User>, response: Response<User>) {
                if (response.isSuccessful) {
                    val user: User = response.body()!!
                    callback(user)
                } else {
                    callback(User()) // Return an empty user object in case of unsuccessful response
                }
            }

            override fun onFailure(call: Call<User>, t: Throwable) {
                callback(User()) // Return an empty user object on failure
            }
        })
    }

    fun uploadPhoto(photo:MultipartBody.Part, callback: (Profile) -> Unit){
        val service = RetrofitClient.createService(ProfileService::class.java)
        val callProfile = service.uploadPhoto(photo)

        // Proceed with the file access

        callProfile.enqueue(object : Callback<Profile> {
            override fun onResponse(call: Call<Profile>, response: Response<Profile>) {
                if (response.isSuccessful) {
                    val profile: Profile = response.body()!!
                    callback(profile)
                } else {
                    callback(Profile()) // Return an empty user object in case of unsuccessful response
                }
            }

            override fun onFailure(call: Call<Profile>, t: Throwable) {
                callback(Profile()) // Return an empty user object on failure
            }
        })
    }
}