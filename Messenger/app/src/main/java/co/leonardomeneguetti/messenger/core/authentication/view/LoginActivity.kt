package co.leonardomeneguetti.messenger.core.authentication.view

import android.os.Bundle
import androidx.appcompat.app.AppCompatActivity
import androidx.fragment.app.commit
import co.leonardomeneguetti.messenger.R

interface LoginCallback {
    fun onCreateAccountClicked()
    fun onLoginClcked()
}
class LoginActivity : AppCompatActivity(), LoginCallback {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_login)
    }

    override fun onCreateAccountClicked() {
        val fragment = SignUpFragment()
        supportFragmentManager.commit {
            setCustomAnimations(
                R.anim.slide_in,
                R.anim.fade_out,
                R.anim.fade_in,
                R.anim.slide_out
            )
            replace(R.id.fragment_container_view, fragment)
            addToBackStack(null)
        }
    }

    override fun onLoginClcked() {
        supportFragmentManager.popBackStack()
    }
}