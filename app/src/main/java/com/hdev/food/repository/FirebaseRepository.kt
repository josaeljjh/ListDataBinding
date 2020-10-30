package com.hdev.food.repository

import com.google.android.gms.tasks.Tasks
import com.google.firebase.firestore.FirebaseFirestore
import com.google.firebase.firestore.QuerySnapshot
import com.google.gson.Gson
import com.hdev.food.models.ListModel
import java.util.concurrent.ExecutionException

class FirebaseRepository {
   // Access a Cloud Firestore instance from your Activity
   val db = FirebaseFirestore.getInstance()
}