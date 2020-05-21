package converter

import java.util.*

fun main() {
    val scanner = Scanner(System.`in`)

    do {
        println("Enter what you want to convert (or exit):")
        val command = scanner.next().toLowerCase()
        if (command != "exit") {
            val snum = command.toDoubleOrNull()
            if (snum == null) {
                scanner.nextLine()
                println("Parse error")
                continue
            }
            var sunit = scanner.next().toLowerCase()
            if (sunit == "degree" || sunit == "degrees") sunit = scanner.next().toLowerCase()
            val conj = scanner.next()
            var dunit = scanner.next().toLowerCase()
            if (dunit == "degree" || dunit == "degrees") dunit = scanner.next().toLowerCase()
            val uc = unitConvert(snum, sunit, dunit)

            if (uc.stype == "?" || uc.dtype == "?" || uc.stype != uc.dtype) {
                println("Conversion from ${uc.sunitOut} to ${uc.dunitOut} is impossible")
            } else if (uc.stype == "g" && snum < 0 ) {
                println("Weight shouldn't be negative")
            } else if (uc.stype == "m" && snum < 0) {
                println("Length shouldn't be negative")
            } else {
                println("$snum ${uc.sunitOut} is ${uc.dnumOut} ${uc.dunitOut}")
            }
        }
    } while (command != "exit")
}

class unitConvert(snumIn: Double, sunitIn: String, dunitIn: String) {

    private var sunit: String = toRegularUnit(sunitIn)
    val stype: String = toType(sunit)
    private var dunit: String = toRegularUnit(dunitIn)
    val dtype: String = toType(dunit)
    private val snum: Double = snumIn
    private var rnum: Double = toRegularNum(snum, sunit)
    val dnumOut: Double = toLocalNum(rnum, dunit)
    val sunitOut = if (snum == 1.0 && stype == dtype) toSingleUnit(sunit) else sunit
    val dunitOut = if (dnumOut == 1.0 && stype == dtype) toSingleUnit(dunit) else dunit

    fun toRegularUnit(unit: String): String {
        return when (unit) {
            "m", "meter", "meters" -> "meters"
            "km", "kilometer", "kilometers" -> "kilometers"
            "cm", "centimeter", "centimeters" -> "centimeters"
            "mm", "millimeter", "millimeters" -> "millimeters"
            "mi", "mile", "miles" -> "miles"
            "yd", "yard", "yards" -> "yards"
            "ft", "foot", "feet" -> "feet"
            "in", "inch", "inches" -> "inches"

            "g", "gram", "grams" -> "grams"
            "kg", "kilogram", "kilograms" -> "kilograms"
            "mg", "milligram", "milligrams" -> "milligrams"
            "lb", "pound", "pounds" -> "pounds"
            "oz", "ounce", "ounces" -> "ounces"

            "celsius", "dc", "c" -> "degrees Celsius"
            "fahrenheit", "df", "f" -> "degrees Fahrenheit"
            "kelvin", "kelvins", "k" -> "Kelvins"

            else -> "???"
        }
    }

    fun toType(unit: String): String {
        return when (unit) {
            "meters" -> "m"
            "kilometers" -> "m"
            "centimeters" -> "m"
            "millimeters" -> "m"
            "miles" -> "m"
            "yards" -> "m"
            "feet" -> "m"
            "inches" -> "m"

            "grams" -> "g"
            "kilograms" -> "g"
            "milligrams" -> "g"
            "pounds" -> "g"
            "ounces" -> "g"

            "degrees Celsius" -> "t"
            "Kelvins" -> "t"
            "degrees Fahrenheit" -> "t"

            else -> "?"
        }
    }

    fun toRegularNum(snumIn: Double, sunitIn: String): Double {
        return when (sunitIn) {
            "meters" -> snumIn
            "kilometers" -> snumIn * 1000
            "centimeters" -> snumIn * 0.01
            "millimeters" -> snumIn * 0.001
            "miles" -> snumIn * 1609.35
            "yards" -> snumIn * 0.9144
            "feet" -> snumIn * 0.3048
            "inches" -> snumIn * 0.0254

            "grams" -> snumIn
            "kilograms" -> snumIn * 1000
            "milligrams" -> snumIn * 0.001
            "pounds" -> snumIn * 453.592
            "ounces" -> snumIn * 28.3495

            "degrees Celsius" -> snumIn
            "Kelvins" -> snumIn.minus(273.15)
            "degrees Fahrenheit" -> snumIn.minus(32.0).times(5.0).div(9.0)

            else -> snumIn
        }
    }

    fun toLocalNum(rnumIn: Double, dunitIn: String): Double {
        return when (dunitIn) {
            "meters" -> rnumIn
            "kilometers" -> rnumIn / 1000
            "centimeters" -> rnumIn / 0.01
            "millimeters" -> rnumIn / 0.001
            "miles" -> rnumIn / 1609.35
            "yards" -> rnumIn / 0.9144
            "feet" -> rnumIn / 0.3048
            "inches" -> rnumIn / 0.0254

            "grams" -> rnumIn
            "kilograms" -> rnumIn / 1000
            "milligrams" -> rnumIn / 0.001
            "pounds" -> rnumIn / 453.592
            "ounces" -> rnumIn / 28.3495

            "degrees Celsius" -> rnumIn
            "Kelvins" -> rnumIn + 273.15
            "degrees Fahrenheit" -> rnumIn.times(9.0).div(5.0).plus(+ 32.0)

            else -> rnumIn
        }
    }

    fun toSingleUnit(unit: String): String {
        return when (unit) {
            "meters" -> "meter"
            "kilometers" -> "kilometer"
            "centimeters" -> "centimeter"
            "millimeters" -> "millimeter"
            "miles" -> "mile"
            "yards" -> "yard"
            "feet" -> "foot"
            "inches" -> "inch"

            "grams" -> "gram"
            "kilograms" -> "kilogram"
            "milligrams" -> "milligram"
            "pounds" -> "pound"
            "ounces" -> "ounce"

            "degrees Celsius" -> "degree Celsius"
            "Kelvins" -> "Kelvin"
            "degrees Fahrenheit" -> "degree Fahrenheit"

            else -> "???"
        }
    }
}
