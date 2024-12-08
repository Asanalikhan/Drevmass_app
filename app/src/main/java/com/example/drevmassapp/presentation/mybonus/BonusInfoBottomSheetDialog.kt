package com.example.drevmassapp.presentation.mybonus

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import com.example.drevmassapp.R
import com.example.drevmassapp.databinding.BottomSheetBonusInfoBinding
import com.google.android.material.bottomsheet.BottomSheetDialogFragment
import dagger.hilt.android.AndroidEntryPoint

@AndroidEntryPoint
class BonusInfoBottomSheetDialog: BottomSheetDialogFragment() {

    private lateinit var _binding: BottomSheetBonusInfoBinding
    private val binding get() = _binding
    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {
        _binding = BottomSheetBonusInfoBinding.inflate(layoutInflater, container, false)
        return binding.root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        binding.toolbar.tvToolbarTitle.visibility = View.VISIBLE
        binding.toolbar.tvToolbarTitle.text = "Бонусная программа"
        binding.toolbar.tvToolbarTitle.setTextColor(resources.getColor(R.color.color_dark_1000))
        binding.toolbar.icBtnBack.setImageResource(R.drawable.ic_back_colored_24)
        binding.toolbar.icBtnInfo.visibility = View.GONE
        binding.toolbar.icBtnBack.setOnClickListener {
            dismiss()
        }
    }
}