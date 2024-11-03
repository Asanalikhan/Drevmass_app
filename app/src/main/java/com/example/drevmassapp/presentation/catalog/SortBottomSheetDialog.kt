package com.example.drevmassapp.presentation.catalog

import android.os.Bundle
import android.util.Log
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.viewModels
import com.example.drevmassapp.R
import com.example.drevmassapp.databinding.BottomSheetSortBinding
import com.google.android.material.bottomsheet.BottomSheetDialogFragment
import dagger.hilt.android.AndroidEntryPoint

@AndroidEntryPoint
class SortBottomSheetDialog(private val currentSortType: Int) : BottomSheetDialogFragment() {

    interface SortListener {
        fun onSort(sortType: Int)
    }

    private var _binding: BottomSheetSortBinding? = null
    private val binding get() = _binding!!
    private var listener: SortListener? = null

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {
        _binding = BottomSheetSortBinding.inflate(inflater, container, false)
        return binding.root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        updateSortOption(currentSortType)

        val sortOptions = listOf(
            binding.byPopularity,
            binding.tvAscending,
            binding.tvDescending
        )

        sortOptions.forEach { option ->
            option.setOnClickListener {
                val sortType = when (option.id) {
                    R.id.byPopularity -> 1
                    R.id.tvAscending -> 2
                    R.id.tvDescending -> 3
                    else -> 1
                }
                updateSortOption(sortType)
                listener?.onSort(sortType)
                Log.d("SortBottomSheetDialog", "Sort option: ${option.text}")
            }
        }
    }

    private fun updateSortOption(selectedId: Int) {
        binding.byPopularity.setCompoundDrawablesWithIntrinsicBounds(0, 0, if (selectedId == 1) R.drawable.ic_sort_clicked else R.drawable.ic_sort_unclicked, 0)
        binding.tvAscending.setCompoundDrawablesWithIntrinsicBounds(0, 0, if (selectedId == 2) R.drawable.ic_sort_clicked else R.drawable.ic_sort_unclicked, 0)
        binding.tvDescending.setCompoundDrawablesWithIntrinsicBounds(0, 0, if (selectedId == 3) R.drawable.ic_sort_clicked else R.drawable.ic_sort_unclicked, 0)
    }

    fun setSortListener(listener: SortListener) {
        this.listener = listener
    }
}