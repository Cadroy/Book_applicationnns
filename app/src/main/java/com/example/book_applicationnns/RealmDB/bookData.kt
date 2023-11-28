package com.example.book_applicationnns.RealmDB

import com.example.book_applicationnns.Models.BookDatas
import io.realm.kotlin.Realm
import io.realm.kotlin.RealmConfiguration
import io.realm.kotlin.ext.query
import io.realm.kotlin.query.RealmResults
import io.realm.kotlin.types.RealmObject
import io.realm.kotlin.types.annotations.PrimaryKey
import org.mongodb.kbson.BsonObjectId
import org.mongodb.kbson.ObjectId

class bookData : RealmObject {
    @PrimaryKey
    var id: ObjectId = BsonObjectId()
    var Author: String = ""
    var BookName: String = ""
    var Pages: String = ""
    var PageRead:  String = ""
    var DateBookAdded: String = ""
    var DateModified: String = ""
    var PublishedDate: String = ""
    var BookStatus: String = ""
}

object DataBase {
    val configuration = RealmConfiguration.create(setOf(bookData::class))
    val s = configuration.deleteRealmIfMigrationNeeded
    val realm = Realm.open(configuration)

    suspend fun write(BAuthor:String, BName:String, Bpages:String,Bpageread:String, BAddedDate:String, BModifiedDate:String, BPubDate:String, BStatus:String) {

        val BlName = bookData().apply {
            Author = BAuthor
            BookName = BName
            Pages = Bpages
            PageRead = Bpageread
            DateBookAdded = BAddedDate
            DateModified = BModifiedDate
            PublishedDate = BPubDate
            BookStatus = BStatus
        }

        realm.write {
            copyToRealm(BlName)
        }

    }

    fun queryBook(BStatus:String): ArrayList<BookDatas> {
        val all: RealmResults<bookData> = realm.query<bookData>().find()
        val bList = mutableListOf<BookDatas>()
        all.forEach { Unit ->
            if(Unit.BookStatus == BStatus)
                bList.add(BookDatas(
                    Unit.id,
                    Unit.Author,
                    Unit.BookName,
                    Unit.Pages,
                    Unit.PageRead,
                    Unit.DateBookAdded,
                    Unit.DateModified,
                    Unit.PublishedDate,
                    Unit.BookStatus))
        }
        return bList as ArrayList<BookDatas>
    }

    suspend fun updateBookStatus(id: ObjectId, newStatus:String){
        realm.write {
            val book = query<bookData>("id == $0", id).find().first()
            book.BookStatus = newStatus
        }
    }

    suspend fun updateBookData(id: ObjectId, bookName:String,bookAuthor:String,bookPage:String,bookRead:String){
        realm.write {
            val book = query<bookData>("id == $0", id).find().first()
            book.BookName = bookName
            book.Author = bookAuthor
            book.Pages = bookPage
            book.PageRead = bookRead
        }
    }

    suspend fun deleteBook(id: ObjectId){
        realm.write {
            val books: bookData = query<bookData>("id == $0", id).find().first()
            delete(books)
        }
    }
    suspend fun deleteAllBook(BookStats: String){
        realm.write {
            val books = query<bookData>("BookStatus == $0", BookStats).find()
            delete(books)
        }
    }
}