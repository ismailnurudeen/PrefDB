package xyz.ismailnurudeen.prefdbexample

import android.os.Bundle
import androidx.appcompat.app.AppCompatActivity
import kotlinx.android.synthetic.main.activity_details.*
import xyz.ismailnurudeen.prefdb.PrefDB

class DetailsActivity : AppCompatActivity() {

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_details)
        val prefDb = PrefDB(this, "MY_LOGIN_DB")
        val user = prefDb.readObject(
            "user",
            MainActivity.User("null", "null", "null")
        ) as MainActivity.User
        name_tv.text = user.name
        email_tv.text = user.email
        img_view.setImageDrawable(prefDb.readDrawable("profile_image", null))
        var interestsStr = ""
        for (like in prefDb.readList("interests_list", arrayListOf())) {
            if (like.toString().isNotEmpty()) {
                interestsStr += "${like},"
            }
        }
        if (!interestsStr.isNotEmpty().equals(",")) {
            interests_tv.text = interestsStr
        } else {
            interests_tv.text = "No interests..."
        }
    }
}
