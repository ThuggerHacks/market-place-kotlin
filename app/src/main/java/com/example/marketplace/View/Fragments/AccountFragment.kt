package com.example.marketplace.View.Fragments

import android.app.Activity
import android.content.Intent
import android.os.Bundle
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.ImageView
import android.widget.LinearLayout
import android.widget.TextView
import com.example.marketplace.R
import com.example.marketplace.View.MyProductActivity

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
    private lateinit var viewCopy:View
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
            val intent = Intent(requireContext(),MyProductActivity::class.java)
            requireContext().startActivity(intent)
        }
        return view
    }

    override fun onActivityResult(requestCode: Int, resultCode: Int, data: Intent?) {
        super.onActivityResult(requestCode, resultCode, data)
        if (requestCode == AddFragment.IMAGE_PICK_REQUEST_CODE && resultCode == Activity.RESULT_OK) {
            val selectedImageUri = data?.data
            // Handle the selected image URI here
            val profile = viewCopy.findViewById<ImageView>(R.id.user_profile)
            profile.setImageURI(selectedImageUri)
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