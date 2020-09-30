package prt.sostrovsky.onlineshopapp.datasource

interface DataSource<T> {
    suspend fun getData() : T?
}