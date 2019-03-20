package com.klex.storage

import org.junit.Assert
import org.junit.Test
import java.lang.reflect.Field
import java.lang.reflect.Modifier

class ModelsTests {

    @Test
    fun testModels() {
        TweetPendingEntity::class.java.declaredFields.forEach {
            Assert.assertTrue(isFinal(it))
        }
    }

    private fun isFinal(field: Field): Boolean {
        println(field)
        return Modifier.isFinal(field.modifiers)
    }
}