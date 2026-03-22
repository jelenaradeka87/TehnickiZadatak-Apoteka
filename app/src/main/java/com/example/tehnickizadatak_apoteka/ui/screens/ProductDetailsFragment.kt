package com.example.tehnickizadatak_apoteka.ui.screens

import android.os.Bundle
import android.util.Log
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.ImageView
import android.widget.ProgressBar
import android.widget.TextView
import androidx.fragment.app.Fragment
import androidx.fragment.app.viewModels
import androidx.lifecycle.Lifecycle
import androidx.lifecycle.lifecycleScope
import androidx.lifecycle.repeatOnLifecycle
import com.bumptech.glide.Glide
import com.example.tehnickizadatak_apoteka.R
import com.example.tehnickizadatak_apoteka.ui.viewModel.ProductDetailsViewModel
import com.example.tehnickizadatak_apoteka.utils.Resource
import dagger.hilt.android.AndroidEntryPoint
import kotlinx.coroutines.launch

@AndroidEntryPoint
class ProductDetailsFragment : Fragment() {

    private val viewModel: ProductDetailsViewModel by viewModels()

    private lateinit var ivImage: ImageView
    private lateinit var tvName: TextView
    private lateinit var tvDescription: TextView
    private lateinit var tvPrice: TextView
    private lateinit var progressBar: ProgressBar
    private lateinit var tvError: TextView

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        return inflater.inflate(R.layout.fragment_product_details, container, false)
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        ivImage = view.findViewById(R.id.ivImage)
        tvName = view.findViewById(R.id.tvName)
        tvDescription = view.findViewById(R.id.tvDescription)
        tvPrice = view.findViewById(R.id.tvPrice)
        progressBar = view.findViewById(R.id.progressBar)
        tvError = view.findViewById(R.id.tvError)

        val productId = arguments?.getInt("productId")
        if (productId != null) {
            viewModel.loadProduct(productId)
        }

        viewLifecycleOwner.lifecycleScope.launch {
            viewLifecycleOwner.repeatOnLifecycle(Lifecycle.State.STARTED) {
                viewModel.uiState.collect { state ->
                    when (state) {
                        is Resource.Loading -> {
                            progressBar.visibility = View.VISIBLE
                            tvError.visibility = View.GONE
                        }

                        is Resource.Success -> {
                            progressBar.visibility = View.GONE
                            tvError.visibility = View.GONE
                            val p = state.data
                            tvName.visibility = View.VISIBLE
                            tvDescription.visibility = View.VISIBLE
                            tvPrice.visibility = View.VISIBLE
                            ivImage.visibility = View.VISIBLE
                            tvName.text = p.title
                            tvDescription.text = p.description
                            tvPrice.text = "$${String.format("%.2f", p.price)}"

                            Glide.with(this@ProductDetailsFragment).clear(ivImage)
                            ivImage.setImageDrawable(null)

                            Glide.with(this@ProductDetailsFragment)
                                .load(p.thumbnail.ifEmpty { p.images.firstOrNull() })
                                .thumbnail(0.1f)
                                .placeholder(android.R.color.transparent)
                                .error(android.R.drawable.ic_menu_report_image)
                                .transition(com.bumptech.glide.load.resource.drawable.DrawableTransitionOptions.withCrossFade())
                                .into(ivImage)
                        }

                        is Resource.Error -> {
                            progressBar.visibility = View.GONE
                            tvError.visibility = View.VISIBLE
                            tvError.text = state.message
                            tvName.visibility = View.GONE
                            tvDescription.visibility = View.GONE
                            tvPrice.visibility = View.GONE
                            ivImage.visibility = View.GONE
                            Log.e("ProductDetailsFragment",
                                "Error loading product details: ${state.message}", state.throwable)
                        }
                    }
                }
            }
        }
    }
}