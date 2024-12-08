package com.example.drevmassapp.presentation.information

import android.os.Bundle
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.navigation.fragment.findNavController
import com.example.drevmassapp.R
import com.example.drevmassapp.databinding.FragmentInformationBinding
import com.example.drevmassapp.utils.provideNavigationHos


class InformationFragment : Fragment() {
    private lateinit var _binding: FragmentInformationBinding
    private val binding get() = _binding

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {
        _binding = FragmentInformationBinding.inflate(layoutInflater, container, false)
        return binding.root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        provideNavigationHos()?.apply {
            setNavigationVisibility(false)
        }

        binding.toolbar.tvToolbarTitle.text = "Информация"
        binding.toolbar.tvToolbarTitle.visibility = View.VISIBLE
        binding.toolbar.icBtnBack.setImageResource(R.drawable.ic_back_colored_24)
        binding.toolbar.icBtnInfo.visibility = View.GONE

        binding.flCompany.setOnClickListener {
            val bottomSheet = CompanyFragmentDialog()
            bottomSheet.show(parentFragmentManager, bottomSheet.tag)
        }
        binding.toolbar.icBtnBack.setOnClickListener {
            findNavController().popBackStack()
        }
        binding.flApp.setOnClickListener {
            val bottomSheet = AppFragmentDialog()
            bottomSheet.show(parentFragmentManager, bottomSheet.tag)
        }

    }
}