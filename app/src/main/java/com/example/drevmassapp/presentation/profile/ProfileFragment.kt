package com.example.drevmassapp.presentation.profile

import android.os.Bundle
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import com.example.drevmassapp.R
import com.example.drevmassapp.databinding.FragmentProductBinding
import com.example.drevmassapp.databinding.FragmentProfileBinding
import com.example.drevmassapp.utils.provideNavigationHos
import kotlinx.coroutines.processNextEventInCurrentThread


class ProfileFragment : Fragment() {

    private lateinit var _binding: FragmentProfileBinding
    private val binding get() = _binding

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {
        _binding = FragmentProfileBinding.inflate(layoutInflater, container, false)
        return binding.root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        provideNavigationHos()?.apply {
            setNavigationVisibility(true)
        }
    }


}