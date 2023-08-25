package com.example.marketplace.View.Fragments

import android.app.Activity
import android.content.Context
import android.content.Intent
import android.net.Uri
import android.os.Bundle
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.*
import androidx.core.view.forEach
import com.example.marketplace.Model.Category
import com.example.marketplace.Model.Product
import com.example.marketplace.R
import com.example.marketplace.Repository.CategoryRepository
import com.example.marketplace.Repository.ProductRepository
import com.example.marketplace.View.Fragments.AccountFragment.Companion.IMAGE_PICK_REQUEST_CODE
import uploadFileToFirebaseStorage

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
    private lateinit var selectedCategory:String
    private lateinit var coverUrl:Uri

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
        getCategoryList(){
            val adapter = ArrayAdapter(view.context,android.R.layout.simple_spinner_dropdown_item,it)
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
                    val selectedItem = it[position].toString()
                    if (selectedItem != "Select Category") {
                        selectedCategory = selectedItem
                    }
                }

                override fun onNothingSelected(parent: AdapterView<*>?) {
                    // Do nothing
                }
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

        //get data from user's product so that it gets updated
        val data = arguments?.getString("id")
        if(data != null){
            val title = view.findViewById<EditText>(R.id.product_title)
            val price = view.findViewById<EditText>(R.id.product_price)
            val location = view.findViewById<EditText>(R.id.product_location)
            val description = view.findViewById<EditText>(R.id.product_desc)
            val btnFile = view.findViewById<Button>(R.id.btn_file)
            val spinner = view.findViewById<Spinner>(R.id.spinner_category)

            getProductOne(data.toInt()){
                title.setText(it.title)
                price.setText(it.price.toString())
                location.setText(it.location)
                description.setText(it.description)
                btnFile.setText("EDITAR PRODUTO")

                getCategoryList {  cat ->
                    var i = 0
                    cat.forEach {  item ->
                        if(item.id == it.categoryId){
                            spinner.setSelection(i)
                            true
                        }
                        i++
                    }
                }

            }

        }

        //publicar file
        val btnFile = view.findViewById<Button>(R.id.btn_file)

        btnFile.setOnClickListener {
            val title = view.findViewById<EditText>(R.id.product_title)
            val price = view.findViewById<EditText>(R.id.product_price)
            val location = view.findViewById<EditText>(R.id.product_location)
            val description = view.findViewById<EditText>(R.id.product_desc)
            val productFileText = view.findViewById<TextView>(R.id.product_file_text)
            val storage = requireContext().getSharedPreferences("user", Context.MODE_PRIVATE)
            val userId = storage.getInt("id",0)
            btnFile.setText("CARREGANDO...")

            if(title.text.toString() == ""
                || price.text.toString() == ""
                || location.text.toString() == ""
                || description.text.toString() == ""
                    ){
                btnFile.setText("PUBLICAR PRODUTO")
                Toast.makeText(view.context,"Por favor preencha todos os dados!",Toast.LENGTH_SHORT).show()
            }else {

                val categoryRepo = CategoryRepository()

                categoryRepo.getCategoryId(selectedCategory) { category ->

                    if (::coverUrl.isInitialized) {

                        uploadFileToFirebaseStorage(coverUrl) { url ->
                            val product = Product(
                                0,
                                userId, title.text.toString(),
                                description.text.toString(),
                                price.text.toString().toDouble(),
                                url.toString(),
                                location.text.toString(),
                                1,
                                category.id
                            )

                            if(data != null){
                                updateProduct(data.toInt(),product){
                                    if(it?.id != 0){
                                        btnFile.setText("EDITAR PRODUTO")
                                        Toast.makeText(view.context,"Produto atualizado com sucesso!",Toast.LENGTH_SHORT).show()
                                       requireActivity().finish()
                                    }else{
                                        btnFile.setText("EDITAR PRODUTO")
                                        Toast.makeText(view.context,"Erro ao atualizar produto",Toast.LENGTH_SHORT).show()
                                    }
                                }
                            }else{
                                addNewProduct(product){
                                    if(it?.id == 0){
                                        Toast.makeText(view.context,"Houve um erro, Volte a tentar mais tarde",Toast.LENGTH_SHORT).show()
                                    }else{
                                        btnFile.setText("PUBLICAR PRODUTO")
                                        title.setText("")
                                        location.setText("")
                                        price.setText("")
                                        description.setText("")
                                        productFileText.setText("")
                                        Toast.makeText(view.context,"Produto inserido com sucesso!",Toast.LENGTH_SHORT).show()
                                    }
                                }
                            }

                        }

                    } else {

                        val product = Product(
                            0,
                            userId, title.text.toString(),
                            description.text.toString(),
                            price.text.toString().toDouble(),
                            "",
                            location.text.toString(),
                            1,
                            category.id
                        )
                        if(data != null){
                            updateProduct(data.toInt(),product){
                                if(it?.id != 0){
                                    btnFile.setText("EDITAR PRODUTO")
                                    Toast.makeText(view.context,"Produto atualizado com sucesso!",Toast.LENGTH_SHORT).show()
                                    requireActivity().finish()
                                }else{
                                    btnFile.setText("EDITAR PRODUTO")
                                    Toast.makeText(view.context,"Erro ao atualizar produto",Toast.LENGTH_SHORT).show()
                                }
                            }
                        }else{
                            addNewProduct(product){
                                if(it?.id == 0){
                                    Toast.makeText(view.context,"Houve um erro, Volte a tentar mais tarde",Toast.LENGTH_SHORT).show()
                                }else{
                                    btnFile.setText("PUBLICAR PRODUTO")
                                    title.setText("")
                                    location.setText("")
                                    price.setText("")
                                    description.setText("")
                                    productFileText.setText("")
                                    Toast.makeText(view.context,"Produto inserido com sucesso!",Toast.LENGTH_SHORT).show()
                                }
                            }
                        }
                    }

                }
            }

        }

        return view
    }



    private fun getCategoryList(callback: (List<Category>) -> Unit){
        val service = CategoryRepository()
        service.getCategoryAll{
            callback(it)
        }
    }

    private fun updateProduct(id:Int, product:Product, callback: (Product) -> Unit){
       try{
           val productRepository = ProductRepository()
           productRepository.updateProduct(id,product){
                if(it?.error != null){
                    callback(Product())
                }else{
                    callback(it)
                }
           }
       }catch(e:Exception){
           callback(Product())
       }

    }

    private fun getProductOne(id:Int, callback: (Product) -> Unit){
       try{
           val service = ProductRepository()
           service.getOneProduct(id){
                if(it?.error != null){
                    callback(Product())
                }else{
                    callback(it)
                }
           }
       }catch(e:Exception){
           callback(Product())
       }

    }
    private fun  addNewProduct(product: Product, callback:(Product) -> Unit){
        try{
            val productRepository = ProductRepository()
            productRepository.createProduct(product){
                if(it?.error != null){
                    callback(Product())
                }else{
                    callback(it)
                }
            }
        }catch(e:Exception){
            callback(Product())
        }
    }

    override fun onActivityResult(requestCode: Int, resultCode: Int, data: Intent?) {
        super.onActivityResult(requestCode, resultCode, data)
        if (requestCode == IMAGE_PICK_REQUEST_CODE && resultCode == Activity.RESULT_OK) {
            val selectedImageUri = data?.data
            // Handle the selected image URI here
            coverUrl = selectedImageUri!!
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