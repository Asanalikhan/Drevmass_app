package com.example.drevmassapp.presentation.basket

import android.os.Bundle
import android.view.Gravity
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import com.example.drevmassapp.R
import com.example.drevmassapp.databinding.FragmentBasketBinding
import com.example.drevmassapp.utils.provideNavigationHos

class BasketFragment : Fragment() {

    private lateinit var _binding: FragmentBasketBinding
    private val binding get() = _binding

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


    }
}