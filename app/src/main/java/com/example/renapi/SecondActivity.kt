package com.example.renapi

import android.os.Bundle
import android.widget.ArrayAdapter
import androidx.activity.enableEdgeToEdge
import androidx.appcompat.app.AppCompatActivity
import androidx.core.view.ViewCompat
import androidx.core.view.WindowInsetsCompat
import com.example.renapi.database.Favorite
import com.example.renapi.database.FavoriteDao
import com.example.renapi.database.FavoriteRoomDatabase
import com.example.renapi.databinding.ActivitySecondBinding
import java.util.concurrent.ExecutorService
import java.util.concurrent.Executors

class SecondActivity : AppCompatActivity() {

    private lateinit var mFavoriteDao: FavoriteDao
    private lateinit var executorService: ExecutorService
    private lateinit var binding: ActivitySecondBinding

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        enableEdgeToEdge()

        binding = ActivitySecondBinding.inflate(layoutInflater)
        setContentView(binding.root)

        executorService = Executors.newSingleThreadExecutor()
        val db = FavoriteRoomDatabase.getDatabase(this)
        mFavoriteDao = db!!.favoriteDao()!!

        binding.btnBack.setOnClickListener {
            onNavigateUp()
            finish()
        }
    }

    override fun onResume() {
        super.onResume()
        getAllFavorites()
    }

    private fun getAllFavorites() {
        mFavoriteDao.allFavorites.observe(this) {
            favorites ->

            val davoriteDistilleriesName = favorites.map {
                it.distilleriesName
            }

            val adapter: ArrayAdapter<String> =
                ArrayAdapter(this, android.R.layout.simple_list_item_1, davoriteDistilleriesName)

            binding.lvFav.adapter = adapter
        }
    }
}