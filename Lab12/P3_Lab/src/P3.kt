fun <K, V> reverseMap(map : Map<K, V>): Map<V, K>
{
    return map.entries.associate { (key, value) -> value to key }
}

fun main()
{
    val originalMap = mapOf(1 to "abc", 2 to "def", 3 to "ghi")
    val reversedMap = reverseMap(originalMap)
    println(reversedMap)
}
