package com.omo.rememberme.domain.model

enum class ThemeMode {
    LIGHT {
        override fun toString(): String = "Light"
    },
    DARK {
        override fun toString(): String = "Dark"
    },
    SYSTEM {
        override fun toString(): String = "System Default"
    }
}
