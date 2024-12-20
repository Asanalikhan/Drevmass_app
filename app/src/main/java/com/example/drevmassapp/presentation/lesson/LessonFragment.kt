package com.example.drevmassapp.presentation.lesson

import android.content.Intent
import android.os.Bundle
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.Toast
import androidx.fragment.app.viewModels
import androidx.navigation.fragment.findNavController
import androidx.navigation.fragment.navArgs
import com.bumptech.glide.Glide
import com.example.drevmassapp.R
import com.example.drevmassapp.data.remote.ServiceBuilder
import com.example.drevmassapp.databinding.FragmentLessonBinding
import com.example.drevmassapp.domain.model.CourseByIdResponse
import com.example.drevmassapp.domain.repository.OnItemClickListener
import com.example.drevmassapp.domain.repository.OnQuantityClickListener
import com.example.drevmassapp.presentation.basket.BasketViewModel
import com.example.drevmassapp.presentation.bookmark.BookmarkViewModel
import com.example.drevmassapp.presentation.catalog.CatalogAdapter
import com.example.drevmassapp.presentation.product.ProductFragmentDirections
import com.example.drevmassapp.utils.GridSpacingItemDecoration
import com.example.drevmassapp.utils.provideNavigationHos
import dagger.hilt.android.AndroidEntryPoint

@AndroidEntryPoint
class LessonFragment : Fragment() {

    private lateinit var _binding: FragmentLessonBinding
    private val binding get() = _binding
    private val viewModel: LessonViewModel by viewModels()
    private val viewModelBookmark: BookmarkViewModel by viewModels()
    private val viewModelBasket: BasketViewModel by viewModels()
    private val args by navArgs<LessonFragmentArgs>()
    private lateinit var productAdapter: CatalogAdapter
    private var isFavorite = false

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {
        _binding = FragmentLessonBinding.inflate(layoutInflater, container, false)
        return binding.root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        provideNavigationHos()?.apply {
            setNavigationVisibility(false)
        }

        viewModel.getLesson(args.id, args.courseId)
        setupViews()

        viewModel.lesson.observe(viewLifecycleOwner){
            Glide.with(binding.ivLesson.context)
                .load(ServiceBuilder.getUrl() + it.imageSrc)
                .into(binding.ivLesson)
            binding.minutes.text = it.duration.toString()
            binding.tvName.text = it.title
            binding.tvDescription.text = it.description
            binding.toolbar.tvToolbarTitle.text = "Урок ${it.id}"
            productAdapter.setProducts(it.usedProducts)
            isFavorite = it.isFavorite
            binding.toolbar.icBtnBookmark.setImageResource(if(it.isFavorite) R.drawable.ic_bookmark_fill_24 else R.drawable.ic_bookmark_24)
            binding.icBoomark.setImageResource(if(it.isFavorite) R.drawable.ic_bookmark_fill_24 else R.drawable.ic_bookmark_24)
        }

        binding.rvRelated.adapter = productAdapter
        productAdapter.setOnItemClickListener(object : OnItemClickListener {
            override fun onItemClick(id: Int?) {
                val action = LessonFragmentDirections.actionLessonFragmentToProductFragment(id!!)
                findNavController().navigate(action)
            }
        })

        binding.nsLesson.setOnScrollChangeListener { _, _, scrollY, _, _ ->
            if (scrollY < 700) {
                binding.toolbar.icBtnBookmark.visibility = View.GONE
            } else {
                binding.toolbar.icBtnBookmark.visibility = View.VISIBLE
            }
        }

        binding.toolbar.icBtnBookmark.setOnClickListener {
            if (isFavorite) {
                viewModelBookmark.deleteFavorite(args.id)
            } else {
                viewModelBookmark.postFavorite(args.id)
            }
            isFavorite = !isFavorite
            binding.toolbar.icBtnBookmark.setImageResource(if (isFavorite) R.drawable.ic_bookmark_fill_24 else R.drawable.ic_bookmark_24)
            binding.icBoomark.setImageResource(if (isFavorite) R.drawable.ic_bookmark_fill_24 else R.drawable.ic_bookmark_24)
        }

        binding.icBoomark.setOnClickListener {
            if (isFavorite) {
                viewModelBookmark.deleteFavorite(args.id)
            } else {
                viewModelBookmark.postFavorite(args.id)
            }
            isFavorite = !isFavorite
            binding.toolbar.icBtnBookmark.setImageResource(if (isFavorite) R.drawable.ic_bookmark_fill_24 else R.drawable.ic_bookmark_24)
            binding.icBoomark.setImageResource(if (isFavorite) R.drawable.ic_bookmark_fill_24 else R.drawable.ic_bookmark_24)
        }

        binding.toolbar.icBtnBack.setOnClickListener{
            findNavController().popBackStack()
        }

        productAdapter.setOnItemClickListener(object : OnItemClickListener{
            override fun onItemClick(id: Int?) {
                val action = LessonFragmentDirections.actionLessonFragmentToProductFragment(id!!)
                findNavController().navigate(action)
            }
        })
        productAdapter.setOnItemQuantityClickListener(object : OnQuantityClickListener{
            override fun onQuantityChanged(newQuantity: Int, productId: Int, increase: Boolean) {
                if(increase){
                    viewModelBasket.addBasket(1, productId, 1)
                }else{
                    viewModelBasket.deleteById(productId)
                }
            }
        })
    }

    private fun setupViews() {
        binding.toolbar.icBtnBack.setImageResource(R.drawable.ic_back_colored_24)
        binding.toolbar.icBtnInfo.setImageResource(R.drawable.ic_share_24)
        productAdapter = CatalogAdapter(CatalogAdapter.GRID_LAYOUT)
        binding.rvRelated.adapter = productAdapter
        val spacingInPixels = resources.getDimensionPixelSize(R.dimen.grid_spacing_right)
        val itemDecoration = GridSpacingItemDecoration(2, spacingInPixels, true)
        binding.rvRelated.addItemDecoration(itemDecoration)
    }
}