package com.example.drevmassapp.presentation.basket

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.Fragment
import androidx.fragment.app.viewModels
import androidx.navigation.fragment.findNavController
import com.example.drevmassapp.R
import com.example.drevmassapp.databinding.FragmentBasketBinding
import com.example.drevmassapp.domain.model.BasketGetResponse
import com.example.drevmassapp.domain.repository.OnItemClickListener
import com.example.drevmassapp.domain.repository.OnQuantityClickListener
import com.example.drevmassapp.presentation.activity.MainActivity
import com.example.drevmassapp.presentation.catalog.CatalogAdapter
import com.example.drevmassapp.utils.GridSpacingItemDecoration
import com.example.drevmassapp.utils.provideNavigationHos
import dagger.hilt.android.AndroidEntryPoint

@AndroidEntryPoint
class BasketFragment : Fragment() {

    private lateinit var _binding: FragmentBasketBinding
    private val binding get() = _binding
    private val viewModel: BasketViewModel by viewModels()
    private lateinit var productAdapter: CatalogAdapter
    private lateinit var basketApater: BasketAdapter

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {
        _binding = FragmentBasketBinding.inflate(layoutInflater, container, false)
        return binding.root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        provideNavigationHos()?.apply {
            setNavigationVisibility(true)
        }

        viewModel.getBasket(binding.sbBonus.isChecked.toString())

        viewModel.basket.observe(viewLifecycleOwner) {
            observeBasket(it)
        }

        binding.sbBonus.setOnClickListener {
            viewModel.getBasket(binding.sbBonus.isChecked.toString())
        }

        setBasketAdapter()
        setRelatedAdapter()

        productAdapter.setOnItemClickListener(object : OnItemClickListener {
            override fun onItemClick(id: Int?) {
                val action = BasketFragmentDirections.actionBasketFragmentToProductFragment(id!!)
                findNavController().navigate(action)
            }
        })

        basketApater.setOnQuantityClickListener(object : OnQuantityClickListener {
            override fun onQuantityChanged(newQuantity: Int, productId: Int, increase: Boolean) {
                viewModel.increaseBasket(newQuantity, productId, 1, increase)
                viewModel.basketUpdated.observe(viewLifecycleOwner) {
                    viewModel.getBasket(binding.sbBonus.isChecked.toString())
                    viewModel.basketUpdated.observe(viewLifecycleOwner) {
                        viewModel.getBasket(binding.sbBonus.isChecked.toString())
                    }
                }
            }
        })

        binding.ibDelete.setOnClickListener {
            viewModel.delete()
            viewModel.basketUpdated.observe(viewLifecycleOwner) {
                viewModel.getBasket(binding.sbBonus.isChecked.toString())
            }
        }

        binding.emptyLayout.btnEmpty.setOnClickListener {
//            val action = BasketFragmentDirections.actionBasketFragmentToCatalogFragment()
//            findNavController().navigate(action)
            (requireActivity() as MainActivity).binding.bottomMenu.selectedItemId = R.id.catalogFragment

        }
    }

    private fun observeBasket(basket: BasketGetResponse) {
        if(basket.basket.isEmpty()){
            binding.clBasket.visibility = View.GONE
            binding.clEmpty.visibility = View.VISIBLE
        }else{
            binding.clBasket.visibility = View.VISIBLE
            binding.clEmpty.visibility = View.GONE
        }
        binding.itogo.tvPrice.text = "${basket.totalPrice.formatWithSpaces()} ₽"
        binding.itogo.tvBasketPrice.text = "${basket.basketPrice.formatWithSpaces()} ₽"
        binding.itogo.tvCount.text = "${basket.countProducts} товара"
        binding.itogo.tvBonusPrice.text = "-${basket.usedBonus.formatWithSpaces()} ₽"
        productAdapter.setProducts(basket.products)
        basketApater.setProducts(basket.basket)
        binding.tvPrice.text = "${basket.totalPrice.formatWithSpaces()} ₽"
        if (binding.sbBonus.isChecked) {
            binding.tvBonusDesc.text =
                "Баллами можно оплатить до ${basket.discount}% от стоимости заказа."
            binding.tvBonus2.setTextColor(resources.getColor(R.color.color_dark_1000))
            binding.tvBonus.setTextColor(resources.getColor(R.color.color_dark_1000))
        } else {
            binding.tvBonusDesc.text = "На данный момент у вас нет бонусов для списания."
            binding.tvBonus2.setTextColor(resources.getColor(R.color.color_gray_700))
            binding.tvBonus.setTextColor(resources.getColor(R.color.color_gray_700))
        }
        binding.tvBonus2.text = basket.usedBonus.formatWithSpaces()
    }

    private fun setBasketAdapter() {
        basketApater = BasketAdapter()
        binding.rvBasket.adapter = basketApater
    }

    private fun setRelatedAdapter() {
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