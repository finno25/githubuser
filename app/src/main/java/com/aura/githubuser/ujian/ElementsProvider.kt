package com.aura.githubuser.ujian

import io.reactivex.Single

interface ElementsProvider {
    fun loadElements(): Single<List<Element>>
}

data class Element(
    val id: String,
    val title: String
)