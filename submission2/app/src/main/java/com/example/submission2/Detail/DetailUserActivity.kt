package com.example.submission2.Detail

import android.annotation.SuppressLint
import android.os.Bundle
import android.view.View
import androidx.appcompat.app.AppCompatActivity
import androidx.lifecycle.ViewModelProvider
import com.bumptech.glide.Glide
import com.example.submission2.databinding.ActivityDetailUserBinding
import kotlinx.coroutines.CoroutineScope
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.launch
import kotlinx.coroutines.withContext

class DetailUserActivity : AppCompatActivity() {

    private lateinit var _binding: ActivityDetailUserBinding
    private lateinit var Viewmodel: DetailViewModel

    companion object {
        const val EXTRA_USER = "extra_user"
        const val EXTRA_ID = "extra_id"
        const val EXATRA_AVATAR ="extra_avatarurl"
    }

    @SuppressLint("SetTextI18n")
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)

        _binding = ActivityDetailUserBinding.inflate(layoutInflater)
        setContentView(_binding.root)

        val username = intent.getStringExtra(EXTRA_USER)
        val id = intent.getIntExtra(EXTRA_ID,0 )
        val avatar_url = intent.getStringExtra(EXATRA_AVATAR)
        val bundle = Bundle()
        bundle.putString(EXTRA_USER, username)

        showLoading(true)
        Viewmodel = ViewModelProvider(this).get(DetailViewModel::class.java)
        username?.let { Viewmodel.setUserDetail(it) }
        Viewmodel.getUserDetail().observe(this, {
            if (it !== null) {
                showLoading(false)
                _binding.apply {
                    name.text = it.name
                    userName.text = it.login
                    gitFollowers.text = it.followers
                    gitFollowing.text = """
                        Following
                        ${it.following}
                        """.trimIndent()
                    gitRepository.text = """
                        Repository
                        ${it.public_repos}
                        """.trimIndent()
                    Glide.with(this@DetailUserActivity)
                        .load(it.avatar_url)
                        .into(photo)
                    Location.text = it.location
                    Company.text = it.company
                }
            }
        })
        var isCheck = false
        CoroutineScope(Dispatchers.IO).launch{
            val count = Viewmodel.checkUser(id)
            withContext(Dispatchers.Main){
                if(count!= null){
                    if(count>0){
                        _binding.btntoglleFav.isChecked = true
                        isCheck = true
                    } else{
                        _binding.btntoglleFav.isChecked = false
                        isCheck = false
                    }
                }
            }
        }
        _binding.btntoglleFav.setOnClickListener{
            isCheck = !isCheck
            if(isCheck){
                Viewmodel.addToFavorite(username!!, id, avatar_url!!)
            }else{
                Viewmodel.removeFromFavorite(id)
            }
            _binding.btntoglleFav.isChecked = isCheck
        }

        val sectionPagerAdapter = SectionPagerAdapter(this, supportFragmentManager, bundle)
        _binding.apply {
            viewPager.adapter = sectionPagerAdapter
            barTabs.setupWithViewPager(viewPager)
        }
    }
    private fun showLoading(state: Boolean) {
        if (state) {
            _binding.progresBar.visibility = View.VISIBLE
        } else {
            _binding.progresBar.visibility = View.GONE
        }
    }
}