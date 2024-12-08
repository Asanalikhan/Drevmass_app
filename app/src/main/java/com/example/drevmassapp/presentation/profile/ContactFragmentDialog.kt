package com.example.drevmassapp.presentation.profile

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import com.example.drevmassapp.databinding.BottomSheetContactBinding
import com.google.android.material.bottomsheet.BottomSheetDialogFragment

class ContactFragmentDialog: BottomSheetDialogFragment() {

    private lateinit var _binding: BottomSheetContactBinding
    private val binding get() = _binding
    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {
        _binding = BottomSheetContactBinding.inflate(layoutInflater, container, false)
        return binding.root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        binding.tvCall.setOnClickListener {
            val bottomSheet = CallFragmentDialog()
            bottomSheet.showNow(parentFragmentManager, bottomSheet.tag)
        }
        binding.tvHelp.setOnClickListener {
            val bottomSheet = HelpFragmentDialog()
            bottomSheet.show(parentFragmentManager, bottomSheet.tag)
        }

    }
}