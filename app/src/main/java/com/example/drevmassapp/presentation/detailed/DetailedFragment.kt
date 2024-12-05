package com.example.drevmassapp.presentation.detailed

import android.content.Intent
import android.os.Bundle
import android.util.TypedValue
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.core.widget.NestedScrollView
import androidx.fragment.app.Fragment
import androidx.fragment.app.viewModels
import androidx.navigation.fragment.findNavController
import androidx.navigation.fragment.navArgs
import com.bumptech.glide.Glide
import com.example.drevmassapp.R
import com.example.drevmassapp.data.remote.ServiceBuilder
import com.example.drevmassapp.databinding.FragmentDetailedBinding
import com.example.drevmassapp.domain.repository.OnItemClickListener
import com.example.drevmassapp.domain.repository.OnQuantityClickListener
import com.example.drevmassapp.presentation.bookmark.BookmarkViewModel
import com.example.drevmassapp.utils.provideNavigationHos
import dagger.hilt.android.AndroidEntryPoint

@AndroidEntryPoint
class DetailedFragment : Fragment() {

    private lateinit var _binding: FragmentDetailedBinding
    private val binding get() = _binding
    private lateinit var adapter: LessonAdapter
    private val args by navArgs<DetailedFragmentArgs>()
    private val viewModel: DetailedViewModel by viewModels()
    private val viewModelBookmark: BookmarkViewModel by viewModels()

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {
        _binding = FragmentDetailedBinding.inflate(layoutInflater, container, false)
        return binding.root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        provideNavigationHos()?.apply {
            setNavigationVisibility(false)
        }

        setup()

        viewModel.getCourse(args.courseId)

        viewModel.response.observe(viewLifecycleOwner) {
            Glide.with(binding.ivCourse.context)
                .load(ServiceBuilder.getUrl() + it.course.imageSrc)
                .into(binding.ivCourse)
            binding.tvTitle.text = it.course.name
            binding.tvTitle2.text = it.course.name
            binding.tvDescription.text = it.course.description
            binding.tvBonus.text = it.course.bonusInfo.price.toString()
            binding.tvBonusDesc.text =
                "Начислим ${it.course.bonusInfo.price} бонусов за прохождение курса"
            binding.countLessons.text = it.course.lessonCnt.toString()
            binding.minutes.text = it.course.duration.toString()
            if (it.course.lessonCnt >= 4) {
                binding.tvLessons.text = "уроков"
            } else if (it.course.lessonCnt < 4) {
                binding.tvLessons.text = "урока"
            }
            adapter.setProducts(it.course.lessons)
        }

        adapter.setOnClickListener(object : OnItemClickListener {
            override fun onItemClick(id: Int?) {
                val action = DetailedFragmentDirections.actionDetailedFragmentToLessonFragment(
                    id!!,
                    args.courseId
                )
                findNavController().navigate(action)
            }
        })

        adapter.setOnBookmarkListener(object : OnQuantityClickListener {
            override fun onQuantityChanged(newQuantity: Int, productId: Int, increase: Boolean) {
                if (increase) viewModelBookmark.postFavorite(productId)
                else viewModelBookmark.deleteFavorite(productId)
            }
        })

        binding.includeZakladka.sbBonus.setOnClickListener {
            if (binding.includeZakladka.sbBonus.isChecked) {
                binding.includeZakladka.dayTime.visibility = View.VISIBLE
            } else {
                binding.includeZakladka.dayTime.visibility = View.GONE
            }
        }

        binding.icBtnShare.setOnClickListener {
            val sendIntent: String = "android.intent.action.SEND"
            val shareIntent = Intent().apply {
                action = sendIntent
                putExtra(Intent.EXTRA_TEXT, "Check out this product!")   /////change message
                type = "text/plain"
            }
            startActivity(Intent.createChooser(shareIntent, null))
        }
        binding.icBtnBack.setOnClickListener {
            findNavController().popBackStack()
        }
    }

    private fun setup() {
        adapter = LessonAdapter()
        binding.rvLessons.adapter = adapter
        binding.tvTitle2.visibility = View.GONE
        binding.nsDetailed.setOnScrollChangeListener(NestedScrollView.OnScrollChangeListener { _, _, scrollY, _, _ ->
            val threshold = TypedValue.applyDimension(
                TypedValue.COMPLEX_UNIT_DIP,
                292f,
                resources.displayMetrics
            ).toInt()
            if (scrollY >= threshold) {
                binding.icBtnShare.setImageResource(R.drawable.ic_share_24)
                binding.icBtnBack.setImageResource(R.drawable.ic_back_colored_24)
                binding.tvTitle2.visibility = View.VISIBLE
                binding.flTitle.setBackgroundColor(
                    resources.getColor(
                        R.color.color_white_1000,
                        null
                    )
                )
            } else {
                binding.icBtnShare.setImageResource(R.drawable.ic_share_white_24)
                binding.icBtnBack.setImageResource(R.drawable.ic_back_white_24)
                binding.tvTitle2.visibility = View.GONE
                binding.flTitle.setBackgroundColor(resources.getColor(R.color.transparent_color))
            }
        })
    }

}