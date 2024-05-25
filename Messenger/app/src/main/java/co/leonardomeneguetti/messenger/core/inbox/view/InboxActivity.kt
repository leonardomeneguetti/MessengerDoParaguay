package co.leonardomeneguetti.messenger.core.inbox.view

import android.content.Intent
import android.graphics.Bitmap
import android.graphics.Canvas
import android.graphics.Color
import android.graphics.Paint
import android.graphics.PorterDuff
import android.graphics.PorterDuffXfermode
import android.graphics.Rect
import android.graphics.drawable.BitmapDrawable
import android.os.Bundle
import android.util.TypedValue
import androidx.appcompat.app.AppCompatActivity
import androidx.core.content.ContextCompat
import androidx.fragment.app.commit
import co.leonardomeneguetti.messenger.R
import co.leonardomeneguetti.messenger.core.model.User
import co.leonardomeneguetti.messenger.core.newmessage.view.NewMessageFragment
import co.leonardomeneguetti.messenger.core.profile.view.ProfileActvity
import co.leonardomeneguetti.messenger.databinding.ActivityInboxBinding

interface ToolbarCallback {
    fun goToNewMessage()
    fun goToProfile()
}
class InboxActivity : AppCompatActivity(), ToolbarCallback {
    private lateinit var binding: ActivityInboxBinding
    val user = User.mock

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = ActivityInboxBinding.inflate(layoutInflater)
        setContentView(binding.root)
        setSupportActionBar(binding.toolbar)
    }

    override fun goToNewMessage() {
        val fragment = NewMessageFragment()
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

    override fun goToProfile() {
        val intent = Intent(this, ProfileActvity::class.java)
        intent.putExtra(ProfileActvity.KEY_USER, user)
        startActivity(intent)
    }
}