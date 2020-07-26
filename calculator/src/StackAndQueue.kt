//push and pop functions added via extensions created by Nicholas Barron
fun <T> MutableList<T>.push(value: T) = add(count(), value)

fun <T> MutableList<T>.pop(): T = removeAt(count()-1)

fun <T> MutableList<T>.peek(): T = last()

fun <T> MutableList<T>.peekOrNull(): T? = if (isEmpty()) null else peek()

fun <T> MutableList<T>.popOrNull(): T? = if(isEmpty()) null else pop()

fun <T> MutableList<T>.enqueue (value: T)= this.add(value)

fun <T> MutableList<T>.dequeue(): T = removeAt(0)

fun <T> MutableList<T>.dequeueOrNull(): T? = if(isEmpty()) null else dequeue()