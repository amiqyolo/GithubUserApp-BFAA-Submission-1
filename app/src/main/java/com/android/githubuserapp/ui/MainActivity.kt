package com.android.githubuserapp.ui

import android.content.ContentValues.TAG
import android.content.Intent
import android.os.Bundle
import android.util.Log
import android.widget.Toast
import androidx.appcompat.app.AppCompatActivity
import androidx.appcompat.widget.SearchView
import androidx.recyclerview.widget.LinearLayoutManager
import com.android.githubuserapp.R
import com.android.githubuserapp.adapter.UserAdapter
import com.android.githubuserapp.databinding.ActivityMainBinding
import com.android.githubuserapp.model.User
import org.json.JSONObject
import java.util.*
import kotlin.collections.ArrayList

class MainActivity : AppCompatActivity() {

    private lateinit var binding: ActivityMainBinding
    private var list: ArrayList<User> = arrayListOf()
    private var tempList: ArrayList<User> = arrayListOf()

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)

        binding = ActivityMainBinding.inflate(layoutInflater)
        setContentView(binding.root)
        this.setSupportActionBar(binding.toolbar)
        binding.rvGithubUser.setHasFixedSize(true)

        addItem()
        searchUsers()
        showRecycler()
    }

    private fun searchUsers() {
        binding.itemSearch.setOnQueryTextListener(object : SearchView.OnQueryTextListener {
            override fun onQueryTextSubmit(query: String?): Boolean {
                return false
            }

            override fun onQueryTextChange(newText: String?): Boolean {
                tempList.clear()
                val searchText = newText?.toLowerCase(Locale.getDefault())
                if (searchText?.isNotEmpty() == true) {
                    list.forEach {
                        if (it.name.toLowerCase(Locale.getDefault()).contains(searchText)) {
                            tempList.add(it)
                        }
                    }
                    binding.rvGithubUser.adapter?.notifyDataSetChanged()
                } else {
                    tempList.clear()
                    tempList.addAll(list)
                    binding.rvGithubUser.adapter?.notifyDataSetChanged()
                }
                return true
            }
        })
    }

    private fun showRecycler() {
        binding.rvGithubUser.layoutManager = LinearLayoutManager(this)
        val adapter = UserAdapter(tempList)
        binding.rvGithubUser.adapter = adapter

        adapter.setOnItemClickCallBack(object : UserAdapter.OnItemClickCallBack {
            override fun onItemClicked(data: User) {
                Toast.makeText(this@MainActivity, data.name, Toast.LENGTH_SHORT).show()
                val intent = Intent(this@MainActivity, DetailActivity::class.java).apply {
                    putExtra(DetailActivity.EXTRA_USER, data)
                }
                startActivity(intent)
            }
        })
    }

    private fun addItem(): ArrayList<User> {
        try {
            val jsonArray = JSONObject(loadJson()).getJSONArray("users")
            for (i in 0 until jsonArray.length()) {
                val dataObject = jsonArray.getJSONObject(i)
                val avatar = resources.getIdentifier(
                    dataObject.getString("avatar"),
                    "drawable", packageName
                )
                val company = dataObject.getString("company")
                val follower = dataObject.getInt("follower")
                val following = dataObject.getInt("following")
                val location = dataObject.getString("location")
                val name = dataObject.getString("name")
                val repository = dataObject.getInt("repository")
                val username = dataObject.getString("username")
                val user =
                    User(avatar, company, follower, following, location, name, repository, username)
                list.add(user)
                tempList.add(user)
            }
        } catch (e: Exception) {
            Log.d(TAG, "Failed addItem: ${e.stackTrace}")
        }
        return list
    }

    private fun loadJson(): String {
        var json: String? = ""
        try {
            json = this.resources.openRawResource(R.raw.githubuser).bufferedReader()
                .use { it.readText() }
        } catch (e: Exception) {
            Log.d(TAG, "Failed loadJson: ${e.stackTrace}")
        }
        return json.toString()
    }

}