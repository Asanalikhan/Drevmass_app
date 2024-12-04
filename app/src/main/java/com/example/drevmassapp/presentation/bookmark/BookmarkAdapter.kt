package com.example.drevmassapp.presentation.bookmark

import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.recyclerview.widget.RecyclerView
import com.example.drevmassapp.databinding.ItemBookmarkBinding
import com.example.drevmassapp.domain.model.FavoriteResponse
import com.example.drevmassapp.domain.repository.OnItemClickListener
import com.example.drevmassapp.domain.repository.OnQuantityClickListener

class BookmarkAdapter : RecyclerView.Adapter<BookmarkAdapter.BookmarkViewHolder>() {

    private lateinit var itemClickListener: OnItemClickListener
    private lateinit var bookmarkClickListener: OnQuantityClickListener
    private val favorites = mutableListOf<FavoriteResponse>()
    private var adapter = ItemBookmarkAdapter()

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): BookmarkViewHolder {
        return BookmarkViewHolder(
            ItemBookmarkBinding.inflate(
                LayoutInflater.from(parent.context),
                parent,
                false
            )
        )
    }

    override fun getItemCount(): Int {
        return favorites.size
    }

    override fun onBindViewHolder(holder: BookmarkViewHolder, position: Int) {
        holder.bind(favorites[position])
    }

    fun setFavorites(list: List<FavoriteResponse>) {
        favorites.clear()
        favorites.addAll(list)
        notifyDataSetChanged()
    }

    inner class BookmarkViewHolder(val binding: ItemBookmarkBinding) :
        RecyclerView.ViewHolder(binding.root) {
        fun bind(favorite: FavoriteResponse) {
            binding.tvTitle.text = favorite.courseName
            binding.rvBookmark.adapter = adapter
            adapter.setProducts(favorite.lessons)
            binding.root.setOnClickListener {
                itemClickListener.onItemClick(favorite.courseId)
            }
            adapter.setOnBookmarkListener(object : OnQuantityClickListener {
                override fun onQuantityChanged(newQuantity: Int, productId: Int, increase: Boolean) {
                    bookmarkClickListener.onQuantityChanged(1, productId, increase)
                    notifyDataSetChanged()
                }
            })
        }
    }

    fun setOnClickListener(listener: OnItemClickListener) {
        itemClickListener = listener
    }
    fun bookmarkClickListener(listener: OnQuantityClickListener){
        bookmarkClickListener = listener
    }
}