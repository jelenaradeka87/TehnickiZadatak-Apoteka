package com.example.tehnickizadatak_apoteka.ui.adapter

import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.Button
import android.widget.ImageView
import android.widget.TextView
import androidx.recyclerview.widget.DiffUtil
import androidx.recyclerview.widget.ListAdapter
import androidx.recyclerview.widget.RecyclerView
import com.bumptech.glide.Glide
import com.example.tehnickizadatak_apoteka.R
import com.example.tehnickizadatak_apoteka.data.local.entity.ProductEntity

class FavoriteProductAdapter(
    private val onItemClick: (ProductEntity) -> Unit = {}
) : ListAdapter<ProductEntity, FavoriteProductAdapter.VH>(Diff) {

    object Diff : DiffUtil.ItemCallback<ProductEntity>() {
        override fun areItemsTheSame(oldItem: ProductEntity, newItem: ProductEntity): Boolean =
            oldItem.id == newItem.id

        override fun areContentsTheSame(oldItem: ProductEntity, newItem: ProductEntity): Boolean =
            oldItem == newItem
    }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): VH {
        val view = LayoutInflater.from(parent.context).inflate(R.layout.item_product, parent, false)
        return VH(view, onItemClick)
    }

    override fun onBindViewHolder(holder: VH, position: Int) {
        holder.bind(getItem(position))
    }

    class VH(itemView: View, private val onItemClick: (ProductEntity) -> Unit) : RecyclerView.ViewHolder(itemView) {
        private val ivThumb: ImageView = itemView.findViewById(R.id.ivThumbnail)
        private val tvName: TextView = itemView.findViewById(R.id.tvName)
        private val tvDescription: TextView = itemView.findViewById(R.id.tvBrand)
        private val tvPrice: TextView = itemView.findViewById(R.id.tvPrice)
        private val tvRating: TextView = itemView.findViewById(R.id.tvRating)

        fun bind(item: ProductEntity) {
            tvName.text = item.title
            tvDescription.text = item.brand
            tvPrice.text = "$${String.format("%.2f", item.price)}"
            tvRating.text = String.format("%.1f", item.rating)

            Glide.with(itemView).clear(ivThumb)
            ivThumb.setImageDrawable(null)

            Glide.with(itemView)
                .load(item.thumbnail)
                .placeholder(android.R.color.darker_gray)
                .error(android.R.drawable.ic_menu_report_image)
                .centerCrop()
                .into(ivThumb)

            itemView.setOnClickListener { onItemClick(item) }
        }
    }
}
