package com.example.drevmassapp.presentation.onboarding

import android.os.Bundle
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.navigation.fragment.findNavController
import androidx.viewpager2.widget.ViewPager2
import com.example.drevmassapp.R
import com.example.drevmassapp.databinding.FragmentOnboardingBinding

class OnboardingFragment : Fragment() {

    private lateinit var _binding: FragmentOnboardingBinding
    private val binding get() = _binding
    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {
        _binding = FragmentOnboardingBinding.inflate(inflater, container, false)
        return binding.root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        checkToken()

        val adapter = OnboardingAdapter()
        adapter.submitList(OnboardingInfoList.getOnboardingModelList(requireContext()))
        binding.viewPager.adapter = adapter

        val viewPagerCallback = object: ViewPager2.OnPageChangeCallback() {
            override fun onPageSelected(position: Int) {
                super.onPageSelected(position)
                switch(position)
            }
        }
        binding.viewPager.registerOnPageChangeCallback(viewPagerCallback)

        binding.loginButton.setOnClickListener{
            findNavController().navigate(R.id.action_onboardingFragment_to_loginFragment)
        }
        binding.singinButton.setOnClickListener{
            findNavController().navigate(R.id.action_onboardingFragment_to_registrationFragment)
        }

    }

    private fun checkToken() {
        val token = requireActivity().getSharedPreferences("sharedPrefs", 0).getString("token", null)
        if (token.isNullOrEmpty()) {
            findNavController().navigate(R.id.action_onboardingFragment_to_courseFragment)
        }
    }

    private fun switch(position : Int) {
        when (position) {
            0 -> {
                binding.progressBar1.progress = 100
                binding.progressBar2.progress = 0
                binding.progressBar3.progress = 0
            }
            1 -> {
                binding.progressBar1.progress = 100
                binding.progressBar2.progress = 100
                binding.progressBar3.progress = 0
            }
            2 -> {
                binding.progressBar1.progress = 100
                binding.progressBar2.progress = 100
                binding.progressBar3.progress = 100
            }
        }
    }
}