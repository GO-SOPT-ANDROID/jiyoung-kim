package org.android.go.sopt.presentation.auth

import android.content.Intent
import android.os.Build
import android.os.Bundle
import android.util.Log
import android.widget.Toast
import androidx.activity.result.contract.ActivityResultContracts
import androidx.activity.viewModels
import com.google.android.material.snackbar.Snackbar
import org.android.go.sopt.R
import org.android.go.sopt.data.model.MyInfo
import org.android.go.sopt.databinding.ActivitySignInBinding
import org.android.go.sopt.presentation.MyPageActivity
import org.android.go.sopt.util.BindingActivity

class SignInActivity : BindingActivity<ActivitySignInBinding>(R.layout.activity_sign_in) {
    private val viewModel: AuthViewModel by viewModels()
    private var myInfo: MyInfo? = null

    private val launcher =
        registerForActivityResult(ActivityResultContracts.StartActivityForResult()) { result ->
            if (result.resultCode == RESULT_OK) {
                myInfo = if (Build.VERSION.SDK_INT >= 33) {
                    result.data?.getParcelableExtra("myInfo", MyInfo::class.java)
                } else {
                    result.data?.getParcelableExtra("myInfo")
                }
                Snackbar.make(binding.root, "회원가입 완료!", Snackbar.LENGTH_SHORT)
                    .show()
            }
        }

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding.lifecycleOwner = this
        binding.vm = viewModel
        clickButton()
    }

    private fun clickButton() {
        with(binding) {
            // 로그인 버튼
            btnSigninBottom.setOnClickListener {
                if (myInfo?.id != null && myInfo?.pwd != null) {
                    viewModel.isSignInValid(myInfo!!.id, myInfo!!.pwd)
                    viewModel.isSignTextInValid.observe(this@SignInActivity) {
                        Log.d("SignIn", viewModel.isSignTextInValid.value.toString())
                        if (viewModel.isSignTextInValid.value == true) {
                            Toast.makeText(this@SignInActivity, "로그인 성공!", Toast.LENGTH_SHORT)
                                .show()
                            Intent(this@SignInActivity, MyPageActivity::class.java).apply {
                                putExtra("myInfo", viewModel.getInfo())
                                setResult(RESULT_OK, intent)
                                startActivity(this)
                                finish()
                            }
                        } else {
                            Toast.makeText(this@SignInActivity, "로그인 실패!", Toast.LENGTH_SHORT)
                                .show()
                        }
                    }
                } else {
                    Toast.makeText(this@SignInActivity, "로그인 정보를 입력해주세요!", Toast.LENGTH_SHORT)
                        .show()
                }
            }

            // 회원가입 버튼
            btnSigninSignup.setOnClickListener {
                launcher.launch(Intent(this@SignInActivity, SignUpActivity::class.java))
            }
        }
    }
}
