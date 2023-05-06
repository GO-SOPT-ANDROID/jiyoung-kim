package org.android.go.sopt

import android.app.Application
import org.android.go.sopt.data.datasource.local.AuthDataSource

/*
SharedPreferences 클래스는 앱에 있는 다른 액티비티보다 먼저 생성되어야 다른 곳에 데이터를 넘겨줄 수 있다.
따라서 Application에 해당하는 클래스를 생성한 뒤, 전역 변수로 SharedPreferences를 가지고 있어야 한다.
Application()을 상속받는 MyApplication 클래스를 생성하여, onCreate()보다 먼저 prefs를 초기화 해준다.
 */
class SoptApplication : Application() {
    companion object {
        lateinit var prefs: AuthDataSource
    }

    override fun onCreate() {
        prefs = AuthDataSource(applicationContext)
        super.onCreate()
    }
}
