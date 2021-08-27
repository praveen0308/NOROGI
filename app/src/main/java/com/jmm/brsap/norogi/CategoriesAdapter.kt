package com.jmm.brsap.norogi

import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.recyclerview.widget.RecyclerView
import com.jmm.brsap.norogi.databinding.TemplateCategoriesBinding
import com.jmm.brsap.norogi.models.CategoryModel

class CategoriesAdapter(val categoriesInterface: CategoriesInterface):RecyclerView.Adapter<CategoriesAdapter.CategoriesViewHolder>() {

    private val categories = mutableListOf<CategoryModel>()

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): CategoriesViewHolder {
        return CategoriesViewHolder(TemplateCategoriesBinding.inflate(LayoutInflater.from(parent.context),parent,false),categoriesInterface)
    }

    override fun onBindViewHolder(holder: CategoriesViewHolder, position: Int) {
        holder.bindViews(categories[position])
    }

    override fun getItemCount(): Int {
        return categories.size
    }

    fun setCategoriesItems(categories:List<CategoryModel>){
        this.categories.clear()
        this.categories.addAll(categories)
        notifyDataSetChanged()
    }

    inner class CategoriesViewHolder(val binding:TemplateCategoriesBinding,val mListener:CategoriesInterface):RecyclerView.ViewHolder(binding.root){

        init {
            itemView.setOnClickListener {
                mListener.onCategoryClick(categories.get(adapterPosition))
            }
        }

        fun bindViews(item:CategoryModel){
            binding.apply {
                imageView.loadImage(item.imageUrl)
                textView.text  = item.name
            }
        }
    }
    interface CategoriesInterface{
        fun onCategoryClick(item: CategoryModel)
    }


}