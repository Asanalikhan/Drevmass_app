package com.example.drevmassapp.presentation.mybonus

import android.os.Bundle
import android.util.TypedValue
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.core.widget.NestedScrollView
import androidx.navigation.fragment.findNavController
import com.example.drevmassapp.R
import com.example.drevmassapp.databinding.FragmentMyBonusBinding


class MyBonusFragment : Fragment() {

    private lateinit var _binding: FragmentMyBonusBinding
    private val binding get() = _binding

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {
        _binding = FragmentMyBonusBinding.inflate(layoutInflater, container, false)
        return binding.root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        binding.nsBonus.setOnScrollChangeListener(NestedScrollView.OnScrollChangeListener { _, _, scrollY, _, _ ->
            val threshold = TypedValue.applyDimension(
                TypedValue.COMPLEX_UNIT_DIP,
                292f,
                resources.displayMetrics
            ).toInt()
            if (scrollY >= threshold) {
                binding.icBtnInfo.setImageResource(R.drawable.ic_info_24)
                binding.icBtnBack.setImageResource(R.drawable.ic_back_colored_24)
                binding.flTitle.setBackgroundColor(resources.getColor(R.color.color_white_1000, null))
                binding.tvTitle2.setTextColor(resources.getColor(R.color.color_dark_1000))
            } else {
                binding.icBtnInfo.setImageResource(R.drawable.ic_btn_info_white)
                binding.icBtnBack.setImageResource(R.drawable.ic_back_white_24)
                binding.flTitle.setBackgroundColor(resources.getColor(R.color.transparent_color))
                binding.tvTitle2.setTextColor(resources.getColor(R.color.color_white_1000))
            }
        })

        binding.emptyLayout.ivEmpty.setImageResource(R.drawable.png_empty_bonus)
        binding.emptyLayout.tvEmptyTitle.text = "В истории баллов пока пусто"
        binding.emptyLayout.tvEmptyDesc.text = "Совершайте покупки, проходите видеоуроки и получайте за это баллы."
        binding.emptyLayout.btnEmpty.visibility = View.GONE

        binding.icBtnBack.setOnClickListener {
            findNavController().popBackStack()
        }
        binding.icBtnInfo.setOnClickListener {
            val bottomSheet = BonusInfoBottomSheetDialog()
            bottomSheet.show(parentFragmentManager, bottomSheet.tag)
        }
    }
}