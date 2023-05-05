package org.android.go.sopt.presentation.main.mypage

import android.os.Bundle
import android.util.Log
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.Fragment
import org.android.go.sopt.data.model.entity.MyInfo
import org.android.go.sopt.databinding.FragmentMypageBinding

class MypageFragment : Fragment() {
    private var myInfo: MyInfo? = null

    private var _binding: FragmentMypageBinding? = null
    private val binding: FragmentMypageBinding
        get() = requireNotNull(_binding) { "앗 ! _binding이 null이다 !" }

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
    }

    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        _binding = FragmentMypageBinding.inflate(inflater, container, false)
        return binding.root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        myInfo?.let {
            setText(it)
            Log.d("Home", it.name)
            Log.d("Home", it.specialty)
        }
    }

    private fun setText(myInfo: MyInfo) {
        with(binding) {
            "이름: ${myInfo.name}".also { tvMypageName.text = it }
            "특기: ${myInfo.specialty}".also { tvMypageSpecialty.text = it }
        }
    }

    override fun onDestroy() {
        super.onDestroy()
        _binding = null
    }
}
