package com.example.glory

import android.content.Intent
import android.os.Bundle
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import com.example.glory.databinding.FragmentHomeBinding


class HomeFragment : Fragment() {

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        // Inflate the layout for this fragment
       // return inflater.inflate(R.layout.fragment_home, container, false)

        val bind = FragmentHomeBinding.inflate(layoutInflater)

        bind.categoryAlbum.setOnClickListener {
            val intent = Intent (this@HomeFragment.requireContext(), CategoryAlbum::class.java)
            startActivity(intent)

        }
        bind.searchIcon.setOnClickListener {
            val intent = Intent (this@HomeFragment.requireContext(), Search::class.java)
            startActivity(intent)

        }
        bind.addCategory.setOnClickListener {
            val intent = Intent (this@HomeFragment.requireContext(), CreateCategory::class.java)
            startActivity(intent)

        }
        bind.alerts.setOnClickListener {
            val intent = Intent (this@HomeFragment.requireContext(), Alerts::class.java)
            startActivity(intent)

        }
        return bind.root
    }
}


