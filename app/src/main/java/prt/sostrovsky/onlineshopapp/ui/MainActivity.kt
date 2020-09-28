package prt.sostrovsky.onlineshopapp.ui

import android.os.Bundle
import androidx.appcompat.widget.Toolbar
import androidx.navigation.findNavController
import prt.sostrovsky.onlineshopapp.R

class MainActivity : BaseActivity(R.id.rootLayout) {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)
        setNavController()
        setToolBar()
    }

    private fun setNavController() {
        navController = this.findNavController(R.id.myNavHostFragment)
    }

    private fun setToolBar() {
        val toolbar = findViewById<Toolbar>(R.id.toolbar)
        setSupportActionBar(toolbar)
        setToolbarButtons()
    }

    private fun setToolbarButtons() {
        toolbarBackButton = findViewById(R.id.flToolbarLeftButton)
    }
}
