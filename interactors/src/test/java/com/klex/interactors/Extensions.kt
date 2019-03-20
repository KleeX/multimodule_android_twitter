package com.klex.interactors

import org.junit.Assert.assertTrue

fun checkException(funToCheck: () -> Unit) =
    assertTrue(
        try {
            funToCheck()
            true
        } catch (e: Throwable) {
            e.printStackTrace()
            false
        }
    )