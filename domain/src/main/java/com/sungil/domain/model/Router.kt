package com.sungil.domain.model

import android.os.Bundle

interface Router {
    fun navigation(target: String, args: Bundle = Bundle())
}