package com.example.drevmassapp.presentation.bookmark

import android.os.Bundle
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.viewModels
import androidx.navigation.fragment.findNavController
import com.example.drevmassapp.R
import com.example.drevmassapp.databinding.FragmentBasketBinding
import com.example.drevmassapp.databinding.FragmentBookmarkBinding
import com.example.drevmassapp.domain.repository.OnItemClickListener
import com.example.drevmassapp.domain.repository.OnQuantityClickListener
import com.example.drevmassapp.utils.provideNavigationHos
import dagger.hilt.android.AndroidEntryPoint

@AndroidEntryPoint
class BookmarkFragment : Fragment() {

    private lateinit var _binding: FragmentBookmarkBinding
    private val binding get() = _binding
    private val viewModel: BookmarkViewModel by viewModels()
    private lateinit var adapter: BookmarkAdapter

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {
        _binding = FragmentBookmarkBinding.inflate(layoutInflater, container, false)
        return binding.root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        provideNavigationHos()?.apply {
            setNavigationVisibility(false)
        }

        binding.toolbar.icBtnBack.setOnClickListener {
            findNavController().popBackStack()
        }

        setup()

        viewModel.getFavorites()
        viewModel.favorites.observe(viewLifecycleOwner){
            if(it.isNullOrEmpty()){
                binding.clEmpty.visibility = View.VISIBLE
                binding.emptyLayout.ivEmpty.setImageResource(R.drawable.png_bookmark)
                binding.emptyLayout.tvEmptyTitle.text = "В закладках пока ничего нет"
                binding.emptyLayout.tvEmptyDesc.text = "Смотрите курсы и сохраняйте полезные уроки здесь"
                binding.emptyLayout.btnEmpty.visibility = View.GONE
                binding.rvBookmark.visibility = View.GONE
            }
            else{
                binding.clEmpty.visibility = View.GONE
                binding.rvBookmark.visibility = View.VISIBLE
                adapter.setFavorites(it)
            }
        }

        adapter.bookmarkClickListener(object : OnQuantityClickListener{
            override fun onQuantityChanged(newQuantity: Int, productId: Int, increase: Boolean) {
                viewModel.deleteFavorite(productId)
            }
        })

    }

    private fun setup(){
        adapter = BookmarkAdapter()
        binding.rvBookmark.adapter = adapter
        binding.toolbar.icBtnInfo.visibility = View.GONE
        binding.toolbar.tvTitle.visibility = View.VISIBLE
        binding.toolbar.tvToolbarTitle.text = "Мои закладки"
        binding.toolbar.tvToolbarTitle.visibility = View.GONE
        binding.toolbar.icBtnBack.setImageResource(R.drawable.ic_back_colored_24)
        binding.nsBookmark.setOnScrollChangeListener { _, _, scrollY, _, _ ->
            if (scrollY < 100) {
                binding.toolbar.tvToolbarTitle.visibility = View.GONE
            } else {
                binding.toolbar.tvToolbarTitle.visibility = View.VISIBLE
            }
        }
    }

}