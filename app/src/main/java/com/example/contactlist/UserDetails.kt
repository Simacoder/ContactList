package com.example.contactlist

import android.content.Intent
import android.net.Uri
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.provider.MediaStore
import android.widget.Button
import android.widget.ImageView
import android.widget.TextView
import com.example.contactlist.core.model.User
import com.squareup.picasso.Picasso

class UserDetails : AppCompatActivity() {
    lateinit var imageView: ImageView
    lateinit var button: Button
    private val pickImage = 100
    private var imageUri: Uri? = null
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_user_details)

        //link text to view code
        val textView = findViewById<TextView>(R.id.textView)
        val imageView = findViewById<ImageView>(R.id.profileImage)
        val button = findViewById<Button>(R.id.takePicture)
        val shareProfile = findViewById<Button>(R.id.shareProfile)

        //get data
        val user = intent.getParcelableExtra<User>("User")

        //add user name to text view
        textView.text = user?.name

        //Picasso.get().load(user?.image).into(imageView)

        button.setOnClickListener {
            this.displayPhoto()
        }

        shareProfile.setOnClickListener{
            if (user !=null){
                this.shareProfile(user)
            }
        }
    }

    private fun displayPhoto(){
        val intent = Intent(Intent.ACTION_PICK, MediaStore.Images.Media.INTERNAL_CONTENT_URI)
        startActivityForResult(intent, pickImage)
    }

    override fun onActivityResult(requestCode: Int, resultCode: Int, data: Intent?) {
        super.onActivityResult(requestCode, resultCode, data)
        if(resultCode== RESULT_OK && requestCode ==pickImage){
            imageUri = data?.data
            imageView.setImageURI(imageUri)
        }
    }

    fun shareProfile(user: User){
        val sendIntent: Intent = Intent().apply {
            action = Intent.ACTION_SEND
            putExtra(Intent.EXTRA_TEXT, user.name)
            type= "text/plain"
        }

        val shareIntent = Intent.createChooser(sendIntent, null)
        startActivity(shareIntent)

    }
}