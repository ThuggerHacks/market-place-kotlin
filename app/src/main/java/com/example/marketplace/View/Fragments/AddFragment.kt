package com.example.marketplace.View.Fragments

import android.app.Activity
import android.content.Intent
import android.os.Bundle
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.AdapterView
import android.widget.ArrayAdapter
import android.widget.LinearLayout
import android.widget.Spinner
import android.widget.TextView
import android.widget.Toast
import com.example.marketplace.R

// TODO: Rename parameter arguments, choose names that match
// the fragment initialization parameters, e.g. ARG_ITEM_NUMBER
private const val ARG_PARAM1 = "param1"
private const val ARG_PARAM2 = "param2"

/**
 * A simple [Fragment] subclass.
 * Use the [AddFragment.newInstance] factory method to
 * create an instance of this fragment.
 */
class AddFragment : Fragment() {
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
        val view = inflater.inflate(R.layout.fragment_add, container, false)
        val spinner = view.findViewById<Spinner>(R.id.spinner_category)
        val list = arrayOf("Telefones","Mobilia","Viaturas","Comida","Bebida")
        val adapter = ArrayAdapter(view.context,android.R.layout.simple_spinner_dropdown_item,list)
        adapter.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item)
        spinner.adapter = adapter

        // Set a listener to handle item selection changes
        spinner.onItemSelectedListener = object : AdapterView.OnItemSelectedListener {
            override fun onItemSelected(
                parent: AdapterView<*>?,
                view: View?,
                position: Int,
                id: Long
            ) {
                val selectedItem = list[position]
                if (selectedItem != "Select Category") {
                    // Do something with the selected item
                    // For example, you can update a TextView with the selected item
                    // textView.text = selectedItem
                    Toast.makeText(view?.context,selectedItem,Toast.LENGTH_SHORT).show()
                }
            }

            override fun onNothingSelected(parent: AdapterView<*>?) {
                // Do nothing
            }
        }

        val fileBtn = view.findViewById<LinearLayout>(R.id.product_file_container)
        fileBtn.setOnClickListener {
            val intent = Intent(Intent.ACTION_PICK)
            intent.type = "image/*"  // Set the content type to images
            val chooser = Intent.createChooser(intent, "Escolha uma imagem")
            startActivityForResult(chooser, IMAGE_PICK_REQUEST_CODE)
            viewCopy = view
        }

        val data = arguments?.getString("id")
        if(data != null){
            Toast.makeText(view.context,data,Toast.LENGTH_SHORT).show()
        }


        return view
    }

    override fun onActivityResult(requestCode: Int, resultCode: Int, data: Intent?) {
        super.onActivityResult(requestCode, resultCode, data)
        if (requestCode == IMAGE_PICK_REQUEST_CODE && resultCode == Activity.RESULT_OK) {
            val selectedImageUri = data?.data
            // Handle the selected image URI here
           val fileText = viewCopy.findViewById<TextView>(R.id.product_file_text)
            fileText.setText(selectedImageUri.toString())
        }
    }

    companion object {
       val  IMAGE_PICK_REQUEST_CODE = 1
        @JvmStatic
        fun newInstance(param1: String, param2: String) =
            AddFragment().apply {
                arguments = Bundle().apply {
                    putString(ARG_PARAM1, param1)
                    putString(ARG_PARAM2, param2)
                }
            }
    }
}