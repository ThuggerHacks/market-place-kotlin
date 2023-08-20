import com.example.marketplace.Model.Product

interface OnMyProductClickListener {
    fun onView(product: Product)
    fun onDelete(product: Product)
    fun onEdit(product: Product)
}
