package xyz.ismailnurudeen.prefdbexample

import android.Manifest
import android.app.Activity
import android.content.Intent
import android.content.pm.PackageManager
import android.graphics.Bitmap
import android.os.Build
import android.os.Bundle
import android.provider.MediaStore
import android.util.Log
import android.widget.Toast
import androidx.appcompat.app.AppCompatActivity
import androidx.core.app.ActivityCompat
import androidx.core.content.ContextCompat
import kotlinx.android.synthetic.main.activity_main.*
import xyz.ismailnurudeen.prefdb.PrefDB

class MainActivity : AppCompatActivity() {
    var image: Bitmap? = null
    private val IMAGE_RC = 101
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)
        signup_btn.setOnClickListener {
            signUp()
            startActivity(Intent(this, DetailsActivity::class.java))
        }

        upload_img_btn.setOnClickListener {
            if (checkPermission(Manifest.permission.WRITE_EXTERNAL_STORAGE, IMAGE_RC)) {
                val imgReadIntent = Intent(Intent.ACTION_PICK)
                imgReadIntent.type = "image/*"
                startActivityForResult(imgReadIntent, IMAGE_RC)
            }
        }
    }

    override fun onActivityResult(requestCode: Int, resultCode: Int, data: Intent?) {
        super.onActivityResult(requestCode, resultCode, data)
        if (requestCode == IMAGE_RC && resultCode == Activity.RESULT_OK) {
            val selectedPicUri = data?.data
            image = MediaStore.Images.Media.getBitmap(contentResolver, selectedPicUri)
            imagview.setImageBitmap(image)
            upload_img_btn.alpha = 0.0F
        }
    }

    private fun signUp() {
        val prefDb = PrefDB(this, "MY_LOGIN_DB")
        val name = name_editText.text.toString()
        val password = password_editText.text.toString()
        val email = email_editText.text.toString()
        if (name.isEmpty() || email.isEmpty() || password.isEmpty()) {
            return
        }
        if (prefDb.insertObject("user", User(name, email, password))) {
            Log.d("SIGN_UP", "user object saved successfully")
        } else {
            Log.d("SIGN_UP", "user object saved failed")
        }

        if (image != null) {
            if (prefDb.insertBitmap("profile_image", image)) {
                Log.d("SIGN_UP", "image saved successfully")
            } else {
                Log.d("SIGN_UP", "image save failed")
            }
        }
        val interests = ArrayList<String>()
        if (like_fashion.isChecked) interests.add("Fashion")
        if (like_football.isChecked) interests.add("Football")
        if (like_comp.isChecked) interests.add("Computers")
        if (like_music.isChecked) interests.add("Music")
        if (interests.isNotEmpty()) {
            if (prefDb.insertList("interests_list", interests as List<Any>?)) {
                Log.d("SIGN_UP", "interests saved successfully")
            } else {
                Log.d("SIGN_UP", "interests save failed")
            }
        }
    }

    data class User(val name: String, val email: String, val password: String)

    private fun checkPermission(permission: String, requestCode: Int): Boolean {
        if (Build.VERSION.SDK_INT >= 23) {
            if (ContextCompat.checkSelfPermission(
                    this,
                    permission
                ) == PackageManager.PERMISSION_GRANTED
            ) {
                Log.v(packageName, "Permission is granted")
                return true
            } else {
                if (ActivityCompat.shouldShowRequestPermissionRationale(this, permission)) {
                    Toast.makeText(
                        this,
                        "We need this permission for the app to work properly",
                        Toast.LENGTH_LONG
                    ).show()
                    ActivityCompat.requestPermissions(this, arrayOf(permission), requestCode)
                } else {
                    ActivityCompat.requestPermissions(this, arrayOf(permission), requestCode)
                }
                Log.v(packageName, "Permission is revoked")
                return false
            }
        } else {
            //permission is automatically granted on sdk<23 upon installation
            Log.v(packageName, "Permission is granted")
            return true
        }
    }

    override fun onRequestPermissionsResult(
        requestCode: Int,
        permissions: Array<String>,
        grantResults: IntArray
    ) {
        super.onRequestPermissionsResult(requestCode, permissions, grantResults)
        if (grantResults[0] == PackageManager.PERMISSION_GRANTED) {
            Log.i(packageName, "Permission: " + permissions[0] + "was " + grantResults[0])
            if (requestCode == IMAGE_RC) {
                val intent = Intent(Intent.ACTION_PICK)
                intent.type = "image/*"
                startActivityForResult(intent, IMAGE_RC)
            }
        }
    }
}
