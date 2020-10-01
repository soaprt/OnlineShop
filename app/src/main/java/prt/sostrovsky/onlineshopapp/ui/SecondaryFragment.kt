package prt.sostrovsky.onlineshopapp.ui

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.activity.OnBackPressedCallback
import androidx.fragment.app.Fragment

open class SecondaryFragment(private val layoutId: Int) : Fragment() {
    private lateinit var backButtonCallback: OnBackPressedCallback

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setBackButtonCallback()
    }

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        return inflater.inflate(layoutId, container,false)
    }

    override fun onActivityCreated(savedInstanceState: Bundle?) {
        super.onActivityCreated(savedInstanceState)
        setToolbarButtons()
    }

    private fun setBackButtonCallback() {
        backButtonCallback = object : OnBackPressedCallback(true) {
            override fun handleOnBackPressed() {
                if (isEnabled) {
                    isEnabled = false
                    requireActivity().onBackPressed()
                }
            }
        }
        requireActivity().onBackPressedDispatcher.addCallback(this, backButtonCallback)
    }

    private fun setToolbarButtons() {
        (activity as MainActivity).apply {
            backButtonEnable(callback = backButtonCallback)
            favoritesButtonDisable()
        }
    }
}