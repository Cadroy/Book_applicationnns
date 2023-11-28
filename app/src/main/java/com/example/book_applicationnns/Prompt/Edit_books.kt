package com.example.book_applicationnns.Prompt

import android.os.Bundle
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.Toast
import androidx.fragment.app.DialogFragment
import com.example.book_applicationnns.R
import com.example.book_applicationnns.RealmDB.DataBase
import com.example.book_applicationnns.databinding.FragmentEditBooksBinding
import kotlinx.coroutines.CoroutineScope
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.Job
import kotlinx.coroutines.launch
import kotlinx.coroutines.withContext
import org.mongodb.kbson.BsonObjectId
import kotlin.coroutines.CoroutineContext

class Edit_books : DialogFragment(), View.OnClickListener {
    private lateinit var binding: FragmentEditBooksBinding
    private lateinit var coroutine: CoroutineContext
    lateinit var onDismissInterfaceCallBack: onEdtDismissInterface
    var ObjectIdHex = ""

    interface onEdtDismissInterface {
        fun getData()
    }
    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        binding = FragmentEditBooksBinding.inflate(inflater,container,false)
        val b = arguments
        var frombundle = b!!.getString("book_id","")
        var bundlesplit = frombundle.split('(',')')
        ObjectIdHex = bundlesplit[1]
        binding.bookTitleEdt.setText(b.getString("book_name",""))
        binding.bookAuthorEdt.setText(b.getString("book_author",""))
        binding.bookPagesEdt.setText(b.getString("book_pages",""))
        binding.bookPagereadEdt.setText(b.getString("book_pages",""))
        binding.bookPagereadEdt.setText(b.getString("book_pageread",""))
        binding.savebtn.setOnClickListener(this)
        binding.cnleditBtn.setOnClickListener(this)
        return binding.root
    }

    override fun onClick(v: View?) {
        when(v!!.id) {
            R.id.savebtn -> {
                if (binding.bookTitleEdt.text.isNullOrEmpty() || binding.bookAuthorEdt.text.isNullOrEmpty() || binding.bookPagesEdt.text.isNullOrEmpty()) {
                    Toast.makeText(context, "Fill Up All The Textbox", Toast.LENGTH_SHORT)
                } else {
                    coroutine = Job() + Dispatchers.IO
                    val scope = CoroutineScope(coroutine)
                    scope.launch(Dispatchers.IO) {
                        withContext(Dispatchers.Main) {
                            DataBase.updateBookData(
                                BsonObjectId(ObjectIdHex),
                                binding.bookTitleEdt.text.toString(),
                                binding.bookAuthorEdt.text.toString(),
                                binding.bookPagesEdt.text.toString(),
                                binding.bookPagereadEdt.text.toString()
                            )
                            onDismissInterfaceCallBack.getData()
                        }
                    }
                    Toast.makeText(context, "Succesfully Updated!", Toast.LENGTH_SHORT).show()
                    dismiss()
                }
            }

            R.id.cnledit_btn -> {
                dismiss()
            }
        }
    }
}