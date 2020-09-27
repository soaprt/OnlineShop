package prt.sostrovsky.onlineshopapp.ui.products

import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import kotlinx.coroutines.CoroutineScope
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.SupervisorJob
import kotlinx.coroutines.launch
import prt.sostrovsky.onlineshopapp.network.response.ProductDTO
import prt.sostrovsky.onlineshopapp.repository.ProductRepository
import timber.log.Timber

class ProductsViewModel :  ViewModel() {

    /**
    * The job for all coroutines started by this ViewModel.
    */
    private val viewModelJob = SupervisorJob()

    /**
     * This is the main scope for all coroutines launched by MainViewModel.
     */
    private val viewModelScope = CoroutineScope(viewModelJob + Dispatchers.Main)

    fun fetchProduct(productId: Int) {
        Timber.e("ProductsViewModel: fetchProduct():" +
                "\n\tproductId: $productId")
    }

    fun fetchProducts() : LiveData<List<ProductDTO>> {
        val products = MutableLiveData<List<ProductDTO>>()

        viewModelScope.launch {
            products.value = ProductRepository.getProducts(0, 10)
        }

        return products
    }

    /**
     * Cancel all coroutines when the ViewModel is cleared
     */
    override fun onCleared() {
        super.onCleared()
        viewModelJob.cancel()
    }
}