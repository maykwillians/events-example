package com.maykmenezes.desafiosicred.util.ui

import android.view.View.GONE
import android.view.View.VISIBLE

object FakeScreenBehavior {

    object FakeCommonScreenBehavior {
        val loading = Triple(VISIBLE, GONE, GONE)
        val success = Triple(GONE, VISIBLE, GONE)
        val error = Triple(GONE, GONE, VISIBLE)
    }

    object FakeCommonScreenBehaviorWithoutErrorLayout {
        val loading = Pair(VISIBLE, GONE)
        val terminated = Pair(GONE, VISIBLE)
    }
}