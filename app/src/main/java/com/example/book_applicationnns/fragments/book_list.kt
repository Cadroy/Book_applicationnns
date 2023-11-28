package com.example.book_applicationnns.fragments

import android.os.Bundle
import android.provider.ContactsContract.Data
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.recyclerview.widget.LinearLayoutManager
import com.example.book_applicationnns.Adapter.BookListAdapter
import com.example.book_applicationnns.Prompt.Add_books
import com.example.book_applicationnns.R
import com.example.book_applicationnns.RealmDB.DataBase
import com.example.book_applicationnns.databinding.FragmentBookListBinding

class book_list : Fragment(), View.OnClickListener, Add_books.onAddDismissInterface {
    private lateinit var binding: FragmentBookListBinding
    private lateinit var adapter: BookListAdapter
    private lateinit var llm: LinearLayoutManager


    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        binding = FragmentBookListBinding.inflate(inflater,container,false)

        val DatasB = DataBase.queryBook("List")
        adapter = BookListAdapter(DatasB,requireContext())
        if(DatasB.size > 0){
            binding.booklistrecycle.adapter = adapter
            llm = LinearLayoutManager(this.context)
            llm.orientation = LinearLayoutManager.VERTICAL
            binding.booklistrecycle.layoutManager = llm
        }

        binding.addBtn.setOnClickListener(this)
        return binding.root
    }

    override fun onClick(v: View?) {
        when(v!!.id){
            R.id.add_btn->{
                var dialog = Add_books()
                dialog.onDismissInterfaceCallBack = this
                dialog.show(getParentFragmentManager(),"AddBook")
            }
        }
    }

    override fun getData() {
        val BooksData = DataBase.queryBook("List")
        adapter.updateBookList(BooksData)
    }
}