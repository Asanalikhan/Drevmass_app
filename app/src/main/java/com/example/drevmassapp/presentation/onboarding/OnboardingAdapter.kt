package com.example.drevmassapp.presentation.onboarding

import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.recyclerview.widget.RecyclerView
import com.example.drevmassapp.domain.model.OnboardingModel
import com.example.drevmassapp.databinding.ItemViewpagerOnboardingBinding

class OnboardingAdapter(): RecyclerView.Adapter<OnboardingAdapter.OnboardingViewHolder>() {

    private val onboardingModelList = mutableListOf<OnboardingModel>()
    fun submitList(list: List<OnboardingModel>) {
        onboardingModelList.clear()
        onboardingModelList.addAll(list)
        notifyDataSetChanged()
    }
    inner class OnboardingViewHolder(private val binding: ItemViewpagerOnboardingBinding): RecyclerView.ViewHolder(binding.root) {
        fun bindItem(onboardingModel: OnboardingModel){
            binding.imageViewOnboarding.setImageResource(onboardingModel.imageId)
            binding.tvTitleOnboarding.text = onboardingModel.title
            binding.tvDescriptionOnboarding.text = onboardingModel.description
        }
    }


    override fun onCreateViewHolder(
        parent: ViewGroup,
        viewType: Int
    ): OnboardingAdapter.OnboardingViewHolder {
        return OnboardingViewHolder(ItemViewpagerOnboardingBinding.inflate(LayoutInflater.from(parent.context), parent, false))
    }

    override fun onBindViewHolder(holder: OnboardingAdapter.OnboardingViewHolder, position: Int) {
        holder.bindItem(onboardingModelList[position])
    }

    override fun getItemCount(): Int {
        return onboardingModelList.size
    }
}