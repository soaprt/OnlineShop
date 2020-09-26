package prt.sostrovsky.onlineshopapp.ui

import android.os.Bundle
import androidx.navigation.findNavController
import androidx.navigation.ui.NavigationUI
import prt.sostrovsky.onlineshopapp.R

class MainActivity : BaseActivity(R.id.rootLayout) {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)
        setNavController()
    }

    private fun setNavController() {
        navController = this.findNavController(R.id.myNavHostFragment)
        NavigationUI.setupActionBarWithNavController(this, navController)
    }
}