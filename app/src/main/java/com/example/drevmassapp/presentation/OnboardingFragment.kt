package com.example.drevmassapp.presentation

import android.animation.ObjectAnimator
import android.os.Bundle
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.ProgressBar
import com.example.drevmassapp.R
import com.example.drevmassapp.databinding.FragmentOnboardingBinding

class OnboardingFragment : Fragment() {

    private lateinit var binding: FragmentOnboardingBinding
    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {
        binding = FragmentOnboardingBinding.inflate(inflater, container, false)
        return binding.root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        binding.btnNext.setOnClickListener {
            animateProgress()
        }

    }
    private fun animateProgress() {
        val animator = ObjectAnimator.ofInt(binding.progressBar1, "progress", 0, 100)
        animator.duration = 5000
        animator.start()
        binding.progressBar2.progress = 70
    }
}