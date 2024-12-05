package com.example.drevmassapp.presentation.promocode

import android.os.Bundle
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.navigation.fragment.findNavController
import com.example.drevmassapp.R
import com.example.drevmassapp.databinding.FragmentProductBinding
import com.example.drevmassapp.databinding.FragmentPromocodeBinding
import com.example.drevmassapp.utils.provideNavigationHos
import kotlinx.coroutines.processNextEventInCurrentThread

class PromocodeFragment : Fragment() {

    private lateinit var _binding: FragmentPromocodeBinding
    private val binding get() = _binding

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {
        _binding = FragmentPromocodeBinding.inflate(layoutInflater, container, false)
        return binding.root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        provideNavigationHos()?.apply {
            setNavigationVisibility(false)
        }

        binding.emptyLayout.ivEmpty.setImageResource(R.drawable.png_promocode)
        binding.emptyLayout.tvEmptyDesc.text = "Если у вас есть промокод, можете использовать его при оформлении заказа."
        binding.emptyLayout.tvEmptyTitle.text = "Действующих промокодов нет"
        binding.emptyLayout.btnEmpty.visibility = View.GONE
        binding.toolbar.tvToolbarTitle.visibility = View.VISIBLE
        binding.toolbar.tvToolbarTitle.text = "Промокоды"
        binding.toolbar.tvToolbarTitle.setTextColor(resources.getColor(R.color.color_dark_1000))
        binding.toolbar.icBtnBack.setImageResource(R.drawable.ic_back_colored_24)

        binding.toolbar.icBtnBack.setOnClickListener {
            findNavController().popBackStack()
        }
    }

}