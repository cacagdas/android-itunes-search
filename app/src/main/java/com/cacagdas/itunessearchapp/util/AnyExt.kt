package com.cacagdas.itunessearchapp.util

import android.os.Build
import androidx.annotation.RequiresApi
import java.lang.Exception
import java.time.LocalDateTime
import java.time.ZonedDateTime
import java.time.format.DateTimeFormatter

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

@RequiresApi(Build.VERSION_CODES.O)
fun Any.formatDate(dateString: String?) : String {
    val zdt: ZonedDateTime = ZonedDateTime.parse(dateString)
    val ldt: LocalDateTime = zdt.toLocalDateTime()
    val formatter = DateTimeFormatter.ofPattern("dd MMMM yyyy")

    return ldt.format(formatter)
}