package com.example.marketplace.Adapter.Listeners

import com.example.marketplace.Model.Product

class OnProductClickListener(val clickListener:(product:Product) -> Unit)  {
    fun onClick(product: Product) = clickListener(product)
}