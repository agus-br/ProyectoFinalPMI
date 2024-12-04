import android.os.Parcelable
import kotlinx.parcelize.Parcelize

@Parcelize
data class ReminderInfo(
    val id: Int,
    val title: String,
    val description: String,
    val timeInMillis: Long
) : Parcelable