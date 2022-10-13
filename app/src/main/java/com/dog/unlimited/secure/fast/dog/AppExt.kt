package com.dog.unlimited.secure.fast.dog

import android.content.Context
import android.content.Intent
import android.content.pm.PackageManager
import android.net.ConnectivityManager
import android.net.Uri
import android.util.DisplayMetrics
import android.util.Log
import android.view.View
import android.widget.Toast
import androidx.appcompat.app.AlertDialog
import com.dog.unlimited.secure.fast.R
import com.dog.unlimited.secure.fast.ss0907server.Ss0907LocalInfo
import java.lang.Exception


fun Context.height(){
    val metrics: DisplayMetrics = resources.displayMetrics
    val td = metrics.heightPixels / 760f
    val dpi = (160 * td).toInt()
    metrics.density = td
    metrics.scaledDensity = td
    metrics.densityDpi = dpi
}


fun View.show(show:Boolean){
    visibility=if (show) View.VISIBLE else View.GONE
}

private fun Context.getNetWorkStatus(): Int {
    val connectivityManager = getSystemService(Context.CONNECTIVITY_SERVICE) as ConnectivityManager
    val activeNetworkInfo = connectivityManager.activeNetworkInfo
    if (activeNetworkInfo != null && activeNetworkInfo.isConnected) {
        if (activeNetworkInfo.type == ConnectivityManager.TYPE_WIFI) {
            return 2
        } else if (activeNetworkInfo.type == ConnectivityManager.TYPE_MOBILE) {
            return 0
        }
    } else {
        return 1
    }
    return 1
}

fun Context.showNoNetDialog():Boolean{
    return if (getNetWorkStatus()==1){
        AlertDialog.Builder(this).apply {
            setMessage("You are not currently connected to the network")
            setPositiveButton("sure", null)
            show()
        }
        true
    }else{
        false
    }
}

fun Context.show0907Toast(text:String){
    Toast.makeText(this, text, Toast.LENGTH_LONG).show()
}

fun getServerIcon(c:String)=when(c){
    "Japan"->R.drawable.japan
    "UnitedStates"->R.drawable.unitedstates
    else -> R.drawable.fast
}

fun Context.contact(){
    try {
        val intent = Intent(Intent.ACTION_SENDTO)
        intent.data= Uri.parse("mailto:")
        intent.putExtra(Intent.EXTRA_EMAIL, Ss0907LocalInfo.EMAIL_STR)
        startActivity(intent)
    }catch (e: Exception){
        show0907Toast("Contact us by email：${Ss0907LocalInfo.EMAIL_STR}")
    }
}

fun Context.share() {
    val pm = packageManager
    val packageName=pm.getPackageInfo(packageName, PackageManager.GET_ACTIVITIES).packageName
    val intent = Intent(Intent.ACTION_SEND)
    intent.type = "text/plain"
    intent.putExtra(
        Intent.EXTRA_TEXT,
        "https://play.google.com/store/apps/details?id=${packageName}"
    )
    startActivity(Intent.createChooser(intent, "share"))
}

fun Context.update() {
    val packName = packageManager.getPackageInfo(packageName, PackageManager.GET_ACTIVITIES).packageName
    val intent = Intent(Intent.ACTION_VIEW).apply {
        data = Uri.parse(
            "https://play.google.com/store/apps/details?id=$packName"
        )
    }
    startActivity(intent)
}


fun log0907(string: String){
    Log.e("qwer",string)
}