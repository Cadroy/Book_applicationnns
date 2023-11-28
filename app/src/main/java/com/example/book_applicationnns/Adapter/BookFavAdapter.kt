package com.example.book_applicationnns.Adapter

import android.content.Context
import android.os.Bundle
import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.appcompat.app.AppCompatActivity
import androidx.recyclerview.widget.RecyclerView
import com.example.book_applicationnns.Models.BookDatas
import com.example.book_applicationnns.Prompt.Edit_books
import com.example.book_applicationnns.RealmDB.DataBase
import com.example.book_applicationnns.databinding.ArchiveAdapterBinding
import com.example.book_applicationnns.databinding.FavoriteAdapterBinding
import kotlinx.coroutines.CoroutineScope
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.Job
import kotlinx.coroutines.launch
import kotlinx.coroutines.withContext
import kotlin.coroutines.CoroutineContext

class BookFavAdapter(var Books: List<BookDatas>, private var context: Context): RecyclerView.Adapter<BookFavAdapter.BooksViewHolder>() {


    private lateinit var coroutine: CoroutineContext

    inner class BooksViewHolder(val binding: FavoriteAdapterBinding) : RecyclerView.ViewHolder(binding.root),
        Edit_books.onEdtDismissInterface {
        fun bindBookList(ReadList: BookDatas){
            binding.tvFavAuthor.text = binding.tvFavAuthor.text.toString()+" "+ReadList.Author
            binding.tvFavBooktitle.text = binding.tvFavBooktitle.text.toString()+" "+ReadList.BookName
            binding.tvFavBookPage.text = binding.tvFavBookPage.text.toString()+" "+ReadList.Pages
            binding.tvFavBookpublished.text =  binding.tvFavBookpublished.text.toString()+" "+ReadList.PublishedDate
            binding.tvFavPageread.text = binding.tvFavPageread.text.toString()+" "+ReadList.PageRead
            binding.unfavBtn.setOnClickListener{
                coroutine = Job() + Dispatchers.IO
                val scope = CoroutineScope(coroutine)
                scope.launch(Dispatchers.IO) {
                    withContext(Dispatchers.Main) {
                        DataBase.updateBookStatus(ReadList.id, "List")
                        val BooksData = DataBase.queryBook("Favorite")
                        updateBookList(BooksData)
                    }
                }
            }
            binding.edtfavBtn.setOnClickListener{
                val args = Bundle()
                args.putString("book_id", ReadList.id.toString())
                args.putString("book_name", ReadList.BookName)
                args.putString("book_author", ReadList.Author)
                args.putString("book_pages", ReadList.Pages)
                args.putString("book_pageread", ReadList.PageRead)
                var dialog = Edit_books()
                dialog.arguments = args
                dialog.onDismissInterfaceCallBack = this
                dialog.show((context as AppCompatActivity).supportFragmentManager,"edt_books")
            }
            binding.rmvbtn.setOnClickListener{
                coroutine = Job() + Dispatchers.IO
                val scope = CoroutineScope(coroutine)
                scope.launch(Dispatchers.IO) {
                    withContext(Dispatchers.Main) {
                        DataBase.updateBookStatus(ReadList.id, "Archive")
                        val BooksData = DataBase.queryBook("Favorite")
                        updateBookList(BooksData)
                    }
                }
            }
        }

        override fun getData() {
            val DataB = DataBase.queryBook("Favorite")
            updateBookList(DataB)
        }
    }

    override fun onCreateViewHolder(
        parent: ViewGroup,
        viewType: Int
    ): BookFavAdapter.BooksViewHolder {
        return BooksViewHolder(FavoriteAdapterBinding.inflate(LayoutInflater.from(parent.context),parent,false))
    }

    override fun onBindViewHolder(holder: BookFavAdapter.BooksViewHolder, position: Int) {
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