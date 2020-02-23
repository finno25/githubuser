package com.aura.githubuser.views.activities

import android.os.Bundle
import android.util.Log
import androidx.appcompat.app.AppCompatActivity
import androidx.fragment.app.Fragment
import androidx.fragment.app.FragmentTransaction
import com.aura.githubuser.BuildConfig
import com.aura.githubuser.R
import com.aura.githubuser.views.fragments.LoadingFragment
import com.aura.githubuser.views.fragments.SearchFragment
import okhttp3.Interceptor
import okhttp3.Request
import okhttp3.ResponseBody


class MainActivity : AppCompatActivity() {

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)

        replaceFragment(SearchFragment())
    }

    fun addFragment(fragment: Fragment?) {
        try {
            fragment?.let {
                val ft: FragmentTransaction = supportFragmentManager.beginTransaction()
                if (fragment !is LoadingFragment) {
                    ft.addToBackStack(null)
                }
                ft.add(R.id.layout, it).commit()
            }
        } catch (ex: Exception) {

        }
    }

    fun replaceFragment(fragment: Fragment?) {
        try {
            fragment?.let {
                val fragmentTransaction = supportFragmentManager.beginTransaction()
                fragmentTransaction.replace(R.id.layout, it).commit()
                supportFragmentManager.popBackStack()
            }
        } catch (ex: Exception) {

        }
    }

    fun removeFragment(fragment: Fragment?) {
        try {
            fragment?.let {
                val fragmentTransaction = supportFragmentManager.beginTransaction()
                fragmentTransaction.remove(it)
                fragmentTransaction.commit()
            }
        } catch (ex: Exception) {

        }
    }
}
