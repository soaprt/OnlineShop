package prt.sostrovsky.onlineshopapp.ui.products

import androidx.lifecycle.ViewModel
import androidx.paging.PagingData
import kotlinx.coroutines.ExperimentalCoroutinesApi
import kotlinx.coroutines.flow.Flow
import prt.sostrovsky.onlineshopapp.data.ProductRepository
import prt.sostrovsky.onlineshopapp.service.response.ProductDTO

class ProductsViewModel(private val repository: ProductRepository) : ViewModel() {
    private var productsResult: Flow<PagingData<ProductDTO>>? = null

    @ExperimentalCoroutinesApi
    fun getProducts(): Flow<PagingData<ProductDTO>> {
        productsResult = repository.getProducts()
            // .cachedIn(viewModelScope)
        return productsResult as Flow<PagingData<ProductDTO>>
    }
}


/*
import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import kotlinx.coroutines.CoroutineScope
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.SupervisorJob
import kotlinx.coroutines.launch
import prt.sostrovsky.onlineshopapp.service.WebServiceUnavailableException
import prt.sostrovsky.onlineshopapp.service.response.ProductDTO
import prt.sostrovsky.onlineshopapp.repository.ProductRepository


class ProductsViewModel : ViewModel() {

    */
/**
     * The job for all coroutines started by this ViewModel.
     *//*

    private val viewModelJob = SupervisorJob()

    */
/**
     * This is the main scope for all coroutines launched by MainViewModel.
     *//*

    private val viewModelScope = CoroutineScope(viewModelJob + Dispatchers.Main)

    private val selectedProduct = MutableLiveData<ProductDTO>()
    var selectedProductIsLoaded = false

    val webServiceIsUnavailable = MutableLiveData<Boolean>(false)

    fun fetchProduct(id: Int): LiveData<ProductDTO> {
        if (!selectedProductIsLoaded) {
            viewModelScope.launch {
                selectedProduct.value = ProductRepository.getProductBy(id)
                selectedProductIsLoaded = true
            }
        }
        return selectedProduct
    }

    fun fetchProducts(): LiveData<List<ProductDTO>> {
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

    */
/**
     * Cancel all coroutines when the ViewModel is cleared
     *//*

    override fun onCleared() {
        super.onCleared()
        viewModelJob.cancel()
    }
}*/
