package com.jmm.brsap.norogi

import android.graphics.Paint
import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.recyclerview.widget.RecyclerView
import com.jmm.brsap.norogi.databinding.TemplateProductsBinding
import com.jmm.brsap.norogi.models.ProductModel

class ProductsAdapter(val productsInterface: ProductsInterface):RecyclerView.Adapter<ProductsAdapter.ProductsViewHolder>() {

    private val products = mutableListOf<ProductModel>()

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): ProductsViewHolder {
        return ProductsViewHolder(TemplateProductsBinding.inflate(LayoutInflater.from(parent.context),parent,false),productsInterface)
    }

    override fun onBindViewHolder(holder: ProductsViewHolder, position: Int) {
        holder.bindViews(products[position])
    }

    override fun getItemCount(): Int {
        return products.size
    }

    fun setproductsItems(products:List<ProductModel>){
        this.products.clear()
        this.products.addAll(products)
        notifyDataSetChanged()
    }

    inner class ProductsViewHolder(val binding:TemplateProductsBinding,val mListener:ProductsInterface):RecyclerView.ViewHolder(binding.root){

        init {
            itemView.setOnClickListener {
                mListener.onProductClick(products.get(adapterPosition))
            }
        }

        fun bindViews(item:ProductModel){
            binding.apply {
                imageView.loadImage(item.imageUrl)
                textView.text  = item.title
                tvPrice.text = "₹${item.price}.00"
                tvOldPrice.text = "₹${item.oldPrice}.00"
                binding.tvOldPrice.paintFlags =
                    binding.tvOldPrice.paintFlags or Paint.STRIKE_THRU_TEXT_FLAG
            }
        }
    }
    interface ProductsInterface{
        fun onProductClick(item: ProductModel)
    }


}