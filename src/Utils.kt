import java.io.File
import java.math.BigInteger
import java.security.MessageDigest

/**
 * Reads lines from the given input txt file.
 */
fun readInput(name: String) = File("src/$name.txt").readLines()

/**
 * Converts string to md5 hash.
 */
fun String.md5(): String = BigInteger(1, MessageDigest.getInstance("MD5").digest(toByteArray())).toString(16)

data class Debug(var enabled: Boolean = false)

fun Debug.debugln() = run { if (enabled) println() }

fun Debug.debugln(obj: Any) = run { if (enabled) println(obj) }

fun Debug.debug(obj: Any) = run { if (enabled) print(obj) }

val debug = Debug()