package com.restpoint

interface Platform {
    val name: String
}

expect fun getPlatform(): Platform