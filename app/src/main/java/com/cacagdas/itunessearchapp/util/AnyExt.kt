package com.cacagdas.itunessearchapp.util

import java.lang.Exception

fun Any.tryCatch(tryBlock : () -> Unit ,
                 catchBlock : ((t:Throwable) -> Unit)? = null,
                 finalBlock : (() -> Unit)? = null){
    try {
        tryBlock()
    }catch (ex : Exception){
        catchBlock?.invoke(ex)
    }finally {
        finalBlock?.invoke()
    }
}