package com.example.demo.Adapters

import android.content.Intent
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.ImageView
import android.widget.TextView
import androidx.appcompat.view.menu.MenuView.ItemView
import androidx.recyclerview.widget.RecyclerView
import com.example.demo.Modal.DummyProducts
import com.example.demo.Modal.Product
import com.example.demo.R
import com.example.demo.TabActivity
import com.squareup.picasso.Picasso
import kotlinx.coroutines.withContext

class ProductsAdapter(private val products: DummyProducts, val productCB: (product: Product) -> Unit) :
    RecyclerView.Adapter<ProductsAdapter.ViewHolder>() {




    private val intent: Intent? = null

    class ViewHolder(itemView: View) : RecyclerView.ViewHolder(itemView) {
        val productsImg: ImageView = itemView.findViewById(R.id.products_img)
        val productsName: TextView = itemView.findViewById(R.id.products_name)
        val productsPrice: TextView = itemView.findViewById(R.id.products_price)


    }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): ViewHolder {
        val view: View =
            LayoutInflater.from(parent.context).inflate(R.layout.products_rv, parent, false)
        return ViewHolder(view)
    }

    override fun getItemCount(): Int {
        return products.products.size
    }

    override fun onBindViewHolder(holder: ViewHolder, position: Int) {
        val productsModal = products.products[position]
        holder.productsName.text = productsModal.title
        holder.productsPrice.text = productsModal.price.toString()
        Picasso.get().load(productsModal.images[0]).into(holder.productsImg)

        holder.itemView.setOnClickListener {
//            val intent = Intent(holder.itemView.context, TabActivity::class.java)
//
//            holder.itemView.context.startActivity(intent)

            productCB(productsModal)
        }
    }
}