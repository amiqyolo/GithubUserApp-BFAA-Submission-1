package com.android.githubuserapp.model

import android.os.Parcelable
import kotlinx.parcelize.Parcelize

@Parcelize
data class User(
    var avatar: Int,
    var company: String,
    var follower: Int,
    var following: Int,
    var location: String,
    var name: String,
    var repository: Int,
    var username: String,
) : Parcelable