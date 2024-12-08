package com.example.drevmassapp.presentation.profile

import android.app.Dialog
import android.content.Context
import android.os.Bundle
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.view.Window
import android.widget.TextView
import androidx.appcompat.app.AlertDialog
import androidx.core.content.ContextCompat
import androidx.navigation.fragment.findNavController
import com.example.drevmassapp.R
import com.example.drevmassapp.data.local.PreferencesManager
import com.example.drevmassapp.data.repository.PreferencesRepositoryImpl
import com.example.drevmassapp.databinding.FragmentProfileBinding
import com.example.drevmassapp.utils.provideNavigationHos


class ProfileFragment : Fragment() {

    private lateinit var _binding: FragmentProfileBinding
    private val binding get() = _binding
    private lateinit var preferencesRepository: PreferencesRepositoryImpl

    override fun onAttach(context: Context) {
        super.onAttach(context)
        preferencesRepository = PreferencesRepositoryImpl(PreferencesManager(context))
    }

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {
        _binding = FragmentProfileBinding.inflate(layoutInflater, container, false)
        return binding.root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        provideNavigationHos()?.apply {
            setNavigationVisibility(true)
        }

        binding.includePromocode.tv.text = "Промокоды"
        binding.includePromocode.tv.setCompoundDrawablesWithIntrinsicBounds(ContextCompat.getDrawable(requireContext(), R.drawable.ic_promocode_24), null, null, null)

        binding.flBonus.setOnClickListener {
            val action = ProfileFragmentDirections.actionProfileFragmentToMyBonusFragment()
            findNavController().navigate(action)
        }
        binding.flPromocode.setOnClickListener {
            val action = ProfileFragmentDirections.actionProfileFragmentToPromocodeFragment()
            findNavController().navigate(action)
        }
        binding.includeMyData.flMyData.setOnClickListener {
            val action = ProfileFragmentDirections.actionProfileFragmentToMyDataFragment()
            findNavController().navigate(action)
        }
        binding.includeMyData.flChangePassword.setOnClickListener {
            val bottomSheet = ChangePasswordBottomDialog()
            bottomSheet.show(parentFragmentManager, bottomSheet.tag)
        }
        binding.includeMyData.flNotification.setOnClickListener {
            val action = ProfileFragmentDirections.actionProfileFragmentToNotificationFragment()
            findNavController().navigate(action)
        }
        binding.includeContact.flContact.setOnClickListener {
            val bottomSheet = ContactFragmentDialog()
            bottomSheet.show(parentFragmentManager, bottomSheet.tag)
        }
        binding.includeContact.flInformation.setOnClickListener {
            val action = ProfileFragmentDirections.actionProfileFragmentToInformationFragment()
            findNavController().navigate(action)
        }
        binding.btnExit.setOnClickListener {
            exit()
        }

    }

    private fun exit() {
        val dialog = Dialog(requireContext())
        dialog.requestWindowFeature(Window.FEATURE_NO_TITLE)
        dialog.setContentView(R.layout.dialog_exit)
        dialog.window?.setBackgroundDrawableResource(R.color.transparent_color)

        val btnStayInAccount: TextView = dialog.findViewById(R.id.btn_stay_in_app)
        val btnLogout: TextView = dialog.findViewById(R.id.btn_logout)

        btnStayInAccount.setOnClickListener {
            dialog.dismiss()
        }

        btnLogout.setOnClickListener {
            dialog.dismiss()
            preferencesRepository.deleteUserToken()
            findNavController().navigate(R.id.onboardingFragment)
        }

        dialog.show()
    }

}