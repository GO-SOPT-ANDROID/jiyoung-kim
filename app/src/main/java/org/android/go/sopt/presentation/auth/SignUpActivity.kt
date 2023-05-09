package org.android.go.sopt.presentation.auth

import android.content.Intent
import android.os.Bundle
import android.util.Log
import androidx.activity.viewModels
import org.android.go.sopt.R
import org.android.go.sopt.data.model.MyInfo
import org.android.go.sopt.databinding.ActivitySignUpBinding
import org.android.go.sopt.util.BindingActivity
import org.android.go.sopt.util.ViewModelFactory
import org.android.go.sopt.util.hideKeyboard

class SignUpActivity : BindingActivity<ActivitySignUpBinding>(R.layout.activity_sign_up) {
    private val viewModel: SignUpViewModel by viewModels { (ViewModelFactory(applicationContext)) }
    private var signUpInfo: MyInfo? = null

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding.lifecycleOwner = this
        binding.vm = viewModel
        hideKeyBoard()
        clickSignUpBtn()
    }

    private fun clickSignUpBtn() {
        binding.btnSignupRegister.setOnClickListener {
            Log.d("SignUp", viewModel.getInfo().toString())
            signUpInfo = viewModel.getInfo()
            if (signUpInfo != null) {
                viewModel.saveUserInfo()
                val intent = Intent(this, SignInActivity::class.java).apply {
                    Log.d("SignUp", signUpInfo.toString())
                    putExtra("myInfo", signUpInfo)
                }
                setResult(RESULT_OK, intent)
                finish()
            }
        }
    }

    private fun hideKeyBoard() {
        binding.clSignupMain.setOnClickListener {
            it.hideKeyboard()
        }
    }
}
