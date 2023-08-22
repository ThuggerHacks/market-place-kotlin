package com.example.marketplace.View.Fragments

import android.Manifest
import android.app.Activity
import android.content.Context
import android.content.Intent
import android.content.pm.PackageManager
import android.net.Uri
import android.os.Bundle
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.Button
import android.widget.EditText
import android.widget.ImageView
import android.widget.LinearLayout
import android.widget.TextView
import android.widget.Toast
import androidx.core.app.ActivityCompat
import androidx.core.net.toUri
import com.example.marketplace.Model.User
import com.example.marketplace.R
import com.example.marketplace.Repository.UserRepository
import com.example.marketplace.View.MyProductActivity
import com.squareup.picasso.Picasso
import uploadFileToFirebaseStorage
import java.net.URI

// TODO: Rename parameter arguments, choose names that match
// the fragment initialization parameters, e.g. ARG_ITEM_NUMBER
private const val ARG_PARAM1 = "param1"
private const val ARG_PARAM2 = "param2"

/**
 * A simple [Fragment] subclass.
 * Use the [AccountFragment.newInstance] factory method to
 * create an instance of this fragment.
 */
class AccountFragment : Fragment() {
    // TODO: Rename and change types of parameters
    private var param1: String? = null
    private var param2: String? = null
    private lateinit var viewCopy: View
    private lateinit var imageUri: Uri
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        arguments?.let {
            param1 = it.getString(ARG_PARAM1)
            param2 = it.getString(ARG_PARAM2)
        }
    }

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        // Inflate the layout for this fragment
        val view = inflater.inflate(R.layout.fragment_account, container, false)
        val profile_pic = view.findViewById<ImageView>(R.id.user_profile)
        profile_pic.setOnClickListener {
            val intent = Intent(Intent.ACTION_PICK)
            intent.type = "image/*"  // Set the content type to images
            val chooser = Intent.createChooser(intent, "Escolha uma imagem")
            startActivityForResult(chooser, AddFragment.IMAGE_PICK_REQUEST_CODE)
            viewCopy = view
        }

        val btnGoToProducts = view.findViewById<TextView>(R.id.go_to_my_products)
        btnGoToProducts.setOnClickListener {
            val intent = Intent(requireContext(), MyProductActivity::class.java)
            requireContext().startActivity(intent)
        }
        //get data onload
        getMyData(view)
        //update user data

        val btnProfileEdit = view.findViewById<Button>(R.id.btn_profile_edit)
        btnProfileEdit.setOnClickListener {
            editProfile(view)
        }

        return view
    }

    fun editProfile(view: View) {
        val storage = requireContext().getSharedPreferences("user", Context.MODE_PRIVATE)
        val id = storage.getInt("id", 0)
        val name = view.findViewById<EditText>(R.id.user_name)
        val email = view.findViewById<EditText>(R.id.user_email)
        val phone = view.findViewById<EditText>(R.id.user_cell)
        val location = view.findViewById<EditText>(R.id.user_location)
        val pass = view.findViewById<EditText>(R.id.user_password)
        val pass2 = view.findViewById<EditText>(R.id.user_c_password)
        val profile = view.findViewById<ImageView>(R.id.user_profile)
        val btnEdit = view.findViewById<Button>(R.id.btn_profile_edit)
        btnEdit.setText("CARREGANDO...")

        if (pass.text.toString() != pass2.text.toString() && pass.text.toString() != "") {
            btnEdit.setText("EDITAR PERFIL")
            Toast.makeText(view.context, "Passwoards devem ser iguais", Toast.LENGTH_SHORT).show()
        } else {
            //update profile with image picture
            if (::imageUri.isInitialized) {
                try {
                    uploadFileToFirebaseStorage(imageUri) {
                        updateProfileData(
                            id, User(
                                0, name.text.toString(),
                                email.text.toString(), pass.text.toString(),
                                phone.text.toString(), location.text.toString(), it
                            )
                        ) {
                            if (it?.error == null) {
                                btnEdit.setText("EDITAR PERFIL")
                                Toast.makeText(
                                    view.context,
                                    "Dados Atualizados com sucesso!",
                                    Toast.LENGTH_SHORT
                                ).show()
                                name.setText(it.name)
                                email.setText(it.email)
                                phone.setText(it.phone)
                                location.setText(it.location)
                                if (it.profilePic.length > 0) {
                                    Picasso.get().load(it.profilePic).into(profile)
                                }
                            }else{
                                Toast.makeText(view.context, it.error, Toast.LENGTH_SHORT).show()
                            }
                        }
                    }
                } catch (e: Exception) {
                    btnEdit.setText("EDITAR PERFIL")
                    Toast.makeText(
                        view.context,
                        "Houve um erro carregando a imagem",
                        Toast.LENGTH_SHORT
                    ).show()
                }
            } else {
                //update profile without image picture
                updateProfileData(
                    id, User(
                        0, name.text.toString(),
                        email.text.toString(), pass.text.toString(),
                        phone.text.toString(), location.text.toString()
                    )
                ) {
                    if (it?.error == null) {
                        btnEdit.setText("EDITAR PERFIL")
                        Toast.makeText(
                            view.context,
                            "Dados Atualizados com sucesso!",
                            Toast.LENGTH_SHORT
                        ).show()
                        name.setText(it.name)
                        email.setText(it.email)
                        phone.setText(it.phone)
                        location.setText(it.location)
                        if (it.profilePic != null) {
                            Picasso.get().load(it.profilePic).into(profile)
                        }
                    }else{
                        Toast.makeText(view.context, it.error, Toast.LENGTH_SHORT).show()
                    }
                }
            }
        }

    }

    fun updateProfileData(id: Int, user: User, callback: (User) -> Unit) {
        val profile = UserRepository()
        try {
            profile.updateUserProfile(id, user) {
                callback(it)
            }
        }catch(e:Exception){
            callback(User())
        }
    }

    fun getMyData(view: View) {
        val storage = requireContext().getSharedPreferences("user", Context.MODE_PRIVATE)
        val id = storage.getInt("id", 0)
        val name = view.findViewById<EditText>(R.id.user_name)
        val email = view.findViewById<EditText>(R.id.user_email)
        val phone = view.findViewById<EditText>(R.id.user_cell)
        val location = view.findViewById<EditText>(R.id.user_location)
        val profile = view.findViewById<ImageView>(R.id.user_profile)

        val user = UserRepository()
        try {
            user.getUserOne(id) {
                if (it?.id != 0) {
                    //add to form
                    name.setText(it.name.toString())
                    email.setText(it.email.toString())
                    phone.setText(it.phone.toString())
                    location.setText(it.location.toString())
                    if (it.profilePic != null) {
                        Picasso.get().load(it.profilePic).into(profile)
                    }
                }
            }
        } catch (e: Exception) {
            Toast.makeText(view.context, "Erro ao carregar dados do utilizador", Toast.LENGTH_SHORT)
                .show()
        }

    }

    override fun onActivityResult(requestCode: Int, resultCode: Int, data: Intent?) {
        val permissions = arrayOf(
            Manifest.permission.READ_EXTERNAL_STORAGE,
            Manifest.permission.WRITE_EXTERNAL_STORAGE
        )
        super.onActivityResult(requestCode, resultCode, data)
        if (ActivityCompat.checkSelfPermission(
                requireActivity(),
                Manifest.permission.READ_EXTERNAL_STORAGE
            ) != PackageManager.PERMISSION_GRANTED
        ) {
            ActivityCompat.requestPermissions(requireActivity(), permissions, 1)
        } else {
            if (requestCode == AddFragment.IMAGE_PICK_REQUEST_CODE && resultCode == Activity.RESULT_OK) {
                val selectedImageUri = data?.data
                // Handle the selected image URI here
                val profile = viewCopy.findViewById<ImageView>(R.id.user_profile)
                profile.setImageURI(selectedImageUri)
                imageUri = selectedImageUri!!
            }
        }
    }

    companion object {
        val IMAGE_PICK_REQUEST_CODE = 1

        @JvmStatic
        fun newInstance(param1: String, param2: String) =
            AccountFragment().apply {
                arguments = Bundle().apply {
                    putString(ARG_PARAM1, param1)
                    putString(ARG_PARAM2, param2)
                }
            }
    }
}