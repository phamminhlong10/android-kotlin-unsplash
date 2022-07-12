package com.kotlin.unsplash.core.util

class OnClickListener<T>(val clickListener: (obj: T) -> Unit){
    fun onClick(obj: T) = clickListener(obj)
}