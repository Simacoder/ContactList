package com.example.contactlist

import android.content.Intent
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.provider.ContactsContract
import android.widget.Button
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView
import com.example.contactlist.core.model.User
import com.example.contactlist.core.model.adapter.userAdapter

class MainActivity : AppCompatActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)

        val recyclerView = findViewById<RecyclerView>(R.id.recyclerView)
        //link recycler view to layout manager
        recyclerView.layoutManager = LinearLayoutManager(this)

        val button = findViewById<Button>(R.id.readContact)

        button.setOnClickListener {

            val contactList: MutableList<User> = ArrayList()

            val contacts = contentResolver.query(
                ContactsContract.CommonDataKinds.Phone.CONTENT_URI,
                null,
                null,
                null,
                null
            )

            while (contacts!!.moveToNext()) {
                val name =
                    contacts.getString(contacts.getColumnIndex(ContactsContract.CommonDataKinds.Phone.DISPLAY_NAME))
                val number =
                    contacts.getString(contacts.getColumnIndex(ContactsContract.CommonDataKinds.Phone.NUMBER))
                val obj = User(name, number)
                obj.name = name
                obj.number = number

                contactList.add(obj)
            }


            //create the adapter and link with recyclerview
            val adapter = userAdapter(contactList)
            recyclerView.adapter = userAdapter(contactList)


            //pass the user clicked onto the next screen
            adapter.onItemClick = { user ->
                val intent = Intent(this, UserDetails::class.java)
                intent.putExtra("User", user)
                startActivity(intent)

            }
        }

    }
}


