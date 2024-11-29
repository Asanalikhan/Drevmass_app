package com.example.drevmassapp.presentation.detailed

import android.os.Bundle
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.viewModels
import androidx.navigation.fragment.findNavController
import androidx.navigation.fragment.navArgs
import com.bumptech.glide.Glide
import com.example.drevmassapp.data.remote.ServiceBuilder
import com.example.drevmassapp.databinding.FragmentDetailedBinding
import com.example.drevmassapp.domain.repository.OnItemClickListener
import com.example.drevmassapp.utils.provideNavigationHos
import dagger.hilt.android.AndroidEntryPoint

@AndroidEntryPoint
class DetailedFragment : Fragment() {

    private lateinit var _binding: FragmentDetailedBinding
    private val binding get() = _binding
    private lateinit var adapter: LessonAdapter
    private val args by navArgs<DetailedFragmentArgs>()
    private val viewModel: DetailedViewModel by viewModels()

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

        setupAdapter()

        viewModel.getCourse(args.courseId)

        viewModel.response.observe(viewLifecycleOwner){
            Glide.with(binding.ivCourse.context)
                .load(ServiceBuilder.getUrl() + it.course.imageSrc)
                .into(binding.ivCourse)
            binding.tvTitle.text = it.course.name
            binding.tvDescription.text = it.course.description
            binding.tvBonus.text = it.course.bonusInfo.price.toString()
            binding.tvBonusDesc.text = "Начислим ${it.course.bonusInfo.price} бонусов за прохождение курса"
            binding.countLessons.text = it.course.lessonCnt.toString()
            binding.minutes.text = it.course.duration.toString()
            if(it.course.lessonCnt >= 4){
                binding.tvLessons.text = "уроков"
            }else if(it.course.lessonCnt < 4){
                binding.tvLessons.text = "урока"
            }
            adapter.setProducts(it.course.lessons)
        }


        adapter.setOnClickListener(object : OnItemClickListener{
            override fun onItemClick(id: Int?) {
                val action = DetailedFragmentDirections.actionDetailedFragmentToLessonFragment(id!!, args.courseId)
                findNavController().navigate(action)
            }
        })

    }

    private fun setupAdapter(){
        adapter = LessonAdapter()
        binding.rvLessons.adapter = adapter
    }

}