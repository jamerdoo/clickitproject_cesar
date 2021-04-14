package com.cesar.cesartestitpoint.utils

import android.app.Activity
import android.content.Context
import android.content.Intent
import android.graphics.drawable.Drawable
import android.net.Uri
import android.util.Log
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.view.inputmethod.InputMethodManager
import android.widget.EditText
import android.widget.ImageView
import android.widget.Toast
import androidx.annotation.LayoutRes
import androidx.core.os.bundleOf
import androidx.fragment.app.Fragment
import androidx.recyclerview.widget.RecyclerView
import com.bumptech.glide.Glide
import com.bumptech.glide.request.RequestOptions
import com.cesar.cesartestitpoint.R
import de.hdodenhof.circleimageview.CircleImageView
import java.lang.Exception
import java.text.SimpleDateFormat
import java.util.*

interface Logger{

    val nameClass: String

    fun logD(message: String){
        Log.d(nameClass,message)
    }

    fun logE(message: String){
        Log.e(nameClass,message)
    }
}

fun Context.printToast (message: String, lenght: Int = Toast.LENGTH_SHORT){
    Toast.makeText(this, message, lenght).show()
}

fun RecyclerView.ViewHolder.printToast (message: String){
    itemView.context.printToast(message)
}

fun ViewGroup.inflate (@LayoutRes layout: Int): View = LayoutInflater
    .from(context)
    .inflate(layout,this,false)

fun ImageView.loadUrl(url: String?) {
    //Glide.with(this).load(url).into(this)
    Glide.with(this)
        .applyDefaultRequestOptions(
            RequestOptions()
                .error(R.drawable.icono_perfil)
        )
        .load(url)
        .into(this)
}

fun ImageView.loadUrl(drawable: Drawable?) {
    //Glide.with(this).load(url).into(this)
    Glide.with(this)
        .applyDefaultRequestOptions(
            RequestOptions()
                .error(R.drawable.icono_perfil)
        )
        .load(drawable)
        .into(this)
}

fun CircleImageView.loadUrl(url: String?) {
    //Glide.with(this).load(url).into(this)
    Glide.with(this)
        .applyDefaultRequestOptions(
            RequestOptions()
                .error(R.drawable.icono_perfil)
        )
        .load(url)
        .into(this)
}

fun CircleImageView.loadUrl(uri: Uri) {
    Glide.with(this)
        .applyDefaultRequestOptions(
            RequestOptions()
                .error(R.drawable.icono_perfil)
        )
        .load(uri)
        .into(this)
}

inline fun <reified T : Activity> Context._startActivity(){
    val intent = Intent(this, T::class.java)
    startActivity(intent)
}

inline fun <reified T : Activity> Context._startActivityNoHistory(){
    val intent = Intent(this, T::class.java)
    intent.flags = Intent.FLAG_ACTIVITY_NEW_TASK or Intent.FLAG_ACTIVITY_CLEAR_TASK
    startActivity(intent)
}

inline fun <reified T : Activity> Context._startActivity(vararg  pairs: Pair<String,Any?>){
    Intent(this, T::class.java).apply {
        putExtras(bundleOf(*pairs))
    }.also (::startActivity)
}

fun Fragment.hideKeyboard() {
    view?.let { activity?.hideKeyboard(it) }
}

fun Activity.hideKeyboard() {
    hideKeyboard(currentFocus ?: View(this))
}

fun Context.hideKeyboard(view: View) {
    val inputMethodManager = getSystemService(Activity.INPUT_METHOD_SERVICE) as InputMethodManager
    inputMethodManager.hideSoftInputFromWindow(view.windowToken, 0)
}

fun EditText.notIsEmpty(value:String): Boolean{

    if(value.isEmpty()){
        return false
    }
    return true
}


fun formatDate(date: String?): String{
    return if(date.isNullOrEmpty())
        ""
    else {
        try {
            val formatter = SimpleDateFormat("EEE MMM dd HH:mm:ss zzz yyyy", Locale.ENGLISH)
            val new = SimpleDateFormat("dd-MM-yyyy HH:mm", Locale.getDefault())
            new.format(formatter.parse(date)?:"")
        }catch (e: Exception){
            Log.d("_formatDate", "--->_formatDate: "+e.localizedMessage)
            return ""
        }
    }
}

fun convertToDate(date: String?): Date? {
    return if(date.isNullOrEmpty())
        null
    else {
        try {
            val formatter = SimpleDateFormat("EEE MMM dd HH:mm:ss zzz yyyy", Locale.ENGLISH)
            val mDate = formatter.parse(date)
            Log.d("--->", "formatDate: $mDate")
            mDate
        }catch (e: Exception){
            Log.d("_formatDate", "--->_formatDate: "+e.localizedMessage)
            return null
        }
    }
}