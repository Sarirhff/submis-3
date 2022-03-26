package com.example.submission2.Detail

import android.os.Bundle
import android.view.View
import androidx.fragment.app.Fragment
import androidx.lifecycle.ViewModelProvider
import androidx.recyclerview.widget.LinearLayoutManager
import com.example.submission2.R
import com.example.submission2.databinding.ActivityMainBinding
import com.example.submission2.databinding.FragmentFollowersBinding
import com.example.submission2.main.MainViewmodel
import com.example.submission2.main.UserAdapter

class FollowersFragment : Fragment(R.layout.fragment_followers){

    private var mbinding : FragmentFollowersBinding? = null
    private val binding get() = mbinding !!

    private lateinit var viewModel: VMFollowers
    private lateinit var adapter: UserAdapter
    private lateinit var usernm: String

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        mbinding = FragmentFollowersBinding.bind(view)

        val Arg = arguments
        usernm = Arg?.getString(DetailUserActivity.EXTRA_USER).toString()

        adapter = UserAdapter()
        adapter.notifyDataSetChanged()

        mbinding.apply {
            this!!.rvfollow.setHasFixedSize(true)
            rvfollow.layoutManager = LinearLayoutManager(activity)
            rvfollow.adapter = adapter
        }
        showloading(true)
        viewModel = ViewModelProvider(this, ViewModelProvider.NewInstanceFactory())
            .get(VMFollowers::class.java)
        viewModel.setlistfollow(usernm)
        viewModel.getlistfollowers().observe(viewLifecycleOwner){
            if(it!==null){
                adapter.setlist(it)
                showloading(false)
            }
        }
    }
    override fun onDestroyView(){
        super.onDestroyView()
        mbinding = null

    }
    private fun showloading(state: Boolean){
        if (state){
            mbinding!!.progresBar.visibility = View.VISIBLE
        }else{
            mbinding!!.progresBar.visibility = View.GONE
        }
    }
}