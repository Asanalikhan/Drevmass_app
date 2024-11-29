package com.example.drevmassapp.presentation.bookmark

import android.os.Bundle
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import com.example.drevmassapp.R
import com.example.drevmassapp.databinding.FragmentBasketBinding
import com.example.drevmassapp.databinding.FragmentBookmarkBinding
import com.example.drevmassapp.utils.provideNavigationHos

class BookmarkFragment : Fragment() {

    private lateinit var _binding: FragmentBookmarkBinding
    private val binding get() = _binding

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {
        _binding = FragmentBookmarkBinding.inflate(layoutInflater, container, false)
        return binding.root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        provideNavigationHos()?.apply {
            setNavigationVisibility(false)
        }

        binding.toolbar.icBtnBack.setOnClickListener {
            activity?.onBackPressed()
        }

    }

}