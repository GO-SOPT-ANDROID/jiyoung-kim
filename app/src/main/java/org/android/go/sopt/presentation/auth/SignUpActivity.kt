package org.android.go.sopt.presentation.auth

import android.content.Intent
import android.os.Bundle
import android.util.Log
import androidx.activity.viewModels
import org.android.go.sopt.R
import org.android.go.sopt.databinding.ActivitySignUpBinding
import org.android.go.sopt.util.BindingActivity

class SignUpActivity : BindingActivity<ActivitySignUpBinding>(R.layout.activity_sign_up) {
    private val viewModel: AuthViewModel by viewModels()

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding.lifecycleOwner = this
        binding.vm = viewModel
        clickSignUpBtn()
    }

    private fun clickSignUpBtn() {
        binding.btnSignupRegister.setOnClickListener {
            Log.d("SignUp", viewModel.getInfo().toString())
            viewModel.isSignUpValid.observe(this) {
                val intent = Intent(this, SignInActivity::class.java).apply {
                    putExtra("myInfo", viewModel.getInfo())
                }
                setResult(RESULT_OK, intent)
                finish()
            }
        }
    }
}
