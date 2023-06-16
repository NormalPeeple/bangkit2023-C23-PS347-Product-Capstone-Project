package com.example.storyvan.activity

import android.content.Intent
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.view.Menu
import android.view.MenuItem
import androidx.activity.viewModels
import androidx.recyclerview.widget.LinearLayoutManager
import com.example.storyvan.R
import com.example.storyvan.adapter.StoryAdapter
import com.example.storyvan.databinding.ActivityMainBinding
import com.example.storyvan.helper.ViewModelFactory
import com.example.storyvan.viewmodels.MainViewModel

class MainActivity : AppCompatActivity() {
    private lateinit var binding: ActivityMainBinding
    private lateinit var viewModelFactory: ViewModelFactory
    private lateinit var storyAdapter: StoryAdapter
    private var token = ""
    private val mainViewModel: MainViewModel by viewModels { viewModelFactory }


    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = ActivityMainBinding.inflate(layoutInflater)
        setContentView(binding.root)

        viewModelFactory=ViewModelFactory.getInstance(this)

        storyAdapter = StoryAdapter()
        binding.rvStory.apply {
            layoutManager = LinearLayoutManager(this@MainActivity)
            adapter = storyAdapter
        }

        mainViewModel.checkToken().observe(this) {
            token = it.token
            if (!it.isLogin) {
                val intent = Intent(this, LoginActivity::class.java)
                intent.flags = Intent.FLAG_ACTIVITY_NEW_TASK or Intent.FLAG_ACTIVITY_CLEAR_TASK
                startActivity(intent)
                finish()
            }else {
                mainViewModel.getListStory.observe(this@MainActivity) { pagingData ->
                    storyAdapter.submitData(lifecycle, pagingData)
                }
            }
        }

        binding.buttonAdd.setOnClickListener {
            startActivity(Intent(this, AddStoryActivity::class.java))
        }
    }

    override fun onCreateOptionsMenu(menu: Menu): Boolean {
        menuInflater.inflate(R.menu.option_menu, menu)
        return super.onCreateOptionsMenu(menu)
    }

    override fun onOptionsItemSelected(item: MenuItem): Boolean {
        if (item.itemId == R.id.menu_logout) {
            mainViewModel.logout()
        }
        return super.onOptionsItemSelected(item)
    }

}