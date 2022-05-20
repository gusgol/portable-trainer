package tech.gusgol.core

import javax.inject.Qualifier


@Qualifier
@Retention(AnnotationRetention.RUNTIME)
annotation class Dispatcher(val dispatchers: Dispatchers)

enum class Dispatchers {
    IO
}