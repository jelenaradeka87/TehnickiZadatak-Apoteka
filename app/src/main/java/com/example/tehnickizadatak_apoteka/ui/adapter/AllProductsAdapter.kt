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
import com.example.tehnickizadatak_apoteka.data.model.ProductResponse

class AllProductsAdapter(
    private val onItemClick: (ProductResponse) -> Unit = {},
    private val onFavoriteClick: (ProductResponse) -> Unit = {}
) : ListAdapter<ProductResponse, AllProductsAdapter.VH>(Diff) {

    object Diff : DiffUtil.ItemCallback<ProductResponse>() {
        override fun areItemsTheSame(oldItem: ProductResponse, newItem: ProductResponse): Boolean =
            oldItem.id == newItem.id

        override fun areContentsTheSame(oldItem: ProductResponse, newItem: ProductResponse): Boolean =
            oldItem == newItem
    }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): VH {
        val view = LayoutInflater.from(parent.context)
            .inflate(R.layout.item_product, parent, false)

        return VH(view, onItemClick, onFavoriteClick)
    }

    override fun onBindViewHolder(holder: VH, position: Int) {
        holder.bind(getItem(position))
    }

    class VH(
        itemView: View,
        private val onItemClick: (ProductResponse) -> Unit,
        private val onFavoriteClick: (ProductResponse) -> Unit
    ) : RecyclerView.ViewHolder(itemView) {

        private val ivThumb: ImageView = itemView.findViewById(R.id.ivThumbnail)
        private val tvName: TextView = itemView.findViewById(R.id.tvName)
        private val tvDescription: TextView = itemView.findViewById(R.id.tvBrand)
        private val tvPrice: TextView = itemView.findViewById(R.id.tvPrice)
        private val tvRating: TextView = itemView.findViewById(R.id.tvRating)
        private val btnFavorite: Button = itemView.findViewById(R.id.btnFavorite)

        fun bind(item: ProductResponse) {
            tvName.text = item.title
            tvDescription.text = item.brand
            tvPrice.text = "$${String.format("%.2f", item.price)}"
            tvRating.text = String.format("%.1f", item.rating)
            btnFavorite.visibility = View.VISIBLE

            Glide.with(itemView).clear(ivThumb)
            ivThumb.setImageDrawable(null)

            Glide.with(itemView)
                .load(item.thumbnail)
                .placeholder(android.R.color.darker_gray)
                .error(android.R.drawable.ic_menu_report_image)
                .centerCrop()
                .into(ivThumb)

            itemView.setOnClickListener {
                onItemClick(item)
            }

            btnFavorite.setOnClickListener {
                onFavoriteClick(item)
            }
        }
    }
}
