package com.srmanager.core.common.util

fun doubleToWords(number: Double): String {
    val integerPart = number.toInt()
    val decimalPart = (number - integerPart) * 100 // considering two decimal places

    val integerWords = numberToWords(integerPart.toLong())
    val decimalWords = numberToWords(decimalPart.toLong())

    return if (decimalPart > 0) {
        "$integerWords dot $decimalWords Only"
    } else {
        "$integerWords Only"
    }
}

fun numberToWords(number: Long): String {
    if (number == 0L) return "zero"

    val unitsArray = arrayOf("", "One", "Two", "Three", "Four", "Five", "Six", "Seven", "Eight", "Nine")
    val tensArray = arrayOf("", "Ten", "Twenty", "Thirty", "Forty", "Fifty", "Sixty", "Seventy", "Eighty", "Ninety")
    val teensArray = arrayOf("Eleven", "Twelve", "Thirteen", "Fourteen", "Fifteen", "Sixteen", "Seventeen", "Eighteen", "Nineteen")
    val powersOfTen = arrayOf("", "Thousand", "Million", "Billion", "Trillion")

    val parts = ArrayList<String>()

    var num = number
    var i = 0
    while (num > 0) {
        if (num % 1000 != 0L) {
            val part = convertLessThanOneThousand(num % 1000, unitsArray, tensArray, teensArray)
            parts.add(part + " " + powersOfTen[i])
        }
        num /= 1000
        i++
    }

    parts.reverse()
    return parts.joinToString(" ").trim()
}

fun convertLessThanOneThousand(number: Long, unitsArray: Array<String>, tensArray: Array<String>, teensArray: Array<String>): String {
    var number = number
    val sb = StringBuilder()

    if (number % 100 < 20) {
        sb.append(unitsArray[(number % 100).toInt()])
        number /= 100
    } else {
        sb.append(unitsArray[(number % 10).toInt()])
        number /= 10

        sb.insert(0, tensArray[(number % 10).toInt()] + " ")
        number /= 10
    }
    if (number != 0L) {
        sb.insert(0, unitsArray[number.toInt()] + " hundred ")
    }

    return sb.toString().trim()
}