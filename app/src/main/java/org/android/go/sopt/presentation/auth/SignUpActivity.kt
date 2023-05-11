package org.android.go.sopt.presentation.auth

import android.content.Intent
import android.os.Bundle
import android.util.Log
import androidx.activity.viewModels
import dagger.hilt.android.AndroidEntryPoint
import org.android.go.sopt.R
import org.android.go.sopt.data.model.MyInfo
import org.android.go.sopt.databinding.ActivitySignUpBinding
import org.android.go.sopt.util.BindingActivity
import org.android.go.sopt.util.hideKeyboard
import org.android.go.sopt.util.showSnackbar

@AndroidEntryPoint
class SignUpActivity : BindingActivity<ActivitySignUpBinding>(R.layout.activity_sign_up) {
    private val viewModel: SignUpViewModel by viewModels()
    private var signUpInfo: MyInfo? = null

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding.vm = viewModel
        hideKeyBoard()
        clickSignUpBtn()
    }

    private fun clickSignUpBtn() {
        binding.btnSignupRegister.setOnClickListener {
            signUpInfo = viewModel.getInfo()
            Log.d("SignUp", "signUpInfo :: $signUpInfo")
            viewModel.saveUserInfo()
            observeSignUpResult()
        }
    }

    private fun observeSignUpResult() {
        viewModel.isSignUpSuccess.observe(this) {
            if (viewModel.isSignUpSuccess.value == true) {
                intentToSignInActivity()
            } else {
                binding.root.showSnackbar("회원가입 실패ㅠ")
            }
            Log.d("signUp", "isSignUpSuccess :: ${viewModel.isSignUpSuccess.value}")
        }
    }

    private fun intentToSignInActivity() {
        val intent = Intent(this, SignInActivity::class.java).apply {
            Log.d("SignUp", "signUpInfo :: $signUpInfo")
            putExtra("myInfo", signUpInfo)
        }
        setResult(RESULT_OK, intent)
        finish()
    }

    private fun hideKeyBoard() {
        binding.clSignupMain.setOnClickListener {
            it.hideKeyboard()
        }
    }
}
