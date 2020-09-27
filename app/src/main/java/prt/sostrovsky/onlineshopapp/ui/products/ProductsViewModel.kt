package prt.sostrovsky.onlineshopapp.ui.products

import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import kotlinx.coroutines.CoroutineScope
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.SupervisorJob
import kotlinx.coroutines.launch
import prt.sostrovsky.onlineshopapp.network.WebServiceUnavailableException
import prt.sostrovsky.onlineshopapp.network.response.ProductDTO
import prt.sostrovsky.onlineshopapp.repository.ProductRepository

class ProductsViewModel :  ViewModel() {

    /**
    * The job for all coroutines started by this ViewModel.
    */
    private val viewModelJob = SupervisorJob()

    /**
     * This is the main scope for all coroutines launched by MainViewModel.
     */
    private val viewModelScope = CoroutineScope(viewModelJob + Dispatchers.Main)

    val webServiceIsUnavailable = MutableLiveData<Boolean>(false)

    fun fetchProduct(id: Int) : LiveData<ProductDTO> {
        val product = MutableLiveData<ProductDTO>()

        viewModelScope.launch {
            product.value = ProductRepository.getProductBy(id)
        }

        return product
    }

    fun fetchProducts() : LiveData<List<ProductDTO>> {
        val products = MutableLiveData<List<ProductDTO>>()

        viewModelScope.launch {
            try {
                products.value = ProductRepository.getProducts(0, 10)
                webServiceIsUnavailable.value = false
            } catch (ex: WebServiceUnavailableException) {
                webServiceIsUnavailable.value = true
            }
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