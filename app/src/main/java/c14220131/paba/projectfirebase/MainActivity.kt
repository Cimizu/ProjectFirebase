package c14220131.paba.projectfirebase

import android.os.Bundle
import android.provider.ContactsContract.Data
import android.util.Log
import android.widget.ArrayAdapter
import android.widget.Button
import android.widget.EditText
import android.widget.ListView
import androidx.activity.enableEdgeToEdge
import androidx.appcompat.app.AppCompatActivity
import androidx.core.view.ViewCompat
import androidx.core.view.WindowInsetsCompat
import com.google.firebase.Firebase
import com.google.firebase.firestore.FirebaseFirestore
import com.google.firebase.firestore.firestore

class MainActivity : AppCompatActivity() {
    val db = Firebase.firestore
    var DataProvinsi = ArrayList<daftarProvinsi>()
    lateinit var lvAdapter : ArrayAdapter<daftarProvinsi>
    lateinit var _etProvinsi : EditText
    lateinit var _etIbukota : EditText
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        enableEdgeToEdge()
        setContentView(R.layout.activity_main)
        ViewCompat.setOnApplyWindowInsetsListener(findViewById(R.id.main)) { v, insets ->
            val systemBars = insets.getInsets(WindowInsetsCompat.Type.systemBars())
            v.setPadding(systemBars.left, systemBars.top, systemBars.right, systemBars.bottom)
            insets
        }
        _etProvinsi = findViewById<EditText>(R.id.etProvinsi)
        _etIbukota = findViewById<EditText>(R.id.etIbukota)

        val _btnSimpan = findViewById<Button>(R.id.btSimpan)
        val _lvData = findViewById<ListView>(R.id.lvData)

        lvAdapter = ArrayAdapter(
            this, android.R.layout.simple_list_item_1, DataProvinsi
        )
        _lvData.adapter = lvAdapter

        _btnSimpan.setOnClickListener {
            TambahData(db,_etProvinsi.text.toString(),_etIbukota.text.toString())
        }
    }
    fun TambahData(db:FirebaseFirestore, Provinsi:String,Ibukota:String){
        val dataBaru = daftarProvinsi(Provinsi,Ibukota)
        db.collection("tbProvinsi")
            .add(dataBaru).addOnSuccessListener {
                _etProvinsi.setText("")
                _etIbukota.setText("")
                Log.d("Firebase", "Data Berhasil Disimpan")
            }
            .addOnFailureListener {
                Log.d("Firebase", it.message.toString())
            }
    }
}