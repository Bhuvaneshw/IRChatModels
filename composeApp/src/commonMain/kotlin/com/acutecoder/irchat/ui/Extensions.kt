package com.acutecoder.irchat.ui


fun String.titleCase(): String {
    return split(" ", "_").joinToString(separator = " ") { it[0].uppercase() + it.substring(1) }
}
