package com.example.drevmassapp.utils

import androidx.fragment.app.Fragment
import com.example.drevmassapp.domain.repository.NavigationHostProvider

fun Fragment.provideNavigationHos(): NavigationHostProvider? {
    return try{
        activity as NavigationHostProvider
    } catch(e: Exception){
        null
    }
}