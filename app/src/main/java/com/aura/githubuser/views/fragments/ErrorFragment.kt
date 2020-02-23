package com.aura.githubuser.views.fragments

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.Fragment
import com.aura.githubuser.R
import kotlinx.android.synthetic.main.fragment_error.*

class ErrorFragment(val listener: Listener? = null) : Fragment() {

    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        return layoutInflater.inflate(R.layout.fragment_empty_data, container, false)
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        btnTryAgain.setOnClickListener {
            listener?.let {
                it.onBtnTryAgainClicked()
            }
        }
    }

    public interface Listener {
        fun onBtnTryAgainClicked()
    }
}