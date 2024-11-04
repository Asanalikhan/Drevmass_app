package com.example.drevmassapp.presentation.product

import android.os.Bundle
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.viewModels
import com.bumptech.glide.Glide
import com.example.drevmassapp.R
import com.example.drevmassapp.data.remote.ServiceBuilder
import com.example.drevmassapp.databinding.FragmentProductBinding
import dagger.hilt.android.AndroidEntryPoint

@AndroidEntryPoint
class ProductFragment : Fragment() {

    private lateinit var _binding: FragmentProductBinding
    private val binding get() = _binding
    private val viewModel: ProductViewModel by viewModels()

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {
        _binding = FragmentProductBinding.inflate(layoutInflater, container, false)
        return binding.root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        binding.toolbar.icBtnBack.setOnClickListener {
            activity?.onBackPressed()
        }

        binding.toolbar.icBtnInfo.setImageResource(R.drawable.ic_share_24)
        binding.toolbar.tvToolbarTitle.text = ""

        viewModel.getProductsById(14)

        viewModel.productById.observe(viewLifecycleOwner) { products ->
            binding.tvTitle.text = products[0].product.title
            binding.tvPrice.text = products[0].product.price.toString()
            binding.tvDescription.text = products[0].product.description
            binding.tvSize.text = products[0].product.size
            binding.tvWeight.text = products[0].product.height
            Glide.with(binding.ivProduct.context)
                .load(ServiceBuilder.getUrl() + products[0].product.imageSrc)
                .into(binding.ivProduct)
        }

    }

}