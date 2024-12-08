package com.example.drevmassapp.presentation.profile

import android.app.Dialog
import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.Toast
import androidx.fragment.app.viewModels
import com.example.drevmassapp.R
import com.example.drevmassapp.databinding.BottomSheetChangePasswordBinding
import com.example.drevmassapp.domain.model.ResetPasswordModel
import com.google.android.material.bottomsheet.BottomSheetBehavior
import com.google.android.material.bottomsheet.BottomSheetDialog
import com.google.android.material.bottomsheet.BottomSheetDialogFragment
import dagger.hilt.android.AndroidEntryPoint

@AndroidEntryPoint
class ChangePasswordBottomDialog: BottomSheetDialogFragment() {

    private lateinit var _binding: BottomSheetChangePasswordBinding
    private val binding get() = _binding
    private val viewModel: ProfileViewModel by viewModels()

    override fun onCreateDialog(savedInstanceState: Bundle?): Dialog {
        val dialog = super.onCreateDialog(savedInstanceState) as BottomSheetDialog

        dialog.setOnShowListener { dialogInterface ->
            val bottomSheetDialog = dialogInterface as BottomSheetDialog
            val bottomSheet = bottomSheetDialog.findViewById<View>(com.google.android.material.R.id.design_bottom_sheet)

            bottomSheet?.let {
                val behavior = BottomSheetBehavior.from(it)
                setupFullHeight(it)
                behavior.state = BottomSheetBehavior.STATE_EXPANDED
            }
        }
        return dialog
    }

    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {
        _binding = BottomSheetChangePasswordBinding.inflate(layoutInflater, container, false)
        return binding.root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        binding.toolbar.tvToolbarTitle.visibility = View.VISIBLE
        binding.toolbar.tvToolbarTitle.text = "Сбросить пароль"
        binding.toolbar.tvToolbarTitle.setTextColor(resources.getColor(R.color.color_dark_1000))
        binding.toolbar.icBtnBack.setImageResource(R.drawable.ic_back_colored_24)
        binding.toolbar.icBtnInfo.visibility = View.GONE

        binding.toolbar.icBtnBack.setOnClickListener {
            dismiss()
        }

        binding.btnConfirm.setOnClickListener {
            val oldPassword = binding.etPassword1.text.toString()
            val newPassword = binding.etPassword2.text.toString()
            viewModel.resetPassword(ResetPasswordModel(oldPassword, newPassword))
            viewModel.error.observe(viewLifecycleOwner){
                if(it.isNullOrEmpty()){
                    Toast.makeText(requireContext(), "Успешно обновлено", Toast.LENGTH_SHORT).show()
                }
                else{
                    Toast.makeText(requireContext(), "Повторите попозже", Toast.LENGTH_SHORT).show()
                }
            }
            dismiss()
        }
    }

    private fun setupFullHeight(bottomSheet: View) {
        val layoutParams = bottomSheet.layoutParams
        layoutParams.height = ViewGroup.LayoutParams.MATCH_PARENT
        bottomSheet.layoutParams = layoutParams
    }
}