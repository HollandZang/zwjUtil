package com.holland

import java.util.*
import java.util.stream.Collectors

/**
 * 零宽字符加密
 */
object ZWJ {
    private const val _0 = '\u200b'
    private const val _1 = '\u200c'

    //    private static final char _10 = '\u200d';
    private const val split = '\uFEFF'

    //    private static final String secret = "秘密";
    fun encode(str: String, secret: String): String {
        val list: MutableList<String> = ArrayList()
        for (c in secret.toCharArray()) {
            val s = Integer.toBinaryString(c.toInt())
            list.add(s)
        }
        val collect = list.stream().map { s: String ->
            val newS = StringBuilder()
            for (c in s.toCharArray()) {
                newS.append(if (c.toInt() == 48) _0 else _1)
            }
            newS.toString()
        }.collect(Collectors.toList())
        return str + collect.stream().collect(Collectors.joining(split.toString(), split.toString(), split.toString()))
    }

    /**
     * @return (明文,密文)
     */
    fun decode(str: String): Pair<String, String> {
        val list: MutableList<String> = ArrayList()
        var s: String? = null
        val stringBuilder = StringBuilder()
        for (c in str.toCharArray()) {
            when (c) {
                split -> {
                    s = if (s == null) {
                        ""
                    } else {
                        list.add(s)
                        ""
                    }
                }
                _0 -> {
                    s += "0"
                }
                _1 -> {
                    s += "1"
                }
                else -> {
                    stringBuilder.append(c)
                }
            }
        }

        val secret = list.stream().map { obj: String? -> BinstrToChar(obj!!) }.map { obj: Char -> obj.toString() }
            .collect(Collectors.joining())
        return Pair(stringBuilder.toString(), secret)
    }

    private fun BinstrToChar(binStr: String): Char {
        val temp = BinstrToIntArray(binStr)
        var sum = 0
        for (i in temp.indices) {
            sum += temp[temp.size - 1 - i] shl i
        }
        return sum.toChar()
    }

    private fun BinstrToIntArray(binStr: String): IntArray {
        val temp = binStr.toCharArray()
        val result = IntArray(temp.size)
        for (i in temp.indices) {
            result[i] = (temp[i] - 48).toInt()
        }
        return result
    }
}
