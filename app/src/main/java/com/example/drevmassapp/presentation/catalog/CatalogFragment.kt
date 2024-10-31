package com.example.drevmassapp.presentation.catalog

import android.os.Bundle
import android.util.Log
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.viewModels
import androidx.recyclerview.widget.GridLayoutManager
import androidx.recyclerview.widget.LinearLayoutManager
import com.example.drevmassapp.data.local.PreferencesManager
import com.example.drevmassapp.data.repository.PreferencesRepositoryImpl
import com.example.drevmassapp.databinding.FragmentCatalogBinding
import com.example.drevmassapp.domain.model.ProductResponse
import dagger.hilt.android.AndroidEntryPoint

@AndroidEntryPoint
class CatalogFragment : Fragment() {

    private lateinit var _binding: FragmentCatalogBinding
    private val binding get() = _binding
    private var isGridLayout = true
    private lateinit var catalogAdapter: CatalogAdapter
    private val viewModel : CatalogViewModel by viewModels()

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {
        _binding = FragmentCatalogBinding.inflate(layoutInflater, container, false)
        return binding.root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        setupRecyclerView()
        binding.ibFilter.setOnClickListener {
            toggleLayout()
        }

        viewModel.getProducts(0)
        viewModel.products.observe(viewLifecycleOwner) { products ->
            catalogAdapter = CatalogAdapter(products, if (isGridLayout) CatalogAdapter.GRID_LAYOUT else CatalogAdapter.VERTICAL_LAYOUT)
            binding.rvCatalog.adapter = catalogAdapter
        }

    }

    private fun setupRecyclerView() {
        binding.rvCatalog.layoutManager = GridLayoutManager(context, 2)
        catalogAdapter = CatalogAdapter(emptyList(), CatalogAdapter.GRID_LAYOUT)
        binding.rvCatalog.adapter = catalogAdapter
    }
    private fun toggleLayout() {
        isGridLayout = !isGridLayout
        if (isGridLayout) {
            binding.rvCatalog.layoutManager = GridLayoutManager(context, 2)
            catalogAdapter = CatalogAdapter(catalogAdapter.getProducts(), CatalogAdapter.GRID_LAYOUT)
        } else {
            binding.rvCatalog.layoutManager = LinearLayoutManager(context)
            catalogAdapter = CatalogAdapter(catalogAdapter.getProducts(), CatalogAdapter.VERTICAL_LAYOUT)
        }
        binding.rvCatalog.adapter = catalogAdapter
    }
}