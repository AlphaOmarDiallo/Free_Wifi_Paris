package com.alphaomardiallo.freewifiparis

interface Platform {
    val name: String
}

expect fun getPlatform(): Platform