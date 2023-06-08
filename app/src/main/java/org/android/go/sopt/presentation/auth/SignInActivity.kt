package org.android.go.sopt.presentation.auth

import android.content.Intent
import android.os.Bundle
import android.util.Log
import androidx.activity.result.contract.ActivityResultContracts
import androidx.activity.viewModels
import dagger.hilt.android.AndroidEntryPoint
import org.android.go.sopt.MainActivity
import org.android.go.sopt.R
import org.android.go.sopt.data.entity.MyInfo
import org.android.go.sopt.databinding.ActivitySignInBinding
import org.android.go.sopt.util.*

@AndroidEntryPoint
class SignInActivity : BindingActivity<ActivitySignInBinding>(R.layout.activity_sign_in) {
    private val viewModel: SignInViewModel by viewModels()
    private var signUpInfo: MyInfo? = null

    private val launcher =
        registerForActivityResult(ActivityResultContracts.StartActivityForResult()) { result ->

            if (result.resultCode == RESULT_OK) {
                val data = result.data ?: return@registerForActivityResult
                data.getParcelable("myInfo", MyInfo::class.java).apply {
                    this?.let {
                        signUpInfo = it
                        viewModel.getSignUpInfo(it)
                        binding.root.showSnackbar("회원가입 완료!")
                    }
                }
            } else {
                return@registerForActivityResult
            }
        }

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding.vm = viewModel
        viewModel.signIn()
        observeAutoSignIn()
        hideKeyBoard()
        clickButton()
    }

    private fun hideKeyBoard() {
        binding.root.setOnClickListener {
            it.hideKeyboard()
        }
    }

    private fun observeAutoSignIn() {
        viewModel.isAutoSignInValid.observe(this) {
            when (it) {
                true -> {
                    intentToMainActivity()
                }
                else -> {
                    Log.d("SignIn", "자동 로그인 오류")
                }
            }
        }
    }

    private fun intentToMainActivity() {
        Intent(this, MainActivity::class.java).apply {
            startActivity(this)
        }
    }

    private fun clickButton() {
        with(binding) {
            // 로그인 버튼
            btnSigninBottom.setOnClickListener {
                Log.d(
                    "SignIn",
                    "회원 데이터 :: ${viewModel.id.value?.toString()} ooo ${viewModel.pwd.value?.toString()} 000 ${signUpInfo?.name} 000 ${signUpInfo?.skill}"
                )
                viewModel.signInValid(viewModel.id.value.toString(), viewModel.pwd.value.toString())
                observeSignInResult()
            }

            // 회원가입 버튼
            tvSigninSignup.setOnClickListener {
                launcher.launch(Intent(this@SignInActivity, SignUpActivity::class.java))
            }
        }
    }

    private fun observeSignInResult() {
        viewModel.isSignInSuccess.observe(this) { isPwdValid ->
            if (isPwdValid) {
                binding.root.showToast("로그인 성공!")
                intentToMainActivity()
            } else {
                binding.root.showSnackbar("로그인 실패ㅠ")
            }
        }
    }
}
