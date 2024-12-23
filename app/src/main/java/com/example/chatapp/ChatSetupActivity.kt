package com.example.chatapp

import android.app.Activity
import android.content.Intent
import android.net.Uri
import android.os.Bundle
import android.widget.Button
import android.widget.EditText
import android.widget.ImageView
import android.widget.Toast
import androidx.activity.result.contract.ActivityResultContracts
import androidx.appcompat.app.AppCompatActivity

class ChatSetupActivity : AppCompatActivity() {

    private lateinit var nicknameEditText: EditText
    private lateinit var chooseImageButton: Button
    private lateinit var profileImageView: ImageView
    private var selectedImageUri: Uri? = null

    private val resultLauncher = registerForActivityResult(ActivityResultContracts.StartActivityForResult()) { result ->
        if (result.resultCode == Activity.RESULT_OK) {
            selectedImageUri = result.data?.data
            profileImageView.setImageURI(selectedImageUri)
        }
    }


    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_chat_setup) // Make sure you have this layout file

        nicknameEditText = findViewById(R.id.nicknameEditText)
        chooseImageButton = findViewById(R.id.chooseImageButton)
        profileImageView = findViewById(R.id.profileImageView)
        val submitButton = findViewById<Button>(R.id.submitButton)


        chooseImageButton.setOnClickListener {
            val intent = Intent(Intent.ACTION_PICK)
            intent.type = "image/*"
            resultLauncher.launch(intent)
        }

        submitButton.setOnClickListener {
            val nickname = nicknameEditText.text.toString()

            if (nickname.isEmpty()) {
                Toast.makeText(this, "Please enter a nickname", Toast.LENGTH_SHORT).show()
                return@setOnClickListener
            }

            val intent = Intent(this, ChatActivity::class.java)
            intent.putExtra("NICKNAME", nickname)
            selectedImageUri?.let { intent.putExtra("IMAGE_URI", it) }
            startActivity(intent)
        }
    }
}