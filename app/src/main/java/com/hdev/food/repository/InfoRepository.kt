package com.hdev.food.repository

import com.google.android.gms.tasks.Task
import com.google.android.gms.tasks.Tasks
import com.google.firebase.firestore.DocumentSnapshot
import com.google.firebase.firestore.FirebaseFirestore
import com.google.firebase.firestore.QuerySnapshot
import com.google.gson.Gson
import com.hdev.food.models.ListModel
import io.grpc.internal.SharedResourceHolder
import kotlinx.coroutines.ExperimentalCoroutinesApi
import kotlinx.coroutines.channels.awaitClose
import kotlinx.coroutines.delay
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.callbackFlow
import kotlinx.coroutines.flow.collect
import kotlinx.coroutines.flow.flow
import java.util.*
import java.util.concurrent.ExecutionException
import kotlin.collections.ArrayList

@ExperimentalCoroutinesApi
class InfoRepository(private val firebase: FirebaseRepository) {

    private var data: ArrayList<ListModel>? = ArrayList()
    //coroutines flow
    suspend fun getDataFirestone(): Flow<ArrayList<ListModel>?> = callbackFlow {
        val eventDocument = firebase.db.collection("municipios")
        val suscription =
            eventDocument.addSnapshotListener { documentSnapshot, firebaseFirestonException ->
                if (documentSnapshot?.documents!!.isNotEmpty()) {
                    documentSnapshot.documents.forEach { document ->
                        //serealizar datos
                        val jsonString = Gson().toJson(document.data)
                        val jsonModel = Gson().fromJson(jsonString, ListModel::class.java)
                        data?.add(jsonModel)
                    }
                    offer(data)
                } else {
                    offer(data)
                }
                data = ArrayList()
            }
        awaitClose { suscription.remove() }
    }

    /*   suspend fun getData() =
       data?.let {
           data
       } ?: getDataFirestone()*/

    /* private suspend fun getDataFirestone(): Flow<ArrayList<ListModel>?> {
         val listModel: ArrayList<ListModel> = ArrayList()
         val querySnapshot = firebase.db.collection("municipios").get()
         try {
             val documentSnapshot: QuerySnapshot? = Tasks.await(querySnapshot)
                 documentSnapshot?.documents?.forEach { document ->
                     //serealizar datos
                     val jsonString = Gson().toJson(document.data)
                     val jsonModel = Gson().fromJson(jsonString, ListModel::class.java)
                     listModel.add(jsonModel)
                 }
         } catch (e: ExecutionException) {
             e.printStackTrace()
         }
         data = listModel
         return data
     }*/
}




