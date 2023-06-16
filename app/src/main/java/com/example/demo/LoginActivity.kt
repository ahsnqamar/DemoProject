package com.example.demo

import android.os.Bundle
import android.view.View
import android.widget.Toast
import androidx.appcompat.app.AppCompatActivity
import androidx.databinding.BindingAdapter
import androidx.databinding.DataBindingUtil
import com.example.demo.databinding.ActivityLoginBinding

import com.example.demo.viewmodel.LoginViewModel


class LoginActivity : AppCompatActivity() {

    private lateinit var activityLoginBinding: ActivityLoginBinding
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)

        activityLoginBinding = DataBindingUtil.setContentView(this,R.layout.activity_login)
        activityLoginBinding.viewModel = LoginViewModel()
        activityLoginBinding.executePendingBindings()

    }
}

@BindingAdapter("toastMessage")
fun runMe(view: View, message: String?) {
    if (message != null) Toast
        .makeText(
            view.context, message,
            Toast.LENGTH_SHORT
        )
        .show()
}