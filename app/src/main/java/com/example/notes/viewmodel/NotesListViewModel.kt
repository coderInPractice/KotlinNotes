package com.example.notes.viewmodel

import android.util.Log
import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import com.example.notes.data.Notes
import com.google.firebase.firestore.DocumentSnapshot
import com.google.firebase.firestore.FirebaseFirestore

class NotesListViewModel : ViewModel() {
    // TODO: Implement the ViewModel

    private  val TAG = "NotesListViewModel"

    var firestoreDB = FirebaseFirestore.getInstance()
    var savedNotes : MutableLiveData<List<Notes>>  = MutableLiveData()

    private lateinit var snapDocs:List<DocumentSnapshot>


    fun addNotes(notesItem: Notes ){
        firestoreDB.collection("notes").add(notesItem)
            .addOnFailureListener({ Log.d(TAG, "saveNotes: unsuccessful") })
            .addOnSuccessListener({ Log.d(TAG, "saveNotes: successfull") })
    }

    fun getAllNotes() : MutableLiveData<List<Notes>> {

        firestoreDB.collection("notes")
                .addSnapshotListener { snapshot, e ->
                    if (e != null) {
                        Log.w(TAG, "Listen failed.", e)
                        return@addSnapshotListener
                    }

                    if (snapshot != null) {

                        snapDocs = snapshot.documents
                        for(doc in snapDocs){
                            Log.d(TAG, "id => ${doc.id}: ")

                        }
                        val notes: MutableList<Notes> = snapshot.toObjects(Notes::class.java)

                        savedNotes.value = notes
                    } else {
                        Log.d(TAG, "data: null")
                    }
                }
        return savedNotes
    }

    fun deleteNote(id:String){
        firestoreDB.collection("notes").document(id).delete()
    }

    fun getId(pos:Int):String{
        val snapDoc = snapDocs[pos]

        return snapDoc.id
    }
}