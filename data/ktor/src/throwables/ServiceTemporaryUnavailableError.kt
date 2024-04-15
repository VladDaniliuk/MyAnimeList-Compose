package data.ktor.throwables

class ServiceTemporaryUnavailableError(private val mess: String) : Throwable(mess) {
    override fun getLocalizedMessage() = mess
    override val message = mess
}
