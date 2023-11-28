package com.example.book_applicationnns.Prompt

import android.os.Build
import android.os.Bundle
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.Toast
import androidx.annotation.RequiresApi
import androidx.fragment.app.DialogFragment
import com.example.book_applicationnns.R
import com.example.book_applicationnns.RealmDB.DataBase
import com.example.book_applicationnns.databinding.FragmentAddBooksBinding
import kotlinx.coroutines.CoroutineScope
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.Job
import kotlinx.coroutines.launch
import kotlinx.coroutines.withContext
import java.time.LocalDateTime
import java.time.format.DateTimeFormatter
import kotlin.coroutines.CoroutineContext

class Add_books : DialogFragment(), View.OnClickListener {
    private lateinit var binding: FragmentAddBooksBinding
    private lateinit var coroutine: CoroutineContext
    lateinit var onDismissInterfaceCallBack: onAddDismissInterface

    interface onAddDismissInterface {
        fun getData()
    }


    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        binding = FragmentAddBooksBinding.inflate(inflater,container,false)
        binding.savebtn.setOnClickListener(this)
        binding.cnleditBtn.setOnClickListener(this)
        coroutine = Job() + Dispatchers.IO
        return binding.root
    }

    @RequiresApi(Build.VERSION_CODES.O)
    override fun onClick(v: View?) {
        val formatter = DateTimeFormatter.ofPattern("yyyy-MM-dd")
        val dateTime = LocalDateTime.now().format(formatter)
        when(v!!.id){
            R.id.savebtn->{
                if(binding.bookAuthorAdd.text.isNullOrEmpty() || binding.bookTitleAdd.text.isNullOrEmpty() || binding.bookPagesAdd.text.isNullOrEmpty()){
                    Toast.makeText(context,"Fill Up All The Textbox", Toast.LENGTH_SHORT)
                }
                else {
                    val scope = CoroutineScope(coroutine)
                    scope.launch(Dispatchers.IO) {

                        withContext(Dispatchers.Main) {
                            DataBase.write(
                                binding.bookAuthorAdd.text.toString(),
                                binding.bookTitleAdd.text.toString(),
                                binding.bookPagesAdd.text.toString(),
                                binding.bookPagereadAdd.text.toString(),
                                dateTime,
                                dateTime,
                                binding.bookPubdateAdd.text.toString(),
                                "List"
                            )
                            onDismissInterfaceCallBack.getData()
                        }
                    }
                    Toast.makeText(context,"Succesfully Inserted!", Toast.LENGTH_SHORT).show()
                    dismiss()
                }
            }
            R.id.cnledit_btn->{
                dismiss()
            }
        }
    }

}