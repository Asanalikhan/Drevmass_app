package com.example.drevmassapp.presentation.product

import android.content.Intent
import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.Fragment
import androidx.fragment.app.viewModels
import androidx.navigation.fragment.findNavController
import androidx.navigation.fragment.navArgs
import com.bumptech.glide.Glide
import com.example.drevmassapp.R
import com.example.drevmassapp.data.remote.ServiceBuilder
import com.example.drevmassapp.databinding.FragmentProductBinding
import com.example.drevmassapp.domain.model.ProductByIdResponse
import com.example.drevmassapp.domain.repository.OnItemClickListener
import com.example.drevmassapp.presentation.activity.MainActivity
import com.example.drevmassapp.presentation.basket.BasketViewModel
import com.example.drevmassapp.presentation.catalog.CatalogAdapter
import com.example.drevmassapp.utils.GridSpacingItemDecoration
import com.example.drevmassapp.utils.provideNavigationHos
import dagger.hilt.android.AndroidEntryPoint

@AndroidEntryPoint
class ProductFragment : Fragment() {

    private lateinit var _binding: FragmentProductBinding
    private val binding get() = _binding
    private val viewModel: ProductViewModel by viewModels()
    private val viewModelBasket: BasketViewModel by viewModels()
    private lateinit var productAdapter: CatalogAdapter
    private val args by navArgs<ProductFragmentArgs>()
    private var title = ""
    private var count: Int = 0

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {
        _binding = FragmentProductBinding.inflate(layoutInflater, container, false)
        return binding.root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        provideNavigationHos()?.apply {
            setNavigationVisibility(false)
        }

        binding.toolbar.icBtnBack.setOnClickListener {
            activity?.onBackPressed()
        }

        setupViews()

        viewModel.getProductsById(args.id)

        viewModel.productById.observe(viewLifecycleOwner) { products ->
            binding.tvTitle.text = products[0].product.title
            binding.tvPrice.text = "${products[0].product.price.formatWithSpaces()} ₽"
            binding.tvDescription.text = products[0].product.description
            binding.tvSize.text = products[0].product.size
            binding.tvWeight.text = products[0].product.height
            Glide.with(binding.ivProduct.context)
                .load(ServiceBuilder.getUrl() + products[0].product.imageSrc)
                .into(binding.ivProduct)
            productAdapter.setProducts(products[0].recommend)
            count = products[0].product.basketCount
            binding.tvButtonPriceFixed.text = "${products[0].product.price.formatWithSpaces()} ₽"
            setupBasketView(products[0].product)
        }

        binding.includeBasket.btnPlus.setOnClickListener {
            viewModelBasket.increaseBasket(count, args.id, 1, true)
            viewModelBasket.basketUpdated.observe(viewLifecycleOwner) {
                viewModel.getProductsById(args.id)
            }
        }
        binding.includeBasket.btnMinus.setOnClickListener {
            viewModelBasket.increaseBasket(count, args.id, 1, false)
            viewModelBasket.basketUpdated.observe(viewLifecycleOwner) {
                viewModel.getProductsById(args.id)
            }
        }
        binding.includeBasketFixed.btnPlus.setOnClickListener {
            viewModelBasket.increaseBasket(count, args.id, 1, true)
            viewModelBasket.basketUpdated.observe(viewLifecycleOwner) {
                viewModel.getProductsById(args.id)
            }
        }
        binding.includeBasketFixed.btnMinus.setOnClickListener {
            viewModelBasket.increaseBasket(count, args.id, 1, false)
            viewModelBasket.basketUpdated.observe(viewLifecycleOwner) {
                viewModel.getProductsById(args.id)
            }
        }
        binding.btnBasket.setOnClickListener {
            viewModelBasket.addBasket(1, args.id, 1)
            viewModelBasket.basketUpdated.observe(viewLifecycleOwner) {
                viewModel.getProductsById(args.id)
            }
        }
        binding.btnBasketFixed.setOnClickListener {
            viewModelBasket.addBasket(1, args.id, 1)
            viewModelBasket.basketUpdated.observe(viewLifecycleOwner) {
                viewModel.getProductsById(args.id)
            }
        }
        binding.rvRelated.adapter = productAdapter
        productAdapter.setOnItemClickListener(object : OnItemClickListener {
            override fun onItemClick(id: Int?) {
                val action = ProductFragmentDirections.actionProductFragmentSelf(id!!)
                findNavController().navigate(action)
            }
        })
        binding.includeBasket.ibToBasket.setOnClickListener{
//            val action = ProductFragmentDirections.actionProductFragmentToBasketFragment()
//            findNavController().navigate(action)
            (requireActivity() as MainActivity).binding.bottomMenu.selectedItemId = R.id.basketFragment
        }
        binding.includeBasketFixed.ibToBasket.setOnClickListener {
//            val action = ProductFragmentDirections.actionProductFragmentToBasketFragment()
//            findNavController().navigate(action)
            (requireActivity() as MainActivity).binding.bottomMenu.selectedItemId = R.id.basketFragment
        }

        binding.toolbar.icBtnInfo.setOnClickListener {
            val sendIntent: String = "android.intent.action.SEND"
            val shareIntent = Intent().apply {
                action = sendIntent
                putExtra(Intent.EXTRA_TEXT, "Check out this product!")   /////change message
                type = "text/plain"
            }
            startActivity(Intent.createChooser(shareIntent, null))
        }

        binding.nsProduct.setOnScrollChangeListener { _, _, scrollY, _, _ ->
            if (scrollY in 650..999) {
                binding.toolbar.tvToolbarTitle.text = title
                binding.fixedButtonContainer.visibility = View.GONE
            } else if (scrollY > 1000) {
                binding.fixedButtonContainer.visibility = View.VISIBLE
            } else {
                binding.toolbar.tvToolbarTitle.text = ""
                binding.fixedButtonContainer.visibility = View.GONE
            }
        }

    }

    private fun setupBasketView(product: ProductByIdResponse.Product) {
        val isInBasket = product.basketCount > 0
        binding.btnBasket.visibility = if (isInBasket) View.GONE else View.VISIBLE
        binding.btnBasketFixed.visibility = if (isInBasket) View.GONE else View.VISIBLE
        binding.btnBasketClicked.visibility = if (isInBasket) View.VISIBLE else View.GONE
        binding.btnBasketClickedFixed.visibility = if (isInBasket) View.VISIBLE else View.GONE
        binding.includeBasket.tvQuantity.text = product.basketCount.toString()
        binding.includeBasketFixed.tvQuantity.text = product.basketCount.toString()
    }

    private fun setupViews() {
        binding.toolbar.icBtnBack.setImageResource(R.drawable.ic_back_colored_24)
        binding.toolbar.icBtnInfo.setImageResource(R.drawable.ic_share_24)
        binding.toolbar.tvToolbarTitle.text = ""
        productAdapter = CatalogAdapter(CatalogAdapter.GRID_LAYOUT)
        binding.rvRelated.adapter = productAdapter
        val spacingInPixels = resources.getDimensionPixelSize(R.dimen.grid_spacing_right)
        val itemDecoration = GridSpacingItemDecoration(2, spacingInPixels, true)
        binding.rvRelated.addItemDecoration(itemDecoration)
    }

    private fun Int.formatWithSpaces(): String {
        return this.toString().reversed().chunked(3).joinToString(" ").reversed()
    }

}