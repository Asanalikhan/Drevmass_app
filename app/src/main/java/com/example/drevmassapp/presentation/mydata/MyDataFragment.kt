package com.example.drevmassapp.presentation.mydata

import android.os.Bundle
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import com.example.drevmassapp.R
import com.example.drevmassapp.databinding.FragmentMyDataBinding


class MyDataFragment : Fragment() {

    private lateinit var _binding: FragmentMyDataBinding
    private val binding get() = _binding

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {
        _binding = FragmentMyDataBinding.inflate(layoutInflater, container, false)
        return binding.root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        binding.etName.setOnFocusChangeListener{ _,hasFocus ->
            if(hasFocus){
                binding.tvName.boxStrokeColor = resources.getColor(R.color.btn_bg_not_added_to_cart)
            }else{
                binding.tvName.boxStrokeColor = resources.getColor(R.color.btn_thumb)
            }
        }
        binding.root.setOnClickListener {
            binding.tvName.clearFocus()
            binding.tvEmail.clearFocus()
            binding.tvBirthDate.clearFocus()
            binding.tvNumber.clearFocus()
        }
        binding.tvName.isErrorEnabled = true

        binding.toolbar.tvToolbarTitle.visibility = View.VISIBLE
        binding.toolbar.tvToolbarTitle.text = "Мои данные"
        binding.toolbar.icBtnInfo.visibility = View.GONE
    }

}