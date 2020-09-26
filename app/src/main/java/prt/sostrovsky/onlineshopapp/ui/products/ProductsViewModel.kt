package prt.sostrovsky.onlineshopapp.ui.products

import androidx.lifecycle.ViewModel
import kotlinx.coroutines.CoroutineScope
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.SupervisorJob

class ProductsViewModel :  ViewModel() {
    /**
    * The job for all coroutines started by this ViewModel.
    */
    private val viewModelJob = SupervisorJob()

    /**
     * This is the main scope for all coroutines launched by MainViewModel.
     */
    private val viewModelScope = CoroutineScope(viewModelJob + Dispatchers.Main)

//    private val _appInitComplete = MutableLiveData<Boolean>()
//    val appInitComplete: LiveData<Boolean>
//        get() = _appInitComplete

//    init {
//        viewModelScope.launch {
//            _appInitComplete.value = Repository.init()
//        }
//    }

    /**
     * Cancel all coroutines when the ViewModel is cleared
     */
    override fun onCleared() {
        super.onCleared()
        viewModelJob.cancel()
    }
}