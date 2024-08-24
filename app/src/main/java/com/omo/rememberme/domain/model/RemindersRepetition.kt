package com.omo.rememberme.domain.model

enum class RemindersRepetition {
    OnceADay { override fun toString(): String ="Once a day" },
    ThreeADay { override fun toString(): String =  "Three a day" },
    FiveADay { override fun toString(): String = "Five a day" }
}