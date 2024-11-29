package com.example.drevmassapp.presentation.course

import android.os.Bundle
import android.util.TypedValue
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.core.content.ContextCompat
import androidx.core.widget.NestedScrollView
import androidx.fragment.app.viewModels
import androidx.lifecycle.findViewTreeViewModelStoreOwner
import androidx.navigation.fragment.findNavController
import com.example.drevmassapp.R
import com.example.drevmassapp.databinding.FragmentCourseBinding
import com.example.drevmassapp.presentation.activity.MainActivity
import com.example.drevmassapp.utils.provideNavigationHos
import dagger.hilt.android.AndroidEntryPoint

@AndroidEntryPoint
class CourseFragment : Fragment() {

    private lateinit var _binding: FragmentCourseBinding
    private val binding get() = _binding
    private val viewModel: CourseViewModel by viewModels()
    private lateinit var adapter: CourseAdapter

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {
        _binding = FragmentCourseBinding.inflate(layoutInflater, container, false)
        return binding.root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        provideNavigationHos()?.apply {
            setNavigationVisibility(true)
        }

        viewModel.getCourse()
        viewModel.getBonus()
        setupView()

        viewModel.course.observe(viewLifecycleOwner){ course ->
            adapter.setProducts(course)
        }
        viewModel.bonus.observe(viewLifecycleOwner){
            binding.tvBonus.text = "Начислим до ${it.price} ₽ бонусами"
        }

        setupAdapter()

        binding.flZakladka.setOnClickListener {
            findNavController().navigate(R.id.action_courseFragment_to_bookmarkFragment)
        }
        binding.toolbar.icBtnInfo.setOnClickListener {
            findNavController().navigate(R.id.action_courseFragment_to_bookmarkFragment)
        }
    }

    private fun setupAdapter(){
        adapter = CourseAdapter()
        binding.rvCourse.adapter = adapter
    }

    private fun setupView() {
        binding.includeZakladka.tv.text = "Мои закладки"
        binding.includeZakladka.tv.setCompoundDrawablesWithIntrinsicBounds(ContextCompat.getDrawable(requireContext(), R.drawable.ic_bookmark_24), null, null, null)
        binding.toolbar.tvToolbarTitle.text = "Курсы"
        binding.toolbar.icBtnBack.visibility = View.GONE
        binding.toolbar.icBtnInfo.setImageDrawable(ContextCompat.getDrawable(requireContext(), R.drawable.ic_bookmark_24))
        binding.toolbarContainer.visibility = View.GONE
        binding.nsCatalog.setOnScrollChangeListener(NestedScrollView.OnScrollChangeListener { _, _, scrollY, _, _ ->
            val threshold = TypedValue.applyDimension(
                TypedValue.COMPLEX_UNIT_DIP,
                92f,
                resources.displayMetrics
            ).toInt()
            binding.toolbarContainer.visibility = if (scrollY >= threshold) View.VISIBLE else View.GONE
        })
    }

}