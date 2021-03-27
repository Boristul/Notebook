package com.boristul.utils.viewbinding

import android.content.Context
import android.util.Log
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.viewbinding.ViewBinding
import java.lang.reflect.Method
import kotlin.reflect.KClass

inline fun <reified T : ViewBinding> ViewGroup.inflateViewBinding(
    context: Context = this.context,
    attachToRoot: Boolean = true
): T = T::class.inflate(LayoutInflater.from(context), this, attachToRoot)

inline fun <reified T : ViewBinding> Context.inflateViewBinding(
    parent: ViewGroup? = null,
    attachToRoot: Boolean = parent != null
): T = T::class.inflate(LayoutInflater.from(this), parent, attachToRoot)

inline fun <reified T : ViewBinding> LayoutInflater.inflateViewBinding(
    parent: ViewGroup? = null,
    attachToRoot: Boolean = parent != null
): T = T::class.inflate(this, parent, attachToRoot)

fun <T : ViewBinding> KClass<T>.inflate(
    inflater: LayoutInflater,
    parent: ViewGroup?,
    attachToRoot: Boolean
): T {
    val inflateMethod = java.getInflateMethod()
    @Suppress("UNCHECKED_CAST")
    return if (inflateMethod.parameterTypes.size > 2) {
        inflateMethod.invoke(null, inflater, parent, attachToRoot)
    } else {
        if (!attachToRoot) {
            Log.d(
                "ViewBinding",
                "attachToRoot is always true for ${java.simpleName}.inflate"
            )
        }
        inflateMethod.invoke(null, inflater, parent)
    } as T
}

private val inflateMethodsCache = mutableMapOf<Class<out ViewBinding>, Method>()

private fun Class<out ViewBinding>.getInflateMethod(): Method =
    inflateMethodsCache.getOrPut(this) {
        declaredMethods.find { method ->
            val parameterTypes = method.parameterTypes
            method.name == "inflate" &&
                parameterTypes[0] == LayoutInflater::class.java &&
                parameterTypes.getOrNull(1) == ViewGroup::class.java &&
                (parameterTypes.size == 2 || parameterTypes[2] == Boolean::class.javaPrimitiveType)
        }
            ?: error("Method ${this.simpleName}.inflate(LayoutInflater, ViewGroup[, boolean]) not found.")
    }

inline fun <reified T : ViewBinding> View.getBinding(): T = T::class.bind(this)
