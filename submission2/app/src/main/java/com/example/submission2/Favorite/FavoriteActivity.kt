package com.example.submission2.Favorite

import android.content.Intent
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import androidx.lifecycle.ViewModelProvider
import androidx.recyclerview.widget.LinearLayoutManager
import com.example.submission2.Detail.DetailUserActivity
import com.example.submission2.databinding.ActivityFavoriteBinding
import com.example.submission2.database.UserFavorite
import com.example.submission2.main.UserAdapter
import com.example.submission2.model.User

class FavoriteActivity : AppCompatActivity() {

    private lateinit var mbinding: ActivityFavoriteBinding
    private lateinit var adapter: UserAdapter
    private lateinit var viewModel: FavoriteViewModel

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)

        mbinding = ActivityFavoriteBinding.inflate(layoutInflater)
        setContentView(mbinding.root)

        supportActionBar!!.title = "Favorite User "

        adapter = UserAdapter()
        adapter.notifyDataSetChanged()

        viewModel = ViewModelProvider(this).get(FavoriteViewModel::class.java)

        adapter.setOnItemClickCallback(object : UserAdapter.OnItemClickCallback {
            override fun onItemClicked(data: User) {
                Intent(this@FavoriteActivity, DetailUserActivity::class.java).also {
                    it.putExtra(DetailUserActivity.EXTRA_USER, data.login)
                    it.putExtra(DetailUserActivity.EXTRA_ID, data.id)
                    it.putExtra(DetailUserActivity.EXATRA_AVATAR, data.avatar_url)
                    startActivity(it)
                }
            }

        })
        mbinding.apply {
            rvUser.setHasFixedSize(true)
            rvUser.layoutManager = LinearLayoutManager(this@FavoriteActivity)
            rvUser.adapter = adapter
        }
        viewModel.getUserFavorite()?.observe(this,{
            if(it!==null){
                val list =mapList(it)
                adapter.setlist(list)
            }
        })
    }

    private fun mapList(users: List<UserFavorite>): ArrayList<User> {
        val listUsers = ArrayList<User>()
        for (user in users){
            val userMapped = User(
                user.login,
                user.id,
                user.avatar_url
            )
            listUsers.add(userMapped)
            }
        return listUsers
    }
}