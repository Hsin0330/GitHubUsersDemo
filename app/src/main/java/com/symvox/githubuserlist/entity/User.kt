package com.symvox.githubuserlist.entity

import kotlinx.serialization.Serializable

@Serializable
data class User(
    val id: String,
    val name: String,
    val fullName: String,
    val avatarUrl: String? = null,
    val followers: Int? = null,
    val following: Int? = null
)