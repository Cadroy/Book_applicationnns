package com.example.book_applicationnns.Adapter

import android.content.Context
import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.recyclerview.widget.RecyclerView
import com.example.book_applicationnns.Models.BookDatas
import com.example.book_applicationnns.RealmDB.DataBase
import com.example.book_applicationnns.databinding.ArchiveAdapterBinding
import kotlinx.coroutines.CoroutineScope
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.Job
import kotlinx.coroutines.launch
import kotlinx.coroutines.withContext
import kotlin.coroutines.CoroutineContext

class BookArchiveAdapter(var Books: List<BookDatas>, private var context: Context): RecyclerView.Adapter<BookArchiveAdapter.BooksViewHolder>() {


    private lateinit var coroutine: CoroutineContext

    inner class BooksViewHolder(val binding: ArchiveAdapterBinding) : RecyclerView.ViewHolder(binding.root) {
        fun bindBookList(ReadList: BookDatas){
            binding.textView.text = ReadList.BookName
            binding.undobtn.setOnClickListener{
                coroutine = Job() + Dispatchers.IO
                val scope = CoroutineScope(coroutine)
                scope.launch(Dispatchers.IO) {
                    withContext(Dispatchers.Main) {
                        DataBase.updateBookStatus(ReadList.id, "List")
                        val BooksData = DataBase.queryBook("Archive")
                        updateBookList(BooksData)
                    }
                }
            }
            binding.deletebtn.setOnClickListener{
                coroutine = Job() + Dispatchers.IO
                val scope = CoroutineScope(coroutine)
                scope.launch(Dispatchers.IO) {
                    withContext(Dispatchers.Main) {
                        DataBase.deleteBook(ReadList.id)
                        val BooksData = DataBase.queryBook("Archive")
                        updateBookList(BooksData)
                    }
                }
            }
        }
    }

    override fun onCreateViewHolder(
        parent: ViewGroup,
        viewType: Int
    ): BookArchiveAdapter.BooksViewHolder {
        return BooksViewHolder(ArchiveAdapterBinding.inflate(LayoutInflater.from(parent.context),parent,false))
    }

    override fun onBindViewHolder(holder: BookArchiveAdapter.BooksViewHolder, position: Int) {
        val Readme = Books[position]
        holder.bindBookList(Readme)
    }

    override fun getItemCount(): Int {
        return Books.size
    }

    fun updateBookList(bookList: ArrayList<BookDatas>){
        this.Books = arrayListOf()
        notifyDataSetChanged()
        this.Books = bookList
        this.notifyItemInserted(this.Books.size)
    }
}