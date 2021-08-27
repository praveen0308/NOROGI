package com.jmm.brsap.norogi

import android.os.Bundle
import android.util.Log
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.navigation.fragment.findNavController
import androidx.recyclerview.widget.GridLayoutManager
import com.google.firebase.firestore.ktx.firestore
import com.google.firebase.ktx.Firebase
import com.jmm.brsap.norogi.databinding.FragmentCategoriesBinding
import com.jmm.brsap.norogi.databinding.FragmentCategoryProductsBinding
import com.jmm.brsap.norogi.models.CategoryModel
import com.jmm.brsap.norogi.models.ProductModel


class CategoryProducts : Fragment(), ProductsAdapter.ProductsInterface {

    private var _binding: FragmentCategoryProductsBinding? = null
    private val binding get() = _binding!!

    val db = Firebase.firestore

    private lateinit var productsAdapter: ProductsAdapter

    private val TAG = "CategoriyProductsFragment"
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)

    }

    override fun onDestroyView() {
        super.onDestroyView()
        _binding = null
    }
    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {
        _binding = FragmentCategoryProductsBinding.inflate(inflater, container, false)
        return binding.root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        setupRvCategories()
        populateCategories()

        (requireActivity() as MainActivity).binding.toolbar.setTitle("Products")
        (requireActivity() as MainActivity).binding.toolbar.setNavigationOnClickListener {
            findNavController().navigateUp()
        }

    }

    private fun populateCategories(){
        val products = mutableListOf<ProductModel>()
        db.collection("products")
            .get()
            .addOnSuccessListener { result ->
                for (document in result) {
                    products.add(
                        ProductModel(
                            productId = document["id"].toString(),
                            title = document["title"] as String,
                            imageUrl = document["imageUrl"] as String,
                            price = document["price"] as String,
                            oldPrice = document["oldPrice"] as String,

                        )
                    )
                    Log.d(TAG, "${document.id} => ${document.data}")
                }
            }
            .addOnFailureListener { exception ->
                Log.w(TAG, "Error getting documents.", exception)
            }.addOnCompleteListener {
                productsAdapter.setproductsItems(products)
            }


    }

    private fun setupRvCategories(){
        productsAdapter = ProductsAdapter(this)
        binding.rvProducts.apply {
            setHasFixedSize(true)
            layoutManager = GridLayoutManager(context,2)
            adapter = productsAdapter
        }
    }





    override fun onProductClick(item: ProductModel) {
        findNavController().navigate(R.id.action_categoryProducts_to_productDetails)
    }


}