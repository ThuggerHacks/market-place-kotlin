package com.example.marketplace.Adapter.Listeners

import com.example.marketplace.Model.Category

class OnCategoryClickListener(val clickListener:(category:Category) -> Unit) {
    fun onClick(category: Category) = clickListener(category)
}