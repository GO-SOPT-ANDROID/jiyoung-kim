package org.android.go.sopt.util

import androidx.lifecycle.Observer

/*
 - 1회성으로만 이벤트를 전달해주기 위해서는 별도의 처리 과정이 필요함
 - View에게 '이건 단일 이벤트야'라고 알려주기 위해서는 데이터를 Event Wrapper로 감싸주면 됨
 -  Event Wrapper를 Activity에서 옵저빙 할 때는 일반 Observer가 아닌 EventObserver를 사용해서 옵저빙 해준다.
 */

class Event<T>(private val content: T) {

    // 단일 이벤트가 이미 실행됐는지를 판단해줌
    // 초기값은 false였다가 단일 이벤트가 실행되면 hasBeenHandled 값을 true로 변경해줌
    private var hasBeenHandled = false

    fun getContentIfNotHandled(): T? {
        return if (hasBeenHandled) {
            null
        } else {
            hasBeenHandled = true
            content
        }
    }
}

class EventObserver<T>(private val onEventUnhandledContent: (T) -> Unit) : Observer<Event<T>> {
    override fun onChanged(value: Event<T>) {
        value.getContentIfNotHandled()?.let {
            onEventUnhandledContent(it)
        }
    }
}
