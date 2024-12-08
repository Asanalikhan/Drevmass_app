package com.example.drevmassapp.presentation.mydata

import android.content.Context
import android.os.Bundle
import android.text.Editable
import android.util.Log
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.view.inputmethod.InputMethodManager
import android.widget.Toast
import androidx.core.text.set
import androidx.fragment.app.viewModels
import androidx.navigation.fragment.findNavController
import com.example.drevmassapp.R
import com.example.drevmassapp.databinding.FragmentMyDataBinding
import com.example.drevmassapp.domain.model.UserInformationModel
import com.example.drevmassapp.presentation.profile.ProfileViewModel
import com.example.drevmassapp.utils.provideNavigationHos
import dagger.hilt.android.AndroidEntryPoint
import kotlinx.coroutines.delay
import kotlinx.coroutines.processNextEventInCurrentThread

@AndroidEntryPoint
class MyDataFragment : Fragment() {

    private var _binding: FragmentMyDataBinding? = null
    private val binding get() = _binding!!
    private val viewModel: ProfileViewModel by viewModels()
    private var id = 0

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

        viewModel.getUserInformation()
        viewModel.userInformation.observe(viewLifecycleOwner){ user ->
            binding.etName.setText(user.name.toEditable())
            binding.etEmail.setText(user.email.toEditable())
            binding.etHeight.setText(user.height.toString().toEditable())
            binding.etNumber.setText(user.phoneNumber.toEditable())
            binding.etWeight.setText(user.weight.toString().toEditable())
            binding.etBirthDate.setText(user.birth.toEditable())
            when (user.gender) {
                0 -> binding.rgGender.check(R.id.rbNotIdentified)
                1 -> binding.rgGender.check(R.id.rbMale)
                2 -> binding.rgGender.check(R.id.rbFemale)
            }
            when(user.activity){
                0 -> binding.rgActivity.check(R.id.rbLow)
                1 -> binding.rgActivity.check(R.id.rbMiddle)
                2 -> binding.rgActivity.check(R.id.rbHigh)
            }
            id = user.id
        }

        binding.btnConfirm.setOnClickListener {
            val name = binding.etName.text.toString()
            val email = binding.etEmail.text.toString()
            val height = binding.etHeight.text.toString().toInt()
            val number = binding.etNumber.text.toString()
            val weight = binding.etWeight.text.toString().toInt()
            val birth = binding.etBirthDate.text.toString()
            val gender = getSelectedGender()
            val activity = getSelectedActivity()
            viewModel.setUserInformation(UserInformationModel(activity, birth, email, gender, height, id, name, number, weight))
            viewModel.error.observe(viewLifecycleOwner){
                if(it.isNullOrEmpty()){
                    Toast.makeText(requireContext(), "Данные успешно сохранены", Toast.LENGTH_SHORT).show()
                }else{
                    Toast.makeText(requireContext(), "Повторите попытку", Toast.LENGTH_SHORT).show()
                }
            }
        }
    }

    private fun String.toEditable(): Editable = Editable.Factory.getInstance().newEditable(this)

    private fun getSelectedGender(): Int {
        return when (binding.rgGender.checkedRadioButtonId) {
            R.id.rbNotIdentified -> 0
            R.id.rbMale -> 1
            R.id.rbFemale -> 2
            else -> 0
        }
    }
    private fun getSelectedActivity(): Int {
        return when (binding.rgActivity.checkedRadioButtonId) {
            R.id.rbLow -> 0
            R.id.rbMiddle -> 1
            R.id.rbHigh -> 2
            else -> 0
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