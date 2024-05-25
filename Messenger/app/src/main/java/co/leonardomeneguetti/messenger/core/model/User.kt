package co.leonardomeneguetti.messenger.core.model

import android.os.Parcelable
import androidx.annotation.DrawableRes
import co.leonardomeneguetti.messenger.R
import kotlinx.parcelize.Parcelize
import java.util.UUID

@Parcelize
data class User(val id: UUID = UUID.randomUUID(), val fullName: String, val email: String, @DrawableRes val profileImageUrl: Int?): Parcelable {
    companion object {
        val mock = User(
            fullName = "Leonardo Meneguetti",
            email = "email@email.com",
            profileImageUrl = R.drawable.eu
        )
    }
}