package com.example.book_applicationnns.fragments

import android.os.Bundle
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.recyclerview.widget.LinearLayoutManager
import com.example.book_applicationnns.Adapter.BookListAdapter
import com.example.book_applicationnns.R
import com.example.book_applicationnns.RealmDB.DataBase
import com.example.book_applicationnns.databinding.FragmentBookArchiveBinding

class book_archive : Fragment() {
    private lateinit var binding: FragmentBookArchiveBinding
    private lateinit var adapter: BookListAdapter
    private lateinit var llm: LinearLayoutManager
    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        binding = FragmentBookArchiveBinding.inflate(inflater,container,false)

        val DataB = DataBase.queryBook("Archive")
        adapter = BookListAdapter(DataB,requireContext())
        if(DataB.size > 0){
            binding.bookarchiverecycle.adapter = adapter
            llm = LinearLayoutManager(this.context)
            llm.orientation = LinearLayoutManager.VERTICAL
            binding.bookarchiverecycle.layoutManager = llm
        }

        return binding.root
    }


}