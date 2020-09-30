package prt.sostrovsky.onlineshopapp.repository

import prt.sostrovsky.onlineshopapp.network.connection.NetworkConnection
import prt.sostrovsky.onlineshopapp.repository.fetcher.ProductFetcher
import prt.sostrovsky.onlineshopapp.repository.fetcher.ProductOfflineFetcher
import prt.sostrovsky.onlineshopapp.repository.fetcher.ProductOnlineFetcher

object ProductFactory {
    fun getFactory() : ProductFetcher {
        return if (NetworkConnection.isAvailable) {
            ProductOnlineFetcher
        } else {
            ProductOfflineFetcher
        }
    }
}