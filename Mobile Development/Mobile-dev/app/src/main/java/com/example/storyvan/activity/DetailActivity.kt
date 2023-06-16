package com.example.storyvan.activity

import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import com.bumptech.glide.Glide
import com.example.storyvan.databinding.ActivityDetailBinding
import com.example.storyvan.response.ListStoryItem

class DetailActivity : AppCompatActivity() {
    private lateinit var binding: ActivityDetailBinding

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = ActivityDetailBinding.inflate(layoutInflater)
        setContentView(binding.root)

        val data = intent.getParcelableExtra<ListStoryItem>(EXTRA_DATA) as ListStoryItem
        binding.apply {
            textTitle.text = data.nama_event
            textDesc.text = data.deskripsi
            tvMulai.text = data.tanggal_mulai
            tvSelesai.text = data.tanggal_berakhir
//            tvKeamanan.text = data
            tvDana.text = data.nama_event
            Glide.with(this@DetailActivity)
                .load(data.foto)
                .fitCenter()
                .into(imageView)
        }
    }

    companion object {
        const val EXTRA_DATA = "extra_data"
    }
}