package splitties.lifecycle.coroutines

/**
 * Symbols having this annotation may get deprecated, then removed **if** an alternative or similar
 * API makes it into AndroidX (likely in AndroidX Lifecycle KTX
 * [as mentioned in this PR comment by Sergey Vasilinets from the AndroidX team](
https://github.com/Kotlin/kotlinx.coroutines/pull/760#issuecomment-436222483)).
 */
@MustBeDocumented
@Retention(value = AnnotationRetention.BINARY)
@Experimental(level = Experimental.Level.WARNING)
annotation class PotentialFutureAndroidXLifecycleKtxApi
