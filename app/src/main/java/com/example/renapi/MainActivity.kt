package com.example.renapi

import android.content.Intent
import android.graphics.drawable.Icon
import android.os.Bundle
import android.widget.ArrayAdapter
import android.widget.ImageButton
import android.widget.ImageView
import android.widget.Toast
import androidx.activity.enableEdgeToEdge
import androidx.appcompat.app.AppCompatActivity
import androidx.core.view.ViewCompat
import androidx.core.view.WindowInsetsCompat
import androidx.core.view.setPadding
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView
import com.example.renapi.database.Favorite
import com.example.renapi.database.FavoriteDao
import com.example.renapi.database.FavoriteRoomDatabase
import com.example.renapi.databinding.ActivityMainBinding
import com.example.renapi.network.ApiClient
import retrofit2.Call
import retrofit2.Callback
import retrofit2.Response
import java.util.concurrent.ExecutorService
import java.util.concurrent.Executors

class MainActivity : AppCompatActivity() {

    private lateinit var binding: ActivityMainBinding
    private lateinit var mFavoriteDao: FavoriteDao
    private lateinit var executorService: ExecutorService
    private var isLiked = false

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        enableEdgeToEdge()

        executorService = Executors.newSingleThreadExecutor()
        val db = FavoriteRoomDatabase.getDatabase(this)
        mFavoriteDao = db!!.favoriteDao()!!

        binding = ActivityMainBinding.inflate(layoutInflater)
        setContentView(binding.root)

        binding.rvDistilleries.layoutManager = LinearLayoutManager(this)

        val client = ApiClient.getInstance()
        val responseDistilleries = client.getAllDistilleries()

        responseDistilleries.enqueue(object: Callback<Map<String, String>> {

            override fun onResponse(p0: Call<Map<String, String>>, p1: Response<Map<String, String>>) {
                if (p1.isSuccessful && p1.body() != null) {
                    val distilleriesMap = p1.body()!!
                    val distilleriesList = distilleriesMap.values.toList()

                    mFavoriteDao.allFavorites.observe(this@MainActivity) {
                        favoriteList ->
                        val favoriteDistilleries = favoriteList.map {
                            it.distilleriesName
                        }.toSet()

                        val adapter = DistilleriesAdapter(distilleriesList, favoriteDistilleries) {
                            distilleriesItem, isLiked ->
                            handleLike(distilleriesItem, isLiked)
                        }

                        binding.rvDistilleries.adapter = adapter
                    }
                } else {
                    Toast.makeText(this@MainActivity, "Failed to load", Toast.LENGTH_LONG).show()
                }
            }

            override fun onFailure(p0: Call<Map<String, String>>, p1: Throwable) {
                Toast.makeText(this@MainActivity, "Koneksi error", Toast.LENGTH_LONG).show()
            }
        })

        binding.btnFav.setOnClickListener {
            val intent = Intent(this, SecondActivity::class.java)
            startActivity(intent)
        }

        ViewCompat.setOnApplyWindowInsetsListener(findViewById(R.id.main)) {
            v, insets ->
            val systemBars = insets.getInsets(WindowInsetsCompat.Type.systemBars())
            v.setPadding(systemBars.left, systemBars.top, systemBars.right, systemBars.bottom)
            insets
        }
    }

    private fun handleLike(distilleriesItem: String, isLiked: Boolean) {
        executorService.execute {
            if (isLiked) {
                mFavoriteDao.insert(Favorite(distilleriesName = distilleriesItem))
            } else {
                mFavoriteDao.deleteByDistilleriesName(distilleriesItem)
            }
        }
    }
}