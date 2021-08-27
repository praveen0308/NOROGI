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
import com.jmm.brsap.norogi.models.CategoryModel


class Categories : Fragment(), CategoriesAdapter.CategoriesInterface {
    private var _binding: FragmentCategoriesBinding? = null
    private val binding get() = _binding!!

    val db = Firebase.firestore

    private lateinit var categoriesAdapter: CategoriesAdapter

    private val TAG = "CategoriesFragment"
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
        _binding = FragmentCategoriesBinding.inflate(inflater, container, false)
        return binding.root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        setupRvCategories()
        populateCategories()
        (requireActivity() as MainActivity).binding.toolbar.setTitle("Categories")
    }

    private fun populateCategories(){
        val categories = mutableListOf<CategoryModel>()
        db.collection("categories")
            .get()
            .addOnSuccessListener { result ->
                for (document in result) {
                    categories.add(
                        CategoryModel(
                        categoryId = document["id"].toString(),
                        name = document["name"] as String,
                        imageUrl = document["imageUrl"] as String
                    ))
                    Log.d(TAG, "${document.id} => ${document.data}")
                }
            }
            .addOnFailureListener { exception ->
                Log.w(TAG, "Error getting documents.", exception)
            }.addOnCompleteListener {
                categoriesAdapter.setCategoriesItems(categories)
            }


    }

    private fun setupRvCategories(){
        categoriesAdapter = CategoriesAdapter(this)
        binding.rvCategories.apply {
            setHasFixedSize(true)
            layoutManager = GridLayoutManager(context,3)
            adapter = categoriesAdapter
        }
    }


    override fun onCategoryClick(item: CategoryModel) {
        findNavController().navigate(R.id.action_categories_to_categoryProducts)
    }




}