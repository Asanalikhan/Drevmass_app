package com.example.drevmassapp.presentation.profile

import android.os.Bundle
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.navigation.fragment.findNavController
import com.example.drevmassapp.R
import com.example.drevmassapp.databinding.FragmentNotificationBinding

class NotificationFragment : Fragment() {

    private lateinit var _binding: FragmentNotificationBinding
    private val binding get() = _binding

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {
        _binding = FragmentNotificationBinding.inflate(layoutInflater, container, false)
        return binding.root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        binding.toolbar.icBtnBack.setImageResource(R.drawable.ic_back_colored_24)
        binding.toolbar.tvToolbarTitle.text = "Уведомления"
        binding.toolbar.tvToolbarTitle.visibility = View.VISIBLE
        binding.toolbar.icBtnInfo.visibility = View.GONE

        binding.toolbar.icBtnBack.setOnClickListener {
            findNavController().popBackStack()
        }
    }

}