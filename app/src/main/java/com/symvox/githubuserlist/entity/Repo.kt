package com.symvox.githubuserlist.entity

import com.google.gson.annotations.SerializedName

data class Repo(
    val name: String,
    val language: Language?,
    val description: String,
    val stargazersCount: Int,
    val htmlUrl: String
)

data class Language(
    val name: String,
    val color: Int
)