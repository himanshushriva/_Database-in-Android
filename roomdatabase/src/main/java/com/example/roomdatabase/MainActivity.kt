package com.example.roomdatabase

import android.os.Bundle
import android.util.Log
import android.widget.TextView
import androidx.appcompat.app.AppCompatActivity
import androidx.lifecycle.lifecycleScope
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.GlobalScope
import kotlinx.coroutines.launch
import kotlinx.coroutines.withContext
import java.util.Date

class MainActivity : AppCompatActivity() {

    private val TAG: String = "MainActivity"
    lateinit var database: ContactDatabase
    lateinit var textView: TextView

    //@OptIn(DelicateCoroutinesApi::class)  //for GlobalScope
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)

        textView = findViewById(R.id.textView)


        database = ContactDatabase.getDatabase(this)


        loadContactsFromDB()


        //val textView = findViewById<TextView>(R.id.textView)
        findViewById<TextView>(R.id.textView).setOnClickListener {
            GlobalScope.launch {
                database.contactDao().insertContact(Contact(0, "John", "9999", Date(), 1))
            }
            //this loadContactsFromDB will call observer many-many times (creates a loop/chain because of LiveData value getting changed each time)
            //loadContactsFromDB()      //no need to load contacts again because the data is LiveData and being observed already in loadContactsFromDB() because we allready called this onCreate()
        }


        textView.setOnLongClickListener {
            val contactsLiveData = database.contactDao().getContacts()   //Here, contactsLiveData.value = null (not List<Contact>)

            contactsLiveData.observe(this) {//added observer just to collect data from LiveData
                //contactsLiveData.removeObservers(this)      //This doesn't remove the observers instantly so it is not causing any error
                lifecycleScope.launch {
                    database.contactDao().deleteContact(it.last())
                }
                contactsLiveData.removeObservers(this)  //If observer is not removed here then observer will be triggered again because we are changing the database and it will turn into a loop which will delete all the data from DB
            }
            //contactsLiveData.removeObservers(this)      //If I'll remove the observers just after observe(this, observerCallback{}) then the observerCallback will not be executed even if it is written above removeObservers(this)


            //We can also define a new function like (suspend fun getAllContacts() : List<Contact>) which will return List not LiveData
            /*lifecycleScope.launch {
                val contactList = database.contactDao().getAllContacts()
                database.contactDao().deleteContact(contactList.last())
            }*/

            true    //If we return false here, then setOnLongClickListener will be called first and it will call setOnClickListener automatically
        }
    }


    private fun loadContactsFromDB() {
        //Getting actual value from LiveData sent by RoomDatabase by adding a observer
        //val totalContacts: LiveData<List<Contact>> = database.contactDb().getContacts()
        database.contactDao().getContacts().observe(this) {      //getContacts has LiveData as return type so Room automatically run this on background thread

            //this does not work, if String class has plus() method, why can't we use it as append from StringBuilder
            /*val allContacts = String()
            it.forEach { allContacts.plus("$it \n") }
            Log.d("TAG", allContacts)*/

            val allContacts = StringBuilder()
            it.forEach { allContacts.append("$it \n") }
            textView.text = allContacts                 //allContacts is of type StringBuilder, still we can set it to a TextView

            // setting text in textview when LiveData is empty
            if (textView.text.isEmpty()) {
                textView.text = "Hello World!"
            }
        }


        //Getting data from suspend fun getAllContacts() : List<Contact>
        /*lifecycleScope.launch(Dispatchers.IO) {
            val contactList = database.contactDao().getAllContacts()
            Log.d(TAG, "loadContactsFromDB: ${Thread.currentThread().name}")
            val allContacts = StringBuilder()
            contactList.forEach { allContacts.append("$it \n") }

            withContext(Dispatchers.Main) {
                textView.text = allContacts                 //allContacts is of type StringBuilder, still we can set it to a TextView

                // setting text in textview when LiveData is empty
                if (textView.text.isEmpty()) {
                    textView.text = "Hello World!"
                }
            }
        }*/

        //this works, but we should not perform database operations on the main thread thread, because the coroutine is started on the main thread
        /*lifecycleScope.launch {
            val contactList = database.contactDao().getAllContacts()
            Log.d(TAG, "loadContactsFromDB: ${Thread.currentThread().name}")
            val allContacts = StringBuilder()
            contactList.forEach { allContacts.append("$it \n") }
            textView.text = allContacts                 //allContacts is of type StringBuilder, still we can set it to a TextView

            // setting text in textview when LiveData is empty
            if (textView.text.isEmpty()) {
                textView.text = "Hello World!"
            }
        }*/


    }
}