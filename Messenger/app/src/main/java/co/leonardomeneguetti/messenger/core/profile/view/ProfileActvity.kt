package co.leonardomeneguetti.messenger.core.profile.view

import android.os.Build
import android.os.Bundle
import android.os.PersistableBundle
import android.view.MenuItem
import android.widget.ImageView
import androidx.activity.result.PickVisualMediaRequest
import androidx.activity.result.contract.ActivityResultContracts
import androidx.appcompat.app.AppCompatActivity
import androidx.core.content.ContextCompat
import co.leonardomeneguetti.messenger.core.model.User
import co.leonardomeneguetti.messenger.databinding.ActivityProfileBinding
import co.leonardomeneguetti.messenger.extensions.toCircle

class ProfileActvity: AppCompatActivity() {
    private lateinit var binding: ActivityProfileBinding

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = ActivityProfileBinding.inflate(layoutInflater)
        setContentView(binding.root)

        setSupportActionBar(binding.toolbar)
        supportActionBar?.title = "Me"
        supportActionBar?.setDisplayHomeAsUpEnabled(true)

        val user = if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.TIRAMISU) {
            intent?.extras?.getParcelable(KEY_USER, User::class.java)
        } else {
            intent?.extras?.getParcelable(KEY_USER)
        }
        if(user == null){
            throw IllegalArgumentException("User not found!")
        }

        user.profileImageUrl?.let {
            val drawable = ContextCompat.getDrawable(this, user.profileImageUrl)
            binding.imgProfile.setImageDrawable(drawable?.toCircle(resources, 32))
            binding.imgProfile.scaleType = ImageView.ScaleType.CENTER_CROP
        }
        binding.txtName.text = user.fullName

        binding.imgProfile.setOnClickListener {
            pickerMedia.launch(PickVisualMediaRequest(ActivityResultContracts.PickVisualMedia.ImageOnly))
        }
    }

    private val pickerMedia = registerForActivityResult(ActivityResultContracts.PickVisualMedia()) { uri ->
        binding.imgProfile.setImageURI(uri)
    }

    override fun onOptionsItemSelected(item: MenuItem): Boolean {
        if (item.itemId == android.R.id.home){
            finish()
            return true
        }

        return false
    }

    companion object {
        const val KEY_USER = "key_user"
    }
}