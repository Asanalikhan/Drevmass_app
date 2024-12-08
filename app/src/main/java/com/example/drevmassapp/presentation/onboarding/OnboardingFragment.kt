package com.example.drevmassapp.presentation.onboarding

import android.content.Context
import android.os.Bundle
import android.util.Log
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.navigation.fragment.findNavController
import androidx.viewpager2.widget.ViewPager2
import com.example.drevmassapp.R
import com.example.drevmassapp.data.local.PreferencesManager
import com.example.drevmassapp.data.repository.PreferencesRepositoryImpl
import com.example.drevmassapp.databinding.FragmentOnboardingBinding
import com.example.drevmassapp.utils.provideNavigationHos

class OnboardingFragment : Fragment() {

    private var _binding: FragmentOnboardingBinding? = null
    private val binding get() = _binding!!
    private lateinit var preferencesRepository: PreferencesRepositoryImpl

    override fun onAttach(context: Context) {
        super.onAttach(context)
        preferencesRepository = PreferencesRepositoryImpl(PreferencesManager(context))
    }

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {
        _binding = FragmentOnboardingBinding.inflate(inflater, container, false)
        return binding.root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        provideNavigationHos()?.apply {
            setNavigationVisibility(false)
        }

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
        val token = preferencesRepository.getUserToken()
        Log.d("OnBoardingFragment", token)
        if (token.isNotEmpty()) {
            findNavController().navigate(R.id.action_onboardingFragment_to_courseFragment)
        }
    }

    private fun switch(position: Int) {
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

    override fun onDestroyView() {
        super.onDestroyView()
        _binding = null
    }
}