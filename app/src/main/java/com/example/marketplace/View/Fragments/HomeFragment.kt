package com.example.marketplace.View.Fragments

import android.content.Intent
import android.os.Bundle
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.Button
import android.widget.EditText
import android.widget.LinearLayout
import android.widget.Toast
import androidx.recyclerview.widget.GridLayoutManager
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView
import com.example.marketplace.Adapter.CategoryListAdapter
import com.example.marketplace.Adapter.Listeners.OnCategoryClickListener
import com.example.marketplace.Adapter.Listeners.OnProductClickListener
import com.example.marketplace.Adapter.ProductListAdapter
import com.example.marketplace.Model.Category
import com.example.marketplace.Model.Product
import com.example.marketplace.R
import com.example.marketplace.Repository.CategoryRepository
import com.example.marketplace.Repository.ProductRepository
import com.example.marketplace.View.MainActivity
import com.example.marketplace.View.ProductActivity
import kotlinx.coroutines.Dispatchers.Main

// TODO: Rename parameter arguments, choose names that match
// the fragment initialization parameters, e.g. ARG_ITEM_NUMBER
private const val ARG_PARAM1 = "param1"
private const val ARG_PARAM2 = "param2"

/**
 * A simple [Fragment] subclass.
 * Use the [HomeFragment.newInstance] factory method to
 * create an instance of this fragment.
 */
class HomeFragment : Fragment() {
    // TODO: Rename and change types of parameters
    private var param1: String? = null
    private var param2: String? = null
    private lateinit var categoryAdapter:CategoryListAdapter
    private lateinit var categoryList:ArrayList<Category>
    private lateinit var adapter: ProductListAdapter
    private lateinit var loader:LinearLayout

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
        val view = inflater.inflate(R.layout.fragment_home, container, false)

        loader = view.findViewById<LinearLayout>(R.id.loader_layout)

        val recyclerView = view.findViewById<RecyclerView>(R.id.recycler_view)
        getAllProducts{
            adapter = ProductListAdapter(it, OnProductClickListener {
                try {
                    val i = Intent(requireContext(), ProductActivity::class.java)
                    i.putExtra("productId", it.id.toString())
                    requireContext().startActivity(i)
                } catch (e: Exception) {
                    e.printStackTrace()
                }
            })
            loader.visibility = View.GONE

            recyclerView.layoutManager = GridLayoutManager(view.context,2)
            recyclerView.adapter = adapter
        }

        //categoryList
        val category = view.findViewById<androidx.recyclerview.widget.RecyclerView>(R.id.category_list)
        loadCategoryList(category, view)

        //search

        val btnOk = view.findViewById<Button>(R.id.ok_btn)
        val searchInput = view.findViewById<EditText>(R.id.search_input)

        btnOk.setOnClickListener {
            loader.visibility = View.VISIBLE
            val title = searchInput.text.toString()
            ProductRepository().searchProduct(title){
                adapter.updateProductList(it)
                loader.visibility = View.GONE
            }
        }
        return view
    }

    private fun getAllProducts(callback: (List<Product>) -> Unit){
        val service = ProductRepository()
        service.getAll {
            callback(it)
        }
    }

    private fun loadCategoryList(category:androidx.recyclerview.widget.RecyclerView, view:View){
        getCategoryList {

            categoryAdapter = CategoryListAdapter(it, OnCategoryClickListener {
                loader.visibility = View.VISIBLE
                ProductRepository().getProductByCategoryName(it.category_name){
                    adapter.updateProductList(it)
                    loader.visibility = View.GONE
                }
            })

            category.layoutManager = LinearLayoutManager(view.context, RecyclerView.HORIZONTAL,false)
            category.adapter = categoryAdapter
        }

    }

    private fun getCategoryList(callback: (List<Category>) -> Unit){
        val service = CategoryRepository()
        service.getCategoryAll{
            callback(it)
        }
    }

    companion object {
        /**
         * Use this factory method to create a new instance of
         * this fragment using the provided parameters.
         *
         * @param param1 Parameter 1.
         * @param param2 Parameter 2.
         * @return A new instance of fragment HomeFragment.
         */
        // TODO: Rename and change types and number of parameters
        @JvmStatic
        fun newInstance(param1: String, param2: String) =
            HomeFragment().apply {
                arguments = Bundle().apply {
                    putString(ARG_PARAM1, param1)
                    putString(ARG_PARAM2, param2)
                }
            }
    }
}