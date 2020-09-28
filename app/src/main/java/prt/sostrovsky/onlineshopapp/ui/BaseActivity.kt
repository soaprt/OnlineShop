package prt.sostrovsky.onlineshopapp.ui

import android.annotation.SuppressLint
import android.view.View
import android.widget.ImageView
import androidx.activity.OnBackPressedCallback
import androidx.appcompat.app.AppCompatActivity
import androidx.navigation.NavController
import androidx.navigation.NavDirections
import com.google.android.material.snackbar.Snackbar
import io.reactivex.disposables.Disposable
import prt.sostrovsky.onlineshopapp.R
import prt.sostrovsky.onlineshopapp.network.connection.NetworkConnection

open class BaseActivity(private val rootLayoutId: Int) : AppCompatActivity() {
    lateinit var navController: NavController
    lateinit var toolbarBackButton: ImageView

    private var snackBarOffline: Snackbar? = null
    private var networkDisposable: Disposable? = null

    override fun onResume() {
        super.onResume()
        networkStateSubscribe()
        checkOffline(NetworkConnection.isAvailable)
    }

    override fun onPause() {
        super.onPause()
        networkStateUnsubscribe()
    }

    @SuppressLint("CheckResult")
    private fun networkStateSubscribe () {
        networkDisposable = NetworkConnection.stateObservable.subscribe { it ->
            checkOffline(it)
        }
    }

    private fun checkOffline(isOffline: Boolean) {
        when(isOffline) {
            true -> hideShackBarOffline()
            false -> showShackBarOffline()
        }
    }

    private fun networkStateUnsubscribe() {
        networkDisposable?.dispose()
    }

    private fun showShackBarOffline() {
        val message = getString(R.string.msg_you_are_offline)

        snackBarOffline = generateSnackBar(findViewById(rootLayoutId), message,
            Snackbar.LENGTH_INDEFINITE)
        snackBarOffline?.let {
            it.duration = Snackbar.LENGTH_INDEFINITE
            it.show()
        }
    }

    private fun generateSnackBar(view: View, message: String, duration: Int) : Snackbar {
        val result = Snackbar.make(view, message, duration)
        val snackBarView: View = result.view
        snackBarView.setBackgroundColor(applicationContext.resources.getColor(R.color.colorPrimary))

        return result
    }

    private fun hideShackBarOffline() {
        snackBarOffline?.dismiss()
    }

    fun showSnackBarEvent(message: String) {
        generateSnackBar(findViewById(rootLayoutId), message, Snackbar.LENGTH_SHORT).show()
    }

    fun moveTo(action: NavDirections) {
        navController.navigate(action)
    }

    fun backButtonEnable(callback: OnBackPressedCallback) {
        toolbarBackButton.apply {
            visibility = View.VISIBLE
            isClickable = true
            setOnClickListener {
                callback.handleOnBackPressed()
            }
        }
    }

    fun backButtonDisable() {
        toolbarBackButton.apply {
            visibility = View.INVISIBLE
            isClickable = false
            setOnClickListener(null)
        }
    }
}
