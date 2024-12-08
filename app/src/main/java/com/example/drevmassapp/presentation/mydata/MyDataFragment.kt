package com.example.drevmassapp.presentation.mydata

import android.content.Context
import android.os.Bundle
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.view.inputmethod.InputMethodManager
import androidx.navigation.fragment.findNavController
import com.example.drevmassapp.R
import com.example.drevmassapp.databinding.FragmentMyDataBinding
import com.example.drevmassapp.utils.provideNavigationHos
import kotlinx.coroutines.processNextEventInCurrentThread

class MyDataFragment : Fragment() {

    private var _binding: FragmentMyDataBinding? = null
    private val binding get() = _binding!!

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {
        _binding = FragmentMyDataBinding.inflate(inflater, container, false)
        return binding.root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        provideNavigationHos()?.apply {
            setNavigationVisibility(false)
        }

        binding.root.setOnClickListener {
            hideKeyboard()
            binding.tvName.clearFocus()
            binding.tvEmail.clearFocus()
            binding.tvBirthDate.clearFocus()
            binding.tvNumber.clearFocus()
            binding.tvHeight.clearFocus()
            binding.tvWeight.clearFocus()
        }
        binding.toolbar.icBtnBack.setImageResource(R.drawable.ic_back_colored_24)
        binding.toolbar.tvToolbarTitle.visibility = View.VISIBLE
        binding.toolbar.tvToolbarTitle.text = "Мои данные"
        binding.toolbar.icBtnInfo.visibility = View.GONE

        binding.toolbar.icBtnBack.setOnClickListener {
            findNavController().popBackStack()
        }
    }

    fun hideKeyboard() {
        val imm = requireActivity().getSystemService(Context.INPUT_METHOD_SERVICE) as InputMethodManager
        imm.hideSoftInputFromWindow(view?.windowToken, 0)
    }

    override fun onDestroyView() {
        super.onDestroyView()
        _binding = null
    }
}