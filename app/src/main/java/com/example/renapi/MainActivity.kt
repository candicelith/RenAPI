package com.example.renapi

import android.os.Bundle
import android.widget.ArrayAdapter
import android.widget.Toast
import androidx.activity.enableEdgeToEdge
import androidx.appcompat.app.AppCompatActivity
import androidx.core.view.ViewCompat
import androidx.core.view.WindowInsetsCompat
import androidx.recyclerview.widget.LinearLayoutManager
import com.example.renapi.databinding.ActivityMainBinding
import com.example.renapi.model.Distilleries
import com.example.renapi.network.ApiClient
import retrofit2.Call
import retrofit2.Callback
import retrofit2.Response

class MainActivity : AppCompatActivity() {

    private lateinit var binding: ActivityMainBinding
    private lateinit var distilleriesAdapter: DistilleriesAdapter

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        enableEdgeToEdge()

        binding = ActivityMainBinding.inflate(layoutInflater)
        setContentView(binding.root)

        binding.rvDistilleries.layoutManager = LinearLayoutManager(this)

        val client = ApiClient.getInstance()
        val responseDistilleries = client.getAllDistilleries()

        responseDistilleries.enqueue(object: Callback<List<Distilleries>> {

            override fun onResponse(p0: Call<List<Distilleries>>, p1: Response<List<Distilleries>>) {
                if (p1.isSuccessful && p1.body() != null) {
                    val distilleriesList = p1.body()!!
                    distilleriesAdapter = DistilleriesAdapter(distilleriesList)
                    binding.rvDistilleries.adapter = distilleriesAdapter
                } else {
                    Toast.makeText(this@MainActivity, "Failed to load", Toast.LENGTH_LONG).show()
                }
            }

            override fun onFailure(p0: Call<List<Distilleries>>, p1: Throwable) {
                Toast.makeText(this@MainActivity, "Koneksi error", Toast.LENGTH_LONG).show()
            }
        })
    }
}