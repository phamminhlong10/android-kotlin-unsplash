package com.kotlin.unsplash.util

class OnClickListener<T>(val clickListener: (obj: T) -> Unit){
    fun onClick(obj: T) = clickListener(obj)
}