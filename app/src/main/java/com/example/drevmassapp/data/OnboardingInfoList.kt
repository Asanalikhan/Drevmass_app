package com.example.drevmassapp.data

import android.content.Context
import com.example.drevmassapp.R
import com.example.drevmassapp.data.model.OnboardingModel

object OnboardingInfoList {

    fun getOnboardingModelList(context: Context): List<OnboardingModel> {
        return listOf(
            OnboardingModel(
                R.drawable.img_onboarding_1,
                context.getString(R.string.onboarding_title_1),
                context.getString(R.string.onboarding_description)
            ),
            OnboardingModel(
                R.drawable.img_onboarding_2,
                context.getString(R.string.onboarding_title_2),
                context.getString(R.string.onboarding_description)
            ),
            OnboardingModel(
                R.drawable.img_onboarding_3,
                context.getString(R.string.onboarding_title_3),
                context.getString(R.string.onboarding_description)
            ),
        )
    }
}
