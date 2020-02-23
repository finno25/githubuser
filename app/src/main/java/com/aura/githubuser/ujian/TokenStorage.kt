package com.aura.githubuser.ujian

interface TokenStorage {
    fun retrieveToken(): String?
}