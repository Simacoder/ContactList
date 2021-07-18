package com.example.contactlist.core.model

import android.graphics.Bitmap
import android.os.Parcelable
import kotlinx.parcelize.Parcelize

@Parcelize
class User (
    var name: String,
    var number: String,
    var image : Bitmap? = null
    ): Parcelable