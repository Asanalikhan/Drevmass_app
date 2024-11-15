package com.example.drevmassapp.presentation.catalog

import android.os.Bundle
import android.util.TypedValue
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.core.widget.NestedScrollView
import androidx.fragment.app.Fragment
import androidx.fragment.app.viewModels
import androidx.navigation.fragment.findNavController
import androidx.recyclerview.widget.GridLayoutManager
import androidx.recyclerview.widget.LinearLayoutManager
import com.example.drevmassapp.R
import com.example.drevmassapp.databinding.FragmentCatalogBinding
import com.example.drevmassapp.domain.repository.OnItemClickListener
import com.example.drevmassapp.utils.GridSpacingItemDecoration
import com.example.drevmassapp.utils.provideNavigationHos
import dagger.hilt.android.AndroidEntryPoint

@AndroidEntryPoint
class CatalogFragment : Fragment(), SortBottomSheetDialog.SortListener {

    private lateinit var _binding: FragmentCatalogBinding
    private val binding get() = _binding
    private var typeOfLayout = 0
    private var currentSortType = 1
    private lateinit var catalogAdapter: CatalogAdapter
    private val viewModel: CatalogViewModel by viewModels()

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {
        _binding = FragmentCatalogBinding.inflate(layoutInflater, container, false)
        return binding.root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        provideNavigationHos()?.apply {
            setNavigationVisibility(true)
        }

        setupRecyclerView()
        setupScrollListener()
        binding.ibFilter.setOnClickListener {
            toggleLayout(false)
        }

        binding.llSort.setOnClickListener {
            val sortBottomSheet = SortBottomSheetDialog(currentSortType)
            sortBottomSheet.setSortListener(this)
            sortBottomSheet.show(parentFragmentManager, sortBottomSheet.tag)
        }

        onSort(currentSortType)
        toggleLayout(true)
    }

    private fun callViewModel() {
        viewModel.products.observe(viewLifecycleOwner) { products ->
            catalogAdapter.setProducts(products)
            binding.rvCatalog.adapter = catalogAdapter
        }
    }

    private fun setupRecyclerView() {
        binding.rvCatalog.layoutManager = GridLayoutManager(context, 2)
        catalogAdapter = CatalogAdapter(CatalogAdapter.GRID_LAYOUT)
        val spacingInPixels = resources.getDimensionPixelSize(R.dimen.grid_spacing)
        val itemDecoration = GridSpacingItemDecoration(2, spacingInPixels, false)
        binding.rvCatalog.adapter = catalogAdapter
        binding.rvCatalog.addItemDecoration(itemDecoration)
        catalogAdapter.setOnItemClickListener(object : OnItemClickListener {
            override fun onItemClick(id: Int?) {
                val action = CatalogFragmentDirections.actionCatalogFragmentToProductFragment(id!!)
                findNavController().navigate(action)
            }
        })
    }

    private fun toggleLayout(decrease: Boolean) {
        if (decrease) typeOfLayout--
        when (typeOfLayout) {
            0 -> {
                typeOfLayout = 1
                binding.rvCatalog.layoutManager = LinearLayoutManager(context)
                catalogAdapter =
                    CatalogAdapter(CatalogAdapter.VERTICAL_LAYOUT)
                binding.ibFilter.setImageResource(R.drawable.ic_vertical_list_24)
            }

            1 -> {
                typeOfLayout = 2
                binding.rvCatalog.layoutManager = LinearLayoutManager(context)
                catalogAdapter =
                    CatalogAdapter(CatalogAdapter.HORIZONTAL_LAYOUT)
                binding.ibFilter.setImageResource(R.drawable.ic_gorizontal_list_24)
            }

            else -> {
                typeOfLayout = 0
                binding.rvCatalog.layoutManager = GridLayoutManager(context, 2)
                catalogAdapter =
                    CatalogAdapter(CatalogAdapter.GRID_LAYOUT)
                binding.ibFilter.setImageResource(R.drawable.ic_grid_24)
            }
        }
        callViewModel()
        binding.rvCatalog.adapter = catalogAdapter
    }

    private fun setupScrollListener() {
        binding.nsCatalog.setOnScrollChangeListener(NestedScrollView.OnScrollChangeListener { _, _, scrollY, _, _ ->
            val threshold = TypedValue.applyDimension(
                TypedValue.COMPLEX_UNIT_DIP,
                92f,
                resources.displayMetrics
            ).toInt()
            binding.flTitle.visibility = if (scrollY >= threshold) View.VISIBLE else View.GONE
        })
    }

    override fun onSort(sortType: Int) {
        currentSortType = sortType
        viewModel.getProducts(sortType)
        binding.tvSort.text = when (sortType) {
            1 -> "По популярности"
            2 -> "По возрастанию цены"
            3 -> "По убыванию цены"
            else -> "По популярности"
        }
    }
}