package prt.sostrovsky.onlineshopapp.repository

import prt.sostrovsky.onlineshopapp.network.connection.NetworkConnection
import prt.sostrovsky.onlineshopapp.repository.fetcher.ProductsFetcher
import prt.sostrovsky.onlineshopapp.repository.fetcher.ProductsOfflineFetcher
import prt.sostrovsky.onlineshopapp.repository.fetcher.ProductsOnlineFetcher

object ProductsFactory {
    fun getFactory() : ProductsFetcher {
        return if (NetworkConnection.isAvailable) {
            ProductsOnlineFetcher
        } else {
            ProductsOfflineFetcher
        }
    }
}